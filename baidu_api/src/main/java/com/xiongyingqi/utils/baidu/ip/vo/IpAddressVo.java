package com.xiongyingqi.utils.baidu.ip.vo;

import com.xiongyingqi.util.EntityHelper;

import java.util.Collection;
import java.util.HashSet;

/**
 * <pre>
 * {
 *    address: "CN|吉林|长春|None|CERNET|1|None",
 *    content: {
 *            address: "吉林省长春市",
 *            address_detail: {
 *              city: "长春市",
 *              city_code: 53,
 *              district: "",
 *              province: "吉林省",
 *              street: "",
 *              street_number: ""
 *          },
 *       point: {
 *           x: "125.31364243",
 *           y: "43.89833761"
 *       }
 *    },
 *    status: 0
 * }
 * </pre>
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/5/4 0004.
 */
public class IpAddressVo extends EntityHelper {
    private static Collection<Status> all = new HashSet<Status>();

    private String address;

    private ContentVo content;

    private Status status;

    public static void addStatus(Status status) {
        all.add(status);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ContentVo getContent() {
        return content;
    }

    public void setContent(ContentVo content) {
        this.content = content;
    }

}
