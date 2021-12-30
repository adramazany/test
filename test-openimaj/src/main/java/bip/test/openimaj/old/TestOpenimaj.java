package bip.test.openimaj.old;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.processing.face.detection.*;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ramezani on 10/28/2019.
 */
public class TestOpenimaj {

    public static void main(String[] args) throws IOException {
        //new TestOpenimaj().detectFace();
//        new TestOpenimaj().detectFace2();
//        new TestOpenimaj().detectFace3();
//        new TestOpenimaj().detectFace4();
        new TestOpenimaj().detectFace5();
//        new TestOpenimaj().detectFace6();
//        new TestOpenimaj().detectFace7();
    }



    public void detectFace7() throws IOException {
//        BufferedImage img= ImageIO.read(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\19707.jpg"));
        BufferedImage img= ImageIO.read(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\19707-rot90.jpg"));
        SandeepFaceDetector detector = new SandeepFaceDetector();
        List<CCDetectedFace> faces = detector.detectFaces(ImageUtilities.createMBFImage(img,true));
        if (faces == null) {
            System.out.println("No faces found in the captured image");
            return;
        }
        Iterator<CCDetectedFace> dfi = faces.iterator();
        int counter=1;
        while (dfi.hasNext()) {
            CCDetectedFace face = dfi.next();
            FImage fimage = face.getFacePatch();
            ImageUtilities.write(fimage,new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\7-19707-"+(counter++)+".png"));
        }
    }

    public void detectFace6() throws IOException {
//        BufferedImage img= ImageIO.read(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\19707.jpg"));
        BufferedImage img= ImageIO.read(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\19707-rot90.jpg"));
        IdentityFaceDetector detector = new IdentityFaceDetector();
        List faces = detector.detectFaces(ImageUtilities.createFImage(img));
        if (faces == null) {
            System.out.println("No faces found in the captured image");
            return;
        }
        Iterator dfi = faces.iterator();
        int counter=1;
        while (dfi.hasNext()) {
            Object face = dfi.next();
            //FImage fimage = face.getFacePatch();
            //ImageUtilities.write(fimage,new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\19707-"+(counter++)+".png"));
        }
    }

    public void detectFace5() throws IOException {
//        BufferedImage img= ImageIO.read(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\19707.jpg"));
        BufferedImage img= ImageIO.read(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\19707-rot90.jpg"));
        FKEFaceDetector detector = new FKEFaceDetector();
        List<KEDetectedFace> faces = detector.detectFaces(ImageUtilities.createFImage(img));
        if (faces == null) {
            System.out.println("No faces found in the captured image");
            return;
        }
        Iterator< KEDetectedFace > dfi = faces.iterator();
        int counter=1;
        while (dfi.hasNext()) {
            KEDetectedFace face = dfi.next();
            FImage fimage = face.getFacePatch();
            ImageUtilities.write(fimage,new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\5-19707-"+(counter++)+".png"));
        }
    }

    public void detectFace4() throws IOException {
        BufferedImage img= ImageIO.read(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\19707.jpg"));
        //BufferedImage img= ImageIO.read(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\19707-rot90.jpg"));
        CLMFaceDetector detector = new CLMFaceDetector();
        List<CLMDetectedFace> faces = detector.detectFaces(ImageUtilities.createFImage(img));
        if (faces == null) {
            System.out.println("No faces found in the captured image");
            return;
        }
        Iterator< CLMDetectedFace > dfi = faces.iterator();
        int counter=1;
        while (dfi.hasNext()) {
            CLMDetectedFace face = dfi.next();
            FImage fimage = face.getFacePatch();
            ImageUtilities.write(fimage,new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\4-19707-"+(counter++)+".png"));
        }
    }

    public void detectFace3() throws IOException {
        BufferedImage img= ImageIO.read(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\19707-rot90.jpg"));
        HaarCascadeDetector detector = new HaarCascadeDetector();
        List<DetectedFace> faces = detector.detectFaces(ImageUtilities.createFImage(img));
        if (faces == null) {
            System.out.println("No faces found in the captured image");
            return;
        }
        Iterator< DetectedFace > dfi = faces.iterator();
        int counter=1;
        while (dfi.hasNext()) {
            DetectedFace face = dfi.next();
            FImage fimage = face.getFacePatch();
            ImageUtilities.write(fimage,new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\3-19707-"+(counter++)+".png"));
        }
    }

    public void detectFace2() throws IOException {
        BufferedImage img= ImageIO.read(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\19707-rot90.jpg"));
        HaarCascadeDetector detector = new HaarCascadeDetector();
        List<DetectedFace> faces = detector.detectFaces(ImageUtilities.createFImage(img));
        if (faces == null) {
            System.out.println("No faces found in the captured image");
            return;
        }
        Iterator< DetectedFace > dfi = faces.iterator();
        int counter=1;
        while (dfi.hasNext()) {
            DetectedFace face = dfi.next();
            FImage fimage = face.getFacePatch();
            ImageUtilities.write(fimage,new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\2-19707"+(counter++)+".png"));
        }
    }


    public void detectFace() throws IOException {

        JFrame fr = new JFrame("Discovered Faces");

//        BufferedImage img= ImageIO.read(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\facedetection_input.jpg"));
        BufferedImage img= ImageIO.read(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\19707-rot90.jpg"));

        HaarCascadeDetector detector = new HaarCascadeDetector();
        List<DetectedFace> faces = detector.detectFaces(ImageUtilities.createFImage(img));

        if (faces == null) {

            System.out.println("No faces found in the captured image");

            return;

        }

        Iterator< DetectedFace > dfi = faces.iterator();

        while (dfi.hasNext()) {

            DetectedFace face = dfi.next();

            FImage image1 = face.getFacePatch();

            ImageUtils.ImagePanel p = new ImageUtils.ImagePanel(ImageUtilities.createBufferedImage(image1));
            p.setSize(new Dimension(100,100));
            p.repaint();


            fr.add(p);


        }

        fr.setLayout(new FlowLayout(0));

        fr.setSize(500, 500);

        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fr.setVisible(true);

    }



}
