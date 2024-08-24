package com.example.registrationui.biomatricAuth

import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import com.example.registrationui.R
import java.util.concurrent.Executor

class CustomBiometricController{
    @Composable
    fun CustomBiometricPrompt(
        title: String,
        subtitle: String,
        onAuthenticate: () -> Unit,
        onCancel: () -> Unit
    ) {
        Dialog(onDismissRequest = onCancel) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Column (horizontalAlignment = Alignment.CenterHorizontally,){
                        IconButton(
                            onClick = onAuthenticate,
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF8AD3C))
                        ) {
                            Icon(
                                modifier = Modifier.size(40.dp),
                                painter = painterResource(id = R.drawable.baseline_fingerprint_24), // Replace with your drawable
                                contentDescription = "Authenticate",
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = onCancel,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White, contentColor = Color.Black
                            )
                        ) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }
    }

    fun startBiometricAuthentication(activity: FragmentActivity, navController: NavHostController, destinationRoute: String) {
        val executor = Executor { it.run() }
        val biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    // Handle success
                    navController.navigate(destinationRoute)

                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    // Handle error
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    // Handle failure
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

}