import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by ramezani on 9/26/2019.
 */
public class JDK8WavemakerProblems {
    public static void main(String[] args) {
        arraysToString();
    }
    public static void problemPattern(){
        Pattern pt = Pattern.compile("[^a-zA-Z0-9\u0600-\u06FF.\\s]");
    }
    public static void problemNioFiles() throws IOException {
        File path = File.createTempFile("a","");
        if(path.exists())path.delete();
        path.mkdirs();
        path.getAbsolutePath();
        path.delete();
    }
    public static void arraysToString(){
        String[] zipPasswords=new String[]{"aaaaa","bbbbb","cccc"};
        System.out.println("zipPassword="+ Arrays.toString(zipPasswords));

    }
}
