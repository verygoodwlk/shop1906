package com.qf.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

public class AliPayUtil {

    private static AlipayClient alipayClient = null;

    static {
        //初始化alipay的核心对象
        alipayClient = new DefaultAlipayClient(
                " https://openapi.alipaydev.com/gateway.do",//换成沙箱的网关
                "2016073000127352",//商户创建应用时的APPID
                "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKSyYjOvn7eHRspXxO6mK766ERA/lk7KAp8/Q6VlS+0IfXWrNePnAvunKET94pPRRUyn6zBBCnZDVzV9Yisx2Q5d7ZDy7GUE5snef5hbrYiD88DJZcsSJtMJFt2ZU8AlQ2ZniqveDP49fXBPAYqjgRzQ5imN9S5kdQvOLZMUph+VAgMBAAECgYA7o60T8mDv4LitxSK3GM12f4J2U9BmL5jLeelRF2FrMEveNGWLZbga0OkwMo+932Ys4BG2o5baRIBsCj9mTOLAUBbhb1i0o2xpRUgorayQfcxaprJ9ak4E8leIMDbk8jRDDTVDHB0wjy88usHErq3knu1nf8Q0/WhqCE8xyd2NAQJBANrk/tm50W9VG+bzr6OOYwbr3A5aarKMbVN7Y1DeAvOVRoxSz9R/fsa+rBbwozrkHPAOKRM+sS855pPvAl/0dRECQQDAnXWuxTH7IRkACAawChwZhXNjyS96RmUwDr4+ocf4JnO4nBcMswei7nJNOZlZI2ok6QT0Q+vz45ppOEk9kXJFAkEAki3XG6OB/62Aoq0eIEWYUCj2ngc3teEdqWB/JKP+qJiJsnAjJq+2BBeVaQRunYd048MxHw8A2J78ItK8bUQdYQJBAIU1YIn5/tL61NtcsaNVmpFb5a2BGcCqpphR2ehe4nkPvsBR0u8JcaKQ3aPizdcD0wirzq1PvALzyXozXxOhXzECQEpSqkKKsajprBMfuhAhga9OEwJ1Uh95k9+L4g4rJCWQ4D1d9JhUrM75cDxEtr9jHOVm5mmvCGdy4HJ/RwTFXjw=",//开发者私钥
                "json",//支持返回的格式
                "UTF-8",
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB",//支付宝的公钥
                "RSA");
    }

    public static AlipayClient getAlipayClient(){
        return alipayClient;
    }
}
