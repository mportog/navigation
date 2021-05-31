package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.repository.LoginRepository

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    fun logar() {
        repository.logar()
    }

    fun estaLogado(): Boolean = repository.estaLogado()

    fun deslogar() {
        repository.deslogar()
    }

}
