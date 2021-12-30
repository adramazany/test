package bip.test.openimaj.video;

import org.openimaj.image.FImage;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.colour.Transforms;
import org.openimaj.image.processing.edges.CannyEdgeDetector;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.FaceDetector;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import org.openimaj.video.Video;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.VideoCapture;

import java.util.List;

/**
 * Created by ramezani on 11/5/2019.
 */
public class TestXuggleVideoCaptureFaceDetector {
    public static void main(String[] args) throws Exception{
        Video<MBFImage> video;

        //video = new XuggleVideo(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\video\\keyboardcat.flv"));
        video = new VideoCapture(320, 240);

        VideoDisplay<MBFImage> display = VideoDisplay.createVideoDisplay(video);

        final FaceDetector<DetectedFace,FImage> fd = new HaarCascadeDetector(40);
        display.addVideoListener(
                new VideoDisplayListener<MBFImage>() {
                    public void beforeUpdate(MBFImage frame) {

                        //frame.processInplace(new CannyEdgeDetector());
                        List<DetectedFace> faces = fd.detectFaces( Transforms.calculateIntensity(frame));
                        for( DetectedFace face : faces ) {
                            frame.drawShape(face.getBounds(), RGBColour.RED);
                        }
                    }
                    public void afterUpdate(VideoDisplay<MBFImage> display) {
                    }
                });
    }
}
