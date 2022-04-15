package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.model.Usuario
import br.com.alura.aluraesporte.repository.FirebaseAuthRepository

class MinhacontaViewModel(private val firebaseAuthRepository: FirebaseAuthRepository) :
    ViewModel() {
    val usuario: LiveData<Usuario> = firebaseAuthRepository.buscaUsuario()
}