package com.objectfanatics.commons.androidx.lifecycle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class TransformationsUtilsTest {
    interface Recorder<T> {
        fun record(value: T)
    }

    /**
     * 3つの[MutableLiveData]を[TransformationsUtils]で監視するテスト。
     */
    @Test
    fun map_forQiita1() {
        // テストの実行経過を記録する Recorder の用意
        val recorder = mockk<Recorder<String>>(relaxed = true)

        // 3 つの MutableLiveData を用意
        val input1: MutableLiveData<String?> = MutableLiveData()
        val input2: MutableLiveData<String?> = MutableLiveData()
        val input3: MutableLiveData<String?> = MutableLiveData()

        // 上記 3 つの MutableLiveData を受けて文字列を返す LiveData を用意
        val output: LiveData<String> = TransformationsUtils.map(input1, input2, input3) {
            "${input1.value}, ${input2.value}, ${input3.value}"
        }

        // 実行結果が標準出力に表示されるように準備
        output.observeForever { value ->
            recorder.record(value)
        }

        // テストの実行
        input1.postValue("red")
        input2.postValue("blue")
        input3.postValue("green")

        // 実行内容の検証
        verify {
            recorder.record("red, null, null")
            recorder.record("red, blue, null")
            recorder.record("red, blue, green")
        }

        // 実行内容の検証がすべて完了したことの確認
        confirmVerified(recorder)
    }
}