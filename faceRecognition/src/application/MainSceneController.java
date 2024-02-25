package application;

import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.*
;

public class MainSceneController {
	// buttons
	@FXML
	private Button StartBtn;
	@FXML
	private Button StopBtn;
	
	// image view
	@FXML
	private ImageView imageView;
	
	// checkboxes
	@FXML
	private CheckBox haarClassifier;
	@FXML
	private CheckBox lbpClassifier;
	
	// variables
	private boolean cameraActive = false;
	private VideoCapture capture = new VideoCapture();
	
	// CascadeClassifier
	CascadeClassifier faceDetector = new CascadeClassifier();

	
	@FXML
	protected void startCamera() {
		// loading the haarCascade.xml provided by opencv
		faceDetector.load("C:\\opencv\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");
	
		// loadig the image on memory
		Mat image = Imgcodecs.imread("C:\\Users\\Grace\\OneDrive\\Pictures\\Saved Pictures\\bscs13E.jpg");
		
		// Saving picture into gray scale for better results
		Mat imageToSave = new Mat();
		Imgproc.cvtColor(image, imageToSave, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(imageToSave, imageToSave);
		
		// detecting multiple faces
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);
		
		// Creating a rectangular boxes around faces
		for (Rect rect: faceDetections.toArray()) {
			Imgproc.rectangle(image,
					new Point(rect.x, rect.y),
					new Point(rect.x + rect.width, rect.y + rect.height), 
					new Scalar(0, 255, 0), // green color 
					2); // thickness
		}
		
		// Save the output image
		String filename = "Output.jpg";
		
		Imgcodecs.imwrite ("C:\\Users\\Grace\\OneDrive\\Pictures\\Saved Pictures\\" + filename, image );
	}
	@FXML
	protected void stopCamera() {
		if (!cameraActive) {
		 if (!capture.isOpened()) {
	            capture.open(0);
	            cameraActive = true;
	            StopBtn.setText("Stop Camera");
		 }
		 
		 Thread captureThread = new Thread(() -> {
			 Mat frame = new Mat();
			 faceDetector.load("C:\\opencv\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");
			 MatOfRect faceDetections = new MatOfRect();
			 while (capture.read(frame)) {
				 Imgproc.cvtColor(frame, frame,Imgproc.COLOR_BGR2GRAY );
				 Imgproc.equalizeHist(frame, frame);
				 faceDetector.detectMultiScale(frame, faceDetections);
				 
				// Creating a rectangular boxes around faces
				for (Rect rect: faceDetections.toArray()) {
					Imgproc.rectangle(frame,
							new Point(rect.x, rect.y),
							new Point(rect.x + rect.width, rect.y + rect.height), 
							new Scalar(0, 255, 0), // green color 
							2); // thickness
				}
					
				 
				 // load the image on image view
				 MatOfByte buffer = new MatOfByte();
				 Imgcodecs.imencode(".png", frame, buffer);
				 Image imageToDisplay = new Image(new ByteArrayInputStream(buffer.toArray()));
				 Platform.runLater(() -> {imageView.setImage(imageToDisplay);});
				 
				 
				 try {
					 Thread.sleep(33);
				 }catch (Exception ex) {
					 System.err.println("ex: " + ex);
				 }
			 }
			 
		 });
		 captureThread.setDaemon(true);
		 captureThread.start();
		}else {
			cameraActive = false;
			capture.release();
			StopBtn.setText("Start Camera");
		}
		
	}
	
	
	 @Override
	 public void finalize() {
		 System.out.print("finallziingnging");
		 if (capture != null) {
			 capture.release();
		 }
	 }
}
