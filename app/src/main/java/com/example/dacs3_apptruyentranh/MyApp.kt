package com.example.dacs3_apptruyentranh

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dacs3_apptruyentranh.ui.theme.screen.Chapter
import com.example.dacs3_apptruyentranh.ui.theme.screen.ComicDetails
import com.example.dacs3_apptruyentranh.ui.theme.screen.Home2
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("NavBar Example") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        drawerContent = {
            Column {
                DrawerItem(icon = Icons.Default.Home, text = "Drawer Item 1")
                DrawerItem(icon = Icons.Default.Info, text = "Drawer Item 2")
            }
        }
    ) {

//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            Text("Main Content")
//        }
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
//                Home2 { truyenId ->
//                    navController.navigate("TruyenTranh/$truyenId")
//                }
            }
            composable("TruyenTranh/{truyenId}") { navBackStackEntry ->
                val truyenId = navBackStackEntry.arguments?.getString("truyenId") ?: ""
                ComicDetails(truyenId = truyenId){
                        chapterId ->
                    navController.navigate("Chapter/$truyenId/$chapterId")
                }
            }
            composable("Chapter/{truyenId}/{ChapterId}") { navBackStackEntry ->
                val truyenId = navBackStackEntry.arguments?.getString("truyenId") ?: ""
                val chapterId = navBackStackEntry.arguments?.getString("ChapterId") ?: ""

//                Chapter(truyenId = truyenId, chapterId = chapterId){
//                        chapterId1 ->
//                    navController.navigate("Chapter/$truyenId/$chapterId1")
//                }
            }

        }

    }
}



@Composable
fun DrawerItem(icon: ImageVector, text: String) {
    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}
