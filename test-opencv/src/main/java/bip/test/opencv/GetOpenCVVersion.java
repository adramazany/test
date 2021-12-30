package bip.test.opencv;

import org.junit.Test;
import org.opencv.core.Core;

/**
 * Created by ramezani on 4/3/2019.
 */
public class GetOpenCVVersion {

    @Test
    public void printOpenCVVersion(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("OpenCV Core.getBuildInformation = " + Core.getBuildInformation());
        System.out.println("OpenCV Core.VERSION = " + Core.VERSION);
    }
}
