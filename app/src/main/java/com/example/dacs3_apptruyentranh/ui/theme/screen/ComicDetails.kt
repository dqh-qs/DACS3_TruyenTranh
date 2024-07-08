package com.example.dacs3_apptruyentranh.ui.theme.screen

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
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ComicDetails(
    truyenId: String,
    openChapTerDetail: (String) -> Unit,
){

    val chapterListState = remember { mutableStateOf<List<ListTruyen.Chapter>>(emptyList()) }
    val truyenRepository = ListTruyen.TruyenRepository()
    var truyenData: ListTruyen.TruyenData by remember { mutableStateOf(ListTruyen.TruyenData.Error) }
    var hasIncrementedView = false
    LaunchedEffect(truyenId) {
        val truyenDetailListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val chapterList = mutableListOf<ListTruyen.Chapter>()
                val Name = dataSnapshot.child("Name").getValue(String::class.java) ?: ""
                val Describe = dataSnapshot.child("Describe").getValue(String::class.java) ?: ""
                val View = dataSnapshot.child("View").getValue(String::class.java) ?: ""
                val Author = dataSnapshot.child("Author").getValue(String::class.java) ?: ""
                val Category = dataSnapshot.child("Category").getValue(String::class.java) ?: ""
                val Ratings = dataSnapshot.child("Ratings").getValue(String::class.java) ?: ""
                val Star = dataSnapshot.child("Star").getValue(String::class.java) ?: ""
                val Image = dataSnapshot.child("Image").getValue(String::class.java) ?: ""

                val view11 = DataHolder.UID
                if (view11 != null  && !hasIncrementedView) {
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
                    val postingTime = chapterSnapshot.child("PostingTime").getValue(String::class.java) ?: ""
                    val chapterId = chapterSnapshot.child("ChapterId").getValue(String::class.java) ?: ""
                    val nameChapter = chapterSnapshot.child("NameChapter").getValue(String::class.java) ?: ""
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

        truyenRepository.getTruyenDetail(truyenId, truyenDetailListener)
    }


    val commentUserState = remember { mutableStateOf<List<Pair<ListTruyen.Comment, ListTruyen.UserInfo>>>(emptyList()) }
    LaunchedEffect(truyenId, truyenId) {
        val commentListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val commentUserList = mutableListOf<Pair<ListTruyen.Comment, ListTruyen.UserInfo>>()
                for (chapterSnapshot in dataSnapshot.children) {
                    val userId = chapterSnapshot.child("userId").getValue(String::class.java) ?: ""
                    val WritingTime = chapterSnapshot.child("WritingTime").getValue(String::class.java) ?: ""
                    val Content = chapterSnapshot.child("Content").getValue(String::class.java) ?: ""
                    val TruyenId1 = chapterSnapshot.child("TruyenId").getValue(String::class.java) ?: ""
//                    if("$truyenId" == "$TruyenId1"){
//
//                    }
                    if("$truyenId" == "$TruyenId1"){
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
                                    val name = dataSnapshot.child("Name").getValue(String::class.java)
                                    val spiritStone = dataSnapshot.child("SpiritStone").getValue(String::class.java)

                                    // Thêm thông tin của người dùng vào danh sách
                                    val userInfo = ListTruyen.UserInfo(
                                        userId = userId,
                                        email = email.toString(),
                                        level = level.toString(),
                                        image = image.toString(),
                                        name = name.toString(),
                                        spiritStone = spiritStone.toString()
                                    )

                                    // Thêm cả comment và user info vào danh sách
                                    commentUserList.add(Pair(comment, userInfo))
                                    commentUserList.sortedByDescending { it.first.WritingTime }
//                                sortedByDescending
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
        val databaseReference = FirebaseDatabase.getInstance().reference.child("Comment")
        databaseReference.addValueEventListener(commentListener)
    }
    // Add the listener to the Firebase database reference


//    https://cdnnvd.com/nettruyen/thumb/tu-linh-su-manh-nhat.jpg


    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Row {
            Spacer(modifier = Modifier.height(100.dp))
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


                Row {
                    Text(text = "$Name",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 25.sp,
                        )
                }

                Spacer(modifier = Modifier.height(30.dp))
                Row(
                ){
                    GlideImage(
                        model = "$Image",
                        contentDescription = "",
                        modifier = Modifier
                            .aspectRatio(1f)
                        ,

                        alignment = Alignment.TopCenter
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp, 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Text(text="Tác giả")
                    }
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text="$Author")
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp, 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Text(text="Tình trạng")
                    }
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text="Đang cập nhật")
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp, 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Text(text=" Thể loại")
                    }
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text="$Category")
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp, 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Text(text="Lượt xem")
                    }
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text="$View")
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.padding(start = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Row(
                        modifier = Modifier.padding(bottom = 10.dp)
                    ){
                        Icon(painter = painterResource(id = R.drawable.baseline_file_copy_24),
                            contentDescription = null)
                        Text(text = "NỘI DUNG")
                    }

//                    var userId = "TpL7nSqVqyfdFp9zEAeOTM7DUGX2"
                    val userId = DataHolder.UID
                    if(userId!=null){
                        Row(){
                            var isRed by remember { mutableStateOf("") }

                            val databaseReference = FirebaseDatabase.getInstance().reference.child("Follow")

                            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    var found = false
                                    for (followSnapshot in dataSnapshot.children) {
                                        val follow = followSnapshot.getValue(ListTruyen.Follow::class.java)
                                        if (follow != null && follow.TruyenId == truyenId && follow.UserId == userId) {
                                            found = true
                                            break
                                        }
                                    }
                                    isRed = if (found) "huhu" else ""
                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                    // Xử lý khi có lỗi

                                }
                            })

                            Button(
                                onClick = {
//                isRed = if (isRed.isEmpty()) "Has Value" else ""
                                    if(isRed!=""){
                                        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                                for (followSnapshot in dataSnapshot.children) {
                                                    val follow = followSnapshot.getValue(ListTruyen.Follow::class.java)
                                                    if (follow != null && follow.TruyenId == truyenId && follow.UserId == userId) {
                                                        followSnapshot.ref.removeValue()
                                                        isRed = ""
                                                        break
                                                    }
                                                }
                                            }

                                            override fun onCancelled(databaseError: DatabaseError) {
                                                // Xử lý khi có lỗi
                                            }
                                        })

                                    }else{
                                        val FollowRef = FirebaseDatabase.getInstance().getReference("Follow").push()
                                        val FollowId = FollowRef.key
                                        val follow = HashMap<String, Any>()
                                        follow["TruyenId"] = "$truyenId"
                                        follow["FollowId"] = "$FollowId"
                                        follow["UserId"] = "$userId"
                                        FollowRef.setValue(follow)
                                        isRed = "huhu"
                                    }


                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isRed.isNotEmpty()) Color.Red else Color.Green
                                ),
                                modifier = Modifier
                                    .height(40.dp)

                            ) {
                                Text(
                                    text = if (isRed.isNotEmpty()) "Bỏ theo dõi" else "theo dõi",
                                    color = Color.White,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }

                }
                Divider(
                    color = Color.Gray,
                    thickness = 2.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                var isExpanded by remember { mutableStateOf(false) }
                val text = "$Describe"

                Row(
                    modifier = Modifier.padding(start = 10.dp)
                        .fillMaxSize(),
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = text,
                            maxLines = if (isExpanded) Int.MAX_VALUE else 3,
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
            is ListTruyen.TruyenData.Error -> {
                Log.d("Firebase1", "Lỗi: 1111111")
            }
        }









        Row(
            modifier = Modifier.padding(start = 10.dp)
        ){
            Icon(painter = painterResource(id = R.drawable.baseline_view_list_24),
                contentDescription = null)
            Text(text = "Danh Sách Chương")
        }
        Divider(
            color = Color.Gray,
            thickness = 2.dp,
        )

        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth()
                .padding(0.dp,15.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Số Chương")
            Text(text = "Ngày Cập Nhật")
        }
        Divider(
            color = Color.Gray,
            thickness = 2.dp,
        )
        chapterListState.value.forEach { chapter ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    openChapTerDetail("${chapter.chapterId}") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RectangleShape,
                    modifier = Modifier
                        .padding(0.dp, 0.dp,0.dp, 0.dp)
                ) {
                    Text(text = "${chapter.nameChapter}",
                        modifier = Modifier.padding(start = 40.dp),
                        color = Color.Black,
                    )
                }
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
                    Text(text = "$result",
                        modifier = Modifier.padding(end = 60.dp))

//                    Text(
//                        text = "$result",
//                        style = TextStyle(color = Color.Black),
//                    )
                }



//                Text(text = "${chapter.postingTime}",
//                    modifier = Modifier.padding(end = 60.dp))

//                Text(text = "${chapter.chapterId}",
//                    modifier = Modifier.padding(end = 60.dp))
            }
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
            )
        }


        Divider(
            color = Color.Gray,
            thickness = 2.dp,
        )
        Row {
            Spacer(modifier = Modifier.height(50.dp))
        }
        val value1 = DataHolder.UID
        if(value1!=null){
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
                        modifier = Modifier.fillMaxWidth()
                            .padding(10.dp,0.dp,10.dp,0.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .background(Color(android.graphics.Color.rgb(104,104,104)))

                    )
                    Button(
                        onClick = {
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
                                Cmt["ChapterId"] = ""
                                Cmt["userId"] = "$value"
                                Cmt["WritingTime"] =  currentDateTime.format(formatter)
                                Cmt["Content"] = "$cmt"
                                CmtRef.setValue(Cmt)
                                cmt=""
                            }


                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(android.graphics.Color.rgb(231,224,236))),
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





        Row {
            Spacer(modifier = Modifier.height(200.dp))
        }
















    }
}