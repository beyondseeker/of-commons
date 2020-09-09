package com.objectfanatics.commons.android.view

import android.content.res.Resources
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View

// FIXME: com.objectfanatics.commons.android.view.getAttr package に移動すべき。直下だと、ファイルを分けようと思った場合などに構造の変更が必要になってしまうので保守時の影響範囲が大きくなってしまう。
// FIXME:Drawable, color, dimension 等も対応すべき。

// public exception
class AttributeValueNotFoundException : IllegalStateException("This attr must be set. Please set attr in layout file or set default value")

// @formatter:off

// boolean
fun View.getBooleanAttr                     (attrs: AttributeSet, styleable: IntArray, index: Int, defValue: Boolean                ): Boolean        = getNullableBooleanAttrInternal     (attrs, styleable, index, defValue)!!
fun View.getBooleanAttr                     (attrs: AttributeSet, styleable: IntArray, index: Int                                   ): Boolean        = getNullableBooleanAttrInternal     (attrs, styleable, index, null    ) ?: throw AttributeValueNotFoundException()
fun View.getNullableBooleanAttr             (attrs: AttributeSet, styleable: IntArray, index: Int                                   ): Boolean?       = getNullableBooleanAttrInternal     (attrs, styleable, index, null    )

// integer
fun View.getIntegerAttr                     (attrs: AttributeSet, styleable: IntArray, index: Int, defValue: Int                    ): Int            = getNullableIntegerAttrInternal     (attrs, styleable, index, defValue)!!
fun View.getIntegerAttr                     (attrs: AttributeSet, styleable: IntArray, index: Int                                   ): Int            = getNullableIntegerAttrInternal     (attrs, styleable, index, null    ) ?: throw AttributeValueNotFoundException()
fun View.getNullableIntegerAttr             (attrs: AttributeSet, styleable: IntArray, index: Int                                   ): Int?           = getNullableIntegerAttrInternal     (attrs, styleable, index, null    )

// float
fun View.getFloatAttr                       (attrs: AttributeSet, styleable: IntArray, index: Int, defValue: Float                  ): Float          = getNullableFloatAttrInternal       (attrs, styleable, index, defValue)!!
fun View.getFloatAttr                       (attrs: AttributeSet, styleable: IntArray, index: Int                                   ): Float          = getNullableFloatAttrInternal       (attrs, styleable, index, null    ) ?: throw AttributeValueNotFoundException()
fun View.getNullableFloatAttr               (attrs: AttributeSet, styleable: IntArray, index: Int                                   ): Float?         = getNullableFloatAttrInternal       (attrs, styleable, index, null    )

// string
fun View.getStringAttr                      (attrs: AttributeSet, styleable: IntArray, index: Int, defValue: String                 ): String         = getNullableStringAttrInternal      (attrs, styleable, index, defValue)!!
fun View.getStringAttr                      (attrs: AttributeSet, styleable: IntArray, index: Int                                   ): String         = getNullableStringAttrInternal      (attrs, styleable, index, null    ) ?: throw AttributeValueNotFoundException()
fun View.getNullableStringAttr              (attrs: AttributeSet, styleable: IntArray, index: Int                                   ): String?        = getNullableStringAttrInternal      (attrs, styleable, index, null    )

// integer array
fun View.getIntegerArrayAttr                (attrs: AttributeSet, styleable: IntArray, index: Int, defValue: IntArray               ): IntArray       = getNullableIntegerArrayAttrInternal(attrs, styleable, index, defValue)!!
fun View.getIntegerArrayAttr                (attrs: AttributeSet, styleable: IntArray, index: Int                                   ): IntArray       = getNullableIntegerArrayAttrInternal(attrs, styleable, index, null    ) ?: throw AttributeValueNotFoundException()
fun View.getNullableIntegerArrayAttr        (attrs: AttributeSet, styleable: IntArray, index: Int                                   ): IntArray?      = getNullableIntegerArrayAttrInternal(attrs, styleable, index, null    )

// string array
fun View.getStringArrayAttr                 (attrs: AttributeSet, styleable: IntArray, index: Int, defValue: Array<String>          ): Array<String>  = getNullableStringArrayAttrInternal (attrs, styleable, index, defValue)!!
fun View.getStringArrayAttr                 (attrs: AttributeSet, styleable: IntArray, index: Int                                   ): Array<String>  = getNullableStringArrayAttrInternal (attrs, styleable, index, null    ) ?: throw AttributeValueNotFoundException()
fun View.getNullableStringArrayAttr         (attrs: AttributeSet, styleable: IntArray, index: Int                                   ): Array<String>? = getNullableStringArrayAttrInternal (attrs, styleable, index, null    )

// long by string
fun View.getLongAttr                        (attrs: AttributeSet, styleable: IntArray, index: Int, defValue: Long                   ): Long            = getStringAttr                     (attrs, styleable, index, defValue.toString()).toLong()
fun View.getLongAttr                        (attrs: AttributeSet, styleable: IntArray, index: Int                                   ): Long            = getStringAttr                     (attrs, styleable, index                     ).toLong()
fun View.getNullableLongAttr                (attrs: AttributeSet, styleable: IntArray, index: Int                                   ): Long?           = getNullableStringAttr             (attrs, styleable, index                     )?.toLong()

// internal non-array
private fun View.getNullableBooleanAttrInternal     (attrs: AttributeSet, styleable: IntArray, index: Int, defValue: Boolean?       ): Boolean?       = getNullableAttrInternal            (attrs, styleable, index, defValue) { typedArray: TypedArray -> typedArray.getBoolean   (index, DUMMY_BOOLEAN) }
private fun View.getNullableIntegerAttrInternal     (attrs: AttributeSet, styleable: IntArray, index: Int, defValue: Int?           ): Int?           = getNullableAttrInternal            (attrs, styleable, index, defValue) { typedArray: TypedArray -> typedArray.getInteger   (index, DUMMY_INT    ) }
private fun View.getNullableFloatAttrInternal       (attrs: AttributeSet, styleable: IntArray, index: Int, defValue: Float?         ): Float?         = getNullableAttrInternal            (attrs, styleable, index, defValue) { typedArray: TypedArray -> typedArray.getFloat     (index, DUMMY_FLOAT  ) }
private fun View.getNullableStringAttrInternal      (attrs: AttributeSet, styleable: IntArray, index: Int, defValue: String?        ): String?        = getNullableAttrInternal            (attrs, styleable, index, defValue) { typedArray: TypedArray -> typedArray.getString    (index               ) }
private fun View.getNullableReferenceAttrInternal   (attrs: AttributeSet, styleable: IntArray, index: Int, defValue: Int?           ): Int?           = getNullableAttrInternal            (attrs, styleable, index, defValue) { typedArray: TypedArray -> typedArray.getResourceId(index, DUMMY_INT    ) }

// internal array
private fun View.getNullableIntegerArrayAttrInternal(attrs: AttributeSet, styleable: IntArray, index: Int, defValue: IntArray?      ): IntArray?      = getNullableArrayAttrInternal       (attrs, styleable, index, defValue) { resources, resId -> resources.getIntArray(resId) }
private fun View.getNullableStringArrayAttrInternal (attrs: AttributeSet, styleable: IntArray, index: Int, defValue: Array<String>? ): Array<String>? = getNullableArrayAttrInternal       (attrs, styleable, index, defValue) { resources, resId -> resources.getStringArray(resId) }

// @formatter:on

private fun <T> View.getNullableAttrInternal(attrs: AttributeSet, styleable: IntArray, index: Int, defValue: T?, getValue: (TypedArray) -> T?): T? =
    context.theme.obtainStyledAttributes(attrs, styleable, 0, 0).run {
        try {
            when {
                !hasValue(index) -> defValue
                else             -> getValue(this)
            }
        } finally {
            recycle()
        }
    }

private fun <T> View.getNullableArrayAttrInternal(attrs: AttributeSet, styleable: IntArray, index: Int, defValue: T?, getValue: (Resources, Int) -> T?): T? =
    when (val resId: Int? = getNullableReferenceAttrInternal(attrs, styleable, index, null)) {
        null -> defValue
        else -> getValue(resources, resId)
    }

private const val DUMMY_BOOLEAN: Boolean = false
private const val DUMMY_INT: Int = 0
private const val DUMMY_FLOAT: Float = 0f