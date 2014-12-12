package com.xiongyingqi.calendar;

import com.xiongyingqi.util.Assert;
import com.xiongyingqi.util.CalendarHelper;
import com.xiongyingqi.util.DateHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class CalendarBuilderTest {
    private CalendarBuilder calendarBuilder;

    @Before
    public void setUp() throws Exception {
        String date = "2000-12-31";
        calendarBuilder = CalendarBuilder.newBuilder(DateHelper.strToDate(date));
    }


    @Test
    public void testNextMilliSeconds() throws Exception {
        calendarBuilder.nextMilliSeconds(1);
        Date resultTime = calendarBuilder.getResultTime();
        Date time = calendarBuilder.getTime();
        Assert.isTrue(resultTime.getTime() - time.getTime() == 1);
        System.out.println(time);
        System.out.println(resultTime);
    }

    @Test
    public void testNextSeconds() throws Exception {
        calendarBuilder.nextSeconds(1);
        Date resultTime = calendarBuilder.getResultTime();
        Date time = calendarBuilder.getTime();
        Assert.isTrue(resultTime.getTime() - time.getTime() == CalendarHelper.SECOND);
        System.out.println(time);
        System.out.println(resultTime);
    }

    @Test
    public void testNextMinutes() throws Exception {
        calendarBuilder.nextMinutes(1);
        Date resultTime = calendarBuilder.getResultTime();
        Date time = calendarBuilder.getTime();
        Assert.isTrue(resultTime.getTime() - time.getTime() == CalendarHelper.MINUTE);
        System.out.println(time);
        System.out.println(resultTime);
    }

    @Test
    public void testNextHours() throws Exception {
        calendarBuilder.nextHours(1);
        Date resultTime = calendarBuilder.getResultTime();
        Date time = calendarBuilder.getTime();
        Assert.isTrue(resultTime.getTime() - time.getTime() == CalendarHelper.HOUR);
        System.out.println(time);
        System.out.println(resultTime);
    }

    @Test
    public void testNextDays() throws Exception {
        calendarBuilder.nextDays(1);
        Date resultTime = calendarBuilder.getResultTime();
        Date time = calendarBuilder.getTime();
        Assert.isTrue(resultTime.getTime() - time.getTime() == CalendarHelper.DAY);
        System.out.println(time);
        System.out.println(resultTime);
    }

    @Test
    public void testNextWeeks() throws Exception {
        calendarBuilder.nextWeeks(1);
        Date resultTime = calendarBuilder.getResultTime();
        Date time = calendarBuilder.getTime();
        System.out.println(time);
        System.out.println(resultTime);
        Assert.isTrue(resultTime.getTime() - time.getTime() == CalendarHelper.DAY * 7);
    }

    @Test
    public void testNextMonths() throws Exception {
        calendarBuilder.nextMonths(1);
        Date resultTime = calendarBuilder.getResultTime();
        Date time = calendarBuilder.getTime();
        System.out.println(time);
        System.out.println(resultTime);
        Assert.isTrue(resultTime.getTime() - time.getTime() == CalendarHelper.DAY * 31);
    }

    @Test
    public void testNextYears() throws Exception {
        calendarBuilder.nextYears(1);
        Date resultTime = calendarBuilder.getResultTime();
        Date time = calendarBuilder.getTime();
        Assert.isTrue(resultTime.getTime() - time.getTime() == 365 * CalendarHelper.DAY);
        System.out.println(time);
        System.out.println(resultTime);
    }
}