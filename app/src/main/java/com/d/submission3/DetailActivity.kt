package com.d.submission3

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.d.submission3.adapter.DetailAdapter
import com.d.submission3.data.AppDatabase
import com.d.submission3.model.DetailResponse
import com.d.submission3.model.FavoriteResponse
import com.d.submission3.model.UserResponse
import com.d.submission3.viewModel.DetailViewModel
import com.d.submission3.viewModel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private val tag = DetailActivity::class.simpleName
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var detailUser: DetailResponse
    private var favorite: FavoriteResponse? = null
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        showLoading(true)

        val intent = intent.getParcelableExtra(EXTRA_DATA) as UserResponse.Item
        val getUsername = intent.login

        supportActionBar?.elevation = 0F
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getUsername

        configDetailViewModel(getUsername!!)
        configViewDetail()

        favButton.setOnClickListener {
            if (favorite == null)
                favButton()
            else
                favorite?.userId?.let { it1 -> unFavButton(it1) }
        }
    }

    private fun configDetailViewModel(getUsername: String) {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        detailViewModel.setDetailUser(getUsername, this)
        detailViewModel.getDetailUser().observe(this, Observer {
            detailUser = it
            tv_item_username.text = it.name
            tv_item_name.text = it.login
            tv_item_company.text = it.organizations_url
            tv_item_location.text = it.location
                    tv_item_repository.text = it.public_repos.toString()
                    tv_item_followers.text = it.followers.toString()
                    tv_item_following.text = it.following.toString()
            Glide.with(this)
                .load(it.avatar_url)
                .into(tv_img)


            showLoading(false)
            checkFavButton()
        })
    }

    private fun configViewDetail() {
        val sectionsPagerAdapter = DetailAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
            tv_img.visibility = View.INVISIBLE
            tabs.visibility = View.INVISIBLE
            viewPager.visibility = View.INVISIBLE
        } else {
            progressBar.visibility = View.GONE
            tv_img.visibility = View.VISIBLE
            tabs.visibility = View.VISIBLE
            viewPager.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun checkFavButton() {
        favoriteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FavoriteViewModel::class.java)

        detailUser.login?.let {
            favoriteViewModel.getQueryByName(this, it).observe(this, Observer {

                if (it.isNullOrEmpty()) {
                    favorite = null
                    Log.d(tag, "favButton")
                    favButton.setBackgroundResource(R.drawable.fav1)
                } else {
                    favorite = it[0]
                    Log.d(tag, "unFavButton")
                    favButton.setBackgroundResource(R.drawable.fav0)
                }

            })
        }
    }
    private fun favButton() {
        AppDatabase.getInstance(this).favoriteDao().insertUser(
            FavoriteResponse(
                username = detailUser.login,
                avatar = detailUser.avatar_url,
                userId = null
            )
        )

        favButton.setBackgroundResource(R.drawable.fav0)
        val toast = Toast.makeText(
            this,
            getString(R.string.add_fav, detailUser.login),
            Toast.LENGTH_SHORT
        )
        toast.show()
        toast.setGravity(Gravity.CENTER, 0, 0)
        Log.d(tag, "favored")
        checkFavButton()
    }

    private fun unFavButton(userId: Int) {
        AppDatabase.getInstance(this).favoriteDao().deleteUser(userId)
        favButton.setBackgroundResource(R.drawable.fav1)
        val toast = Toast.makeText(
            this,
            getString(R.string.remove_fav, detailUser.login),
            Toast.LENGTH_SHORT
        )
        toast.show()
        toast.setGravity(Gravity.CENTER, 0, 0)
        checkFavButton()
    }
}