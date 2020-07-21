package com.example.exam.view.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi

/**
 * 下拉加载更多的view
 * 包括2个子view: Head和content
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class PullToDo @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {
    private var downY: Int = 0
    private var lastY: Int = 0
    private var moveY: Int = 0
    var up: Boolean = false
    private var isLayout: Boolean = false
    private lateinit var head: View
    private lateinit var content: View
    var canPull: Boolean = true
    lateinit var listener: OnPullListener

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val suggestWidth = MeasureSpec.getSize(widthMeasureSpec)
        val suggestHeight = MeasureSpec.getSize(heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var resultW = suggestWidth
        var resultH = suggestHeight
        if (!isLayout) {
            head = getChildAt(0)
            content = getChildAt(1)
            head.visibility = View.GONE  //首先让头view不可见并且不占用位置
            isLayout = true
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            resultW = head.measuredWidth + content.measuredWidth
            resultH = head.measuredHeight + content.measuredHeight
        } else if (widthMode == MeasureSpec.AT_MOST) {
            resultW = head.measuredWidth + content.measuredWidth
        } else if (heightMode == MeasureSpec.AT_MOST) {
            resultH = head.measuredHeight + content.measuredHeight
        }
        setMeasuredDimension(resultW, resultH)

    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (!canPull) {  //如果当前不能往下滑动 直接返回false不拦截
            return false
        }
        //如果能往下滑动
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                lastY = ev.y.toInt()
                return false
            }
            MotionEvent.ACTION_MOVE -> {
                moveY = moveY + ev.y.toInt() - lastY
                lastY = ev.y.toInt()
                if (moveY > 0) { //往下滑动
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d("-----", event.x.toString() + "," + event.y + "")
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downY = event.y.toInt()
                lastY = downY
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                moveY = (moveY + event.y - lastY).toInt()
                lastY = event.y.toInt()
                moveY = if (moveY > 0) {
                    Log.d("-------moveY : $moveY", "")
                    requestLayout() //重新layout
                    if (moveY > 400) {
                        listener.onPullMuch()
                    } else {
                        listener.onPullLittle()
                    }
                    return true
                } else {
                    0
                }
            }
            MotionEvent.ACTION_UP -> {
                up = true
                requestLayout()
                if (moveY >= 400) {
                    listener.onPullUp()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (isLayout) {
            if (up) {
                head.layout(0, -height, width, 0) //让头view不可见
                content.layout(
                    content.paddingLeft,
                    paddingTop,
                    content.measuredWidth,
                    content.measuredHeight
                )
                up = false
                moveY = 0
                lastY = 0
                downY = 0
            } else {
                head.visibility = View.VISIBLE //让头view可见
                head.layout(
                    head.paddingLeft,
                    moveY - head.measuredHeight,
                    head.measuredWidth,
                    moveY
                )
                content.layout(
                    paddingLeft,
                    moveY + paddingTop,
                    content.measuredWidth - paddingRight,
                    content.measuredHeight + moveY
                )
            }
        }
    }
     fun requestLa(){
         requestLayout()
     }
    interface OnPullListener {
        fun onPullUp()
        fun onPullMuch()
        fun onPullLittle()
    }
}