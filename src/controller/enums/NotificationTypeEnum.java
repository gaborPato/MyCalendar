/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.enums;

/**
 *
 * @author gabor
 */
public enum NotificationTypeEnum {
    REGULAR,ADHOC;
    
    public static String getREGULAR() {
        return REGULAR.toString();
    }

    public static String getADHOC() {
        return ADHOC.toString();
    }
}
