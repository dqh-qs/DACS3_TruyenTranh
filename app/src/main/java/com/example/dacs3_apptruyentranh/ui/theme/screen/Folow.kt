package com.example.dacs3_apptruyentranh.ui.theme.screen

import android.util.Log
import androidx.compose.foundation.border
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.dacs3_apptruyentranh.R
import com.example.dacs3_apptruyentranh.model.DataHolder
import com.example.dacs3_apptruyentranh.model.ListTruyen
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener




@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DisplayFollowItems() {

    val userId = DataHolder.UID
    if(userId!=null){
        val followItemsState = remember { mutableStateOf<List<ListTruyen.Follow>>(emptyList()) }

        // Firebase reference to "Follow" node
        val databaseReference = FirebaseDatabase.getInstance().reference.child("Follow")

        // Query Firebase for items with matching UserId
        databaseReference.orderByChild("UserId").equalTo(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Temporary list to hold Follow items
                    val followItems = mutableListOf<ListTruyen.Follow>()

                    // Iterate through each child item in the dataSnapshot
                    for (snapshot in dataSnapshot.children) {
                        // Retrieve data and create a Follow object
                        val followId = snapshot.key ?: ""
                        val truyenId = snapshot.child("TruyenId").getValue(String::class.java) ?: ""
                        val userId = snapshot.child("UserId").getValue(String::class.java) ?: ""

                        // Create a Follow object and add it to the list
                        val follow = ListTruyen.Follow(followId, truyenId, userId)
                        followItems.add(follow)
                    }

                    // Update the state with the fetched Follow items
                    followItemsState.value = followItems

                    // Log the retrieved Follow items
                    Log.d("FirebaseData", "FollowItems: $followItems")
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors
                    Log.e("FirebaseData", "Error: ${databaseError.message}")
                }
            })

        // Display the fetched Follow items using Compose
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            Row {
                Spacer(modifier = Modifier.height(100.dp))
            }
            for (follow in followItemsState.value) {
//            Text(
//                text = "FollowId: ${follow.FollowId}, TruyenId: ${follow.TruyenId}, UserId: ${follow.UserId}",
//                modifier = Modifier.padding(8.dp)
//            )

                val chapterListState =
                    remember { mutableStateOf<List<ListTruyen.Chapter>>(emptyList()) }
                val truyenRepository = ListTruyen.TruyenRepository()
                var truyenData: ListTruyen.TruyenData by remember { mutableStateOf(ListTruyen.TruyenData.Error) }
                var hasIncrementedView = false
                LaunchedEffect(follow.TruyenId) {
                    val truyenDetailListener = object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val chapterList = mutableListOf<ListTruyen.Chapter>()
                            val Name = dataSnapshot.child("Name").getValue(String::class.java) ?: ""
                            val Describe =
                                dataSnapshot.child("Describe").getValue(String::class.java) ?: ""
                            val View = dataSnapshot.child("View").getValue(String::class.java) ?: ""
                            val Author = dataSnapshot.child("Author").getValue(String::class.java) ?: ""
                            val Category =
                                dataSnapshot.child("Category").getValue(String::class.java) ?: ""
                            val Ratings =
                                dataSnapshot.child("Ratings").getValue(String::class.java) ?: ""
                            val Star = dataSnapshot.child("Star").getValue(String::class.java) ?: ""
                            val Image = dataSnapshot.child("Image").getValue(String::class.java) ?: ""

                            val view11 = DataHolder.UID
                            if (view11 != null && !hasIncrementedView) {
                                if (View != null) {
                                    val view = View.toIntOrNull()
                                    if (view != null) {
                                        val newView = view + 1
                                        dataSnapshot.ref.child("View").setValue(newView.toString())
                                        hasIncrementedView = true
                                    } else {
                                        println("Error: View is not a valid integer")
                                    }
                                } else {
                                    println("Error: View is null")
                                }
                            }


                            for (chapterSnapshot in dataSnapshot.child("Chapter").children) {
                                val postingTime =
                                    chapterSnapshot.child("PostingTime").getValue(String::class.java)
                                        ?: ""
                                val chapterId =
                                    chapterSnapshot.child("ChapterId").getValue(String::class.java)
                                        ?: ""
                                val nameChapter =
                                    chapterSnapshot.child("NameChapter").getValue(String::class.java)
                                        ?: ""
                                chapterList.add(ListTruyen.Chapter(postingTime, chapterId, nameChapter))
                            }
                            chapterList.sortByDescending { it.postingTime }
                            chapterListState.value = chapterList
                            truyenData = ListTruyen.TruyenData.Success(
                                Name,
                                Describe,
                                View,
                                Author,
                                Category,
                                Ratings,
                                Star,
                                Image,

                                )
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            // Xử lý khi có lỗi xảy ra
                            Log.d("Firebase", "Lỗi: ${databaseError.message}")
                        }
                    }

                    truyenRepository.getTruyenDetail(follow.TruyenId, truyenDetailListener)
                }

                when (truyenData) {
                    is ListTruyen.TruyenData.Success -> {
                        val Name = (truyenData as ListTruyen.TruyenData.Success).Name
                        val Describe = (truyenData as ListTruyen.TruyenData.Success).Describe
                        val View = (truyenData as ListTruyen.TruyenData.Success).View
                        val Author = (truyenData as ListTruyen.TruyenData.Success).Author
                        val Category = (truyenData as ListTruyen.TruyenData.Success).Category
                        val Ratings = (truyenData as ListTruyen.TruyenData.Success).Ratings
                        val Star = (truyenData as ListTruyen.TruyenData.Success).Star
                        val Image = (truyenData as ListTruyen.TruyenData.Success).Image


                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {

                            Column(
                                modifier = Modifier
                                    .width(60.dp)
                                    .padding(5.dp, 0.dp, 0.dp, 0.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(0.dp, 10.dp, 0.dp, 10.dp)
                                        .border(width = 1.dp, color = Color.Black)
                                ) {
                                    GlideImage(

                                        model = "$Image",
                                        contentDescription = "",
                                        modifier = Modifier
                                            .height(60.dp)
                                            .width(60.dp),
                                        contentScale = ContentScale.Crop,
                                        alignment = Alignment.TopCenter
                                    )
                                }

                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(5.dp, 0.dp, 0.dp, 0.dp)

                            ) {


                                Row(
                                    modifier = Modifier
                                        .height(35.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.Top,
                                ) {
                                    Text(

                                        text = "$Name",
                                        modifier = Modifier.padding(5.dp, 10.dp, 0.dp, 0.dp),
                                        fontSize = 20.sp,

                                        )

                                }



                                Row(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .fillMaxWidth()
                                        .padding(start = 20.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {

                                    Row() {
                                        Icon(painter = painterResource(id = R.drawable.baseline_visibility_24),
                                            contentDescription = null)
                                    }
                                    Row() {
                                        Text(
                                            text = "Name",
                                            fontSize = 14.sp,
                                            style = TextStyle(color = Color.Black),
                                            modifier = Modifier
                                                .padding(5.dp, 2.dp, 10.dp, 2.dp)

                                        )
                                    }


                                }

                            }

                        }
                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                        )


                    }

                    else -> {}
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
                text = "Vui lòng đăng nhập",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 300.dp),
                textAlign = TextAlign.Center

            )
        }

    }



}






