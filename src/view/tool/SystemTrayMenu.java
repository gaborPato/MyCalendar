/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.tool;

import view.AddEventWind1;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author gabor
 */
public class SystemTrayMenu {
private static boolean strayIs;

    private SystemTrayMenu() {
    }
    static {
        strayIs=false;
       
    }
     public static void hideOperation(AddEventWind1 addew1){
         if(!SystemTray.isSupported()){
             JOptionPane.showMessageDialog(null,"System tray is not supported");
             addew1.setVisible(true);
         }else{
             addew1.setVisible(false);
             if(strayIs) return;
             
             strayIs=true;
             SystemTray systemTray = SystemTray.getSystemTray();
             Image image = new ImageIcon("Calendar.jpeg").getImage();
             PopupMenu popupMenu = new PopupMenu();
             MenuItem viewItem = new MenuItem("naptar program megjelenites");
             viewItem.addActionListener(al -> {
                 addew1.setVisible(true);
          
                 
                 
             });
              MenuItem closeItem = new MenuItem("Close");
              closeItem.addActionListener(al ->{
                  System.exit(0);
              });
              popupMenu.add(viewItem);
              popupMenu.add(closeItem);
             TrayIcon trayIcon = new TrayIcon(image, "Naptár megjelenítés", popupMenu);
             trayIcon.setImageAutoSize(true);
             try {
                 systemTray.add(trayIcon);
             } catch (AWTException ex) {
                addew1.setVisible(true);
             }
         }
         
     }
    
}
