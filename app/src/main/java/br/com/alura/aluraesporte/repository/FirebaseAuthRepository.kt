package br.com.alura.aluraesporte.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.aluraesporte.model.Usuario
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import java.lang.Exception
import java.lang.IllegalArgumentException

class FirebaseAuthRepository(private val firebaseAuth: FirebaseAuth) {
    fun deslogaUsuario() {
        firebaseAuth.signOut()
    }

    fun autenticaUsuario(usuario: Usuario): LiveData<Resource<Boolean>> {
        val liveData = MutableLiveData<Resource<Boolean>>()
        try {
            firebaseAuth.signInWithEmailAndPassword(usuario.email, usuario.senha)
                .addOnCompleteListener { tarefa ->
                    if (tarefa.isSuccessful)
                        liveData.value = Resource(true)
                    else {
                        Log.i(FIREBASE_AUTH_REPOSITORY_TAG, "autenticaUsuario: Falha")
                        val mensagemErro = verificaErroAutenticacao(tarefa.exception)
                        liveData.value = Resource(false, mensagemErro)
                    }
                }
        } catch (e: IllegalArgumentException) {
            liveData.value = Resource(false, "E-mail ou senha não podem ser vazios")
        }
        return liveData
    }

    private fun verificaErroAutenticacao(exception: Exception?) =
        when (exception) {
            is FirebaseAuthInvalidUserException,
            is FirebaseAuthInvalidCredentialsException -> "E-mail ou senha incorretos"
            else -> "Erro desconhecido"
        }

    fun cadastraUsuario(usuario: Usuario): LiveData<Resource<Boolean>> {
        val liveData = MutableLiveData<Resource<Boolean>>()
        try {
            val task = firebaseAuth.createUserWithEmailAndPassword(usuario.email, usuario.senha)
            task.addOnSuccessListener {
                Log.i(FIREBASE_AUTH_REPOSITORY_TAG, "cadastraUsuario: Sucesso")
                liveData.value = Resource(true)
            }
            task.addOnFailureListener {
                Log.i(FIREBASE_AUTH_REPOSITORY_TAG, "cadastraUsuario: Falha")
                val mensagemErro = verificaErroCadastro(it)
                liveData.value = Resource(false, mensagemErro)
            }
        } catch (e: IllegalArgumentException) {
            liveData.value = Resource(false, "E-mail ou senha não podem ser vazios")
        }
        return liveData
    }

    private fun verificaErroCadastro(it: Exception) = when (it) {
        is FirebaseAuthWeakPasswordException -> "Senha menor que 6 digitos"
        is FirebaseAuthInvalidCredentialsException -> "E-mail inválido"
        is FirebaseAuthUserCollisionException -> "E-mail já cadastrado"
        else -> "Erro desconhecido"
    }

    fun estaLogado(): Boolean =
        firebaseAuth.currentUser?.let {
            true
        } ?: run {
            false
        }

    fun buscaUsuario(): LiveData<Usuario> {
        val liveData = MutableLiveData<Usuario>()
        liveData.value =
            firebaseAuth.currentUser?.let { firebaseUser ->
                firebaseUser.email?.let { Usuario(it) }
            }
        return liveData
    }

    companion object {
        private const val FIREBASE_AUTH_REPOSITORY_TAG = "FIREBASE_AUTH_REPO_TAG"
    }
}