package bip.test.openimaj.audio;

import org.openimaj.audio.SampleChunk;
import org.openimaj.audio.analysis.FourierTransform;
import org.openimaj.audio.samples.FloatSampleBuffer;
import org.openimaj.audio.samples.SampleBuffer;
import org.openimaj.video.xuggle.XuggleAudio;
import org.openimaj.vis.audio.AudioWaveform;

import java.io.File;
import java.util.Arrays;

/**
 * Created by ramezani on 11/6/2019.
 */
public class TestFourierTransform {


    public static void main(String[] args) throws Exception{

        final AudioWaveform vis = new AudioWaveform( 400, 400 );
        vis.showWindow( "Waveform" );

        vis.setAutoScale( false );
        //vis.setMaximum( Float.MAX_VALUE );

        final XuggleAudio xa = new XuggleAudio(
                new File( "C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\audio\\audiocheck.net_sweep20-20klin.wav" ) );


        SampleChunk sc = null;


        FourierTransform fft = new FourierTransform( xa );
        while( (sc = fft.nextSampleChunk()) != null )
        {
            vis.setData(sc.getSampleBuffer());
            float[][] fftData = fft.getMagnitudes();
            //vis.setData( cnvFloat2DoubleMatrix(fftData)[0] );
        }

    }
    static double[][] cnvFloat2DoubleMatrix(float[][] in){
        double[][] res = new double[in.length][in[0].length];
        for (int i = 0; i < in.length; i++) {
            float[] s = in[i];
            for (int j = 0; j < s.length; j++) {
                res[i][j] = s[j];
            }
        }
        return res;
    }
}
