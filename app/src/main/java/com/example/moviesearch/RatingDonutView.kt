package com.example.moviesearch

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

class RatingDonutView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : View(context, attributeSet) {

    private val oval = RectF()
    private var radius: Float = 0f
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var stroke = 7f
    private var progress = 50
    private var scaleSize = 60f
    private lateinit var strokePaint: Paint
    private lateinit var digitPaint: Paint
    private lateinit var circlePaint: Paint
    private var isStaticPictureDrawn: Boolean = false
    private lateinit var bitmap: Bitmap
    private lateinit var staticCanvas: Canvas
    private var animator: ValueAnimator? = null

    private fun initPaint() {
        strokePaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = stroke
            color = getPaintColor(progress)
            isAntiAlias = true
        }

        digitPaint = Paint().apply {
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = 2f
            textSize = scaleSize
            typeface = Typeface.SERIF
            color = getPaintColor(progress)
            isAntiAlias = true
        }

        circlePaint = Paint().apply {
            style = Paint.Style.FILL_AND_STROKE
            color = Color.WHITE
        }
    }

    init {
        val a =
            context.theme.obtainStyledAttributes(attributeSet, R.styleable.RatingDonutView, 0, 0)
        try {
            stroke = a.getFloat(R.styleable.RatingDonutView_stroke, stroke)
            progress = a.getInt(R.styleable.RatingDonutView_progress, progress)
        } finally {
            a.recycle()
        }
        initPaint()
        startRatingAnimation()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = if (width > height) {
            height.div(2f)
        } else {
            width.div(2f)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val chosenWidth = chosenDimension(widthMode, widthSize)
        val chosenHeight = chosenDimension(heightMode, heightSize)

        val minSide = Math.min(chosenWidth, chosenHeight)
        centerX = minSide.div(2f)
        centerY = minSide.div(2f)

        setMeasuredDimension(minSide, minSide)
    }

    private fun drawStaticPicture() {
        bitmap = Bitmap.createBitmap(
            (centerX * 2).toInt(),
            (centerY * 2).toInt(),
            Bitmap.Config.ARGB_8888
        )
        staticCanvas = Canvas(bitmap)
        drawRating(staticCanvas)
        drawText(staticCanvas)
    }

    private fun chosenDimension(mode: Int, size: Int) = when (mode) {
        MeasureSpec.AT_MOST, MeasureSpec.EXACTLY -> size
        else -> 200
    }

    override fun onDraw(canvas: Canvas) {
        if (!isStaticPictureDrawn) {
            drawStaticPicture()
        }
        canvas.drawBitmap(bitmap, centerX - radius, centerY - radius, null)
        drawRating(canvas)
        drawText(canvas)
        isStaticPictureDrawn = true
    }

    fun setProgress(pr: Int) {
        progress = pr
        initPaint()
        invalidate()
    }

    private fun drawRating(canvas: Canvas) {
        val scale = radius * 0.8f
        canvas.save()
        canvas.translate(centerX, centerY)
        oval.set(0f - scale, 0f - scale, scale, scale)
        canvas.drawCircle(0f, 0f, radius, circlePaint)
        canvas.drawArc(oval, -90f, convertProgressToDegress(progress), false, strokePaint)
        canvas.restore()
    }

    private fun drawText(canvas: Canvas) {
        val message = String.format("%.1f", progress / 10f)
        val widths = FloatArray(message.length)
        digitPaint.getTextWidths(message, widths)
        var advance = 0f
        for (width in widths) advance += width
        canvas.drawText(message, centerX - advance / 2, centerY + advance / 4, digitPaint)
    }

    private fun getPaintColor(progress: Int): Int = when (progress) {
        in 0..25 -> Color.parseColor("#FF2400")
        in 26..50 -> Color.parseColor("#FAD201")
        in 51..75 -> Color.parseColor("#2AFF18")
        else -> Color.parseColor("#0000FF")
    }

    private fun convertProgressToDegress(progress: Int): Float = progress * 3.6f

    private fun startRatingAnimation() {
        animator?.cancel()
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1600
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                this@RatingDonutView.alpha = it.animatedValue as Float
                invalidate()
            }
        }
        animator?.start()
    }
}