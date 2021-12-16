package github.nocamles.httphelper.utils

import android.content.Context
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * @author zs
 * @date 2020-03-18
 */
class KeyBoardUtil private constructor() {
    companion object {
        /**
         * 打卡软键盘
         */
        fun openKeyboard(mEditText: EditText?, mContext: Context) {
            val imm =
                mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
            imm.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        }

        /**
         * 关闭软键盘
         */
        fun closeKeyboard(ibinder: IBinder, mContext: Context) {
            val imm =
                mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(ibinder, 0)
        }
    }

    init {
        throw UnsupportedOperationException("KeyBoardUtil cannot be instantiated")
    }
}