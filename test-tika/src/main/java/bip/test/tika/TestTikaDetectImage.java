package bip.test.tika;

import org.apache.tika.Tika;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by ramezani on 4/1/2019.
 */
public class TestTikaDetectImage {

    Tika tika=new Tika();

    @Test
    public void testDetectMp4() throws IOException {
        String detect = tika.detect(TestTikaDetectImage.class.getResource("/7d052a584abf9ec79eb6785ffa6e5fa614233916-144p__37284.mp4"));
        System.out.println("TestTikaDetectImage.testDetectMp4="+detect);//video/mp4
    }

    @Test
    public void testDetectGif() throws IOException {
        String detect = tika.detect(TestTikaDetectImage.class.getResource("/300px-Rotating_earth_(large).gif"));
        System.out.println("TestTikaDetectImage.testDetectGif="+detect);//image/gif
    }

    @Test
    public void testDetectPng() throws IOException {
        String detect = tika.detect(TestTikaDetectImage.class.getResource("/icon.png"));
        System.out.println("TestTikaDetectImage.testDetectPng="+detect);//image/png
    }

    @Test
    public void testDetectDocx() throws IOException {
        String detect = tika.detect(TestTikaDetectImage.class.getResource("/injection.docx"));
        System.out.println("TestTikaDetectImage.testDetectDocx="+detect);//application/vnd.openxmlformats-officedocument.wordprocessingml.document
    }

    @Test
    public void testDetectDoc() throws IOException {
        String detect = tika.detect(TestTikaDetectImage.class.getResource("/RFP.doc"));
        System.out.println("TestTikaDetectImage.testDetectDoc="+detect);//application/msword
    }

}
