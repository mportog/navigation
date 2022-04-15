package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.ui.viewmodel.MinhacontaViewModel
import kotlinx.android.synthetic.main.minha_conta.*
import org.koin.android.viewmodel.ext.android.viewModel

class MinhaContaFragment : BaseFragment() {
    private val minhaContaviewModel: MinhacontaViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.minha_conta, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        minhaContaviewModel.usuario.observe(viewLifecycleOwner, Observer {
            minha_conta_email.text = it.email
        })
    }

}