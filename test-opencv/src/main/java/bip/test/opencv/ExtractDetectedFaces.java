package bip.test.opencv;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ramezani on 4/8/2019.
 */
public class ExtractDetectedFaces {

    public static void main(String[] args) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String imgFile ="C:/Users/ramezani/git/test/test-main/test-opencv/src/main/resources/faces-db/tutorialspoint/facedetection_input.jpg";
        Mat matSrc = Imgcodecs.imread(imgFile);

        String xmlClassifierFile = "C:/Users/ramezani/git/test/test-main/test-opencv/src/main/resources/xml/lbpcascade_frontalface.xml";
        CascadeClassifier classifier = new CascadeClassifier(xmlClassifierFile);


        MatOfRect detectedFaces=new MatOfRect();
        classifier.detectMultiScale(matSrc,detectedFaces);

        Scalar color=new Scalar(0,0,255);
        int counter=1;
        for (Rect detectedFace :detectedFaces.toArray()) {
            Mat matFace = new Mat(matSrc,detectedFace);
            Imgproc.rectangle(matSrc,detectedFace,color);

            writeMat(matFace,new File(String.format("./test-opencv/out/%d.jpg",counter++)));
        }
        MatOfByte buf=new MatOfByte();
        Imgcodecs.imencode(".jpg",matSrc,buf);

        JFrame frame = new JFrame("ExtractDetectedFaces");
        frame.getContentPane().add(new JLabel(new ImageIcon(buf.toArray())));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);

    }

    public static void writeMat(Mat face, File out) throws IOException {
        out.getParentFile().mkdirs();
        MatOfByte buf=new MatOfByte();
        Imgcodecs.imencode(".jpg",face,buf);

        FileOutputStream fout = new FileOutputStream(out);
        try {
            fout.write(buf.toArray());
        }finally{
            fout.close();
        }

    }
}
