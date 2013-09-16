package com.example.canteenapp.model;

public class LunchMenuItem {
    private String weekDayName, courseName;

    public LunchMenuItem()
    {
    }

    public LunchMenuItem(String weekDayName, String courseName)
    {
        this.weekDayName = getWeekdayWithCapitalLetter(weekDayName);
        this.courseName = courseName;
    }

    public static String getWeekdayWithCapitalLetter(String weekDayName) {
        return weekDayName.substring(0, 1).toUpperCase() + weekDayName.substring(1);
    }

    public void setWeekDayName(String name)
    {
        weekDayName = getWeekdayWithCapitalLetter(name);
    }

    public String getWeekDayName()
    {
        return weekDayName;
    }

    public void setCourseName(String name)
    {
        courseName = name;
    }

    public String getCourseName()
    {
        return courseName;
    }
}