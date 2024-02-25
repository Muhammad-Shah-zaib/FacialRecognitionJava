package application;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import static org.opencv.objdetect.Objdetect.CASCADE_SCALE_IMAGE;
import org.opencv.videoio.VideoCapture;


/**
 *
 * @author ccs
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    
    String source = "C:\javacv resource\opencv\sources\data\haarcascades\haarcascade_frontalface_alt2.xml"; //paste here
  //  String source = "C:\Users\ccs\Desktop\trafic\vehicle_detection_haarcascades-master\cars.xml";
    CascadeClassifier faceDetector = new CascadeClassifier(source);
    
    
    public Home() {
        initComponents();
    }

  

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      
        
        (new Thread(){
            public void run(){
                VideoCapture capture = new VideoCapture(0);//0 mean your default web cam
         //   VideoCapture capture = new VideoCapture("C:\Users\ccs\Desktop\videoplayback.mp4");
                MatOfRect rostros = new MatOfRect();
                MatOfByte mem = new MatOfByte();
                
                Mat frame = new Mat();
                Mat frame_gray = new Mat();
                
                Rect[] facesArray;      
                Graphics g;
                BufferedImage buff = null;

                
                while(capture.read(frame)){
                    if(frame.empty()){
                        System.out.println("No detection");
                        break;
                    }else{
                        try {
                            g = jPanel1.getGraphics();
                            Imgproc.cvtColor(frame, frame_gray, Imgproc.COLOR_BGR2GRAY);
                            Imgproc.equalizeHist(frame_gray, frame_gray);
                            double w = frame.width();
                            double h = frame.height();
                            faceDetector.detectMultiScale(frame_gray, rostros, 1.1, 2, 0|CASCADE_SCALE_IMAGE, new Size(30, 30), new Size(w, h) );
                            facesArray = rostros.toArray();
                            System.out.println("No of faces: "+facesArray.length);
                         
                            for (int i = 0; i < facesArray.length; i++) {
                               
                                Point center = new Point((facesArray[i].x + facesArray[i].width * 0.5), 
                                        (facesArray[i].y + facesArray[i].height * 0.5));
                                Imgproc.ellipse(frame, 
                                        center, 
                                        new Size(facesArray[i].width * 0.5, facesArray[i].height * 0.5), 
                                        0, 
                                        0, 
                                        360, 
                                        new Scalar(255, 0, 255), 4, 8, 0);
                             
                                Mat faceROI = frame_gray.submat(facesArray[i]);
                                Imgproc.rectangle(frame,
                                        new Point(facesArray[i].x,facesArray[i].y),
                                        new Point(facesArray[i].x+facesArray[i].width,facesArray[i].y+facesArray[i].height),
                                        new Scalar(123, 213, 23, 220));
                              //  Imgproc.putText(frame, "Width: "+faceROI.width()+" Height: "+faceROI.height()+" X = "+facesArray[i].x+
                               //         " Y = "+facesArray[i].y, new Point(facesArray[i].x, facesArray[i].y-20), 1, 1, new Scalar(255,255,255));
                                Imgproc.putText(frame, "This is human "
                                     , new Point(facesArray[i].x, facesArray[i].y-20), 1, 1, new Scalar(255,255,255));
                            }
                           
                            int no= facesArray.length;
                            lblnumber.setText(String.valueOf(no));
                            
                             Imgcodecs.imencode(".bmp", frame, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                            buff = (BufferedImage) im;
                            if(g.drawImage(buff, 0, 0, jPanel1.getWidth(), jPanel1.getHeight() , 0, 0, buff.getWidth(), buff.getHeight(), null)){
                            }
                            
                            
                            
                        } catch (Exception ex) {
                   
                        }
                    }
                    
                }
            }
            
        }).start();
        
        
    }//GEN-LAST:event_jButton1ActionPerformed