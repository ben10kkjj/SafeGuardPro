package com.pira.safeguardpro.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pira.safeguardpro.databinding.FragmentCadasEpiBinding
import com.pira.safeguardpro.service.model.Epi
import com.pira.safeguardpro.service.model.Login
import com.pira.safeguardpro.viewmodel.EpiViewModel


class CadasEpiFragment : Fragment() {

    private val viewModel: EpiViewModel by viewModels()

    private var _binding: FragmentCadasEpiBinding? = null
    private val binding: FragmentCadasEpiBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadasEpiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Carregar a pessoa caso tenha selecionado
        arguments?.let {
            viewModel.loadEpi(it.getInt("idEpi"))
        }

        binding.btnCadastro.setOnClickListener {
            val ca = binding.edtCa.editableText.toString().toInt()
            val nomeEpi = binding.edtEpi.editableText.toString()
            val validade = binding.edtDateValidade.editableText.toString().toInt()
            val vencimento = binding.edtDateVencimento.editableText.toString()


            if (ca != 0 && nomeEpi != "" &&  validade != 0 && vencimento != "") {

                val epi = Epi(
                    nome_equipamento = nomeEpi,
                    numero_ca = ca,
                    tempo_uso = validade,
                    data_vencimento = vencimento
                )

                viewModel.epi.value?.let {
                    epi.id = it.id
                    viewModel.update(epi)
                } ?: run {
                    viewModel.insert(epi)
                }

                binding.edtCa.editableText.clear()
                binding.edtEpi.editableText.clear()
                binding.edtDateValidade.editableText.clear()
                binding.edtDateVencimento.editableText.clear()

                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "Digite os dados", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnDeletar.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Exclusão  de epi")
                .setMessage("Você realmente deseja excluir?")
                .setPositiveButton("Sim") { _, _ ->
                    viewModel.delete(viewModel.epi.value?.id ?: 0)
                    findNavController().navigateUp()
                }
                .setNegativeButton("Não") { _, _ -> }
                .show()
        }

        viewModel.epi.observe(viewLifecycleOwner) { epi ->
            binding.edtCa.setText(epi.numero_ca)
            binding.edtEpi.setText(epi.nome_equipamento)
            binding.edtDateValidade.setText(epi.tempo_uso)
            binding.edtDateVencimento.setText(epi.data_vencimento)

            if (Login.userAdmin) {
                binding.btnDeletar.visibility = View.VISIBLE
            } else {
                binding.edtCa.isClickable = false
                binding.edtEpi.isClickable = false
                binding.edtDateValidade.isClickable = false
                binding.edtDateVencimento.isClickable = false

                binding.btnCadastro.visibility = View.GONE
            }
        }
    }
}