package UI
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationui.R
import java.util.Calendar



@Composable
fun SignupScreen(navController: NavHostController){

    Column(modifier = Modifier.fillMaxSize().background(Color.White), verticalArrangement =Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
        TopBar(onBackClick = { navController.popBackStack()})
        CustomSwipeButton()
        //TermsSection()
       // nextBtn()

    }
}

@Composable
fun CustomSwipeButton() {
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
                        text = "Regular",
                        fontSize = 16.sp
                    )
                }
            } else {
                Text(
                    text = "Regular",
                    modifier = Modifier
                        .weight(1f)
                        .height(53.dp)
                        .padding(16.dp)
                        .clickable { selectedButton = 1 },
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
                        text = "Proprietorship",
                        fontSize = 16.sp
                    )
                }
            } else {
                Text(
                    text = "Proprietorship",
                    modifier = Modifier
                        .weight(1f)
                        .height(53.dp)
                        .padding(16.dp)
                        .clickable { selectedButton = 2 },
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }



    }
    // Conditionally render RegularUserTextFields
    if (selectedButton == 1) {
        UserTextFields(selectedButton = 1)
    } else if (selectedButton == 2) {
        UserTextFields(selectedButton = 2)
    }


}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Sign Up",
                color = Color.Black,
                fontSize = 20.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTextFields(selectedButton: Int){
    val scrollState = rememberScrollState()
    val userIDState = remember { mutableStateOf("") }
    val accountNumberState = remember { mutableStateOf("") }
    val accountNameState = remember { mutableStateOf("") }
    val mobileNumberState = remember { mutableStateOf("") }
    val nidNoState = remember { mutableStateOf("") }
    Column(modifier = Modifier
        .verticalScroll(scrollState), verticalArrangement =Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {

        //user id
        Text(text = "User ID", modifier = Modifier
            .align(Alignment.Start)
            .padding(start = 16.dp))
        OutlinedTextField( modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),value =userIDState.value , onValueChange = {userIDState.value = it},
            label ={ Text(text = "Enter your user ID")} ,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF014C8F),
                unfocusedBorderColor = Color.Gray, // Optional: define the unfocused border color
                cursorColor = Color(0xFF014C8F),         // Cursor color
                focusedLabelColor = Color(0xFF014C8F),   // Focused label color
                unfocusedLabelColor = Color.Gray
            ),)
        Spacer(modifier = Modifier.height(15.dp))

        //account number
        Text(text = "Account Number", modifier = Modifier
            .align(Alignment.Start)
            .padding(start = 16.dp))
        OutlinedTextField( modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),value =accountNumberState.value , onValueChange = {accountNumberState.value = it},
            label ={ Text(text = "Enter your Account Number")} ,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF014C8F),
                unfocusedBorderColor = Color.Gray, // Optional: define the unfocused border color
                cursorColor = Color(0xFF014C8F),         // Cursor color
                focusedLabelColor = Color(0xFF014C8F),   // Focused label color
                unfocusedLabelColor = Color.Gray
            ),)

        Spacer(modifier = Modifier.height(15.dp))


        //account name
        Text(text = "Account Name", modifier = Modifier
            .align(Alignment.Start)
            .padding(start = 16.dp))
        OutlinedTextField( modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),value =accountNameState.value , onValueChange = {accountNameState.value = it},
            label ={ Text(text = "Enter your Account Name")} ,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF014C8F),
                unfocusedBorderColor = Color.Gray, // Optional: define the unfocused border color
                cursorColor = Color(0xFF014C8F),         // Cursor color
                focusedLabelColor = Color(0xFF014C8F),   // Focused label color
                unfocusedLabelColor = Color.Gray
            ),)

        Spacer(modifier = Modifier.height(15.dp))

        //mobile number
        Text(text = "Mobile Number", modifier = Modifier
            .align(Alignment.Start)
            .padding(start = 16.dp))
        OutlinedTextField( modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),value =mobileNumberState.value , onValueChange = {mobileNumberState.value = it},
            label ={ Text(text = "Enter your Mobile Number")} ,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF014C8F),
                unfocusedBorderColor = Color.Gray, // Optional: define the unfocused border color
                cursorColor = Color(0xFF014C8F),         // Cursor color
                focusedLabelColor = Color(0xFF014C8F),   // Focused label color
                unfocusedLabelColor = Color.Gray
            ),)

        Spacer(modifier = Modifier.height(15.dp))

        //date of birth
        Text(text =  if (selectedButton == 1) "Date of Birth" else "Owner's Birthday", modifier = Modifier
            .align(Alignment.Start)
            .padding(start = 16.dp))
       /* OutlinedTextField( modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),value =textState.value , onValueChange = {textState.value = it},
            label ={ Text(text = "Enter your Date of Birth")} ,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF014C8F),
                unfocusedBorderColor = Color.Gray, // Optional: define the unfocused border color
                cursorColor = Color(0xFF014C8F),         // Cursor color
                focusedLabelColor = Color(0xFF014C8F),   // Focused label color
                unfocusedLabelColor = Color.Gray
            ),)*/
        DateOfBirthField()

        Spacer(modifier = Modifier.height(15.dp))

        //nid no
        Text(text = "NID No", modifier = Modifier
            .align(Alignment.Start)
            .padding(start = 16.dp))
        OutlinedTextField( modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),value =nidNoState.value , onValueChange = {nidNoState.value = it},
            label ={ Text(text = "Enter your NID No")} ,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF014C8F),
                unfocusedBorderColor = Color.Gray, // Optional: define the unfocused border color
                cursorColor = Color(0xFF014C8F),         // Cursor color
                focusedLabelColor = Color(0xFF014C8F),   // Focused label color
                unfocusedLabelColor = Color.Gray
            ),)

        TermsSection()
        nextBtn()


    }


}


@Composable
fun TermsSection() {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Checkbox(
            checked = isChecked,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF014C8F), // Set the checked color
                uncheckedColor = Color.Gray,      // Set the unchecked color
                checkmarkColor = Color.White      // Set the checkmark color
            ),
            onCheckedChange = { isChecked = it }
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = "I agree with this",
            fontSize = 16.sp,
        )

        TextButton(onClick = { /* Handle terms & conditions click */ }) {
            Text(
                text = "Terms & Conditions",
                color = Color(0xFF014C8F), // Color for the text button
                fontSize = 16.sp
            )
        }
    }
}


@Composable
fun nextBtn(){
    Button(
        onClick = { /* Handle click */ },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor =  Color(0xFF014C8F), // Background color
            contentColor = Color.White // Text color f8ad3c
        ),
        modifier = Modifier
            .height(53.dp)
            .width(350.dp) // Set a fixed height
    ) {
        Text(modifier = Modifier.padding(5.dp), text = "Next", fontSize = 20.sp)
    }
}


@OptIn(ExperimentalMaterial3Api::class)

    @Composable
    fun DateOfBirthField() {
        val dateOfBirthState = remember { mutableStateOf(TextFieldValue()) }
        val context = LocalContext.current
        val calendar = Calendar.getInstance()

        // DatePickerDialog to show the date picker
        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // Update the textState with the selected date
                dateOfBirthState.value = TextFieldValue("$dayOfMonth/${month + 1}/$year")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            value = dateOfBirthState.value,
            onValueChange = { dateOfBirthState.value = it },
            label = { Text(text = "Enter your Date of Birth") },
            trailingIcon = {
                IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_calendar_month_24), // Replace with your calendar icon resource
                        contentDescription = "Calendar Icon"
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF014C8F),
                unfocusedBorderColor = Color.Gray, // Optional: define the unfocused border color
                cursorColor = Color(0xFF014C8F),         // Cursor color
                focusedLabelColor = Color(0xFF014C8F),   // Focused label color
                unfocusedLabelColor = Color.Gray
            ),
        )
    }







/*@Composable
fun CardWithTwoButtons() {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {   },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF8AD3C), // Background color
                    contentColor = Color.White // Text color
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(53.dp)
            ) {
                Text(
                    text = "Button 1",
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {  },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF014C8F), // Background color
                    contentColor = Color.White // Text color
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(53.dp)
            ) {
                Text(
                    text = "Button 2",
                    fontSize = 16.sp
                )
            }
        }
    }
}


data class TabItem(
    val text: String, // Tab Title
    val screen: @Composable () -> Unit // Tab Screen
)*/

/*@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun PagingScreen() {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 2 })

    val tabRowItems = listOf(
        TabItem(
            text = "Regular",
            screen = {  Screen content for Regular tab  }
        ),
        TabItem(
            text = "Proprietorship",
            screen = {  Screen content for Proprietorship tab  }
        )
    )
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TabRow(
            indicator = { tabPositions ->
                CustomIndicator(tabPositions = tabPositions, pagerState = pagerState, tabTexts = tabRowItems.map { it.text })
            },
            containerColor = Color.Transparent.copy(0.1f),
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .clip(RoundedCornerShape(16)),
            selectedTabIndex = pagerState.currentPage
        ) {
            tabRowItems.forEachIndexed { index, item ->
                Tab(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .padding(horizontal = 16.dp),
                    text = { Text(text = item.text) },
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } }
                )
            }
        }
        HorizontalPager(
            beyondBoundsPageCount = tabRowItems.size,
            state = pagerState,
        ) {
            tabRowItems[pagerState.currentPage].screen()
        }
    }
}*/

/*@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
private fun CustomIndicator(tabPositions: List<TabPosition>, pagerState: PagerState, tabTexts: List<String>) {
    val transition = updateTransition(pagerState.currentPage, label = "")

    val indicatorStart by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 50f)
            } else {
                spring(dampingRatio = 1f, stiffness = 100f)
            }
        }, label = ""
    ) {
        tabPositions[it].left
    }

    val indicatorEnd by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 100f)
            } else {
                spring(dampingRatio = 1f, stiffness = 50f)
            }
        }, label = ""
    ) {
        tabPositions[it].right
    }

    Box(
        Modifier
            .offset(x = indicatorStart)
            .wrapContentSize(align = Alignment.BottomStart)
            .width(indicatorEnd - indicatorStart)
            .fillMaxSize()
            .background(Color.Green, RoundedCornerShape(16))
            .padding(10.dp)
            .wrapContentSize(align = Alignment.Center)
    ) {
        Text(
            text = tabTexts[pagerState.currentPage],
            fontSize = 16.sp,


        )
    }
}*/


