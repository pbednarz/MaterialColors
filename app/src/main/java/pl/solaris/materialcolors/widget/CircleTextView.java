package pl.solaris.materialcolors.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import pl.solaris.materialcolors.R;

/**
 * Created by solaris on 2015-01-05.
 */
public class CircleTextView extends TextView {
    private int centerY;
    private int centerX;
    private int outerRadius;
    private Paint circlePaint;
    private int defaultColor = Color.BLACK;

    public CircleTextView(Context context) {
        super(context);
        init(context, null);
    }

    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, outerRadius, circlePaint);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        outerRadius = Math.min(w, h) / 2;
    }

    public void setColor(int color) {
        this.defaultColor = color;
        circlePaint.setColor(defaultColor);
        this.invalidate();
    }

    private void init(Context context, AttributeSet attrs) {
        this.setFocusable(false);
        setClickable(false);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL);
        int color = Color.BLACK;
        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleTextView);
            color = a.getColor(R.styleable.CircleTextView_ctv_color, color);
            a.recycle();
        }
        setColor(color);
    }
}
