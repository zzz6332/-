package com.example.exam.view.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi

/**
 * 流式布局的一个Viewgroup
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class TagView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val suggestWidth = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val suggestHeight = MeasureSpec.getSize(heightMeasureSpec)
        //测量子 View 尺寸信息
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        /**
         *  主要处理 width 和 height AT_MOST 测量模式下的情况
         *  在 width 方面，TagView 中的子元素要求出所有行中的宽度最大的一行，并且这个数值
         *  不能大于 parent 给出的建议宽度
         * */
        var cWidth: Int
        var cHeight: Int
        var lineWidth: Int = paddingLeft + paddingRight
        var lineMaxWidth: Int = lineWidth
        var lineHeight: Int = paddingBottom + paddingTop
        var childlPara: MarginLayoutParams
        var resultW: Int = suggestWidth
        var resultH: Int = suggestHeight
        for (index in 0 until childCount) {
            if (index == 0) {
                lineHeight = getChildAt(0).measuredHeight
            }
            val view = getChildAt(index)
            childlPara = view.layoutParams as MarginLayoutParams
            // 子 View 的实际宽高包含它们的 margin
            cWidth = view.measuredWidth + childlPara.leftMargin + childlPara.rightMargin
            cHeight = view.measuredHeight + childlPara.topMargin + childlPara.bottomMargin
            if (widthMode == MeasureSpec.AT_MOST) {
                // 如果此次排列后，这一行的宽度超过 parent 提供的 size 就表明要换行了
                if (lineWidth + cWidth > suggestWidth) {
                    // 换行后需要重置 lineWidth
                    lineWidth = paddingLeft + paddingRight + cWidth
                    lineHeight += cHeight
                } else {
                    // lineWidth 对子 View 宽度进行累加
                    lineWidth += cWidth
                }
                if (lineWidth > lineMaxWidth) {
                    //   更新最大的行宽数值
                    lineMaxWidth = lineWidth
                }
            }
        }
        if (widthMode == MeasureSpec.AT_MOST) {
            resultW = lineMaxWidth
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            resultH = lineHeight
            if (resultH > suggestHeight) {
                resultH = suggestHeight
            }
        }
        setMeasuredDimension(resultW, resultH+15)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left: Int = paddingLeft
        val right: Int = width - paddingRight
        var top: Int = paddingTop
        val bottom: Int = height - paddingBottom
        var lp: MarginLayoutParams
        var cw: Int
        var ch: Int
        for (index in 0 until childCount) {
            var view = getChildAt(index)
            lp = view.layoutParams as MarginLayoutParams
            cw = view.measuredWidth + lp.leftMargin + lp.rightMargin
            ch = view.measuredHeight + lp.topMargin + lp.bottomMargin
            //该换行了
            if (left + cw > right) {
                left = paddingLeft
                top += ch
            }
            //如果高度超出了范围就退出绘制
            if (top >= bottom) break
            view.layout(left + lp.leftMargin, top + lp.topMargin, left + cw, top + ch)
            left += cw
        }
    }
}