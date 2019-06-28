package com.ac.httphelper.model;

/**
 * @author:artificialunintelligent
 * @Date:2019-06-25
 * @Time:22:38
 */
public class Proxy {

    private String ip;

    private Integer port;

    public Proxy(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    public Proxy() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
