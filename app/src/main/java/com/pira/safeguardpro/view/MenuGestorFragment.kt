package com.pira.safeguardpro.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pira.safeguardpro.R
import com.pira.safeguardpro.databinding.FragmentMenuGestorBinding
import com.pira.safeguardpro.service.model.Login

class MenuGestorFragment : Fragment() {

    private var _binding: FragmentMenuGestorBinding? = null
    private val binding: FragmentMenuGestorBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuGestorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!Login.userAdmin) {
            binding.btnEntregas.visibility = View.GONE

            binding.btnRelatorioEpi.text = "Meus EPIs"
            binding.btnRelatorioFunc.text = "Minhas informações"
        }

        binding.btnEntregas.setOnClickListener {
            findNavController().navigate(R.id.relatorioEntregaFragment)
        }

        binding.btnRelatorioEpi.setOnClickListener {
            if (Login.userAdmin){
                val funcionarioBundle = Bundle()
                funcionarioBundle.putInt("idFuncionario", Login.userId)
                arguments = funcionarioBundle
                findNavController().navigate(R.id.cadasFuncFragment, arguments)
            } else {
                findNavController().navigate(R.id.relatorioFuncFragment)
            }
        }

        binding.btnRelatorioFunc.setOnClickListener {
            findNavController().navigate(R.id.relatorioEpiFragment)
        }
    }
}