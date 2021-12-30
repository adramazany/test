package bip.test.opencv;

import org.opencv.core.*;
import org.opencv.face.EigenFaceRecognizer;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.FisherFaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramezani on 4/8/2019.
 * https://github.com/euginfrancis/Java-Image-Comparing-with-OpenCV
 */
public class CompareFaces {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //System.loadLibrary("opencv_world401");
        //System.loadLibrary("opencv_ffmpeg401_64");
//        System.loadLibrary("Emgu.CV");
//        System.loadLibrary("Emgu.CV.GPU");
//        System.loadLibrary("Emgu.CV.ML");
//        System.loadLibrary("Emgu.CV.UI");
//        System.loadLibrary("Emgu.Util");


        FaceRecognizer faceRecognizer= EigenFaceRecognizer.create();//java.lang.UnsatisfiedLinkError: org.opencv.face.EigenFaceRecognizer.create_2()J
        //FaceRecognizer faceRecognizer= EigenFaceRecognizer.create(10,10);//java.lang.UnsatisfiedLinkError: org.opencv.face.EigenFaceRecognizer.create_0(ID)J
        //FaceRecognizer faceRecognizer= LBPHFaceRecognizer.create();//java.lang.UnsatisfiedLinkError: org.opencv.face.LBPHFaceRecognizer.create_5()J
        //FaceRecognizer faceRecognizer= FisherFaceRecognizer.create();// java.lang.UnsatisfiedLinkError: org.opencv.face.FisherFaceRecognizer.create_2()J

        List<Mat> faces=loadTrains();
        MatOfInt labels=loadLabesl(faces);
        faceRecognizer.train(faces,labels);

        String imgPath = "C:\\Users\\ramezani\\git\\test\\test-main\\test-opencv\\src\\main\\resources\\faces-db\\MultiPie\\003_04_02_051_06.jpg";
        Mat imgMat = Imgcodecs.imread(imgPath);

        int predictLabel = faceRecognizer.predict_label(imgMat);
        System.out.println("predictLabel = " + predictLabel);
    }

    private static MatOfInt loadLabesl(List<Mat> faces) {
        MatOfInt labels = new MatOfInt(new int[faces.size()]);
        for (int i = 0; i < faces.size(); i++) {
            labels.put(i,0,i);
        }
        return labels;
    }

    private static List<Mat> loadTrains() {
        List<Mat> faces = new ArrayList<Mat>();
        File path = new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-opencv\\src\\main\\resources\\faces-db\\MultiPie");
        for (File imgFile:path.listFiles()) {
            Mat imgMat = Imgcodecs.imread(imgFile.getPath());
            faces.add(imgMat);
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
}
