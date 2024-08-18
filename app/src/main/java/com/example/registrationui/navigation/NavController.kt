package navigation

import SignupOtpScreen
import UI.LoginScreen
import UI.SignupScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.registrationui.OnboardingUI.IntroScreen
import com.example.registrationui.ui.FAQScreen


@Composable
fun NavController() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("intro") { IntroScreen(navController) }
        composable("otp") { SignupOtpScreen(navController) }
        composable("faq") { FAQScreen(navController) }
    }
}