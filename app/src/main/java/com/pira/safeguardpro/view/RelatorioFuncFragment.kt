package com.pira.safeguardpro.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.pira.safeguardpro.databinding.FragmentRelatorioFuncBinding
import com.pira.safeguardpro.view.Adapter.FuncionarioAdapter


class RelatorioFuncFragment : Fragment() {

    private lateinit var adapter: FuncionarioAdapter

    private var _binding: FragmentRelatorioFuncBinding? = null
    private val binding: FragmentRelatorioFuncBinding  get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRelatorioFuncBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter

        //Configura a recycler
        val recycler = binding.rvFuncionario
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
    }

}