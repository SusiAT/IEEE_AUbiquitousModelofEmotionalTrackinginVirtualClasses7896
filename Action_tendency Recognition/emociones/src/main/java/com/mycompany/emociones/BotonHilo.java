/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.emociones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 *
 * @author pc
 */
public class BotonHilo extends Thread {

    private JButton buttonArray[];
    private JButton botonAccion;
    private MainRunnable gdt;
    private JButton botonOwl;
    private JButton btStop;

    /**
     * Get the value of btStop
     *
     * @return the value of btStop
     */
    public JButton getBtStop() {
        return btStop;
    }

    /**
     * Set the value of btStop
     *
     * @param btStop new value of btStop
     */
    public void setBtStop(JButton btStop) {
        this.btStop = btStop;
    }

    public JButton getBotonOwl() {
        return botonOwl;
    }

    public void setBotonOwl(JButton botonOwl) {
        this.botonOwl = botonOwl;
    }

    public JButton getBotonAccion() {
        return botonAccion;
    }

    public void setBotonAccion(JButton botonAccion) {
        this.botonAccion = botonAccion;
    }

    public MainRunnable getGdt() {
        return gdt;
    }

    public void setGdt(MainRunnable gdt) {
        this.gdt = gdt;
    }

    public BotonHilo(JButton[] buttonArray, JButton botonAccion, MainRunnable gdt, JButton botonOwl, MetodosExternos emo) {
        this.buttonArray = buttonArray;
        this.botonAccion = botonAccion;
        this.gdt = gdt;
        this.botonOwl = botonOwl;
        this.emo = emo;
    }

    public JButton[] getButtonArray() {
        return buttonArray;
    }

    public void setButtonArray(JButton[] buttonArray) {
        this.buttonArray = buttonArray;
    }

    private MetodosExternos emo;

    public MetodosExternos getEmo() {
        return emo;
    }

    public void setEmo(MetodosExternos emo) {
        this.emo = emo;
    }

    @Override
    public synchronized void run() {
//        try {
            while (gdt.isCorre()) {

                if (buttonArray != null) {
                    for (JButton jButton : buttonArray) {
                        jButton.setEnabled(false);
                    }
                }
                
            }
            
//            wait();
            
            if (gdt.isCorrecto()) {

                if (buttonArray != null && botonAccion != null) {

                    //ActionEvent event;
                    //long when = System.currentTimeMillis();
                    //event = new ActionEvent(botonAccion, ActionEvent.ACTION_PERFORMED, "Anything", when, 0);
                    //for (ActionListener listener : botonAccion.getActionListeners()) {
                    //listener.actionPerformed(event);
                    //}
                    emo.cargarFromHilo();
                    for (JButton jButton : buttonArray) {
                        jButton.setEnabled(true);
                    }
                }
            } else {
                for (JButton jButton : buttonArray) {
                    jButton.setEnabled(true);
                }
                botonOwl.setEnabled(false);
                if(btStop != null){
                    btStop.setEnabled(false);
                }
            }
//        } 
//        catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
    }

}
