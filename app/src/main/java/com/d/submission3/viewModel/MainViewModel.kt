package com.d.submission3.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header


import androidx.lifecycle.ViewModel
import com.d.submission3.model.UserResponse
import org.json.JSONObject

class MainViewModel :ViewModel() {
    private val listUsers = MutableLiveData<ArrayList<UserResponse.Item>>()

    fun setUser(users: String) {
        val listItems = ArrayList<UserResponse.Item>()

        val apiKey = "76355315c772623c18f41a7631303940d06f26a6"
        val url = "https://api.github.com/search/users?q=$users"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    //parsing json
                    val result = String(responseBody)
                    Log.e("data", result)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("items")

                    for (i in 0 until list.length()) {
                        val user = list.getJSONObject(i)
                        val userResponse = UserResponse.Item()
                        userResponse.login = user.getString("login")
//                        userResponse.organizations_url = user.getString("organizations_url")
                        userResponse.avatar_url = user.getString("avatar_url")

                        listItems.add(userResponse)
                    }

                     listUsers.postValue(listItems)
                } catch (e: Exception) {
                    Log.e("Exception", e.message.toString())
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.e("onFailure", error.message.toString())
            }
        })
    }

    fun getUsers(): LiveData<ArrayList<UserResponse.Item>> {
        return listUsers
    }
}