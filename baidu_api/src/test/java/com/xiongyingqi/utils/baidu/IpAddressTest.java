package com.xiongyingqi.utils.baidu;

import com.xiongyingqi.util.EntityHelper;
import com.xiongyingqi.utils.baidu.ip.IpAddress;
import com.xiongyingqi.utils.baidu.ip.vo.IpAddressVo;
import org.junit.Test;

public class IpAddressTest {

    @Test
    public void testGetIp() throws Exception {
        IpAddress address = new IpAddress();
        IpAddressVo ipAddressVo = address.getIp("");
        EntityHelper.print(ipAddressVo);
    }
}