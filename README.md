# IEEE_AUbiquitousModelofEmotionalTrackinginVirtualClasses7896
IEEE_A Ubiquitous Model of Emotional Tracking in Virtual Classes: From Simple Emotions to Learning Action Tendency_/7896

<img width="832" alt="graphical_abstract" src="https://user-images.githubusercontent.com/132758488/236627184-e7dc0c9e-3b74-4ed4-b9f4-212bba559faa.png">
Resources and extra documentation for the manuscript "A Ubiquitous Model of Emotional Tracking in Virtual Classes: From Simple Emotions to Learning Action Tendency" published in IEEE Latin America Transactions. The project hierarchy and folders description is as follows


**Emotion Recognition Master**: Base software is used to record the human face and mixed emotions and get the probabilities of these emotions. (https://github.com/omar-aymen/Emotion-recognition#p1).
![image](https://user-images.githubusercontent.com/132758488/236630757-f8844bc0-c6c1-4483-aaba-7024a82818fb.png)


**Emotion Recognition Master\detector\src**: Code that allows the execution of the facial emotion identification software. The quality of the video must be verified, and it must have the name of the Profesor. 

**Real_Time_Video**: is a principal code. 

**Emotions.csv** has the facial emotions. 

![image](https://user-images.githubusercontent.com/132758488/236630861-23c0892b-7621-4600-8753-ce4e95c226f6.png)

**Emociones**: Code that receives the emotions and their percentages as input, executes the Kmeans, and works with the ontology for the action Tendency inference.

**emociones\src\main\java\com\mycompany\emociones**:Emociones_2.java principal class.
![image](https://user-images.githubusercontent.com/132758488/236631250-1ee59940-7369-4a86-a4e2-0ea921d664d0.png)

**REQUIREMENTS**

Product Version: Apache NetBeans IDE 11.3

Java: 1.8.0_333; Java HotSpot(TM) 64-Bit Server VM 25.333-b02

System: Windows 11 version 10.0 running on amd64; Cp1252; es_EC (nb)

**Jena java lib** and others: https://jena.apache.org/

![image](https://user-images.githubusercontent.com/132758488/236631919-e64327c3-5fae-4658-af7b-0659cece7787.png)

**FERO.owl**: Ontology to inference

**SCREENSHOTS**

![image](https://user-images.githubusercontent.com/132758488/236632379-26ef662b-5d4b-4e3b-bbc2-8d9e1c8c6ca5.png)![image](https://user-images.githubusercontent.com/132758488/236632401-0c68d4fd-9966-4954-b606-b13ed91309e2.png)

![image](https://user-images.githubusercontent.com/132758488/236632596-13e6744c-a7cf-48db-893d-d00990a84beb.png) ![image](https://user-images.githubusercontent.com/132758488/236632687-27ce6fdd-db6d-49a9-8ad5-479ba85dcc2b.png)

**INSTRUCTIONS FOR RUNNING THE GUI**

- Work with emotions_2.java using Netbeans

![image](https://user-images.githubusercontent.com/132758488/236633051-2fbe9f94-bb10-4227-9fbd-655813276541.png)

- Run Generar Dataset:

![image](https://user-images.githubusercontent.com/132758488/236633168-10788f43-1b45-422e-a02b-d2be9163b461.png)
![image](https://user-images.githubusercontent.com/132758488/236633235-f7a563a8-c59d-4dcf-940b-3ba15a058415.png)
![image](https://user-images.githubusercontent.com/132758488/236633329-197cab9b-f691-44d5-af0f-58ea57debe79.png)

- Wait for finish message, see the message:

![image](https://user-images.githubusercontent.com/132758488/236633398-1cd24a12-841a-4f9f-a832-51d1a1232da4.png)

- Active Kmeans:

![image](https://user-images.githubusercontent.com/132758488/236633434-eafd6eb3-09fd-4bad-9bfa-b92ad147dd8a.png)

- See the Cargar ontologia botton, using FERO.owl

![image](https://user-images.githubusercontent.com/132758488/236633517-c1317481-d6ea-412a-8cb0-15fcde9f5766.png)

![image](https://user-images.githubusercontent.com/132758488/236633573-42359914-dd2b-45b0-8df6-63f1d229eccd.png)

- See the message, Fero-generada.owl (ontology inference)

![image](https://user-images.githubusercontent.com/132758488/236633631-1936bf19-955b-436c-80c6-deaf8a95b75f.png)

- See Action_Tendency results:

![image](https://user-images.githubusercontent.com/132758488/236633709-c88ea5fa-68b0-44db-99b6-02075c32c119.png)

We have restricted videos with 800Mb or more. If you need our videos, please send message to sa.arias@uta.edu.ec 























