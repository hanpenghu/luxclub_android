package com.hsbank.luxclub_android;

import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.util.java.type.DatetimeUtil;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    private LinkedList<String> linkedList = new LinkedList<>();
    private List<String> list;

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void isNumber() throws Exception {
        assertTrue(NumberUtils.isNumber("1212.33200"));
    }

    @Test
    public void decimal() throws Exception {
        String s = NumberUtils.createBigDecimal("0").toString();
        String s2 = ProjectUtil.getPriceFloatString("0");
        System.out.println("s: " + s);
        System.out.println("s2: " + s2);

    }

    @Test
    public void toArray() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        String[] strings = list.toArray(new String[]{});
        String s = ArrayUtils.toString(strings);
        System.out.println("S: " + s);

    }

    @Test
    public void listRef() throws Exception {
        list = linkedList;
        linkedList.add("aa");
        linkedList.addLast("bb");

        System.out.println("list: " + list.toString());

        list.add("cc");
        System.out.println("list: " + list.toString());
        System.out.println("linkedList: " + linkedList.toString());

    }

    @Test
    public void calendar() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 2 ,24);
        System.out.println("calendar: " + calendar.getTime());
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("week: " + week);
    }

    @Test
    public void week() throws Exception {
        int week = DatetimeUtil.getCurrenDayOfWeek();
        System.out.println("week: " + week);
    }

}