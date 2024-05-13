package com.pira.safeguardpro.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pira.safeguardpro.databinding.ListItemEpiBinding
import com.pira.safeguardpro.service.model.Epi


class EpiAdapter(epi: List<Epi>?, private val clickListListener: (Epi)-> Unit):
    RecyclerView.Adapter<EpiAdapter.EpiViewHolder>(){

    //Criar uma lista vazia de pessoas
    private var epiList: List<Epi> = arrayListOf()

    class EpiViewHolder(private val binding: ListItemEpiBinding):
        RecyclerView.ViewHolder(binding.root){
        //Carregas as informações da pessoa na lista
        fun bind(epi: Epi, clickListListener: (Epi)-> Unit){

            //Configura algum item da lista
            binding.root.setOnClickListener{
                clickListListener(epi)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpiViewHolder {
        val listItemPessoaBinding = ListItemEpiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpiViewHolder(listItemPessoaBinding)
    }

    override fun getItemCount(): Int {
        return epiList.count()
    }

    override fun onBindViewHolder(holder: EpiViewHolder, position: Int) {
        holder.bind(epiList[position], clickListListener)
    }

    //carrega a lista de pessoas para serem exibidas
    fun updateEpis(list: List<Epi>){
        epiList = list
        notifyDataSetChanged()
    }
}