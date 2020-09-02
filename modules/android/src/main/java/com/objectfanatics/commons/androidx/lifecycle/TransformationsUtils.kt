package com.objectfanatics.commons.androidx.lifecycle

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

object TransformationsUtils {
    /**
     * @see androidx.lifecycle.Transformations.map
     */
    @MainThread
    fun <T> map(vararg sources: LiveData<out Any?>, function: () -> T): LiveData<T> =
        MediatorLiveData<T>().apply {
            sources.forEach { source ->
                addSource(source) {
                    value = function()
                }
            }
        }
}