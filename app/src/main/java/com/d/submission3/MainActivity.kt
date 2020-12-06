package com.d.submission3

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.d.submission3.DetailActivity.Companion.EXTRA_DATA
import com.d.submission3.adapter.userAdapter
import com.d.submission3.fragment.FavoriteFragment
import com.d.submission3.viewModel.MainViewModel
import com.d.submission3.model.UserResponse
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: userAdapter

    private lateinit var mainViewModel: MainViewModel

    private val listUser = ArrayList<UserResponse.Item>()
    companion object {
        const val STATE_TRUE = "stateTrue"
        const val STATE_FALSE = "stateFalse"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showTextStart(true)
        showRecyclerViewUser()

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get((MainViewModel::class.java))

        mainViewModel.getUsers().observe(this, Observer {
            if (it != null) {
                showLoading(false)
                showTextBase(false)
                showTextStart(false)
                listUser.clear()
                listUser.addAll(it)
                adapter.notifyDataSetChanged()
            }
            if (it.isEmpty()){
                if (savedInstanceState != null){
                    showTextBase(true)
                }
                else{
                    showTextBase(true)
                }
            }
        })
        showLoading(true)
        showTextBase(false)
        mainViewModel.setUser("a")
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_TRUE, true)
        outState.putBoolean(STATE_FALSE, false)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
    private fun showTextBase(state: Boolean) {
        if (state) {
            tv_item_find.visibility = View.VISIBLE
        } else {
            tv_item_find.visibility = View.GONE
        }
    }

    private fun showTextStart(state: Boolean) {
        if (state) {
            find.visibility = View.VISIBLE
        } else {
            find.visibility = View.GONE
        }
    }

    private fun showRecyclerViewUser() {
        rv_komponen.layoutManager = LinearLayoutManager(this)
        adapter = userAdapter(this, listUser)
        rv_komponen.adapter = adapter
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : userAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponse.Item) {
                showSelectedData(data)
            }
        })
    }

    private fun showSelectedData(data: UserResponse.Item) {
        val dataUser = UserResponse.Item(
            data.login,
            data.organizations_url,
            data.avatar_url,
            data.location,
            data.repository,
            data.followers,
            data.following
        )

        val intentDetail = Intent(this@MainActivity, DetailActivity::class.java)
        intentDetail.putExtra(EXTRA_DATA, dataUser)
        startActivity(intentDetail)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                showLoading(true)
                showTextBase(false)
                mainViewModel.setUser(query)
                val view: View? = this@MainActivity.currentFocus
                if (view != null) {
                    val imm: InputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.favorite) {
            showLoading(false)
            showTextStart(false)
            showTextBase(false)

            startActivity(Intent(this, FavoriteActivity::class.java))

//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_favorite, FavoriteFragment())
//                .addToBackStack(null)
//                .commit()
//            return true
        }
        if (item.itemId == R.id.language) {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        if (item.itemId == R.id.TabAlarm) {
            startActivity(Intent(this, AlarmActivity::class.java))
        }
            return super.onOptionsItemSelected(item)
    }
}