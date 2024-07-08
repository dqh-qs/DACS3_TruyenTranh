package com.example.dacs3_apptruyentranh.ui.theme.admin

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.dacs3_apptruyentranh.model.ListTruyen
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun InsertChapter(
    truyenId: String,
){
    var NameChapter by remember {
        mutableStateOf("")
    }
    var textFieldValues by remember { mutableStateOf(listOf("")) }
    val imageUrls = textFieldValues.filter { it.isNotEmpty() }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
            TextField(
                value = NameChapter,
                onValueChange = {
                    NameChapter =it
                },
                label = { Text(text = "NameChapter") },
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp,),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 10.dp, 10.dp)
                    .background(Color(android.graphics.Color.rgb(104, 104, 104)))

            )

        }
        textFieldValues.forEachIndexed { index, value ->
            OutlinedTextField(
                value = value,
                onValueChange = { newValue ->
                    textFieldValues = textFieldValues.toMutableList().apply {
                        set(index, newValue)
                        if (index == textFieldValues.lastIndex && newValue.isNotEmpty()) {
                            add("")
                        }
                    }
                },
                label = { Text("TextField ${index + 1}") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 10.dp, 10.dp)
            )
            if (value.isNotEmpty()) {
                GlideImage(
                    model = value,
                    contentDescription = "Image for TextField ${index + 1}",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
            .padding(10.dp, 0.dp, 0.dp, 0.dp)

        ){
            Button(onClick = {
                InsertC(
                    truyenId,
                    NameChapter,
                    imageUrls
                )
            }) {
                Text(text = "Insert")
            }

        }


    }




 }
fun InsertC(
    truyenId: String,
    NameChapter: String,
    imageUrls: List<String>
){
    val TruyenTranh1Id = "$truyenId"
    val chapterId = FirebaseDatabase.getInstance().getReference("ListTruyen").push().key
    val specificTruyenRef = FirebaseDatabase.getInstance().getReference("ListTruyen").child(TruyenTranh1Id)
    val chapterRef = specificTruyenRef.child("Chapter").child("$NameChapter")

    chapterRef.child("ChapterId").setValue(chapterId)
    chapterRef.child("NameChapter").setValue("$NameChapter")
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    currentDateTime.format(formatter)
    chapterRef.child("PostingTime").setValue(currentDateTime.format(formatter))

    imageUrls.forEach { url ->
        Log.d("url", "$url")
    }
    val images = imageUrls.mapIndexed { index, url ->
        "image${index + 1}" to url
    }.toMap()

    images.forEach { (key, value) ->
        Log.d("url", "$key: $value")
    }
    chapterRef.child("images").setValue(images)


}