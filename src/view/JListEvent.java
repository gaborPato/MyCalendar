/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import modell.EventList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import controller.logic.Event;

/**
 *
 * @author gabor
 */
public class JListEvent extends JScrollPane{
    
     
  
    JList<String> esList;
    ListModel<String> listModel;

  
   

    public JListEvent() {
        super();
        listModel=new DefaultListModel<>();
      esList=new JList<>(listModel);
       
        
      esList.  setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      esList.  setListData(createListData());
        
        setViewportView(esList);

        
        setBounds(30, 10, 580, 270);

        
    }                   

  String[] createListData() {
         
       
         Event[] esemenyTomb = EventList.getEventArray();
         String[] result=new String[esemenyTomb.length];
         int s=0;
         for (Event es:esemenyTomb) {
           
             result[s++]=es.toString();  
         }
         
         return result;
    }


    }
    

