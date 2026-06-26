package com.example.android_assignement_question_3

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_assignement_question_3.api.RetrofitClient
import com.example.android_assignement_question_3.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var etCity: EditText
    private lateinit var btnSearch: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var tvCity: TextView
    private lateinit var tvTemperature: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvHumidity: TextView
    private lateinit var tvWind: TextView
    private lateinit var tvCountry: TextView

    // Replace with your WeatherAPI.com API Key
    private val apiKey = "dd7fada247e844cf907105519262606"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()

        btnSearch.setOnClickListener {

            val city = etCity.text.toString().trim()

            if (city.isEmpty()) {

                etCity.error = "Please enter a city"
                etCity.requestFocus()

            } else {

                // Automatically search Indian cities
                loadWeather("$city, India")

            }
        }
    }

    private fun initializeViews() {

        etCity = findViewById(R.id.etCity)
        btnSearch = findViewById(R.id.btnSearch)
        progressBar = findViewById(R.id.progressBar)

        tvCity = findViewById(R.id.tvCity)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvCondition = findViewById(R.id.tvCondition)
        tvHumidity = findViewById(R.id.tvHumidity)
        tvWind = findViewById(R.id.tvWind)
        tvCountry = findViewById(R.id.tvCountry)
    }

    private fun loadWeather(city: String) {

        progressBar.visibility = View.VISIBLE

        RetrofitClient.api.getWeather(apiKey, city)
            .enqueue(object : Callback<WeatherResponse> {

                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {

                    progressBar.visibility = View.GONE

                    if (response.isSuccessful && response.body() != null) {

                        val weather = response.body()!!

                        tvCity.text = weather.location.name
                        tvTemperature.text = "${weather.current.temp_c} °C"
                        tvCondition.text = weather.current.condition.text
                        tvHumidity.text = "Humidity : ${weather.current.humidity}%"
                        tvWind.text = "Wind Speed : ${weather.current.wind_kph} km/h"
                        tvCountry.text = "Country : ${weather.location.country}"

                    } else {

                        Toast.makeText(
                            this@MainActivity,
                            "City not found!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<WeatherResponse>,
                    t: Throwable
                ) {

                    progressBar.visibility = View.GONE

                    Toast.makeText(
                        this@MainActivity,
                        "Error: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}