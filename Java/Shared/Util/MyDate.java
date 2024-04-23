package Shared.Util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MyDate {
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
    public void set(int month, int day, int year){
        //Year handler
        if (year<0){
            this.year=year*-1;
        }
        else{
            this.year = year;
        }

        //Month Handler
        if (month<1){
            this.month = 1;
        }
        else if(month>12){
            this.month=12;
        }
        else{
            this.month=month;
        }

        //Day Handler
        if (day<1){
            this.day = 1;
        }
        else if(day>numberOfDaysInMonth()){
            this.day=numberOfDaysInMonth();
        }
        else{
            this.day=day;
        }
    }

    public boolean equals(Object obj){
        if(!(obj instanceof MyDate)){
            return false;
        }
        if (this.toString().equals(((MyDate)obj).toString()))
        {
            return true;
        }
        else {return false;}
    }
    public String toString(){
        String result="";

        //if days is single digit
        if(day<10){
            result += "0"+Integer.toString(day) + "/";
        }
        else {
            result += Integer.toString(day) + "/";
        }

        //if month is single digit
        if(month<10){
            result += "0"+Integer.toString(month) + "/";
        }
        else {
            result += Integer.toString(month) + "/";
        }

        if(year<10){
            result += "000"+Integer.toString(year);
        }
        else if (year < 100) {
            result += "00" + Integer.toString(year);
        }
        else if (year < 1000) {
            result += "0" + Integer.toString(year);
        }
        else {
            result += Integer.toString(year);
        }
        return result;
    }

    public String getMonthName(){
        return switch (month) {
            case 1 -> "January";
            case 2 -> "February";
            case 3 -> "March";
            case 4 -> "April";
            case 5 -> "May";
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> "None";
        };
    }

    public int getMonthName(String month){
        return switch (month.toLowerCase()) {
            case "january" ->1;
            case "february" ->2;
            case "march" ->3;
            case "april" ->4;
            case "may" ->5;
            case "june"->6;
            case "july"->7;
            case "august"->8;
            case "september"->9;
            case "october"->10;
            case "november"->11;
            case "december"-> 12;
            default -> 0;
        };
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
        day--;
        if(day<1){
            month--;
            if(month<1){
                year--;
                month=12;
            }
            day=numberOfDaysInMonth();
        }
    }
    public void stepBackward(int days){
        int i=0;
        while(i<days){
            i++;
            this.stepBackwardOneDay();
        }
    }
    public void stepForwardOneDay(){
        day++;
        if(day>numberOfDaysInMonth()){
            day=day%numberOfDaysInMonth();
            month++;
            if(month>12){
                month=month%12;
                year++;
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
        if(this.year<other.year){
            return true;
        }
        else if (this.year==other.year) {
            if(this.month<other.month){
                return true;
            }
            else if(this.month==other.month){
                if(this.day<other.day){
                    return true;
                }
            }
        }
        return false;
    }

    public int yearsBetween(MyDate other){
        int tempdelta=0;
        int tempYear=0;

        tempdelta = other.year - this.year;
        if(tempdelta==0){
            return 0;
        }
        else if(tempdelta>0) {
            tempYear=this.year;
            this.year = other.year;
            if(this.isBefore(other)||this.equals(other)){
                this.year=tempYear;
                return tempdelta;
            }
            else{
                this.year=tempYear;
                return (tempdelta-1);
            }
        }
        else{
            tempYear=this.year;
            this.year = other.year;
            if(other.isBefore(this)||this.equals(other)){
                this.year=tempYear;
                return tempdelta*-1;
            }
            else{
                this.year=tempYear;
                return (tempdelta*-1)-1;
            }
        }
    }


    public MyDate copy(){
        return new MyDate(this.month, this.day,this.year);
    }

    public int daysBetween(MyDate other){
        MyDate counterDate;
        int i=0;
        if(this.isBefore(other)){
            counterDate = this.copy();
            while(counterDate.isBefore(other)){
                i++;
                counterDate.stepForwardOneDay();
            }
        }
        else{
            counterDate = other.copy();
            while(counterDate.isBefore(other)){
                i++;
                counterDate.stepForwardOneDay();
            }
        }
        return i;
    }

    public static MyDate now(){
        return new MyDate();
    }

}
