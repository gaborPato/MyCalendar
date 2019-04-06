/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.strategy;

/**
 *
 * @author gabor
 */
public interface NotificationStrategy {
    public int notifyTime(int dateCode,int year,int month,int day);
    public String getNotifyType();
}
