package com.example.youtubetopbar.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScrollViewModel @Inject constructor(): ViewModel() {

    private var lastScrollIndex: Int = 0

    private val _scrollUp = MutableLiveData(false)
    val scrollUp: LiveData<Boolean> = _scrollUp

    fun updateScrollPosition(newScrollIndex: Int) {

        Log.d("scrollState1","$lastScrollIndex")

        //if (newScrollIndex == lastScrollIndex) return

        // 上にスクロースしたら、マイナスのピクセル
        // 下にスクロールしたら、プラスのピクセル
        _scrollUp.value =  newScrollIndex > lastScrollIndex

        lastScrollIndex = newScrollIndex
        Log.d("scrollState2","$lastScrollIndex")
    }
}