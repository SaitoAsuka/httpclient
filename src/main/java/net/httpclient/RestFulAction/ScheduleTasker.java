package net.httpclient.RestFulAction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduleTasker implements Runnable {
    private int corePoolSize = 10;
    ScheduledThreadPoolExecutor scheduler;

    public ScheduleTasker() {
        scheduler = new ScheduledThreadPoolExecutor(corePoolSize);
    }

    public ScheduleTasker(int quantiy) {
        corePoolSize = quantiy;
        scheduler = new ScheduledThreadPoolExecutor(corePoolSize);

    }

    public void scheduler(Runnable event, long delay) {
        scheduler.schedule(event, delay, TimeUnit.SECONDS);
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    public void run() {
        try {
            getFlowTable getFlowTable = new getFlowTable();
            LinkedHashMap<String,List> map = new LinkedHashMap();
            map = getFlowTable.reqFlowTable();
            int map_size = map.size();
            int list_size=0;
           List<String> tempList = new LinkedList<>();
            tempList.add("switchAdd");
            tempList.add("name");
            tempList.add("cookieMask")  ;
            tempList.add("actions")  ;
            tempList.add("cookie")  ;
            tempList.add("idleTimeoutSec")  ;
            tempList.add("flags") ;
            tempList.add("in_port");
            tempList.add("hardTimeoutSec") ;
            tempList.add("outGroup");
            tempList.add("priority") ;
            tempList.add("outPort") ;
            tempList.add("version");
            tempList.add("command") ;
            for (Map.Entry<String, List> entry : map.entrySet()) {
                list_size = entry.getValue().size();
                if (list_size != 0) {
                    break;
                }
            }
            for (Map.Entry<String, List> entry : map.entrySet()) {

                String tempKey = entry.getKey();
                List<flowTable> tempValue = entry.getValue();
                for (int j = 0; j < list_size; j++) {
                    flowTable tempFlowTable = tempValue.get(j);
                    tempList.add(tempFlowTable.getSwitchAdd());
                    tempList.add(tempFlowTable.getName());
                    tempList.add(tempFlowTable.getCookieMask());
                    tempList.add(tempFlowTable.getActions());
                    tempList.add(tempFlowTable.getCookie());
                    tempList.add(tempFlowTable.getIdleTimeoutSec());
                    tempList.add(tempFlowTable.getFlags());
                    tempList.add(tempFlowTable.getIn_port());
                    tempList.add(tempFlowTable.getHardTimeoutSec());
                    tempList.add(tempFlowTable.getOutGroup());
                    tempList.add(tempFlowTable.getPriority());
                    tempList.add(tempFlowTable.getOutPort());
                    tempList.add(tempFlowTable.getVersion());
                    tempList.add(tempFlowTable.getCommand());
                }
            }
            JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(tempList));
            System.out.println("此为json array格式的流表："+jsonArray);
            Thread.sleep(1);
            System.out.println(new Date());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        ScheduleTasker scheduleTasker = new ScheduleTasker(1);

        System.out.println("stub");
//        scheduleTasker.scheduler.scheduleAtFixedDelay(new ScheduleTasker(1), 0, 5, TimeUnit.SECONDS);
        scheduleTasker.scheduler.scheduleWithFixedDelay(new ScheduleTasker(1), 0, 5, TimeUnit.SECONDS);
//        scheduleTasker.shutdown();

    }
}
