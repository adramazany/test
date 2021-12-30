package bip.test.file.attributes;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.DefaultParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp4.MP4Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by ramezani on 3/24/2019.
 */
public class TestDocumentAttributes {

    @Test
    public void readFromTika() throws IOException, TikaException, SAXException {
        //String docFile="./src/main/resources/injection.docx";;
        //String docFile="./src/main/resources/RFP.doc";;
        String docFile="./src/main/resources/7d052a584abf9ec79eb6785ffa6e5fa614233916-144p__37284.mp4";;
        File file = new File(docFile);
        FileInputStream inputstream = new FileInputStream(file);

        //tika
        Tika tika = new Tika();

        //read  mime-type
        String mime = tika.detect(file);
        MediaType mt = MediaType.parse(mime);
        System.out.println(String.format("mime = %s ,MediaType=%s , basetype=%s , parameters=%s",mime,mt.toString(),mt.getBaseType(),mt.getParameters()));



        //Parser method parameters
        //Parser parser = new DefaultParser();
        Parser parser = new AutoDetectParser();
        //Parser parser = new MP4Parser();

        BodyContentHandler handler = new BodyContentHandler();
        org.apache.tika.metadata.Metadata metadata = new org.apache.tika.metadata.Metadata();
        ParseContext context = new ParseContext();

        parser.parse(inputstream, handler, metadata, context);
        System.out.println(handler.toString());

        //getting the list of all meta data elements
        //metadata.

        StringBuffer buf = new StringBuffer();
        String[] names = metadata.names();
        Arrays.sort(names);

        for(int i = 0; i < names.length; ++i) {
            String[] values = metadata.getValues(names[i]);

            for(int j = 0; j < values.length; ++j) {
                buf.append(names[i]).append("=").append(values[j]);
                buf.append("\r\n");
            }
        }

        System.out.println(buf);
        System.out.println();
        System.out.println("metadata.toString() = " + metadata.toString());
    }

    @Test
    public void translateEn2FA(){
        Tika tika = new Tika();

        String text = "This is a test.";
        String translate = tika.translate(text,"FA");
        System.out.println();
    }

    @Test
    public void testFolderLastModifiedDate(){
        File folder = new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-file-attributes\\src\\main\\java");
        System.out.println("folder = " + new Date(( folder.lastModified())));
    }
}
