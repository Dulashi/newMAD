package com.example.industryexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.textclassifier.TextLanguage.Request
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var country = findViewById<Spinner>(R.id.country)
        var coun = ArrayList<String>()
        coun.add("America")
        coun.add("Italy")
        coun.add("Malaysia")


        var adapter_country =
            ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, coun)
        adapter_country.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        country!!.setAdapter(adapter_country)


        country?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                if (country.selectedItem.equals("America")) {
                    var country = "America"
                    getWeatherData(country)
                }
                if (country.selectedItem.equals("Italy")) {
                    var country = "Italy"
                    getWeatherData(country)
                }
                if (country.selectedItem.equals("Malaysia")) {
                    var country = "Malaysia"
                    getWeatherData(country)

                }
            }

            private fun getWeatherData(country: String) {
                var country: String = country
                var queue = Volley.newRequestQueue(this@MainActivity)
                val url =
                    "https://api.openweathermap.org/data/2.5/weather?q=$country&appid=ae62f7e01e62179e26caad1f5283d43d"
                val jsonRequest =
                    JsonObjectRequest(com.android.volley.Request.Method.GET, url, null,
                        { response -> setValues(response) },
                        { Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG) })

                queue.add(jsonRequest)
            }

            private fun setValues(response: JSONObject) {

                var txtDescription: TextView
                var txtTemp: TextView
                var txtWind: TextView
                var txtHumidity: TextView
                var image: ImageView


                txtDescription = findViewById(R.id.description)
                image = findViewById(R.id.image)
                txtTemp = findViewById(R.id.temperature)
                txtWind = findViewById(R.id.wind)
                txtHumidity = findViewById(R.id.humidity)


                var description =
                    response.getJSONArray("weather").getJSONObject(0).getString("description")
                var icon = response.getJSONArray("weather").getJSONObject(0).getString("icon")
                var url = "https://openweathermap.org/img/wn/$icon.png"



                Glide.with(this@MainActivity)
                    .load(url)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .fitCenter()
                    .into(image)


                var temp = response.getJSONObject("main").getString("temp")
                temp = ((((temp).toFloat() - 273.15)).toInt()).toString()

                var wind = response.getJSONObject("wind").getString("speed")
                var humidity = response.getJSONObject("main").getString("humidity") + "%"



                txtDescription.setText("Description  ${description}")
                txtTemp.setText("Temperature  ${temp}°C")
                txtWind.setText("Wind  ${wind} km/h")
                txtHumidity.setText("Humidity  ${humidity}")

            }
        }
    }}