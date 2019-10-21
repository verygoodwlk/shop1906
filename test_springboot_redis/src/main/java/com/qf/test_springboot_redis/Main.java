package com.qf.test_springboot_redis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 输入一个日期，判断是这一年的第几天
 */
public class Main {

    public static void main(String[] args) throws ParseException {
        String dateStr = "2019-1-23";

        //string -> date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateStr);

        //date -> days
        SimpleDateFormat sdf2 = new SimpleDateFormat("DDD");
        String format = sdf2.format(date);
        System.out.println(dateStr + "是今年的第" + format + "天");
    }
}
