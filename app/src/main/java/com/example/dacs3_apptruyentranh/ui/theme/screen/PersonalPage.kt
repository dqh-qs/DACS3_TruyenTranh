package com.example.dacs3_apptruyentranh.ui.theme.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.dacs3_apptruyentranh.model.DataHolder
import com.example.dacs3_apptruyentranh.model.ListTruyen
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PersonalPage(){

//    val userId = "TpL7nSqVqyfdFp9zEAeOTM7DUGX2"
    val userId = DataHolder.UID
    if(userId!=null){


        Log.d("FirebaseAuth", "Current uid11111: $userId")
        var UserState by remember { mutableStateOf<ListTruyen.UserInfo?>(null) }
        val database = FirebaseDatabase.getInstance().reference.child("Users").child("$userId")

        LaunchedEffect(Unit) {
            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val email = dataSnapshot.child("Email").getValue(String::class.java)
                        val level = dataSnapshot.child("Level").getValue(String::class.java)
                        val image = dataSnapshot.child("Image").getValue(String::class.java)
                        val name = dataSnapshot.child("Name").getValue(String::class.java)
                        val spiritStone = dataSnapshot.child("SpiritStone").getValue(String::class.java)


                        UserState = ListTruyen.UserInfo(
                            userId = "$userId",
                            email = email.toString(),
                            level = level.toString(),
                            image = image.toString(),
                            name = name.toString(),
                            spiritStone = spiritStone.toString()
                        )

                    } else {
                        Log.d("User Info", "User with ID $userId does not exist.")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Firebase", "Error getting user info: ${databaseError.message}")
                }
            })
        }

        val commentState = remember { mutableStateOf<List<ListTruyen.Comment>>(emptyList()) }
        val database1 = FirebaseDatabase.getInstance().getReference("Comment")
//    val database1 = FirebaseDatabase.getInstance().getReference("Comment").child("TpL7nSqVqyfdFp9zEAeOTM7DUGX2")
        LaunchedEffect(Unit) {
            database1.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val commentList = mutableListOf<ListTruyen.Comment>()

                    for (chapterSnapshot in dataSnapshot.children) {
                        val userId1 = chapterSnapshot.child("userId").getValue(String::class.java) ?: ""
                        val WritingTime = chapterSnapshot.child("WritingTime").getValue(String::class.java) ?: ""
                        val Content = chapterSnapshot.child("Content").getValue(String::class.java) ?: ""

                        if(userId == userId1){
                            val comment = ListTruyen.Comment(
                                userId = userId,
                                WritingTime = WritingTime,
                                Content = Content,
                            )
                            commentList.add(comment)
                        }

                    }

                    commentState.value = commentList
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Firebase", "Error getting comments: ${databaseError.message}")
                }
            })
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ){
//        commentState.value.forEach { comment ->
//            Text(text = "huhu ID: ${comment.Content}")
//        }
            UserState?.let { userInfo ->

//            Text(text = "User ID: ${userInfo.userId}")
//            Text(text = "Email: ${userInfo.email}")
//            Text(text = "Level: ${userInfo.level}")
//            Text(text = "Image: ${userInfo.image}")
//            Text(text = "Name: ${userInfo.name}")
//            Text(text = "Spirit Stone: ${userInfo.spiritStone}")

                var Name by remember {
                    mutableStateOf("${userInfo.name}")
                }
                var Email by remember {
                    mutableStateOf("${userInfo.email}")
                }
                var Level by remember {
                    mutableStateOf("${userInfo.level}")
                }
                var SpiritStone by remember {
                    mutableStateOf("${userInfo.spiritStone}")
                }
//            var Image by remember {
//                mutableStateOf("${userInfo.image}")
//            }
                var imageUrl by remember { mutableStateOf("${userInfo.image}") }
                Row(

                ){
                    TextField(
                        value = imageUrl,
                        onValueChange = {
                            imageUrl = it
                        },
                        label = { Text(text = "Image URL") },
                        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 70.dp, 10.dp, 10.dp),
                        singleLine = true
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 10.dp),
                ){
                    if (imageUrl.isNotEmpty()) {
                        GlideImage(
                            model = imageUrl,
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    TextField(
                        value = Name,
                        onValueChange = {
                            Name =it
                        },
                        label = { Text(text = "Name") },
                        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp,),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 0.dp, 10.dp, 10.dp)
                            .background(Color(android.graphics.Color.rgb(104, 104, 104)))

                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    TextField(
                        value = Email,
                        onValueChange = {
                            Email =it
                        },
                        label = { Text(text = "Email") },
                        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp,),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 0.dp, 10.dp, 10.dp)
                            .background(Color(android.graphics.Color.rgb(104, 104, 104)))
                        ,enabled = false
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    TextField(
                        value = Level,
                        onValueChange = {
                            Level =it
                        },
                        label = { Text(text = "Level") },
                        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp,),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 0.dp, 10.dp, 10.dp)
                            .background(Color(android.graphics.Color.rgb(104, 104, 104)))
                        ,enabled = false
                    )

                }



                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    TextField(
                        value = SpiritStone,
                        onValueChange = {
                            SpiritStone =it
                        },
                        label = { Text(text = "SpiritStone") },
                        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp,),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 0.dp, 10.dp, 10.dp)
                            .background(Color(android.graphics.Color.rgb(104, 104, 104)))
                        ,enabled = false
                    )

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 10.dp)
                ){
                    Button(onClick = {
                        val databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId)
                        val updates = hashMapOf<String, Any>(
                            "Image" to imageUrl,
                            "Name" to Name
                        )
                        databaseReference.updateChildren(updates)
                            .addOnSuccessListener {
                                Log.d("FirebaseDatabase", "Cập nhật imageUrl thành công")
                            }
                            .addOnFailureListener { e ->
                                Log.e("FirebaseDatabase", "Lỗi khi cập nhật imageUrl", e)
                            }
                    }) {
                        Text(text = "update")
                    }
                }


            }

        }




    }else{
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ){
                Text(
                    text = "Hãy đăng nhập",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 300.dp),
                    textAlign = TextAlign.Center

                )
            }



    }





}