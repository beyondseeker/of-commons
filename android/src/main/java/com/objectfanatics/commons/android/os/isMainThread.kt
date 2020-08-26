package com.objectfanatics.commons.android.os

import android.os.Looper

val isMainThread: Boolean
    get() = Thread.currentThread() == Looper.getMainLooper().thread