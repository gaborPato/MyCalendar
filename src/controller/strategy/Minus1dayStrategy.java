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
public class Minus1dayStrategy implements NotificationStrategy{
    public static  final NotifyTypeStrategyEnum NOTIFYTYPE=NotifyTypeStrategyEnum.Minus1Day;

    @Override
    public String  getNotifyType() {
        return NOTIFYTYPE.toString();
    }
    public static String getNotifyStrategy(){
        return NOTIFYTYPE.toString();
    }

    @Override
    public int notifyTime(int datumkod,int year,int honap ,int nap) {
       if (nap > 1) {
            return datumkod - 1;
        }
        if (nap <= 7) {
            int hoonapGeneralt = honap - 1;
            if (hoonapGeneralt < 0) {
                hoonapGeneralt = 11;
            }
           
           
            Calendar cal = new GregorianCalendar(year, hoonapGeneralt+1, 0);
            Date date = cal.getTime();
            DateFormat sdf = new SimpleDateFormat("dd");
            int maxDayofMounth=Integer.valueOf(sdf.format(date));
            
       return Integer.valueOf(Integer.toString(hoonapGeneralt)+Integer.toString(maxDayofMounth - (1 - nap)));

        }
        return -1;
    }
    
}
