package com.Calendar;

import com.util.RegexUtil;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CalendarUtil02 {
    // 交易日map，false是将国家节假日调成交易日
    private static final Map<Integer, Boolean> WORKDAY_MAP = new HashMap<>();

    /**
     * 初始化交易日
     * 日期格式必须为yyyyMMdd，false为国家节假日非交易日调整，如果本来就是周末的节假日则不需再设置
     */
    private static void initWorkday() {
        // ---------------2019------------------
        WORKDAY_MAP.put(20190101, false);
        WORKDAY_MAP.put(20190204, false);
        WORKDAY_MAP.put(20190205, false);
        WORKDAY_MAP.put(20190206, false);
        WORKDAY_MAP.put(20190207, false);
        WORKDAY_MAP.put(20190208, false);
        WORKDAY_MAP.put(20190405, false);
        WORKDAY_MAP.put(20190501, false);
        WORKDAY_MAP.put(20190502, false);
        WORKDAY_MAP.put(20190503, false);
        WORKDAY_MAP.put(20190607, false);
        WORKDAY_MAP.put(20190913, false);
        WORKDAY_MAP.put(20191001, false);
        WORKDAY_MAP.put(20191002, false);
        WORKDAY_MAP.put(20191007, false);
        // ------------------2020----------------
        WORKDAY_MAP.put(20200101, false);
        WORKDAY_MAP.put(20200124, false);
        WORKDAY_MAP.put(20200127, false);
        WORKDAY_MAP.put(20200128, false);
        WORKDAY_MAP.put(20200129, false);
        WORKDAY_MAP.put(20200130, false);
        WORKDAY_MAP.put(20200406, false);
        WORKDAY_MAP.put(20200501, false);
        WORKDAY_MAP.put(20200504, false);
        WORKDAY_MAP.put(20200505, false);
        WORKDAY_MAP.put(20200625, false);
        WORKDAY_MAP.put(20200626, false);
        WORKDAY_MAP.put(20201001, false);
        WORKDAY_MAP.put(20201002, false);
        WORKDAY_MAP.put(20201005, false);
        WORKDAY_MAP.put(20201006, false);
        WORKDAY_MAP.put(20201007, false);
        WORKDAY_MAP.put(20201008, false);
        // ------------------2021----------------
        WORKDAY_MAP.put(20210101, false);
    }

    /**
     * 去掉周末和国家节假日等非交易日期
     *
     * @param calendar
     * @param days
     * @return
     */
    public Date getTradeDate(Calendar calendar, int days) {
        int dateStamp = calendar.get(Calendar.YEAR) * 10000 + (calendar.get(Calendar.MONTH) + 1) * 100 + calendar.get(Calendar.DAY_OF_MONTH);
        if (days > 0) {
            for (int i = 1; i <= days; i++) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                        calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                        (WORKDAY_MAP.get(dateStamp) != null && (!WORKDAY_MAP.get(dateStamp)))) {
                    i--;
                }
            }
        } else if (days <= 0) {
            for (int i = 0; i >= days; i--) {
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                        calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                        (WORKDAY_MAP.get(dateStamp) != null && (!WORKDAY_MAP.get(dateStamp)))) {
                    i++;
                }
            }
        }
        return calendar.getTime();
    }

    public Date getStartDate(int startDay, Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //if (date.endsWith("d")) {
        calendar.setTime(new Date());//初始化日历为当前日期
        // 时
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        // 分
        calendar.set(Calendar.MINUTE, 0);
        // 秒
        calendar.set(Calendar.SECOND, 0);
        // 毫秒
        calendar.set(Calendar.MILLISECOND, 0);

        Date tradeStartDate = getTradeDate(calendar, -(startDay - 1));//上面初始化日期，前多少个交易日//起始日期
        System.out.println("startDate:" + dateFormat.format(tradeStartDate));
        return tradeStartDate;
        //}

        /*if (date.endsWith("M")) {
            calendar.add(Calendar.DATE, 0);//从第0天算起
            Date tradeDate = getTradeDate(calendar, -days);//初始化日历为当前日期，前多少个交易日
            System.out.println(dateFormat.format(tradeDate));
        }*/
    }

    public Date getEndDate(int endDay, Calendar endCalendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date tradeEndDate = getTradeDate(endCalendar, endDay);//上面初始化日期，前多少个交易日//起始日期
        System.out.println("endDate:" + dateFormat.format(tradeEndDate));
        return tradeEndDate;
    }

    /**
     * 边界日期处理策略<br/>
     */

    /*// 起始日期处理策略:开始第一天处理
    private static final BoundaryDateHandlingStrategy START_DATE_HANDLING_STRATEGY = date -> {
        return true;// 开始时间无论几点，都算一天，从当天日期计算起
    };*/

    // 结束日期处理策略：最后日期处理
    private int ifCountAsOneDay(Date endDate) {
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);

        // 如果截止时间在上午9点前，则截止日期往前推一天，
        if (endCalendar.get(Calendar.HOUR_OF_DAY) < 9) {
            return -1;//小于9点钟，交易日以昨天为截止日期
        }
        return 0;//大于9点交易日今天截止日期
    }

    public Date getWeekDate(int week, int day,Calendar calendar) {
        //最近一周
        calendar.setTime(new Date());
        calendar.add(Calendar.WEEK_OF_YEAR, -week);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date weekTradeStartDate = getTradeDate(calendar, day);
        System.out.println(dateFormat.format(weekTradeStartDate));
        return weekTradeStartDate;

    }

    @Test
    public void test() {
        String date = "date:5W";
        Integer days = Integer.parseInt(new RegexUtil().getRegexSub(date, "\\d{1,2}"));//解析前n个交易日
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //获取前多少天的交易日
        if (date.endsWith("d")) {
            int endDays = ifCountAsOneDay(calendar.getTime());
            int startDays = days - endDays;

            Date endDate = getEndDate(endDays, calendar);
            Date startDate = getStartDate(startDays, calendar);
            System.out.println("----------");
            System.out.println("endDays：" + endDate);
            System.out.println("startDays：" + startDate);
        }

        //获取前多少周的交易日
        if (date.endsWith("W")) {
            int startDay =1;
            int endDay = 5*days;
            Date weekStartDate = getWeekDate(days, startDay,calendar);
            Date weekEndDate = getWeekDate(days, endDay, calendar);
        }

        //获取前第几周的交易日
        if (date.endsWith("SW")) {
            int startDay =1;
            int endDay = 5;
            Date weekStartDate = getWeekDate(days, startDay,calendar);
            Date weekEndDate = getWeekDate(days, endDay, calendar);
        }
    }


}
