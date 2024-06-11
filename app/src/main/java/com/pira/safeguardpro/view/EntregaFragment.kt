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
import com.pira.safeguardpro.databinding.FragmentEntregaBinding
import com.pira.safeguardpro.service.model.Emprestimo
import com.pira.safeguardpro.service.model.Funcionario
import com.pira.safeguardpro.viewmodel.EmprestimoViewModel
import com.pira.safeguardpro.viewmodel.FuncionarioViewModel


class EntregaFragment : Fragment() {

    private val viewModel: EmprestimoViewModel by viewModels()

    private var _binding: FragmentEntregaBinding? = null
    private val binding: FragmentEntregaBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEntregaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Carregar a pessoa caso tenha selecionado
        arguments?.let {
            viewModel.loadEmprestimo(it.getInt("idFuncionario"))
        }

        binding.btnEnviarEntrega.setOnClickListener {
            val entrega = binding.textEntrega.editableText.toString()
            val cpf = binding.textCpfEntrega.editableText.toString().toInt()
            val epi = binding.textEpi.editableText.toString()



            if (entrega != "" && cpf != 0 &&  epi != "" ) {

                val emprestimo: Emprestimo(
                    entrega = entrega,
                    cpf = cpf,
                    epi = epi,
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

                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "Digite os dados", Toast.LENGTH_LONG).show()
            }

        }

        viewModel.funcionario.observe(viewLifecycleOwner) { funcionario ->
            binding.textSenha.setText(funcionario.senha)
            binding.textEmail.setText(funcionario.email)
            binding.textNome.setText(funcionario.nome)
            binding.textCpf.setText(funcionario.cpf)
        }
    }
}

