package com.example.dacs3_apptruyentranh.model

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
object DataHolder {
    var UID: String? = null
}
object DataHolder1 {
    var level: String? = null
}
class ListTruyen {
    data class Truyen(val tenTruyen: String, val truyenId: String,val url: String,val view: String)

    data class Chapter(val postingTime: String, val chapterId: String, val nameChapter: String)

    data class Truyen1(
        val truyenId: String,
        val IdAuthor: String,
        val Name: String,
        val View: String,
        val Ratings: String,
        val Star: String,
        val Image: String,
        val nameChapter: String,
        val postingTime: String,
        val Time: String,
        val Author: String,
        val Describe: String,
        val Category: String,

    )
    class TruyenRepository {

        private val database = FirebaseDatabase.getInstance()

        fun getTruyenList(listener: ValueEventListener) {
            database.getReference("ListTruyen").addValueEventListener(listener)
        }

        fun getTruyenDetail(truyenId: String, listener: ValueEventListener) {
            database.getReference("ListTruyen").child(truyenId).addValueEventListener(listener)
        }
        fun getChapterDetail(truyenId: String, chapterId: String, listener: ValueEventListener) {
            database.getReference("ListTruyen").child(truyenId).addValueEventListener(listener)
        }

        fun getUsersList(listener: ValueEventListener) {
            database.getReference("Users").addValueEventListener(listener)
        }



    }
    sealed class TruyenData {
        data class Success(
            val Name: String,
            val Describe: String,
            val View: String,
            val Author: String,
            val Category: String,
            val Ratings: String,
            val Star: String,
            val Image: String,
        ) : TruyenData()
        object Error : TruyenData()
    }



    data class ChapterDetail(
        val chapterId: String = "",
        val nameChapter: String = "",
        val postingTime: String = "",
        val images: Map<String, String> = emptyMap()
    )
    data class Comment(
        val ChapterId: String = "",
        val CmtId: String = "",
        val Content: String = "",
        val TruyenId: String = "",
        val WritingTime: String = "",
        val userId: String = ""

    )
    data class UserInfo(
        val userId: String,
        val email: String,
        val level: String,
        val image: String,
        val spiritStone: String,
        val name: String = ""
    )
    data class Follow(
        val FollowId: String = "",
        val TruyenId: String = "",
        val UserId: String = ""
    )




}