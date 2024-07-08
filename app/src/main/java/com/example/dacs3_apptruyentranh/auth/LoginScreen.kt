package com.example.dacs3_apptruyentranh.auth

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.dacs3_apptruyentranh.DestinationScreen
import com.example.dacs3_apptruyentranh.FbViewModel
import com.example.dacs3_apptruyentranh.Navigation.NavListTruyen
import com.example.dacs3_apptruyentranh.R
import com.example.dacs3_apptruyentranh.model.DataHolder
import com.example.dacs3_apptruyentranh.model.DataHolder1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun LoginScreen(navController: NavController, vm: FbViewModel) {

    val emty by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var erroeE by remember { mutableStateOf(false) }
    var erroeP by remember { mutableStateOf(false) }



    Box(modifier = Modifier.fillMaxSize()) {
        GlideImage(
            model = "https://i.pinimg.com/564x/a2/42/ca/a242ca30e9fce0f972a3613941671229.jpg",
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            if(vm.inProgress.value){
                CircularProgressIndicator()
            }
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                ),
            verticalArrangement = Arrangement.Center

        ){
            if(erroeE){
                Text(
                    text = "Enter mail",
                    color = Color.Red,
                    modifier = Modifier.padding(end = 100.dp)
                )

            }
            TextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = {
                    Text(text = "Email")
                },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.baseline_person_24),
                        contentDescription = null)
                },
                trailingIcon = {
                    if(email.isNotEmpty()){
                        Icon(painter = painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = null,
                            Modifier.clickable { email = emty },
//                            Color.White
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                ),
                shape = RoundedCornerShape(50.dp),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor =Color.Transparent,
//                    cursorColor = Color.Black,
                    containerColor = Color.Transparent,
                    focusedLeadingIconColor = Color.Black,
                    unfocusedLeadingIconColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedTrailingIconColor = Color.Black,
                    unfocusedTrailingIconColor = Color.Black,
                ),
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(android.graphics.Color.rgb(255, 255, 255)).copy(alpha = 0.5f),
                                Color(android.graphics.Color.rgb(196, 193, 213)).copy(alpha = 0.2f),
                                Color(android.graphics.Color.rgb(255, 255, 255)).copy(alpha = 0.3f),
                                Color(android.graphics.Color.rgb(196, 193, 213)).copy(alpha = 0.2f),
                            )
                        )
                    )
                    .border(1.dp, Color.White)
            )
            Spacer(modifier = Modifier.height(30.dp))
            if(erroeP){
                Text(
                    text = "Enter passs",
                    color = Color.Red,
                    modifier = Modifier.padding(end= 100.dp)
                )
            }

            TextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text(text = "Password")
                },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.baseline_lock_24),
                        contentDescription = null)
                },
                trailingIcon = {
                    if(password.isNotEmpty()){
                        val visiblityIcon = if(passwordVisibility){
                            painterResource(id = R.drawable.baseline_visibility_24)
                        }else{
                            painterResource(id = R.drawable.baseline_visibility_off_24)
                        }
                        Icon(painter = visiblityIcon,
                            contentDescription = if(passwordVisibility)"Hide Password" else "Show Password",
                            Modifier.clickable {
                                passwordVisibility = !passwordVisibility
                            }
                        )
                    }
                },
                visualTransformation = if(passwordVisibility){
                    VisualTransformation.None
                }else{
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                ),
                shape = RoundedCornerShape(50.dp),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor =Color.Transparent,
//                    cursorColor = Color.Green,
                    containerColor = Color.Transparent,
                    focusedLeadingIconColor = Color.Black,
                    unfocusedLeadingIconColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedTrailingIconColor = Color.Black,
                    unfocusedTrailingIconColor = Color.Black,
                ),
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(android.graphics.Color.rgb(255, 255, 255)).copy(alpha = 0.5f),
                                Color(android.graphics.Color.rgb(255, 255, 255)).copy(alpha = 0.3f),
                                Color(android.graphics.Color.rgb(196, 193, 213)).copy(alpha = 0.2f),
                            )
                        )
                    )
                    .border(1.dp, Color.White)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = {
                    if(email.isNotEmpty()){
                        erroeE = false
                        if(password.isNotEmpty()){
                            erroeP = false
                           vm.login(email,password)
                        }else{
                            erroeP = true
                        }
                    }
                    else{
                        erroeE = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .width(130.dp)
                    .height(45.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(android.graphics.Color.rgb(196, 193, 213)).copy(alpha = 0.2f),
                                Color(android.graphics.Color.rgb(255, 255, 255)).copy(alpha = 0.5f),
                                Color(android.graphics.Color.rgb(196, 193, 213)).copy(alpha = 0.2f),
                            )
                        )

                    )
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(50.dp)
                    ),
            ) {
                Text(
                    text = "Log In",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if(vm.signedIn.value){




//                NavListTruyen(level = userViewModel.currentLevel.value)
//                navController.navigate(DestinationScreen.Success.route)
//                navController.navigate("home")



                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = FirebaseAuth.getInstance().currentUser
                            val uid = user?.uid

                            if (uid != null) {

                                DataHolder.UID = "$uid"

                                val value = DataHolder.UID

                                Log.d("FirebaseAuth", "UID của người dùng1: $value")
                                Log.d("FirebaseAuth", "UID của người dùng2: $uid")
                            }

                            val databaseReference = FirebaseDatabase.getInstance().reference.child("Users").child("$uid")
                            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        val levelLogin = dataSnapshot.child("Level_login").getValue(String::class.java)
                                        if (levelLogin != null) {
                                            DataHolder1.level = levelLogin
                                            val levellogin = DataHolder1.level
                                            Log.d("FirebaseAuth", "Level_login của người dùng: $levellogin")
                                        } else {
                                            Log.d("FirebaseAuth", "Level_login không tồn tại")
                                        }
                                    } else {
                                        Log.d("FirebaseAuth", "Người dùng không tồn tại")
                                    }
                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                    Log.e("FirebaseDatabase", "Lỗi khi truy xuất dữ liệu", databaseError.toException())
                                }
                            })

                        }
                    }


                navController.navigate("home")
            }
            vm.signedIn.value = false


        }
    }



//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ){
//        if(vm.inProgress.value){
//            CircularProgressIndicator()
//        }
//    }
//    Column (
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 30.dp)
//            .verticalScroll(
//                rememberScrollState()
//            )
//    ){
//        Text(
//            text ="user login",
//            color = Color.White,
//            fontWeight = FontWeight.Bold,
//            fontSize = 40.sp
//        )
//        Spacer(modifier = Modifier.height(50.dp))
//        if(erroeE){
//            Text(
//                text = "Enter mail",
//                color = Color.Red,
//                modifier = Modifier.padding(end = 100.dp)
//            )
//
//        }
//        TextField(
//            value = email,
//            onValueChange = {
//                email = it
//            },
//            label = {
//                Text(text = "Email")
//            },
//            leadingIcon = {
//                Icon(painter = painterResource(id = R.drawable.baseline_person_24),
//                    contentDescription = null)
//            },
//            trailingIcon = {
//                if(email.isNotEmpty()){
//                    Icon(painter = painterResource(id = R.drawable.baseline_close_24),
//                        contentDescription = null,
//                        Modifier.clickable { email = emty }
//                    )
//                }
//            },
//            keyboardOptions = KeyboardOptions(
//                imeAction = ImeAction.Next
//            ),
//            singleLine = true,
//            textStyle = TextStyle(
//                color = Color.LightGray,
//                fontSize = 19.sp,
//                fontWeight = FontWeight.Bold
//            ),
//            shape = RoundedCornerShape(50.dp),
//            modifier = Modifier
//                .width(300.dp)
//                .height(60.dp),
//            colors = TextFieldDefaults.textFieldColors(
//                unfocusedIndicatorColor = Color.Transparent,
//                focusedIndicatorColor = Color.Transparent,
//                cursorColor = Color.Green,
//                containerColor = Color(0x30FFFFFF),
//                focusedLeadingIconColor = Color.LightGray,
//                unfocusedLeadingIconColor = Color.LightGray,
//                focusedLabelColor = Color.LightGray,
//                unfocusedLabelColor = Color.LightGray,
//                focusedTrailingIconColor = Color.LightGray,
//                unfocusedTrailingIconColor = Color.LightGray,
//            )
//        )
//        Spacer(modifier = Modifier.height(30.dp))
//        if(erroeP){
//            Text(
//                text = "Enter passs",
//                color = Color.Red,
//                modifier = Modifier.padding(end= 100.dp)
//            )
//        }
//
//        TextField(
//            value = password,
//            onValueChange = {
//                password = it
//            },
//            label = {
//                Text(text = "password")
//            },
//            leadingIcon = {
//                Icon(painter = painterResource(id = R.drawable.baseline_lock_24),
//                    contentDescription = null)
//            },
//            trailingIcon = {
//                if(password.isNotEmpty()){
//                    val visiblityIcon = if(passwordVisibility){
//                        painterResource(id = R.drawable.baseline_visibility_24)
//                    }else{
//                        painterResource(id = R.drawable.baseline_visibility_off_24)
//                    }
//                    Icon(painter = visiblityIcon,
//                        contentDescription = if(passwordVisibility)"Hide Password" else "Show Password",
//                        Modifier.clickable {
//                            passwordVisibility = !passwordVisibility
//                        }
//                    )
//                }
//            },
//            visualTransformation = if(passwordVisibility){
//                VisualTransformation.None
//            }else{
//                PasswordVisualTransformation()
//            },
//            keyboardOptions = KeyboardOptions(
//                imeAction = ImeAction.Done,
//                keyboardType = KeyboardType.Password
//            ),
//            singleLine = true,
//            textStyle = TextStyle(
//                color = Color.LightGray,
//                fontSize = 19.sp,
//                fontWeight = FontWeight.Bold
//            ),
//            shape = RoundedCornerShape(50.dp),
//            modifier = Modifier
//                .width(300.dp)
//                .height(60.dp),
//            colors = TextFieldDefaults.textFieldColors(
//                unfocusedIndicatorColor = Color.Transparent,
//                focusedIndicatorColor = Color.Transparent,
//                cursorColor = Color.Green,
//                containerColor = Color(0x30FFFFFF),
//                focusedLeadingIconColor = Color.LightGray,
//                unfocusedLeadingIconColor = Color.LightGray,
//                focusedLabelColor = Color.LightGray,
//                unfocusedLabelColor = Color.LightGray,
//                focusedTrailingIconColor = Color.LightGray,
//                unfocusedTrailingIconColor = Color.LightGray,
//            )
//        )
//        Spacer(modifier = Modifier.height(50.dp))
//        Box(
//            modifier = Modifier
//                .clip(RoundedCornerShape(50.dp))
//                .background(
//                    color = Color.White
//                )
//        ){
//            Button(onClick = {
//                if(email.isNotEmpty()){
//                    erroeE = false
//                    if(password.isNotEmpty()){
//                        erroeP = false
//                       vm.login(email,password)
//                    }else{
//                        erroeP = true
//                    }
//                }
//                else{
//                    erroeE = true
//                }
//            },
//                colors = ButtonDefaults.buttonColors(
////                    Color.Transparent
//                    Color.Blue
//                ),
//                modifier = Modifier.width(200.dp)
//            ) {
//                Text(
//                    text = "Log In",
//                    color = Color.Black,
//                    fontSize = 30.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//            if(vm.signedIn.value){
//                navController.navigate(DestinationScreen.Success.route)
//            }
//            vm.signedIn.value = false
//
//        }
//    }


}

