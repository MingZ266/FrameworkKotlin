package com.mingz.frameworkkotlin.entiy

/**
 * 实况天气数据.
 *
 * 各数据项意义如下:
 *
 * [code] 请求返回状态码
 *
 * [now] 天气数据
 */
data class WeatherNow(val code: String, val now: Now) {

    /**
     * 各数据项意义如下:
     *
     * [temp] 温度 &emsp; [feelsLike] 体感温度 &emsp; [text] 天气状况
     *
     * [windDir] 风向 &emsp; [windScale] 风力等级 &emsp; [windSpeed] 风速
     *
     * [humidity] 相对湿度 &emsp; [precip] 降水量 &emsp; [pressure] 大气压强
     *
     * [vis] 能见度
     */
    data class Now(val temp: String, val feelsLike: String, val text: String,
                   val windDir: String, val windScale: String, val windSpeed: String,
                   val humidity: String, val precip: String, val pressure: String,
                   val vis: String)
}
