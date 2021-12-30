package bip.test.openimaj.eigenfaces;

import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.data.dataset.VFSGroupDataset;
import org.openimaj.experiment.dataset.split.GroupedRandomSplitter;
import org.openimaj.experiment.dataset.util.DatasetAdaptors;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.model.EigenImages;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramezani on 11/6/2019.
 */
public class TestEigenfaces {

    public static void main(String[] args)throws Exception {

        VFSGroupDataset<FImage> dataset =
                new VFSGroupDataset<FImage>("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\att_faces\\"
                        , ImageUtilities.FIMAGE_READER);


        int nTraining = 5;
        int nTesting = 5;
        GroupedRandomSplitter<String, FImage> splits =
                new GroupedRandomSplitter<String, FImage>(dataset, nTraining, 0, nTesting);
        GroupedDataset<String, ListDataset<FImage>, FImage> training = splits.getTrainingDataset();
        GroupedDataset<String, ListDataset<FImage>, FImage> testing = splits.getTestDataset();


        List<FImage> basisImages = DatasetAdaptors.asList(training);
        int nEigenvectors = 100;


        EigenImages eigen = new EigenImages(nEigenvectors);
        eigen.train(basisImages);


        List<FImage> eigenFaces = new ArrayList<FImage>();
        for (int i = 0; i < 12; i++) {
            eigenFaces.add(eigen.visualisePC(i));
        }

        DisplayUtilities.display("EigenFaces", eigenFaces);
    }
}
