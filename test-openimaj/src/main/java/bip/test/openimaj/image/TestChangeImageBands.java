package bip.test.openimaj.image;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

import java.io.File;

/**
 * Created by ramezani on 11/6/2019.
 */
public class TestChangeImageBands {
    public static void main(String[] args) throws Exception{
        MBFImage image = ImageUtilities.readMBF(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\image\\sinaface.jpg"));

        System.out.println(image.colourSpace);

        MBFImage clone = image.clone();
        for (int y=0; y<image.getHeight(); y++) {
            for(int x=0; x<image.getWidth(); x++) {
                clone.getBand(1).pixels[y][x] = 0;
                clone.getBand(2).pixels[y][x] = 0;
            }
        }
        DisplayUtilities.display(clone);
    }
}
