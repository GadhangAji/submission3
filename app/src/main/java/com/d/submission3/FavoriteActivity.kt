package com.d.submission3

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.d.submission3.adapter.FavoriteAdapter
import com.d.submission3.model.FavoriteResponse
import com.d.submission3.viewModel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    private val tag = FavoriteActivity::class.simpleName
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backGroundColor()
        setContentView(R.layout.activity_favorite)

        configRecyclerView()
        configQueryAll()
    }

    override fun onResume() {
        super.onResume()

        configRecyclerView()
        configQueryAll()
    }

    private fun configQueryAll() {

        progressBar.visibility = View.VISIBLE

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favoriteViewModel.getQueryAll(this).observe(this, Observer {

            progressBar.visibility = View.GONE

            if (it.size > 0) {
                Log.d(tag, "getAllUser")
                adapter.favorites = it
//                lottieNotFound.visibility = View.GONE
            } else {
                Log.d(tag, "get All User: empty")
//                lottieNotFound.visibility = View.VISIBLE
                adapter.favorites = it
            }

        })

    }

    private fun configRecyclerView() {

        adapter = FavoriteAdapter()
        val width = Resources.getSystem().displayMetrics.widthPixels

        favoriteRecyclerView.layoutManager = GridLayoutManager(applicationContext, width / 300)
        favoriteRecyclerView.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(favorite: FavoriteResponse) {
                showSelectedData(favorite)
            }
        })

        adapter.notifyDataSetChanged()

    }

    fun showSelectedData(favorite: FavoriteResponse) {
        val intentDetail = Intent(this, DetailActivity::class.java)
        intentDetail.putExtra(DetailActivity.EXTRA_DATA, favorite.username)
        startActivity(intentDetail)

        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
        Log.d(tag, "intent to Detail Activity")
    }

    fun backButton(view: View) {
        onBackPressed()
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
        Log.d(tag, "backButton: onBackPressed")
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
    }

    private fun backGroundColor() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
    }
}