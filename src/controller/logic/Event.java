/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.logic;

import java.util.Comparator;
import controller.strategy.NotificationStrategy;

/**
 *
 * @author gabor
 */
public abstract class Event {
    
    protected String month;
    protected int day;
    protected int year;
    protected int NotificationCode;
    protected int dateCode;
    protected String notiText;
    private NotificationStrategy notifyTypeStrategy;
    public static Comparator<Event> dateCodeComparator;
    public static Comparator<Event> yearCodeComparator;
    static {
        dateCodeComparator=((Event e1, Event e2) -> {
            int dk1=e1.dateCode;
            int dk2=e2.dateCode;
            return Integer.compare(dk1, dk2);
        });
        yearCodeComparator=(Event o1, Event o2) -> {
            int year1 = o1.year;
            int year2 = o2.year;
            return Integer.compare(year1, year2);
        };
    }

    public NotificationStrategy getNotifyTypeStrategy() {
        return notifyTypeStrategy;
    }

    
  
    

    public String getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    public int getNotificationCode() {
        return NotificationCode;
    }

    public int getDateCode() {
        return dateCode;
    }

    public String getNotiText() {
        return notiText;
    }

    

    protected Event(int year,int month, int day,NotificationStrategy _strategy,String notifyText) {
  
   notifyTypeStrategy=_strategy;
        notiText=notifyText;
        int tmpMonth;
        tmpMonth=day<10 ? month*10 : month;
         dateCode=Integer.valueOf(Integer.toString(tmpMonth)+Integer.toString(day));
       NotificationCode=notifyTypeStrategy.notifyTime(dateCode, year, month, day);
        switch (month) {
            case 0:
                this.month="januar";
                  break;
            case 1:
                this.month="februar";
                  break;
            case 2:
                this.month="marcius";
                  break;
            case 3:
                this.month="aprilis";
                  break;
            case 4:
                this.month="majus";
                
                  break;
            case 5:
                this.month="junius";
                  break;
            case 6:
                this.month="julius";
                  break;
            case 7:
                this.month="augusztus";
                  break;
            case 8:
                this.month="szeptember";
                  break;
            case 9:
                this.month="oktober";
                  break;
            case 10:
                this.month="november";
                  break;
            default:
                this.month="december";
        }
        this.day = day;
        this.year=year;
    }

    @Override
    public String toString() {
        return ""+year+" "+month+" "+day+"   "+notiText+"  ||   tipus:"+notifyTypeStrategy.getNotifyType();
    }

    public String toSimpleString(){
    return ""+year+" "+month+" "+day+"   "+notiText ;
    }
  

  
    
    
}
