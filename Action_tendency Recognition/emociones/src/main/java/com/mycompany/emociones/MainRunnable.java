/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.emociones;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.ShutdownHookProcessDestroyer;

/**
 *
 * @author pc
 */
public class MainRunnable extends Thread {

//    private boolean ct = true;
    private ExecuteWatchdog watchdog = null;
    private Executor executor;
//    private DefaultExecuteResultHandler resultHandler=null;
    private JButton button;
    private JFileChooser file = new JFileChooser();

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

    /**
     * Get the value of file
     *
     * @return the value of file
     */
    public JFileChooser getFile() {
        return file;
    }

    /**
     * Set the value of file
     *
     * @param file new value of file
     */
    public void setFile(JFileChooser file) {
        this.file = file;
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    public MainRunnable(JFileChooser file, JButton button, JTextArea jTextAreaSl, JRootPane emo) {
        this.button = button;
        this.file = file;
        this.jTextAreaSl = jTextAreaSl;
        this.emo = emo;
    }

    public String getPid() throws IOException, InterruptedException {
        //Process v = Runtime.getRuntime().exec("tasklist  /fo csv | findstr /i \"python\"");
        //tasklist /nh /fo csv /fi "imagename eq python.exe" /v | findstr /i "Probabilities face your_face"
        //String comandoList = System.getenv("windir") + "/system32/" + "tasklist "
        //+ "/nh /fo csv /fi \"imagename eq python.exe\" /v "
        //+ "|"+System.getenv("windir") + "/system32/"+" findstr /i \"Probabilities face your_face\"";
        String ccomandoList
                = "cmd /C "
                + "tasklist " + "/nh /fo csv /fi \"imagename eq python.exe\" /v "
                + " | "
                + "findstr /i \"Probabilities face your_face\"";
        String line;
        //Process p = Runtime.getRuntime().exec(System.getenv("windir") + "/system32/" + "tasklist.exe /fo csv");
        Process p = Runtime.getRuntime().exec(ccomandoList);
        BufferedReader input
                = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((line = input.readLine()) != null) {
            if (line.contains("python")) {
//                System.out.println();
                return line.split(",")[1].replaceAll("\"", "");
            }
        }

        return "";

    }

    private boolean crto = false;

    public boolean isCorrecto() {
        return crto;
    }

    private boolean corre = false;

    /**
     * Get the value of corre
     *
     * @return the value of corre
     */
    public boolean isCorre() {
        return corre;
    }

    /**
     * Set the value of corre
     *
     * @param corre new value of corre
     */
    public void setCorre(boolean corre) {
        this.corre = corre;
    }

    private boolean mtStop;

    /**
     * Get the value of mtStop
     *
     * @return the value of mtStop
     */
    public boolean isMtStop() {
        return mtStop;
    }

    /**
     * Set the value of mtStop
     *
     * @param mtStop new value of mtStop
     */
    public void setMtStop(boolean mtStop) {
        this.mtStop = mtStop;
    }

    public /*synchronized*/ void thStop() {
        try {
            mtStop = true;
            corre = true;
            enStop = true;
            //ct = false; // estableces el booleano a false para detener la ejecucion del hilo
            button.setEnabled(true);
            String direc = ((CollectingLogOutputStream) executor.getStreamHandler()).getFileStr();
            System.out.println("direc - 1: " + direc);
            if (direc != null) {
                file.setSelectedFile(new File(direc));
                crto = true;
                JOptionPane.showMessageDialog(emo, "dataset se genero correctmente");
            } else {
                crto = false;
                JOptionPane.showMessageDialog(emo, "el dataset no se genero correctmente, cargelo manualmente");
            }
            //resultHandler.getExitValue();
            // interrumpis la ejecucion
            Process v = Runtime.getRuntime().exec("taskkill /f /pid " + getPid());
            watchdog.destroyProcess();
            watchdog.stop();
            JOptionPane.showMessageDialog(emo, "generacion del dataset detenida");
            System.out.println("killedProcess -1: " + watchdog.killedProcess());
//            stop();
            corre = false;
            mtStop = false;
//            notifyAll();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private JRootPane emo;

    public JRootPane getEmo() {
        return emo;
    }

    public void setEmo(JRootPane emo) {
        this.emo = emo;
    }

    boolean enStop = false;

    @Override
    public synchronized void run() {
        try {
            enStop = false;
            mtStop = false;
            JOptionPane.showMessageDialog(emo, "inicio");

            CommandLine cmdLine = new CommandLine("python");
            //final
//            cmdLine.addArgument(System.getProperty("user.dir") + "/detector/src" + "/" + "real_time_video.py");
            //desarrollo
            cmdLine.addArgument(System.getProperty("user.dir") + "/.." + "/detector/src" + "/" + "real_time_video.py");
            DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

            watchdog = new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT);
            executor = new DefaultExecutor();
            //executor.setExitValue(1);
            executor.setProcessDestroyer(new ShutdownHookProcessDestroyer());
            executor.setWatchdog(watchdog);
            executor.setStreamHandler(new CollectingLogOutputStream(jTextAreaSl, emo));
            executor.execute(cmdLine, resultHandler);

            // some time later the result handler callback was invoked so we
            // can safely request the exit value
            resultHandler.waitFor();
//            watchdog.stop();
//            watchdog.destroyProcess();
            System.out.println("killedProcess -2: " + watchdog.killedProcess());
            button.setEnabled(true);
            String direc = ((CollectingLogOutputStream) executor.getStreamHandler()).getFileStr();
            System.out.println("direc - 2: " + direc);
            if (direc != null) {
                file.setSelectedFile(new File(direc));
                if (!enStop) {
                    crto = true;
                    JOptionPane.showMessageDialog(emo, "dataset se genero correctmente");
                }
            } else {
                if (!enStop) {
                    crto = false;
                    JOptionPane.showMessageDialog(emo, "el dataset no se genero correctmente, cargelo manualmente");
                }
            }
            if (direc != null) {
                JOptionPane.showMessageDialog(emo, "archivo con el dataset generado en: \n"
                        + direc);
            } else {
                JOptionPane.showMessageDialog(emo, "archivo con el dataset no se genero");
            }

            while (mtStop) {
                //System.out.println("espera 2");
            }

            //Enumeration<Object> k = System.getProperties().keys();
            //Enumeration<Object> v = System.getProperties().elements();
            //while (k.hasMoreElements()) {
            //String nextElement = (String) k.nextElement();
            //System.out.println("key: "+nextElement+" ,patch: "+System.getProperty(nextElement));
            //}
//            notifyAll();
            corre = false;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
