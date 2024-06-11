package com.pira.safeguardpro.service.model

data class Emprestimo(
    var id: Int = 0,
    var codigo_funcionario: Int = 0,
    var numero_ca: Int = 0,
    var nome_epi: String = "",
    var data_entrega: String = "",
)
