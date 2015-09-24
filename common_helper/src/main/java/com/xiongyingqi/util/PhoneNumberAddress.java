package com.xiongyingqi.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/4/15 0015.
 */
public class PhoneNumberAddress {

    public static XmlProvince getMobileAddress(String mobileNumber) throws MalformedURLException {
        String urlString = "http://life.tenpay.com/cgi-bin/mobile/MobileQueryAttribution.cgi?chgmobile=" + mobileNumber;
        URL url = new URL(urlString);

        XmlProvince result = null;
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance(XmlProvince.class);
            Unmarshaller u = jc.createUnmarshaller();

            try {
                StreamSource streamSource = new StreamSource(url.openStream());
                JAXBElement<XmlProvince> element = u.unmarshal(streamSource, XmlProvince.class);
                result = element.getValue();
                EntityHelper.print(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }

//
//        SAXReader saxReader = new SAXReader();
//        try {
//            Document document = saxReader.read(url);
//
//
//            Element root = document.getRootElement();
//            for (Iterator<Element> iterator = root.elementIterator(); iterator.hasNext(); ) {
//                Element element = iterator.next();
//                EntityHelper.print(element.getName());
//                EntityHelper.print(element.getStringValue());
//            }
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//
//
//        String jsonString = null;
//        BufferedReader buffer;
//        StringBuffer sb = new StringBuffer();
////        URL url = new URL(urlString);
//        try {
//            InputStream in = url.openStream();
//
//            buffer = new BufferedReader(new InputStreamReader(in, "gb2312"));
//            String line = null;
//            while ((line = buffer.readLine()) != null) {
//                sb.append(line);
//            }
//            in.close();
//            buffer.close();
//            jsonString = sb.toString();
//            EntityHelper.print(jsonString);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }

    /**
     * Test the phone number from which the city is using Taobao API
     *
     * @return
     * @param mobileNumber phone number
     */
    public static String calcMobileCity(String mobileNumber) throws MalformedURLException {
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonString = null;
        String urlString = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=" + mobileNumber;
        StringBuffer sb = new StringBuffer();
        BufferedReader buffer;
        URL url = new URL(urlString);
        try {
            InputStream in = url.openStream();

//solve the garbage problem
            buffer = new BufferedReader(new InputStreamReader(in, "gb2312"));
            String line = null;
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            buffer.close();
            // System.out.println(sb.toString());
            jsonString = sb.toString();
            EntityHelper.print(jsonString);
//replace \
            jsonString = jsonString.replaceAll("^[__]\\w{14}+[_ = ]+", "[");
            // System.out.println(jsonString+"]");
            String jsonString2 = jsonString + "]";
//json object into the STRING
//            array = JSONArray.fromObject(jsonString2);

//Get JSONArray of JSONObject object , easy to read array of key-value pairs in
//            jsonObject = array.getJSONObject(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        return jsonObject.getString("province");
        return null;
    }

    /**
     * <?xml version="1.0" encoding="gb2312" ?>
     * <root>
     * <ENV_CgiName>/cgi-bin/mobile/MobileQueryAttribution.cgi</ENV_CgiName>
     * <ENV_ClientAgent>Java/1.7.0_51</ENV_ClientAgent>
     * <ENV_ClientIp>59.40.152.213</ENV_ClientIp>
     * <ENV_QueryString>chgmobile=15888888888</ENV_QueryString>
     * <ENV_RequestMethod>GET</ENV_RequestMethod>
     * <ENV_referer></ENV_referer>
     * <chgmobile>15888888888</chgmobile>
     * <city>杭州 </city>
     * <province>浙江 </province>
     * <retcode>0</retcode>
     * <retmsg>OK</retmsg>
     * <supplier>移动 </supplier>
     * <tid></tid>
     * </root>
     */
    static class XmlProvince extends EntityHelper {
        private String chgmobile;
        private String city;
        private String province;
        private String retcode;
        private String retmsg;
        private String supplier;
        private String tid;

        public String getChgmobile() {
            return chgmobile;
        }

        public void setChgmobile(String chgmobile) {
            this.chgmobile = chgmobile;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getRetcode() {
            return retcode;
        }

        public void setRetcode(String retcode) {
            this.retcode = retcode;
        }

        public String getRetmsg() {
            return retmsg;
        }

        public void setRetmsg(String retmsg) {
            this.retmsg = retmsg;
        }

        public String getSupplier() {
            return supplier;
        }

        public void setSupplier(String supplier) {
            this.supplier = supplier;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }
    }

    static class PhoneProvince {
        /**
         * 前缀
         */
        private String mts;
        /**
         * 省份
         */
        private String province;
        /**
         * 服务商
         */
        private String catName;
        private String telString;
        private String areaVid;
        private String ispVid;

        public String getMts() {
            return mts;
        }

        public void setMts(String mts) {
            this.mts = mts;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }

        public String getTelString() {
            return telString;
        }

        public void setTelString(String telString) {
            this.telString = telString;
        }

        public String getAreaVid() {
            return areaVid;
        }

        public void setAreaVid(String areaVid) {
            this.areaVid = areaVid;
        }

        public String getIspVid() {
            return ispVid;
        }

        public void setIspVid(String ispVid) {
            this.ispVid = ispVid;
        }
    }


    /**
     * Calculate multiple numbers of attribution
     *
     * @throws java.net.MalformedURLException
     * @param mobileNumbers number list
     */
//    public static JSONObject calcMobilesCities(List<String> mobileNumbers) throws MalformedURLException{
//        JSONObject jsonNumberCity = new JSONObject();
//        for(String mobileNumber : mobileNumbers){
//            jsonNumberCity.put(mobileNumber, calcMobileCity(mobileNumber));            ;
//        }
//        return jsonNumberCity;
//    }
    public static void main(String[] args) throws Exception {
        TimerHelper.getTime();
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            long number = 13000000000L + random.nextInt(1000000000);
            String testMobileNumber = "" + number;
//            System.out.println(getMobileAddress(testMobileNumber));
            ThreadPool.invoke(null, PhoneNumberAddress.class.getMethod("getMobileAddress", String.class), testMobileNumber);
        }
        ThreadPool.shutDown();
        System.out.println(TimerHelper.getTime());
//        System.out.println(calcMobilesCities(mobileList).toString());
    }
}
