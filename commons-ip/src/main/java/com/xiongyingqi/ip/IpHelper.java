package com.xiongyingqi.ip;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiongyingqi.http.HttpAccess;
import com.xiongyingqi.http.HttpBuilder;
import com.xiongyingqi.ip.vo.IpVo;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by blademainer<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/11/18 0018.
 */
public abstract class IpHelper {
    public static IpVo getLocalIp() {
        HttpRequestBase build = HttpBuilder.newBuilder()
                .url("ipinfo.io/json")
                .build();
        try {
            InputStream inputStream = HttpAccess.executeAndGetInputStream(HttpAccess.getClient(), build);
            ObjectMapper objectMapper = new ObjectMapper();
            IpVo ipVo = objectMapper.readValue(inputStream, IpVo.class);
            return ipVo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args){
        System.out.println(getLocalIp());
    }


}
