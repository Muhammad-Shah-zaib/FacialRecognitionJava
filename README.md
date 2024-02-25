# Face Recognition Basic Setup

This project is a basic setup for face recognition using Java with Eclipse, JavaFX SDK, JK21, JavaFX Scene Builder, and OpenCV. The face detection is implemented using the Haar Cascade frontal face recognition algorithm provided by OpenCV. The project utilizes the default webcam for capturing video, but you can easily change it by modifying the code accordingly.

## Setup Instructions

### Prerequisites

Before running the project, ensure you have the following installed:

- Eclipse IDE (Optional)
- JavaFX SDK
- JK21 library
- JavaFX Scene Builder
- OpenCV library

### Configuration

1. Clone this repository to your local machine:

    ```
    git clone https://github.com/yourusername/facerecognition-basic-setup.git
    ```

2. Open the project in Eclipse IDE.

3. Make sure all necessary libraries (JavaFX SDK, JK21, JavaFX Scene Builder, OpenCV) are properly configured in the *project build path* .

4. Ensure the paths to the required files are correctly set in the project. Modify the code accordingly if necessary.

## Usage

1. Run the project in Eclipse IDE.

2. The application will start, and the default webcam will be used for face detection.

3. You can modify the code to use a different webcam by changing the `videoCapture()` parameter.

4. The application will detect faces in the captured video stream using the Haar Cascade frontal face recognition algorithm provided by OpenCV.You also have to update the path according to your system

## Acknowledgements

- Thanks to the creators and contributors of OpenCV for providing the Haar Cascade frontal face recognition algorithm.
- Thanks to the developers of Eclipse IDE, JavaFX SDK, JK21, and JavaFX Scene Builder for their contributions to the Java development ecosystem.
