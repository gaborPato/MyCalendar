/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Exceptions;

/**
 *
 * @author gabor
 */
public class ZeroCharException extends Exception {

    public ZeroCharException() {
    }

    @Override
    public String toString() {
        return "Az eseménymezöt ki kell tolteni";
    }
    
}
