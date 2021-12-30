package bip.test.hash;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by ramezani on 3/14/2019.
 */
public class TestHash {

    @Test
    public void testHashBase64SHA256() throws IOException {
        //DigestUtils.getSha3_256Digest()
        System.out.println("sample1.txt="+ Base64.encodeBase64String(DigestUtils.digest(DigestUtils.getDigest("SHA-512"), TestHash.class.getResourceAsStream("/sample1.txt"))) );
        System.out.println("sample2.pdf="+ Base64.encodeBase64String( DigestUtils.digest(DigestUtils.getDigest("SHA-512"),TestHash.class.getResourceAsStream("/sample2.pdf"))) );
        System.out.println("sample3.zip="+ Base64.encodeBase64String( DigestUtils.digest(DigestUtils.getDigest("SHA-512"),TestHash.class.getResourceAsStream("/sample3.zip"))) );
        System.out.println("sample4.exe="+ Base64.encodeBase64String( DigestUtils.digest(DigestUtils.getDigest("SHA-512"),TestHash.class.getResourceAsStream("/sample4.exe"))) );
    }

    @Test
    public void testHashHexSHA256() throws IOException {
        System.out.println("sample1.txt="+ Hex.encodeHexString(DigestUtils.digest(DigestUtils.getDigest("SHA-256"), TestHash.class.getResourceAsStream("/sample1.txt"))) );
        System.out.println("sample2.pdf="+ Hex.encodeHexString( DigestUtils.digest(DigestUtils.getDigest("SHA-256"),TestHash.class.getResourceAsStream("/sample2.pdf"))) );
        System.out.println("sample3.zip="+ Hex.encodeHexString( DigestUtils.digest(DigestUtils.getDigest("SHA-256"),TestHash.class.getResourceAsStream("/sample3.zip"))) );
        System.out.println("sample4.exe="+ Hex.encodeHexString( DigestUtils.digest(DigestUtils.getDigest("SHA-256"),TestHash.class.getResourceAsStream("/sample4.exe"))) );
    }

    @Test
    public void testCompressAndIncreaseSimilarityByRemoveUnmatching(){
        Pattern p = Pattern.compile("[^(a-zA-Z0-9\\@\\+\\*\\-\\/\\.\\_\\=ا-یي٠-٩۰-۹)]");
        String text = "this123 is a test567\n" +
                "این یک ٩۰مثال است\n" +
                "ad@y_a-b.com\n" +
                "این یک مثال٠۹ است\n" +
                "321THIS IS 987A TEST\n" +
                "1*2+3-5/4.0=10";
        String compressed = p.matcher(text).replaceAll("");
        System.out.println("compressed = " + compressed);

    }
}
