package com.wuguangdong.caranimator;



import android.content.Context;
import android.graphics.PointF;
import android.renderscript.Sampler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.TypeEvaluator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * 在点击添加按钮的进候，产生动画效果:抛物线
 */
public class CartAnimLayout extends FrameLayout {
    public CartAnimLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CartAnimLayout(Context context) {
        this(context, null, 0);
    }

    public CartAnimLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //坐标 int[]
    private PointF mLocation = new PointF();

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //获取当前控件在界面的屏幕坐标
        int[] layoutLoc = new int[2];
        getLocationInWindow(layoutLoc);
        mLocation.set(layoutLoc[0], layoutLoc[1]);
    }

    //提供一个动画方法:参考 1，开始位置 2，结束位置
    //3 打气进来的移动控件
    public void startCartAnim(View startView, View endView, int layoutIdMove) {
        //1，开始位置
        int[] startLoc = new int[2];
        startView.getLocationInWindow(startLoc);
        PointF startF=new PointF(startLoc[0],startLoc[1]);
        // 2，结束位置
        int[] endLoc = new int[2];
        endView.getLocationInWindow(endLoc);
       final  PointF endF=new PointF(endLoc[0],endLoc[1]);
        //3.打气LayoutInflater
        final View moveView= LayoutInflater.from(getContext()).inflate(layoutIdMove,this,false);
        //开始动画  使用属性动画
        AnimatorSet set=new AnimatorSet();

        ObjectAnimator scaleXAnim=ObjectAnimator.ofFloat(moveView,"scaleX",1.0f,0.1f);
        ObjectAnimator scaleYAnim=ObjectAnimator.ofFloat(moveView,"scaleY",1.0f,0.1f);
        ValueAnimator pathAnim=ObjectAnimator.ofObject(beisaier,startF,endF);

        pathAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF newPointF= (PointF) animation.getAnimatedValue();
                moveView.setX(newPointF.x);
                moveView.setY(newPointF.y);
            }
        });
        set.playTogether(scaleXAnim,scaleYAnim,pathAnim);
        Animator.AnimatorListener listener=new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                 CartAnimLayout.this.addView(moveView);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                CartAnimLayout.this.removeView(moveView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
        set.addListener(listener);
        set.setDuration(2000);//运动时间
        //时间
        set.start();

    }
    //路径计算器
    private TypeEvaluator<PointF>  beisaier=new TypeEvaluator<PointF>() {
        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            //线性计算
            //贝塞尔曲线
            PointF newF=new PointF((startValue.x+endValue.x)/2,0);
            return BezierCurve.bezier(fraction,startValue,newF ,endValue);
        }
    };
}
