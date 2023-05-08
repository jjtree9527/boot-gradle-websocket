package top.inson.gradle;

import cn.hutool.core.lang.Console;
import cn.hutool.core.net.NetUtil;
import org.junit.jupiter.api.Test;

public class TestJava {

    @Test
    public void handler(){
        String host = "www.dq-sv.com";
        String ipAddr = NetUtil.getIpByHost(host);
        Console.log("IP地址：{}", ipAddr);

    }

}
