package UI

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import com.example.registrationui.R
import com.example.registrationui.biomatricAuth.CustomBiometricController

val items = listOf(
    BottomBarItem(
        iconRes = R.drawable.location,
        text = "Location",
        onClick = { /* Handle location click */ }
    ),
    BottomBarItem(
        iconRes = R.drawable.contact,
        text = "Contact",
        onClick = { /* Handle contact click */ }
    ),
    BottomBarItem(
        iconRes = R.drawable.explore,
        text = "Explore",
        onClick = { /* Handle explore click */ }
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController){
    val scrollState = rememberScrollState()
    val textState = remember { mutableStateOf("") }


        Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState), verticalArrangement =Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(modifier = Modifier
            .width(300.dp)
            .height(200.dp), painter = painterResource(id = R.drawable.masterpay), contentDescription = "login image")

        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField( modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),value =textState.value , onValueChange = {textState.value = it},
            placeholder ={ Text(text = "User ID")} ,
            singleLine = true,

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF014C8F),
                unfocusedBorderColor = Color(0x54545454), // Optional: define the unfocused border color
                cursorColor = Color(0xFF014C8F),         // Cursor color
                focusedLabelColor = Color(0xFF014C8F),   // Focused label color
                unfocusedLabelColor = Color.Gray
            ),)

        TextButton(modifier = Modifier
            .align(Alignment.End).padding(end = 5.dp), onClick = {}, colors = ButtonDefaults.textButtonColors(
            contentColor = Color(0xFF014C8F)
        ),){
            Text(text = "Forgot User ID")
        }
        PasswordOutlinedTextField()
        TextButton(modifier = Modifier
            .align(Alignment.End).padding(end = 5.dp)
           ,  colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFF014C8F)
            ), onClick = {},){
            Text(text = "Forgot Password")
        }

        TwoButtonsInRow(navController = navController)
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(modifier = Modifier.width(200.dp), thickness = 1.dp)
        SignupSection(navController = navController)
        OutlinedBtn()
        Spacer(modifier = Modifier.height(20.dp))
        BottomBar(items = items)


    }
    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordOutlinedTextField() {
    var passwordVisible by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }

    OutlinedTextField(
        singleLine = true,
        value = password,
        onValueChange = { newPassword -> password = newPassword },
        placeholder = { Text(text = "Password") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF014C8F),
            unfocusedBorderColor = Color(0x54545454), // Optional: define the unfocused border color
            cursorColor = Color(0xFF014C8F),         // Cursor color
            focusedLabelColor = Color(0xFF014C8F),   // Focused label color
            unfocusedLabelColor = Color.Gray
        ),
        trailingIcon = {
            val icon: ImageVector = if (passwordVisible) {
                ImageVector.vectorResource(id = R.drawable.baseline_remove_red_eye_24) // Replace with your visibility off icon
            } else {
                ImageVector.vectorResource(id = R.drawable.baseline_visibility_off_24) // Replace with your visibility icon
            }
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = icon, contentDescription = if (passwordVisible) "Hide password" else "Show password")
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    )
}


@Composable
fun TwoButtonsInRow(navController: NavHostController) {
    val activity = LocalContext.current as FragmentActivity
    var biometricMessage by remember { mutableStateOf("") }
    var showBiometricPrompt by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth() // Make the Row take the full width of the parent
            .padding(start = 16.dp, end = 16.dp), // Optional: Add padding around the Row
        horizontalArrangement = Arrangement.spacedBy(8.dp), // Optional: Space between buttons
        verticalAlignment = Alignment.CenterVertically // Center align buttons vertically
    ) {
        Button(
            onClick = { /* Handle click */ },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor =  Color(0xFF014C8F), // Background color
                contentColor = Color.White // Text color f8ad3c
            ),
            modifier = Modifier
                .weight(4f) // Make this button take more space relative to the other
                .height(53.dp) // Set a fixed height
        ) {
            Text(modifier = Modifier.padding(10.dp), text = "Login")
        }

        Button(
            onClick = {
                //showBiometricPrompt = true
                navController.navigate("ticket")
                      },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor =  Color(0xFFF8AD3C), // Background color
                contentColor = Color.White // Text color f8ad3c
            ),
            modifier = Modifier
                .weight(1.2f) // Make this button take less space relative to the other
                .height(53.dp) // Set a fixed height
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_fingerprint_24), // Replace with your icon resource
                contentDescription = "Example Icon",
                modifier = Modifier.size(100.dp)
            )
            if (showBiometricPrompt) {
                CustomBiometricController().CustomBiometricPrompt(
                    title = "Login with fingerprint",
                    subtitle = "Touch the Fingerprint sensor",
                    onAuthenticate = {
                        showBiometricPrompt = false
                        CustomBiometricController().startBiometricAuthentication(activity, navController = navController,destinationRoute = "otp")
                    },
                    onCancel = {
                        showBiometricPrompt = false
                        // Handle cancellation
                    }
                )
            }
        }
    }
}


@Composable
fun SignupSection(navController: NavHostController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "New in Master Pay ?",
            fontSize = 16.sp,
            color =  Color.Gray

        )

        TextButton(
            onClick = { navController.navigate("intro")},
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFFF8AD3C) // Set the text button color
            )
        ) {
            Text(text = "Sign Up")
        }
    }
}

@Composable
fun OutlinedBtn() {

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, start = 16.dp, end = 16.dp, bottom = 20.dp),
            onClick = { /* Do something */ },
            border = BorderStroke(1.dp, Color(0xFF014C8F)),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF014C8F)
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Image(
                    painter = painterResource(id = R.drawable.bangladesh_bank),
                    contentDescription = "Account Icon",
                    modifier = Modifier
                        .padding(end = 30.dp)
                        .size(35.dp)

                )

                Text(modifier = Modifier.padding(10.dp), text = "Open Bank Account", fontSize = 20.sp)
            }
        }

        }



data class BottomBarItem(
    val iconRes: Int,
    val text: String,
    val onClick: () -> Unit
)




@Composable
fun BottomBar(items: List<BottomBarItem>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    ) {
        items.forEachIndexed { index, item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = item.onClick)
            ) {
                Image(
                    painter = painterResource(id = item.iconRes),
                    contentDescription = "${item.text} Icon",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(vertical = 1.dp)
                )
                Text(
                    text = item.text,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            if (index < items.size - 1) {
                VerticalDivider(
                    modifier = Modifier.height(30.dp),
                    thickness = 1.dp
                )
            }
        }
    }
}





