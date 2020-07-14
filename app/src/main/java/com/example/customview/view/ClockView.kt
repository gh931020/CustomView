package com.example.customview.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import java.lang.Integer.min
import java.util.*
import android.view.View as View

/**
 * author: zhang
 * created on: 2020/7/14 11:16
 * description:
 */
class ClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?= null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    //时针宽度
    private val mHourPointWidth = 15f
    //分针宽度
    private val mMinutePointWidth = 10f
    //秒针宽度
    private val mSecondPointWidth = 4f
    //指针矩形弧度
    private val mPointRange = 20f
    //刻度与数字间距
    private val mNumSpace = 10f
    //表盘宽度
    private val mCircleWidth = 4.0f
    //设置表盘整点刻度尺寸
    private val scaleMax = 50
    //设置表盘非整点刻度尺寸
    private val scaleMin = 25
    //view宽度
    private var mWidth = 0;
    //view高度
    private var mHeight = 0
    //⚪半径,默认200像素
    private var radius = 300.0f
    //画笔
    private val mPaint:Paint by lazy { Paint() }
    //形状
    private val mRect:Rect by lazy { Rect() }
    //初始化画笔属性
    init {
        mPaint.textSize = 35f
        mPaint.typeface = Typeface.DEFAULT_BOLD
        mPaint.isAntiAlias = true
//        //设置圆心X轴位置
//        val centerX:Float = width/2f
//        //设置圆心Y轴位置
//        val centerY:Float = height/2f
//        //设置圆半径,默认为1/4屏幕宽度,可添加为自定义属性
//        val radius:Float = width/4f
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = onMeasureSpec(widthMeasureSpec) + (mCircleWidth * 2).toInt()
        mHeight = onMeasureSpec(heightMeasureSpec) + (mCircleWidth * 2).toInt()
        //半径,直径-外周线宽度/2
        radius = (mWidth -mCircleWidth *2)/2
        //保存尺寸值
        setMeasuredDimension(mWidth,mHeight)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun onMeasureSpec(measureSpec: Int): Int {
        var specViewSize = 0
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        when (specMode) {
            MeasureSpec.EXACTLY -> {
                specViewSize = specSize
            }
            MeasureSpec.AT_MOST ->{
                //计算半径,以宽高最小值为准
                specViewSize = min((radius *2).toInt(),specSize)
            }
        }
        return specViewSize
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //设置圆心X轴位置
        val centerX:Float = (mWidth/2).toFloat()
        val centerY:Float = (mHeight/2).toFloat()
//        平移画布到圆心位置
        canvas?.translate(centerX,centerY)
        if (canvas != null) {
            //绘制最外层圆
            drawClock(canvas)
            //绘制刻度
            drawClockScale(canvas)
            /**绘制指针**/
            drawPointer(canvas)
        }
    }

    /**
     * 1.绘制表盘
     */
    private fun drawClock(canvas: Canvas){
        //设置外层圆的画笔宽度
        mPaint.strokeWidth = mCircleWidth
        //设置画笔颜色
        mPaint.color = Color.BLACK
        //设置画笔为空心
        mPaint.style = Paint.Style.STROKE
        //绘制圆
        canvas.drawCircle(0F,0F,radius,mPaint)
    }

    /**
     * 2.绘制表盘刻度
     */
    private fun drawClockScale(canvas: Canvas) {
        for (index in 1..60){
            //刻度绘制以12点钟为准,每次将表盘旋转6°,后续绘制都以12点钟为基准绘制
            canvas.rotate(6f,0f,0f)
            //绘制长刻度线
            if (index%5 == 0){
                //设置长刻度画笔宽度
                mPaint.strokeWidth = 4.0f
                //绘制刻度线
                canvas.drawLine(0f,-radius,0f,-radius+scaleMax,mPaint)
                //绘制数字
                canvas.save()//保存当前状态
                //设置画笔宽度
                mPaint.strokeWidth = 1.0f
                //设置实心画笔
                mPaint.style = Paint.Style.FILL
                //
                mPaint.getTextBounds((index/5).toString(),0,(index/5).toString().length,mRect)
                canvas.translate(0f,-radius+mNumSpace+scaleMax+(mRect.height()/2))
                canvas.rotate((index * -6).toFloat())
                canvas.drawText((index/5).toString(),-mRect.width()/2.toFloat(),mRect.height().toFloat()/2,mPaint)
                canvas.restore()//因为绘制文字时对画布有平移和旋转动作,所以在此处恢复画布之前的保存状态
            }else{
                //设置段刻度画笔宽度
                mPaint.strokeWidth = 2.0f
                canvas.drawLine(0f,-radius,0f,-radius+scaleMin,mPaint)
            }
        }

    }

    /**
     * 3.绘制指针
     */
    private fun drawPointer(canvas: Canvas) {
        //获取当前时间
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR]
        val minute = calendar[Calendar.MINUTE]
        val second = calendar[Calendar.SECOND]
        //计算时分秒转过的角度
        val angleHour = (hour + minute.toFloat() / 60)*360/12
        val angleMinute = (minute +second.toFloat()/60)*360/60
        val angleSecond = second*360/60

        //绘制时针
        canvas.save()
        //将画布旋转到时针的角度
        canvas.rotate(angleHour,0f,0f)
        //这里用到矩形的左上点和右下点
        val rectHour = RectF(-mHourPointWidth/2,-radius/2,mHourPointWidth/2,radius/6)
        //设置时针画笔属性
        mPaint.color = Color.BLUE
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mHourPointWidth
        canvas.drawRoundRect(rectHour,mPointRange,mPointRange,mPaint)
        canvas.restore()

        //绘制分针
        canvas.save()
        //将画布旋转到分针角度
        canvas.rotate(angleMinute,0f,0f)
        val rectMinute = RectF(-mMinutePointWidth/2,-radius*3.5f/5,mMinutePointWidth/2,radius/6)
        //设置分针画笔属性
        mPaint.color = Color.BLACK
        mPaint.strokeWidth = mMinutePointWidth
        canvas.drawRoundRect(rectMinute,mPointRange,mPointRange,mPaint)
        canvas.restore()

        //绘制秒针
        canvas.save()
        //将画布旋转到秒针角度
        canvas.rotate(angleSecond.toFloat(),0f,0f)
        val rectSecond = RectF(-mSecondPointWidth/2,-radius+10,mSecondPointWidth/2,radius/6)
        //设置秒针画笔
        mPaint.color = Color.RED
        mPaint.strokeWidth = mSecondPointWidth
        canvas.drawRoundRect(rectSecond,mPointRange,mPointRange,mPaint)
        canvas.restore()

        //绘制原点
        mPaint.style = Paint.Style.FILL
        canvas.drawCircle(0f,0f,mSecondPointWidth*4,mPaint)
    }
}