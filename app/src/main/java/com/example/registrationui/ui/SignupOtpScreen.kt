import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationui.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupOtpScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()

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
                        "Sign Up",
                        color = Color.Black,
                        fontSize = 16.sp
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
            contentAlignment = Alignment.Center // Initially center the content
        ) {
            OTPSubmitView(navController =navController)
        }
    }
}


@Composable
fun OTPSubmitView(navController: NavHostController) {
    var selectedButton by remember { mutableStateOf<Int?>(null) }
    var otpDigits = remember { mutableStateListOf(TextFieldValue(), TextFieldValue(), TextFieldValue(), TextFieldValue(), TextFieldValue(), TextFieldValue()) }
    var canResendOtp by remember { mutableStateOf(true) }
    var timerSeconds by remember { mutableStateOf(0) } // Timer starts at 0

    val focusRequesters = List(otpDigits.size) { FocusRequester() }
    val focusManager = LocalFocusManager.current

    // Timer logic, runs only if the timer is active
    LaunchedEffect(timerSeconds) {
        if (timerSeconds > 0) {
            delay(1000L) // Wait for 1 second
            timerSeconds--
            if (timerSeconds == 0) {
                canResendOtp = true // Enable the Resend OTP button when timer ends
            }
        }
    }

    // Clear focus when the first field is empty
    LaunchedEffect(otpDigits[0].text) {
        if (otpDigits[0].text.isEmpty()) {
            focusManager.clearFocus()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = if (selectedButton == null || selectedButton == 1) Alignment.Center else Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Please complete your payment confirmation Via:",
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ConfirmationButton(
                    imageResourceId = R.drawable.fingerprint_otp,
                    isSelected = selectedButton == 1,
                    onClick = {
                        selectedButton = 1
                        canResendOtp = false
                        timerSeconds = 60 // Start the timer when this option is selected
                    }
                )
                ConfirmationButton(
                    imageResourceId = R.drawable.sms_otp,
                    isSelected = selectedButton == 2,
                    onClick = {
                        selectedButton = 2
                        canResendOtp = false
                        timerSeconds = 60 // Start the timer when this option is selected
                    }
                )
                ConfirmationButton(
                    imageResourceId = R.drawable.email_otp,
                    isSelected = selectedButton == 3,
                    onClick = {
                        selectedButton = 3
                        canResendOtp = false
                        timerSeconds = 60 // Start the timer when this option is selected
                    }
                )
            }

            // Use AnimatedVisibility to show or hide the OTP section with animation
            AnimatedVisibility(visible = selectedButton == 2 || selectedButton == 3) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    Divider(
                        color = Color.Gray.copy(alpha = 0.5f),
                        thickness = 1.dp,
                        modifier = Modifier.width(300.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.otp_image),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(100.dp)
                    )

                    Text(
                        text = "OTP Verification",
                        color = Color(0xFF5C5C5C),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Display dynamic text based on selected button
                    Text(
                        text = when (selectedButton) {
                            2 -> "Enter verification code we just sent to your verified mobile number 0176********40"
                            3 -> "Enter verification code we just sent to your verified email shim*****@gmail.com"
                            else -> ""
                        },
                        fontSize = 12.sp,
                        color = Color(0xFF3B3B3B),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 40.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(top = 20.dp)
                    ) {
                        for (index in otpDigits.indices) {
                            OTPTextField(
                                value = otpDigits[index],
                                onValueChange = { newValue ->
                                    if (newValue.text.length <= 1) {
                                        otpDigits[index] = newValue
                                        when {
                                            newValue.text.isNotEmpty() -> {
                                                if (index < otpDigits.size - 1) {
                                                    focusRequesters[index + 1].requestFocus() // Move to next field
                                                } else {
                                                    // When all fields are filled, dismiss the keyboard
                                                    focusManager.clearFocus()
                                                }
                                            }
                                            newValue.text.isEmpty() -> {
                                                if (index > 0) {
                                                    focusRequesters[index - 1].requestFocus() // Move to previous field
                                                } else {
                                                    // If first field is empty, dismiss the keyboard
                                                    focusManager.clearFocus()
                                                }
                                            }
                                        }
                                    }
                                },
                                focusRequester = focusRequesters[index]
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Didn’t you receive the OTP? ",
                            fontSize = 12.sp,
                            color = Color(0xFF3B3B3B)
                        )
                        if (canResendOtp) {
                            TextButton(onClick = {
                                // Start the timer when Resend OTP is clicked
                                canResendOtp = false
                                timerSeconds = 60 // Start the timer from 60 seconds
                            }) {
                                Text(
                                    text = "Send OTP",
                                    fontSize = 12.sp,
                                    color = Color.Blue
                                )
                            }
                        } else {
                            Text(
                                text = "$timerSeconds seconds",
                                fontSize = 12.sp,
                                color = Color.Blue
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            navController.navigate("faq")
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF014C8F)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp)
                            .height(48.dp)
                    ) {
                        Text(text = "Submit", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OTPTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    focusRequester: FocusRequester
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFF6F6F6),  // Set the background color
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent, // Remove the bottom border when focused
            unfocusedIndicatorColor = Color.Transparent // Remove the bottom border when not focused
        ),
        singleLine = true,
        modifier = Modifier
            .height(50.dp).width(45.dp)
            .background(Color(0xFFF2F2F2), RoundedCornerShape(5.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
            .focusRequester(focusRequester),
        textStyle = androidx.compose.ui.text.TextStyle(
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        keyboardActions = KeyboardActions(
            onDone = {
                // Hide the keyboard when 'Done' action is triggered
                focusRequester.freeFocus()
            }
        )
    )
}










@Composable
fun ConfirmationButton(
    imageResourceId: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF6F6F6)),
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF014C8F) else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Image(
            painter = painterResource(id = imageResourceId),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OTPTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFF6F6F6),
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        modifier = Modifier
            .size(40.dp, 50.dp)
            .background(Color(0xFFF2F2F2), RoundedCornerShape(5.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(5.dp)),
        textStyle = androidx.compose.ui.text.TextStyle(
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}



