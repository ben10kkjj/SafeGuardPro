package com.pira.safeguardpro.view.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pira.safeguardpro.databinding.ListItemFuncionarioBinding

class FuncionarioAdapter(
    funcionarioAdapter: List<FuncionarioAdapter>,
    private val clickListlistener: (FuncionarioAdapter) -> Unit
) :
    RecyclerView.Adapter<FuncionarioAdapter.PessoaViewHolder>() {

    private var funcionarioBinding: List<FuncionarioAdapter> = arrayListOf()

    class PessoaViewHolder(private val binding: ListItemFuncionarioBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(funcionarioAdapter: FuncionarioAdapter, clickListlistener: (FuncionarioAdapter) -> Unit) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PessoaViewHolder {
        val listItemFuncionarioBinding =
            ListItemFuncionarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PessoaViewHolder(listItemFuncionarioBinding)
    }

    override fun getItemCount(): Int {
        return funcionarioBinding.count()
    }

    override fun onBindViewHolder(holder: PessoaViewHolder, position: Int) {
        holder.bind(funcionarioBinding[position], clickListlistener)
    }

    fun updateFuncionario (list: List<FuncionarioAdapter>) {
        funcionarioBinding = list
        notifyDataSetChanged()
    }
}