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
public class TestXuggleVideoListener {
    public static void main(String[] args) {
        Video<MBFImage> video;

        video = new XuggleVideo(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\video\\keyboardcat.flv"));

        VideoDisplay<MBFImage> display = VideoDisplay.createVideoDisplay(video);

        display.addVideoListener(
                new VideoDisplayListener<MBFImage>() {
                    public void beforeUpdate(MBFImage frame) {
                        frame.processInplace(new CannyEdgeDetector());
                    }
                    public void afterUpdate(VideoDisplay<MBFImage> display) {
                    }
                });
    }
}
