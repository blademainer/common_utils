package com.xiongyingqi.utils.baidu;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiongyingqi.http.BuildNameValuePairsHelper;
import com.xiongyingqi.http.HttpAccess;
import com.xiongyingqi.http.HttpBuilder;
import com.xiongyingqi.util.EntityHelper;
import com.xiongyingqi.util.MD5Helper;
import com.xiongyingqi.util.PropertiesHelper;
import com.xiongyingqi.util.UnicodeHelper;
import com.xiongyingqi.util.comparator.StringComparator;
import com.xiongyingqi.utils.baidu.vo.IpAddressVo;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/5/4 0004.
 */
public class IpAddress {
    private static String url;
    private static String ak;
    private static String sk;
    private static boolean sn;

    private ThreadLocal<ObjectMapper> _mapper = new ThreadLocal<ObjectMapper>() {
        @Override
        protected ObjectMapper initialValue() {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            return objectMapper;
        }
    };

    static {
        Map<String, String> propertiesMap = PropertiesHelper.readProperties(IpAddress.class.getClassLoader().getResource("baidu_api.properties").getFile());
        url = propertiesMap.get("ip-location-api");
        ak = propertiesMap.get("ak");
        sk = propertiesMap.get("sk");
        String snStr = propertiesMap.get("sn");
        if (snStr != null && "true".equals(snStr)) {
            sn = true;
        }
    }

    public IpAddressVo getIp(String ip) throws IOException {
        HttpBuilder httpBuilder = HttpBuilder.newBuilder().get().charset("UTF-8").param("ip", ip).param("ak", ak);
        httpBuilder.url(url + "?" + BuildNameValuePairsHelper.buildParameters(httpBuilder.getNameValuePairs()));
        if (sn) {
            String sign = caculateAKSN(ak, sk, url, httpBuilder.getNameValuePairs(), "POST");
            httpBuilder.param("sn", sign);
        }
        String rs = HttpAccess.execute(HttpAccess.getClient(), httpBuilder.build());
        rs = UnicodeHelper.unicodeToUtf8(rs);
        ObjectMapper mapper = _mapper.get();
        IpAddressVo ipAddressVo = mapper.readValue(rs, IpAddressVo.class);
        return ipAddressVo;
    }

    /**
     * sn = MD5(urlencode(basicString + sk))
     * <p/>
     * 其中 basicString 的算法如下：
     * <p/>
     * (1) get 请求
     * url 中 http://域名{uri}
     * basicString = uri
     * (2) post 请求
     * url 中 http://域名{uri} POST 参数按照key进行从小大到字母排序
     * 然后拼装成：k1= v1&k2= v2&k3=v3&...&kn=vn的格式=> {params}
     * basicString = uri +?+ params
     *
     * @param ak             access key
     * @param sk             secret key
     * @param url            url值，例如: /geosearch/nearby 不能带hostname和querstring，也不能带？
     * @param nameValuePairs $querystring_arrays 参数数组，key=>value形式。在计算签名后不能重新排序，也不能添加或者删除数据元素
     * @param method         method 只能为'POST'或者'GET'
     * @brief 计算SN签名算法
     */
    public String caculateAKSN(String ak, String sk, String url, List<NameValuePair> nameValuePairs, String method) {
        String params = "";
        if (method.equals("POST")) {
            Collections.sort(nameValuePairs, new Comparator<NameValuePair>() {
                @Override
                public int compare(NameValuePair o1, NameValuePair o2) {
                    StringComparator comparator = new StringComparator();
                    return comparator.compare(o1.getName(), o2.getName());
                }
            });

            StringBuilder stringBuilder = new StringBuilder();
            for (Iterator<NameValuePair> iterator = nameValuePairs.iterator(); iterator.hasNext(); ) {
                NameValuePair nameValuePair = iterator.next();
                String name = nameValuePair.getName();
                String value = nameValuePair.getValue();
                stringBuilder.append(name);
                stringBuilder.append("=");
                stringBuilder.append(value);
                if (iterator.hasNext()) {
                    stringBuilder.append("&");
                }
            }
            params = stringBuilder.toString();
        }

        String basicString = url + "? " + params;
        String sn = null;
        try {
            sn = MD5Helper.encrypt(URLEncoder.encode(basicString + sk, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sn;
    }

    public static void main(String[] args) {
        EntityHelper.print(IpAddress.class.getClassLoader().getResource("baidu_api.properties").getPath());
        EntityHelper.print(IpAddress.class.getClassLoader().getResource("baidu_api.properties").getFile());

        List<String> strs = new ArrayList<String>();
        strs.add("a");
        strs.add("421");
        strs.add("cc");
        strs.add("dd");
        strs.add("123");
        strs.add("bb");
        Collections.sort(strs, new StringComparator());
        EntityHelper.print(strs);
        StringComparator comparator = new StringComparator();
        EntityHelper.print(comparator.compare("a", "b"));
        EntityHelper.print(comparator.compare("a", "a"));
        EntityHelper.print(comparator.compare("a", "ab"));
        EntityHelper.print(comparator.compare("ab", "ac"));
    }
}
