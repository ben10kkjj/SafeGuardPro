package com.pira.safeguardpro.service.model

data class Epi(
    var id: Int = 0,
    var nome_equipamento: String = "",
    var numero_ca: Int = 0,
    var tempo_uso: Int = 0,
    var data_vencimento: String = ""
)