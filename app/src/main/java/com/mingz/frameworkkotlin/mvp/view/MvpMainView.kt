package com.mingz.frameworkkotlin.mvp.view

import com.mingz.frameworkkotlin.entiy.SearchCity
import com.mingz.frameworkkotlin.entiy.WeatherNow

interface MvpMainView {
    fun showToast(msg: String)

    fun showWaitDialog()

    fun hideWaitDialog()

    fun showResult(cityInfo: SearchCity.CityInfo, now: WeatherNow.Now)
}
