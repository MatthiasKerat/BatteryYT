package com.kapps.batteryyt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.kapps.batteryyt.ui.theme.*
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BatteryYTTheme {
                window.statusBarColor = orange.toArgb()
                window.navigationBarColor = orange.toArgb()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(gray),
                    contentAlignment = Alignment.Center
                ){
                    Battery(
                        value = 80,
                        color = orange,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .aspectRatio(1.6f),
                        outerThickness = 60f,
                        knobLength = 100f,
                        totalBarSpace = 250f,
                        steps = 8
                    )
                }
            }
        }
    }
}

@Composable
fun Battery(
    modifier: Modifier = Modifier,
    value:Int,
    steps:Int = 10,
    outerThickness:Float = 30f,
    totalBarSpace:Float = 120f,
    color: Color,
    knobLength:Float = 45f
) {

    Canvas(
        modifier = modifier
    ){
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawRect(
            color = color,
            size = Size(
                width = canvasWidth,
                height = canvasHeight
            ),
            style = Stroke(
                width = outerThickness,
                pathEffect = PathEffect.cornerPathEffect(10.dp.toPx())
            )
        )
        drawRoundRect(
            color = orange,
            topLeft = Offset(canvasWidth,canvasHeight*0.25f),
            size = Size(knobLength,canvasHeight*0.5f),
            cornerRadius = CornerRadius(25f,25f)
        )
        val innerBatteryWidth = canvasWidth - outerThickness
        val spaceBetween = totalBarSpace / (steps+1)
        val loadingBarWidth = (innerBatteryWidth-totalBarSpace)/steps

        var currentStartOffset = Offset(
            x = (outerThickness/2f) + (loadingBarWidth/2f)+spaceBetween,
            y = outerThickness
        )

        var currentEndOffset = Offset(
            x = (outerThickness/2f) + (loadingBarWidth/2f)+spaceBetween,
            y = canvasHeight - outerThickness
        )

        for(i in 0 until (value/100f*steps).roundToInt()){
            drawLine(
                color = color,
                strokeWidth = loadingBarWidth,
                start = currentStartOffset,
                end = currentEndOffset
            )
            currentStartOffset =
                currentStartOffset.copy(x = currentStartOffset.x + loadingBarWidth+spaceBetween)
            currentEndOffset =
                currentEndOffset.copy(x = currentEndOffset.x + loadingBarWidth+spaceBetween)
        }
    }
}

