package bip.test.tika;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by ramezani on 4/1/2019.
 */
public class TestTikaBinary2Text {

    Tika tika = new Tika();

    @Test
    public void testMp4_Text() throws IOException, TikaException {
        String text = tika.parseToString(TestTikaBinary2Text.class.getResource("/7d052a584abf9ec79eb6785ffa6e5fa614233916-144p__37284.mp4"));
        System.out.println("testMp4_Text = " + text);
    }
    @Test
    public void testGif_Text() throws IOException, TikaException {
        String text = tika.parseToString(TestTikaBinary2Text.class.getResource("/300px-Rotating_earth_(large).gif"));
        System.out.println("testGif_Text = " + text);
    }

    @Test
    public void testPng_Text() throws IOException, TikaException {
        String text = tika.parseToString(TestTikaBinary2Text.class.getResource("/icon.png"));
        System.out.println("testGif_Text = " + text);
    }

    @Test
    public void testDocx_Text() throws IOException, TikaException {
        String text = tika.parseToString(TestTikaBinary2Text.class.getResource("/injection.docx"));
        System.out.println("testDocx_Text = " + text);
    }

    @Test
    public void testDoc_Text() throws IOException, TikaException {
        String text = tika.parseToString(TestTikaBinary2Text.class.getResource("/RFP.doc"));
        System.out.println("testDoc_Text = " + text);
    }

    @Test
    public void testParserExtraction() throws IOException,SAXException, TikaException {

        //Assume sample.txt is in your current directory
        File file = new File(TestTikaBinary2Text.class.getResource("/7d052a584abf9ec79eb6785ffa6e5fa614233916-144p__37284.mp4").getFile());

        //parse method parameters
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(file);
        ParseContext context = new ParseContext();

        //parsing the file
        parser.parse(inputstream, handler, metadata, context);
        System.out.println("File content : " + handler.toString());
    }



}
