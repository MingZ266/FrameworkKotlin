package com.mingz.frameworkkotlin.entiy

/**
 * 城市搜素结果.
 *
 * 各数据项意义如下:
 *
 * [code] 请求返回状态码
 *
 *
 */
data class SearchCity(val code: String, val location: MutableList<CityInfo>) {

    /**
     * 各数据项意义如下:
     *
     * [id] 城市ID &emsp; [name] 城市名
     *
     * [adm2] 地区/城市的上级行政区划名称
     *
     * [adm1] 地区/城市所属一级行政区域
     *
     * [country] 地区/城市所属国家名称
     *
     * [utcOffset] 地区/城市当前与UTC时间偏移的小时数
     */
    data class CityInfo(val id: String, val name: String, val adm2: String,
                        val adm1: String, val country: String, val utcOffset: String)
}
