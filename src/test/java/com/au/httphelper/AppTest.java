package com.au.httphelper;

import static org.junit.Assert.assertTrue;

import com.ac.httphelper.HttpCheckUtil;
import com.ac.httphelper.HttpClientUtil;
import com.ac.httphelper.HttpProxyUtil;
import com.ac.httphelper.model.IP;
import com.ac.httphelper.model.Pair;
import com.ac.httphelper.model.Proxy;
import org.apache.http.HttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void checkTest() {
        try {
            this.logBaidu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getIpTest() {
        List<IP> pl = HttpProxyUtil.getProxy();
        Random random = new Random();
        int i = random.nextInt(5);
        assert pl != null;
        IP ip = pl.get(i);
        Integer r = HttpCheckUtil.checkProxyIp(new Proxy(ip.getIp(), ip.getPort()));
        System.out.println(r);
    }

    public static String getOrderShow(String orderUniqueId) {
        return "https://meican.com/preorder/api/v2.1/orders/show?noHttpGetCache=" + getSystemSec() + "&restoreCart=false&type=CORP_ORDER&uniqueId=" + orderUniqueId;
    }

    private static String getSystemSec() {
        return String.valueOf(System.currentTimeMillis());
    }

    private HttpClientContext logTest() throws IOException {
        List<Pair<String, String>> pairs = new ArrayList<>();
        pairs.add(new Pair<>("username", ""));
        pairs.add(new Pair<>("password", ""));
        pairs.add(new Pair<>("remember", "true"));
        pairs.add(new Pair<>("openId", ""));
        pairs.add(new Pair<>("loginType", "username"));
        Pair<HttpResponse, HttpClientContext> responseHttpClientContextPair =
                HttpClientUtil.post("https://meican.com/account/directlogin", pairs, null, null);
        responseHttpClientContextPair = HttpClientUtil.getWithContext("https://meican.com/special?key=dscmcwawpza1y", responseHttpClientContextPair.getValue());
        String html = HttpClientUtil.convertToJson(responseHttpClientContextPair.getKey());
//        HttpResponse response = responseHttpClientContextPair.getKey();
//        String toJson =  HttpClientUtils.converToJson(response);
//        System.out.println(toJson);
        System.out.println(html);
        return responseHttpClientContextPair.getValue();
    }

    private void logBaidu() throws IOException {
        HttpResponse response =
                HttpClientUtil.get("https://www.baidu.com", new Proxy("113.200.56.13", 8010));
        System.out.println(response.getStatusLine().getStatusCode());
    }
}
