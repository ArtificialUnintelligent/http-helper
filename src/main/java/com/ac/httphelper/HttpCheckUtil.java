package com.ac.httphelper;

import com.ac.httphelper.model.Proxy;
import org.apache.http.HttpResponse;

/**
 * @author:artificialunintelligent
 * @Date:2019-06-25
 * @Time:21:51
 */
public class HttpCheckUtil {

    public static Integer checkProxyIp(Proxy proxy) {
        int statusCode = 0;
        try {
            //url暂时用百度来进行验证
            HttpResponse response =
                    HttpClientUtil.get("https://www.baidu.com", proxy);
            statusCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            System.out.println("ip " + proxy.getIp() + " is not aviable");
        }
        if(statusCode>0){
            System.out.println("host-" + proxy.getIp() + " port-" + proxy.getPort() + " status-" + statusCode);
        }
        return statusCode;
    }
}
