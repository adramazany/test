package bip.test.opencv;

/**
 * Created by ramezani on 4/9/2019.
 */

import static  com.googlecode.javacv.cpp.opencv_highgui.*;
import static  com.googlecode.javacv.cpp.opencv_core.*;
import static  com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_contrib.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import com.googlecode.javacv.cpp.opencv_imgproc;
import com.googlecode.javacv.cpp.opencv_contrib.FaceRecognizer;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_core.MatVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
*/

public  class PersonRecognizer {
    /*
    Logger Log = LoggerFactory.getLogger(PersonRecognizer.class);

    public final static int MAXIMG = 100;
    FaceRecognizer faceRecognizer;
    FaceRecognizerPtr faceRecognizerPtr;
    String mPath;
    int count=0;
    HashMap<String,Integer> labelsFile;

    static  final int WIDTH= 128;
    static  final int HEIGHT= 128;;
    private int mProb=999;


    PersonRecognizer(String path)
    {
        faceRecognizerPtr =  com.googlecode.javacv.cpp.opencv_contrib.createLBPHFaceRecognizer(2,8,8,8);//,200);
        faceRecognizer = faceRecognizerPtr.get();
        // path=Environment.getExternalStorageDirectory()+"/facerecog/faces/";
        mPath=path;
        labelsFile= new HashMap<String, Integer>(0);//mPath);


    }

    void changeRecognizer(int nRec)
    {
        switch(nRec) {
            case 0: faceRecognizer = com.googlecode.javacv.cpp.opencv_contrib.createLBPHFaceRecognizer(1,8,8,8,100);
                break;
            case 1: faceRecognizer = com.googlecode.javacv.cpp.opencv_contrib.createFisherFaceRecognizer();
                break;
            case 2: faceRecognizer = com.googlecode.javacv.cpp.opencv_contrib.createEigenFaceRecognizer();
                break;
        }
        train();

    }

    void add(Mat m, String description) {
        Bitmap bmp= Bitmap.createBitmap(m.width(), m.height(), Bitmap.Config.ARGB_8888);

        Utils.matToBitmap(m,bmp);
        bmp= Bitmap.createScaledBitmap(bmp, WIDTH, HEIGHT, false);

        FileOutputStream f;
        try {
            f = new FileOutputStream(mPath+description+"-"+count+".jpg",true);
            count++;
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, f);
            f.close();

        } catch (Exception e) {
            Log.e("error",e.getCause()+" "+e.getMessage());
            e.printStackTrace();

        }
    }

    public boolean train() {

        File root = new File(mPath);
        Log.i("mPath",mPath);
        FilenameFilter pngFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg");

            };
        };

        File[] imageFiles = root.listFiles(pngFilter);

        MatVector images = new MatVector(imageFiles.length);

        int[] labels = new int[imageFiles.length];

        int counter = 0;
        int label;

        IplImage img=null;
        IplImage grayImg;

        int i1=mPath.length();


        for (File image : imageFiles) {
            String p = image.getAbsolutePath();
            img = cvLoadImage(p);

            if (img==null)
                Log.e("Error","Error cVLoadImage");
            Log.i("image",p);

            int i2=p.lastIndexOf("-");
            int i3=p.lastIndexOf(".");
            int icount=Integer.parseInt(p.substring(i2+1,i3));
            if (count<icount) count++;

            String description=p.substring(i1,i2);

            if (labelsFile.get(description)<0)
                labelsFile.add(description, labelsFile.max()+1);

            label = labelsFile.get(description);

            grayImg = IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 1);

            cvCvtColor(img, grayImg, CV_BGR2GRAY);

            images.put(counter, grayImg);

            labels[counter] = label;

            counter++;
        }
        if (counter>0)
            if (labelsFile.max()>1)
                faceRecognizer.train(images, labels);
        labelsFile.Save();
        return true;
    }

    public boolean canPredict()
    {
        if (labelsFile.max()>1)
            return true;
        else
            return false;

    }

    public String predict(Mat m) {
        if (!canPredict())
            return "";
        int n[] = new int[1];
        double p[] = new double[1];
        IplImage ipl = MatToIplImage(m,WIDTH, HEIGHT);

        faceRecognizer.predict(ipl, n, p);

        if (n[0]!=-1)
            mProb=(int)p[0];
        else
            mProb=-1;
        if (n[0] != -1)
            return labelsFile.get(n[0]);
        else
            return "Unkown";
    }

    IplImage MatToIplImage(Mat m,int width,int heigth)
    {


        Bitmap bmp=Bitmap.createBitmap(m.width(), m.height(), Bitmap.Config.ARGB_8888);


        Utils.matToBitmap(m, bmp);
        return BitmapToIplImage(bmp,width, heigth);

    }

    IplImage BitmapToIplImage(Bitmap bmp, int width, int height) {

        if ((width != -1) || (height != -1)) {
            Bitmap bmp2 = Bitmap.createScaledBitmap(bmp, width, height, false);
            bmp = bmp2;
        }

        IplImage image = IplImage.create(bmp.getWidth(), bmp.getHeight(),
                IPL_DEPTH_8U, 4);

        bmp.copyPixelsToBuffer(image.getByteBuffer());

        IplImage grayImg = IplImage.create(image.width(), image.height(),
                IPL_DEPTH_8U, 1);

        cvCvtColor(image, grayImg, opencv_imgproc.CV_BGR2GRAY);

        return grayImg;
    }



    protected void SaveBmp(Bitmap bmp,String path)
    {
        FileOutputStream file;
        try {
            file = new FileOutputStream(path , true);

            bmp.compress(Bitmap.CompressFormat.JPEG,100,file);
            file.close();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("",e.getMessage()+e.getCause());
            e.printStackTrace();
        }

    }


    public void load() {
        train();

    }

    public int getProb() {
        // TODO Auto-generated method stub
        return mProb;
    }

*/
}