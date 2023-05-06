/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.emociones;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import weka.clusterers.SimpleKMeans;
import weka.core.Capabilities;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.CSVLoader;

/**
 *
 * @author pc
 */
public class NewClass1 {

    private static String USAGE = "use parameters: <my_filepath.csv> <K>";
    static int K = 3;
    static File inFile;
    private static PrintStream err = null;
    static int maxIteration = 1000;
    final static int maxK = 1000;
    static JFileChooser jFileChooser1 = new JFileChooser();

    public static void main(String[] args) {
        //weka.clusterers.SimpleKMeans -init 0 -max-candidates 100 -periodic-pruning 10000 -min-density 2.0 -t1 -1.25 -t2 -1.0 -N 2 -A "weka.core.EuclideanDistance -R first-last" -I 500 -num-slots 1 -S 10
        jFileChooser1.setFileFilter(new FileNameExtensionFilter("CSV", "csv"));
        int returnValue = jFileChooser1.showOpenDialog(null);
        if (returnValue == jFileChooser1.APPROVE_OPTION) {
            try {
                USAGE = jFileChooser1.getSelectedFile().getAbsolutePath();
                CSVLoader loader = new CSVLoader();
                loader.setSource(jFileChooser1.getSelectedFile());
                Instances data = loader.getDataSet();
//                //hack to avoid some weka error  messages
//                err = System.err;
//                System.setErr(err);//hack to avoid some error messages

                // Create the KMeans object.
                SimpleKMeans kmeans = new SimpleKMeans();
                kmeans.setCanopyMaxNumCanopiesToHoldInMemory(100);
                kmeans.setCanopyMinimumCanopyDensity(2.0);
                kmeans.setCanopyPeriodicPruningRate(10000);
                kmeans.setCanopyT1(-1.25);
                kmeans.setCanopyT2(-1.0);
                EuclideanDistance Euclide = new EuclideanDistance();
                Euclide.setAttributeIndices("first-last");
                kmeans.setDistanceFunction(Euclide);
                kmeans.setMaxIterations(500);
                kmeans.setNumExecutionSlots(1);
                kmeans.setSeed(10);
                kmeans.setNumClusters(K);
                kmeans.setInitializationMethod(new SelectedTag("Random", SimpleKMeans.TAGS_SELECTION));
//                kmeans.setPreserveInstancesOrder(true);

                // Perform K-Means clustering.
                kmeans.buildClusterer(data);

                // print out the cluster centroids
                Instances centroids = kmeans.getClusterCentroids();
                System.out.println("variances: " + centroids.variances()[0]);
                System.out.println("variances: " + centroids.variances()[1]);
//                System.out.println("variances: " + centroids.variances()[2]);
                System.out.println("numDistinctValues: " + centroids.numDistinctValues(1));
                System.out.println("meanOrMode: " + centroids.meanOrMode(1)+", ");
//                System.out.print("meanOrMode: " + centroids.meanOrMode(2)+", ");
                for (int i = 0; i < K; i++) {
                    System.out.print("Cluster " + (i + 1) + " size: " + kmeans.getClusterSizes()[i]);
                    System.out.print(" ,Centroid: " + centroids.instance(i));
                    System.out.print(" ,Centroid name: " + centroids.instance(i).stringValue(0));
                    System.out.println(" ,Centroid value: " + centroids.instance(i).value(1));
                }

                Enumeration<Object> v1 = centroids.attribute(0).enumerateValues();
                while (v1.hasMoreElements()) {
                    String el = (String) v1.nextElement();
                    System.out.println(el);
                }

                System.out.println("+++++++++++++++++++++++++++++++++");
                for (int i = 0; i < 1; i++) {
                    for (int j = 0; j < centroids.get(i).numAttributes(); j++) {
                        for (int k = 0; k < centroids.get(i).attribute(0).numValues(); k++) {
                            if (centroids.get(i).attribute(j).isNumeric()) {
                                System.out.println("i: " + i + " ,j: " + j + " ,k: " + (k + 1) + " ---- " + centroids.get(i).attribute(j).isNumeric());
                            } else {
                                System.out.println("i: " + i + " ,j: " + j + " ,k: " + (k + 1) + " ---- " + centroids.get(i).attribute(j).value(k));
                            }
                        }
                    }
                }

                System.out.println("+++++++++++++++++++++++++++++++++");
//                for (int i = 0; i < 3; i++) {
//                    System.out.println("I-0: "+centroids.attributeToDoubleArray(0)[i]);
//                    System.out.println("I-1: "+centroids.attributeToDoubleArray(1)[i]);
//                }
                System.out.println("+++++++++++++++++++++++++++++++++");
//                for (int i = 0; i < centroids.stream().count(); i++) {
//                System.out.println("I-0: " + centroids.kthSmallestValue(0, 0));
//                System.out.println("I-0: " + centroids.kthSmallestValue(0, 1));
//                System.out.println("I-0: " + centroids.kthSmallestValue(0, 2));
//                System.out.println("I-0: " + centroids.kthSmallestValue(0, 3));
//                System.out.println("I-0: " + centroids.kthSmallestValue(1, 0));
                System.out.println("I-0: " + centroids.kthSmallestValue(1, 1));
                System.out.println("I-0: " + centroids.kthSmallestValue(1, 2));
//                System.out.println("I-0: " + centroids.kthSmallestValue(1, 3));

//                    System.out.println("I-1: "+centroids.attributeToDoubleArray(1)[i]);
//                }
                System.out.println("+++++++++++++++++++++++++++++++++");
//                for (int sig : kmeans.getAssignments()) {
//                    System.out.println(sig);
//                }
                System.out.println("+++++++++++++++++++++++++++++++++");
//                Instances  t = kmeans.getClusterStandardDevs();
//                System.out.println(t);

                System.out.println("getClusterNominalCounts");
                int a=0;
                for (double dat1[][] : kmeans.getClusterNominalCounts()) {
                    int b = 0;
                    for (double dat2[] : dat1) {
                        int c = 0;
                        if (dat2 != null) {
                            for (double dat3 : dat2) {
                                System.out.println("a: " + (a + 1) +", b: " + (b + 1) + ", c: " + (c + 1) + " - " + dat3);
                                c++;
                            }
                        }
                        b++;
                    }
                    a++;
                }
                System.out.println("+++++++++++++++++++++++++++++++++");
//                for (Instance valo: centroids) {
//                    System.out.println("valo: "+valo.attributeSparse(1).numValues());
//                }
                System.out.println("+++++++++++++++++++++++++++++++++");
//                System.out.println(kmeans.numberOfClusters());
                System.out.println("+++++++++++++++++++++++++++++++++");
//                Iterator<Capabilities.Capability> val = kmeans.getCapabilities().capabilities();
//                while(val.hasNext()) {
//                    Capabilities.Capability v=val.next();
//                    System.out.println("ordinal: "+v.ordinal() + " ,name: "+v.name()+" ,: "+v.isClassCapability());
//                }

//                kmeans.getClusterNominalCounts()[0][1]
//                for (Double data2 : kmeans.getClusterNominalCounts()[0][0]) {
//                    System.out.printf("a: %f \n", data2);
//                }
//                System.out.println(kmeans.getClusterNominalCounts()[0][0][0]);
//                System.out.println(centroids.get(1).dataset().enumerateInstances().nextElement());
                Enumeration<Instance> v2 = centroids.get(1).dataset().enumerateInstances();
                while (v2.hasMoreElements()) {
                    Instance el = (Instance) v2.nextElement();
                    double value = el.value(1);
                    System.out.println(value);
                }

                //Print Assignments:
//                int[] assignments = kmeans.getAssignments();
//                System.out.println("Length: " + assignments.length);
//                for (int i = 0; i < assignments.length; i++) {
//                    System.out.println(assignments[i]);
//
//                }
//                for (String dato : kmeans.getOptions()) {
//                    System.out.println(dato);
//                }
            } catch (IOException ex) {
                Logger.getLogger(NewClass1.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(NewClass1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
