package com.mingz.frameworkkotlin.mvp.presenter.impl

import android.text.TextUtils
import com.mingz.frameworkkotlin.entiy.SearchCity
import com.mingz.frameworkkotlin.entiy.WeatherNow
import com.mingz.frameworkkotlin.mvp.model.MvpMainModel
import com.mingz.frameworkkotlin.mvp.model.impl.MvpMainModelImpl
import com.mingz.frameworkkotlin.mvp.presenter.MvpMainPresenter
import com.mingz.frameworkkotlin.mvp.view.MvpMainView
import com.mingz.frameworkkotlin.utils.MyLog

class MvpMainPresenterImpl(private val view: MvpMainView/*View <- Presenter*/) : MvpMainPresenter {
    private val myLog = MyLog("MyTAGPresenter")
    private val model: MvpMainModel

    init {
        model = MvpMainModelImpl(this)// Presenter -> Model
    }

    override fun requestWeather(searchKey: String?, weatherId: String, weatherKey: String) {
        if (TextUtils.isEmpty(searchKey)) {
            view.showToast("请输入城市名")
        } else {
            searchKey!!
            if (searchKey.length == 1 && searchKey[0] !in '\u4E00'..'\u9FCB') {
                view.showToast("请输入至少一个中文或两个英文字符")
            } else {
                view.showWaitDialog()
                model.requestWeather(searchKey, weatherId, weatherKey)
            }
        }
    }

    override fun requestSuccess(cityInfo: SearchCity.CityInfo, now: WeatherNow.Now) {
        view.hideWaitDialog()
        view.showResult(cityInfo, now)
    }

    override fun requestFail(code: String) {
        view.hideWaitDialog()
        myLog.v("请求失败，错误码：$code")
        if (code == "404") {
            view.showToast("无搜索结果")
        } else {
            view.showToast("请求失败，错误码：$code")
        }
    }

    override fun requestFail(e: Throwable?) {
        view.hideWaitDialog()
        myLog.v("请求失败", e)
        view.showToast("获取天气数据失败")
    }
}
