/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.emociones;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author pc
 */
public class hilotest extends Thread{

    private JButton button;

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    public hilotest(JButton button) {
        this.button = button;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000*5);
            button.setEnabled(true);
        } 
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    
}
