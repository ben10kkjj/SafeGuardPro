package com.pira.safeguardpro.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pira.safeguardpro.R
import com.pira.safeguardpro.databinding.FragmentRelatorioEntregaBinding
import com.pira.safeguardpro.view.adapter.EntregaAdapter
import com.pira.safeguardpro.viewmodel.EmprestimoViewModel

class RelatorioEntregaFragment: Fragment(){

    private val viewModel: EmprestimoViewModel by viewModels()

    private lateinit var adapter: EntregaAdapter

    private var _binding: FragmentRelatorioEntregaBinding? = null
    private val binding: FragmentRelatorioEntregaBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRelatorioEntregaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EntregaAdapter(viewModel.emprestimoList.value) { entrega ->
            val entregaBundle = Bundle()
            entregaBundle.putInt("idEntrega", entrega.id)
            arguments = entregaBundle
            findNavController().navigate(R.id.entregaFragment, arguments)
        }

        //configurar a recycler
        val recycler = binding.rvEntrega
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        viewModel.emprestimoList.observe(viewLifecycleOwner) {
            adapter.updateEmprestimos(it)
        }

        viewModel.erro.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Erro $it", Toast.LENGTH_LONG).show()
            Log.e("erro relatorio entrega", it)
        }

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.relatorioEntregaFragment)
        }

        // Carregar as pessoas cadastradas
        viewModel.load()
    }
}