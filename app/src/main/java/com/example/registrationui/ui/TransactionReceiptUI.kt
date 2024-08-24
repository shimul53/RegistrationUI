package com.example.registrationui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlin.math.floor


@Composable
fun TicketView(
    navController: NavHostController,
    content: @Composable () -> Unit = {
        Text("Ticket View")
    }
) {


    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(300.dp)
            .shadow(
                2.dp,
                shape = CustomShape(),
                clip = true
            )
            .background(Color.White)
    ) {

        Box(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }



}

class TicketShape(
    private val teethWidthDp: Float,
    private val teethHeightDp: Float
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(Path().apply {

        moveTo(
            size.width * 0.99f,
            size.height * 0.01f
        )

        val teethHeightPx = teethHeightDp * density.density
        var fullTeethWidthPx = teethWidthDp * density.density
        var halfTeethWidthPx = fullTeethWidthPx / 2
        var currentDrawPositionX = size.width * 0.99f
        var teethBasePositionY = size.height * 0.01f + teethHeightPx
        val shapeWidthPx = size.width * 0.99f - size.width * 0.01f

        val teethCount = shapeWidthPx / fullTeethWidthPx
        val minTeethCount = floor(teethCount)

        // logic to find optimized count of teethes to fit the available width without overflowing
        // or underflowing teethes, by modifying the teeth width
        if (teethCount != minTeethCount) { // check to allow drawing if shape width is a multiple of teeth count
            val newTeethWidthPx = shapeWidthPx / minTeethCount
            fullTeethWidthPx = newTeethWidthPx
            halfTeethWidthPx = fullTeethWidthPx / 2
        }

        var drawnTeethCount = 1 // considering we will draw half of first and last teeth
        // statically we start with one teeth

        // draw half of first teeth
        lineTo(
            currentDrawPositionX - halfTeethWidthPx,
            teethBasePositionY + teethHeightPx
        )

        // draw remaining teethes
        while (drawnTeethCount < minTeethCount) {

            currentDrawPositionX -= halfTeethWidthPx

            // draw right half of teeth
            lineTo(
                currentDrawPositionX - halfTeethWidthPx,
                teethBasePositionY - teethHeightPx
            )

            currentDrawPositionX -= halfTeethWidthPx

            // draw left half of teeth
            lineTo(
                currentDrawPositionX - halfTeethWidthPx,
                teethBasePositionY + teethHeightPx
            )

            drawnTeethCount++
        }

        currentDrawPositionX -= halfTeethWidthPx

        // draw half of last teeth
        lineTo(
            currentDrawPositionX - halfTeethWidthPx,
            teethBasePositionY - teethHeightPx
        )

        // draw left edge
        lineTo(
            size.width * 0.01f,
            size.height * 0.99f
        )

        drawnTeethCount = 1
        teethBasePositionY = size.height * 0.99f - teethHeightPx
        currentDrawPositionX = size.width * 0.01f

        // draw half of first teeth
        lineTo(
            currentDrawPositionX,
            teethBasePositionY + teethHeightPx
        )

        lineTo(
            currentDrawPositionX + halfTeethWidthPx,
            teethBasePositionY - teethHeightPx
        )

        // draw remaining teethes
        while (drawnTeethCount < minTeethCount) {

            currentDrawPositionX += halfTeethWidthPx

            // draw left half of teeth
            lineTo(
                currentDrawPositionX + halfTeethWidthPx,
                teethBasePositionY + teethHeightPx
            )

            currentDrawPositionX += halfTeethWidthPx

            // draw right half of teeth
            lineTo(
                currentDrawPositionX + halfTeethWidthPx,
                teethBasePositionY - teethHeightPx
            )

            drawnTeethCount++
        }

        currentDrawPositionX += halfTeethWidthPx

        // draw half of last teeth
        lineTo(
            currentDrawPositionX + halfTeethWidthPx,
            teethBasePositionY + teethHeightPx
        )

        // left edge will automatically be drawn to close the path with the top-left arc
        close()
    })

}


//another
class BottomTicketShape(
    private val teethWidthDp: Float,
    private val teethHeightDp: Float
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(Path().apply {
        // Move to top-left corner
        moveTo(0f, 0f)

        // Draw straight line across the top edge
        lineTo(size.width, 0f)

        // Draw straight line down the right edge
        lineTo(size.width, size.height - teethHeightDp * density.density)

        // Now draw the teeth pattern on the bottom edge
        val teethHeightPx = teethHeightDp * density.density
        var fullTeethWidthPx = teethWidthDp * density.density
        var halfTeethWidthPx = fullTeethWidthPx / 2
        var currentDrawPositionX = size.width
        val shapeWidthPx = size.width

        val teethCount = shapeWidthPx / fullTeethWidthPx
        val minTeethCount = floor(teethCount)

        // Adjust the teeth width to fit exactly across the width of the shape
        if (teethCount != minTeethCount) {
            val newTeethWidthPx = shapeWidthPx / minTeethCount
            fullTeethWidthPx = newTeethWidthPx
            halfTeethWidthPx = fullTeethWidthPx / 2
        }

        var drawnTeethCount = 0

        // Draw the teeth
        while (drawnTeethCount < minTeethCount) {
            currentDrawPositionX -= fullTeethWidthPx

            // Draw bottom half of the tooth
            lineTo(
                currentDrawPositionX + halfTeethWidthPx,
                size.height
            )

            // Draw top half of the tooth
            lineTo(
                currentDrawPositionX,
                size.height - teethHeightPx
            )

            drawnTeethCount++
        }

        // Draw the last half-tooth
        lineTo(halfTeethWidthPx, size.height)
        lineTo(0f, size.height - teethHeightPx)

        // Draw straight line up the left edge to close the path
        lineTo(0f, 0f)

        close()
    })
}

class WeaveShape(
    private val weaveWidthDp: Float,
    private val weaveHeightDp: Float
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(Path().apply {
        // Move to top-left corner
        moveTo(0f, 0f)

        // Draw straight line across the top edge
        lineTo(size.width, 0f)

        // Draw straight line down the right edge
        lineTo(size.width, size.height - weaveHeightDp * density.density)

        // Now draw the weave pattern on the bottom edge
        val weaveHeightPx = weaveHeightDp * density.density
        var fullWeaveWidthPx = weaveWidthDp * density.density
        var halfWeaveWidthPx = fullWeaveWidthPx / 2
        var currentDrawPositionX = size.width
        val shapeWidthPx = size.width

        val weaveCount = shapeWidthPx / fullWeaveWidthPx
        val minWeaveCount = floor(weaveCount)

        // Adjust the weave width to fit exactly across the width of the shape
        if (weaveCount != minWeaveCount) {
            val newWeaveWidthPx = shapeWidthPx / minWeaveCount
            fullWeaveWidthPx = newWeaveWidthPx
            halfWeaveWidthPx = fullWeaveWidthPx / 2
        }

        var drawnWeaveCount = 0
        var goingUp = true

        // Draw the weave pattern
        while (drawnWeaveCount < minWeaveCount) {
            currentDrawPositionX -= fullWeaveWidthPx

            if (goingUp) {
                // Draw upward part of the weave
                lineTo(
                    currentDrawPositionX + halfWeaveWidthPx,
                    size.height - weaveHeightPx
                )
                lineTo(
                    currentDrawPositionX,
                    size.height
                )
            } else {
                // Draw downward part of the weave
                lineTo(
                    currentDrawPositionX + halfWeaveWidthPx,
                    size.height + weaveHeightPx
                )
                lineTo(
                    currentDrawPositionX,
                    size.height
                )
            }

            goingUp = !goingUp // Alternate between up and down
            drawnWeaveCount++
        }

        // Draw the last part of the weave
        if (goingUp) {
            lineTo(halfWeaveWidthPx, size.height - weaveHeightPx)
        } else {
            lineTo(halfWeaveWidthPx, size.height + weaveHeightPx)
        }
        lineTo(0f, size.height)

        // Draw straight line up the left edge to close the path
        lineTo(0f, 0f)

        close()
    })
}

class CustomShape(private val waveCount: Int = 10): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawBottomTicketPath(size, waveCount = waveCount)
        )
    }
}

fun drawBottomTicketPath(size: Size, waveCount: Int): Path {
    val waveLength = size.width / (waveCount * 2)
    val waveHeight = waveLength / 3

    return Path().apply {
        reset()

        // Start from the top-left corner
        moveTo(0f, 0f)

        // Draw the top side (flat)
        lineTo(size.width, 0f)

        // Draw the right side (vertical)
        lineTo(size.width, size.height)

        // Draw the wave pattern along the bottom edge
        for (i in 0 until waveCount) {
            // Downward arc of the wave (creates the "u" shape)
            arcTo(
                rect = Rect(
                    topLeft = Offset(size.width - waveLength * (2 * i + 1), size.height - waveHeight),
                    bottomRight = Offset(size.width - waveLength * 2 * i, size.height)
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 100f,
                forceMoveTo = false
            )

            // Upward arc of the wave (creates the "n" shape)
            arcTo(
                rect = Rect(
                    topLeft = Offset(size.width - waveLength * (2 * i + 2), size.height - waveHeight),
                    bottomRight = Offset(size.width - waveLength * (2 * i + 1), size.height)
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = -80f,
                forceMoveTo = false
            )
        }

        // Draw the left side (vertical)
        lineTo(0f, size.height)

        // Close the path to the starting point (top-left corner)
        close()
    }
}