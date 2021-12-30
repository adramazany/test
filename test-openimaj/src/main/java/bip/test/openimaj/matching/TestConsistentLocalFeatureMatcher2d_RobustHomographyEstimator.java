package bip.test.openimaj.matching;

import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.feature.local.matcher.FastBasicKeypointMatcher;
import org.openimaj.feature.local.matcher.LocalFeatureMatcher;
import org.openimaj.feature.local.matcher.MatchingUtilities;
import org.openimaj.feature.local.matcher.consistent.ConsistentLocalFeatureMatcher2d;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.feature.local.engine.DoGSIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import org.openimaj.math.geometry.transforms.HomographyRefinement;
import org.openimaj.math.geometry.transforms.estimation.RobustAffineTransformEstimator;
import org.openimaj.math.geometry.transforms.estimation.RobustHomographyEstimator;
import org.openimaj.math.model.fit.RANSAC;

import java.io.File;


/**
 * Created by ramezani on 11/5/2019.
 * very bad
 */
public class TestConsistentLocalFeatureMatcher2d_RobustHomographyEstimator {
    static String basePath = "C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\att_faces\\";

    
    public static void main(String[] args)throws Exception {
        MBFImage query = ImageUtilities.readMBF(new File(basePath+"s1/1.pgm"));
        MBFImage target = ImageUtilities.readMBF(new File(basePath+"s1/10.pgm"));
        //MBFImage target = ImageUtilities.readMBF(new File(basePath+"s3/10.pgm"));

        DoGSIFTEngine engine = new DoGSIFTEngine();
        LocalFeatureList<Keypoint> queryKeypoints = engine.findFeatures(query.flatten());
        System.out.println("queryKeypoints = " + queryKeypoints.size()+ " - " + queryKeypoints);
        LocalFeatureList<Keypoint> targetKeypoints = engine.findFeatures(target.flatten());
        System.out.println("targetKeypoints = " + targetKeypoints.size()+ " - " + targetKeypoints);

        RobustHomographyEstimator modelFitter = new RobustHomographyEstimator(5.0,
                HomographyRefinement.SINGLE_IMAGE_TRANSFER);
        LocalFeatureMatcher<Keypoint> matcher = new ConsistentLocalFeatureMatcher2d<Keypoint>(
                new FastBasicKeypointMatcher<Keypoint>(8), modelFitter);
        matcher.setModelFeatures(queryKeypoints);
        boolean findMatches=matcher.findMatches(targetKeypoints);
        System.out.println("findMatches = " + findMatches);


        MBFImage consistentMatches = MatchingUtilities.drawMatches(query, target, matcher.getMatches(),
                RGBColour.RED);
        DisplayUtilities.display(consistentMatches);


    }

}
