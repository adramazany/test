package bip.test.openimaj.video;

import org.openimaj.image.FImage;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.colour.Transforms;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.FaceDetector;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.FacialKeypoint;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.math.geometry.point.Point2d;
import org.openimaj.math.geometry.shape.Circle;
import org.openimaj.math.geometry.shape.Ellipse;
import org.openimaj.video.Video;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.VideoCapture;

import java.util.List;

/**
 * Created by ramezani on 11/5/2019.
 */
public class TestXuggleVideoCaptureFKEFaceDetector {
    public static void main(String[] args) throws Exception{
        Video<MBFImage> video;

        //video = new XuggleVideo(new File("C:\\Users\\ramezani\\git\\test\\test-main\\test-openimaj\\src\\main\\resources\\video\\keyboardcat.flv"));
        video = new VideoCapture(320, 240);

        VideoDisplay<MBFImage> display = VideoDisplay.createVideoDisplay(video);

        final FaceDetector<KEDetectedFace,FImage> fd = new FKEFaceDetector();
        display.addVideoListener(
                new VideoDisplayListener<MBFImage>() {
                    public void beforeUpdate(MBFImage frame) {
                        //frame.processInplace(new CannyEdgeDetector());
                        List<KEDetectedFace> faces = fd.detectFaces( Transforms.calculateIntensity( frame ) );
                        for( KEDetectedFace face : faces ) {
                            frame.drawShape(face.getBounds(), RGBColour.RED);


                            for(FacialKeypoint k:face.getKeypoints()){
                                Point2d point2d = k.position.clone();
                                point2d.translate(face.getBounds().getTopLeft());
                                frame.drawShape(new Circle(point2d,5),RGBColour.GREEN);
                            }

                            //frame.drawShape();
/*
                            frame.drawShapeFilled(new Ellipse(200f, 200f, 10f, 10f, 0f), RGBColour.WHITE);
                            frame.drawShapeFilled(new Ellipse(19f, 190f, 12f, 12f, 0f), RGBColour.WHITE);
                            frame.drawShapeFilled(new Ellipse(180f, 180f, 15f, 15f, 0f), RGBColour.WHITE);
                            frame.drawShapeFilled(new Ellipse(100f, 100f, 30f, 30f, 0f), RGBColour.WHITE);
                            frame.drawText("OpenIMAJ is", 100, 100, HersheyFont.ASTROLOGY, 20, RGBColour.BLACK);
                            frame.drawText("Awesome", 100, 130, HersheyFont.ASTROLOGY, 20, RGBColour.BLACK);
*/
                        }
                    }
                    public void afterUpdate(VideoDisplay<MBFImage> display) {
                    }
                });
    }
}
