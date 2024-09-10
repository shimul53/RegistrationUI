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
import com.example.registrationui.location.AgentBankingLocationUI
import com.example.registrationui.location.BranchLocationUI
import com.example.registrationui.location.FindMeItemUI
import com.example.registrationui.location.loadLocationItemDataFromJson
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
    val state = viewModel.qiblaState.collectAsState()
    val prayerTimesState by viewModel.prayerTimes.collectAsState()
    val prayerTimings = prayerTimesState?.timings?.let { Timings(Asr = it.Asr, Dhuhr = it.Dhuhr, Maghrib = it.Maghrib, Fajr = it.Fajr, Isha = it.Isha) }

    NavHost(navController, startDestination = "billsPayItem") {
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("intro") { IntroScreen(navController) }
        composable("otp") { SignupOtpScreen(navController) }
        composable("faq") { FAQScreen(navController) }
        composable("transactionReceipt") { CenteredTicketCard(navController) }
        composable("transactionStatus") { TransactionStatus(navController) }
        composable("prayerTime") {
            if (prayerTimings != null) {
                PrayerTimeUI(navController, prayerTimings = prayerTimings)
            }
        }
        composable("tasbeehCount") {
            // Obtain the TasbeehViewModel
            val tasbeehViewModel: TasbeehViewModel = viewModel(
                factory = TasbeehViewModelFactory(LocalContext.current)
            )
            // Pass the ViewModel to the TasbeehCountUI composable
            TasbeehCountUI(navController, tasbeehViewModel)
        }
        composable("emiCalculator") { EMICalculator(navController) }
        //composable("qibla") { QiblaFinderUI(navController) }
        composable("qibla") { QiblaScreen(
            qiblaDirection = state.value.qiblaDirection,
            currentDirection = state.value.currentDirection,
            navController = navController
        )
        }

       //location
        composable("location") { FindMeItemUI(navController) }
        composable("branchLocation") { BranchLocationUI(navController, title = "Branch") }
        composable("subBranchLocation") { BranchLocationUI(navController, title = "Sub-Branch") }
        composable("boothLocation") { BranchLocationUI(navController, title = "Booth") }
        composable("corporateLocation") { BranchLocationUI(navController, title = "Corporate Office") }
        composable("agentBankLocation") { AgentBankingLocationUI(navController, title = "Agent Banking") }


        //billsPay
        composable("billsPay") { BillsPayUI(navController) }
        composable("billsPayItem") { BillsPayItemUI(navController) }

    }
}