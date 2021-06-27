package com.mingz.frameworkkotlin.utils

import android.util.Log

class MyLog(private var TAG: String) {
    private val DEBUG = true

    fun v(msg: String, tag: String = TAG) {
        if (DEBUG) {
            Log.v(tag, msg)
        }
    }

    fun v(errorInfo: String, e: Throwable?, tag: String = TAG) {
        if (DEBUG) {
            if (e != null) {
                Log.v(tag, "$errorInfo(${e::class.simpleName}): ${e.message}")
            } else {
                v(errorInfo)
            }
        }
    }

    fun d(msg: String, tag: String = TAG) {
        if (DEBUG) {
            Log.d(tag, msg)
        }
    }

    fun d(errorInfo: String, e: Throwable?, tag: String = TAG) {
        if (DEBUG) {
            if (e != null) {
                Log.d(tag, "$errorInfo(${e::class.simpleName}): ${e.message}")
            } else {
                d(errorInfo)
            }
        }
    }

    fun i(msg: String, tag: String = TAG) = Log.i(tag, msg)

    fun i(errorInfo: String, e: Throwable?, tag: String = TAG) =
        if (e != null) {
            Log.i(tag, "$errorInfo(${e::class.simpleName}): ${e.message}")
        } else {
            i(errorInfo)
        }

    fun w(msg: String, tag: String = TAG) = Log.w(tag, msg)

    fun w(errorInfo: String, e: Throwable?, tag: String = TAG) =
        if (e != null) {
            Log.w(tag, "$errorInfo(${e::class.simpleName}): ${e.message}")
        } else {
            w(errorInfo)
        }

    fun e(msg: String, tag: String = TAG) = Log.e(tag, msg)

    fun e(errorInfo: String, e: Throwable?, tag: String = TAG) =
        if (e != null) {
            Log.e(tag, "$errorInfo(${e::class.simpleName}): ${e.message}")
        } else {
            e(errorInfo)
        }
}
