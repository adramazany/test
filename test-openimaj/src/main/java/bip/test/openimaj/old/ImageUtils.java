package bip.test.openimaj.old;

/**
 * Created by ramezani on 10/28/2019.
 */
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

class ImageUtils {
    ImageUtils() {
    }

    public static byte[][] getGrayscaleData(BufferedImage img) {
        byte[][] pixels = new byte[img.getWidth()][img.getHeight()];
        int x;
        int y;
        switch(img.getType()) {
            case 10:
                DataBuffer data = img.getRaster().getDataBuffer();

                for(x = 0; x < img.getWidth(); ++x) {
                    for(y = 0; y < img.getHeight(); ++y) {
                        pixels[x][y] = (byte)data.getElem(y * img.getWidth() + x);
                    }
                }

                return pixels;
            default:
                for(x = 0; x < img.getWidth(); ++x) {
                    for(y = 0; y < img.getHeight(); ++y) {
                        int argb = img.getRGB(x, y);
                        int red = argb >> 16 & 255;
                        int green = argb >> 8 & 255;
                        int blue = argb & 255;
                        pixels[x][y] = (byte)((blue + red + green) / 3);
                    }
                }

                return pixels;
        }
    }

    public static BufferedImage convertToGreyscale(BufferedImage img) {
        BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(), 10);
        Graphics g = image.getGraphics();
        g.drawImage(img, 0, 0, (ImageObserver)null);
        g.dispose();
        return image;
    }

    public static BufferedImage copyImage(BufferedImage source) {
        BufferedImage target = new BufferedImage(source.getWidth(), source.getHeight(), 6);
        Graphics g = target.getGraphics();
        g.drawImage(source, 0, 0, (ImageObserver)null);
        return target;
    }

    public static BufferedImage loadImage(File f) {
        try {
            return loadImage((InputStream)(new FileInputStream(f)));
        } catch (FileNotFoundException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static BufferedImage loadImage(InputStream in) {
        BufferedImage image = null;

        try {
            try {
                image = ImageIO.read(in);
            } catch (IOException var7) {
                ;
            } finally {
                in.close();
            }
        } catch (IOException var9) {
            ;
        }

        return image;
    }

    public static void saveImage(OutputStream out, BufferedImage image, String formatName) {
        try {
            try {
                ImageIO.write(image, formatName, out);
            } catch (IOException var8) {
                ;
            } finally {
                out.flush();
                out.close();
            }
        } catch (IOException var10) {
            ;
        }

    }

    public static BufferedImage getScaledInstance(BufferedImage img, int targetWidthIn, int targetHeightIn, Object hint, boolean higherQuality, boolean keepAspect) {
        int type = img.getTransparency() == 1?1:2;
        BufferedImage ret = img;
        int targetWidth = targetWidthIn;
        int targetHeight = targetHeightIn;
        if(keepAspect) {
            double fixedRatio = (double)targetWidthIn / (double)targetHeightIn;
            if((double)img.getWidth() / (double)img.getHeight() >= fixedRatio) {
                targetHeight = (int)((double)targetWidthIn * ((double)img.getHeight() / (double)img.getWidth()));
            } else {
                targetWidth = (int)((double)img.getWidth() * ((double)targetHeightIn / (double)img.getHeight()));
            }
        }

        int h;
        int w;
        if(higherQuality) {
            w = img.getWidth();
            h = img.getHeight();
        } else {
            w = targetWidth;
            h = targetHeight;
        }

        BufferedImage tmp;
        do {
            if(higherQuality && w > targetWidth) {
                w /= 2;
                if(w < targetWidth) {
                    w = targetWidth;
                }
            }

            if(higherQuality && h > targetHeight) {
                h /= 2;
                if(h < targetHeight) {
                    h = targetHeight;
                }
            }

            tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, (ImageObserver)null);
            g2.dispose();
            ret = tmp;
        } while(w != targetWidth || h != targetHeight);

        return tmp;
    }

    public static BufferedImage invertImage(BufferedImage img, boolean newImage) {
        BufferedImage returnImage = img;
        if(newImage) {
            returnImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        }

        for(int y = 0; y < img.getHeight(); ++y) {
            for(int x = 0; x < img.getWidth(); ++x) {
                int in = img.getRGB(x, y);
                returnImage.setRGB(x, y, in & -16777216 | ~in & 16777215);
            }
        }

        return returnImage;
    }

    public static void displayImage(final BufferedImage img) {
        JFrame f = new JFrame();
        f.getContentPane().add(new JPanel() {
            private static final long serialVersionUID = 1259304458335048851L;

            public void paint(Graphics g) {
                int newW = this.getWidth();
                int newH = this.getHeight();
                BufferedImage scaledImage = ImageUtils.getScaledInstance(img, newW, newH, RenderingHints.VALUE_INTERPOLATION_BILINEAR, false, true);
                g.drawImage(scaledImage, 0, 0, (ImageObserver)null);
            }
        }, "Center");
        f.setPreferredSize(new Dimension(800, 600));
        f.pack();
        f.setVisible(true);
    }

    public static class ImagePanel extends JPanel {
        private BufferedImage img = null;
        private static final long serialVersionUID = 1259304458335048851L;

        public ImagePanel() {
        }

        public ImagePanel(BufferedImage img) {
            this.img = img;
        }

        public void setImage(BufferedImage img) {
            this.img = img;
            this.repaint();
        }

        public void paint(Graphics g) {
            if(this.img != null) {
                int newW = this.getWidth();
                int newH = this.getHeight();
                BufferedImage scaledImage = ImageUtils.getScaledInstance(this.img, newW, newH, RenderingHints.VALUE_INTERPOLATION_BILINEAR, false, true);
                g.drawImage(scaledImage, 0, 0, (ImageObserver)null);
            }
        }
    }
}
