from keras.preprocessing.image import img_to_array
from pathlib import Path
import imutils
import cv2
from keras.models import load_model
import numpy as np
import time
import os




# parameters for loading data and images
home = os.path.dirname(__file__)
detection_model_path = home+'/'+'haarcascade_files/haarcascade_frontalface_default.xml'
emotion_model_path = home+'/'+'models/_mini_XCEPTION.102-0.66.hdf5'
# hyper-parameters for bounding boxes shape
# loading models
face_detection = cv2.CascadeClassifier(detection_model_path)
emotion_classifier = load_model(emotion_model_path, compile=False)
EMOTIONS = ["angry" ,"disgust","scared", "happy", "sad", "surprised",
 "neutral"]


#feelings_faces = []
#for index, emotion in enumerate(EMOTIONS):
   # feelings_faces.append(cv2.imread('emojis/' + emotion + '.png', -1))


#file
fName= 'emotions.csv'  
f = open(home + '/' + fName, 'w')
titulo = "{},{}".format("Emotions", "Percent")
f.write(titulo)
f.close
print(fName+'@@'+home + '/' + fName)

# starting video streaming
cv2.namedWindow('face')
#camera = cv2.VideoCapture(0)
camera = cv2.VideoCapture(home+'/'+'Profesor.mp4')
#cont = 0

while True:
    ret,frame = camera.read()
    if ret:
        #reading the frame
        frame = imutils.resize(frame,width=1280)
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        faces = face_detection.detectMultiScale(gray,scaleFactor=1.1,minNeighbors=5,minSize=(30,30),flags=cv2.CASCADE_SCALE_IMAGE)
    
        canvas = np.zeros((250, 300, 3), dtype="uint8")
        frameClone = frame.copy()
        if len(faces) > 0:
            faces = sorted(faces, reverse=True,
            key=lambda x: (x[2] - x[0]) * (x[3] - x[1]))[0]
            (fX, fY, fW, fH) = faces
                    # Extract the ROI of the face from the grayscale image, resize it to a fixed 28x28 pixels, and then prepare
            # the ROI for classification via the CNN
            roi = gray[fY:fY + fH, fX:fX + fW]
            roi = cv2.resize(roi, (64, 64))
            roi = roi.astype("float") / 255.0
            roi = img_to_array(roi)
            roi = np.expand_dims(roi, axis=0)
        
        
            preds = emotion_classifier.predict(roi)[0]
            emotion_probability = np.max(preds)
            label = EMOTIONS[preds.argmax()]
        else: continue
        weigth = 0 
        f = open(home+'/'+fName,'a')
        for (i, (emotion, prob)) in enumerate(zip(EMOTIONS, preds)):
                    weigth=100
                    print(prob)
                    # construct the label text
                    #EMOTIONS = ["angry" ,"disgust","scared", "happy", "sad", "surprised", "neutral"]
                    if (emotion== "sad") or (emotion == "scared") or (emotion == "disgust") or (emotion == "angry"):
                        weigth = -5 * prob
                    if (emotion == "happy") or (emotion == "surprised"):
                        weigth = 5 * prob
                    if (emotion =="neutral"):
                        weigth = 0
                
     
                    text = "{}: {:.2f}%".format(emotion, prob * 100)
                    #seconds = time.time()
                    #local_time = time.ctime(minute)
                    #result = local_time
                    #text2 = "{};{};{};{}".format(emotion, prob * 100,time.localtime().tm_min,weigth)
                    text2 = "{},{}".format(emotion, prob * 100)
                    print(text2)
                    #print (local_time)
                    #f = open('emotions.txt','a')
                    f.write('\n'+text2)
                    #f.close

                # draw the label + probability bar on the canvas
               # emoji_face = feelings_faces[np.argmax(preds)]

                
                    w = int(prob * 300)
                    cv2.rectangle(canvas, (7, (i * 35) + 5),
                    (w, (i * 35) + 35), (0, 0, 255), -1)
                    cv2.putText(canvas, text, (10, (i * 35) + 23),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.45,
                    (255, 255, 255), 2)
                    cv2.putText(frameClone, label, (fX, fY - 10),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.45, (0, 0, 255), 2)
                    cv2.rectangle(frameClone, (fX, fY), (fX + fW, fY + fH),
                              (0, 0, 255), 2)
#    for c in range(0, 3):
#        frame[200:320, 10:130, c] = emoji_face[:, :, c] * \
#        (emoji_face[:, :, 3] / 255.0) + frame[200:320,
#        10:130, c] * (1.0 - emoji_face[:, :, 3] / 255.0)


        cv2.imshow('your_face', frameClone)
        cv2.imshow("Probabilities", canvas)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
    else:
        break

camera.release()
cv2.destroyAllWindows()
f.close
