import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

private val LinearIndicatorWidth = 240.dp
private val LinearIndicatorHeight = ProgressIndicatorDefaults.StrokeWidth

private val CircularIndicatorDiameter = 40.dp

@Composable
internal fun RoundedLinearProgressIndicator(
    /*@FloatRange(from = 0.0, to = 1.0)*/
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    backgroundColor: Color =  Color.LightGray
) {
    Canvas(
        modifier
            .progressSemantics(progress)
            .size(LinearIndicatorWidth, LinearIndicatorHeight)
            .focusable()
    ) {
        val strokeWidth = size.height
        drawRoundedLinearProgressIndicator(
            startFraction = 0f,
            endFraction = 1f,
            color = backgroundColor,
            strokeWidth = strokeWidth
        )
        drawRoundedLinearProgressIndicator(
            startFraction = 0f,
            endFraction = progress,
            color = color,
            strokeWidth = strokeWidth
        )
    }
}

@Composable
internal fun RoundedCircularProgressIndicator(
    /*@FloatRange(from = 0.0, to = 1.0)*/
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth
) {
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
    }
    Canvas(
        modifier
            .progressSemantics(progress)
            .size(CircularIndicatorDiameter)
            .focusable()
    ) {
        // Start at 12 O'clock
        val startAngle = 270f
        val sweep = progress * 360f
        drawRoundedCircularIndicator(startAngle, sweep, color, stroke)
    }
}

private fun DrawScope.drawRoundedCircularIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke
) {
    // To draw this circle we need a rect with edges that line up with the midpoint of the stroke.
    // To do this we need to remove half the stroke width from the total diameter for both sides.
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )
}

private fun DrawScope.drawRoundedLinearProgressIndicator(
    startFraction: Float,
    endFraction: Float,
    color: Color,
    strokeWidth: Float
) {
    val cap = StrokeCap.Round
    val width = size.width
    val height = size.height
    // Start drawing from the vertical center of the stroke
    val yOffset = height / 2

    val roundedCapOffset = size.height / 2

    val isLtr = layoutDirection == LayoutDirection.Ltr
    val barStart = (if (isLtr) startFraction else 1f - endFraction) * width + if (isLtr) roundedCapOffset else -roundedCapOffset
    val barEnd = if (endFraction != 0f) {
        (if (isLtr) endFraction else 1f - startFraction) * width - if (isLtr) roundedCapOffset else -roundedCapOffset
    } else height / 2

    // Progress line
    drawLine(
        color = color,
        start = Offset(barStart, yOffset),
        end = Offset(barEnd, yOffset),
        strokeWidth = strokeWidth,
        cap = cap,
    )
}