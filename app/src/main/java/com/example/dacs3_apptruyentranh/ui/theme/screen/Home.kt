package com.example.dacs3_apptruyentranh.ui.theme.screen
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.dacs3_apptruyentranh.R
import com.example.dacs3_apptruyentranh.model.Chapter
import com.example.dacs3_apptruyentranh.model.DataHolder
import com.example.dacs3_apptruyentranh.model.ListTruyen
import com.example.dacs3_apptruyentranh.model.Truyen
import com.example.dacs3_apptruyentranh.model.TruyenRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.min





@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Home2(
    openTruyenTranhDetail: (String) -> Unit,
    openChapTerDetail: (String, String) -> Unit,

) {




    val truyenListState = remember { mutableStateOf<List<ListTruyen.Truyen>>(emptyList()) }
    val topTruyenListState = remember { mutableStateOf<List<ListTruyen.Truyen>>(emptyList()) }
    val truyenRepository = ListTruyen.TruyenRepository()

    // Lắng nghe sự thay đổi trong cơ sở dữ liệu
    LaunchedEffect(Unit) {
        val truyenListListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val truyenList = mutableListOf<ListTruyen.Truyen>()
                val topTruyenList = mutableListOf<ListTruyen.Truyen>()

                for (truyenSnapshot in dataSnapshot.children) {
                    val tenTruyen = truyenSnapshot.child("Name").getValue(String::class.java) ?: ""
                    val truyenId = truyenSnapshot.child("TruyenId").getValue(String::class.java) ?: ""
                    val url = truyenSnapshot.child("Image").getValue(String::class.java) ?: ""
                    val view = truyenSnapshot.child("View").getValue(String::class.java) ?: ""

                    Log.d("Truyen", "Tên truyện: $truyenId")
                    truyenList.add(ListTruyen.Truyen(tenTruyen, truyenId,url,view))
                    topTruyenList.add(ListTruyen.Truyen(tenTruyen, truyenId,url,view))


                }
                truyenListState.value = truyenList

                topTruyenList.sortByDescending { it.view.toIntOrNull() ?: 0 }
                topTruyenListState.value = topTruyenList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Xử lý khi có lỗi xảy ra
                Log.d("Firebase", "Lỗi: ${databaseError.message}")
            }
        }

        truyenRepository.getTruyenList(truyenListListener)
    }



    Log.d("tag", "Tên truyện: 4444")


    var userStateListSpiritStone by remember { mutableStateOf<List<ListTruyen.UserInfo>>(emptyList()) }
    var userStateListLevel by remember { mutableStateOf<List<ListTruyen.UserInfo>>(emptyList()) }
//    var userStateListSpiritStone by remember { mutableStateOf<List<ListTruyen.UserInfo>>(emptyList()) }
    LaunchedEffect(Unit) {
        val userListListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userListSpiritStone = mutableListOf<ListTruyen.UserInfo>()
                val userListLevel = mutableListOf<ListTruyen.UserInfo>()
                for (userSnapshot in dataSnapshot.children) {
                    val userId = userSnapshot.child("UserId").getValue(String::class.java) ?: ""
                    val email = userSnapshot.child("Email").getValue(String::class.java) ?: ""
                    val level = userSnapshot.child("Level").getValue(String::class.java) ?: ""
                    val image = userSnapshot.child("Image").getValue(String::class.java) ?: ""
                    val name = userSnapshot.child("Name").getValue(String::class.java) ?: ""
                    val spiritStone = userSnapshot.child("SpiritStone").getValue(String::class.java) ?: ""



                    val userInfo = ListTruyen.UserInfo(
                        userId = userId,
                        email = email,
                        level = level,
                        image = image,
                        name = name,
                        spiritStone = spiritStone
                    )
                    userListSpiritStone.add(userInfo)
                    userListLevel.add(userInfo)
                }

                userListSpiritStone.sortByDescending { it.spiritStone.toIntOrNull() ?: 0 }
                userStateListSpiritStone = userListSpiritStone
                userListLevel.sortByDescending { it.level.toIntOrNull() ?: 0 }
                userStateListLevel = userListLevel
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Firebase", "Error getting user info: ${databaseError.message}")
            }
        }
        truyenRepository.getUsersList(userListListener)
    }




    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        Row {
            Spacer(modifier = Modifier.height(100.dp))
        }

////////////////////////==================================================================================



//        TruyenList()


////////////////////////==================================================================================






        val itemsPerRow = 2 // Số lượng mục trong mỗi hàng

        for (i in truyenListState.value.indices step itemsPerRow) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (j in i until min(i + itemsPerRow, truyenListState.value.size)) {
                    val truyen = truyenListState.value[j]



                    val chapterListState = remember { mutableStateOf<List<ListTruyen.Chapter>>(emptyList()) }
                    val truyenRepository5 = ListTruyen.TruyenRepository()
                    LaunchedEffect("${truyen.truyenId}") {
                        val truyenDetailListener = object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val chapterList = mutableListOf<ListTruyen.Chapter>()

                                for (chapterSnapshot in dataSnapshot.child("Chapter").children) {
                                    val postingTime = chapterSnapshot.child("PostingTime").getValue(String::class.java) ?: ""
                                    val chapterId = chapterSnapshot.child("ChapterId").getValue(String::class.java) ?: ""
                                    val nameChapter = chapterSnapshot.child("NameChapter").getValue(String::class.java) ?: ""
                                    chapterList.add(ListTruyen.Chapter(postingTime, chapterId, nameChapter))
                                }
                                chapterList.sortByDescending { it.postingTime }
                                chapterListState.value = chapterList


                            }
                            override fun onCancelled(databaseError: DatabaseError) {
                                // Xử lý khi có lỗi xảy ra
                                Log.d("Firebase", "Lỗi: ${databaseError.message}")
                            }
                        }

                        truyenRepository5.getTruyenDetail("${truyen.truyenId}", truyenDetailListener)
                    }
//                    chapterListState.value.forEach { chapter ->
//                        Log.d("tag","name chapter ${chapter.nameChapter}")
//                    }















                    Column(
                        modifier = Modifier
                            .weight(0.5f)
                            .shadow(15.dp, shape = RoundedCornerShape(8.dp))
                            .padding(3.dp)
                            .border(1.dp, Color.Gray),

                        ) {
                        Row(
                            modifier = Modifier
                                .height(200.dp)
                        ) {

                            Button(
                                onClick = { openTruyenTranhDetail("${truyen.truyenId}") },
                                shape = RectangleShape,
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {

                                    GlideImage(
                                        model = "${truyen.url}",
                                        contentDescription = "",
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        contentScale = ContentScale.Crop,
//                                    alignment = Alignment.TopCenter

                                    )
                                }

                            }
                        }


                        Row(
                            modifier = Modifier
//                                .height(50.dp)
                                .border(1.dp, Color.Gray)

                        ) {
                            Button(
                                onClick = { openTruyenTranhDetail("${truyen.truyenId}") },
                                shape = RectangleShape,
                                contentPadding = PaddingValues(0.dp, 15.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        android.graphics.Color.rgb(
                                            231,
                                            224,
                                            236
                                        )
                                    )
                                ),
                                modifier = Modifier.fillMaxSize()

                            ) {
                                Text(
                                    text = "${truyen.tenTruyen}",
                                    style = TextStyle(color = Color.Black),
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                )
                            }
                        }



                        val displayedChapter = chapterListState.value.take(2)
                        displayedChapter.forEachIndexed { index, chapter ->
                            Log.d("tag", "name chapter ${chapter.nameChapter}")
                            //                    ${chapter.chapterId}
//                    ${chapter.nameChapter}
//                    ${chapter.postingTime}
                            Row(
                                modifier = Modifier
                                    .height(30.dp)
                                    .border(1.dp, Color.Gray)
                            ) {
                                Button(
                                    onClick = { openChapTerDetail("${truyen.truyenId}","${chapter.chapterId}") },
                                    shape = RectangleShape,
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier.fillMaxSize(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            android.graphics.Color.rgb(
                                                231,
                                                224,
                                                236
                                            )
                                        )
                                    ),

                                    ) {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(5.dp),
                                    ) {
                                        Text(
                                            text = "${chapter.nameChapter}",
                                            style = TextStyle(color = Color.Black),
                                        )
                                        if(chapter.postingTime != null){
                                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                            val givenDate = LocalDateTime.parse("${chapter.postingTime}", formatter)

                                            // Thời gian hiện tại
                                            val currentDate = LocalDateTime.now()

                                            // Tính toán khoảng thời gian chênh lệch
                                            val duration = Duration.between(givenDate, currentDate)

                                            // Xác định thông báo phù hợp
                                            val result = when {
                                                duration.toHours() < 24 -> "20 giờ trước"
                                                duration.toDays() < 7 -> "1 ngày trước"
                                                duration.toDays() < 30 -> "1 tuần trước"
                                                duration.toDays() < 365 -> "1 tháng trước"
                                                else -> "Ngày ${duration.toDays()} trước"
                                            }

                                            Text(
                                                text = "$result",
                                                style = TextStyle(color = Color.Black),
                                            )
                                        }

                                    }

                                }
                            }
                        }
                    }

                }
                val emptyItems =
                    itemsPerRow - (min(i + itemsPerRow, truyenListState.value.size) - i)
                for (k in 0 until emptyItems) {
                    Column(
                        modifier = Modifier
                            .weight(0.5f)
                            .shadow(15.dp, shape = RoundedCornerShape(8.dp))
                            .padding(3.dp)
                            .border(
                                1.dp,
                                Color.Transparent
                            ) // Sử dụng đường viền trong suốt để các mục rỗng không hiển thị viền
                    ) {
                        // Nội dung trống để lấp đầy hàng
                    }
                }


            }
            Spacer(modifier = Modifier.height(10.dp))
        }














        Row {
            Spacer(modifier = Modifier.height(100.dp))
        }
        Row() {
            Text(text = "Top tài phú")

        }
        if (userStateListSpiritStone.isNotEmpty()) {
            val displayedUsers = userStateListSpiritStone.take(5)  // Take only the first 5 users

            displayedUsers.forEachIndexed { index, userInfo ->
//            userStateListSpiritStone.forEach { userInfo ->


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
                            .padding(5.dp, 15.dp, 0.dp, 0.dp)
                    ) {
                        Text(
                            text = "0${index + 1}",
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center,
                            style = TextStyle(color = Color.Black),
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
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
                                model = "${userInfo.image}",
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
                                text = "${userInfo.name}",
                                modifier = Modifier.padding(5.dp, 10.dp, 0.dp, 0.dp),
                                fontSize = 20.sp,

                                )

                        }



                        Row(
                            modifier = Modifier
                                .height(30.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(5.dp, 0.dp, 10.dp, 0.dp)
                            ) {
//                                val level = "${userInfo.level}"
                                val level = userInfo.level.toIntOrNull() ?: 0

                                if (level >= 0 && level <= 10) {
                                    Text(
                                        text = "Luyện Khí",
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(color = Color.Black),
                                        modifier = Modifier
                                            .shadow(10.dp, shape = RoundedCornerShape(0.dp))
                                            .border(1.dp, Color.White)
                                            .padding(10.dp, 2.dp, 10.dp, 2.dp)
                                    )
                                } else if (level > 10 && level <= 100) {
                                    Text(
                                        text = "Trúc Cơ",
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(color = Color.Black),
                                        modifier = Modifier
                                            .shadow(10.dp, shape = RoundedCornerShape(0.dp))
                                            .border(1.dp, Color.White)
                                            .padding(10.dp, 2.dp, 10.dp, 2.dp)
                                    )
                                } else if (level > 100 && level <= 1000) {
                                    Text(
                                        text = "Kim Đan",
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(color = Color.Black),
                                        modifier = Modifier
                                            .shadow(10.dp, shape = RoundedCornerShape(0.dp))
                                            .border(1.dp, Color.White)
                                            .padding(10.dp, 2.dp, 10.dp, 2.dp)
                                    )
                                } else if (level > 1000 && level <= 10000) {
                                    Text(
                                        text = "Nguyên Anh",
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(color = Color.Black),
                                        modifier = Modifier
                                            .shadow(10.dp, shape = RoundedCornerShape(0.dp))
                                            .border(1.dp, Color.White)
                                            .padding(10.dp, 2.dp, 10.dp, 2.dp)
                                    )
                                }

                            }

                            Row() {
                                Text(
                                    text = "${userInfo.spiritStone}",
                                    fontSize = 14.sp,
                                    style = TextStyle(color = Color.Black),
                                    modifier = Modifier
                                        .shadow(10.dp, shape = RoundedCornerShape(0.dp))
                                        .border(1.dp, Color.White)
                                        .padding(10.dp, 2.dp, 10.dp, 2.dp)

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

        }
        Row {
            Spacer(modifier = Modifier.height(100.dp))
        }
        Row() {
            Text(text = "Top Truyện")

        }
//        truyenId
        if (topTruyenListState.value.isNotEmpty()) {
//            topTruyenListState.value.forEach { truyen ->
//                Text("Tên truyện: ${truyen.tenTruyen}, Lượt xem: ${truyen.view}")
//            }
            val displayedUsers = topTruyenListState.value.take(5)

            displayedUsers.forEachIndexed { index, userInfo ->

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
                            .padding(5.dp, 15.dp, 0.dp, 0.dp)
                    ) {
                        Text(
                            text = "0${index + 1}",
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center,
                            style = TextStyle(color = Color.Black),
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
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

                                model = "${userInfo.url}",
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

                                text = "${userInfo.tenTruyen}",
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

//                            Row() {
//                                Button(
//                                    onClick = {},
//                                    shape = RectangleShape,
//                                    contentPadding = PaddingValues(0.dp),
//                                    modifier = Modifier
//                                        .padding(0.dp,0.dp,10.dp,0.dp),
//                                    colors = ButtonDefaults.buttonColors(
//                                        containerColor = Color(
//                                            android.graphics.Color.rgb(
//                                                255,
//                                                255,
//                                                255
//                                            )
//                                        )
//                                    ),
//                                ) {
//                                    Text(
//                                        text = "Chapter 12",
//                                        style = TextStyle(color = Color.Black),
//                                        fontSize = 16.sp
//                                    )
//                                }
//                            }
                            Row() {
                                Icon(painter = painterResource(id = R.drawable.baseline_visibility_24),
                                    contentDescription = null)
                            }
                            Row() {
                                Text(
                                    text = "${userInfo.view}",
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
        }
        Row {
            Spacer(modifier = Modifier.height(100.dp))
        }
        Row() {
            Text(text = "Top thành viên")

        }
        if (userStateListLevel.isNotEmpty()) {
            val displayedUsers = userStateListLevel.take(5)  // Take only the first 5 users

            displayedUsers.forEachIndexed { index, userInfo ->
//            userStateListSpiritStone.forEach { userInfo ->


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
                            .padding(5.dp, 15.dp, 0.dp, 0.dp)
                    ) {
                        Text(
                            text = "0${index + 1}",
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center,
                            style = TextStyle(color = Color.Black),
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
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
                                model = "${userInfo.image}",
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
                                text = "${userInfo.name}",
                                modifier = Modifier.padding(5.dp, 10.dp, 0.dp, 0.dp),
                                fontSize = 20.sp,

                                )

                        }



                        Row(
                            modifier = Modifier
                                .height(30.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(5.dp, 0.dp, 10.dp, 0.dp)
                            ) {
//                                val level = "${userInfo.level}"
                                val level = userInfo.level.toIntOrNull() ?: 0

                                if (level >= 0 && level <= 10) {
                                    Text(
                                        text = "Luyện Khí",
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(color = Color.Black),
                                        modifier = Modifier
                                            .shadow(10.dp, shape = RoundedCornerShape(0.dp))
                                            .border(1.dp, Color.White)
                                            .padding(10.dp, 2.dp, 10.dp, 2.dp)
                                    )
                                } else if (level > 10 && level <= 100) {
                                    Text(
                                        text = "Trúc Cơ",
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(color = Color.Black),
                                        modifier = Modifier
                                            .shadow(10.dp, shape = RoundedCornerShape(0.dp))
                                            .border(1.dp, Color.White)
                                            .padding(10.dp, 2.dp, 10.dp, 2.dp)
                                    )
                                } else if (level > 100 && level <= 1000) {
                                    Text(
                                        text = "Kim Đan",
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(color = Color.Black),
                                        modifier = Modifier
                                            .shadow(10.dp, shape = RoundedCornerShape(0.dp))
                                            .border(1.dp, Color.White)
                                            .padding(10.dp, 2.dp, 10.dp, 2.dp)
                                    )
                                } else if (level > 1000 && level <= 10000) {
                                    Text(
                                        text = "Nguyên Anh",
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(color = Color.Black),
                                        modifier = Modifier
                                            .shadow(10.dp, shape = RoundedCornerShape(0.dp))
                                            .border(1.dp, Color.White)
                                            .padding(10.dp, 2.dp, 10.dp, 2.dp)
                                    )
                                }

                            }

                            Row() {
                                Text(
                                    text = "${userInfo.spiritStone}",
                                    fontSize = 14.sp,
                                    style = TextStyle(color = Color.Black),
                                    modifier = Modifier
                                        .shadow(10.dp, shape = RoundedCornerShape(0.dp))
                                        .border(1.dp, Color.White)
                                        .padding(10.dp, 2.dp, 10.dp, 2.dp)

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

        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TruyenList() {
    val itemsPerPage = 2 // Number of items per page

    var truyenListState by remember { mutableStateOf(emptyList<Truyen>()) }
    var currentPage by remember { mutableStateOf(1) }

    val truyenRepository = TruyenRepository()

    // Function to fetch Truyen list for a specific page
    fun fetchTruyenList(page: Int) {
        truyenRepository.fetchTruyenList(page, itemsPerPage) { truyenList ->
            truyenListState = truyenList
        }
    }

    // Initial fetch when the composable is first launched
    LaunchedEffect(Unit) {
        fetchTruyenList(currentPage)
    }

    Column {
        // Display truyen items in rows
        for (i in truyenListState.indices step itemsPerPage) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (j in i until min(i + itemsPerPage, truyenListState.size)) {
                    val truyen = truyenListState[j]

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    ) {
                        // Image or placeholder
                        GlideImage(model = truyen.url, contentDescription = "")
//                        Image(
//                            painter = rememberImagePainter(truyen.url),
//                            contentDescription = null,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(200.dp)
//                                .clip(RoundedCornerShape(8.dp)),
//                            contentScale = ContentScale.Crop
//                        )

                        // Truyen name
                        Text(
                            text = truyen.tenTruyen,
                            style = TextStyle(color = Color.Black),
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(8.dp)
                        )

                        // Fetch and display chapters (example)
                        val chapterListState = remember { mutableStateOf<List<Chapter>>(emptyList()) }
                        LaunchedEffect(truyen.truyenId) {
                            truyenRepository.fetchChapters(truyen.truyenId) { chapterList ->
                                chapterListState.value = chapterList.take(2) // Limit to first 2 chapters for display
                            }
                        }

                        // Display chapters
                        chapterListState.value.forEach { chapter ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = chapter.nameChapter,
                                    style = TextStyle(color = Color.Black),
                                    modifier = Modifier.weight(1f)
                                )
                                // Posting time (example)
                                Text(
                                    text = chapter.postingTime, // Format this as needed
                                    style = TextStyle(color = Color.Black),
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Load More button to fetch next page
        Button(
            onClick = {
                currentPage++
                fetchTruyenList(currentPage)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Load More")
        }
    }
}


