package com.xiongyingqi.util;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class DateHelperTest {

    @org.junit.Test
    public void testDiffDay() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
        org.junit.Assert.assertEquals(DateHelper.diffOfDay(calendar.getTime(), new Date()), 1);
    }

    @Test
    public void testGetNowDate() throws Exception {

    }

    @Test
    public void testPhpDateToJavaDate() throws Exception {

    }

    @Test
    public void testGetNowDateShort() throws Exception {

    }

    @Test
    public void testGetStringDate() throws Exception {

    }

    @Test
    public void testGetStringDateShort() throws Exception {

    }

    @Test
    public void testGetTimeShort() throws Exception {

    }

    @Test
    public void testStrToDateLong() throws Exception {

    }

    @Test
    public void testStrToDate() throws Exception {

    }

    @Test
    public void testStrToDate1() throws Exception {

    }

    @Test
    public void testDateToStrLong() throws Exception {

    }

    @Test
    public void testDateToStr() throws Exception {

    }

    @Test
    public void testStrToDate2() throws Exception {

    }

    @Test
    public void testGetNow() throws Exception {

    }

    @Test
    public void testGetLastDate() throws Exception {

    }

    @Test
    public void testGetStringToday() throws Exception {

    }

    @Test
    public void testGetHour() throws Exception {

    }

    @Test
    public void testGetTime() throws Exception {

    }

    @Test
    public void testGetUserDate() throws Exception {

    }

    @Test
    public void testGetTwoHour() throws Exception {

    }

    @Test
    public void testGetTwoDay() throws Exception {

    }

    @Test
    public void testGetPreTime() throws Exception {

    }

    @Test
    public void testGetNextDay() throws Exception {

    }

    @Test
    public void testGetNextDay1() throws Exception {

    }

    @Test
    public void testIsLeapYear() throws Exception {

    }

    @Test
    public void testGetEDate() throws Exception {

    }

    @Test
    public void testGetEndDateOfMonth() throws Exception {

    }

    @Test
    public void testIsSameWeekDates() throws Exception {

    }

    @Test
    public void testGetSeqWeek() throws Exception {

    }

    @Test
    public void testGetWeek() throws Exception {

    }

    @Test
    public void testGetWeek1() throws Exception {

    }

    @Test
    public void testGetWeekStr() throws Exception {

    }

    @Test
    public void testGetDays() throws Exception {

    }

    @Test
    public void testGetNowMonth() throws Exception {

    }

    @Test
    public void testGetNo() throws Exception {

    }

    @Test
    public void testGetRandom() throws Exception {

    }

    @Test
    public void testRightDate() throws Exception {

    }

    @Test
    public void testGetDay() throws Exception {

    }

    @Test
    public void testMain() throws Exception {

    }
}