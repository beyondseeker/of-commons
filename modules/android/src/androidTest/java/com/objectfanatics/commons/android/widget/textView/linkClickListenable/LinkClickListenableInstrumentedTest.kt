package com.objectfanatics.commons.android.widget.textView.linkClickListenable

import android.app.Activity
import android.os.Bundle
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.objectfanatics.commons.android.test.R
import org.junit.Rule
import org.junit.Test

class LinkClickListenableInstrumentedTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<LinkClickListenableInstrumentedTestActivity1> =
        ActivityScenarioRule(LinkClickListenableInstrumentedTestActivity1::class.java)

    @Test
    fun getXxxAttrTest() {
        launchActivity<LinkClickListenableInstrumentedTestActivity1>()
    }
}

class LinkClickListenableInstrumentedTestActivity1 : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.android_widget_textview_linkclicklistenable_linkclicklistenableinstrumentedtestactivity1)
    }
}