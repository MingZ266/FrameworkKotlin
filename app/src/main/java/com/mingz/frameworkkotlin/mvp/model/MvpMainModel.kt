package com.mingz.frameworkkotlin.mvp.model

interface MvpMainModel {
    /**
     * 通过搜索关键字请求搜索城市信息，
     * 若有搜索结果，则用搜索结果中的城市信息请求天气数据.
     *
     * @param searchKey 搜索关键字，最少一个汉字或2个字符
     * @param weatherKey 天气数据请求密钥
     */
    fun requestWeather(searchKey: String, weatherId: String, weatherKey: String)
}
