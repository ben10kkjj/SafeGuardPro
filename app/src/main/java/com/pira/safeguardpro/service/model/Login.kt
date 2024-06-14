package com.pira.safeguardpro.service.model

object Login {
    var userId = 0
    var userCpf = 0
    var userAdmin = false

    fun userConected(id: Int, cpf: Int, admin: Boolean) {
        userId = id
        userCpf = cpf
        userAdmin = admin
    }
}