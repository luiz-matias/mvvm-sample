package com.luizmatias.mvvmsample.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.luizmatias.mvvmsample.R
import com.luizmatias.mvvmsample.databinding.ActivityLoginBinding
import com.luizmatias.mvvmsample.viewmodel.LoginStateHandler
import com.luizmatias.mvvmsample.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewmodel = viewModel

        viewModel.loginStateHandler.observe(this, Observer {
            when (it) {
                is LoginStateHandler.setCarregando -> {
                    progressBarCarregando.visibility = if (it.carregando) View.VISIBLE else View.INVISIBLE
                    progressBarCarregando.isIndeterminate = it.carregando
                }
                is LoginStateHandler.setEmailError -> {
                    if (it.erro) {
                        binding.textInputLayoutEmail?.error = getString(R.string.email_invalido)
                        binding.textInputLayoutEmail?.isErrorEnabled = true
                    } else {
                        binding.textInputLayoutEmail?.isErrorEnabled = false
                    }
                }
                is LoginStateHandler.setSenhaError -> {
                    if (it.erro) {
                        binding.textInputLayoutSenha?.error = getString(R.string.senha_invalida)
                        binding.textInputLayoutSenha?.isErrorEnabled = true
                    } else {
                        binding.textInputLayoutSenha?.isErrorEnabled = false
                    }
                }
                is LoginStateHandler.navegarParaHome -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
                is LoginStateHandler.setLoginError -> {
                    setLoginError()
                }
            }
        })

    }

    private fun setLoginError() {
        val builder = AlertDialog
                .Builder(this)
                .setTitle(getString(R.string.erro_login_titulo))
                .setMessage(getString(R.string.erro_login_descricao))
                .setPositiveButton(getString(R.string.ok), { d, _ ->
                    d.dismiss()
                })
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
