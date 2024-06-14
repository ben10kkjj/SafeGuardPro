package com.pira.safeguardpro.view

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
import com.pira.safeguardpro.service.model.Epi
import com.pira.safeguardpro.service.model.Funcionario
import com.pira.safeguardpro.viewmodel.EmprestimoViewModel
import com.pira.safeguardpro.viewmodel.EpiViewModel
import com.pira.safeguardpro.viewmodel.FuncionarioViewModel
import java.time.LocalDateTime


class EntregaFragment : Fragment() {

    private val viewModel: EmprestimoViewModel by viewModels()
    private val viewModelFuncionario: FuncionarioViewModel by viewModels()
    private val viewModelEpi: EpiViewModel by viewModels()

    private lateinit var epiByCa: Epi
    private lateinit var funcionarioByCpf: Funcionario

    private var _binding: FragmentEntregaBinding? = null
    private val binding: FragmentEntregaBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntregaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Carregar a pessoa caso tenha selecionado
        arguments?.let {
            viewModel.loadEmprestimo(it.getInt("idEntrega"))
        }

        binding.btnEnviarEntrega.setOnClickListener {
            val cpf = binding.textCpfEntrega.editableText.toString().toInt()
            val epi = binding.textEpi.editableText.toString()
            val data_entrega = LocalDateTime.now().toLocalDate().toString()

            if (cpf != 0 &&  epi != "" ) {

                val emprestimo = Emprestimo(
                    codigo_funcionario = cpf,
                    numero_ca = epiByCa.numero_ca,
                    nome_epi = epiByCa.nome_equipamento,
                    data_entrega = data_entrega
                )

                viewModel.emprestimo.value?.let {
                    emprestimo.id = it.id
                    viewModel.update(emprestimo)

                } ?: run {
                    viewModel.insert(emprestimo)
                }

                binding.textCpfEntrega.editableText.clear()
                binding.textEpi.editableText.clear()
                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "Digite os dados", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.emprestimo.observe(viewLifecycleOwner) {
        }

        viewModelEpi.epi.observe(viewLifecycleOwner) {
            epiByCa = it
            binding.textEpi.setText(it.nome_equipamento)
        }

        viewModelFuncionario.funcionario.observe(viewLifecycleOwner) {
            funcionarioByCpf = it
            binding.textCpfEntrega.setText(it.cpf)
        }
    }
}