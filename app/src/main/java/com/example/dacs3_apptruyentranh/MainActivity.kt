package com.example.dacs3_apptruyentranh


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.dacs3_apptruyentranh.Navigation.NavListTruyen
import com.example.dacs3_apptruyentranh.auth.LoginScreen
import com.example.dacs3_apptruyentranh.auth.MainScreen
import com.example.dacs3_apptruyentranh.auth.SignupScreen
import com.example.dacs3_apptruyentranh.auth.SuccessScreen
import com.example.dacs3_apptruyentranh.main.NotificationMessage
import com.example.dacs3_apptruyentranh.model.DataHolder
import com.example.dacs3_apptruyentranh.ui.theme.DACS3_AppTruyenTranhTheme
import com.example.dacs3_apptruyentranh.ui.theme.admin.InsertChapter
import com.example.dacs3_apptruyentranh.ui.theme.admin.InsertTruyen
import com.example.dacs3_apptruyentranh.ui.theme.admin.TruyenDetails
import com.example.dacs3_apptruyentranh.ui.theme.screen.Chapter
import com.example.dacs3_apptruyentranh.ui.theme.screen.ComicDetails
import com.example.dacs3_apptruyentranh.ui.theme.screen.DisplayFollowItems


import com.example.dacs3_apptruyentranh.ui.theme.screen.Home2
import com.example.dacs3_apptruyentranh.ui.theme.screen.PersonalPage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.statusBarColor = getColor(R.color.black)
            window.navigationBarColor = getColor(R.color.black)
            DACS3_AppTruyenTranhTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavListTruyen()
//                    Test()
//                                    DisplayFollowItems()







//                    InsertChapter(
//                        "-NxnJvadZpvCF-6vC7lZ"
//                    )



//                    AuthenticationApp()
//                    Home()
//                    Test()

//                    ComicDetails( "-NxnJvadZpvCF-6vC7lZ"){}
//                    CommentDisplay(
//                        "-NxnJvadZpvCF-6vC7lZ",
//                        "-NxnJvaqe_lTpH9_Eef7")
//                    UserDispaly("TpL7nSqVqyfdFp9zEAeOTM7DUGX2")
//                    Comment()
//                    Home2(){}
//                    nếu homw2 có lỗi thì nó lỗi cái comlumn.shadow(15.dp, shape = RoundedCornerShape(8.dp))
//                    sửa cái padding 5dp
//                    NavListTruyen("3")

//                    AuthenticationApp()
//                    InsertChapter()

//                    PersonalPage()
//                    MyApp()










                        }
        }
    }
}




//    fun findTruyenByChapterId(chapterId: String, onResult: (String, String) -> Unit) {
//        val truyenRef = FirebaseDatabase.getInstance().getReference("ListTruyen")
//
//        truyenRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (truyenSnapshot in dataSnapshot.children) {
//                    val truyenId = truyenSnapshot.key
//                    val truyenName = truyenSnapshot.child("Name").getValue(String::class.java) ?: ""
//
//                    for (chapterSnapshot in truyenSnapshot.child("Chapter").children) {
//                        val currentChapterId = chapterSnapshot.child("ChapterId").getValue(String::class.java)
//                        Log.d("Truyen", "$currentChapterId")
//                        if (currentChapterId == chapterId) {
//                            onResult(truyenName, truyenId ?: "")
//
//                            return
//                        }
//                    }
//                }
//                onResult("", "") // Không tìm thấy
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Xử lý lỗi
//                onResult("", "")
//            }
//        })
//    }
//    @Composable
//    fun FindTruyenScreen(chapterId: String) {
//        var truyenName by remember { mutableStateOf("") }
//        var truyenId by remember { mutableStateOf("") }
//
//        LaunchedEffect(chapterId) {
//            findTruyenByChapterId(chapterId) { name, id ->
//                truyenName = name
//                truyenId = id
//            }
//        }
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            if (truyenName.isNotEmpty() && truyenId.isNotEmpty()) {
//                Text(text = "Tên truyện: $truyenName")
//                Text(text = "TruyenId: $truyenId")
//            } else {
//                Text(text = "Không tìm thấy truyện")
//            }
//        }
//    }

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Screen(){
    Column {

        Row {
            Spacer(modifier = Modifier.height(200.dp))
        }
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Cột 1
            Box(
//            modifier = Modifier.background(Color.Red)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.background(Color.Green),
                ) {
                    GlideImage(
                        model = "https://cdnntx.com/nettruyen/thumb/toi-thang-cap-mot-minh-2.jpg",
                        contentDescription = "",
                        modifier = Modifier
                            .width(185.dp)
                            .height(150.dp)
                        ,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.TopCenter
                    )
                    Text(text = "haha")
                    Text(text = "hihi")
                }
            }

            Box(
//            modifier = Modifier.background(Color.Red)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.background(Color.Blue),
                ) {
                    GlideImage(
                        model = "https://cdnntx.com/nettruyen/thumb/toi-thang-cap-mot-minh-2.jpg",
                        contentDescription = "",
                        modifier = Modifier
                            .width(185.dp)
                            .height(150.dp)
//                            .aspectRatio(1f)
                        ,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.TopCenter
                    )
                    Text(text = "haha")
                    Text(text = "hihi")
                }
            }

        }
        Spacer(modifier = Modifier.height(10.dp))

    }
//    Row(
//        verticalAlignment = Alignment.Top,
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceAround
//        ) {
//        // Cột 1
//        Box(
////            modifier = Modifier.background(Color.Red)
//        ) {
//                Column(
//                    verticalArrangement = Arrangement.SpaceAround,
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier.background(Color.Green),
//                ) {
//                    GlideImage(
//                        model = "https://cdnntx.com/nettruyen/thumb/toi-thang-cap-mot-minh-2.jpg",
//                        contentDescription = "",
//                        modifier = Modifier
//                            .width(185.dp)
//                            .height(150.dp)
//                        ,
//                        contentScale = ContentScale.Crop,
//                        alignment = Alignment.TopCenter
//                    )
//                    Text(text = "haha")
//                    Text(text = "hihi")
//                }
//        }
//
//        Box(
////            modifier = Modifier.background(Color.Red)
//        ) {
//            Column(
//                verticalArrangement = Arrangement.SpaceAround,
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier.background(Color.Blue),
//            ) {
//                GlideImage(
//                    model = "https://cdnntx.com/nettruyen/thumb/toi-thang-cap-mot-minh-2.jpg",
//                    contentDescription = "",
//                    modifier = Modifier
//                        .width(185.dp)
//                        .height(150.dp)
////                            .aspectRatio(1f)
//                    ,
//                    contentScale = ContentScale.Crop,
//                    alignment = Alignment.TopCenter
//                )
//                Text(text = "haha")
//                Text(text = "hihi")
//            }
//        }
//
//    }


}

@Composable
fun Test(){
//                                    val userId = "UID của người dùng cần xóa"
//                                    val usersRef = FirebaseDatabase.getInstance().getReference("users")
//                                    usersRef.child(userId).removeValue()











//    val context = LocalContext.current
//    val email = "abc3@gmail.com"
//    val pass = "Abc123!"
//    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val user = FirebaseAuth.getInstance().currentUser
//                user?.let {
//                    val userId = it.uid // Lấy ID của người dùng
//                    val usersRef = FirebaseDatabase.getInstance().getReference("Users")
//                    val userEmail = it.email
//                    val truyen = HashMap<String, Any>()
//                    truyen["UserId"] = "$userId"
//                    truyen["Pass"] = "$pass"
//                    truyen["Email"] = "$userEmail"
//                    truyen["Name"] = "Vực chủ tu tiên"
//                    truyen["Image"] = "https://cdn-images-3.listennotes.com/podcasts/the-emerald-radio/t%E1%BA%ADp-2-solo-leveling-t%C3%B4i-D1P2x-pcJmv-qviDxVF5g_-.1400x1400.jpg"
//                    truyen["Login"] = "Login google"
//                    truyen["SpiritStone"] = "Linh thạch"
//                    truyen["Level"] = "10"
//                    truyen["Delete"] = "delete"
//                    truyen["Level_login"] = "Level_login"
//                    usersRef.child(userId).setValue(truyen)
//                }
//            } else {
//                Log.d("YourTag", "Error creating user: ${task.exception?.message}")
//            }
//        }




//    val database = Firebase.database
//
//    Column() {
//
//        val context = LocalContext.current
//        val email = "abc2@gmail.com"
//        val pass = "Abc123!"
//        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("YourTag", "lỗi realtime222")
//                    val user = FirebaseAuth.getInstance().currentUser
//                    user?.let {
//                        val usersRef = FirebaseDatabase.getInstance().getReference("users")
//                        val userId = it.uid
//                        val userEmail = it.email
////                                        val truyen = HashMap<String, Any>()
////                                        truyen["userId"] = "$userId"
////                                        truyen["userEmail"] = "TenTruyen"
////                                        usersRef.setValue(truyen)
//                        usersRef.child(userId).child("email").setValue(userEmail)
//                    }
//                } else {
//                    Log.d("YourTag", "lỗi realtime333")
//                }
//            }
//    }




//        val TruyenTranhRef = FirebaseDatabase.getInstance().getReference("ListTruyen").push()
//        val TruyenId = TruyenTranhRef.key
//        val truyen = HashMap<String, Any>()
//        truyen["TruyenId"] = "$TruyenId"
//        truyen["Name"] = " BÁCH QUỶ DẠ HÀNH, TUYẾT THI HỘ ĐẠO"
//        truyen["Chapter"] = "Chapter"
//        truyen["Describe"] = "Mô tả"
//        truyen["View"] = "0"
//        truyen["Author"] = "Đang cập nhật"
//        truyen["Category"] = "Action - Adventure - Manhua - Mystery - Truyện Màu - Xuyên Không"
//        truyen["Ratings"] = "0"
//        truyen["Star"] = "0"
//        truyen["Time"] = currentDateTime.format(formatter)
//        truyen["Image"] = "https://www.nhattruyenss.net/images/comics/ngay-toi-sinh-ra-bach-quy-da-hanh-tuyet-thi-ho-dao.jpg"
//        TruyenTranhRef.setValue(truyen)
//
//        val chapterId = FirebaseDatabase.getInstance().getReference("ListTruyen").push().key
//
//        val chapterRef = TruyenTranhRef.child("Chapter").child("Chapter1")
//
//        chapterRef.child("ChapterId").setValue(chapterId)
//        chapterRef.child("NameChapter").setValue("Chapter1")
//        chapterRef.child("PostingTime").setValue(currentDateTime.format(formatter))
//
//        val images = hashMapOf(
//            "image1" to "https://s3.mideman.com/file/mideman/cmanga/chapter/489390/1.png?v=12",
//            "image2" to "https://s3.mideman.com/file/mideman/cmanga/chapter/489390/2.png?v=12",
//            "image3" to "https://s3.mideman.com/file/mideman/cmanga/chapter/489390/3.png?v=12",
//            "image4" to "https://s3.mideman.com/file/mideman/cmanga/chapter/489390/4.png?v=12",
//            "image5" to "https://s3.mideman.com/file/mideman/cmanga/chapter/489390/5.png?v=12",
//            "image6" to "https://s3.mideman.com/file/mideman/cmanga/chapter/489390/6.png?v=12",
//            "image7" to "https://s3.mideman.com/file/mideman/cmanga/chapter/489390/7.png?v=12",
//            "image8" to "https://s3.mideman.com/file/mideman/cmanga/chapter/489390/8.png?v=12",
//            "image9" to "https://s3.mideman.com/file/mideman/cmanga/chapter/489390/9.png?v=12",
//
//        )
//        chapterRef.child("images").setValue(images)

////// theem chap
//                    val TruyenTranh1Id = "-NxnJvadZpvCF-6vC7lZ"
//
//                    val chapterId = FirebaseDatabase.getInstance().getReference("ListTruyen").push().key
//
//                    val specificTruyenRef = FirebaseDatabase.getInstance().getReference("ListTruyen").child(TruyenTranh1Id)
//
//                    val chapterRef = specificTruyenRef.child("Chapter").child("Chapter4")
//                    chapterRef.child("ChapterId").setValue(chapterId)
//                    chapterRef.child("NameChapter").setValue("Chapter4")
//                    chapterRef.child("PostingTime").setValue(currentDateTime.format(formatter))
//
//// Đặt danh sách các URL hình ảnh của chương
//                    val images = hashMapOf(
//
//                        "image1" to "https://s3.mideman.com/file/mideman/cmanga/chapter/488626/8.png?v=12",
//                        "image2" to "https://s3.mideman.com/file/mideman/cmanga/chapter/488626/7.png?v=12",
//                        "image3" to "https://s3.mideman.com/file/mideman/cmanga/chapter/137893/12.png?v=12",
//                        "image4" to "https://s3.mideman.com/file/mideman/cmanga/chapter/137893/14.png?v=12",
//                        "image5" to "https://s3.mideman.com/file/mideman/cmanga/chapter/210283/35.png?v=12",
//                        "image6" to "https://s3.mideman.com/file/mideman/cmanga/chapter/210283/36.png?v=12",
//                        "image7" to "https://s3.mideman.com/file/mideman/cmanga/chapter/210283/37.png?v=12",
//                        "image8" to "https://s3.mideman.com/file/mideman/cmanga/chapter/210283/38.png?v=12",
//                        "image9" to "https://s3.mideman.com/file/mideman/cmanga/chapter/210283/39.png?v=12",


//                    )
//                    chapterRef.child("images").setValue(images)





}


@Composable
fun Home(){
//                        val email = "abc@gmail.com"
//                        val pass = "Abc123!"
//
//                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
//                        .addOnCompleteListener { task ->
//                            if (task.isSuccessful) {
//                                // Đăng nhập thành công
//                                val user = FirebaseAuth.getInstance().currentUser
//                                val uid = user?.uid
//                                if (uid != null) {
//                                    // Bây giờ bạn đã có UID của người dùng
//                                    // Bạn có thể sử dụng UID này để truy xuất dữ liệu từ Realtime Database hoặc thực hiện các hoạt động khác
//                                    Log.d("FirebaseAuth", "UID của người dùng: $uid")
////                                    val userId = "UID của người dùng cần xóa"
////                                    val usersRef = FirebaseDatabase.getInstance().getReference("users")
////                                    usersRef.child(userId).removeValue()
//                                } else {
//                                    Log.d("FirebaseAuth", "Không tìm thấy UID của người dùng")
//                                }
//                            } else {
//                                // Đăng nhập không thành công
//                                Log.d("FirebaseAuth", "Đăng nhập không thành công: ${task.exception?.message}")
//                            }
//                        }
//
//                    val userId = "tP9yUF0UGWgxfHQwur46ApxyIaa2"
//                    val usersRef = FirebaseDatabase.getInstance().getReference("users")
//                    usersRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
//                        override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            // Xử lý dữ liệu được trả về từ Realtime Database
//                            val userData = dataSnapshot.getValue(User::class.java)
//                            // userData chứa thông tin của người dùng
//                            Log.d("FirebaseAuth", " userData chứa thông tin của người dùng: $userData")
//                        }
//
//                        override fun onCancelled(databaseError: DatabaseError) {
//                            // Xử lý khi có lỗi xảy ra
//                            Log.e("Firebase", "Lỗi khi truy xuất thông tin người dùng: ${databaseError.message}")
//                        }
//                    })








//    val TruyenTranhRef = FirebaseDatabase.getInstance().getReference("ListTruyen").push()
//                    val TruyenTranh1Id = TruyenTranhRef.key
//
//                    val truyen = HashMap<String, Any>()
//                    truyen["tenTruyen"] = "TenTruyen"
//                    truyen["Image"] = "url111111111"
//                    truyen["TruyenTranh1Id"] = "$TruyenTranh1Id"
//
//                    TruyenTranhRef.setValue(truyen)
//
//
//
//                    val chapterId = FirebaseDatabase.getInstance().getReference("ListTruyen").push().key
//
//                    val chapterRef = TruyenTranhRef.child("Chapter").child("Chapter1")
//
////                    val chap = HashMap<String, Any>()
////                    truyen["ChapId"] = "$chapterId"
//
//                    chapterRef.child("ChapterId").setValue(chapterId)
//                    chapterRef.child("content").setValue("Content of Chapter1")
//                    val images = hashMapOf(
//                        "image1" to "url_to_image1_1",
//                        "image2" to "url_to_image2_1",
//                        "image3" to "url_to_image3_1"
//                    )
//                    chapterRef.child("images").setValue(images)

//// theem chap
//                    val TruyenTranh1Id = "-NxczASmkBcih06V8CyN"
//                    // Lấy ID mới cho chương
//                    val chapterId = FirebaseDatabase.getInstance().getReference("ListTruyen").push().key
//
//// Đường dẫn đến truyện cụ thể (ví dụ: TruyenTranh1Id = "-NxThB0bD8B5OnLBQWdZ")
//                    val specificTruyenRef = FirebaseDatabase.getInstance().getReference("ListTruyen").child(TruyenTranh1Id)
//
//// Đường dẫn đến danh sách các chương của truyện và thêm chương mới
//                    val chapterRef = specificTruyenRef.child("Chapter").child("Chapter2")
//
//// Đặt giá trị cho ID của chương
//                    chapterRef.child("ChapterId").setValue(chapterId)
//
//// Đặt nội dung của chương
//                    chapterRef.child("content").setValue("Content of Chapter2")
//
//// Đặt danh sách các URL hình ảnh của chương
//                    val images = hashMapOf(
//                        "image1" to "url_to_image1_2",
//                        "image2" to "url_to_image2_2",
//                        "image3" to "url_to_image3_2"
//                    )
//                    chapterRef.child("images").setValue(images)




//    val truyenListener = object : ValueEventListener {
//        override fun onDataChange(dataSnapshot: DataSnapshot) {
//            // Kiểm tra nếu có dữ liệu tồn tại
//
//            if (dataSnapshot.exists()) {
//                for (truyenSnapshot in dataSnapshot.children) {
//                    // Lấy dữ liệu của mỗi truyện
//                    val tenTruyen = truyenSnapshot.child("tenTruyen").getValue(String::class.java)
//                    val truyenId = truyenSnapshot.child("TruyenTranh1Id").getValue(String::class.java)
//
//                    // Hiển thị thông tin truyện
//                    Log.d("Truyen", "Tên truyện: $tenTruyen, ID truyện: $truyenId")
//
//                    // Đường dẫn đến truyện cụ thể
//                    val truyenRef = FirebaseDatabase.getInstance().getReference("ListTruyen").child(truyenId.toString())
//
//                    // Lắng nghe sự thay đổi trong truyện cụ thể
//                    val truyenDetailListener = object : ValueEventListener {
//                        override fun onDataChange(truyenDataSnapshot: DataSnapshot) {
//                            if (truyenDataSnapshot.exists()) {
//                                // Lấy thông tin của truyện từ dataSnapshot
//                                val tenTruyen = truyenDataSnapshot.child("tenTruyen").getValue(String::class.java)
//                                // Hiển thị thông tin của truyện
//                                Log.d("Truyen", "Tên truyện: $tenTruyen")
//
//                                // Lặp qua các chương của truyện
//                                for (chapterSnapshot in truyenDataSnapshot.child("Chapter").children) {
//                                    // Lấy nội dung của chương
//                                    val content = chapterSnapshot.child("content").getValue(String::class.java)
//                                    val ChapterId = chapterSnapshot.child("ChapterId").getValue(String::class.java)
//                                    // Hiển thị nội dung của chương
//                                    Log.d("Truyen", "Nội dung chương: $content")
//                                    Log.d("Truyen", "ID truyện: $ChapterId")
//
//                                }
//                            }
//                        }
//
//                        override fun onCancelled(databaseError: DatabaseError) {
//                            // Xử lý khi có lỗi xảy ra
//                            Log.d("Firebase", "Lỗi: ${databaseError.message}")
//                        }
//                    }
//
//                    // Thêm trình lắng nghe vào đường dẫn của truyện trong cơ sở dữ liệu
//                    truyenRef.addValueEventListener(truyenDetailListener)
//                }
//            }
//        }
//
//        override fun onCancelled(databaseError: DatabaseError) {
//            // Xử lý khi có lỗi xảy ra
//            Log.d("Firebase", "Lỗi: ${databaseError.message}")
//        }
//    }
//
//// Thêm trình lắng nghe vào đường dẫn "ListTruyen" trong cơ sở dữ liệu
//    FirebaseDatabase.getInstance().getReference("ListTruyen").addValueEventListener(truyenListener)




















//
//coi lại cái này

//    val idTruyen = "-NxThB0bD8B5OnLBQWdZ" // ID của truyện cần truy cập
////    val truyenRef = FirebaseDatabase.getInstance().getReference("ListTruyen").child(idTruyen)
//
//    val truyenListener = object : ValueEventListener {
//        override fun onDataChange(dataSnapshot: DataSnapshot) {
//            if (dataSnapshot.exists()) {
//                // Lấy thông tin của truyện từ dataSnapshot
//                val tenTruyen = dataSnapshot.child("tenTruyen").getValue(String::class.java)
//                // Hiển thị thông tin của truyện
//                Log.d("Truyen", "Tên truyện: $tenTruyen")
//
//                // Lặp qua các chương của truyện
//                for (chapterSnapshot in dataSnapshot.child("Chapter").children) {
//                    // Lấy nội dung của chương
//                    val content = chapterSnapshot.child("content").getValue(String::class.java)
//                    val ChapterId = chapterSnapshot.child("ChapterId").getValue(String::class.java)
//                    // Hiển thị nội dung của chương
//                    Log.d("Truyen", "Nội dung chương: $content")
//                    Log.d("Truyen", "ID truyện: $ChapterId")
//
//                }
//            } else {
//                Log.d("Truyen", "Không tìm thấy truyện có ID: $idTruyen")
//            }
//        }
//
//        override fun onCancelled(databaseError: DatabaseError) {
//            // Xử lý khi có lỗi xảy ra
//            Log.d("Truyen", "Lỗi: ${databaseError.message}")
//        }
//    }
//
//// Thêm trình lắng nghe vào đường dẫn của truyện trong cơ sở dữ liệu
//    truyenRef.addValueEventListener(truyenListener)





                   }
}


sealed class DestinationScreen(val route: String){
    object Main: DestinationScreen("main")
    object Signup: DestinationScreen("signup")
    object Login: DestinationScreen("login")
    object Success: DestinationScreen("success")


}
@Composable
fun AuthenticationApp(){
    val vm = hiltViewModel<FbViewModel>()
    val navController = rememberNavController()

    NotificationMessage(vm)

    NavHost(navController = navController, startDestination = DestinationScreen.Main.route ){
        composable(DestinationScreen.Main.route){
            MainScreen(navController , vm )
        }
        composable(DestinationScreen.Signup.route){
            SignupScreen(navController , vm )
        }
        composable(DestinationScreen.Login.route){
            LoginScreen(navController , vm )
        }
        composable(DestinationScreen.Success.route){
            SuccessScreen   (navController , vm )
        }

    }
}
//data class TruyenTranh(val name: String,val tacgia:String,val phanloai:String,val tacgia:String)
//data class Contact(val email: String,val phone:String)
