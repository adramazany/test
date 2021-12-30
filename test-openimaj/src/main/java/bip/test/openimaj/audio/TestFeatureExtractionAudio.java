package bip.test.openimaj.audio;

import org.openimaj.audio.AudioFormat;
import org.openimaj.audio.JavaSoundAudioGrabber;
import org.openimaj.audio.SampleChunk;
import org.openimaj.audio.analysis.FourierTransform;
import org.openimaj.audio.features.MFCC;
import org.openimaj.audio.processor.FixedSizeSampleAudioProcessor;
import org.openimaj.video.xuggle.XuggleAudio;
import org.openimaj.vis.audio.AudioWaveform;

import java.io.File;

/**
 * Created by ramezani on 11/6/2019.
 */
public class TestFeatureExtractionAudio {


    public static void main(String[] args) throws Exception{

        final AudioWaveform vis = new AudioWaveform( 400, 400 );
        vis.showWindow( "Waveform" );

        final XuggleAudio xa = new XuggleAudio(
                new File( "C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\audio\\audiocheck.net_sweep20-20klin.wav" ) );

        SampleChunk sc=null;

        MFCC mfcc = new MFCC( xa );
        while( (sc = mfcc.nextSampleChunk()) != null )
        {
            double[][] mfccs = mfcc.getLastCalculatedFeatureWithoutFirst();//getLastGeneratedFeature();
            vis.setData( mfccs[0] );
        }

        JavaSoundAudioGrabber jsag = new JavaSoundAudioGrabber( new AudioFormat( 16, 44.1, 1 ) );
        FixedSizeSampleAudioProcessor fssap = new FixedSizeSampleAudioProcessor( jsag, 441*3, 441 );

    }
}
