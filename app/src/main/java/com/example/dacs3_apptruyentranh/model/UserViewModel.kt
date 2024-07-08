package com.example.dacs3_apptruyentranh.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


data class Truyen(
    val truyenId: String,
    val tenTruyen: String,
    val url: String // Example URL for image
)

data class Chapter(
    val chapterId: String,
    val nameChapter: String,
    val postingTime: String // Assuming posting time is a String
)
class TruyenRepository {
    // Example function to fetch Truyen list for a specific page
    fun fetchTruyenList(page: Int, itemsPerPage: Int, callback: (List<Truyen>) -> Unit) {
        // Example implementation using a callback
        // Replace with your actual data fetching logic
        // Simulating data fetching for demonstration
        val truyenList = mutableListOf<Truyen>()
        for (i in 1..itemsPerPage) {
            val truyen = Truyen(
                truyenId = "ID_${page * itemsPerPage + i}",
                tenTruyen = "Truyen ${page * itemsPerPage + i}",
                url = "https://example.com/image_${page * itemsPerPage + i}.jpg"
            )
            truyenList.add(truyen)
        }
        callback(truyenList)
    }

    // Example function to fetch chapters for a Truyen
    fun fetchChapters(truyenId: String, callback: (List<Chapter>) -> Unit) {
        // Example implementation using a callback
        // Replace with your actual data fetching logic
        // Simulating data fetching for demonstration
        val chapterList = mutableListOf<Chapter>()
        for (i in 1..5) {
            val chapter = Chapter(
                chapterId = "Chapter_$i",
                nameChapter = "Chapter $i",
                postingTime = "2024-06-17 12:00:00"
            )
            chapterList.add(chapter)
        }
        callback(chapterList)
    }
}

//class UserViewModel : ViewModel() {
////    private val _uid = MutableLiveData<String>()
//
//    val uid = MutableLiveData<String?>()
//
//    fun setUid(uid: String?) {
//        this.uid.value = uid
//    }
//
//    fun getUid(): String? {
//        return uid.value
//    }
//    var currentLevel = mutableStateOf("3")
//
//    fun updateLevel(newLevel: String) {
//        currentLevel.value = newLevel
//    }
//}