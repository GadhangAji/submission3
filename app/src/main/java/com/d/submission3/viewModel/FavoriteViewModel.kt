package com.d.submission3.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d.submission3.data.AppDatabase
import com.d.submission3.model.FavoriteResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteViewModel  : ViewModel() {

    private val queryAll = MutableLiveData<ArrayList<FavoriteResponse>>()
    private val queryById = MutableLiveData<ArrayList<FavoriteResponse>>()

    fun getQueryAll(context: Context): LiveData<ArrayList<FavoriteResponse>> {
        viewModelScope.launch(Dispatchers.Main) {
            val deferredQueryById = async(Dispatchers.IO) {
                AppDatabase.getInstance(context).favoriteDao().getUsers()
            }
            val get = deferredQueryById.await()
            queryAll.postValue(get)
        }
        return queryAll
    }

    fun getQueryByName(context: Context, userName: String): LiveData<ArrayList<FavoriteResponse>> {
        viewModelScope.launch(Dispatchers.Main) {
            val deferredQueryById = async(Dispatchers.IO) {
                AppDatabase.getInstance(context).favoriteDao().getUserByName(userName)
            }
            val get = deferredQueryById.await()
            queryById.postValue(get)
        }
        return queryById
    }
}