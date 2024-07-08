package com.example.dacs3_apptruyentranh.auth

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.dacs3_apptruyentranh.DestinationScreen
import com.example.dacs3_apptruyentranh.FbViewModel
import com.example.dacs3_apptruyentranh.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, vm:FbViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {

//        Image(
//            painterResource( id = R.drawable.solo_leveling),
//            contentDescription = null,
//            contentScale = ContentScale.FillBounds,
//            modifier = Modifier.fillMaxSize()
//        )
        Box(modifier = Modifier.fillMaxSize()) {
            GlideImage(
                model = "https://lvgames.net/lvgames_wallpapers_app/wall/nakroth%20thu%20nguyen%20ve%20than.jpg",
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
//            Column(
//                modifier = Modifier
//                    .align(Alignment.TopCenter)
//                    .padding(50.dp)
//                    .fillMaxWidth()
//            ) {
//                Text(
//                    text = "App Đọc truyện",
//                    color = Color(76, 147, 232),
//                    fontSize = 25.sp,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.fillMaxWidth()
//
//                )
//            }

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
                    .width(150.dp)
            ) {

                Button(
                    onClick = { navController.navigate(DestinationScreen.Login.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),

                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(50.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(
                                        android.graphics.Color.rgb(
                                            147,
                                            87,
                                            144
                                        )
                                    ).copy(alpha = 0.2f),
                                    Color(
                                        android.graphics.Color.rgb(
                                            39,
                                            47,
                                            98
                                        )
                                    ).copy(alpha = 0.2f),
                                    Color(android.graphics.Color.rgb(8, 10, 33)).copy(alpha = 0.2f),
                                    Color(
                                        android.graphics.Color.rgb(
                                            39,
                                            47,
                                            98
                                        )
                                    ).copy(alpha = 0.8f),
                                    Color(android.graphics.Color.rgb(8, 10, 33)).copy(alpha = 0.2f),
                                    Color(
                                        android.graphics.Color.rgb(
                                            51,
                                            64,
                                            128
                                        )
                                    ).copy(alpha = 0.2f),
                                )
                            )

                        )
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(50.dp)
                        )

                    ,
                ) {
                    Text(
                        text = "Log In",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
                Button(
                    onClick = { navController.navigate(DestinationScreen.Signup.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),

                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(50.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(
                                        android.graphics.Color.rgb(
                                            23,
                                            63,
                                            125
                                        )
                                    ).copy(alpha = 0.2f),
                                    Color(
                                        android.graphics.Color.rgb(
                                            39,
                                            47,
                                            98
                                        )
                                    ).copy(alpha = 0.2f),
                                    Color(
                                        android.graphics.Color.rgb(
                                            39,
                                            47,
                                            98
                                        )
                                    ).copy(alpha = 0.2f),
                                    Color(
                                        android.graphics.Color.rgb(
                                            39,
                                            47,
                                            98
                                        )
                                    ).copy(alpha = 0.2f),
                                    Color(
                                        android.graphics.Color.rgb(
                                            51,
                                            64,
                                            128
                                        )
                                    ).copy(alpha = 0.2f),
                                )
                            )

                        )
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(50.dp)
                        )
                    ,
                ) {
                    Text(
                        text = "Sign Up",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
        }



//
//        Spacer(modifier = Modifier.height(80.dp))
//        Box(
//            modifier = Modifier
//                .clip(RoundedCornerShape(50.dp))
//                .background(
//                    brush = Brush.horizontalGradient(
//                        colors = listOf(Color(0xFFFFD700), Color.White, Color(0xFFFFD700))
//                    )
//                )
//        ) {
//            Button(
//                onClick = { navController.navigate(DestinationScreen.Signup.route) },
//                colors = ButtonDefaults.buttonColors(
//                    Color.Transparent
//                ),
//                modifier = Modifier.width(300.dp)
//            ) {
//                Text(
//                    text = "Sign Up",
//                    color = Color.Black,
//                    fontSize = 30.sp
//                )
//
//            }
//        }
//        Spacer(modifier = Modifier.height(30.dp))
//        Box(
//            modifier = Modifier
//                .clip(RoundedCornerShape(50.dp))
//                .background(
//                    brush = Brush.horizontalGradient(
//                        colors = listOf(Color(0xFFFFD700), Color.White, Color(0xFFFFD700))
//                    )
//                )
//        ) {
//            Button(
//                onClick = { navController.navigate(DestinationScreen.Login.route) },
//                colors = ButtonDefaults.buttonColors(
//                    Color.Transparent
//                ),
//                modifier = Modifier.width(300.dp)
//            ) {
//                Text(
//                    text = "Log In",
//                    color = Color.Black,
//                    fontSize = 30.sp
//                )
//
//            }
//        }


    }
}