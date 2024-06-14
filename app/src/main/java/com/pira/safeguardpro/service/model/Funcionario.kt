package com.pira.safeguardpro.service.model

data class Funcionario(
    var id: Int = 0,
    var nome: String = "",
    var cpf: Int = 0,
    var email: String = "",
    var senha: String = "",
    var admin: Boolean = false,
)