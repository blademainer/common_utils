package com.xiongyingqi.ip.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiongyingqi.util.EntityHelper;

import java.io.Serializable;

/**
 * Created by blademainer<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/11/18 0018.
 */
public class IpVo extends EntityHelper implements Serializable {
    private String ip;
    @JsonProperty("hostname")
    private String hostName;
    private String city;
    private String region;
    private String country;
    private String loc;
    private String org;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }
}
