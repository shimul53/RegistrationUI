import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationui.R

@Composable
fun CenteredTicketCard(
    navController: NavHostController,
    circleRadius: Dp = 10.dp,
    cornerSize: CornerSize = CornerSize(8.dp),
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize(), // This will fill the entire screen
        contentAlignment = Alignment.Center // This will align the content (TicketCard) in the center
    ) {
        TicketCard(
            navController = navController,
            circleRadius = circleRadius,
            cornerSize = cornerSize,
            modifier = modifier
        )
    }
}

@Composable
fun TicketCard(
    navController: NavHostController,
    circleRadius: Dp = 10.dp,
    cornerSize: CornerSize = CornerSize(8.dp),
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 70.dp, top = 70.dp)
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        // Ticket Card with custom shape
        Card(
            shape = TicketShapeUI(circleRadius = circleRadius, cornerSize = cornerSize),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
                .align(Alignment.Center)
        ) {
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)){
                // Content inside the Card
                Text(text = "Payment Receipt !",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally))
                
                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Your payment has been successfully done.",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally))

                Divider(
                    color = Color.Gray.copy(alpha = 0.5f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 30.dp)
                )

                Text(text = "Total Transfer",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally))

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "BDT 1,000,000",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally))

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "REF 000085752257",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally))

                Spacer(modifier = Modifier.height(20.dp))

                CardGridView()

                Spacer(modifier = Modifier.height(10.dp))
                downloadBtn()
            }

        }

        // Overlay Circle with Check Icon
        Box(
            contentAlignment = Alignment.Center, // Align the icon in the center of the circle
            modifier = Modifier
                .height(80.dp)
                .width(80.dp)
                .align(Alignment.TopCenter)
                .shadow(elevation = 4.dp, shape = CircleShape, clip = false)
        ) {
            Canvas(
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
            ) {
                drawCircle(
                    color = Color.White,
                    radius = 40.dp.toPx(),
                    center = Offset(size.width / 2, size.height / 2)
                )
            }

            // Check Icon inside the Circle
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_check_circle_24), // Use your check icon here
                contentDescription = "Check Icon",
                tint = Color(0xff23a26d),
                modifier = Modifier.size(60.dp) // Size of the icon inside the circle
            )
        }
    }
}


class TicketShapeUI(
    private val circleRadius: Dp,
    private val cornerSize: CornerSize
) : Shape {

    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        return Outline.Generic(path = getPath(size, density))
    }

    private fun getPath(size: Size, density: Density): Path {
        val roundedRect = RoundRect(size.toRect(), CornerRadius(cornerSize.toPx(size, density)))
        val roundedRectPath = Path().apply { addRoundRect(roundedRect) }
        return Path.combine(operation = PathOperation.Intersect, path1 = roundedRectPath, path2 = getTicketPath(size, density))
    }

    private fun getTicketPath(size: Size, density: Density): Path {
        val circleRadiusInPx = with(density) { circleRadius.toPx() }
        val numberOfArcs = 14
        val totalSpaceForArcs = size.width * 0.8f  // 80% of width for arcs, 20% for gaps
        val arcWidth = totalSpaceForArcs / numberOfArcs
        val spaceBetweenArcs = (size.width - totalSpaceForArcs) / (numberOfArcs - 5)

        return Path().apply {
            reset()
            // Start at the top left corner
            lineTo(x = 0F, y = 0F)
            // Draw the top edge to the top right corner
            lineTo(x = size.width, y = 0F)
            // Draw the right edge to the bottom right corner
            lineTo(x = size.width, y = size.height)

            // Draw 10 arcs along the bottom line with spaces in between
            for (i in 0 until numberOfArcs) {
                val startX = i * (arcWidth + spaceBetweenArcs)
                val arcCenterX = startX + arcWidth / 2

                val arcRect = Rect(
                    left = arcCenterX - circleRadiusInPx,
                    top = size.height - circleRadiusInPx,
                    right = arcCenterX + circleRadiusInPx,
                    bottom = size.height + circleRadiusInPx
                )

                // Move to the starting point of the arc
                if (i == 0) {
                    lineTo(x = arcRect.left + circleRadiusInPx, y = size.height)
                } else {
                    lineTo(x = startX, y = size.height)
                }

                // Draw the arc
                arcTo(
                    rect = arcRect,
                    startAngleDegrees = 0F,
                    sweepAngleDegrees = -180F,
                    forceMoveTo = false
                )
            }

            // Draw line to bottom left
            lineTo(x = 0F, y = size.height)
            // Draw line back to top left
            lineTo(x = 0F, y = 0F)
        }
    }
}


@Composable
fun CardGridView() {
    // Outer Box for padding and alignment
    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        // Column to arrange rows vertically
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),

        ) {
            // Row 1
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CardView("Payment Method", "NPSB Transfer", Modifier.weight(1f))
                CardView("Receiver Name", "Mir Faizul Hauque", Modifier.weight(1f))
            }

            // Row 2
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CardView("Receiver Account", "1171510093316", Modifier.weight(1f))
                CardView("Receiver Bank Name", "The City Bank LTD.", Modifier.weight(1f))
            }

            // Row 3
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CardView("Ref Number", "000085752257", Modifier.weight(1f))
                CardView("Payment Time", "20 AUG 2023, 13:22", Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun CardView(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier.padding(5.dp).border(width = 1.dp, color = Color(0xfff1f1f1), shape = MaterialTheme.shapes.small)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(text = title, color = Color.Gray)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = subtitle, color = Color.Black)
        }
    }
}

@Composable
fun downloadBtn() {

    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, start = 50.dp, end = 50.dp, bottom = 20.dp),
        onClick = { /* Do something */ },
        border = BorderStroke(1.dp, Color(0xFF1c73c2)),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color(0xFF1c73c2)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            Image(
                painter = painterResource(id = R.drawable.transaction_receipt_btn_icon),
                contentDescription = "Account Icon",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(35.dp)

            )

            Text(modifier = Modifier.padding(10.dp), color = Color.Black, text = "Get PDF Receipt", fontSize = 20.sp)
        }
    }

}

