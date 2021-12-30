package bip.test.openimaj.video;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.edges.CannyEdgeDetector;
import org.openimaj.video.Video;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.xuggle.XuggleVideo;

import java.io.File;

/**
 * Created by ramezani on 11/5/2019.
 */
public class TestXuggleVideo {
    public static void main(String[] args) {
        Video<MBFImage> video;

        video = new XuggleVideo(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\video\\keyboardcat.flv"));
        //video = new VideoCapture(320, 240);

        VideoDisplay<MBFImage> display = VideoDisplay.createVideoDisplay(video);

        for (MBFImage mbfImage : video) {
            DisplayUtilities.displayName(mbfImage.process(new CannyEdgeDetector()), "videoFrames");
        }

    }
}
