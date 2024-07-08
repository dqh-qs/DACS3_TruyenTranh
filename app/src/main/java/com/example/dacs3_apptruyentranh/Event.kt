package com.example.dacs3_apptruyentranh

import android.app.assist.AssistContent

open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set
    fun getContentOrNull(): T?{
        return if(hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }
}