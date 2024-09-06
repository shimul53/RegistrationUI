package com.example.registrationui.ui

import UI.DatePickerText
import UI.TermsSection
import UI.UserTextFields
import UI.nextBtn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationui.R
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EMICalculator(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    var sliderValue by remember { mutableStateOf(0f) } // Starting value of the slider
    val valueRange = 0f..100f // The range of the slider


    Scaffold(
        containerColor = Color.White,
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxSize()
            .imePadding(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    actionIconContentColor = Color.Black,
                    navigationIconContentColor = Color.Black,
                    scrolledContainerColor = Color.White,
                    titleContentColor = Color.Black
                ),
                title = {
                    Text(
                        "EMI Calculator",
                        color = Color.Black,
                        fontSize = 18.sp, fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { navController.popBackStack() } }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            contentAlignment = Alignment.TopStart
        ) {
            Column (modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White), verticalArrangement =Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally){
               // var sliderWeight by remember { mutableStateOf(0f) }



               /* CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    Slider(
                        value = sliderWeight,
                        onValueChange = { sliderWeight = it },
                    )
                }*/

                SwipableButton(navController = navController)


                
                /*  SliderWithLabel( value = sliderValue,
                    valueRange = valueRange,
                    finiteEnd = true,
                    labelMinWidth = 36.dp,
                    onRadiusChange = { newValue ->
                        sliderValue = newValue.toFloat()
                    })*/

            }

        }
    }
    
    
}


@Composable
fun SwipableButton(navController: NavHostController) {
    // State to track which button is clicked
    var selectedButton by remember { mutableStateOf(1) }
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEDEDED)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Button 1
            if (selectedButton == 1) {
                Button(
                    onClick = { selectedButton = 1 },
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(2.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White, // Background color
                        contentColor = Color.Black // Text color
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(53.dp)

                ) {
                    Text(
                        text = "Loan",
                        fontSize = 16.sp
                    )
                }
            } else {
                Text(
                    text = "Loan",
                    modifier = Modifier
                        .weight(1f)
                        .height(53.dp)
                        .padding(16.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) { selectedButton = 1 },
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Button 2
            if (selectedButton == 2) {
                Button(
                    onClick = { selectedButton = 2 },
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(2.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White, // Background color
                        contentColor = Color.Black // Text color
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(53.dp)
                ) {
                    Text(
                        text = "Fixed Deposit",
                        fontSize = 16.sp
                    )
                }
            } else {
                Text(
                    text = "Fixed Deposit",
                    modifier = Modifier
                        .weight(1f)
                        .height(53.dp)
                        .padding(16.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) { selectedButton = 2 },
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }



    }
    // Conditionally render RegularUserTextFields
    if (selectedButton == 1) {
        EMICalculatorTextFields(navController = navController,selectedButton = 1)
    } else if (selectedButton == 2) {
        EMICalculatorTextFields(navController = navController,selectedButton = 2)
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EMICalculatorTextFields(navController: NavHostController,selectedButton: Int){
    val scrollState = rememberScrollState()
    val userIDState = remember { mutableStateOf("") }
    val accountNumberState = remember { mutableStateOf("") }
    val accountNameState = remember { mutableStateOf("") }
    val mobileNumberState = remember { mutableStateOf("") }
    val nidNoState = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    var sliderValue by remember { mutableFloatStateOf(0f) }
    Column(modifier = Modifier.padding(bottom = 20.dp, top = 20.dp)
        , verticalArrangement =Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {

        //user id
        Text(text = if (selectedButton == 1)"Loan Amount" else "Principal Amount",fontWeight = FontWeight.SemiBold, modifier = Modifier
            .align(Alignment.Start)
            .padding(start = 16.dp))
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),value =userIDState.value , onValueChange = {userIDState.value = it},
            singleLine = true,
            placeholder ={ Text(text = if (selectedButton == 1) "Enter Loan Amount" else "Enter Principal Amount"  )} ,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF014C8F),
                unfocusedBorderColor = Color(0x54545454), // Optional: define the unfocused border color
                cursorColor = Color(0xFF014C8F),         // Cursor color
                focusedLabelColor = Color(0xFF014C8F),   // Focused label color
                unfocusedLabelColor = Color.Gray,
                containerColor = Color(0xFBFBFBFB),
            ),)
        Spacer(modifier = Modifier.height(15.dp))

        Text(text = "Interest Rate", fontWeight = FontWeight.SemiBold, modifier = Modifier
            .align(Alignment.Start)
            .padding(start = 16.dp))
        Spacer(modifier = Modifier.height(5.dp))
        customSliderUi(value = sliderValue, onValueChange = {sliderValue =it})
        Spacer(modifier = Modifier.height(15.dp))




        //Loan Duration
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Text(
                text = if (selectedButton == 1) "Loan Duration " else "Duration " , textAlign = TextAlign.Start,
                 fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(textAlign = TextAlign.Start,
                text = "(Tenure in Year)",
            )
        }
        Spacer(modifier = Modifier.height(5.dp))


        OutlinedTextField( modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),value =accountNameState.value , onValueChange = {accountNameState.value = it},
            placeholder ={ Text(text = if (selectedButton == 1) "Enter Loan Duration" else "Enter Investment Duration")} ,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF014C8F),
                unfocusedBorderColor = Color(0x54545454), // Optional: define the unfocused border color
                cursorColor = Color(0xFF014C8F),         // Cursor color
                focusedLabelColor = Color(0xFF014C8F),   // Focused label color
                unfocusedLabelColor = Color.Gray,
                containerColor = Color(0xFBFBFBFB),
            ),)

        Spacer(modifier = Modifier.height(15.dp))
        //account number
        if (selectedButton == 2){
            Text(text = "Frequency",fontWeight = FontWeight.SemiBold, modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp))
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField( modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),value =accountNumberState.value , onValueChange = {accountNumberState.value = it},
                placeholder ={ Text(text = "Enter Frequency")} ,
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF014C8F),
                    unfocusedBorderColor = Color(0x54545454), // Optional: define the unfocused border color
                    cursorColor = Color(0xFF014C8F),         // Cursor color
                    focusedLabelColor = Color(0xFF014C8F),   // Focused label color
                    unfocusedLabelColor = Color.Gray ,
                    containerColor = Color(0xFBFBFBFB),
                ),)

            Spacer(modifier = Modifier.height(15.dp))
        }


        submitBtn(navController =  navController)

        EMITotalCardContainer(selectedButton = selectedButton)


    }


}

@Composable
fun submitBtn(navController: NavHostController) {
    Button(
        onClick = {
           // navController.navigate("otp")
                  },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent, // Set background to transparent to use gradient
        ),
        contentPadding = PaddingValues(), // Remove padding to match gradient size
        modifier = Modifier
            .height(53.dp)
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp, top = 20.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                ),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            text = "Submit",
            fontSize = 20.sp,
            color = Color.White // Text color
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
}


@Composable
fun EMITotalCardContainer(selectedButton: Int) {
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column (modifier = Modifier

                .padding(top = 20.dp)) {
                // Content inside the Card
                Text(
                    text = if (selectedButton == 1) "Your EMI per Month is" else "Your Total Interest Earned",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.taka_img),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(2.dp))
                    GradientText(
                        text = "1500.00",
                        gradient = Brush.linearGradient(
                            colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                        ),
                        fontSize = 32.sp
                    )



                }



                Spacer(modifier = Modifier.height(10.dp))

                Divider(
                    color = Color.Gray.copy(alpha = 0.5f),
                    thickness = 1.dp, modifier = Modifier.padding(start = 20.dp,  end = 20.dp)

                    )

                if (selectedButton == 1){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 30.dp, bottom = 5.dp, start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween // Space between the texts
                    ) {
                        Text(text = "Total Payable Interest", color = Color.Black, fontSize = 12.sp, modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.dp), textAlign = TextAlign.Start)
                        Text(text = "16000.00", color = Color.Black, fontSize = 12.sp,modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.dp),textAlign = TextAlign.End)
                    }




                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 10.dp, bottom = 30.dp, start = 16.dp, end = 16.dp),

                        horizontalArrangement = Arrangement.SpaceBetween // Space between the texts
                    ) {

                        Text(text = "Total Payment (Principal + Interest)", color = Color.Black, fontSize = 12.sp, modifier = Modifier
                            .weight(2f)
                            .padding(start = 5.dp), textAlign = TextAlign.Start)
                        Text(text = "520000.00", color = Color.Black, fontSize = 12.sp,modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.dp),textAlign = TextAlign.End)
                    }
                }
                else{
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 10.dp, bottom = 30.dp, start = 16.dp, end = 16.dp),

                        horizontalArrangement = Arrangement.SpaceBetween // Space between the texts
                    ) {

                        Text(text = "Total Maturity Value", color = Color.Black, fontSize = 12.sp, modifier = Modifier
                            .weight(2f)
                            .padding(start = 5.dp), textAlign = TextAlign.Start)
                        Text(text = "1625000.00", color = Color.Black, fontSize = 12.sp,modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.dp),textAlign = TextAlign.End)
                    }
                }


            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun customSliderUi(value: Float,onValueChange: (Float) -> Unit,){

    CustomSlider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        value = value,
        onValueChange = onValueChange,
        valueRange = 0f..100f,
        gap = 1,
        showIndicator = false,
        thumb = { thumbValue ->
            CustomSliderDefaults.Thumb(
                thumbValue = "$thumbValue%",
                color = Color.Transparent,
                size = 40.dp,
                modifier = Modifier.background(
                    brush = Brush.linearGradient(listOf(Color(0xff40A0F5), Color(0xff085BA6))),
                    shape = CircleShape
                )
            )
        },
        track = { sliderState ->
            Box(
                modifier = Modifier
                    .track()
                    .border(
                        width = 1.dp,
                        color = Color.LightGray.copy(alpha = 0.4f),
                        shape = CircleShape
                    )
                    .background(Color.LightGray),

                contentAlignment = Alignment.CenterStart
            ) {
                Box(
                    modifier = Modifier
                        .progress(sliderState = sliderState)
                        .background(
                            brush = Brush.linearGradient(
                                listOf(
                                    Color(0xff40A0F5),
                                    Color(0xff085BA6)
                                )
                            )
                        )
                )
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = ValueRange,
    gap: Int = Gap,
    showIndicator: Boolean = false,
    showLabel: Boolean = false,
    enabled: Boolean = true,
    thumb: @Composable (thumbValue: Int) -> Unit = {
        CustomSliderDefaults.Thumb(it.toString())
    },
    track: @Composable (sliderState: SliderState) -> Unit = { sliderState ->
        CustomSliderDefaults.Track(sliderState = sliderState)
    },
    indicator: @Composable (indicatorValue: Int) -> Unit = { indicatorValue ->
        CustomSliderDefaults.Indicator(indicatorValue = indicatorValue.toString())
    },
    label: @Composable (labelValue: Int) -> Unit = { labelValue ->
        CustomSliderDefaults.Label(labelValue = labelValue.toString())
    }
) {
    val itemCount = (valueRange.endInclusive - valueRange.start).roundToInt()
    val steps = if (gap == 1) 0 else (itemCount / gap - 1)

    Box(modifier = modifier) {
        Layout(
            measurePolicy = customSliderMeasurePolicy(
                itemCount = itemCount,
                gap = gap,
                value = value,
                startValue = valueRange.start
            ),
            content = {
                if (showLabel)
                    Label(
                        modifier = Modifier.layoutId(CustomSliderComponents.LABEL),
                        value = value,
                        label = label
                    )

                Box(modifier = Modifier.layoutId(CustomSliderComponents.THUMB)) {
                    thumb(value.roundToInt())
                }

                Slider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .layoutId(CustomSliderComponents.SLIDER),
                    value = value,
                    valueRange = valueRange,
                    steps = steps,
                    onValueChange = { onValueChange(it) },
                    thumb = {
                        thumb(value.roundToInt())
                    },
                    track = { track(it) },
                    enabled = enabled
                )

                if (showIndicator)
                    Indicator(
                        modifier = Modifier.layoutId(CustomSliderComponents.INDICATOR),
                        valueRange = valueRange,
                        gap = gap,
                        indicator = indicator
                    )
            })
    }
}

@Composable
private fun Label(
    modifier: Modifier = Modifier,
    value: Float,
    label: @Composable (labelValue: Int) -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        label(value.roundToInt())
    }
}

@Composable
private fun Indicator(
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float>,
    gap: Int,
    indicator: @Composable (indicatorValue: Int) -> Unit
) {
    // Iterate over the value range and display indicators at regular intervals.
    for (i in valueRange.start.roundToInt()..valueRange.endInclusive.roundToInt() step gap) {
        Box(
            modifier = modifier
        ) {
            indicator(i)
        }
    }
}

private fun customSliderMeasurePolicy(
    itemCount: Int,
    gap: Int,
    value: Float,
    startValue: Float
) = MeasurePolicy { measurables, constraints ->
    // Measure the thumb component and calculate its radius.
    val thumbPlaceable = measurables.first {
        it.layoutId == CustomSliderComponents.THUMB
    }.measure(constraints)
    val thumbRadius = (thumbPlaceable.width / 2).toFloat()

    val indicatorPlaceables = measurables.filter {
        it.layoutId == CustomSliderComponents.INDICATOR
    }.map { measurable ->
        measurable.measure(constraints)
    }
    val indicatorHeight = indicatorPlaceables.maxByOrNull { it.height }?.height ?: 0

    val sliderPlaceable = measurables.first {
        it.layoutId == CustomSliderComponents.SLIDER
    }.measure(constraints)
    val sliderHeight = sliderPlaceable.height

    val labelPlaceable = measurables.find {
        it.layoutId == CustomSliderComponents.LABEL
    }?.measure(constraints)
    val labelHeight = labelPlaceable?.height ?: 0

    // Calculate the total width and height of the custom slider layout
    val width = sliderPlaceable.width
    val height = labelHeight + sliderHeight + indicatorHeight

    // Calculate the available width for the track (excluding thumb radius on both sides).
    val trackWidth = width - (2 * thumbRadius)

    // Calculate the width of each section in the track.
    val sectionWidth = trackWidth / itemCount
    // Calculate the horizontal spacing between indicators.
    val indicatorSpacing = sectionWidth * gap

    // To calculate offset of the label, first we will calculate the progress of the slider
    // by subtracting startValue from the current value.
    // After that we will multiply this progress by the sectionWidth.
    // Add thumb radius to this resulting value.
    val labelOffset = (sectionWidth * (value - startValue)) + thumbRadius

    layout(width = width, height = height) {
        var indicatorOffsetX = thumbRadius
        // Place label at top.
        // We have to subtract the half width of the label from the labelOffset,
        // to place our label at the center.
        labelPlaceable?.placeRelative(
            x = (labelOffset - (labelPlaceable.width / 2)).roundToInt(),
            y = 0
        )

        // Place slider placeable below the label.
        sliderPlaceable.placeRelative(x = 0, y = labelHeight)

        // Place indicators below the slider.
        indicatorPlaceables.forEach { placeable ->
            // We have to subtract the half width of the each indicator from the indicatorOffset,
            // to place our indicators at the center.
            placeable.placeRelative(
                x = (indicatorOffsetX - (placeable.width / 2)).roundToInt(),
                y = labelHeight + sliderHeight
            )
            indicatorOffsetX += indicatorSpacing
        }
    }
}

/**
 * Object to hold defaults used by [CustomSlider]
 */
object CustomSliderDefaults {

    /**
     * Composable function that represents the thumb of the slider.
     *
     * @param thumbValue The value to display on the thumb.
     * @param modifier The modifier for styling the thumb.
     * @param color The color of the thumb.
     * @param size The size of the thumb.
     * @param shape The shape of the thumb.
     */
    @Composable
    fun Thumb(
        thumbValue: String,
        modifier: Modifier = Modifier,
        color: Color = PrimaryColor,
        size: Dp = ThumbSize,
        shape: Shape = CircleShape,
        content: @Composable () -> Unit = {
            Text(
                text = thumbValue,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    ) {
        Box(
            modifier = modifier
                .thumb(size = size, shape = shape)
                .background(color)
                .padding(2.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }

    /**
     * Composable function that represents the track of the slider.
     *
     * @param sliderState The state of the slider.
     * @param modifier The modifier for styling the track.
     * @param trackColor The color of the track.
     * @param progressColor The color of the progress.
     * @param height The height of the track.
     * @param shape The shape of the track.
     */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Track(
        sliderState: SliderState,
        modifier: Modifier = Modifier,
        trackColor: Color = TrackColor,
        progressColor: Color = PrimaryColor,
        height: Dp = TrackHeight,
        shape: Shape = CircleShape
    ) {
        Box(
            modifier = modifier
                .track(height = height, shape = shape)
                .background(trackColor)
        ) {
            Box(
                modifier = Modifier
                    .progress(
                        sliderState = sliderState,
                        height = height,
                        shape = shape
                    )
                    .background(progressColor)
            )
        }
    }

    /**
     * Composable function that represents the indicator of the slider.
     *
     * @param indicatorValue The value to display as the indicator.
     * @param modifier The modifier for styling the indicator.
     * @param style The style of the indicator text.
     */
    @Composable
    fun Indicator(
        indicatorValue: String,
        modifier: Modifier = Modifier,
        style: TextStyle = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Normal)
    ) {
        Box(modifier = modifier) {
            Text(
                text = indicatorValue,
                style = style,
                textAlign = TextAlign.Center
            )
        }
    }

    /**
     * Composable function that represents the label of the slider.
     *
     * @param labelValue The value to display as the label.
     * @param modifier The modifier for styling the label.
     * @param style The style of the label text.
     */
    @Composable
    fun Label(
        labelValue: String,
        modifier: Modifier = Modifier,
        style: TextStyle = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal)
    ) {
        Box(modifier = modifier) {
            Text(
                text = labelValue,
                style = style,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun Modifier.track(
    height: Dp = TrackHeight,
    shape: Shape = CircleShape
) = this
    .fillMaxWidth()
    .heightIn(min = height)
    .clip(shape)

@OptIn(ExperimentalMaterial3Api::class)
fun Modifier.progress(
    sliderState: SliderState,
    height: Dp = TrackHeight,
    shape: Shape = CircleShape
) = this
    // Compute the fraction based on the slider's current value.
    // We do this by dividing the current value by the total value.
    // However, the start value might not always be 0, so we need to
    // subtract the start value from both the current value and the total value.
    .fillMaxWidth(fraction = (sliderState.value - sliderState.valueRange.start) / (sliderState.valueRange.endInclusive - sliderState.valueRange.start))
    .heightIn(min = height)
    .clip(shape)

fun Modifier.thumb(
    size: Dp = ThumbSize,
    shape: Shape = CircleShape
) = this
    .defaultMinSize(minWidth = size, minHeight = size)
    .clip(shape)

private enum class CustomSliderComponents {
    SLIDER, LABEL, INDICATOR, THUMB
}

val PrimaryColor = Color(0xFF6650a4)
val TrackColor = Color(0xFFE7E0EC)

private const val Gap = 1
private val ValueRange = 0f..10f
private val TrackHeight = 8.dp
private val ThumbSize = 30.dp




/*private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f,0.0f,0.0f,0.0f)

}*/

/*


@Composable
fun SliderWithLabel(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    finiteEnd: Boolean,
    labelMinWidth: Dp = 24.dp,
    onRadiusChange: (String) -> Unit
) {

    Column(modifier = Modifier.padding(16.dp)) {

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
        ) {



            val offset = getSliderOffset(
                value = value,
                valueRange = valueRange,
                boxWidth = maxWidth,
                labelWidth = labelMinWidth + 8.dp // Since we use a padding of 4.dp on either sides of the SliderLabel, we need to account for this in our calculation
            )

            val endValueText =
                if (!finiteEnd && value >= valueRange.endInclusive) "${
                    value.toInt()
                }+" else value.toInt().toString()


           // SliderLabel(label = valueRange.start.toInt().toString(), minWidth = labelMinWidth)

            if (value >= valueRange.start) {
                SliderLabel(
                    label = endValueText, minWidth = labelMinWidth, modifier = Modifier
                        .padding(start = offset)
                )
            }
        }


        Slider(
            value = value, onValueChange = {
                onRadiusChange(it.toString())
            },
            valueRange = valueRange,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderColors(activeTrackColor = Color.Blue,
                activeTickColor = Color.Blue,
                thumbColor = Color.Blue,
                disabledThumbColor = Color.Blue,
                inactiveTickColor = Color.Gray,
                inactiveTrackColor = Color.Gray,
                disabledInactiveTickColor = Color.Gray,
                disabledInactiveTrackColor = Color.Gray,
                disabledActiveTrackColor = Color.Gray,
                disabledActiveTickColor = Color.Gray)
        )

    }
}


@Composable
fun SliderLabel(label: String, minWidth: Dp, modifier: Modifier = Modifier) {
    Text(
        label,
        textAlign = TextAlign.Center,
        color = Color.White,
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                ),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(4.dp)
            .defaultMinSize(minWidth = minWidth)
    )
}




private fun getSliderOffset(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    boxWidth: Dp,
    labelWidth: Dp
): Dp {

    val coerced = value.coerceIn(valueRange.start, valueRange.endInclusive)
    val positionFraction = calcFraction(valueRange.start, valueRange.endInclusive, coerced)

    return (boxWidth - labelWidth) * positionFraction
}


// Calculate the 0..1 fraction that `pos` value represents between `a` and `b`
private fun calcFraction(a: Float, b: Float, pos: Float) =
    (if (b - a == 0f) 0f else (pos - a) / (b - a)).coerceIn(0f, 1f)
*/

