package com.ac.httphelper;

import com.ac.httphelper.model.IP;
import com.ac.httphelper.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:artificialunintelligent
 * @Date:2019-06-25
 * @Time:21:51
 * @Desc:从xici代理网拉取代理ip
 */
public class HttpProxyUtil {

    public static List<IP> getProxy(){
        List<IP> pl = new ArrayList<>();
        for (int i = 1; i < 20; ++i){
            try {
                Document doc = Jsoup.connect("http://www.xicidaili.com/nn/" + i)
                        .data("query", "Java")
                        .userAgent("Netscape/5")
                        .cookie("auth", "token")
                        .timeout(3000)
                        .get();
                String regex =
                        "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";
                Elements elements =
                        doc.select("td:matches(" + regex + ")");
                for(int j = 0; j < elements.size(); ++j) {
                    Element e = elements.get(j);
                    Element e1 = e.nextElementSibling();
                    IP ip = new IP();
                    ip.setIp(e.text());
                    ip.setPort(Integer.parseInt(e1.text()));
                    if (isReachable(ip.getIp())){
                        System.out.println("proxy : " + ip);
                        pl.add(ip);
                    }
                    if (5 <= pl.size()){
                        return pl;
                    }
                }
            }catch (Exception e) {
                System.out.println("getProxy error" + e);
            }
        }
        return null;
    }

    private static boolean isReachable(String ip) {
        boolean status = false;
        if(StringUtil.isNotEmpty(ip)) {
            try {
                status = InetAddress.getByName(ip).isReachable(3000);
            }
            catch(Exception e) {
                System.out.println("check error" + e);
            }
        }
        return status;
    }
}
