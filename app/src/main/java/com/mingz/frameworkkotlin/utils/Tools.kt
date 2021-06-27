package com.mingz.frameworkkotlin.utils

import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * 设置状态栏黑色字体.
 */
fun setBlackWordOnStatus(activity: AppCompatActivity) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}
