package com.mingz.frameworkkotlin.mvp.presenter

import com.mingz.frameworkkotlin.entiy.SearchCity
import com.mingz.frameworkkotlin.entiy.WeatherNow

interface MvpMainPresenter {
    fun requestWeather(searchKey: String?, weatherId: String, weatherKey: String)

    fun requestSuccess(cityInfo: SearchCity.CityInfo, now: WeatherNow.Now)

    fun requestFail(code: String)

    fun requestFail(e: Throwable? = null)
}
