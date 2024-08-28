package navigation

import CenteredTicketCard
import SignupOtpScreen
import TicketCard
import UI.LoginScreen
import UI.SignupScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.registrationui.OnboardingUI.IntroScreen
import com.example.registrationui.ui.FAQScreen
import com.example.registrationui.ui.PrayerTimeUI
import com.example.registrationui.ui.TasbeehCountUI
import com.example.registrationui.ui.TasbeehViewModel
import com.example.registrationui.ui.TasbeehViewModelFactory

import com.example.registrationui.ui.TicketView
import com.example.registrationui.ui.TransactionStatus
import java.util.concurrent.TimeUnit


@Composable
fun NavController() {

    val navController = rememberNavController()
    NavHost(navController, startDestination = "prayerTime") {
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("intro") { IntroScreen(navController) }
        composable("otp") { SignupOtpScreen(navController) }
        composable("faq") { FAQScreen(navController) }
        composable("transactionReceipt") { CenteredTicketCard(navController) }
        composable("transactionStatus") { TransactionStatus(navController) }
        composable("prayerTime") { PrayerTimeUI(navController) }
        composable("tasbeehCount") {
            // Obtain the TasbeehViewModel
            val tasbeehViewModel: TasbeehViewModel = viewModel(
                factory = TasbeehViewModelFactory(LocalContext.current)
            )
            // Pass the ViewModel to the TasbeehCountUI composable
            TasbeehCountUI(navController, tasbeehViewModel)
        }
    }
}