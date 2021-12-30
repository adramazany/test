package bip.test.file.attributes;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.IOUtils;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by ramezani on 5/30/2019.
 */
public class ExtractExtRenamedFilesAttributes {

    @Test
    public void extractExtRenamedFilesAttributes() throws TikaException, SAXException, IOException {
        //String docFile="./src/main/resources/7d052a584abf9ec79eb6785ffa6e5fa614233916-144p__37284.mp4";;
        String docFile="./src/main/resources/docx.xyz";
        //String docFile="./src/main/resources/jpg.xyz";
        //String docFile="./src/main/resources/rar.xyz";
        //String docFile="./src/main/resources/txt.xyz";
        //String docFile="./src/main/resources/zip.xyz";
        File file = new File(docFile);
        FileInputStream inputstream = new FileInputStream(file);

        //tika
        Tika tika = new Tika();

        //read  mime-type
        String mime = tika.detect(file);
        MediaType mt = MediaType.parse(mime);


/*
        ByteArrayInputStream ba_in = new ByteArrayInputStream(IOUtils.toByteArray(new FileInputStream(file)));
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        parser.parse(ba_in, handler, metadata, context);
        String contentText = handler.toString();
*/

        System.out.println(String.format("mime = %s ,MediaType=%s , basetype=%s , parameters=%s",mime,mt.toString(),mt.getBaseType(),mt.getParameters()));



        //Parser method parameters
        //Parser parser = new DefaultParser();
        Parser parser = new AutoDetectParser();
        //Parser parser = new MP4Parser();

        BodyContentHandler handler = new BodyContentHandler();
        org.apache.tika.metadata.Metadata metadata = new org.apache.tika.metadata.Metadata();
        ParseContext context = new ParseContext();

        parser.parse(inputstream, handler, metadata, context);
        System.out.println("contentText="+handler.toString());

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
}
