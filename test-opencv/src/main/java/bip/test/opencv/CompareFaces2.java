package bip.test.opencv;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_face;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.opencv.core.*;
import org.opencv.face.EigenFaceRecognizer;
import org.opencv.face.FaceRecognizer;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramezani on 4/8/2019.
 * https://github.com/euginfrancis/Java-Image-Comparing-with-OpenCV
 */
public class CompareFaces2 {

/*
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.loadLibrary("opencv_world401");
        System.loadLibrary("opencv_ffmpeg401_64");

        opencv_face.FaceRecognizer faceRecognizer= opencv_face.EigenFaceRecognizer.create();

        opencv_core.MatVector faces=loadTrains();
        opencv_core.MatOfInt labels=loadLabesl(faces);
        faceRecognizer.train(faces,labels);

        String imgPath = "C:\\Users\\ramezani\\git\\test\\test-main\\test-opencv\\src\\main\\resources\\faces-db\\MultiPie\\003_04_02_051_06.jpg";
        Mat imgMat = Imgcodecs.imread(imgPath);

        int predictLabel = faceRecognizer.predict_label(imgMat);
        System.out.println("predictLabel = " + predictLabel);
    }

    private static MatOfInt loadLabesl(List<Mat> faces) {
        opencv_core.Mat labels ;//= new MatOfInt(new int[faces.size()]);
        opencv_core.UMat l;
        for (int i = 0; i < faces.size(); i++) {
            labels.put(i,0,i);
        }
        return labels;
    }

    private static opencv_core.MatVector loadTrains() {
        opencv_core.MatVector faces = new opencv_core.MatVector();
        File path = new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-opencv\\src\\main\\resources\\faces-db\\MultiPie");
        for (File imgFile:path.listFiles()) {
            //Mat imgMat = Imgcodecs.imread(imgFile.getPath());
            opencv_core.Mat imgMat= opencv_imgcodecs.imread(imgFile.getPath());
            faces.put(imgMat);
        }
        return faces;
    }


    private static void compareImages(String path1, String path2) {
        System.out.println(path1 + "-" + path2);

        FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
        DescriptorExtractor descriptor = DescriptorExtractor.create(DescriptorExtractor.ORB);

        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

        // first image
        Mat img1 = Imgcodecs.imread(path1, Imgcodecs.IMREAD_GRAYSCALE);
        Mat descriptors1 = new Mat();
        MatOfKeyPoint keypoints1 = new MatOfKeyPoint();

        detector.detect(img1, keypoints1);
        descriptor.compute(img1, keypoints1, descriptors1);

        // second image
        Mat img2 = Imgcodecs.imread(path2, Imgcodecs.IMREAD_GRAYSCALE);
        Mat descriptors2 = new Mat();
        MatOfKeyPoint keypoints2 = new MatOfKeyPoint();

        detector.detect(img2, keypoints2);
        descriptor.compute(img2, keypoints2, descriptors2);

        // match these two keypoints sets
        MatOfDMatch matches = new MatOfDMatch();
        matcher.match(descriptors1, descriptors2, matches);

        for (DMatch m : matches.toArray()) {
            // how to use these values to detect the similarity? They seem to be way off
            // all of these values are in range 50-80 which seems wrong to me
            System.out.println(m.distance);
        }

    }
*/
}
