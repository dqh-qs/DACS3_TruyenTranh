package com.example.dacs3_apptruyentranh.ui.theme.admin

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.dacs3_apptruyentranh.model.DataHolder
import com.example.dacs3_apptruyentranh.model.DataHolder1
import com.example.dacs3_apptruyentranh.model.ListTruyen
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun InsertTruyen(
    openUpdate: (String) -> Unit,
    openinsert: (String) -> Unit,
    opentruyendetails: (String) -> Unit,
    ) {
    val idtacgia = DataHolder.UID
    val levellogin = DataHolder1.level
    if(levellogin == "2"){
        //    var idtacgia = "TpL7nSqVqyfdFp9zEAeOTM7DUGX2"
        val truyenListState = remember { mutableStateOf<List<ListTruyen.Truyen1>>(emptyList()) }
        val truyenRepository = ListTruyen.TruyenRepository()

        LaunchedEffect(Unit) {
            val truyenListListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val truyenList = mutableListOf<ListTruyen.Truyen1>()
                    for (truyenSnapshot in dataSnapshot.children) {
                        val truyenId = truyenSnapshot.child("TruyenId").getValue(String::class.java) ?: ""
                        val IdAuthor = truyenSnapshot.child("IdAuthor").getValue(String::class.java) ?: ""
                        val Name = truyenSnapshot.child("Name").getValue(String::class.java) ?: ""
                        val View = truyenSnapshot.child("View").getValue(String::class.java) ?: ""
                        val Ratings = truyenSnapshot.child("Ratings").getValue(String::class.java) ?: ""
                        val Star = truyenSnapshot.child("Star").getValue(String::class.java) ?: ""
                        val Image = truyenSnapshot.child("Image").getValue(String::class.java) ?: ""
                        val Time = truyenSnapshot.child("Time").getValue(String::class.java) ?: ""


                        val Author = truyenSnapshot.child("Author").getValue(String::class.java) ?: ""
                        val Describe = truyenSnapshot.child("Describe").getValue(String::class.java) ?: ""
                        val Category = truyenSnapshot.child("Category").getValue(String::class.java) ?: ""






                        if (IdAuthor == idtacgia) {
                            var postingTime = ""
                            var nameChapter = ""
                            for (chapterSnapshot in truyenSnapshot.child("Chapter").children) {
                                postingTime = chapterSnapshot.child("PostingTime").getValue(String::class.java) ?: ""
                                nameChapter = chapterSnapshot.child("NameChapter").getValue(String::class.java) ?: ""
                            }
                            truyenList.add(ListTruyen.Truyen1(truyenId, IdAuthor, Name, View, Ratings, Star, Image, postingTime, nameChapter, Time,Author,Describe,Category))

                        }
                    }
                    truyenList.sortByDescending { it.Time }
                    truyenListState.value = truyenList
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Xử lý khi có lỗi xảy ra
                    Log.d("Firebase", "Lỗi: ${databaseError.message}")
                }
            }

            truyenRepository.getTruyenList(truyenListListener)
        }








        var Author by remember {
            mutableStateOf("")
        }
        var Describe by remember {
            mutableStateOf("")
        }
        var Category by remember {
            mutableStateOf("")
        }
        var Image by remember {
            mutableStateOf("")
        }
        var Name by remember {
            mutableStateOf("")
        }
        var errorEmty by remember { mutableStateOf(false) }









        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()


        ){


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 70.dp, 10.dp, 0.dp)
            ){
                if(errorEmty){
                    Text(
                        text = "Không được để trống",
                        color = Color.Red,
                        modifier = Modifier.padding(end= 100.dp)
                    )

                }

            }
//      Author
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                TextField(
                    value = Author,
                    onValueChange = {
                        Author =it
                    },
                    label = { Text(text = "Author") },
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp,),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 10.dp)
                        .background(Color(android.graphics.Color.rgb(104, 104, 104)))

                )

            }

            // Describe
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                TextField(
                    value = Describe,
                    onValueChange = {
                        Describe =it
                    },
                    label = { Text(text = "Describe") },
//                    placeholder = {Text(text = "coi sai chính tả")},
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp,),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 10.dp)
//                    .clip(RoundedCornerShape(25.dp))
                        .background(Color(android.graphics.Color.rgb(104, 104, 104)))

                )

            }
            // Category
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                TextField(
                    value = Category,
                    onValueChange = {
                        Category =it
                    },
                    label = { Text(text = "Category") },
//                    placeholder = {Text(text = "coi sai chính tả")},
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp,),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 10.dp)
//                    .clip(RoundedCornerShape(25.dp))
                        .background(Color(android.graphics.Color.rgb(104, 104, 104)))

                )

            }
            //   Name
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
//                    placeholder = {Text(text = "coi sai chính tả")},
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp,),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 10.dp)
                        .background(Color(android.graphics.Color.rgb(104, 104, 104)))

                )

            }
            //  Image
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                TextField(
                    value = Image,
                    onValueChange = {
                        Image =it
                    },
                    label = { Text(text = "Image") },
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp,),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 10.dp)
                        .background(Color(android.graphics.Color.rgb(104, 104, 104)))

                )

            }

            Row(){
                Column(
                    modifier = Modifier
                        .padding(10.dp, 0.dp, 0.dp, 10.dp)
                        .weight(0.3f)
                ){
                    Button(onClick = {
                        if(Author.isNotEmpty() && Describe.isNotEmpty() && Category.isNotEmpty() && Image.isNotEmpty() && Name.isNotEmpty()){
                            errorEmty = false
                            Insert(
                                Name,
                                Image,
                                Category,
                                Describe,
                                Author,
                            )
                            Name = ""
                            Image = ""
                            Category = ""
                            Describe = ""
                            Author = ""

                        }else {
                            errorEmty = true


                        }
                    }) {
                        Text(text = "Insert")
                    }

                }
                Column(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 10.dp, 10.dp)
                        .weight(0.7f)
                    ,
                ){
                    GlideImage(
                        model = "$Image",
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                }
            }



//        truyenId, IdAuthor, Name, View, Ratings, Star, Image, postingTime, nameChapter, Time
//        Author Describe Category

            for (truyen in truyenListState.value) {
//            Log.d("TruyenList", "TruyenId: ${truyen.Author}, Name: ${truyen.Describe}, IdAuthor: ${truyen.Category} Time: ${truyen.Time}")
                Row(){
                    Column(
                        modifier = Modifier
                            .padding(3.dp)
                            .weight(0.7f)
                            .shadow(15.dp, shape = RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Gray)
                            .height(200.dp)
                        ,
                    ){
                        Box(){
                            GlideImage(
                                model = "${truyen.Image}",
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop,
//                  alignment = Alignment.TopCenter

                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp)
                                ,
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.Bottom,
                            ){
                                Text(text = "${truyen.Name}",
                                    style = TextStyle(color = Color.Black),
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    modifier = Modifier

                                        .shadow(10.dp, shape = RoundedCornerShape(5.dp))
                                        .background(
                                            Color.White.copy(0.8f),
                                            shape = RoundedCornerShape(5.dp)
                                        )
                                        .border(1.dp, Color.White)
                                        .padding(10.dp, 3.dp, 10.dp, 3.dp)

                                )


                            }


                        }
                    }

                    Column(
                        modifier = Modifier
                            .padding(3.dp, 0.dp, 3.dp, 3.dp)
                            .weight(0.3f)
                    ){

                        Button(
                            onClick = { openUpdate("${truyen.truyenId}") },

                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier
                                .fillMaxSize(),
                            shape = RectangleShape,
                        ) {
                            Text(text = "Edit")
                        }
                        Button(onClick = {
                            openinsert("${truyen.truyenId}")
                        },
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier
                                .fillMaxSize(),
                            shape = RectangleShape,
                        ) {
                            Text(text = "Thêm Chap")
                        }
                        Button(onClick = {
                            opentruyendetails("${truyen.truyenId}")
                        },
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier
                                .fillMaxSize(),
                            shape = RectangleShape,
                        ) {
                            Text(text = "Xem")
                        }

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
                text = "không đủ quyền truy cập",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 300.dp),
                textAlign = TextAlign.Center

            )
        }

    }

}



//@Composable
fun Insert(
    Name: String,
    Image: String,
    Category: String,
    Describe: String,
    Author: String,
){

    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    currentDateTime.format(formatter)

        val TruyenTranhRef = FirebaseDatabase.getInstance().getReference("ListTruyen").push()
        val TruyenId = TruyenTranhRef.key
        val truyen = HashMap<String, Any>()
        truyen["TruyenId"] = "$TruyenId"
        truyen["Name"] = "$Name"
        truyen["Chapter"] = "Chapter"
        truyen["IdAuthor"] = "TpL7nSqVqyfdFp9zEAeOTM7DUGX2"
        truyen["Describe"] = "$Describe"
        truyen["View"] = "0"
        truyen["Time"] =  currentDateTime.format(formatter)
        truyen["Author"] = "$Author"
        truyen["Category"] = "$Category"
        truyen["Ratings"] = "0"
        truyen["Star"] = "0"
        truyen["Image"] = "$Image"
        TruyenTranhRef.setValue(truyen)

}