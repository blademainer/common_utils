package com.xiongyingqi.calendar;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by qi<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 14-12-11.
 */
public class CalendarBuilder {
    private Calendar calendar;// 原始日期
    private Calendar calculateCalendar;// 计算结果

    private CalendarBuilder() {
    }

    public static CalendarBuilder newBuilder(Date date) {
        CalendarBuilder calendarBuilder = new CalendarBuilder();
        Calendar instance = Calendar.getInstance();
        Calendar instance2 = Calendar.getInstance();
        instance.setTime(date);
        instance2.setTime(date);
        calendarBuilder.calendar = instance;
        calendarBuilder.calculateCalendar = instance2;

        return calendarBuilder;
    }

    public static CalendarBuilder newBuilder() {
        return newBuilder(new Date());
    }

    public CalendarBuilder time(Date date) {
        calendar.setTime(date);
        calculateCalendar.setTime(date);
        return this;
    }

    /**
     * 增加plusMilliSeconds毫秒
     * @param plusMilliSeconds
     * @return
     */
    public CalendarBuilder nextMilliSeconds(int plusMilliSeconds) {
        calculateCalendar.add(Calendar.MILLISECOND, plusMilliSeconds);
        return this;
    }

    /**
     * 增加plusSeconds秒
     * @param plusSeconds
     * @return
     */
    public CalendarBuilder nextSeconds(int plusSeconds) {
        calculateCalendar.add(Calendar.SECOND, plusSeconds);
        return this;
    }

    /**
     * 增加plusMinutes分钟
     * @param plusMinutes
     * @return
     */
    public CalendarBuilder nextMinutes(int plusMinutes) {
        calculateCalendar.add(Calendar.MINUTE, plusMinutes);
        return this;
    }

    /**
     * 增加plusHours小时
     * @param plusHours
     * @return
     */
    public CalendarBuilder nextHours(int plusHours) {
        calculateCalendar.add(Calendar.HOUR, plusHours);
        return this;
    }

    /**
     * 增加plusDays天
     * @param plusDays
     * @return
     */
    public CalendarBuilder nextDays(int plusDays) {
        calculateCalendar.add(Calendar.DAY_OF_YEAR, plusDays);
        return this;
    }

    /**
     * 增加plusWeeks星期
     * @param plusWeeks
     * @return
     */
    public CalendarBuilder nextWeeks(int plusWeeks) {
        calculateCalendar.add(Calendar.WEEK_OF_YEAR, plusWeeks);
        return this;
    }

    /**
     * 增加plusMonths月
     * @param plusMonths
     * @return
     */
    public CalendarBuilder nextMonths(int plusMonths) {
        calculateCalendar.add(Calendar.MONTH, plusMonths);
        return this;
    }

    /**
     * 增加plusYears年
     * @param plusYears
     * @return
     */
    public CalendarBuilder nextYears(int plusYears) {
        calculateCalendar.add(Calendar.YEAR, plusYears);
        return this;
    }

    /**
     * 增加指定字段的数量
     * @param field 字段{@link java.util.Calendar#set(int, int)}
     * @param plus
     * @return
     */
    public CalendarBuilder next(int field, int plus) {
        calculateCalendar.set(field, calculateCalendar.get(field) + plus);
        return this;
    }


    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Calendar getCalculateCalendar() {
        return calculateCalendar;
    }

    public void setCalculateCalendar(Calendar calculateCalendar) {
        this.calculateCalendar = calculateCalendar;
    }

    /**
     * 获取计算结果
     *
     * @return
     */
    public Date getResultTime() {
        return calculateCalendar.getTime();
    }

    /**
     * 获取原始时间
     *
     * @return
     */
    public Date getTime() {
        return calendar.getTime();
    }
}
