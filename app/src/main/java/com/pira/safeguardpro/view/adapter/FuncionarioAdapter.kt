package com.pira.safeguardpro.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pira.safeguardpro.databinding.ListItemFuncionarioBinding
import com.pira.safeguardpro.service.model.Funcionario

class FuncionarioAdapter(
    funcionarioAdapter: List<Funcionario>?,
    private val clickListListener: (Funcionario) -> Unit
) :
    RecyclerView.Adapter<FuncionarioAdapter.PessoaViewHolder>() {

    private var funcionarioList: List<Funcionario> = arrayListOf()

    class PessoaViewHolder(private val binding: ListItemFuncionarioBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(funcionario: Funcionario, clickListListener: (Funcionario) -> Unit) {
//       jogar as informacoes do funcionario no item da lista

            binding.textCpf.text = funcionario.cpf.toString()
            binding.textNome.text = funcionario.nome

            //Configura algum item da lista
            binding.root.setOnClickListener{
                clickListListener(funcionario)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PessoaViewHolder {
        val listItemFuncionarioBinding =
            ListItemFuncionarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PessoaViewHolder(listItemFuncionarioBinding)
    }

    override fun getItemCount(): Int {
        return funcionarioList.count()
    }

    override fun onBindViewHolder(holder: PessoaViewHolder, position: Int) {
        holder.bind(funcionarioList[position], clickListListener)
    }

    fun updateFuncionario (list: List<Funcionario>) {
        funcionarioList = list
        notifyDataSetChanged()
    }
}