/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Robin Chutaux
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package pl.solaris.materialcolors.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import pl.solaris.materialcolors.R;

public class RippleViewFrameLayout extends FrameLayout {
    private int WIDTH;
    private int HEIGHT;
    private int FRAME_RATE = 10;
    private int DURATION = 200;
    private int PAINT_ALPHA = 255;
    private float radiusMax = 0;
    private boolean animationRunning = false;
    private int timer = 0;
    private float x = -1;
    private float y = -1;
    private Paint paint;
    private int rippleColor;
    private int ripplePadding;
    private animationEndListener listener;

    public RippleViewFrameLayout(Context context) {
        super(context);
    }

    public RippleViewFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RippleViewFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(final Context context, final AttributeSet attrs) {
        if (isInEditMode())
            return;

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleViewFrameLayout);
        rippleColor = typedArray.getColor(R.styleable.RippleViewFrameLayout_rv_color, getResources().getColor(android.R.color.white));
        DURATION = typedArray.getInteger(R.styleable.RippleViewFrameLayout_rv_rippleDuration, DURATION);
        FRAME_RATE = typedArray.getInteger(R.styleable.RippleViewFrameLayout_rv_framerate, FRAME_RATE);
        PAINT_ALPHA = typedArray.getInteger(R.styleable.RippleViewFrameLayout_rv_alpha, PAINT_ALPHA);
        ripplePadding = typedArray.getDimensionPixelSize(R.styleable.RippleViewFrameLayout_rv_ripplePadding, 0);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(rippleColor);
        paint.setAlpha(PAINT_ALPHA);
        this.setWillNotDraw(false);
        this.setDrawingCacheEnabled(true);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (animationRunning) {
            if (DURATION <= timer * FRAME_RATE) {
                animationRunning = false;
                canvas.drawColor(rippleColor);
                setBackgroundColor(rippleColor);
                timer = 0;
                canvas.save();
                if (listener != null) {
                    listener.onAnimationEnd();
                }
                return;
            } else {
                postInvalidateDelayed(FRAME_RATE);
            }
            if (timer == 0)
                canvas.save();


            canvas.drawCircle(x, y, (radiusMax * (((float) timer * FRAME_RATE) / DURATION)), paint);
            paint.setAlpha((int) (((PAINT_ALPHA) * (((float) timer * FRAME_RATE) / DURATION))));
            timer++;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        WIDTH = w;
        HEIGHT = h;
    }

    public void startAnimation(float x, float y, animationEndListener listener) {
        startAnimation(x, y, rippleColor, listener);
    }

    public void startAnimation(float x, float y, int color, animationEndListener listener) {
        this.listener = listener;
        if (!animationRunning) {
            radiusMax = Math.max(WIDTH, HEIGHT);
            radiusMax -= ripplePadding;
            this.x = x;
            this.y = y;
            animationRunning = true;
            rippleColor = color;
            paint.setColor(rippleColor);
            invalidate();
        }
    }

    public interface animationEndListener {
        public void onAnimationEnd();

    }
}
