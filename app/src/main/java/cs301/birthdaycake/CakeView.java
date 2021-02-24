package cs301.birthdaycake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;

public class CakeView extends SurfaceView {

    /* These are the paints we'll use to draw the birthday cake below */
    Paint cakePaint = new Paint();
    Paint frostingPaint = new Paint();
    Paint candlePaint = new Paint();
    Paint outerFlamePaint = new Paint();
    Paint innerFlamePaint = new Paint();
    Paint wickPaint = new Paint();
    Paint balloonPaint = new Paint();
    Paint balloonStringPaint = new Paint();
    Paint xyTextPaint = new Paint();

    /* These constants define the dimensions of the cake.  While defining constants for things
        like this is good practice, we could be calculating these better by detecting
        and adapting to different tablets' screen sizes and resolutions.  I've deliberately
        stuck with hard-coded values here to ease the introduction for CS371 students.
     */
    public static final float cakeTop = 400.0f;
    public static final float cakeLeft = 100.0f;
    public static final float cakeWidth = 1200.0f;
    public static final float layerHeight = 200.0f;
    public static final float frostHeight = 50.0f;
    public static final float candleHeight = 300.0f;
    public static final float candleWidth = 60.0f;
    public static final float wickHeight = 30.0f;
    public static final float wickWidth = 6.0f;
    public static final float outerFlameRadius = 30.0f;
    public static final float innerFlameRadius = 15.0f;
    public static final float balloonRadius = 150.0f;
    public static final float balloonPointLen = 50.0f;
    public static final double balloonPointAngle = Math.PI * 0.5;
    public static final float balloonStringLen = 200.0f;

    private CakeModel model;

    /*
     * constructor must be overridden here as per standard Java inheritance practice.  We need it
     * anyway to initialize the member variables
     */
    public CakeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //This is essential or your onDraw method won't get called
        setWillNotDraw(false);

        //Setup our palette
        cakePaint.setColor(0xFFC755B5);  //violet-red
        cakePaint.setStyle(Paint.Style.FILL);
        frostingPaint.setColor(0xFFFFFACD);  //pale yellow
        frostingPaint.setStyle(Paint.Style.FILL);
        candlePaint.setColor(0xFF32CD32);  //lime green
        candlePaint.setStyle(Paint.Style.FILL);
        outerFlamePaint.setColor(0xFFFFD700);  //gold yellow
        outerFlamePaint.setStyle(Paint.Style.FILL);
        innerFlamePaint.setColor(0xFFFFA500);  //orange
        innerFlamePaint.setStyle(Paint.Style.FILL);
        wickPaint.setColor(Color.BLACK);
        wickPaint.setStyle(Paint.Style.FILL);
        balloonPaint.setColor(Color.BLUE);
        balloonPaint.setStyle(Paint.Style.FILL);
        balloonPaint.setStrokeJoin(Paint.Join.MITER);
        balloonPaint.setStrokeCap(Paint.Cap.ROUND);
        balloonStringPaint.setColor(Color.BLACK);
        balloonStringPaint.setStyle(Paint.Style.STROKE);
        balloonStringPaint.setStrokeCap(Paint.Cap.ROUND);
        balloonStringPaint.setStrokeWidth(15.0f);
        xyTextPaint.setColor(Color.RED);
        xyTextPaint.setTextSize(50.0f);

        setBackgroundColor(Color.WHITE);  //better than black default

        model = new CakeModel();

    }

    /**
     * draws a candle at a specified position.  Important:  the left, bottom coordinates specify
     * the position of the bottom left corner of the candle
     */
    public void drawCandle(Canvas canvas, float left, float bottom) {
        canvas.drawRect(left, bottom - candleHeight, left + candleWidth, bottom, candlePaint);

        //checks if the model indicates the flame should be drawn
        if(model.litCandles) {
            //draw the outer flame
            float flameCenterX = left + candleWidth / 2;
            float flameCenterY = bottom - wickHeight - candleHeight - outerFlameRadius / 3;
            canvas.drawCircle(flameCenterX, flameCenterY, outerFlameRadius, outerFlamePaint);

            //draw the inner flame
            flameCenterY += outerFlameRadius / 3;
            canvas.drawCircle(flameCenterX, flameCenterY, innerFlameRadius, innerFlamePaint);
        }

        //draw the wick
        float wickLeft = left + candleWidth/2 - wickWidth/2;
        float wickTop = bottom - wickHeight - candleHeight;
        canvas.drawRect(wickLeft, wickTop, wickLeft + wickWidth, wickTop + wickHeight, wickPaint);

    }

    //Draws balloon on canvas at given x,y
    public void drawBalloon(Canvas canvas, float x, float y){

        //Draws balloon string
        canvas.drawLine(x, y, x, y + balloonRadius + balloonPointLen + balloonStringLen,
                balloonStringPaint);

        //Draws balloon
        canvas.drawCircle(x, y, balloonRadius, balloonPaint);

        //Draws balloon tip
        Path path = new Path();
        path.moveTo(x - balloonRadius * (float) Math.sin(Math.PI/2 - balloonPointAngle/2),
                    y + balloonRadius * (float) Math.cos(Math.PI/2 - balloonPointAngle/2));
        path.lineTo(x, y + balloonRadius + balloonPointLen);
        path.lineTo(x + balloonRadius * (float) Math.sin(Math.PI/2 - balloonPointAngle/2),
                    y + balloonRadius * (float) Math.cos(Math.PI/2 - balloonPointAngle/2));
        path.close();
        canvas.drawPath(path, balloonPaint);
    }

    /*
     * onDraw is like "paint" in a regular Java program.  While a Canvas is
     * conceptually similar to a Graphics in javax.swing, the implementation has
     * many subtle differences.  Show care and read the documentation.
     *
     * This method will draw a birthday cake
     */
    @Override
    public void onDraw(Canvas canvas)
    {
        //top and bottom are used to keep a running tally as we progress down the cake layers
        float top = cakeTop;
        float bottom = cakeTop + frostHeight;

        //Frosting on top
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);
        top += layerHeight;
        bottom += frostHeight;

        //Then a second frosting layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a second cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);

        //Checks if model indicates candles should be drawn
        if(model.showCandles) {
            //Now draw the candles
            for(int i = 1; i <= model.amountCandles; i++){
                drawCandle(canvas, cakeLeft + (i * cakeWidth / (model.amountCandles + 1))
                                    - (candleWidth / 2), cakeTop);
            }
        }

        //Draws balloon if it should be drawn
        if(model.showBalloon){ drawBalloon(canvas, model.x, model.y); }

        canvas.drawText("x: " + model.x + ", y: " + model.y, 1000, 1000, xyTextPaint);

    }//onDraw

    //CakeModel accessor
    public CakeModel getModel(){
        return model;
    }

}//class CakeView

