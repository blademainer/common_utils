package com.xiongyingqi.http;

import com.xiongyingqi.Logger;
import com.xiongyingqi.util.EntityHelper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/4/18 0018.
 */
public class HttpBuilder {
    private String method = "GET";
    private String host = "";
    private int port = -1;
    private String url = "";
    private String uri = "";
    private Collection<Header> headers = new ArrayList<Header>();
    private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    private Charset charset = Charset.forName("UTF-8");
    private String protocol = "http";

    public static HttpBuilder newBuilder() {
        return new HttpBuilder();
    }

    public HttpBuilder host(String host) {
        this.host = host;
        return this;
    }

    public HttpBuilder port(int port) {
        this.port = port;
        return this;
    }

    public HttpBuilder url(String url) {
        this.url = url;
        return this;
    }

    public HttpBuilder uri(String uri) {
        this.uri = uri;
        return this;
    }

    public HttpBuilder header(String name, String value) {
        Header header = new BasicHeader(name, value);
        this.headers.add(header);
        return this;
    }

    public HttpBuilder param(String name, String value) {
        NameValuePair nameValuePair = new BasicNameValuePair(name, value);
        this.nameValuePairs.add(nameValuePair);
        return this;
    }

    public HttpBuilder http() {
        this.protocol = "http";
        return this;
    }

    public HttpBuilder https() {
        this.protocol = "https";
        return this;
    }


    public HttpBuilder post() {
        this.method = "POST";
        return this;
    }

    public HttpBuilder get() {
        this.method = "GET";
        return this;
    }

    public HttpBuilder delete() {
        this.method = "DELETE";
        return this;
    }

    public HttpBuilder put() {
        this.method = "PUT";
        return this;
    }


    public HttpBuilder charset(String charset) {
        Charset charsetVar = null;
        try {
            charsetVar = Charset.forName(charset);
        } catch (UnsupportedCharsetException e) {
            charsetVar = Charset.forName("UTF-8");
        }
        this.charset = charsetVar;
        return this;
    }


    private String buildUrl() {
        if (this.url != null && !"".equals(this.url.trim())) {
            if (!this.url.startsWith("http://") && !this.url.startsWith("https://")) {
                this.url = protocol + "://" + this.url;
            }
            return this.url;
        }
        String url = "";
        if (!this.url.startsWith(protocol + "://")) {
            url = protocol + "://";
        }
        url += host;
        if (port <= 0) {
            url += port;
        }
        if (!url.endsWith("/") && !url.endsWith("\\")) {
            url += "/";
        }
        url += uri;
        return url;
    }

    private HttpRequestBase buildMethod() {
        HttpRequestBase httpRequestBase = null;
        if (method.equals("GET")) {
            httpRequestBase = new HttpGet();
        } else if (method.equals("POST")) {
            httpRequestBase = new HttpPost();
        } else if (method.equals("PUT")) {
            httpRequestBase = new HttpPut();
        } else if (method.equals("DELETE")) {
            httpRequestBase = new HttpDelete();
        }
        return httpRequestBase;
    }

    public HttpRequestBase build() {
        String url = buildUrl();

        HttpRequestBase requestBase = buildMethod();
        for (Header header : headers) {
            requestBase.addHeader(header);
        }

        if (nameValuePairs != null && nameValuePairs.size() > 0) {
            if (requestBase instanceof HttpPost) {// 如果是post
                HttpEntity requestEntity = new UrlEncodedFormEntity(nameValuePairs, charset);
                ((HttpPost) requestBase).setEntity(requestEntity);
            } else { // 如果是其他方法
                StringBuilder builder = new StringBuilder(url);
                String start = "&";
                if (!url.contains("?")) {
                    start = "?";
                }

                NameValuePair nameValuePairStart = nameValuePairs.get(0);
                String nameStart = nameValuePairStart.getName();
                String valueStart = nameValuePairStart.getValue();
                builder.append(start);// ?a=b    &a=b
                builder.append(nameStart);
                builder.append("=");
                builder.append(valueStart);

                for (int i = 1; i < nameValuePairs.size(); i++) {
                    NameValuePair nameValuePair = nameValuePairs.get(i);
                    String name = nameValuePair.getName();
                    String value = nameValuePair.getValue();
                    builder.append("&");
                    builder.append(name);
                    builder.append("=");
                    builder.append(value);
                }
                url = builder.toString();
            }
        }
        requestBase.setURI(URI.create(url));
        Logger.debug(getClass(), "nameValuePairs: " + nameValuePairs + ", headers: " + headers + ", url: " + url);
        return requestBase;
    }


    public void test() {
        try {
            String rs = HttpAccess.execute(HttpAccess.getClient(), HttpBuilder.newBuilder().url("http://www.baidu.com").get().build());
            EntityHelper.print(rs);

            String encode = URLEncoder.encode("?", "UTF-8");
            System.out.println(encode);


            HttpRequestBase requestBase = HttpBuilder.newBuilder()
                    .get()
                    .url("http://xueqiu.com/stock/f10/bonus.json")
                    .param("symbol", "SZ002261")
                    .param("page", "1")
                    .param("size", "4")
                    .build();
//        InputStream inputStream = HttpAccess.executeAndGetInputStream(httpClient, requestBase);
            String s = HttpAccess.execute(HttpAccess.getClient(), requestBase);
            EntityHelper.print(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<NameValuePair> getNameValuePairs() {
        return nameValuePairs;
    }
}
