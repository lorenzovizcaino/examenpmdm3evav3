package com.antonio.examenpmdm3evav3.ui.model

import java.io.Serializable

data class Login(
    var email:String,
    var password:String
):Serializable

var listaLogin= mutableListOf<Login>(
    Login("lorenzovizcaino@gmail.com","123QWEasd"),
    Login("juanvaldes@gmail.com","juanvaldes"),
    Login("anacamino@yahoo.es","anacamino")

)

var listaLoginIntroducidos= mutableListOf<Login>(

)

fun getListaLoginclass(): MutableList<Login> {
    return listaLogin
}

fun getListaLoginIntroducidosclass(): MutableList<Login> {
    return listaLoginIntroducidos
}
