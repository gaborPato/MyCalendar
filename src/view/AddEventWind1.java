/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Exceptions.IllegalCharException;
import controller.Exceptions.TooMatchCharException;
import controller.Exceptions.ZeroCharException;
import modell.EventList;
import controller.check.Datacheck;
import datechooser.beans.DateChooserPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Calendar;
import view.tool.SystemTrayMenu;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import controller.logic.AdHocEvent;
import controller.logic.Event;
import controller.logic.RegularEvent;
import controller.strategy.Minus7DayStrategy;
import controller.strategy.Minus1dayStrategy;
import controller.strategy.NotificationStrategy;

/**
 *
 * @author gabor
 */
public class AddEventWind1 extends JFrame {

    private javax.swing.JButton delEventButton;
    private javax.swing.JButton addButton;
    private javax.swing.JButton closeButton;
    private datechooser.beans.DateChooserPanel dateChooserPanel1;
    private javax.swing.ButtonGroup notifyRBGroup;
    private javax.swing.JLabel eventJLabel;
           
    private javax.swing.JTextField eventTextField;
    private javax.swing.JCheckBox regularCBox;

    private JRadioButton weekRadioB;
    private JRadioButton oneDayRadioB;
    private NotificationStrategy notificationStrategy;

    private int selectedMonth;
    private int selectedDay;
    private int selectedYear;

    private JPanel jpanel;
    private JListEvent EventJlist;

    public AddEventWind1() {
        super("NaptárEsemény");
        
        this.setIconImage(new ImageIcon("classicCalendar.jpeg").getImage());
    
        EventJlist = new JListEvent();
        setLayout(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        this.setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
//---------------------------------------------------------------------------------------------------------------------//
        notificationStrategy = new Minus1dayStrategy();
//----------------------------------------------------------------------------------------------------/        
        notifyRBGroup = new ButtonGroup();
        oneDayRadioB = new JRadioButton("értesítés 1 nappal előtte", true);
        oneDayRadioB.addActionListener(l -> {
            notificationStrategy = new Minus1dayStrategy();

        });
        weekRadioB = new JRadioButton("értesités egy hettel elotte");
        weekRadioB.addActionListener(ii
                -> {
            notificationStrategy = new Minus7DayStrategy();

        });

        getContentPane().add(oneDayRadioB);
        oneDayRadioB.setBounds(10, 290, 250, 23);

        getContentPane().add(weekRadioB);
        weekRadioB.setBounds(10, 330, 250, 23);

        notifyRBGroup.add(weekRadioB);
        notifyRBGroup.add(oneDayRadioB);
//------------------------------------------------------------------------------------------------//
        dateChooserPanel1 = new DateChooserPanel();
        getContentPane().add(dateChooserPanel1);
        dateChooserPanel1.setBounds(10, 11, 448, 180);
//-----------------------------------------------------------------------------------------------//
        eventJLabel = new JLabel();
        eventJLabel.setText("esemény:");
        getContentPane().add(eventJLabel);
        eventJLabel.setBounds(8, 226, 85, 14);
//----------------------------------------------------------------------------------------------//
        eventTextField = new JTextField();
        eventTextField.setText("ide kell beirni az esemenyt");
        getContentPane().add(eventTextField);
        eventTextField.setBounds(84, 223, 354, 20);
//---------------------------------------------------------------------------------------------------/

        regularCBox = new JCheckBox();
        regularCBox.setText("rendszeres esemény");

        getContentPane().add(regularCBox);
        regularCBox.setBounds(10, 261, 195, 23);
//--------------------------------------------------------------------------------------------------//
        addButton = new JButton();
        addButton.setText("add");
        getContentPane().add(addButton);
        addButton.setBounds(10, 370, 70, 23);
        addButton.setBackground(Color.green);
        addButton.addActionListener((ActionEvent selected) -> {
            try {
                Datacheck.checkEventText(eventTextField.getText());
                EventList.addEvent(CreateEvent());
                EventJlist.esList.setListData(EventJlist.createListData());
                jpanel.invalidate();
                jpanel.repaint();

            } catch (TooMatchCharException | ZeroCharException | IllegalCharException | SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, ex);
            }

        });
        //---------------------------------------------------------------------------------------------------//
        closeButton = new JButton();

        closeButton.setText("-");
        closeButton.setToolTipText("A program elrejtése a rendszertálcára");
        getContentPane().add(closeButton);
        closeButton.setBounds(1280, 15, 45, 23);
        closeButton.addActionListener(e
                -> {
            SystemTrayMenu.hideOperation(this);
        });

        //--------------------------------------------------------------------------------------------------//
        delEventButton = new javax.swing.JButton();
        delEventButton.setText("Esemény törlése");

        delEventButton.setBounds(40, 300, 160, 23);
        delEventButton.addActionListener(l -> {
            int index = EventJlist.esList.getSelectedIndex();
            if (index == -1) {
                return;
            }

            try {

                EventList.removeEvent(index);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, ex);
            }
            EventJlist.esList.setListData(EventJlist.createListData());
            jpanel.invalidate();
            jpanel.repaint();

        });

        //-----------------------------------------------------------------------------------------//   
        jpanel = new JPanel();

        jpanel.setBackground(Color.white);
        jpanel.setBounds(550, 11, 700, 450);
        jpanel.setLayout(null);
        jpanel.add(EventJlist);
        jpanel.add(delEventButton);
        add(jpanel);
      

    }

    private Event CreateEvent() {
        Calendar selectedDate = dateChooserPanel1.getSelectedDate();

        selectedYear = selectedDate.get(Calendar.YEAR);
        selectedMonth = selectedDate.get(Calendar.MONTH);
        selectedDay = selectedDate.get(Calendar.DAY_OF_MONTH);
        if (regularCBox.isSelected()) {
            return new RegularEvent(selectedYear, selectedMonth, selectedDay, notificationStrategy, eventTextField.getText());

        }
        return new AdHocEvent(selectedYear, selectedMonth, selectedDay, notificationStrategy, eventTextField.getText());
    }

}
