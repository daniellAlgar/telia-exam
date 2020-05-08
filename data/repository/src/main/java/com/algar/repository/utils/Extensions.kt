package com.algar.repository.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <T, S> MediatorLiveData<T>.addSourceOnce(source: LiveData<S>, observer: (S?) -> Unit) {
    addSource(source) { data ->
        removeSource(source)
        observer(data)
    }
}