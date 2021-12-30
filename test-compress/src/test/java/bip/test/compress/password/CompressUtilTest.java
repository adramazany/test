package bip.test.compress.password;

import bip.common.util.BIPUtil;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by ramezani on 6/5/2019.
 */
public class CompressUtilTest {
    String[] passwords=new String[]{"1234","12345","123"};

    @org.junit.Test
    public void extract() throws Exception {
        //String srcZipPath = "e:\\email\\adramazany@yahoo.com.rar";
        String srcZipPath = "C:\\java\\workspace-maven\\bip-common\\bip-common-compress\\src\\test\\resources\\test-pass-123.rar";
        File srcZip = new File(srcZipPath);
        String filenameNoExt = BIPUtil.getFileNameNoPathNoExt(srcZipPath);
        File destFolder = new File(srcZip.getParent(),filenameNoExt);
        destFolder.mkdirs();

        CompressUtil.extract(srcZip,destFolder,passwords);
        System.out.println("succeed.");
    }

}