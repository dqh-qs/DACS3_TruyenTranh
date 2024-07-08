package com.example.dacs3_apptruyentranh


import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject




@HiltViewModel
class FbViewModel @Inject constructor(
    val auth: FirebaseAuth
):ViewModel(){

    val signedIn = mutableStateOf(false)
    val inProgress = mutableStateOf(false)
    val popupNotification = mutableStateOf<Event<String>?>(null)

    fun onSignup(email: String, pass: String){
        inProgress.value = true

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener() {
                if(it.isSuccessful){
                    signedIn.value = true
                    handleException(it.exception, "Signup successful")
                }else{
                    handleException(it.exception, "Signup failed")
                }
                inProgress.value = true
            }


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.let {
                        val userId = it.uid // Lấy ID của người dùng
                        val usersRef = FirebaseDatabase.getInstance().getReference("Users")
                        val userEmail = it.email
                        val truyen = HashMap<String, Any>()
                        truyen["UserId"] = "$userId"
                        truyen["Pass"] = "$pass"
                        truyen["Email"] = "$userEmail"
                        truyen["Name"] = "$userEmail"
                        truyen["Image"] = "https://cdn-images-3.listennotes.com/podcasts/the-emerald-radio/t%E1%BA%ADp-2-solo-leveling-t%C3%B4i-D1P2x-pcJmv-qviDxVF5g_-.1400x1400.jpg"
                        truyen["Login"] = "Login google"
                        truyen["SpiritStone"] = "0"
                        truyen["Level"] = "0"
                        truyen["Delete"] = "delete"
                        truyen["Level_login"] = "3"
                        usersRef.child(userId).setValue(truyen)
                    }
                } else {
                    Log.d("YourTag", "lỗi realtime")
                }
            }



    }
    fun login(email: String, pass: String){
        inProgress.value = true
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(){
                if(it.isSuccessful){
                    signedIn.value = true
                    handleException(it.exception, "Login successful")
                }else{
                    handleException(it.exception, "Login failed")
                }
                inProgress.value = false
            }
    }
    fun handleException(exception: Exception? = null, customMessage: String = ""){
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?:""
        val message = if(customMessage.isNotEmpty()) errorMsg else "$customMessage: $errorMsg"
        popupNotification.value = Event(message)
    }

}