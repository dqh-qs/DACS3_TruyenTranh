package com.example.dacs3_apptruyentranh.ui.theme.screen

import android.graphics.Color.rgb
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.dacs3_apptruyentranh.model.DataHolder
import com.example.dacs3_apptruyentranh.model.DataHolder1
import com.example.dacs3_apptruyentranh.model.ListTruyen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun Comment(){
    val CmtRef = FirebaseDatabase.getInstance().getReference("Comment").push()
    val CmtId = CmtRef.key
    val Cmt = HashMap<String, Any>()
    Cmt["CmtId"] = "$CmtId"
    Cmt["TruyenId"] = "-NxnJvadZpvCF-6vC7lZ"
    Cmt["ChapterId"] = "-NxnJvaqe_lTpH9_Eef7"
    Cmt["userId"] = "V4u6AxCm3Eav435xoWIEenVmat93"
    Cmt["WritingTime"] = "23 giờ trước"
    Cmt["Content"] = "Nghệ thuật không cần phải là ánh trăng lừa dối, nghệ thuật không nên là ánh trăng lừa dối, nghệ thuật chỉ có thể là tiếng đau khổ kia thoát ra từ những kiếp lầm than"
    CmtRef.setValue(Cmt)
}
@Composable
fun CommentDisplay(truyenId: String, chapterId: String) {
    Log.d("tag", "Content: hhhhh")
    val commentUserState = remember { mutableStateOf<List<Pair<ListTruyen.Comment, ListTruyen.UserInfo>>>(emptyList()) } // Mutable state for storing both comment and user info

    LaunchedEffect(truyenId, chapterId) {
        val commentListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val commentUserList = mutableListOf<Pair<ListTruyen.Comment, ListTruyen.UserInfo>>()
                for (chapterSnapshot in dataSnapshot.children) {
                    val userId = chapterSnapshot.child("userId").getValue(String::class.java) ?: ""
                    val WritingTime = chapterSnapshot.child("WritingTime").getValue(String::class.java) ?: ""
                    val Content = chapterSnapshot.child("Content").getValue(String::class.java) ?: ""

                    val comment = ListTruyen.Comment(
                        userId = userId,
                        WritingTime = WritingTime,
                        Content = Content,
                    )

                    val databaseReference = FirebaseDatabase.getInstance().reference.child("Users").child(userId)
                    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                val email = dataSnapshot.child("Email").getValue(String::class.java)
                                val level = dataSnapshot.child("Level").getValue(String::class.java)
                                val image = dataSnapshot.child("Image").getValue(String::class.java)
                                val spiritStone = dataSnapshot.child("SpiritStone").getValue(String::class.java)

                                // Thêm thông tin của người dùng vào danh sách
                                val userInfo = ListTruyen.UserInfo(
                                    userId = userId,
                                    email = email.toString(),
                                    level = level.toString(),
                                    image = image.toString(),
                                    spiritStone = spiritStone.toString()
                                )

                                // Thêm cả comment và user info vào danh sách
                                commentUserList.add(Pair(comment, userInfo))
                                commentUserState.value = commentUserList
                            } else {
                                Log.d("User Info", "User with ID $userId does not exist.")
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.e("Firebase", "Error getting user info: ${databaseError.message}")
                        }
                    })
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Firebase", "loadComment:onCancelled", databaseError.toException())
            }
        }
        // Add the listener to the Firebase database reference
        val databaseReference = FirebaseDatabase.getInstance().reference.child("Comment")
        databaseReference.addValueEventListener(commentListener)
    }

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        // Display comments and user info
        commentUserState.value.forEach { (comment, userInfo) ->
            // Display comment
            Text(text = "Content: ${comment.Content}")
            Text(text = "Writing Time: ${comment.WritingTime}")
            Text(text = "User ID: ${comment.userId}")
            Text(text = "Email: ${userInfo.email}")
            Text(text = "Level: ${userInfo.level}")
            Text(text = "Image: ${userInfo.image}")
            Text(text = "Spirit Stone: ${userInfo.spiritStone}")
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Chapter(
    truyenId: String,
    chapterId: String,
    openChapTerDetail: (String) -> Unit,
    openTruyenDetail: (String) -> Unit,

){

    val imageListState = remember { mutableStateOf<List<String>>(emptyList()) }
    val truyenRepository = ListTruyen.TruyenRepository()
    var chapterDetailState by remember { mutableStateOf<ListTruyen.ChapterDetail?>(null) }


    var prevChapterId by remember { mutableStateOf<String?>(null) }
    var nextChapterId by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(truyenId) {
        val truyenDetailListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val chapterListSnapshot = dataSnapshot.child("Chapter").children.toList()
                for ((index, chapterSnapshot) in chapterListSnapshot.withIndex()) {
                    val chapterId1 = chapterSnapshot.child("ChapterId").getValue(String::class.java) ?: ""
                    if(chapterId1==chapterId){
                        val postingTime = chapterSnapshot.child("PostingTime").getValue(String::class.java) ?: ""
                        val nameChapter = chapterSnapshot.child("NameChapter").getValue(String::class.java) ?: ""

                        val imagesSnapshot = chapterSnapshot.child("images")
                        val images = imagesSnapshot.children.associate { it.key.toString() to it.getValue(String::class.java).orEmpty() }

                        chapterDetailState = ListTruyen.ChapterDetail(chapterId1, nameChapter, postingTime, images)
                        imageListState.value = images.values.toList()

                        if (index > 0) {
                            val prevChapterSnapshot = chapterListSnapshot[index - 1]
                            prevChapterId = prevChapterSnapshot.child("ChapterId").getValue(String::class.java) ?: ""
                            Log.d("FirebaseAuth", "previousChild $prevChapterId")
                        }
                        if (index < chapterListSnapshot.size - 1) {
                            val nextChapterSnapshot = chapterListSnapshot[index + 1]
                            nextChapterId = nextChapterSnapshot.child("ChapterId").getValue(String::class.java) ?: ""
                            Log.d("FirebaseAuth", "nextChapterId $nextChapterId")
                        }
                            break
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Xử lý khi có lỗi xảy ra
                Log.d("Firebase", "Lỗi: ${databaseError.message}")
            }
        }

        truyenRepository.getChapterDetail(truyenId,truyenId, truyenDetailListener)
    }



    val View1 = DataHolder.UID
    if(View1!=null){
        val databaseReferenceq = FirebaseDatabase.getInstance().reference.child("Users").child("$View1")
        databaseReferenceq.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val levelString = dataSnapshot.child("Level").getValue(String::class.java)
                    if (levelString != null) {
                        val level = levelString.toIntOrNull()
                        if (level != null) {
                            val newLevel = level + 1
                            databaseReferenceq.child("Level").setValue(newLevel.toString())
                        } else {
                            println("Error: Level is not a valid integer")
                        }
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



//        chapterDetailState?.let { chapterDetail ->
//
//            Row(
//                verticalAlignment = Alignment.Top,
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(text = "Chapter ID: ${chapterDetail.chapterId}")
//                Text(text = "Tên chương: ${chapterDetail.nameChapter}")
//                Text(text = "Thời gian đăng: ${chapterDetail.postingTime}")
//
//                }
//
//        }
    val commentUserState = remember { mutableStateOf<List<Pair<ListTruyen.Comment, ListTruyen.UserInfo>>>(emptyList()) } // Mutable state for storing both comment and user info

    LaunchedEffect(truyenId, chapterId) {
        val commentListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val commentUserList = mutableListOf<Pair<ListTruyen.Comment, ListTruyen.UserInfo>>()
                for (chapterSnapshot in dataSnapshot.children) {
                    val userId = chapterSnapshot.child("userId").getValue(String::class.java) ?: ""
                    val WritingTime = chapterSnapshot.child("WritingTime").getValue(String::class.java) ?: ""
                    val Content = chapterSnapshot.child("Content").getValue(String::class.java) ?: ""
                    val ChapterId123 = chapterSnapshot.child("ChapterId").getValue(String::class.java) ?: ""
                    if("$chapterId" == "$ChapterId123"){
                        val comment = ListTruyen.Comment(
                            userId = userId,
                            WritingTime = WritingTime,
                            Content = Content,
                        )

                        val databaseReference = FirebaseDatabase.getInstance().reference.child("Users").child(userId)
                        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    val email = dataSnapshot.child("Email").getValue(String::class.java)
                                    val level = dataSnapshot.child("Level").getValue(String::class.java)
                                    val image = dataSnapshot.child("Image").getValue(String::class.java)
                                    val Name = dataSnapshot.child("Name").getValue(String::class.java)
                                    val spiritStone = dataSnapshot.child("SpiritStone").getValue(String::class.java)

                                    // Thêm thông tin của người dùng vào danh sách
                                    val userInfo = ListTruyen.UserInfo(
                                        userId = userId,
                                        email = email.toString(),
                                        level = level.toString(),
                                        image = image.toString(),
                                        name = Name.toString(),
                                        spiritStone = spiritStone.toString()
                                    )

                                    // Thêm cả comment và user info vào danh sách
                                    commentUserList.add(Pair(comment, userInfo))
                                    commentUserList.sortedByDescending { it.first.WritingTime }
                                    commentUserState.value = commentUserList
                                } else {
                                    Log.d("User Info", "User with ID $userId does not exist.")
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                Log.e("Firebase", "Error getting user info: ${databaseError.message}")
                            }
                        })
                    }

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Firebase", "loadComment:onCancelled", databaseError.toException())
            }
        }
        // Add the listener to the Firebase database reference
        val databaseReference = FirebaseDatabase.getInstance().reference.child("Comment")
        databaseReference.addValueEventListener(commentListener)
    }








    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()

    ) {
        Row(
            modifier= Modifier.padding(top = 50.dp)
        ){
            Button(onClick = { openTruyenDetail("$truyenId") }) {
                Text(text = "Trang truyện")
            }
        }

        imageListState.value.forEach { imageUrl ->
            Row(
            ){
                GlideImage(
                    model = "$imageUrl",
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.TopCenter
                )
            }
        }


        Row {
            Spacer(modifier = Modifier.height(50.dp))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Button(onClick = {
                prevChapterId?.let {
                    Log.d("Chapter", "Previous chapter ID: $it")
                    openChapTerDetail(it)
                }
            }) {
                Text(text = "Chap trước")
            }
            Spacer(modifier = Modifier.width(20.dp))

            Button(onClick = {
                nextChapterId?.let {
                    Log.d("Chapter", "Next chapter ID: $it")
                    openChapTerDetail(it)
                }
            }) {
                Text(text = "Chap sau")
            }
        }
        Row {
            Spacer(modifier = Modifier.height(100.dp))
        }
        val value = DataHolder.UID
        if(value!=null){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                var cmt by remember {
                    mutableStateOf("")
                }
                Box(modifier = Modifier.weight(1f)){
                    TextField(
                        value = cmt,
                        onValueChange = {
                            cmt =it
                        },
                        label = {Text(text = "Comment")},
//                    placeholder = {Text(text = "coi sai chính tả")},
                        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold),
//                leadingIcon = { Icon(Icons.Default.Person, contentDescription = "text") }
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 0.dp, 10.dp, 0.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .background(Color(rgb(104, 104, 104)))

                    )
                    Button(
                        onClick = {
                            Log.d("tag","$cmt")
                            val value = DataHolder.UID
                            Log.d("tag","$cmt")
                            val currentDateTime = LocalDateTime.now()
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            currentDateTime.format(formatter)
                            if("$cmt" != "" && "$value" != null){
                                val CmtRef = FirebaseDatabase.getInstance().getReference("Comment").push()
                                val CmtId = CmtRef.key
                                val Cmt = HashMap<String, Any>()
                                Cmt["CmtId"] = "$CmtId"
                                Cmt["TruyenId"] = "$truyenId"
                                Cmt["ChapterId"] = "$chapterId"
                                Cmt["userId"] = "$value"
                                Cmt["WritingTime"] =  currentDateTime.format(formatter)
                                Cmt["Content"] = "$cmt"
                                CmtRef.setValue(Cmt)
                                cmt=""
                            }

                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(rgb(231,224,236))),
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Gửi",
                            tint = Color.LightGray,
                            modifier = Modifier.size(35.dp)
                        )
                    }
                }

            }
        }

        commentUserState.value.forEach { (comment, userInfo) ->
//            Text(text = "Content: ${comment.Content}")
//            Text(text = "Writing Time: ${comment.WritingTime}")
//            Text(text = "User ID: ${comment.userId}")
//            Text(text = "Email: ${userInfo.email}")
//            Text(text = "Level: ${userInfo.level}")
//            Text(text = "Image: ${userInfo.image}")
//            Text(text = "Spirit Stone: ${userInfo.spiritStone}")
//            Text(text = "Spirit Stone: ${userInfo.Name}")
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Column(
                    modifier = Modifier
                        .width(60.dp)
                        .padding(5.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(0.dp, 10.dp, 0.dp, 0.dp)
                            .border(width = 1.dp, color = Color.Black)
                    ){
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
                    Row {
                        val level = userInfo.level.toIntOrNull() ?: 0
                        if (level >= 0 && level <= 10) {
                            Text(
                                text = "Luyện Khí",
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center,
                                style = TextStyle(color = Color.Black),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(10.dp, shape = RoundedCornerShape(8.dp))
                            )
                        }
                        else if(level > 10 && level <= 100){
                            Text(
                                text = "Trúc Cơ",
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center,
                                style = TextStyle(color = Color.Black),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(10.dp, shape = RoundedCornerShape(8.dp))
                            )
                        }
                        else if(level > 100 && level <= 1000){
                            Text(
                                text = "Kim Đan",
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center,
                                style = TextStyle(color = Color.Black),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(10.dp, shape = RoundedCornerShape(8.dp))
                            )
                        }
                        else if(level > 1000 && level <= 10000){
                            Text(
                                text = "Nguyên Anh",
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center,
                                style = TextStyle(color = Color.Black),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(10.dp, shape = RoundedCornerShape(8.dp))
                            )
                        }

                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp, 0.dp, 0.dp, 0.dp)

                ) {
                    Row(
                        modifier = Modifier
                            .height(30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text = "${userInfo.name}",
                            modifier =  Modifier.padding(5.dp, 5.dp,0.dp,0.dp)
                        )
                        Text(
                            text = "${comment.WritingTime}",
                            modifier = Modifier.padding(0.dp, 5.dp,15.dp,0.dp)
                        )
                    }
//                    Row(
//                        modifier = Modifier
//                            .fillMaxSize(),
//                    ){
//                        Text(
//                            text = "${comment.Content}",
//                            maxLines = Int.MAX_VALUE,
//                            modifier = Modifier
//                                .padding(5.dp, 5.dp, 0.dp, 0.dp)
//                                .fillMaxWidth()
//                        )
//
//                    }
                    var isExpanded by remember { mutableStateOf(false) }
                    val text = "${comment.Content}"

                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = text,
                                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            if (!isExpanded) {
                                Text(
                                    text = "Xem thêm",
                                    color = Color.Blue,
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                        .clickable { isExpanded = true }
                                )
                            }
                        }
                    }


                }

            }

        }
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 20.dp)
        )












    }
}
