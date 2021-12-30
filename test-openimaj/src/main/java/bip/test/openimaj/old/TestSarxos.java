package bip.test.openimaj.old;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by ramezani on 10/28/2019.
 */
public class TestSarxos extends JFrame{

    public TestSarxos() throws IOException {

        Webcam webcam = Webcam.getDefault();

        webcam.setViewSize(WebcamResolution.VGA.getSize());

        webcam.open(true);

        BufferedImage img = webcam.getImage();

        webcam.close();

        ImageUtils.ImagePanel panel = new ImageUtils.ImagePanel(img);

        panel.setPreferredSize(WebcamResolution.VGA.getSize());

        add(panel);

        setTitle("Face Recognizer");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();

        setLocationRelativeTo(null);

        setVisible(true);

    }
}
