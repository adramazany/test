package bip.test.openimaj.image;

import org.openimaj.feature.DoubleFVComparison;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.connectedcomponent.GreyscaleConnectedComponentLabeler;
import org.openimaj.image.pixel.ConnectedComponent;
import org.openimaj.image.pixel.statistics.HistogramModel;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.math.statistics.distribution.MultidimensionalHistogram;
import org.openimaj.ml.clustering.FloatCentroidsResult;
import org.openimaj.ml.clustering.assignment.HardAssigner;
import org.openimaj.ml.clustering.kmeans.FloatKMeans;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ramezani on 11/6/2019.
 */
public class TestMultidimensionalHistogram {
    public static void main(String[] args) throws Exception{
        MBFImage image = ImageUtilities.readMBF(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\image\\sinaface.jpg"));

        //MultidimensionalHistogram histogram = new MultidimensionalHistogram( 4, 4, 4 );
/*
        HistogramModel model = new HistogramModel( 4, 4, 4 );
        model.estimateModel( image );
        MultidimensionalHistogram histogram = model.histogram;
*/

        File[] imageURLs = new File[] {
                new File( "C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\image\\hist1.jpg" ),
                new File( "C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\image\\hist2.jpg" ),
                new File( "C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\image\\hist3.jpg" )
        };
        List<MultidimensionalHistogram> histograms = new ArrayList<MultidimensionalHistogram>();
        HistogramModel model = new HistogramModel(4, 4, 4);
        for( File u : imageURLs ) {
            model.estimateModel(ImageUtilities.readMBF(u));
            histograms.add( model.histogram.clone() );
        }

        //double distanceScore = histogram1.compare( histogram2, DoubleFVComparison.EUCLIDEAN );

        for( int i = 0; i < histograms.size(); i++ ) {
            for( int j = i; j < histograms.size(); j++ ) {
                double distance = histograms.get(i).compare( histograms.get(j), DoubleFVComparison.EUCLIDEAN );
                System.out.println(String.format("hist%d compare hist%d then distance = %f" ,i+1,j+1, distance));
            }
        }

        //DisplayUtilities.display(input);

    }
}
