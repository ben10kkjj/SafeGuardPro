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
import com.pira.safeguardpro.view.adapter.EpiAdapter
import com.pira.safeguardpro.databinding.FragmentRelatorioEpiBinding
import com.pira.safeguardpro.service.model.Epi
import com.pira.safeguardpro.service.model.Login
import com.pira.safeguardpro.viewmodel.EmprestimoViewModel
import com.pira.safeguardpro.viewmodel.EpiViewModel


class RelatorioEpiFragment : Fragment() {

    private val viewModel: EpiViewModel by viewModels()
    private val viewModelEmprestimo: EmprestimoViewModel by viewModels()

    private lateinit var adapter: EpiAdapter

    private val episFuncionario = mutableListOf<Epi>()

    private var _binding: FragmentRelatorioEpiBinding? = null
    private val binding: FragmentRelatorioEpiBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRelatorioEpiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EpiAdapter(listOf()) { epi ->
            val epiBundle = Bundle()
            epiBundle.putInt("idEpi", epi.id)
            arguments = epiBundle
            findNavController().navigate(R.id.cadasEpiFragment, arguments)
        }

        //configurar a recycler
        val recycler = binding.rvEpi
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        viewModel.epiList.observe(viewLifecycleOwner) {
            adapter.updateEpis(it)
        }

        viewModel.epi.observe(viewLifecycleOwner) { epi ->
            episFuncionario.add(epi)
//            TODO testar aqui ou na linha 75
            adapter.updateEpis(episFuncionario)
        }

        viewModelEmprestimo.emprestimoList.observe(viewLifecycleOwner) { listEntregas ->
            val entregasFuncionario = listEntregas.filter { it.codigo_funcionario == Login.userId }

            entregasFuncionario.forEach {
                viewModel.loadEpiByCa(it.numero_ca)
            }

//            TODO testar aqui ou na linha 64
            adapter.updateEpis(episFuncionario)
            Toast.makeText(requireContext(), "Epis: $listEntregas", Toast.LENGTH_LONG).show()
        }

        viewModel.erro.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Erro $it", Toast.LENGTH_LONG).show()
            Log.e("erro relatorio epi", it)
        }

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.relatorioEpiFragment)
        }

        if (Login.userAdmin){
            // Carregar as pessoas cadastradas
            viewModel.load()
        } else {
            viewModelEmprestimo.load()
        }
    }
}