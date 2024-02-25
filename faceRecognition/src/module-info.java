module faceRecognition {
	requires javafx.controls;
	requires javafx.fxml;
	requires opencv;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
}
