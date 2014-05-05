package com.xiongyingqi.utils.baidu.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiongyingqi.util.EntityHelper;

/**
 * <pre>
 * content: {
 *      address: "吉林省长春市",
 *      address_detail: {
 *          city: "长春市",
 *          city_code: 53,
 *          district: "",
 *          province: "吉林省",
 *          street: "",
 *          street_number: ""
 *      },
 * </pre>
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/5/4 0004.
 */
public class ContentVo extends EntityHelper {
    private String address;
    @JsonProperty("address_detail")
    private AddressDetail addressDetail;
    private Point point;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AddressDetail getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(AddressDetail addressDetail) {
        this.addressDetail = addressDetail;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    /**
     * <pre>
     * point: {
     *      x: "125.31364243",
     *      y: "43.89833761"
     * }
     * </pre>
     */
    public class Point extends EntityHelper {
        private double x;
        private double y;

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    /**
     * city: "长春市",
     * city_code: 53,
     * district: "",
     * province: "吉林省",
     * street: "",
     * street_number: ""
     */
    public class AddressDetail extends EntityHelper {
        private String city;

        @JsonProperty("city_code")
        private String cityCode;

        private String district;
        private String province;
        private String street;

        @JsonProperty("street_number")
        private String streetNumber;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getStreetNumber() {
            return streetNumber;
        }

        public void setStreetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
        }
    }
}
