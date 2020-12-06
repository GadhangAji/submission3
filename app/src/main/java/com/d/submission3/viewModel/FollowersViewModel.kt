package com.d.submission3.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.d.submission3.model.FollowersResponse
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowersViewModel : ViewModel() {

    val listUsersFollowers = MutableLiveData<ArrayList<FollowersResponse>>()

    fun setFollowersUser(getUsername: String?, context: Context) {

        val listFollowersItems = ArrayList<FollowersResponse>()

        val apiKey = "76355315c772623c18f41a7631303940d06f26a6"
        val url = "https://api.github.com/users/$getUsername/followers"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {

                    val result = String(responseBody!!)
                    val responseArray = JSONArray(result)

                    for (i in 0 until responseArray.length()) {

                        val users = responseArray.getJSONObject(i)
                        val followersResponse =
                            FollowersResponse()

                        followersResponse.login = users.getString("login")
                        followersResponse.avatar_url = users.getString("avatar_url")

                        listFollowersItems.add(followersResponse)
                    }

                    listUsersFollowers.postValue(listFollowersItems)

                } catch (e: Exception) {
                    Toast.makeText(context, "Unable to Connect", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Toast.makeText(context, "Unable to Connect: $statusCode", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getFollowersUser(): LiveData<ArrayList<FollowersResponse>> {
        return listUsersFollowers
    }
}