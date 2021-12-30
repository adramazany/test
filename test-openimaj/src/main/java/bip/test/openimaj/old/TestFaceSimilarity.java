package bip.test.openimaj.old;

import org.openimaj.feature.DoubleFVComparison;
import org.openimaj.image.processing.face.alignment.RotateScaleAligner;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.image.processing.face.recognition.EigenFaceRecogniser;
import org.openimaj.image.processing.face.recognition.FaceRecognitionEngine;
import org.openimaj.image.processing.face.similarity.FaceSimilarityEngine;

/**
 * Created by ramezani on 11/4/2019.
 */
public class TestFaceSimilarity {

    public static void main(String[] args) {
        //FaceSimilarityEngine<>
    }

    //https://stackoverflow.com/questions/15496269/how-to-identify-new-faces-with-openimaj
/*
    void doImageProcessing() {

        // Create face stuff
        FKEFaceDetector faceDetector = new FKEFaceDetector(new HaarCascadeDetector());
        EigenFaceRecogniser<KEDetectedFace, Person> faceRecognizer = EigenFaceRecogniser.create(512, new RotateScaleAligner(), 512, DoubleFVComparison.CORRELATION, Float.MAX_VALUE);
        FaceRecognitionEngine<KEDetectedFace, Extractor<KEDetectedFace>, Person> faceEngine = FaceRecognitionEngine.create(faceDetector, faceRecognizer);

        // Start loop
        while (true) {

            // Get next frame
            byte[] imgData = nextProcessingData;
            nextProcessingData = null;

            // Decode image
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imgData));

            // Detect faces
            FImage fimg = ImageUtilities.createFImage(img);
            List<KEDetectedFace> faces = faceEngine.getDetector().detectFaces(fimg);

            // Go through detected faces
            for (KEDetectedFace face : faces) {

                // Find existing person for this face
                Person person = null;
                try {

                    List<IndependentPair<KEDetectedFace, ScoredAnnotation<Person>>> rfaces = faceEngine.recogniseBest(face.getFacePatch());
                    ScoredAnnotation<Person> score = rfaces.get(0).getSecondObject();
                    if (score != null)
                        person = score.annotation;

                } catch (Exception e) {
                }

                // If not found, create
                if (person == null) {

                    // Create person
                    person = new Person();
                    System.out.println("Identified new person: " + person.getIdentifier());

                    // Train engine to recognize this new person
                    faceEngine.train(person, face.getFacePatch());

                } else {

                    // This person has been detected before
                    System.out.println("Identified existing person: " + person.getIdentifier());

                }

            }

        }

    }
*/


    //https://github.com/openimaj/openimaj/blob/master/demos/examples/src/main/java/org/openimaj/examples/image/faces/FaceSimilarity.java
}
