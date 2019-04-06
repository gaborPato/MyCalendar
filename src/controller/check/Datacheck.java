/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.check;

import controller.Exceptions.TooMatchCharException;
import controller.Exceptions.ZeroCharException;
import controller.Exceptions.IllegalCharException;

/**
 *
 * @author gabor
 */
public class Datacheck {
    private Datacheck(){}
    
    public static void checkEventText(String text) throws TooMatchCharException, ZeroCharException, IllegalCharException{
        if (text.length()>100) throw new TooMatchCharException();
        if (text.isEmpty()) throw new ZeroCharException();
        if (text.contains("¤")) throw new IllegalCharException();
    }
    
}
