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
public class AdHocEvent extends Event {
    private final NotificationTypeEnum NOTYTYPE=NotificationTypeEnum.ADHOC;

    public NotificationTypeEnum getNOTYTYPE() {
        return NOTYTYPE;
    }
public String getNotifyType(){
    return NOTYTYPE.toString();
}
   
    
    public AdHocEvent(int year, int month, int day, NotificationStrategy _strategy,String text) {
        super(year, month, day, _strategy,text);
    }

    @Override
    public String toString() {
        return super.toString()+"    "+NOTYTYPE.toString();
    }
    
    
    
}
