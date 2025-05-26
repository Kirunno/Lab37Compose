package com.example.lab37compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab37App()
        }
    }
}

@Composable
fun Lab37App() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar(navController) }
    ) { inner ->
        NavHost(
            navController,
            startDestination = "home",
            Modifier
                .padding(inner)
                .fillMaxSize()
        ) {
            composable("home") { HomeScreen() }
            composable("time") { TimeScreen() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(nav: NavHostController) {
    val backStack by nav.currentBackStackEntryAsState()
    val current = backStack?.destination?.route
    TopAppBar(
        title = { Text("Lab 37") },
        actions = {
            IconButton(onClick = { nav.navigate("home") }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = if (current == "home") MaterialTheme.colorScheme.primary else Color.Gray
                )
            }
            IconButton(onClick = { nav.navigate("time") }) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Time",
                    tint = if (current == "time") MaterialTheme.colorScheme.primary else Color.Gray
                )
            }
        }
    )
}

@Composable
fun HomeScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Локальное изображение", fontSize = 18.sp)
        Image(
            painter = painterResource(R.drawable.local_image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(Modifier.height(20.dp))
        Text("Изображение из интернета", fontSize = 18.sp)
        AsyncImage(
            model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSnvuVWEKr3h3c_VkxijvAqyUjroUlCQ1Sdg&s",
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
    }
}

@Composable
fun TimeScreen() {
    var time by remember { mutableStateOf(currentTime()) }

    LaunchedEffect(Unit) {
        while (true) {
            time = currentTime()
            delay(1000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = time,
            fontSize = 48.sp,
            color = Color.White
        )
    }
}

fun currentTime(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return LocalTime.now().format(formatter)
}
