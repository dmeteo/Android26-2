package ru.urfu.droidpractice1.content

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var likesCount by mutableStateOf(0)
        private set
    var dislikesCount by mutableStateOf(0)
        private set
    var isRead by mutableStateOf(false)
        private set

    fun like() {
        likesCount++
    }

    fun dislike() {
        dislikesCount++
    }

    fun markRead() {
        isRead = true
    }

    fun resetRead() {
        isRead = false
    }
}