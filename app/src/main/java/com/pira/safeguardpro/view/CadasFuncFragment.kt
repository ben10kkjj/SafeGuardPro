package com.pira.safeguardpro.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pira.safeguardpro.databinding.FragmentCadasFuncionarioBinding
import com.pira.safeguardpro.service.model.Epi
import com.pira.safeguardpro.service.model.Funcionario
import com.pira.safeguardpro.service.model.Login
import com.pira.safeguardpro.viewmodel.EpiViewModel
import com.pira.safeguardpro.viewmodel.FuncionarioViewModel

class CadasFuncFragment : Fragment( ){
    private val viewModel: FuncionarioViewModel by viewModels()

    private var _binding: FragmentCadasFuncionarioBinding? = null
    private val binding: FragmentCadasFuncionarioBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadasFuncionarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Carregar a pessoa caso tenha selecionado
        arguments?.let {
            viewModel.loadFuncionario(it.getInt("idFuncionario"))
        }

        binding.btnCadastrar.setOnClickListener {
            val nome = binding.textNome.editableText.toString()
            val cpf = binding.textCpf.editableText.toString().toInt()
            val email = binding.textEmail.editableText.toString()
            val senha = binding.textSenha.editableText.toString()
            val admin = binding.chkAdmin.isChecked


            if (nome != "" && cpf != 0 &&  email != "" && senha != "") {

                val funcionario = Funcionario(
                    nome = nome,
                    cpf = cpf,
                    email = email,
                    senha = senha,
                     admin = admin
                )

                viewModel.funcionario.value?.let {
                    funcionario.id = it.id
                    viewModel.update(funcionario)
                } ?: run {
                    viewModel.insert(funcionario)
                }

                binding.textCpf.editableText.clear()
                binding.textEmail.editableText.clear()
                binding.textNome.editableText.clear()
                binding.textSenha.editableText.clear()
                binding.chkAdmin.isChecked = false

                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "Digite os dados", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnDeletar.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Exclusão  de funcionario")
                .setMessage("Você realmente deseja excluir?")
                .setPositiveButton("Sim") { _, _ ->
                    viewModel.delete(viewModel.funcionario.value?.id ?: 0)
                    findNavController().navigateUp()
                }
                .setNegativeButton("Não") { _, _ -> }
                .show()
        }


        viewModel.funcionario.observe(viewLifecycleOwner) { funcionario ->
            binding.textSenha.setText(funcionario.senha)
            binding.textEmail.setText(funcionario.email)
            binding.textNome.setText(funcionario.nome)
            binding.textCpf.setText(funcionario.cpf)

            if (Login.userAdmin) {
                binding.btnDeletar.visibility = View.VISIBLE
            } else {
                binding.textSenha.isClickable = false
                binding.textEmail.isClickable = false
                binding.textNome.isClickable = false
                binding.textCpf.isClickable = false

                binding.btnCadastrar.visibility = View.GONE
                binding.textSenha.visibility = View.GONE
                binding.chkAdmin.visibility = View.GONE
            }
        }
    }
}