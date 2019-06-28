package com.ac.httphelper;

import com.ac.httphelper.model.Pair;
import com.ac.httphelper.model.Proxy;
import com.ac.httphelper.util.StringUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author:artificialunintelligent
 * @Date:2019-06-25
 * @Time:21:51
 * @Desc:http请求工具真身
 */
public class HttpClientUtil {

    /**
     * post请求
     *
     * @param url     地址链接
     * @param pairs   请求参数
     * @param context 上下文
     * @param proxy   代理参数
     * @return HttpResponse HttpClientContext组成的一对数据结构
     * @throws RuntimeException
     * @throws IOException
     */
    public static Pair<HttpResponse, HttpClientContext> post(String url, List<Pair<String, String>> pairs, HttpClientContext context, Proxy proxy)
            throws RuntimeException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        System.out.println(url);
        setProxy(proxy, post);
        HttpResponse httpResponse = null;
        if (pairs == null) pairs = new ArrayList<>();
        List<NameValuePair> formparams = pairs.stream()
                .map(e -> new BasicNameValuePair(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
        if (context == null) {
            context = HttpClientContext.create();
        }

        UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
        post.setEntity(uefEntity);
        httpResponse = httpClient.execute(post, context);
        return new Pair<>(httpResponse, context);
    }

    /**
     * post请求-json参数
     *
     * @param url     链接
     * @param pairs   参数
     * @param context 上下文
     * @param proxy 代理信息
     * @return
     * @throws IOException
     */
    public static Pair<String, HttpClientContext> postForJson(String url, List<Pair<String, String>> pairs,
                                                              HttpClientContext context, Proxy proxy) throws IOException {
        Pair<HttpResponse, HttpClientContext> pair = post(url, pairs, context, proxy);
        HttpResponse httpResponse = pair.getKey();
        HttpEntity entity = httpResponse.getEntity();
        String json = entity != null ? EntityUtils.toString(entity) : null;
        return new Pair<>(json, pair.getValue());
    }


    /**
     * get请求
     *
     * @param url     请求链接
     * @param context 上下文
     * @return
     * @throws IOException
     */
    public static Pair<HttpResponse, HttpClientContext> getWithContext(String url, HttpClientContext context) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        System.out.println(url);
        HttpResponse httpResponse = null;
        if (context == null) {
            context = HttpClientContext.create();
        }
        httpResponse = httpClient.execute(get, context);
        return new Pair<>(httpResponse, context);
    }

    /**
     * get请求-代理
     *
     * @param url     请求链接
     * @param context 上下文
     * @param proxy   代理信息
     * @return
     * @throws IOException
     */
    public static HttpResponse get(String url, HttpClientContext context, Proxy proxy) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        System.out.println(url);
        setProxy(proxy, get);
        HttpResponse httpResponse = null;
        if (context == null) {
            context = HttpClientContext.create();
        }
        httpResponse = httpClient.execute(get, context);
        return httpResponse;
    }

    /**
     * get请求-返回值为json形式
     *
     * @param url     请求链接
     * @param context 上下文
     * @param proxy   代理信息
     * @return
     * @throws IOException
     */
    public static String getForJson(String url, HttpClientContext context, Proxy proxy) throws IOException {
        HttpResponse httpResponse = HttpClientUtil.get(url, context, proxy);
        String json = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        return json;
    }

    /**
     * get请求
     *
     * @param url   请求链接
     * @param proxy 代理信息
     * @return
     * @throws IOException
     */
    public static HttpResponse get(String url, Proxy proxy) throws IOException {
        HttpClientContext context = HttpClientContext.create();
        return get(url, context, proxy);
    }

    public static String convertToJson(HttpResponse httpResponse) {
        try {
            return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        } catch (Exception e) {
            System.out.println("converToJson error" + e);
        }
        return null;
    }

    private static void setProxy(Proxy proxy, HttpRequestBase request) {
        if (Objects.isNull(proxy)){
            return;
        }
        String ip = proxy.getIp();
        Integer port = proxy.getPort();
        if (StringUtil.isNotEmpty(ip)) {
            if (null == port || 0 == port) {
                port = 8080;
            }
            HttpHost host = new HttpHost(ip, port);
            RequestConfig config = RequestConfig.custom().setProxy(host).build();
            if (null != request) {
                request.setConfig(config);
            }
        }
    }
}
