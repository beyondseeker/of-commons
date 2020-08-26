package com.objectfanatics.commons.android.view

import android.app.Activity
import android.util.Log
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class GetAttrInstrumentedTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<GetAttrInstrumentedTestActivity> =
        ActivityScenarioRule(GetAttrInstrumentedTestActivity::class.java)

    @Test
    fun getXxxAttrTest() {
        // Activity を起動
        val scenario = launchActivity<GetAttrInstrumentedTestActivity>()

        // 起動した Activity の simpleName をログ出力してみる
        scenario.onActivity { activity ->
            Log.e(
                "GetAttrInstrumentedTestActivity",
                "activity.javaClass.simpleName = ${activity.javaClass.simpleName}"
            )
        }
    }
}

class GetAttrInstrumentedTestActivity : Activity()