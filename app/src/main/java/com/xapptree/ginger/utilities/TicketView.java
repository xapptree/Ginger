package com.xapptree.ginger.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Akbar on 11/29/2017.
 */

public class TicketView extends LinearLayout {
    private Bitmap bm;
    private Canvas cv;
    private Paint eraser;
    private int holesBottomMargin = 40;// changed 70 to 40
    private int holesCustomMargin = 140;
    private int holeRadius = 40;

    public TicketView(Context context) {
        super(context);
        Init();
    }

    public TicketView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    public TicketView(Context context, AttributeSet attrs,
                      int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init();
    }

    private void Init() {
        eraser = new Paint();
        eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        eraser.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            cv = new Canvas(bm);
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();

        bm.eraseColor(Color.TRANSPARENT);

        // set the view background color
        cv.drawColor(Color.parseColor("#22d3a7"));

        // drawing footer square contains the buy now button
        Paint paint = new Paint();
        paint.setARGB(255, 245, 245, 245);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        cv.drawRect(0, h, w, h - pxFromDp(getContext(), holesBottomMargin), paint);

        // adding punching holes on the ticket by erasing them
        cv.drawCircle(0, 0, holeRadius, eraser); // top-left hole
        cv.drawCircle(w / 2, 0, holeRadius, eraser); // top-middle hole
        cv.drawCircle(w, 0, holeRadius, eraser); // top-right
        cv.drawCircle(0, h - pxFromDp(getContext(), holesBottomMargin), holeRadius, eraser); // bottom-left hole
        cv.drawCircle(w, h - pxFromDp(getContext(), holesBottomMargin), holeRadius, eraser); // bottom right hole
        cv.drawCircle(0, h - pxFromDp(getContext(), holesCustomMargin), holeRadius, eraser); // bottom-left hole
        cv.drawCircle(w, h - pxFromDp(getContext(), holesCustomMargin), holeRadius, eraser); // bottom right hole

        // drawing the image
        canvas.drawBitmap(bm, 0, 0, null);


        // drawing dashed lines at the bottom
        Path mPath = new Path();
        mPath.moveTo(holeRadius, h - pxFromDp(getContext(), holesCustomMargin));
        mPath.quadTo(w - holeRadius, h - pxFromDp(getContext(), holesCustomMargin), w - holeRadius, h - pxFromDp(getContext(), holesCustomMargin));

        // dashed line
        Paint dashed = new Paint();
        dashed.setARGB(255, 255, 255, 255);
        dashed.setStyle(Paint.Style.STROKE);
        dashed.setStrokeWidth(4);
        dashed.setPathEffect(new DashPathEffect(new float[]{15, 10}, 0));
        canvas.drawPath(mPath, dashed);

        super.onDraw(canvas);
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
