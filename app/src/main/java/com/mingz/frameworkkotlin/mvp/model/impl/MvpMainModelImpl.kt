package com.mingz.frameworkkotlin.mvp.model.impl

import android.text.TextUtils
import com.mingz.frameworkkotlin.entiy.SearchCity
import com.mingz.frameworkkotlin.entiy.WeatherNow
import com.mingz.frameworkkotlin.mvp.model.MvpMainModel
import com.mingz.frameworkkotlin.mvp.presenter.MvpMainPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import rxhttp.wrapper.param.RxHttp
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.text.StringBuilder

class MvpMainModelImpl(private val presenter: MvpMainPresenter/*Presenter <- Model*/) : MvpMainModel {
    //private val myLog = MyLog("MyTAGModel")
    private val toDigits: String by lazy {
        "0123456789abcdef"
    }

    override fun requestWeather(searchKey: String, weatherId: String, weatherKey: String) {
        var timeStr = System.currentTimeMillis().toString()
        var time = timeStr.substring(0, timeStr.length - 3)
        RxHttp.get("https://geoapi.qweather.com/v2/city/lookup?" +
                "location=$searchKey&range=cn&number=1&publicid=$weatherId&t=$time&sign=${
                    getSign(weatherId, weatherKey, time, 
                        "location", searchKey, "range", "cn", "number", "1")}")
                .asClass(SearchCity::class.java)
                .timeout(5, TimeUnit.SECONDS)// 总超时限制5秒
                .observeOn(AndroidSchedulers.mainThread())// 切换到主线程
                .subscribe({
                    if (it.code == "200" && it.location.isNotEmpty()) {
                        timeStr = System.currentTimeMillis().toString()
                        time = timeStr.substring(0, timeStr.length - 3)
                        RxHttp.get("https://devapi.qweather.com/v7/weather/now?" +
                                "location=${it.location[0].id}&publicid=$weatherId&t=$time&sign=${
                                    getSign(weatherId, weatherKey, time,
                                        "location", it.location[0].id)}")
                                .asClass(WeatherNow::class.java)
                                .observeOn(AndroidSchedulers.mainThread())// 切换到主线程
                                .subscribe({ weatherNow ->
                                    if (weatherNow.code == "200") {
                                        presenter.requestSuccess(it.location[0], weatherNow.now)
                                    } else {
                                        presenter.requestFail(weatherNow.code)
                                    }
                                }, { e -> presenter.requestFail(e) })
                    } else {
                        presenter.requestFail(it.code)
                    }
                }, {
                    presenter.requestFail(it)
                })
    }

    /**
     * 和风天气签名生成算法.
     */
    private fun getSign(weatherId: String, weatherKey: String, time: String, vararg keyAndValue: String): String {
        if (keyAndValue.isNotEmpty() && keyAndValue.size % 2 == 0) {
            val map = TreeMap<String, String>()
            for (i in 0 until (keyAndValue.size - 1) step 2) {
                map[keyAndValue[i]] = keyAndValue[i + 1]
            }
            map["publicid"] = weatherId
            map["t"] = time
            val str = StringBuilder()
            val keys = map.keys
            for (key in keys) {
                if (key.isEmpty() || TextUtils.isEmpty(map[key]) || key == "key" || key == "sign") {
                    continue
                }
                str.append(key).append('=').append(map[key]).append('&')
            }
            if (str.isNotEmpty()) {
                str.deleteCharAt(str.length - 1).append(weatherKey)
            }
            val md5 = MessageDigest.getInstance("MD5")
            return toHexString(md5.digest(str.toString().toByteArray(StandardCharsets.UTF_8)))
        }
        return ""
    }

    private fun toHexString(bytes: ByteArray): String {
        val len = bytes.size
        val out = CharArray(len shl 1)
        var var5 = 0
        for (i in 0 until len) {
            out[var5++] = toDigits[(240 and bytes[i].toInt()) ushr 4]
            out[var5++] = toDigits[15 and bytes[i].toInt()]
        }
        return String(out)
    }
}
