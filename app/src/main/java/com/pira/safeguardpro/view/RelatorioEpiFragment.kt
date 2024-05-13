package com.pira.safeguardpro.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.pira.safeguardpro.view.adapter.EpiAdapter
import com.pira.safeguardpro.databinding.FragmentRelatorioEpiBinding


class RelatorioEpiFragment : Fragment() {

    private lateinit var adapter: EpiAdapter

    private var _binding: FragmentRelatorioEpiBinding? = null
    private val binding: FragmentRelatorioEpiBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRelatorioEpiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EpiAdapter(listOf()) { epi ->

        }

        //configurar a recycler
        val recycler = binding.rvEpi
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
    }
}