package bip.test.openimaj.audio;

import org.openimaj.audio.AudioPlayer;
import org.openimaj.audio.SampleChunk;
import org.openimaj.video.xuggle.XuggleAudio;
import org.openimaj.vis.audio.AudioWaveform;

import java.io.File;
import java.net.URL;

/**
 * Created by ramezani on 11/6/2019.
 */
public class TestAudioWaveform {


    public static void main(String[] args) throws Exception{

        final AudioWaveform vis = new AudioWaveform( 400, 400 );
        vis.showWindow( "Waveform" );
        final XuggleAudio xa = new XuggleAudio(
                new File( "C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\audio\\audiocheck.net_sweep20-20klin.wav" ) );
        SampleChunk sc = null;
        while( (sc = xa.nextSampleChunk()) != null )
            vis.setData( sc.getSampleBuffer() );


    }
}
