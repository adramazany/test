package bip.test.compress.password;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import net.lingala.zip4j.core.ZipFile;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import net.sf.sevenzipjbinding.util.ByteArrayStream;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by ramezani on 6/4/2019.
 */
public class CheckIsEncryptedCompress {
    @Test
    public void isEncryptByZipp4j() throws net.lingala.zip4j.exception.ZipException {
        //String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-nopass.zip";
        String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\test-pass-123.zip";
        ZipFile zipFile = new ZipFile(sourceZipFile);

        System.out.println(sourceZipFile+":isEncrypted="+zipFile.isEncrypted());
    }

    @Test
    public void isEncryptByJUnRar() throws IOException, RarException {
        //String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-nopass.rar";
        String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-pass-123.rar";
        Archive arch = new Archive(new File(sourceZipFile));

        System.out.println(sourceZipFile+":isEncrypted="+arch.nextFileHeader().isEncrypted());
    }


    @Test
    public void isEncryptBySevenZipUtil() throws IOException, RarException {
        //String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-nopass.zip";
        String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-pass-123.zip";
        //String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-nopass.rar";
        //String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-pass-123.rar";
        //String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\nozip.txt";
        //String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-nopass.7z";
        //String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-pass-123.7z";

        RandomAccessFile randomAccessFile= new RandomAccessFile(sourceZipFile, "r");
        IInArchive inArchive = SevenZip.openInArchive(null, // autodetect archive type
                new RandomAccessFileInStream(randomAccessFile));
        ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();

        int counter=0;
        while(counter<simpleInArchive.getNumberOfItems() && simpleInArchive.getArchiveItem(counter).isFolder()){
            counter++;
        }
        System.out.println(sourceZipFile+":isEncrypted="+inArchive.getSimpleInterface().getArchiveItem(counter).isEncrypted());
    }


}
