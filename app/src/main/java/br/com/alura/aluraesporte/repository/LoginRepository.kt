package br.com.alura.aluraesporte.repository

import android.content.SharedPreferences
import androidx.core.content.edit

private const val CHAVE_LOGADO = "CHAVE_LOGADO"

class LoginRepository(private val preferences: SharedPreferences) {
    fun logar() =
        salvaEstadoLogin(true)


    fun estaLogado(): Boolean =
        preferences.getBoolean(CHAVE_LOGADO, false)

    fun deslogar() =
        salvaEstadoLogin(false)


    private fun salvaEstadoLogin(estado: Boolean) {
        preferences.edit {
            putBoolean(CHAVE_LOGADO, estado)
        }
    }
}
