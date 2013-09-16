package com.example.canteenapp.model;

public class LunchMenuItem {
    private String weekDayName, courseName;

    public LunchMenuItem()
    {
    }

    public LunchMenuItem(String weekDayName, String courseName)
    {
        this.weekDayName = weekDayName;
        this.courseName = courseName;
    }

    public void setWeekDayName(String name)
    {
        weekDayName = name;
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
