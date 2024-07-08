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
import com.example.dacs3_apptruyentranh.model.ListTruyen
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun UpdateTruyen(
    truyenId: String,

){


    val truyenRepository = ListTruyen.TruyenRepository()
    var truyenData: ListTruyen.TruyenData by remember { mutableStateOf(ListTruyen.TruyenData.Error) }
    LaunchedEffect(truyenId) {
        val truyenDetailListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val Name = dataSnapshot.child("Name").getValue(String::class.java) ?: ""
                val Describe = dataSnapshot.child("Describe").getValue(String::class.java) ?: ""
                val View = dataSnapshot.child("View").getValue(String::class.java) ?: ""
                val Author = dataSnapshot.child("Author").getValue(String::class.java) ?: ""
                val Category = dataSnapshot.child("Category").getValue(String::class.java) ?: ""
                val Ratings = dataSnapshot.child("Ratings").getValue(String::class.java) ?: ""
                val Star = dataSnapshot.child("Star").getValue(String::class.java) ?: ""
                val Image = dataSnapshot.child("Image").getValue(String::class.java) ?: ""
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



    when (truyenData) {
        is ListTruyen.TruyenData.Success -> {
            val Name1 = (truyenData as ListTruyen.TruyenData.Success).Name
            val Describe1 = (truyenData as ListTruyen.TruyenData.Success).Describe
            val Author1 = (truyenData as ListTruyen.TruyenData.Success).Author
            val Category1 = (truyenData as ListTruyen.TruyenData.Success).Category
            val Image1 = (truyenData as ListTruyen.TruyenData.Success).Image

            var errorEmty by remember { mutableStateOf(false) }
            var Author by remember {
                mutableStateOf("$Author1")
            }
            var Describe by remember {
                mutableStateOf("$Describe1")
            }
            var Category by remember {
                mutableStateOf("$Category1")
            }
            var Image by remember {
                mutableStateOf("$Image1")
            }
            var Name by remember {
                mutableStateOf("$Name1")
            }



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
                        //                    placeholder = {Text(text = "coi sai chính tả")},
                        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp,),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 0.dp, 10.dp, 10.dp)
                            //                    .clip(RoundedCornerShape(25.dp))
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

                                Update(
                                    truyenId,
                                    Name,
                                    Image,
                                    Category,
                                    Describe,
                                    Author,
                                )

                            }else {
                                errorEmty = true


                            }
                        }) {
                            Text(text = "Edit")
                        }

                    }
                    Column(
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 10.dp,10.dp)
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
            }
        }


        else -> {}
    }

}



//@Composable
fun Update(
    TruyenId: String,
    Name: String,
    Image: String,
    Category: String,
    Describe: String,
    Author: String,
) {
    val IDtg = DataHolder.UID
    val TruyenTranhRef = FirebaseDatabase.getInstance().getReference("ListTruyen").child(TruyenId)
    val truyen = HashMap<String, Any>()
    truyen["TruyenId"] = TruyenId
    truyen["Name"] = Name
    truyen["IdAuthor"] = "$IDtg"
    truyen["Describe"] = Describe
    truyen["Author"] = Author
    truyen["Category"] = Category
    truyen["Image"] = Image
    TruyenTranhRef.updateChildren(truyen)
}
