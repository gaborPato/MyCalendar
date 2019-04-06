/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.strategy;

import controller.enums.NotifyTypeStrategyEnum;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author gabor
 */
public class Minus7DayStrategy implements NotificationStrategy {
  public  final NotifyTypeStrategyEnum NOTIFYTYPE=NotifyTypeStrategyEnum.MinusSevenDay;

  @Override
    public String getNotifyType() {
        return NOTIFYTYPE.toString();
    }
  
    @Override
    public int notifyTime(int dateCode, int yeear, int month, int day) {
        if (day > 7) {
            return dateCode - 7;
        }
        if (day <= 7) {
            int hoonapGeneralt = month - 1;
            if (hoonapGeneralt < 0) {
                hoonapGeneralt = 11;
            }
           
           
            Calendar cal = new GregorianCalendar(yeear, hoonapGeneralt+1, 0);
            Date date = cal.getTime();
            DateFormat sdf = new SimpleDateFormat("dd");
            int maxDayofMounth=Integer.valueOf(sdf.format(date));
            
       return Integer.valueOf(Integer.toString(hoonapGeneralt)+Integer.toString(maxDayofMounth - (7 - day)));

        }
        return -1;
    }

}
