package com.sja.demo.druid;

import com.alibaba.fastjson.JSONObject;
import com.sja.demo.thread.util.ThreadPoolUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author sja  created on 2019/5/29.
 */
public class DruidTest {

    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor executor = ThreadPoolUtil.getThreadPoolExecutor();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data = sdf.parse("2019-06-10 00:00:00");
        for (int i = 0; i < 100; i++) {
            DeliveryPackageHistorySum info = new DeliveryPackageHistorySum();

            info.setTime(new DateTime().toString());
            info.setDeliveryDates("2019-06-10 00:00:00");
            info.setDeliveryDate(data);
            info.setExpressName("圆通快递");
            info.setWarehouseName("正品仓"+i);
            info.setItemNum(i+1);
            info.setPackageNum(1);
            info.setShopName("天猫旗舰店");
            info.setPostFee(5.0d);
            info.setPostCost(0.5d);
            info.setTenantId(2345334009L);
            String jsonStr = JSONObject.toJSONString(info);
            executor.execute(new MyTask(jsonStr));
        }
        //query();


        new CountDownLatch(1).await();
    }

    public static void query(){
//        String url = "http://192.168.0.135:8082/druid/v2?pretty";
        String url = "http://10.26.112.186:8082/druid/v2?pretty";
        String jsonStr = "{" +
                "    \"aggregations\": [{" +
                "        \"fieldName\": \"postCost\"," +
                "        \"name\": \"postCost\"," +
                "        \"type\": \"doubleSum\"" +
                "    }, {" +
                "        \"fieldName\": \"postFee\"," +
                "        \"name\": \"postFee\"," +
                "        \"type\": \"doubleSum\"" +
                "    }]," +
                "    \"dataSource\": \"demo_test\"," +
                "    \"dimensions\": [ \"expressName\", \"shopName\"]," +
                "    \"filter\": {" +
                "        \"fields\": [{" +
                "            \"dimension\": \"tenantId\"," +
                "            \"type\": \"selector\"," +
                "            \"value\": \"2345334009\"" +
                "        }, {" +
                "            \"dimension\": \"expressName\"," +
                "            \"type\": \"selector\"," +
                "            \"value\": \"申通快递\"" +
                "        }, {" +
                "            \"fields\": [{" +
                "                \"dimension\": \"shopName\"," +
                "                \"type\": \"selector\"," +
                "                \"value\": \"天猫店\"" +
                "            }, {" +
                "                \"dimension\": \"shopName\"," +
                "                \"type\": \"selector\"," +
                "                \"value\": \"天猫旗舰店\"" +
                "            }]," +
                "            \"type\": \"or\"" +
                "        }]," +
                "        \"type\": \"and\"" +
                "    }," +
                "    \"granularity\": \"all\","  +
                "    \"intervals\": [\"2009-06-01/2019-06-11\"]," +
                "    \"limitSpec\": {" +
                "        \"limit\": 100," +
                "        \"type\": \"default\"" +
                "    }," +
                "    \"queryType\": \"groupBy\"" +
                "}";


        String result = doPost(url,jsonStr);
        System.out.println(result);
    }


    public static String doPost(String url, String jsonStr) {

        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Connection", "close");
            StringEntity stringEntity = new StringEntity(jsonStr, Charset.forName("UTF-8"));
            httpPost.setEntity(stringEntity);
            HttpResponse response = client.execute(httpPost);
            return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
        } catch (Exception ex) {
            JSONObject jsonObjectError = new JSONObject();
            jsonObjectError.put("code", "500");
            jsonObjectError.put("msg", ex.getMessage());
            return jsonObjectError.toJSONString();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
            }
        }

    }

    static class MyTask implements Runnable {
        private String jsonStr;

        MyTask(String jsonStr) {
            this.jsonStr = jsonStr;
        }

        public void run() {
//            String url = "http://192.168.0.135:8200/v1/post/pageviews";
//            String url = "http://192.168.0.135:8200/v1/post/deliveryPackageHistorySum";
            String url = "http://10.26.112.186:8200/v1/post/demo_test";
            String result = doPost(url,jsonStr);
            System.out.println("----" + result);
        }


    }
}
