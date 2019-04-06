/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modell;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import controller.logic.Event;

/**
 *
 * @author gabor
 */
public class EventList {
    private static DerbyDB derbyDB;

    private static List<Event> notificationList;

    private static void eventListOrder() {
        notificationList.sort(Event.yearCodeComparator);
        notificationList.sort(Event.dateCodeComparator);
    }

    private EventList() {
    }

    static {
        notificationList = new ArrayList<>();
        try {
            derbyDB=DerbyDB.getInstance();
        } catch (SQLException ex) {
            try {
                throw new SQLException();
            } catch (SQLException ex1) {
                Logger.getLogger(EventList.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    public static void dbToEsenenyekList() throws SQLException {
        notificationList = derbyDB.dbToEventList();
        eventListOrder();
        
      

    }

    public static void addEvent(Event ev) throws SQLException {

        if (derbyDB.saveToDataBase(ev)) {
            notificationList.add(ev);
            eventListOrder();

        }

    }

    public static Event[] getEventArray() {
        Event[] est = new Event[notificationList.size()];
        for (int i = 0; i < notificationList.size(); i++) {
            est[i] = notificationList.get(i);

        }
        return est;
    }

    public static void removeEvent(int index) throws SQLException {
        String torlendoText = notificationList.get(index).getNotiText();
        if (derbyDB.deleteEventFromDatabase(torlendoText)) {
            notificationList.remove(index);
            eventListOrder();
        }


    }

    public static String[] getReportedEvents() throws SQLException {

   
        List<Event> reportedEvevtList = new ArrayList<>();
      
        int actualYear = Calendar.getInstance().get(Calendar.YEAR);
       
       int actualDateCode = (Calendar.getInstance().get(Calendar.MONTH)) * 100 + Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
      
       System.out.println(actualDateCode);
        for (int i = 0; i < notificationList.size(); i++) {


                
             if(notificationList.get(i).getDateCode()!=notificationList.get(i).getNotificationCode() && notificationList.get(i).getDateCode()>100)/* nem januar date*/{
                if(( notificationList.get(i).getYear()==0 || notificationList.get(i).getYear()==actualYear)&& notificationList.get(i).getNotificationCode()<=actualDateCode  ){
                    reportedEvevtList.add(notificationList.get(i));
                }
                
            }else if(notificationList.get(i).getDateCode()!=notificationList.get(i).getNotificationCode() && notificationList.get(i).getDateCode()<100)/* januar date*/{
              
                if(( notificationList.get(i).getYear()==0 || notificationList.get(i).getYear()==actualYear+1) && notificationList.get(i).getNotificationCode()<=actualDateCode){
                    reportedEvevtList.add(notificationList.get(i));
                }
            }
            
        }
        String[] result = new String[reportedEvevtList.size()];
        for (int i = 0; i < reportedEvevtList.size(); i++) {
          
         
            result[i] = reportedEvevtList.get(i).toSimpleString();

        }
        if (result.length == 0) {
            return new String[]{"nincs közelgö esemény"};
        }
        return result;
    }

    public static String getUpComingForStartJOP(String[] events) {
        String result = "";
        for (String string : events) {

            result += string + System.lineSeparator();

        }
        return result;
    }

    public static int eventskListSize() {
        return notificationList.size();
    }
}
