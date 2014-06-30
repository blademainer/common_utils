package com.xiongyingqi.http;

import com.xiongyingqi.util.FileHelper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/5/4 0004.
 */
public class HttpAccess {
    public static Charset defaultCharset = Charset.forName("UTF-8");

    public static CloseableHttpClient getClient() {
        HttpClientBuilder client = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = client.build();
        return closeableHttpClient;
    }

    public static String execute(CloseableHttpClient closeableHttpClient, HttpRequestBase requestBase) throws IOException {
        CloseableHttpResponse response = closeableHttpClient.execute(requestBase);
        return readResponse(response);
    }


    public static String readResponse(CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        ContentType contentType = ContentType.getOrDefault(entity);// 获取编码
//            EntityHelper.print(contentType);
        Charset charset = contentType.getCharset();
        if(charset == null){
            charset = defaultCharset;
        }
        InputStream inputStream = entity.getContent();
        String result = FileHelper.readInputStreamToString(inputStream, charset);
//        result = URLDecoder.decode(result, charset.toString());
        return result;
    }
}
