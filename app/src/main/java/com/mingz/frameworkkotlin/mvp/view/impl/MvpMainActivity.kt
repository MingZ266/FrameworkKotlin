package com.mingz.frameworkkotlin.mvp.view.impl

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mingz.frameworkkotlin.R
import com.mingz.frameworkkotlin.entiy.SearchCity
import com.mingz.frameworkkotlin.entiy.WeatherNow
import com.mingz.frameworkkotlin.mvp.presenter.impl.MvpMainPresenterImpl
import com.mingz.frameworkkotlin.mvp.view.MvpMainView
import com.mingz.frameworkkotlin.utils.setBlackWordOnStatus

class MvpMainActivity : AppCompatActivity(), MvpMainView {
    private val activity = this
    private val presenter = MvpMainPresenterImpl(this)// 由View层构造Presenter层（View -> Presenter）
    private val inputMethodManager: InputMethodManager by lazy {
        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    }
    private val weatherId: String by lazy {
        getString(R.string.weatherId)
    }
    private val weatherKey: String by lazy {
        getString(R.string.weatherKey)
    }
    private var waitDialog: AlertDialog? = null

    //private val myLog = MyLog("MyTAGView")

    // 仿SearchView的输入区域
    private lateinit var inputCity: EditText
    private lateinit var clearInput: View
    // 天气数据区域
    private lateinit var resultArea: View
    private lateinit var cityName: TextView
    private lateinit var timezone: TextView
    private lateinit var location: TextView
    private lateinit var nowText: TextView
    private lateinit var nowTemp: TextView
    private lateinit var windDir: TextView
    private lateinit var windScale: TextView
    private lateinit var windSpeed: TextView
    private lateinit var precip: TextView
    private lateinit var vis: TextView
    private lateinit var feelTemp: TextView
    private lateinit var humidity: TextView
    private lateinit var pressure: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvp_main)
        setBlackWordOnStatus(activity)

        initView()
        myListener()
    }

    private fun initView() {
        inputCity = findViewById(R.id.inputCity)
        clearInput = findViewById(R.id.clearInput)
        resultArea = findViewById(R.id.resultArea)
        cityName = findViewById(R.id.cityName)
        timezone = findViewById(R.id.timezone)
        location = findViewById(R.id.location)
        nowText = findViewById(R.id.nowText)
        nowTemp = findViewById(R.id.nowTemp)
        windDir = findViewById(R.id.windDir)
        windScale = findViewById(R.id.windScale)
        windSpeed = findViewById(R.id.windSpeed)
        precip = findViewById(R.id.precip)
        vis = findViewById(R.id.vis)
        feelTemp = findViewById(R.id.feelTemp)
        humidity = findViewById(R.id.humidity)
        pressure = findViewById(R.id.pressure)
    }

    private fun myListener() {
        // 清空输入，显示软键盘
        clearInput.setOnClickListener {
            inputCity.setText("")
            inputMethodManager.showSoftInput(inputCity, InputMethodManager.SHOW_IMPLICIT)
        }
        // 控制清空输入的图标显示与否
        inputCity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    clearInput.visibility = if (s.isEmpty()) View.INVISIBLE else View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        // 回车时收起软键盘，隐藏结果区域，发起请求
        inputCity.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                event?.let {
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                        inputMethodManager.hideSoftInputFromWindow(inputCity.windowToken,
                                InputMethodManager.HIDE_NOT_ALWAYS)
                        resultArea.visibility = View.INVISIBLE
                        presenter.requestWeather(inputCity.text.toString(), weatherId, weatherKey)
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showWaitDialog() {
        waitDialog?:let {
            val waitView = View.inflate(activity, R.layout.dialog_wait, null)
            waitDialog = AlertDialog.Builder(activity, R.style.CircleCornerAlertDialog)
                    .setView(waitView)
                    .create()
            waitDialog!!.setCancelable(false)
            waitDialog!!.setCanceledOnTouchOutside(false)
            waitView.findViewById<View>(R.id.wait).animation =
                    AnimationUtils.loadAnimation(activity, R.anim.rotate_wait)
        }
        waitDialog!!.show()
    }

    override fun hideWaitDialog() {
        waitDialog?.let {
            waitDialog!!.dismiss()
        }
        waitDialog = null
    }

    @SuppressLint("SetTextI18n")
    override fun showResult(cityInfo: SearchCity.CityInfo, now: WeatherNow.Now) {
        resultArea.visibility = View.VISIBLE
        cityName.text = cityInfo.name
        timezone.text = "GMT${cityInfo.utcOffset}"
        location.text = "${cityInfo.country} - ${cityInfo.adm1} - ${cityInfo.adm2}"
        nowText.text = now.text
        nowTemp.text = "${now.temp}\u2103"
        windDir.text = now.windDir
        windScale.text = "${now.windScale}级"
        windSpeed.text = "${now.windSpeed}km/h"
        precip.text = "${now.precip}mm"
        vis.text = "${now.vis}km"
        feelTemp.text = "${now.feelsLike}\u2103"
        humidity.text = "${now.humidity}%"
        pressure.text = "${now.pressure}hpa"
    }
}
