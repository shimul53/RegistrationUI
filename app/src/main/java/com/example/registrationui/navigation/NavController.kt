package navigation

import CenteredTicketCard
import SignupOtpScreen
import TicketCard
import UI.LoginScreen
import UI.SignupScreen
import android.os.Build
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.registrationui.OnboardingUI.IntroScreen
import com.example.registrationui.QiblaFinder.MainViewModel
import com.example.registrationui.QiblaFinder.QiblaScreen
import com.example.registrationui.billsPay.BillsPayItem
import com.example.registrationui.billsPay.BillsPayItemUI
import com.example.registrationui.billsPay.BillsPayUI
import com.example.registrationui.billsPay.BillsPaymentScreen
import com.example.registrationui.location.AgentBankingLocationUI
import com.example.registrationui.location.FindMeItemUI
import com.example.registrationui.location.LocationListUI
import com.example.registrationui.models.PrayerTimeItemModel
import com.example.registrationui.models.Timings
import com.example.registrationui.ui.EMICalculator
import com.example.registrationui.ui.FAQScreen
import com.example.registrationui.ui.PrayerTimeUI
import com.example.registrationui.ui.TasbeehCountUI
import com.example.registrationui.ui.TasbeehViewModel
import com.example.registrationui.ui.TasbeehViewModelFactory

import com.example.registrationui.ui.TicketView
import com.example.registrationui.ui.TransactionStatus
import java.util.concurrent.TimeUnit



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavController(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "location") {
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("intro") { IntroScreen(navController) }
        composable("otp") { SignupOtpScreen(navController) }
        composable("faq") { FAQScreen(navController) }
        composable("transactionReceipt") { CenteredTicketCard(navController) }
        composable("transactionStatus") { TransactionStatus(navController) }
        composable("prayerTime") {
            val prayerTimesState by viewModel.prayerTimes.collectAsState()
            val prayerTimings = prayerTimesState?.timings?.let { Timings(
                Asr = it.Asr, Dhuhr = it.Dhuhr, Maghrib = it.Maghrib, Fajr = it.Fajr, Isha = it.Isha
            )}
            if (prayerTimings != null) {
                PrayerTimeUI(navController, prayerTimings = prayerTimings)
            }
        }
        composable("tasbeehCount") {
            val tasbeehViewModel: TasbeehViewModel = viewModel(
                factory = TasbeehViewModelFactory(LocalContext.current)
            )
            TasbeehCountUI(navController, tasbeehViewModel)
        }
        composable("emiCalculator") { EMICalculator(navController) }
        composable("qibla") {
            val state by viewModel.qiblaState.collectAsState()
            QiblaScreen(
                qiblaDirection = state.qiblaDirection,
                currentDirection = state.currentDirection,
                navController = navController
            )
        }
        composable("location") { FindMeItemUI(navController) }
        composable("locationItemUI/{selectedTitle}") { backStackEntry ->
            val selectedTitle = backStackEntry.arguments?.getString("selectedTitle") ?: ""
            if (selectedTitle == "Agent Banking")
                AgentBankingLocationUI(navController = navController, title = selectedTitle)
            else{
                LocationListUI(navController = navController, title = selectedTitle)
            }

        }


        // Bills Pay screens
        composable("billsPay") { BillsPayUI(navController) }
        composable("billsPayItemUI/{selectedTitle}") { backStackEntry ->
            val selectedTitle = backStackEntry.arguments?.getString("selectedTitle") ?: ""
            BillsPayItemUI(navController = navController, selectedTitle = selectedTitle)
        }

        composable("billsPaymentScreen/{selectedTitle}") { backStackEntry ->
            val selectedTitle = backStackEntry.arguments?.getString("selectedTitle") ?: ""
            BillsPaymentScreen(navController = navController, selectedTitle = selectedTitle)
        }
    }
}
