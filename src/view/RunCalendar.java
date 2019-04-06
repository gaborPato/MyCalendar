/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import view.tool.SystemTrayMenu;
import modell.DerbyDB;
import modell.EventList;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author gabor
 */
public class RunCalendar {

    private AddEventWind1 addew1;
    private String message;
    private  DerbyDB derbyDB;
    

    public RunCalendar()  {
      
        try {  derbyDB=DerbyDB.getInstance();
        dtBaseOperations();
        addew1 = new AddEventWind1();
            message = EventList.getUpComingForStartJOP(EventList.getReportedEvents())
                    + System.lineSeparator()
                    + System.lineSeparator()
                    + "Teljes Méretben indítsuk a programot?";
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        int showConfirmDialog = JOptionPane.showConfirmDialog(null, message, "Közelgö események", JOptionPane.YES_NO_CANCEL_OPTION);

        switch (showConfirmDialog) {
            case 0:
                addew1.setVisible(true);
                break;
            case 1:
                SystemTrayMenu.hideOperation(addew1);
                break;
            default:
                System.exit(0);
        }

    }

    private void dtBaseOperations() {

        try {
            int actualYear = Calendar.getInstance().get(Calendar.YEAR);
            int actualDateCode = (Calendar.getInstance().get(Calendar.MONTH)) * 100 + Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            derbyDB.autoRemoveExperiedEvent(actualYear, actualDateCode);
            EventList.dbToEsenenyekList();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

}
