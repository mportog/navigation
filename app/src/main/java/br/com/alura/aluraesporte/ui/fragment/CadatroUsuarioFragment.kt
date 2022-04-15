package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.extensions.snackBar
import br.com.alura.aluraesporte.model.Usuario
import br.com.alura.aluraesporte.ui.viewmodel.CadastroUsuarioViewModel
import br.com.alura.aluraesporte.ui.viewmodel.ComponentesVisuais
import br.com.alura.aluraesporte.ui.viewmodel.EstadoAppViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.cadastro_usuario.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CadatroUsuarioFragment : Fragment() {
    private val controlador by lazy {
        findNavController()
    }
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()
    private val cadastroUsuarioViewModel: CadastroUsuarioViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.cadastro_usuario,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoAppViewModel.temComponentes = ComponentesVisuais()
        cadastro_usuario_botao_cadastrar.setOnClickListener {
            limpaErrosCampos()
            val email = cadastro_usuario_email.editText?.text.toString()
            val senha = cadastro_usuario_senha.editText?.text.toString()
            val confirmaSenha = cadastro_usuario_confirma_senha.editText?.text.toString()

            if (validaCampos(email, senha, confirmaSenha))
                cadastra(Usuario(email, senha))
        }
    }

    private fun cadastra(usuario: Usuario) {
        cadastroUsuarioViewModel.cadastra(usuario)
            .observe(viewLifecycleOwner, Observer {
                if (it.dado) {
                    controlador.popBackStack()
                } else {
                    view?.snackBar(it.erro.toString())
                }
            })
    }

    private fun validaCampos(
        email: String,
        senha: String,
        confirmaSenha: String
    ): Boolean {
        var valido = true

        if (email.isBlank()) {
            cadastro_usuario_email.error = "E-mail é necessário"
            valido = false
        }

        if (email.isBlank()) {
            cadastro_usuario_senha.error = "Senha é necessária"
            valido = false
        }

        if (senha != confirmaSenha) {
            cadastro_usuario_confirma_senha.error = "Senhas devem ser iguais"
            valido = false
        }
        return valido
    }

    private fun limpaErrosCampos() {
        cadastro_usuario_email.error = null
        cadastro_usuario_senha.error = null
        cadastro_usuario_confirma_senha.error = null
    }
}