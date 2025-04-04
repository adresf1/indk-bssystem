package Shared.Util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MyDate implements Serializable {
    int month;
    int day;
    int year;

    public MyDate(int month, int day, int year){
        this.set(month, day, year);
    }


    public MyDate(){
        Calendar now = GregorianCalendar.getInstance();

        set(now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH) + 1,now.get(Calendar.YEAR));

    }


    public void set(int month, int day, int year) {
        // Normalize negative year values
        this.year = Math.abs(year);

        // Normalize the month within [1, 12]
        if (month < 1) {
            this.month = 1;
        } else if (month > 12) {
            this.month = 12;
        } else {
            this.month = month;
        }

        // Normalize the day to fit within the number of days in the given month
        int maxDays = this.numberOfDaysInMonth();
        if (day < 1) {
            this.day = 1;
        } else if (day > maxDays) {
            this.day = maxDays;
        } else {
            this.day = day;
        }
    }

    public boolean equals(Object obj){
        if(!(obj instanceof MyDate)){
            return false;
        }
        MyDate other = (MyDate) obj;
        return this.day == other.day && this.month == other.month && this.year == other.year;
    }
    public String toString(){
        String result="";

        //if days is single digit
        result += String.format("%02d/", this.day);
        // Month as two digits
        result += String.format("%02d/", this.month);
        // Year as four digits
        result += String.format("%04d", this.year);
        return result;
    }
    public static MyDate fromString(String date){
        MyDate convertedDate = new MyDate();

        if(date.length()<=10){
            String[] strArr = date.split("/");
            if (strArr.length !=3 ){
               throw new IllegalArgumentException("Wrong format for date, correct format for date is DD/MM/YYYY");
            }
            convertedDate.set(
                    Integer.parseInt(strArr[0]),
                    Integer.parseInt(strArr[1]),
                    Integer.parseInt(strArr[2])
            );
            return convertedDate;
        }

        return convertedDate;
    }


    public boolean isLeapYear(){
        if((year%4==0)&&((year%100!=0)||(year%400==0))){
            return true;
        } else{
            return false;
        }
    }
    public int numberOfDaysInMonth(){
        switch (month){
            case 1, 3, 5, 7, 8, 10, 12:
                return 31;
            case 4, 6, 9, 11:
                return 30;
            case 2:
                if(isLeapYear()){
                    return 29;
                }
                else {
                    return 28;
                }
            default:
                return 0;
        }
    }

    public void stepBackwardOneDay(){
        this.day--;
        if (this.day < 1) {
            this.month--;
            if (this.month < 1) {
                this.month = 12;
                this.year--;
            }
            this.day = this.numberOfDaysInMonth();
        }
    }
    public void stepForwardOneDay(){
        this.day++;
        if (this.day > this.numberOfDaysInMonth()) {
            this.day = this.day % this.numberOfDaysInMonth();
            this.month++;
            if (this.month > 12) {
                this.month = this.month % 12;
                this.year++;
            }
        }
    }

    public void stepForward(int days){
        int i=0;
        while(i<days){
            i++;
            this.stepForwardOneDay();
        }
    }

    public boolean isBefore(MyDate other){
        if(other != null){
            if (this.year < other.year) {
                return true;
            } else if (this.year > other.year) {
                return false;
            }

            // If years are equal, compare months
            if (this.month < other.month) {
                return true;
            } else if (this.month > other.month) {
                return false;
            }

            // If months are also equal, compare days
            return this.day < other.day;
        }
        return false;
    }

    public MyDate copy(){
        return new MyDate(this.month, this.day,this.year);
    }

    public int daysBetween(MyDate other) {
        MyDate start;
        MyDate end;

        if (this.isBefore(other)) {
            start = this.copy();
            end = other;
        } else {
            start = other.copy();
            end = this;
        }

        int daysCount = 0;

        // Increment the start date one day at a time until it matches the end date
        while (!start.equals(end)) {
            start.stepForwardOneDay();
            daysCount++;
        }

        return daysCount;
    }

    public static MyDate today() {
        Calendar now = GregorianCalendar.getInstance();
        int day = now.get(Calendar.DAY_OF_MONTH);
        int month = now.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
        int year = now.get(Calendar.YEAR);

        return new MyDate(month, day, year);
    }

    public int compareTo(MyDate other) {
        if (this.year != other.year) {
            return this.year - other.year; // Returns a positive or negative number depending on the difference in years
        }

        if (this.month != other.month) {
            return this.month - other.month; // Same logic for months
        }

        return this.day - other.day; // Returns a positive or negative number for the day comparison

    }
}
