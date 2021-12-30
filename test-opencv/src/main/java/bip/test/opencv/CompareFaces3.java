package bip.test.opencv;

import com.googlecode.javacv.cpp.opencv_contrib;
import com.googlecode.javacv.cpp.opencv_core;
import org.bytedeco.javacpp.IntPointer;
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

import static com.googlecode.javacv.cpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;

/**
 * Created by ramezani on 4/8/2019.
 * https://github.com/euginfrancis/Java-Image-Comparing-with-OpenCV
 */
public class CompareFaces3 {
/*

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //System.loadLibrary("opencv_world401");
        //System.loadLibrary("opencv_ffmpeg401_64");
//        System.loadLibrary("Emgu.CV");
//        System.loadLibrary("Emgu.CV.GPU");
//        System.loadLibrary("Emgu.CV.ML");
//        System.loadLibrary("Emgu.CV.UI");
//        System.loadLibrary("Emgu.Util");


        //FaceRecognizer faceRecognizer= EigenFaceRecognizer.create();//java.lang.UnsatisfiedLinkError: org.opencv.face.EigenFaceRecognizer.create_2()J
        //FaceRecognizer faceRecognizer= EigenFaceRecognizer.create(10,10);//java.lang.UnsatisfiedLinkError: org.opencv.face.EigenFaceRecognizer.create_0(ID)J
        //FaceRecognizer faceRecognizer= LBPHFaceRecognizer.create();//java.lang.UnsatisfiedLinkError: org.opencv.face.LBPHFaceRecognizer.create_5()J
        //FaceRecognizer faceRecognizer= FisherFaceRecognizer.create();// java.lang.UnsatisfiedLinkError: org.opencv.face.FisherFaceRecognizer.create_2()J

        //opencv_contrib.FaceRecognizerPtr faceRecognizerPtr =  com.googlecode.javacv.cpp.opencv_contrib.createLBPHFaceRecognizer(2,8,8,8);//,200);
        //opencv_contrib.FaceRecognizerPtr faceRecognizerPtr =

        opencv_contrib.FaceRecognizerPtr model = com.googlecode.javacv.cpp.opencv_contrib.createLBPHFaceRecognizer(1, 8, 8, 8);//, Double.MAX_VALUE);
        //opencv_contrib.FaceRecognizer faceRecognizer = com.googlecode.javacv.cpp.opencv_contrib.createEigenFaceRecognizer(0);

        opencv_core.MatVector faces=loadTrains();

        opencv_core.CvMat labels=loadLabesl(faces);
        //faceRecognizer.train(faces,labels);

        model.get().train(faces, labels);

        String imgPath = "C:\\Users\\ramezani\\git\\test\\test-main\\test-opencv\\src\\main\\resources\\faces-db\\MultiPie\\003_04_02_051_06.jpg";
        //Mat imgMat = Imgcodecs.imread(imgPath);
        opencv_core.IplImage img=null;
        opencv_core.IplImage grayImg=null;
        img = cvLoadImage(imgPath,CV_LOAD_IMAGE_GRAYSCALE);
        //cvCvtColor(img, grayImg, CV_BGR2GRAY);

        int predictLabel = faceRecognizer.predict(grayImg);
        System.out.println("predictLabel = " + predictLabel);
    }

    private static opencv_core.CvMat loadLabesl(opencv_core.MatVector faces) {
        opencv_core.CvMat labels = opencv_core.CvMat.create((int)faces.size(),1,4);
        for (int i = 0; i < faces.size(); i++) {
            labels.put(i,i);
        }
        return labels;
    }

    private static opencv_core.MatVector loadTrains() {
        File path = new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-opencv\\src\\main\\resources\\faces-db\\MultiPie");
        File[] listImages = path.listFiles();
        opencv_core.MatVector faces = new opencv_core.MatVector(listImages.length);
        //opencv_core.CvMat[] faces = new opencv_core.CvMat[listImages.length];
        //opencv_core.CvMat[] faces = new opencv_core.CvMat[listImages.length];

        opencv_core.IplImage img=null;
        opencv_core.IplImage grayImg=null;
        for (int i=0;i<listImages.length;i++) {
            String p = listImages[i].getAbsolutePath();
            img = cvLoadImage(p,CV_LOAD_IMAGE_GRAYSCALE);
            //cvCvtColor(img, grayImg, CV_BGR2GRAY);
            //opencv_core.CvArr c = new opencv_core.CvArr();
            faces.put(i,grayImg);
            //faces[i]=grayImg.asCvMat();
        }

        //opencv_core.CvArr result = new opencv_core.CvArr(new opencv_core.CvMatArray(faces).);
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
