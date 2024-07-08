package com.example.dacs3_apptruyentranh.Navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dacs3_apptruyentranh.DestinationScreen
import com.example.dacs3_apptruyentranh.FbViewModel
import com.example.dacs3_apptruyentranh.auth.LoginScreen
import com.example.dacs3_apptruyentranh.auth.MainScreen
import com.example.dacs3_apptruyentranh.auth.SignupScreen
import com.example.dacs3_apptruyentranh.auth.SuccessScreen
import com.example.dacs3_apptruyentranh.main.NotificationMessage
import com.example.dacs3_apptruyentranh.model.ListTruyen
import com.example.dacs3_apptruyentranh.ui.theme.admin.InsertChapter
import com.example.dacs3_apptruyentranh.ui.theme.admin.InsertTruyen
import com.example.dacs3_apptruyentranh.ui.theme.admin.TruyenDetails
import com.example.dacs3_apptruyentranh.ui.theme.admin.UpdateTruyen
import com.example.dacs3_apptruyentranh.ui.theme.screen.Chapter
import com.example.dacs3_apptruyentranh.ui.theme.screen.ComicDetails
import com.example.dacs3_apptruyentranh.ui.theme.screen.DisplayFollowItems
import com.example.dacs3_apptruyentranh.ui.theme.screen.Home2
import com.example.dacs3_apptruyentranh.ui.theme.screen.PersonalPage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
//    var currentLevel by remember { mutableStateOf("$level") }
fun NavListTruyen() {
//    val currentLevel by rememberUpdatedState(newValue = level)

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val vm = hiltViewModel<FbViewModel>()

    NotificationMessage(vm)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {},
        drawerContent = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                    Text(
                        text = "Menu",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                )

                Row(){
                    Button(onClick = {
                        navController.navigate("home")
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                        shape = RectangleShape,
                        modifier = Modifier
//                            .padding(0.dp, 0.dp,0.dp, 0.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                        ) {
                        Text(
                            text = "Trang chủ",
                            fontSize = 17.sp,
                            modifier = Modifier
                                .padding(start = 0.dp)
                                .fillMaxWidth()
                        )
                    }
                }
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                )
                Row(){
                    Button(onClick = {
                        navController.navigate("DisplayFollowItems")
                    },
                        shape = RectangleShape,
                        modifier = Modifier
//                            .padding(0.dp, 0.dp,0.dp, 0.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Theo dõi",
                            fontSize = 17.sp,
                            modifier = Modifier
                                .padding(start = 0.dp)
                                .fillMaxWidth()

                        )
                    }
                }
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                )
                Row(){
                    Button(onClick = {
                        navController.navigate("PersonalPage")
                    },
                        shape = RectangleShape,
                        modifier = Modifier
//                            .padding(0.dp, 0.dp,0.dp, 0.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Thông tin tài khoản",
                            fontSize = 17.sp,
                            modifier = Modifier
                                .padding(start = 0.dp)
                                .fillMaxWidth()

                        )
                    }
                }
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                )
                Row(){
                    Button(onClick = {
                        navController.navigate(DestinationScreen.Login.route)
                    },
                        shape = RectangleShape,
                        modifier = Modifier
//                            .padding(0.dp, 0.dp,0.dp, 0.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Đăng Nhập",
                            fontSize = 17.sp,
                            modifier = Modifier
                                .padding(start = 0.dp)
                                .fillMaxWidth()

                        )
                    }
                }
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                )
                Row(){
                    Button(onClick = {
                        navController.navigate(DestinationScreen.Signup.route)
                    },
                        shape = RectangleShape,
                        modifier = Modifier
//                            .padding(0.dp, 0.dp,0.dp, 0.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Đăng Ký",
                            fontSize = 17.sp,
                            modifier = Modifier
                                .padding(start = 0.dp)
                                .fillMaxWidth()

                        )
                    }
                }
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                )
                Row(){
                    Button(onClick = {
                        navController.navigate("Admin/InsertTruyen")
                    },
                        shape = RectangleShape,
                        modifier = Modifier
//                            .padding(0.dp, 0.dp,0.dp, 0.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Thêm Truyện",
                            fontSize = 17.sp,
                            modifier = Modifier
                                .padding(start = 0.dp)
                                .fillMaxWidth()

                        )
                    }
                }
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                )

            }
        }
    ) {

//        NavHost(navController = navController, startDestination = DestinationScreen.Main.route ){
//        NavHost(navController = navController, startDestination = "Admin/InsertTruyen" ){
        NavHost(navController = navController, startDestination = "home" ){
            composable(DestinationScreen.Main.route){
                MainScreen(navController , vm )
            }
            composable(DestinationScreen.Signup.route){
                SignupScreen(navController , vm )
            }
            composable(DestinationScreen.Login.route){
                LoginScreen(navController , vm )
            }
            composable(DestinationScreen.Success.route){
                SuccessScreen   (navController , vm )
            }
            composable("home") {

                Home2 (
                    openTruyenTranhDetail = { truyenId ->
                        navController.navigate("TruyenTranh/$truyenId") },
                    openChapTerDetail = { truyenId, chapterId ->
                        navController.navigate("Chapter/$truyenId/$chapterId") }
                )
//
//                Home2 { truyenId ->
//                    navController.navigate("TruyenTranh/$truyenId"),
//
//                }
            }
            composable("TruyenTranh/{truyenId}") { navBackStackEntry ->
                val truyenId = navBackStackEntry.arguments?.getString("truyenId") ?: ""
                ComicDetails(truyenId = truyenId) { chapterId ->
                    navController.navigate("Chapter/$truyenId/$chapterId")
                }
            }
            composable("Chapter/{truyenId}/{ChapterId}") { navBackStackEntry ->
                val truyenId = navBackStackEntry.arguments?.getString("truyenId") ?: ""
                val chapterId = navBackStackEntry.arguments?.getString("ChapterId") ?: ""

                Chapter(truyenId = truyenId, chapterId = chapterId,
                    openChapTerDetail = { chapterId1 ->
                        navController.navigate("Chapter/$truyenId/$chapterId1")
                    },
                    openTruyenDetail = { truyenId1 ->
                        navController.navigate("TruyenTranh/$truyenId1")
                    }
                )




//                        chapterId1 ->
//                    navController.navigate("Chapter/$truyenId/$chapterId1")


            }
            composable("Admin/InsertTruyen") {
                InsertTruyen(
                    openUpdate= {truyenId->
                        navController.navigate("Admin/UpdateTruyen/$truyenId")},
                    openinsert= {truyenId->
                        navController.navigate("Admin/InsertChapter/$truyenId")},
                    opentruyendetails= {truyenId->
                        navController.navigate("Admin/TruyenDetails/$truyenId")},
                )


            }
            composable("Admin/InsertChapter/{truyenId}") {navBackStackEntry ->
                val truyenId = navBackStackEntry.arguments?.getString("truyenId") ?: ""
                InsertChapter(truyenId)
            }
            composable("Admin/TruyenDetails/{truyenId}") {navBackStackEntry ->
                val truyenId = navBackStackEntry.arguments?.getString("truyenId") ?: ""
                TruyenDetails(truyenId){
                     chapterId ->
                        navController.navigate("Chapter/$truyenId/$chapterId")

                }
            }
            composable("Admin/UpdateTruyen/{truyenId}") {navBackStackEntry ->
                val truyenId = navBackStackEntry.arguments?.getString("truyenId") ?: ""
                UpdateTruyen(truyenId)
//                {
//                        truyenId ->
//                    navController.navigate("Admin/UpdateTruyen/$truyenId")
//                }
            }
            composable("PersonalPage") {
                PersonalPage()
            }
            composable("DisplayFollowItems") {
                DisplayFollowItems()
            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black)
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Close",
                        tint = Color(0xFF600B78)
                    )
                }
                Row(
                    Modifier.padding(top = 10.dp)
                ){
                    Text(
                        text = "Truyện Tranh",
                        fontSize = 20.sp,
                    )

                }

                IconButton(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }) {
                    Icon(Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color(0xFF600B78)
                    )
                }

            }
        }




    }

}

//@Composable
//fun MainScreen(userViewModel: UserViewModel = viewModel()) {
//    NavListTruyen(level = "2")
//}
