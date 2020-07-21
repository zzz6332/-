package com.example.exam.view.widget

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi

/**
 * 刷新时的imageview
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class LoadingImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ImageView(context, attrs, defStyleAttr, defStyleRes) {
    private var mTop = 0
    private var rotate: RotateAnimation =
        RotateAnimation(0f,360f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)
    init {
        rotate.duration = 1000
        rotate.repeatMode = RotateAnimation.RESTART
        rotate.repeatCount = RotateAnimation.INFINITE
       //  this.startAnimation(rotate)
       /* val valueAnimator = ValueAnimator.ofInt(0,100,0)
        valueAnimator.repeatMode = ValueAnimator.RESTART
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.setDuration(2000)
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.addUpdateListener {
            val dx =it.animatedValue as Int
            top = mTop + dx
        }
        valueAnimator.start()*/
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mTop = top
    }
    fun startAnim(){
        this.startAnimation(rotate)
    }

    fun cancleAnim(){
        this.animation = null
    }
}