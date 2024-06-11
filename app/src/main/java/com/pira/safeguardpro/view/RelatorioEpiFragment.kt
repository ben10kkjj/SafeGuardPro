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
import com.pira.safeguardpro.viewmodel.EpiViewModel


class RelatorioEpiFragment : Fragment() {

    private val viewModel: EpiViewModel by viewModels()

    private lateinit var adapter: EpiAdapter

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

        viewModel.erro.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Erro $it", Toast.LENGTH_LONG).show()
            Log.e("erro relatorio epi", it)
        }

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.relatorioEpiFragment)
        }

        // Carregar as pessoas cadastradas
        viewModel.load()
    }
}
