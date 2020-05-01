package net.httpclient.RestFulAction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class test {
    public static void main(String[] args) {
        String test = "{\"cookieMask\":\"0\",\"instructions\":{\"instruction_apply_actions\":{\"actions\":\"output=flood\"}},\"cookie\":\"45035996515584882\",\"idleTimeoutSec\":\"0\",\"flags\":\"1\",\"match\":{\"in_port\":\"2\"},\"hardTimeoutSec\":\"0\",\"outGroup\":\"any\",\"priority\":\"32769\",\"outPort\":\"any\",\"version\":\"OF_13\",\"command\":\"ADD\"}";
        JSONObject jsonObject = JSON.parseObject(test);
        Map<String, Object> map = (Map<String, Object>) jsonObject;
        System.out.println("map对象为:"+map);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }
}
