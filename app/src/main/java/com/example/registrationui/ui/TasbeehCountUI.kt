package com.example.registrationui.ui

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationui.R
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first


val Context.dataStore by preferencesDataStore("tasbeeh_preferences")


class TasbeehViewModel(private val context: Context) : ViewModel() {

    var count by mutableStateOf(0)
        private set

    var totalCount by mutableStateOf(0)
        private set

    init {
        viewModelScope.launch {
            count = readCount()
            totalCount = readTotalCount()
        }
    }

    fun incrementCount() {
        viewModelScope.launch {
            count++
            totalCount++
            if (count >= 100) {
                count = 0
                totalCount += count
                saveTotalCount(totalCount)
            }
            saveTotalCount(totalCount)
            saveCount(count)
        }
    }

    fun resetCount() {
        viewModelScope.launch {
            count = 0
            totalCount=0
            saveCount(count)
            saveTotalCount(totalCount)
        }
    }

    private suspend fun saveCount(value: Int) {
        context.dataStore.edit { preferences ->
            preferences[intPreferencesKey("count")] = value
        }
    }

    private suspend fun saveTotalCount(value: Int) {
        context.dataStore.edit { preferences ->
            preferences[intPreferencesKey("total_count")] = value
        }
    }

    private suspend fun readCount(): Int {
        val preferences = context.dataStore.data.first()
        return preferences[intPreferencesKey("count")] ?: 0
    }

    private suspend fun readTotalCount(): Int {
        val preferences = context.dataStore.data.first()
        return preferences[intPreferencesKey("total_count")] ?: 0
    }
}
class TasbeehViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasbeehViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TasbeehViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasbeehCountUI(navController: NavHostController, viewModel: TasbeehViewModel ) {
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
                        "Tasbeeh Counter",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
            Column(modifier = Modifier.align(Alignment.Center)) {
                Column (modifier = Modifier.weight(1f)){
                    TasbeehCountImg(
                        painter = painterResource(id = R.drawable.tasbeeh_count_image),
                        contentDescription = "",
                        text = viewModel.count.toString(), viewModel = viewModel
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                /*Column(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(16.dp)
                ) {
                    TotalCountText(text = viewModel.totalCount.toString())
                }*/

                GradientButtonRow(
                    onCountClick = { viewModel.incrementCount() },
                    onResetClick = { viewModel.resetCount() }
                )
                Spacer(modifier = Modifier.height(30.dp))

                Image(
                    painter = painterResource(id = R.drawable.tasbeeh_counter_img),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(top = 16.dp, start = 5.dp, end = 5.dp)
                )
            }
        }
    }
}

@Composable
fun TasbeehCountImg(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String,
    text: String,
    viewModel: TasbeehViewModel
) {
    val digitalFont = FontFamily(
        Font(R.font.ds_digital) // Replace with your font file name
    )
    val shape = RoundedCornerShape(8.dp)
    val height = 300.dp
    Box(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .background(White, shape = shape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize()
                .clip(shape)
        )

        Text(
            modifier = Modifier.padding(bottom = 90.dp),
            text = text,
            color = White, fontSize = 30.sp,
            fontFamily = digitalFont,
            letterSpacing = 2.sp
        )
        Spacer(modifier = Modifier.height(30.dp))
        Column (modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(bottom = 10.dp, end = 16.dp)){
            TotalCountText(text = viewModel.totalCount.toString())
        }
        

    }
}

@Composable
fun GradientButtonRow(
    onCountClick: () -> Unit,
    onResetClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            GradientButton(
                text = "COUNT",
                onClick = onCountClick // Pass the onCountClick function
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            GradientButton(
                text = "RESET",
                onClick = onResetClick // Pass the onResetClick function
            )
        }
    }
}

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                    ),
                    shape = MaterialTheme.shapes.medium
                )
                .padding(vertical = 12.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}



@Composable
fun TotalCountText(
    text: String,
    fontSize: Int = 16,
    textColor: Color = Color.Black,
    outlineTextColor: Color = Color.Black,
    outlineThickness: Dp = 1.dp,
    fontWeight: FontWeight = FontWeight.Bold,
    boxBorderColor: Color = Color(0xFF1c73c2),
    boxCornerRadius: Dp = 8.dp,
    boxPadding: Dp = 5.dp
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 2.dp, // Thickness of the box outline
                    color = boxBorderColor,
                    shape = RoundedCornerShape(boxCornerRadius)
                )
                .padding(boxPadding)
                .height(30.dp)
                .width(60.dp),
            contentAlignment = Alignment.Center
        ) {
            // Layered text for the outline effect
            Text(
                text = text,
                fontSize = fontSize.sp,
                fontWeight = fontWeight,
                color = outlineTextColor,
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Total Count",
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            color = outlineTextColor
        )
    }
}





