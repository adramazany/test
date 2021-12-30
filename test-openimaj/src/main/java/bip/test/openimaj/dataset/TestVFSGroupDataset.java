package bip.test.openimaj.dataset;

import org.openimaj.data.dataset.VFSGroupDataset;
import org.openimaj.data.dataset.VFSListDataset;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;

import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by ramezani on 11/5/2019.
 */
public class TestVFSGroupDataset {
    static String basePath = "C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\att_faces\\";

    public static void main(String[] args) throws Exception{

        VFSListDataset<FImage> images =
                new VFSListDataset<FImage>(basePath, ImageUtilities.FIMAGE_READER);
        System.out.println("images.size() = " + images.size());

        DisplayUtilities.display(images.getRandomInstance(), "A random image from the dataset");


        DisplayUtilities.display("My images", images);

        VFSGroupDataset<FImage> groupedFaces =
                new VFSGroupDataset<FImage>( basePath, ImageUtilities.FIMAGE_READER);
        System.out.println("groupedFaces.size() = " + groupedFaces.size());

        for (final Entry<String, VFSListDataset<FImage>> entry : groupedFaces.entrySet()) {
            DisplayUtilities.display(entry.getKey(), entry.getValue());
        }
    }
}
