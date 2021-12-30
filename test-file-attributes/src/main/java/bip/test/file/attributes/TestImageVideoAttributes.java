package bip.test.file.attributes;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.IImageMetadata;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.junit.Test;

import org.w3c.dom.*;

import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.imageio.stream.*;
import javax.imageio.metadata.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

/**
 * Created by ramezani on 3/24/2019.
 *
 *  getting image, document, video, audio or even e-book files , metadata hidden inside the files
 */
public class TestImageVideoAttributes {


    @Test
    public void readFromTika() throws IOException, TikaException, SAXException {
        //String filePath="./src/main/resources/flower-3140492_1280.jpg";;
        //String filePath= "./src/main/resources/7d052a584abf9ec79eb6785ffa6e5fa614233916-144p__37284.mp4";
        String filePath= "./src/main/resources/text.txt";
        File file = new File(filePath);

        //Parser method parameters
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        org.apache.tika.metadata.Metadata metadata = new org.apache.tika.metadata.Metadata();
        FileInputStream inputstream = new FileInputStream(file);
        ParseContext context = new ParseContext();

        parser.parse(inputstream, handler, metadata, context);
        System.out.println(handler.toString());

        //getting the list of all meta data elements
        String[] metadataNames = metadata.names();

        for(String name : metadataNames) {
            System.out.println(name + ": " + metadata.get(name));
        }
    }

    @Test
    public void readFromApacheCommonsImage() throws IOException, ImageReadException {
        String jpegImageFile="./src/main/resources/flower-3140492_1280.jpg";;
        final IImageMetadata metadata = Imaging.getMetadata(new File(jpegImageFile));
        final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
        for (IImageMetadata.IImageMetadataItem item:jpegMetadata.getItems()) {
            System.out.println(item.toString());

        }
    }

    @Test
    public void readFromMetadataExtractor() throws ImageProcessingException, IOException {
        //String imagePath= "./src/main/resources/flower-3140492_1280.jpg";
        //String imagePath= "./src/main/resources/watercolor-flower-png-pack.png";
        //String imagePath= "./src/main/resources/300px-Rotating_earth_(large).gif";
        String imagePath= "./src/main/resources/7d052a584abf9ec79eb6785ffa6e5fa614233916-144p__37284.mp4";

        Metadata metadata = ImageMetadataReader.readMetadata(new File(imagePath));
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }
        }
    }

    @Test
    public void readFromJavaImageJpegAttributes(){
        //String file= "C:/Users/ramezani/git/test/test-main/test-file-attributes/src/main/resources/europeslostf.jpg";
        String file1= "./src/main/resources/flower-3140492_1280.jpg";
        String file2= "./src/main/resources/watercolor-flower-png-pack.png";


        readAndDisplayMetadata(file1);
        readAndDisplayMetadata(file2);
    }

    void readAndDisplayMetadata( String fileName ) {
        try {

            File file = new File( fileName );
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

            if (readers.hasNext()) {

                // pick the first available ImageReader
                ImageReader reader = readers.next();

                // attach source to the reader
                reader.setInput(iis, true);
                reader.read()

                // read metadata of first image
                IIOMetadata metadata = reader.getImageMetadata(0);

                String[] names = metadata.getMetadataFormatNames();
                int length = names.length;
                for (int i = 0; i < length; i++) {
                    System.out.println( "Format name: " + names[ i ] );
                    displayMetadata(metadata.getAsTree(names[i]));
                }
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    void displayMetadata(Node root) {
        displayMetadata(root, 0);
    }

    void indent(int level) {
        for (int i = 0; i < level; i++)
            System.out.print("    ");
    }

    void displayMetadata(Node node, int level) {
        // print open tag of element
        indent(level);
        System.out.print("<" + node.getNodeName());
        NamedNodeMap map = node.getAttributes();
        if (map != null) {

            // print attribute values
            int length = map.getLength();
            for (int i = 0; i < length; i++) {
                Node attr = map.item(i);
                System.out.print(" " + attr.getNodeName() +
                        "=\"" + attr.getNodeValue() + "\"");
            }
        }

        Node child = node.getFirstChild();
        if (child == null) {
            // no children, so close element and return
            System.out.println("/>");
            return;
        }

        // children, so close current tag
        System.out.println(">");
        while (child != null) {
            // print children recursively
            displayMetadata(child, level + 1);
            child = child.getNextSibling();
        }

        // print close tag of element
        indent(level);
        System.out.println("</" + node.getNodeName() + ">");
    }

}
