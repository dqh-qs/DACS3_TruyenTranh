package com.example.dacs3_apptruyentranh.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.dacs3_apptruyentranh.DestinationScreen
import com.example.dacs3_apptruyentranh.FbViewModel
import com.example.dacs3_apptruyentranh.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun SignupScreen(navController: NavController, vm:FbViewModel){



    val emty by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var cpassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var cpasswordVisibility by remember { mutableStateOf(false) }
    var erroeE by remember { mutableStateOf(false) }
    var erroeP by remember { mutableStateOf(false) }
    var erroeCP by remember { mutableStateOf(false) }
    var erroeC by remember { mutableStateOf(false) }
    var plenght by remember { mutableStateOf(false) }


    Box(modifier = Modifier.fillMaxSize()) {
        GlideImage(
            model = "https://i.pinimg.com/736x/fc/db/c8/fcdbc83fe37f4c5d0d985fa2ae1fed6b.jpg",
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
                        Modifier.clickable { email = emty }
                        )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
            ),
            shape = RoundedCornerShape(50.dp),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor =Color.Transparent,
//                cursorColor = Color.Red,
                containerColor = Color.Transparent,
                focusedLeadingIconColor = Color.White,
                unfocusedLeadingIconColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedTrailingIconColor = Color.White,
                unfocusedTrailingIconColor = Color.White,
            ),
            modifier = Modifier
                .width(300.dp)
                .height(60.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(android.graphics.Color.rgb(1, 1, 1)).copy(alpha = 0.5f),
                            Color(android.graphics.Color.rgb(1, 1, 1)).copy(alpha = 0.4f),
                            Color(android.graphics.Color.rgb(196, 193, 213)).copy(alpha = 0.3f),
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
        if(plenght){
            Text(
                text = "Password must be 6 characters",
                color = Color.Red,
                modifier = Modifier.padding(end= 100.dp)
            )

        }
        TextField(
            value = password,
            onValueChange = {
                password = it
                plenght = it.length < 6
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
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            singleLine = true,
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
            ),
            shape = RoundedCornerShape(50.dp),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor =Color.Transparent,
                cursorColor = Color.Red,
                containerColor = Color.Transparent,
                focusedLeadingIconColor = Color.White,
                unfocusedLeadingIconColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedTrailingIconColor = Color.White,
                unfocusedTrailingIconColor = Color.White,
            ),
            modifier = Modifier
                .width(300.dp)
                .height(60.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(android.graphics.Color.rgb(1, 1, 1)).copy(alpha = 0.5f),
                            Color(android.graphics.Color.rgb(1, 1, 1)).copy(alpha = 0.4f),
                            Color(android.graphics.Color.rgb(196, 193, 213)).copy(alpha = 0.3f),
                        )
                    )
                )
                .border(1.dp, Color.White)
        )
        Spacer(modifier = Modifier.height(30.dp))
        if(erroeCP){
            Text(
                text="Password Not Match",
                color = Color.Red,
                modifier = Modifier.padding(end = 100.dp)
            )
        }
        if(erroeC){
            Text(
                text="Enter ComForm Password",
                color = Color.Red,
                modifier = Modifier.padding(end = 100.dp)
            )
        }
        TextField(value = cpassword,
            onValueChange ={
                cpassword = it
            },
            label = {
                Text(text = "Conform Password")
            },
            leadingIcon = {
                Icon(painter = painterResource(id = R.drawable.baseline_lock_24),
                    contentDescription = null)
            },
            trailingIcon = {
                if(cpassword.isNotEmpty()){
                    val visiblityIcon = if(cpasswordVisibility){
                        painterResource(id = R.drawable.baseline_visibility_24)
                    }else{
                        painterResource(id = R.drawable.baseline_visibility_off_24)
                    }
                    Icon(painter = visiblityIcon,
                        contentDescription = if(cpasswordVisibility)"Hide Password" else "Show Password",
                        Modifier.clickable {
                            cpasswordVisibility = !cpasswordVisibility
                        }
                    )
                }
            },
            visualTransformation = if(cpasswordVisibility){
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
                color = Color.White,
                fontSize = 16.sp,
            ),
            shape = RoundedCornerShape(50.dp),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor =Color.Transparent,
                cursorColor = Color.Red,
                containerColor = Color.Transparent,
                focusedLeadingIconColor = Color.White,
                unfocusedLeadingIconColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedTrailingIconColor = Color.White,
                unfocusedTrailingIconColor = Color.White,
            ),
            modifier = Modifier
                .width(300.dp)
                .height(60.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(android.graphics.Color.rgb(1, 1, 1)).copy(alpha = 0.5f),
                            Color(android.graphics.Color.rgb(1, 1, 1)).copy(alpha = 0.4f),
                            Color(android.graphics.Color.rgb(196, 193, 213)).copy(alpha = 0.3f),
                        )
                    )
                )
                .border(1.dp, Color.White)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = {
            if(email.isNotEmpty()){
                erroeE = false
                if(password.isNotEmpty()){
                    erroeP = false
                    if(cpassword.isNotEmpty()){
                        erroeC = false
                        if(password== cpassword){
                            erroeCP = false
                            vm.onSignup(email, password)
                        }else{
                            erroeCP = true
                        }
                    }else{
                        erroeC = true
                    }
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
                            Color(android.graphics.Color.rgb(1, 1, 1)).copy(alpha = 0.5f),
                            Color(android.graphics.Color.rgb(1, 1, 1)).copy(alpha = 0.4f),
                            Color(android.graphics.Color.rgb(196, 193, 213)).copy(alpha = 0.3f),
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
                text = "Sign Up",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold

            )
        }
        if(vm.signedIn.value){
            navController.navigate(DestinationScreen.Login.route)
        }
        vm.signedIn.value = false
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
//                        if(cpassword.isNotEmpty()){
//                            erroeC = false
//                            if(password== cpassword){
//                                erroeCP = false
//                                vm.onSignup(email, password)
//                            }else{
//                                erroeCP = true
//                            }
//                        }else{
//                            erroeC = true
//                        }
//                    }else{
//                        erroeP = true
//                    }
//                }
//                else{
//                    erroeE = true
//                }
//            },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Transparent
//                ),
//                modifier = Modifier
//                    .padding(8.dp)
//                    .fillMaxWidth()
//                    .clip(RoundedCornerShape(50.dp))
//                    .background(
//                        brush = Brush.horizontalGradient(
//                            colors = listOf(
//                                Color(
//                                    android.graphics.Color.rgb(
//                                        147,
//                                        87,
//                                        144
//                                    )
//                                ).copy(alpha = 0.8f),
//                                Color(
//                                    android.graphics.Color.rgb(
//                                        39,
//                                        47,
//                                        98
//                                    )
//                                ).copy(alpha = 0.8f),
//                                Color(android.graphics.Color.rgb(8, 10, 33)).copy(alpha = 0.8f),
//                                Color(
//                                    android.graphics.Color.rgb(
//                                        39,
//                                        47,
//                                        98
//                                    )
//                                ).copy(alpha = 0.8f),
//                                Color(android.graphics.Color.rgb(8, 10, 33)).copy(alpha = 0.8f),
//                                Color(
//                                    android.graphics.Color.rgb(
//                                        51,
//                                        64,
//                                        128
//                                    )
//                                ).copy(alpha = 0.8f),
//                            )
//                        )
//
//                    )
//                    .border(
//                        width = 1.dp,
//                        color = Color.Gray,
//                        shape = RoundedCornerShape(50.dp)
//                    ),
//
//            ) {
//                Text(
//                    text = "Sign Up",
//                    color = Color.Gray,
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
//            text ="user sign",
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
//                        )
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
//                focusedIndicatorColor =Color.Transparent,
//                cursorColor = Color.Red,
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
//        if(plenght){
//            Text(
//                text = "Password must be 6 characters",
//                color = Color.Red,
//                modifier = Modifier.padding(end= 100.dp)
//            )
//
//        }
//        TextField(
//            value = password,
//            onValueChange = {
//                password = it
//                plenght = it.length < 6
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
//                 PasswordVisualTransformation()
//            },
//            keyboardOptions = KeyboardOptions(
//                imeAction = ImeAction.Next,
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
//                focusedIndicatorColor =Color.Transparent,
//                cursorColor = Color.Red,
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
//        if(erroeCP){
//            Text(
//                text="Password Not Match",
//                color = Color.Red,
//                modifier = Modifier.padding(end = 100.dp)
//            )
//        }
//        if(erroeC){
//            Text(
//                text="Enter ComForm Password",
//                color = Color.Red,
//                modifier = Modifier.padding(end = 100.dp)
//            )
//        }
//        TextField(value = cpassword,
//            onValueChange ={
//                cpassword = it
//            },
//            label = {
//                Text(text = "Conform Password")
//            },
//            leadingIcon = {
//                Icon(painter = painterResource(id = R.drawable.baseline_lock_24),
//                    contentDescription = null)
//            },
//            trailingIcon = {
//                if(cpassword.isNotEmpty()){
//                    val visiblityIcon = if(cpasswordVisibility){
//                        painterResource(id = R.drawable.baseline_visibility_24)
//                    }else{
//                        painterResource(id = R.drawable.baseline_visibility_off_24)
//                    }
//                    Icon(painter = visiblityIcon,
//                        contentDescription = if(cpasswordVisibility)"Hide Password" else "Show Password",
//                        Modifier.clickable {
//                            cpasswordVisibility = !cpasswordVisibility
//                        }
//                    )
//                }
//            },
//            visualTransformation = if(cpasswordVisibility){
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
//                focusedIndicatorColor =Color.Transparent,
//                cursorColor = Color.Red,
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
//                        if(cpassword.isNotEmpty()){
//                            erroeC = false
//                            if(password== cpassword){
//                                erroeCP = false
//                                vm.onSignup(email, password)
//                            }else{
//                                erroeCP = true
//                            }
//                        }else{
//                            erroeC = true
//                        }
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
//                    text = "Sign Up",
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