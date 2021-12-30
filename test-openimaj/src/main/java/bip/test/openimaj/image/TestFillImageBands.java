package bip.test.openimaj.image;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

import java.io.File;

/**
 * Created by ramezani on 11/6/2019.
 */
public class TestFillImageBands {
    public static void main(String[] args) throws Exception{
        MBFImage image = ImageUtilities.readMBF(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\image\\sinaface.jpg"));

        System.out.println(image.colourSpace);

        MBFImage clone = image.clone();
        clone.getBand(1).fill(0f);
        clone.getBand(2).fill(0f);
        DisplayUtilities.display(clone);
    }
}
