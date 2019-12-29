package com.Calendar;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class calendarUtil01 {
    public static void main(String[] args) {
        //创建一个输入对象
        java.util.Scanner scan = new java.util.Scanner(System.in);
        //以1900-1-1为时间参考点
        String str = "1900-1-1";
        System.out.println("请输入年月(年-月):");
        //输入
        String str1 = scan.next();

        //拼接一个表示日的字符串+"-1"
        String str2 = str1 + "-1";

        //调用此方法，会返回一个总的天数      总天数 = 现在的时间(yyyy-mm-dd) - 参考时间(1900-1-1)
        long days = charge(str, str2);

        //总的天数对7取余+1，就得到每个月的1号是星期几
        long week = days % 7 + 1;

        //截取字符串，会把输入的月份截取出来
        String str4 = str1.substring(5);

        //把截取出来的月份转换成int类型的数据
        int num = Integer.valueOf(str4);

        //截取年份，用来判断是否是闰年
        String str5 = str1.substring(0, 4);

        //把截取的年份转换成int类型的数据
        int num1 = Integer.valueOf(str5);

        //调用此方法会返回你输入的月份的天数
        int dayNum = number(num, num1);

        //用于打印日历表
        print(week, dayNum);
    }

    public static int number(int num, int num1) {
        int day = 0;
        switch (num) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                day = 30;
                break;
            case 2: {
                //判断是否为闰年
                if ((num1 % 4 == 0 && num1 % 100 != 0) || num1 % 400 == 0) {
                    day = 29;
                } else {
                    day = 28;
                }
            }
            break;
        }
        return day;
    }

    //打印日历表
    public static void print(long b, int c) {
        System.out.println("星期一\t" + "星期二\t" + "星期三\t" + "星期四\t" + "星期五\t" + "星期六\t" + "星期日");
        for (int i = 1; i < b; i++) {
            if (i % 7 == 0) {
                System.out.println();
            }
            System.out.print("  *" + "\t\t");
        }
        int d = 0;
        for (int i = (int) b; i < c + (int) b; i++) {
            d++;
            if ((i - 1) % 7 == 0 && i != 1) {
                System.out.println();
            }
            if (d < 10 && d >= 1) {
                System.out.print("  " + ("0" + d) + "\t");
            } else {
                System.out.print("  " + d + "\t");
            }
        }
    }

    //计算两个时间差，返回天数
    public static long charge(String date1, String date2) {
        //做时间差
        //将String转为Date做计算yyyy-mm-dd是格式
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        long charge = 0;
        try {
            Date d1 = sd.parse(date1);
            Date d2 = sd.parse(date2);
            //时间也是以毫秒为单位。
            charge = (d2.getTime() - d1.getTime()) / (1000 * 24 * 60 * 60);
        } catch (Exception e) {
            System.out.println(e);
        }
        return charge;
    }




    /**
     * 根据开始日期 ，需要的工作日天数 ，计算工作截止日期，并返回截止日期
     *
     * @param startDate 开始日期
     * @param workDay   工作日天数(周一到周五)
     * @author 【狒狒：Q9715234】
     * @time 2015-11-23 上午9:21:25
     * @motto 既然笨到家，就要努力到家...
     */
    public static String getWorkDay(Date startDate, int workDay) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(startDate);
        for (int i = 0; i < workDay; i++) {
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) - 1);
            if (Calendar.SATURDAY == c1.get(Calendar.SATURDAY) || Calendar.SUNDAY == c1.get(Calendar.SUNDAY)) {
                workDay = workDay + 1;
                c1.set(Calendar.DATE, c1.get(Calendar.DATE) - 1);
                continue;
            }
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
        // System.out.println(df.format(c1.getTime()) + " " + getWeekOfDate(c1.getTime()));
        return df.format(c1.getTime());
    }

    /**
     * 根据日期，获取星期几
     *
     * @param dt
     * @return String类型
     * @author 【狒狒：Q9715234】
     * @time 2015-11-23 上午9:21:25
     * @motto 既然笨到家，就要努力到家...
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) w = 0;
        return weekDays[w];
    }

    //public static void main(String[] args) {
    @Test
    public void test() {
        // Date date = AppUtil.getCurrentDate2();
        System.out.println("工作日： 8天之前 " + getWorkDay(new Date(), 8));
        System.out.println("工作日： 9天之前 " + getWorkDay(new Date(), 9));
        System.out.println("工作日： 10天之前 " + getWorkDay(new Date(), 10));
        System.out.println("工作日： 11天之前 " + getWorkDay(new Date(), 11));
    }
}
