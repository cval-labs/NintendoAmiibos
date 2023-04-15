package com.example.nintendoamiibos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private lateinit var amiiboList:  MutableList<Triple<String,String,String>>
    private lateinit var rvAmiibos: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        amiiboList = arrayListOf()
        rvAmiibos = findViewById(R.id.amiibo_list)

        getAmiibo()
    }

    private fun getAmiibo() {
        val client = AsyncHttpClient()

        client["https://www.amiiboapi.com/api/amiibo/", object: JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.d("Amibo Error", "error")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d("Amibo", "success! $json")

                val amiiboArray = json.jsonObject.getJSONArray("amiibo")
                for (i in 0 until amiiboArray.length()) {
                    val image = amiiboArray.getJSONObject(i).getString("image")
                    val name = amiiboArray.getJSONObject(i).getString("name")
                    val series = amiiboArray.getJSONObject(i).getString("amiiboSeries")

                    Log.d("Amiibo Image", "$i, $image")
                    Log.d("Amiibo Name", "$i, $name")
                    Log.d("Amiibo Series", "$i, $series")
                    amiiboList.add(Triple(image, name, series))
                }
                val adapter = AmiiboAdapter(amiiboList)
                rvAmiibos.adapter = adapter
                rvAmiibos.layoutManager = LinearLayoutManager(this@MainActivity)
                rvAmiibos.addItemDecoration(DividerItemDecoration(this@MainActivity,LinearLayoutManager.VERTICAL))
            }

        }]
    }
}