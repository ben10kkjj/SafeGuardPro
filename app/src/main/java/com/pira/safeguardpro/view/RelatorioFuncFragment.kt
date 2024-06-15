package com.pira.safeguardpro.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pira.safeguardpro.R
import com.pira.safeguardpro.databinding.FragmentRelatorioFuncBinding
import com.pira.safeguardpro.view.adapter.FuncionarioAdapter
import com.pira.safeguardpro.viewmodel.FuncionarioViewModel


class RelatorioFuncFragment : Fragment() {
    private val viewModel: FuncionarioViewModel by viewModels()

    private lateinit var adapter: FuncionarioAdapter

    private var _binding: FragmentRelatorioFuncBinding? = null
    private val binding: FragmentRelatorioFuncBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRelatorioFuncBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FuncionarioAdapter(viewModel.funcionarioList.value) { funcionario ->
            val funcionarioBundle = Bundle()
            funcionarioBundle.putInt("idFuncionario", funcionario.id)
            arguments = funcionarioBundle
            findNavController().navigate(R.id.cadasFuncFragment, arguments)
        }

        //Configura a recycler
        val recycler = binding.rvFuncionario
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter


        viewModel.funcionarioList.observe(viewLifecycleOwner) {
            adapter.updateFuncionario(it)
        }

        viewModel.erro.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Erro $it", Toast.LENGTH_LONG).show()
            Log.e("erro relatorio func", it)
        }

        binding.btnAddFunc.setOnClickListener {
            findNavController().navigate(R.id.relatorioFuncFragment)
        }

        // Carregar as pessoas cadastradas
        viewModel.load()
    }
}