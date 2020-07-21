package com.example.exam.view.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.RequiresApi

/**
 * 圆角的imageview
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class RoundImageView @JvmOverloads constructor (context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : ImageView(context, attrs, defStyleAttr, defStyleRes) {
    private var width = 0f
    private var height = 0f

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        width = (right-left).toFloat()
        height = (bottom-top).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        if (width >= 30 && height > 30) {
            val path = Path()
            //四个圆角
            path.moveTo(30f, 0f)
            path.lineTo(width - 30, 0f)
            path.quadTo(width, 0f, width, 30f)
            path.lineTo(width, height - 30)
            path.quadTo(width, height, width - 30, height)
            path.lineTo(30f, height)
            path.quadTo(0f, height, 0f, height - 30)
            path.lineTo(0f, 30f)
            path.quadTo(0f, 0f, 30f, 0f)
            canvas?.clipPath(path)
        }
        super.onDraw(canvas)
    }
}