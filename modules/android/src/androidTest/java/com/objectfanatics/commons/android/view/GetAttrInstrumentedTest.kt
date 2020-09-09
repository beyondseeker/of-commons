package com.objectfanatics.commons.android.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.objectfanatics.commons.android.test.R
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import com.objectfanatics.commons.android.test.R.styleable.GetAttrInstrumentedTestView as styleable
import com.objectfanatics.commons.android.test.R.styleable.GetAttrInstrumentedTestView_nonDefBooleanValue as nonDefBooleanValueIndex
import com.objectfanatics.commons.android.test.R.styleable.GetAttrInstrumentedTestView_nonDefFloatValue as nonDefFloatValueIndex
import com.objectfanatics.commons.android.test.R.styleable.GetAttrInstrumentedTestView_nonDefIntegerArrayValue as nonDefIntegerArrayValueIndex
import com.objectfanatics.commons.android.test.R.styleable.GetAttrInstrumentedTestView_nonDefIntegerValue as nonDefIntegerValueIndex
import com.objectfanatics.commons.android.test.R.styleable.GetAttrInstrumentedTestView_nonDefLongValue as nonDefLongValueIndex
import com.objectfanatics.commons.android.test.R.styleable.GetAttrInstrumentedTestView_nonDefStringArrayValue as nonDefStringArrayValueIndex
import com.objectfanatics.commons.android.test.R.styleable.GetAttrInstrumentedTestView_nonDefStringValue as nonDefStringValueIndex
import com.objectfanatics.commons.android.test.R.styleable.GetAttrInstrumentedTestView_nonNullBooleanValue as nonNullBooleanValueIndex
import com.objectfanatics.commons.android.test.R.styleable.GetAttrInstrumentedTestView_nonNullFloatValue as nonNullFloatValueIndex
import com.objectfanatics.commons.android.test.R.styleable.GetAttrInstrumentedTestView_nonNullIntegerArrayValue as nonNullIntegerArrayValueIndex
import com.objectfanatics.commons.android.test.R.styleable.GetAttrInstrumentedTestView_nonNullIntegerValue as nonNullIntegerValueIndex
import com.objectfanatics.commons.android.test.R.styleable.GetAttrInstrumentedTestView_nonNullLongValue as nonNullLongValueIndex
import com.objectfanatics.commons.android.test.R.styleable.GetAttrInstrumentedTestView_nonNullStringArrayValue as nonNullStringArrayValueIndex
import com.objectfanatics.commons.android.test.R.styleable.GetAttrInstrumentedTestView_nonNullStringValue as nonNullStringValueIndex
import com.objectfanatics.commons.android.test.R.styleable.GetAttrInstrumentedTestView_nullValue as nullValueIndex

class GetAttrInstrumentedTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<GetAttrInstrumentedTestActivity> =
        ActivityScenarioRule(GetAttrInstrumentedTestActivity::class.java)

    @Test
    fun getXxxAttrTest() {
        launchActivity<GetAttrInstrumentedTestActivity>()
    }
}

class GetAttrInstrumentedTestActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.android_view_getattrinstrumentedtestactivity)
    }
}

class GetAttrInstrumentedTestView : View {
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        getAttrs(attrs!!)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        getAttrs(attrs!!)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        getAttrs(attrs!!)
    }

    /**
     * 各型に対して、以下のパターンのテストを実行します。
     * (1) getXxxAttr(): デフォルト値が指定されているが、値がセットされているため、セットされた値が返る。
     * (2) getXxxAttr(): デフォルト値が指定されており、値に @null がセットされているため、デフォルト値が返る。
     * (3) getXxxAttr(): デフォルト値が指定されており、値がセットされていないため、デフォルト値が返る。
     * (4) getNullableXxxAttr(): デフォルト値が指定されておらず、値がセットされているため、セットされた値が返る。
     * (5) getNullableXxxAttr(): デフォルト値が指定されておらず、値に @null がセットされているため、null が返る。
     * (6) getNullableXxxAttr(): デフォルト値が指定されておらず、値がセットされていないため、null が返る。
     * (7) getXxxAttr(): デフォルト値が指定されていないが、値がセットされているため、セットされた値が返る。
     * (8) getXxxAttr(): デフォルト値が指定されておらず、値に @null がセットされているため、AttributeValueNotFoundException がスローされる。
     * (9) getXxxAttr(): デフォルト値が指定されておらず、値がセットされていないため、AttributeValueNotFoundException がスローされる。
     */
    private fun getAttrs(attrs: AttributeSet) {
        // @formatter:off

        // boolean
        assertObject(
            attrs,
            DEF_BOOLEAN_VALUE, SET_BOOLEAN_VALUE,
            this::getBooleanAttr, this::getNullableBooleanAttr, this::getBooleanAttr,
            nonNullBooleanValueIndex, nonDefBooleanValueIndex
        )

        // integer
        assertObject(
            attrs,
            DEF_INTEGER_VALUE, SET_INTEGER_VALUE,
            this::getIntegerAttr, this::getNullableIntegerAttr, this::getIntegerAttr,
            nonNullIntegerValueIndex, nonDefIntegerValueIndex
        )

        // float
        assertObject(
            attrs,
            DEF_FLOAT_VALUE, SET_FLOAT_VALUE,
            this::getFloatAttr, this::getNullableFloatAttr, this::getFloatAttr,
            nonNullFloatValueIndex, nonDefFloatValueIndex
        )

        // string
        assertObject(
            attrs,
            DEF_STRING_VALUE, SET_STRING_VALUE,
            this::getStringAttr, this::getNullableStringAttr, this::getStringAttr,
            nonNullStringValueIndex, nonDefStringValueIndex
        )

        // long
        assertObject(
            attrs,
            DEF_LONG_VALUE, SET_LONG_VALUE,
            this::getLongAttr, this::getNullableLongAttr, this::getLongAttr,
            nonNullLongValueIndex, nonDefLongValueIndex
        )

        // integer array
        assertIntArray(
            attrs,
            DEF_INTEGER_ARRAY_VALUE, SET_INTEGER_ARRAY_VALUE,
            this::getIntegerArrayAttr, this::getNullableIntegerArrayAttr, this::getIntegerArrayAttr,
            nonNullIntegerArrayValueIndex, nonDefIntegerArrayValueIndex
        )

        // string array
        assertArray(
            attrs,
            DEF_STRING_ARRAY_VALUE, SET_STRING_ARRAY_VALUE,
            this::getStringArrayAttr, this::getNullableStringArrayAttr, this::getStringArrayAttr,
            nonNullStringArrayValueIndex, nonDefStringArrayValueIndex
        )

        // @formatter:on
    }

    private fun <T> assertObject(
        attrs: AttributeSet,
        defValue: T,
        setValue: T,
        getAttrWithDef: (attrs: AttributeSet, styleable: IntArray, index: Int, defValue: T) -> T,
        getNullableAttr: (attrs: AttributeSet, styleable: IntArray, index: Int) -> T?,
        getAttrWithoutDef: (attrs: AttributeSet, styleable: IntArray, index: Int) -> T,
        nonNullValueIndex: Int,
        nonDefValueIndex: Int
    ) {
        assertEquals(setValue, getAttrWithDef(attrs, styleable, nonNullValueIndex, defValue))
        assertEquals(defValue, getAttrWithDef(attrs, styleable, nullValueIndex, defValue))
        assertEquals(defValue, getAttrWithDef(attrs, styleable, nonDefValueIndex, defValue))

        assertEquals(setValue, getNullableAttr(attrs, styleable, nonNullValueIndex))
        assertEquals(null, getNullableAttr(attrs, styleable, nullValueIndex))
        assertEquals(null, getNullableAttr(attrs, styleable, nonDefValueIndex))

        assertEquals(setValue, getAttrWithoutDef(attrs, styleable, nonNullValueIndex))
        assertAttributeValueNotFound { getAttrWithoutDef(attrs, styleable, nullValueIndex) }
        assertAttributeValueNotFound { getAttrWithoutDef(attrs, styleable, nonDefValueIndex) }
    }

    @Suppress("SameParameterValue")
    private fun assertIntArray(
        attrs: AttributeSet,
        defValue: IntArray,
        setValue: IntArray,
        getAttrWithDef: (attrs: AttributeSet, styleable: IntArray, index: Int, defValue: IntArray) -> IntArray,
        getNullableAttr: (attrs: AttributeSet, styleable: IntArray, index: Int) -> IntArray?,
        getAttrWithoutDef: (attrs: AttributeSet, styleable: IntArray, index: Int) -> IntArray,
        nonNullValueIndex: Int,
        nonDefValueIndex: Int
    ) {
        assertArrayEquals(setValue, getAttrWithDef(attrs, styleable, nonNullValueIndex, defValue))
        assertArrayEquals(defValue, getAttrWithDef(attrs, styleable, nullValueIndex, defValue))
        assertArrayEquals(defValue, getAttrWithDef(attrs, styleable, nonDefValueIndex, defValue))

        assertArrayEquals(setValue, getNullableAttr(attrs, styleable, nonNullValueIndex))
        assertEquals(null, getNullableAttr(attrs, styleable, nullValueIndex))
        assertEquals(null, getNullableAttr(attrs, styleable, nonDefValueIndex))

        assertArrayEquals(setValue, getAttrWithoutDef(attrs, styleable, nonNullValueIndex))
        assertAttributeValueNotFound { getAttrWithoutDef(attrs, styleable, nullValueIndex) }
        assertAttributeValueNotFound { getAttrWithoutDef(attrs, styleable, nonDefValueIndex) }
    }

    @Suppress("SameParameterValue")
    private fun <T> assertArray(
        attrs: AttributeSet,
        defValue: Array<T>,
        setValue: Array<T>,
        getAttrWithDef: (attrs: AttributeSet, styleable: IntArray, index: Int, defValue: Array<T>) -> Array<T>,
        getNullableAttr: (attrs: AttributeSet, styleable: IntArray, index: Int) -> Array<T>?,
        getAttrWithoutDef: (attrs: AttributeSet, styleable: IntArray, index: Int) -> Array<T>,
        nonNullValueIndex: Int,
        nonDefValueIndex: Int
    ) {
        assertArrayEquals(setValue, getAttrWithDef(attrs, styleable, nonNullValueIndex, defValue))
        assertArrayEquals(defValue, getAttrWithDef(attrs, styleable, nullValueIndex, defValue))
        assertArrayEquals(defValue, getAttrWithDef(attrs, styleable, nonDefValueIndex, defValue))

        assertArrayEquals(setValue, getNullableAttr(attrs, styleable, nonNullValueIndex))
        assertArrayEquals(null, getNullableAttr(attrs, styleable, nullValueIndex))
        assertArrayEquals(null, getNullableAttr(attrs, styleable, nonDefValueIndex))

        assertArrayEquals(setValue, getAttrWithoutDef(attrs, styleable, nonNullValueIndex))
        assertAttributeValueNotFound { getAttrWithoutDef(attrs, styleable, nullValueIndex) }
        assertAttributeValueNotFound { getAttrWithoutDef(attrs, styleable, nonDefValueIndex) }
    }

    private fun <T> assertAttributeValueNotFound(function: () -> T) {
        try {
            function()
            fail("${AttributeValueNotFoundException::class.simpleName} must be thrown")
        } catch (e: AttributeValueNotFoundException) {
            // success
        }
    }

    companion object {
        private const val DEF_BOOLEAN_VALUE: Boolean = false
        private const val SET_BOOLEAN_VALUE: Boolean = true

        private const val DEF_INTEGER_VALUE: Int = 10
        private const val SET_INTEGER_VALUE: Int = 20

        private const val DEF_FLOAT_VALUE: Float = 10f
        private const val SET_FLOAT_VALUE: Float = 20f

        private const val DEF_STRING_VALUE: String = "い"
        private const val SET_STRING_VALUE: String = "あ"

        private const val DEF_LONG_VALUE: Long = Long.MAX_VALUE / 2 + 1
        private const val SET_LONG_VALUE: Long = Long.MAX_VALUE

        private val DEF_STRING_ARRAY_VALUE: Array<String> = arrayOf("d", "e", "f")
        private val SET_STRING_ARRAY_VALUE: Array<String> = arrayOf("a", "b", "c")

        private val DEF_INTEGER_ARRAY_VALUE: IntArray = intArrayOf(4, 5, 6)
        private val SET_INTEGER_ARRAY_VALUE: IntArray = intArrayOf(1, 2, 3)
    }
}