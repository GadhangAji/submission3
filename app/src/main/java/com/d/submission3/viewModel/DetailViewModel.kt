package com.d.submission3.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.d.submission3.model.DetailResponse
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel : ViewModel() {


    val listUsersDetail = MutableLiveData<DetailResponse>()
    fun setDetailUser(getUsername: String?, context: Context) {
        val apiKey = "76355315c772623c18f41a7631303940d06f26a6"
        val url = "https://api.github.com/users/$getUsername"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {

            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) = try {

                val result = String(responseBody!!)
                val responseObject = JSONObject(result)
                val userItemsDetail =
                    DetailResponse()

                userItemsDetail.login = responseObject.getString("login")
                userItemsDetail.organizations_url = responseObject.getString("company")
                userItemsDetail.name = responseObject.getString("name")
                userItemsDetail.location = responseObject.getString("location")
                    userItemsDetail.public_repos = responseObject.getInt("public_repos")
                    userItemsDetail.followers = responseObject.getInt("followers")
                    userItemsDetail.following = responseObject.getInt("following")
                userItemsDetail.avatar_url = responseObject.getString("avatar_url")

                listUsersDetail.postValue(userItemsDetail)

            } catch (e: Exception) {
                Toast.makeText(context, "Unable to Connect", Toast.LENGTH_SHORT).show()
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

    fun getDetailUser(): LiveData<DetailResponse> {
        return listUsersDetail
    }
}