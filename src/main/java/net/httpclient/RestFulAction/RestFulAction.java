package net.httpclient.RestFulAction;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class RestFulAction {
    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject flow = new JSONObject();
        flow.put("switch", "00:00:00:00:00:00:00:01");
        flow.put("name", "flow_mod_1");
        flow.put("cookie", "0");
        flow.put("priority", "32769");
        flow.put("in_port", "2");
        flow.put("active", "true");
        flow.put("actions", "output=flood");
        URIBuilder uriBuilder = new URIBuilder("http://192.168.247.130:8080/wm/staticentrypusher/json");
        HttpPost httpPost = new HttpPost(uriBuilder.build());
        StringEntity stringEntity = new StringEntity(flow.toJSONString(), "UTF-8");
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Accept", "application/json");
        HttpResponse httpResponse = httpClient.execute(httpPost);
        String respContent = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        System.out.println(respContent);
        JSONObject flow2 = new JSONObject();
        flow2.put("switch", "00:00:00:00:00:00:00:01");
        flow2.put("name", "flow_mod_2");
        flow2.put("cookie", "0");
        flow2.put("priority", "32769");
        flow2.put("in_port", "3");
        flow2.put("active", "true");
        flow2.put("actions", "output=flood");
        HttpPost httpPost1 = new HttpPost(uriBuilder.build());
        httpPost1.setEntity(new StringEntity(flow2.toJSONString(), "UTF-8"));
        httpPost1.setHeader("Content-type", "application/json");
        httpPost1.setHeader("Accept", "application/json");
        HttpResponse httpResponse1 = httpClient.execute(httpPost1);
        String resContext2 = EntityUtils.toString(httpResponse1.getEntity(), "UTF-8");
        System.out.println(resContext2);
        httpClient.close();
    }
}
