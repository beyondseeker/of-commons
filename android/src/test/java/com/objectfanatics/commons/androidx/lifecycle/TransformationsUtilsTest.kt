package com.objectfanatics.commons.androidx.lifecycle

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class TransformationsUtilsTest {
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

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

    /**
     * フォーム(名前、苗字)の両方が空でない時にのみ送信ボタンが押せるという仕様を想定した内容のテスト。
     * ※ Unit test としては微妙だが、qiita 記事的にわかりやすいものにした。
     */
    @Test
    fun map_forQiita2() {
        // テストの実行経過を記録する Recorder の用意
        val recorder = mockk<Recorder<Boolean>>(relaxed = true)

        // 名前、苗字の MutableLiveData を用意
        val firstName: MutableLiveData<String?> = MutableLiveData()
        val lastName: MutableLiveData<String?> = MutableLiveData()
        val forms = listOf(firstName, lastName)

        // 送信ボタンが押せるかどうかの MutableLiveData を用意。
        // ※名前、苗字の両方が空でない場合のみ true になる
        val isSubmitButtonEnabled: LiveData<Boolean> =
            TransformationsUtils.map(firstName, lastName) {
                !forms.any { it.value.isNullOrEmpty() }
            }

        // 実行経過が Recorder に記録されるように準備
        isSubmitButtonEnabled.observeForever { value ->
            recorder.record(value)
        }

        // テストの実行
        firstName.postValue("シャミ子")
        lastName.postValue("吉田")

        // 実行内容の検証
        verify {
            // firstName が "シャミ子" になったが lastName が null のため false
            recorder.record(false)
            // firstName が "シャミ子"、lastName が "吉田" になったため true
            recorder.record(true)
        }

        // 実行内容の検証がすべて完了したことの確認
        confirmVerified(recorder)
    }
}