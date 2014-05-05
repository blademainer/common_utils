package com.xiongyingqi.http;

import com.xiongyingqi.util.EntityHelper;
import com.xiongyingqi.util.FileHelper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
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
            if (!this.url.startsWith(protocol + "://")) {
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
        System.out.println(url);
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
        requestBase.setURI(URI.create(url));

        if (nameValuePairs != null && nameValuePairs.size() > 0 && requestBase instanceof HttpPost) {
            HttpEntity requestEntity = new UrlEncodedFormEntity(nameValuePairs, charset);
            ((HttpPost) requestBase).setEntity(requestEntity);
        }

        return requestBase;
    }

    public static String readResponse(CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        ContentType contentType = ContentType.getOrDefault(entity);// 获取编码
//            EntityHelper.print(contentType);
        Charset charset = contentType.getCharset();
        InputStream inputStream = entity.getContent();
        String result = FileHelper.readInputStreamToString(inputStream, charset);
//        result = URLDecoder.decode(result, charset.toString());
        return result;
    }

    public static CloseableHttpClient getClient() {
        HttpClientBuilder client = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = client.build();
        return closeableHttpClient;
    }

    public static String execute(CloseableHttpClient closeableHttpClient, HttpRequestBase requestBase) throws IOException {
        CloseableHttpResponse response = closeableHttpClient.execute(requestBase);
        return readResponse(response);
    }

    @Test
    public void test() {
        try {
            String rs = HttpBuilder.execute(getClient(), HttpBuilder.newBuilder().url("http://10.188.199.4:8080/WEB_LOAN_WEB/login/doLogin").post().param("user.userName","xyq").param("user.userPassword","111").param("isPasswordMd5", "false").build());
            EntityHelper.print(rs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<NameValuePair> getNameValuePairs() {
        return nameValuePairs;
    }
}
