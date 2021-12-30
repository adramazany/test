package bip.test.openimaj.audio;

import org.openimaj.audio.AudioPlayer;
import org.openimaj.video.xuggle.XuggleAudio;

import java.io.File;

/**
 * Created by ramezani on 11/6/2019.
 */
public class TestXuggleAudio {


    public static void main(String[] args) {
        XuggleAudio xa = new XuggleAudio( new File( "C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\audio\\MolaJaanam.wma" ) );
        AudioPlayer.createAudioPlayer( xa ).run();
    }
}
