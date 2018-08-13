package com.luizmatias.mvvmsample.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.luizmatias.mvvmsample.R
import com.luizmatias.mvvmsample.databinding.ActivityHomeBinding
import com.luizmatias.mvvmsample.viewmodel.HomeStateHandler
import com.luizmatias.mvvmsample.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewmodel = viewModel

        initObservers()

        binding.swipeRefreshLayoutAtualizar.setColorSchemeColors(getColor(R.color.colorPrimary), getColor(R.color.colorAccent))
        binding.swipeRefreshLayoutAtualizar.setOnRefreshListener {
            viewModel.loadUser("luiz-matias")
        }

    }

    private fun initObservers() {
        viewModel.getUser().observe(this, Observer {
            it?.let {
                binding.user = it
            }
        })

        viewModel.homeStateHandler.observe(this, Observer {
            when (it) {
                is HomeStateHandler.setError -> {
                    Snackbar.make(binding.root, it.erro, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        viewModel.loadUser("luiz-matias")
    }
}
