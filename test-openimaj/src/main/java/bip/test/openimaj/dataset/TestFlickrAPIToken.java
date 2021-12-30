package bip.test.openimaj.dataset;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.dataset.FlickrImageDataset;
import org.openimaj.util.api.auth.DefaultTokenFactory;
import org.openimaj.util.api.auth.common.FlickrAPIToken;

/**
 * Created by ramezani on 11/5/2019.
 */
public class TestFlickrAPIToken {

    public static void main(String[] args) throws Exception{
        FlickrAPIToken flickrToken = DefaultTokenFactory.get(FlickrAPIToken.class);
        FlickrImageDataset<FImage> cats =
                FlickrImageDataset.create(ImageUtilities.FIMAGE_READER, flickrToken, "cat", 10);
        DisplayUtilities.display("Cats", cats);
    }
}
