package com.pira.safeguardpro.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pira.safeguardpro.databinding.ListItemEntregaBinding
import com.pira.safeguardpro.service.model.Emprestimo


class EntregaAdapter(
    entrega: List<Emprestimo>?,
    private val clickListListener: (Emprestimo) -> Unit
) :
    RecyclerView.Adapter<EntregaAdapter.EntregaViewHolder>() {

    //Criar uma lista vazia de pessoas
    private var emprestimoList: List<Emprestimo> = arrayListOf()

    class EntregaViewHolder(private val binding: ListItemEntregaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //Carregas as informações da pessoa na lista
        fun bind(entrega: Emprestimo, clickListListener: (Emprestimo) -> Unit) {

            binding.tvDataEntrega.text = entrega.data_entrega
            binding.tvNomeEpi.text = entrega.nome_epi

            //Configura algum item da lista
            binding.root.setOnClickListener {
                clickListListener(entrega)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntregaViewHolder {
        val listItemEntregaBinding =
            ListItemEntregaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EntregaViewHolder(listItemEntregaBinding)
    }

    override fun getItemCount(): Int {
        return emprestimoList.count()
    }

    override fun onBindViewHolder(holder: EntregaViewHolder, position: Int) {
        holder.bind(emprestimoList[position], clickListListener)
    }

    //carrega a lista de pessoas para serem exibidas
    fun updateEmprestimos(list: List<Emprestimo>) {
        emprestimoList = list
        notifyDataSetChanged()
    }
}