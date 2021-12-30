package bip.test.opencv;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by ramezani on 4/4/2019.
 */
public class DisplayDetectedFaces {

    public static void main(String[] args) throws IOException {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

//        String imgFile ="C:/Users/ramezani/git/test/test-main/test-opencv/src/main/resources/faces-db/tutorialspoint/facedetection_input.jpg";
        //String imgFile ="C:\\Users\\ramezani\\git\\test\\test-main\\test-opencv\\src\\main\\resources\\faces-db\\a\\19707.jpg";
        String imgFile ="C:\\Users\\ramezani\\git\\test\\test-main\\test-opencv\\src\\main\\resources\\faces-db\\a\\19707-rot90.jpg";
        Mat matSrc = Imgcodecs.imread(imgFile);

        String xmlClassifierFile = "C:/Users/ramezani/git/test/test-main/test-opencv/src/main/resources/xml/lbpcascade_frontalface.xml";
        CascadeClassifier classifier = new CascadeClassifier(xmlClassifierFile);

        MatOfRect detectedFacesRect = new MatOfRect();
        classifier.detectMultiScale(matSrc, detectedFacesRect);
        classifier.detectMultiScale3();


        Scalar faceBorder = new Scalar(0,0,255);
        for (Rect rect :detectedFacesRect.toArray()) {
            Imgproc.rectangle(matSrc,rect,faceBorder);
        }

        MatOfByte matBuf = new MatOfByte();
        Imgcodecs.imencode(".jpg",matSrc,matBuf);

        ByteArrayInputStream bain = new ByteArrayInputStream(matBuf.toArray());
        BufferedImage bufImage = ImageIO.read(bain);

        JFrame frame = new JFrame("DisplayDetectedFaces");
        //frame.getContentPane().add(new JLabel(new ImageIcon(matBuf.toArray())));
        frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);



    }

}
