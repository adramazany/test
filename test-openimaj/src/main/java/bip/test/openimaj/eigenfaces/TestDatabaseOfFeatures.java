package bip.test.openimaj.eigenfaces;

import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.data.dataset.VFSGroupDataset;
import org.openimaj.experiment.dataset.split.GroupedRandomSplitter;
import org.openimaj.experiment.dataset.util.DatasetAdaptors;
import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.DoubleFVComparison;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.model.EigenImages;
import org.openimaj.image.processing.resize.FixedResizeProcessor;
import org.openimaj.image.processing.resize.ResizeProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ramezani on 11/6/2019.
 */
public class TestDatabaseOfFeatures {

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

/*
        List<FImage> eigenFaces = new ArrayList<FImage>();
        for (int i = 0; i < 12; i++) {
            eigenFaces.add(eigen.visualisePC(i));
        }

        DisplayUtilities.display("EigenFaces", eigenFaces);
*/


        Map<String, DoubleFV[]> features = new HashMap<String, DoubleFV[]>();
        for (final String person : training.getGroups()) {
            final DoubleFV[] fvs = new DoubleFV[nTraining];
            for (int i = 0; i < nTraining; i++) {
                final FImage face = training.get(person).get(i);
                fvs[i] = eigen.extractFeature(face);
            }
            features.put(person, fvs);
        }



        List<FImage> faces=new ArrayList<>();

        double correct = 0, incorrect = 0;
        for (String truePerson : testing.getGroups()) {
            for (FImage face : testing.get(truePerson)) {
                DoubleFV testFeature = eigen.extractFeature(face);
                String bestPerson = null;
                //double minDistance = Double.MAX_VALUE;
                double minDistance = 10;
                for (final String person : features.keySet()) {
                    for (final DoubleFV fv : features.get(person)) {

                        double distance = fv.compare(testFeature, DoubleFVComparison.EUCLIDEAN);
                        if (distance < minDistance) {
                            minDistance = distance;
                            bestPerson = person;
                        }
                    }
                }
                System.out.println("Actual: " + truePerson + "\tguess: " + bestPerson+"\tdistance:"+minDistance);
                //System.out.println("Actual: " + testing.get(truePerson) + "\tguess: " + bestPerson);
                if (truePerson.equals(bestPerson)) {
                    correct++;
                    faces.add(face);
                    faces.add(training.get(bestPerson).getInstance(0));
                }else
                    incorrect++;
            }
        }
        System.out.println("Accuracy: " + (correct / (correct + incorrect)));
        System.out.println(String.format("training:%d , testing:%d , correct:%d , incorrect:%d",training.size(), testing.size(), (int)correct, (int)incorrect));

        //DisplayUtilities.display("",faces);

    }
    /******************************************************
    minDistance=100
    Accuracy: 0.92
    training:40 , testing:40 , correct:184 , incorrect:16
    ----------------------------------
     minDistance=10
     Accuracy: 0.835
     training:40 , testing:40 , correct:167 , incorrect:33
     *******************************************************/

}
