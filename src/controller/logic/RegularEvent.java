/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.logic;

import controller.enums.NotificationTypeEnum;
import controller.strategy.NotificationStrategy;

/**
 *
 * @author gabor
 */
public class RegularEvent extends Event{
   
    
    private final NotificationTypeEnum    NOTIFYTYPE=NotificationTypeEnum.REGULAR; 

    public NotificationTypeEnum getNOTIFYTYPE() {
        return NOTIFYTYPE;
    }
  public String getEsemenyTipusString (){
    return NOTIFYTYPE.toString();
}  
    public RegularEvent(int year, int hoonap, int nap, NotificationStrategy _strategy,String szoveg) {
        super(year, hoonap, nap, _strategy,szoveg);
        super.year=0;
    }

    public int getYear() {
        return super.year;
    }

    @Override
    public String toString() {
        return super.toString()+"    "+ NOTIFYTYPE.toString();
    }

   
    
   
    
}
