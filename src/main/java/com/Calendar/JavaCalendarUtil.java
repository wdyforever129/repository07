package com.Calendar;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JavaCalendarUtil {

    //特殊的工作日(星期六、日工作)
    private static final List<String> SPECIAL_WORK_DAYS = new ArrayList<>();

    //特殊的休息日(星期一到五休息)
    private static final List<String> SPECIAL_REST_DAYS = new ArrayList<>();

    //维护的特殊工作日(因为是未知的，所以必须手工维护)
    static {
        initSpecialDays();
    }

    private static void initSpecialDays(){
        //工作日维护
        SPECIAL_WORK_DAYS.add("2019-09-29");
        SPECIAL_WORK_DAYS.add("2019-10-12");
        //休息日维护
        SPECIAL_REST_DAYS.add("2019-10-01");
        SPECIAL_REST_DAYS.add("2019-10-02");
        SPECIAL_REST_DAYS.add("2019-10-03");
        SPECIAL_REST_DAYS.add("2019-10-04");
        SPECIAL_REST_DAYS.add("2019-10-07");
    }

    @Test
    public void getCalendar() {
        Date date = getDate(new Date(), -7);
        System.out.println("工作日："+DateFormatUtils.format(date, "yyyy-MM-dd"));

        //Date date = getDate(DateUtils.parseDate("2019-10-01", "yyyy-MM-dd"), 6);
        //System.out.println(DateFormatUtils.format(date, "yyyy-MM-dd"));
    }

    public static Date getDate(Date currentDate, int days) {
        if (days == 0) {
            return currentDate;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        int step = days < 0 ? -1 : 1;
        int i = 0;
        int daysAbs = Math.abs(days);
        while (i < daysAbs) {
            calendar.add(Calendar.DATE, step);
            i++;
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                //如果周六上班就算一个工作日
                if (!SPECIAL_WORK_DAYS.contains(DateFormatUtils.format(calendar.getTime(),"yyyy-MM-dd"))){
                    i--;
                }
            }else {
                //周一到周五休息就算一个休息日
                if (SPECIAL_REST_DAYS.contains(DateFormatUtils.format(calendar.getTime(),"yyyy-MM-dd"))){
                    i--;
                }
            }
        }
        return calendar.getTime();
    }
}
