package bip.test.openimaj.image;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.math.geometry.shape.Ellipse;
import org.openimaj.ml.clustering.FloatCentroidsResult;
import org.openimaj.ml.clustering.assignment.HardAssigner;
import org.openimaj.ml.clustering.kmeans.FloatKMeans;

import java.io.File;
import java.util.Arrays;

/**
 * Created by ramezani on 11/6/2019.
 */
public class TestFloatKMeans {
    public static void main(String[] args) throws Exception{
        MBFImage input = ImageUtilities.readMBF(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\image\\sinaface.jpg"));

        input = ColourSpace.convert(input, ColourSpace.CIE_Lab);

        FloatKMeans cluster = FloatKMeans.createExact(2);

        float[][] imageData = input.getPixelVectorNative(new float[input.getWidth() * input.getHeight()][3]);

        FloatCentroidsResult result = cluster.cluster(imageData);

        float[][] centroids = result.centroids;
        for (float[] fs : centroids) {
            System.out.println(Arrays.toString(fs));
        }


        HardAssigner<float[],?,?> assigner = result.defaultHardAssigner();
        for (int y=0; y<input.getHeight(); y++) {
            for (int x=0; x<input.getWidth(); x++) {
                float[] pixel = input.getPixelNative(x, y);
                int centroid = assigner.assign(pixel);
                input.setPixelNative(x, y, centroids[centroid]);
            }
        }

        input = ColourSpace.convert(input, ColourSpace.RGB);
        DisplayUtilities.display(input);

    }
}
