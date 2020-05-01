package net.httpclient.RestFulAction;

import com.alibaba.fastjson.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.io.ObjectOutput;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class getFlowTable {
    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
    /*该集合用于存储目标集合，即交换机地址以及流表列表的映射关系*/
    LinkedHashMap<String, List> switchAdd_flowTableList = new LinkedHashMap<>();
    public static void recursionJson(Map<String, Object> map, LinkedHashMap<String, String> storage) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            System.out.println("这是当前key：" + key);
            Object value = entry.getValue();
 /*           if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                for (int i = 0; i < jsonArray.size(); i++) {
                    System.out.println(jsonArray.getString(i));
                }
                for (int i = 0; i < jsonArray.size(); i++) {
                    System.out.println("第" + i + "次循环");
                    deepth++;
                    String temp = jsonArray.getString(i);
                    System.out.println(temp);
                    JSONObject jsonObject = JSONObject.parseObject(temp);
                    Map<String, Object> maps = (Map<String, Object>) jsonObject;
                    System.out.println("map对象是" + maps);
                    System.out.println("递归");
                    recursionJson(maps, storage, deepth);
                }
            } else*/
            if (value instanceof JSONObject) {

                /*JSONObject jsonObject = (JSONObject) value;*/
                System.out.println("打印");
                String str = JSONObject.toJSONString(value);
                /*                System.out.println(str);*/
                JSONObject jsonObject = JSON.parseObject(str);
                System.out.println(jsonObject);

                recursionJson((Map) jsonObject, storage);


            } else {
                System.out.println("存储映射表");
                System.out.println("插入的key为" + key + "插入的value为" + value.toString());
                String tempValue = value.toString();
                storage.put(key, tempValue);
                System.out.println(storage.get(key));
                System.out.println("插入完毕");
            }

        }

    }

    public HttpEntity responseEntity = null;
    String tempResponseEntity = null;

    public LinkedHashMap<String, List> reqFlowTable() throws URISyntaxException, IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder("http://192.168.247.130:8080/wm/staticentrypusher/list/all/json");
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.addHeader("Content-type", "application/json");
        httpGet.addHeader("Accept", "application/json");
        CloseableHttpResponse response = null;
        try {
            response = closeableHttpClient.execute(httpGet);
            responseEntity = response.getEntity();
            System.out.println("响应状态" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度" + responseEntity.getContentLength());
                tempResponseEntity = EntityUtils.toString(responseEntity);
                /*下面部分为将从RESTAPI中取回的内容进行处理
                 *首先返回为entity格式，将其转为字符串之后再转为阿里fastjson中的JSONObjoct
                  * 整体的返回格式为一个key为交换机地址的map，map的value为一个数组，数组元素为单条流表
                   * 最用于存储的格式为key为交换机地址的字符串，value为自己建立的流表类的列表*/
                /*将字符串转化为json格式*/
                JSONObject jsonObject = JSON.parseObject(tempResponseEntity);
                /*将JSONObject转换为map类型*/
                Map<String, Object> add_array = (Map) jsonObject;
                /*map.size()可以返回集合内的键值对数量，由此来定义字符串数组的大小*/
                String[] switchAdd = new String[add_array.size()];
                /*使用map遍历，所以外置了一个计数器*/
                int count = 0;
                for (Map.Entry<String, Object> entry : add_array.entrySet()) {
                    /*向数组传入所有的交换机地址*/
                    switchAdd[count++] = entry.getKey();
                }


                for (int i = 0; i < switchAdd.length; i++) {
                    /*在返回值中的value为存储了流表的数组
                    * 在此处进行变量类型转换*/
                    Object object = add_array.get(switchAdd[i]);
                    JSONArray jsonArray = (JSONArray) object;
                    /*实例化作为value的list对象*/
                    List<flowTable> flowTableList = new LinkedList<>();
                    /*遍历流表数组*/
                    for (int j = 0; j < jsonArray.size(); j++) {
                        /*提取流表数组元素*/
                        String temp = jsonArray.getString(j);
                        JSONObject tempJsonObject = JSONObject.parseObject(temp);
                        /*将流表数组中的元素转换为正常的映射变量*/
                        Map<String, Object> maps = (Map<String, Object>) tempJsonObject;

                        LinkedHashMap<String, String> storage = new LinkedHashMap<String, String>();
                        /*使用迭代解析嵌套的映射，并存入提前实例化的变量中*/
                        recursionJson(maps, storage);
                        for (Map.Entry<String, String> entry : storage.entrySet()) {
                            System.out.println("key为" + entry.getKey());
                            System.out.println("value" + entry.getValue());
                        }
                        /*实例化流表对象*/
                        flowTable flowTables = new flowTable();
                        /*手动进行交换机地址与流表名赋值*/
                        flowTables.setSwitchAdd(switchAdd[i]);
                        for (Map.Entry<String, Object> entry : maps.entrySet()) {
                            flowTables.setName(entry.getKey());
                        }

                        /*使用遍历将迭代出来的map中的value对流表类赋值*/
                        for (Map.Entry<String, String> entry : storage.entrySet()) {
                            flowTables.set(entry.getKey(), entry.getValue());
                        }
                        /*将流表类传入列表中*/
                        flowTableList.add(flowTables);
                    }
                    /*将交换机地址与流表列表进行映射*/
                    switchAdd_flowTableList.put(switchAdd[i], flowTableList);
                }

            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (closeableHttpClient != null) {
                closeableHttpClient.close();

            }
            if (response != null) {
                response.close();
            }

        }
        return switchAdd_flowTableList;
    }
}
