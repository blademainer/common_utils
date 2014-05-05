package com.xiongyingqi.utils.baidu.vo;

/**
 * <pre>
 * 0	正常
 * 1	服务器内部错误
 * 2	请求参数非法
 * 3	权限校验失败
 * 4	配额校验失败
 * 5	ak不存在或者非法
 * 101	服务禁用
 * 102	不通过白名单或者安全码不对
 * 2xx	无权限
 * 3xx	配额错误
 * </pre>
 * <pre>
 *     202-205、210、233	无请求权限
 *      231	用户uid，ak不存在
 *      232	用户、ak被封禁
 *      234	sn签名计算错误
 *      210	权限资源不存在
 *      345	分钟配额超额
 *      346	月配额超额
 *      347	年配额超额
 *      348	永久配额超额无请求权限
 *      355	日配额超额
 *      350	配额资源不存在
 * </pre>
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/5/4 0004.
 */
public enum Status {
    SUCCESS(0, "正常"),
    SERVER_ERRO(1, "服务器内部错误"),
    ILLEGAL_PARAM(2, "请求参数非法"),
    PERMISSION_ERRO(3, "权限校验失败"),
    LIMIT_ERRO(4, "配额校验失败"),
    AK_ERRO(5, "ak不存在或者非法"),
    SERVICE_DISABLE_ERRO(101, "服务禁用"),
    SECURITY_ERRO(102, "不通过白名单或者安全码不对"),
    NO_PERMISSION_ERRO1(202, "无请求权限"),
    NO_PERMISSION_ERRO2(203, "无请求权限"),
    NO_PERMISSION_ERRO3(204, "无请求权限"),
    NO_PERMISSION_ERRO4(205, "无请求权限"),
    NO_PERMISSION_ERRO5(210, "无请求权限"),
    NO_PERMISSION_ERRO6(233, "无请求权限"),
    NO_SUCK_USER(231, "用户uid，ak不存在"),
    USER_DISABLED(232, "用户、ak被封禁"),
    SN_SIGNED_ERRO(234, "sn签名计算错误"),
    NO_PERMISSION_RESOURCE(210, "权限资源不存在"),
    MINUTE_LIMIT_ERRO(345, "分钟配额超额"),
    DAY_LIMIT_ERRO(355, "日配额超额"),
    MONTH_LIMIT_ERRO(346, "月配额超额"),
    YEAR_LIMIT_ERRO(347, "年配额超额"),
    FOREVER_LIMIT_ERRO(348, "永久配额超额无请求权限"),
    NO_PERMISSION_RESOURCE_ERRO(350, "配额资源不存在");

    private int id;
    private String name;

    Status(int id, String name) {
        this.id = id;
        this.name = name;
        IpAddressVo.addStatus(this);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
