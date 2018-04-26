package ch.appquest.boredboizz.flashcode;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Girardin on 09.04.2018.
 */
// TODO : framerate Berechnung + Besserer Algoritmus für die Lichtpunkt erkennung
public class encodeFootage {
    private int width;
    private int hellsterPunktX;
    private int hellsterPunktY;
    private int scanRadius = 30;

    public ArrayList<Integer> getMsgListKamera() {
        return msgListKamera;
    }

    private ArrayList<Integer> msgListKamera = new ArrayList<>();

    public void resetMsgListKamera() {
        this.msgListKamera.clear();
    }

    public int[] getLightPoints(Bitmap bitmap) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        this.width = width;
        int[] data = new int[width * height];
        int[][] data2D = new int[width][height];
        Bitmap b = darkenBitMap(bitmap);
        b.getPixels(data, 0, width, 0, 0, width, height);

        int hellsterWert = data[0];
        hellsterPunktX = 0;
        hellsterPunktY = 0;
        int brightestValue = 0; int R = 0; int G = 0; int B = 0; int n = 0;
        // anstatt +1 +10 und bei true genauer mit +1
        for(int h = 0; h < height; h++) {
            for(int w = 0; w < width; w++) {
                data2D[w][h] = data[get1DPos(w,h)];
                if (data2D[w][h] > Color.argb(255,252,252,252)){
                    int color = data2D[w][h];
                    R += Color.red(color);
                    G += Color.green(color);
                    B += Color.blue(color);
                    n++;
                    if(data2D[w][h] >= hellsterWert && ((R + B + G) / (n * 3)) > brightestValue) {
                        hellsterPunktX = w;
                        hellsterPunktY = h;
                        hellsterWert = data2D[w][h];

                        brightestValue = ((R + B + G) / (n * 3));

                    }
                }else {
                     R = 0;  G = 0;  B = 0;  n = 0;
                }
            }
        }
        return new int[] {hellsterPunktX,hellsterPunktY};
    }

    public  int[] scanLightPoint(Bitmap b) {
        // TODO es scanned nun in der Mitte
        int punktX = b.getWidth()/2;
        int punktY = b.getHeight()/2;
        int height = b.getHeight();
        int width =  b.getWidth();
        int hits = 0;

        int[] data = new int[width * height];
        darkenBitMap(b).getPixels(data, 0, width, 0, 0, width, height);
        int hellsterWert = data[get1DPos(punktX,punktY)];
        for(int r = 0; r < scanRadius; r+=3) {
            if(data[get1DPos(punktX+r,punktY)] > Color.argb(255,252,252,252) ||
                data[get1DPos(punktX-r,punktY)] > Color.argb(255,252,252,252) ||
                data[get1DPos(punktX,punktY+r)] > Color.argb(255,252,252,252) ||
                data[get1DPos(punktX,punktY-r)] > Color.argb(255,252,252,252)) {
                hits++;
                // TODO neuer Hellster Punkt im Umfeld definieren.
            }

        }
        if (hits > 5) {
            msgListKamera.add(1);
        }
        else
        {
            msgListKamera.add(0);
        }

        return new int[] {punktX,punktY};
    }

    private int get1DPos(int x,int y) {
        return x + y*width;
    }

    public Bitmap drawCircle(int posX,int posY,int width, int height,int color) {
        Bitmap bmp = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888); // this creates a MUTABLE bitmap
        Canvas canvas = new Canvas(bmp);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(color);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5f);
        Paint pp = new Paint(Paint.ANTI_ALIAS_FLAG);
        pp.setColor(Color.GRAY);
        pp.setStyle(Paint.Style.STROKE);
        pp.setStrokeWidth(10f);
        canvas.drawCircle(posX, posY, 30, p);
        canvas.drawCircle(width/2, height/2, 50, pp);
        return bmp;
    }


    private Bitmap darkenBitMap(Bitmap bm) {

        Bitmap bitmap = bm.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas();
        canvas.setBitmap(bitmap);
        Paint p = new Paint(Color.RED);
        //ColorFilter filter = new LightingColorFilter(0xFFFFFFFF , 0x00222222); // lighten
        ColorFilter filter = new LightingColorFilter(0xFF7F7F7F, 0x00000000);


        p.setColorFilter(filter);
        canvas.drawBitmap(bm, new Matrix(), p);

        return bm;
    }







}
