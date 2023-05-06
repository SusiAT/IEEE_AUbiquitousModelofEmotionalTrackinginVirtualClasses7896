/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.emociones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import org.apache.commons.exec.ExecuteStreamHandler;

/**
 *
 * @author pc
 */
public class CollectingLogOutputStream implements ExecuteStreamHandler {

    private final List<String> lines = new LinkedList<String>();

    private String fileStr = null;
    
    private JRootPane emo;

    public JRootPane getEmo() {
        return emo;
    }

    public void setEmo(JRootPane emo) {
        this.emo = emo;
    }

    /**
     * Get the value of fileStr
     *
     * @return the value of fileStr
     */
    public String getFileStr() {
        return fileStr;
    }

    /**
     * Set the value of fileStr
     *
     * @param fileStr new value of fileStr
     */
    public void setFileStr(String fileStr) {
        this.fileStr = fileStr;
    }

    public CollectingLogOutputStream(JTextArea jTextAreaSl, JRootPane emo) {
        this.jTextAreaSl = jTextAreaSl;
        this.emo=emo;
    }

    
    
    
    public void setProcessInputStream(OutputStream outputStream) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(outputStream);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.flush();
    }

    public void setProcessErrorStream(InputStream inputStream) throws IOException {
        //InputStream is = ...; // keyboard, file or Internet
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        while ((line = br.readLine()) != null) {
            //use lines whereever you want - for now just print on console
            System.out.println("error: " + line);
        }
    }

    public void setProcessOutputStream(InputStream inputStream) throws IOException {
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        while ((line = br.readLine()) != null) {
            if (line.contains("emotions.csv")) {
                this.fileStr = line.split("@@")[1];
                System.out.println("fileStr: " + this.fileStr);
                JOptionPane.showMessageDialog(emo, "dataset se esta generando");

            }
            //use lines whereever you want - for now just print on console
            System.out.println("output: " + line);
            jTextAreaSl.append(line+"\n");

        }
    }

    public void start() throws IOException {

    }

    public void stop() throws IOException {

    }

    private JTextArea jTextAreaSl;

    /**
     * Get the value of jTextAreaSl
     *
     * @return the value of jTextAreaSl
     */
    public JTextArea getjTextAreaSl() {
        return jTextAreaSl;
    }

    /**
     * Set the value of jTextAreaSl
     *
     * @param jTextAreaSl new value of jTextAreaSl
     */
    public void setjTextAreaSl(JTextArea jTextAreaSl) {
        this.jTextAreaSl = jTextAreaSl;
    }

}
