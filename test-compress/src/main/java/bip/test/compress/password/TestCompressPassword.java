package bip.test.compress.password;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.extract.ExtractArchive;
import net.lingala.zip4j.core.ZipFile;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import org.apache.commons.compress.PasswordRequiredException;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipException;

/**
 * Created by ramezani on 3/19/2019.
 */

public class TestCompressPassword {
    static final Logger logger = Logger.getLogger(TestCompressPassword.class);

    String zip_pass_no="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-nopass.zip";
    String zip_pass_123="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\test-pass-123.zip";
    String rar_pass_no="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-nopass.rar";
    String rar_pass_123="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-pass-123.rar";
    String txt="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\nozip.txt";
    String z7_pass_no="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-nopass.7z";
    String z7_pass_123="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-pass-123.7z";


    @Test
    public void extractTestNoPass() throws IOException {
        String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\test-nopass.zip";
        String destinationDir="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\out";
        SevenZipUtil.unzipDir(sourceZipFile,destinationDir);
    }

    @Test
    public  void extractTestPass123() throws FileNotFoundException, SevenZipException {
        String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\test-pass-123.zip";
        String destinationDir="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\out";
        String password="123";
        try {
            SevenZipUtil.unzipDir(sourceZipFile,destinationDir);
        }catch(Exception e){
            logger.error("error extract compress file : "+sourceZipFile);
            if(e instanceof ZipException && e.getMessage().equalsIgnoreCase("encrypted ZIP entry not supported")){
                try {
                    SevenZipUtil.unzipDirWithPassword(sourceZipFile,destinationDir,password);
                }catch(SevenZipException e2){
                    logger.error(String.format("error extract compress file : %s with password : %s ",sourceZipFile,password));
                    if(e2.getMessage().equalsIgnoreCase("Error extracting item: DATAERROR")){
                        throw new SevenZipException("please try another password!");
                    }
                }
            }
        }
        //
    }

    @Test
    public  void extractTmpNoPass() throws IOException {
        String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-nopass.zip";
        String destinationDir="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\out";
        SevenZipUtil.unzipDir(sourceZipFile,destinationDir);
    }

    @Test
    public void extractTmpPass123() throws IOException {
        String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-pass-123.zip";
        String destinationDir="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\out";
        String[] passwords=new String[]{"1234","12345","123"};
        String correctPassword=null;
        try {
            SevenZipUtil.unzipDir(sourceZipFile,destinationDir);
        }catch(PasswordRequiredException e){
            logger.error(e.getMessage());
            int incorrectPasswordCount=0;
            for (String password:passwords) {
                try {
                    SevenZipUtil.unzipDirWithPassword(sourceZipFile, destinationDir, password);
                    correctPassword = password;
                    logger.info(String.format("%s file uncompressed by password:%s",sourceZipFile,correctPassword));
                    break;
                }catch (PasswordIncorrectZipException e2) {
                    incorrectPasswordCount++;
                    logger.error(String.format("password %s is incorrect for %s zip file!",password,sourceZipFile));
                }
            }
            if(incorrectPasswordCount==passwords.length) {
                throw new PasswordIncorrectZipException(String.format("provided passwords %s is incorrect for %s zip file!", Arrays.toString(passwords),sourceZipFile));
            }
        }
    }

    @Test
    public void extractTmpNoPassRar() throws IOException {
        String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-nopass.rar";
        String destinationDir="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\out";
        SevenZipUtil.unzip(sourceZipFile,destinationDir,new String[]{null});
    }

    @Test
    public void extractTmpPass123Rar() throws IOException {
        String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-pass-123.rar";
        String destinationDir="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\out";
        String[] passwords=new String[]{"1234","12345","123"};
        SevenZipUtil.unzip(sourceZipFile,destinationDir,passwords);
    }

    @Test
    public void extractTmpNoPass7z() throws IOException {
        String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-nopass.7z";
        String destinationDir="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\out";
        SevenZipUtil.unzip(sourceZipFile,destinationDir,new String[]{null});
    }

    @Test
    public void extractTmpPass1237z() throws IOException {
        String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-pass-123.7z";
        String destinationDir="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\out";
        String[] passwords=new String[]{};//new String[]{"1234","12345","123"};
        SevenZipUtil.unzip(sourceZipFile,destinationDir,passwords);
    }

    @Test
    public void extractNozip() throws IOException {
        String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\nozip.txt";
        String destinationDir="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\out";
        SevenZipUtil.unzip(sourceZipFile,destinationDir,new String[]{null});
    }

    @Test
    public void extractByBipCommonCompress() throws IOException {
        String sourceZipFile="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\src\\main\\resources\\tmp-pass-123.rar";
        String destinationDir="C:\\Users\\ramezani\\git\\test\\test-main\\test-compress\\out";
        String[] passwords=new String[]{"1234","12345","123"};
        bip.common.compress.SevenZipUtil.unzip(sourceZipFile,destinationDir,passwords);
    }



}
