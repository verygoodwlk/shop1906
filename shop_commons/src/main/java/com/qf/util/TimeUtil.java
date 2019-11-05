package com.qf.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    /**
     * 查询当前的整点
     * @return
     */
    public static Date getNow(){
        //获得当前的日历时间
        return getNextX(0);
    }

    /**
     * 获得指定后续的整点时间
     * @param next
     * @return
     */
    public static Date getNextX(int next){
        //获得当前的日历时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, next);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }


    /**
     * 根据时间计算出评分
     *
     * @return
     */
    public static double date2Score(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHH");
        String format = sdf.format(date);
        return Double.valueOf(format);
    }
}
