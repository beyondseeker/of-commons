package com.objectfanatics.commons.android.os

import androidx.test.annotation.UiThreadTest
import com.google.common.truth.Truth
import org.assertj.core.api.Assertions
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Test

class PackageKtInstrumentedTest {
    /**
     * Running on worker thread.
     */
    @Test
    fun isMainThread_ReturnsFalse() {
        // Junit
        Assert.assertFalse(isMainThread)

        // Google Truth
        Truth.assertThat(isMainThread).isFalse()

        // AssertJ
        Assertions.assertThat(isMainThread).isFalse()

        // Hamcrest
        MatcherAssert.assertThat(isMainThread, `is`(false))
    }

    /**
     * Running on main thread.
     */
    @Test
    @UiThreadTest
    fun isMainThread_ReturnsTrue() {
        // Junit
        Assert.assertTrue(isMainThread)

        // Google Truth
        Truth.assertThat(isMainThread).isTrue()

        // AssertJ
        Assertions.assertThat(isMainThread).isTrue()

        // Hamcrest
        MatcherAssert.assertThat(isMainThread, `is`(true))
    }
}