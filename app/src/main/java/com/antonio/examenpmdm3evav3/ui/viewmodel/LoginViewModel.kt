package com.antonio.examenpmdm3evav3.ui.viewmodel

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.antonio.examenpmdm3evav3.ui.model.Login
import com.antonio.examenpmdm3evav3.ui.model.getListaLoginIntroducidosclass
import com.antonio.examenpmdm3evav3.ui.model.getListaLoginclass
import java.io.File

import java.text.DecimalFormat

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var loginenabled by mutableStateOf(false)
        private set



    var usuarioCorrecto by mutableStateOf(false)
        private set

    var contadorIntentos by mutableStateOf(3)
        private set




    var format = DecimalFormat("#,###.00")
        private set

    fun getListaLogin():MutableList<Login>{
        return  getListaLoginclass()
    }

    fun getListaLoginIntroducidos():MutableList<Login>{
        return  getListaLoginIntroducidosclass()
    }

    fun set_Email(email: String) {
        this.email = email
    }

    fun set_Password(password: String) {
        this.password = password
    }

    fun set_Loginenabled(loginenabled: Boolean) {
        this.loginenabled = Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 5
    }

    fun set_UsuarioCorrecto(usuarioCorrecto: Boolean){
        this.usuarioCorrecto=usuarioCorrecto;
    }

    fun set_ContadorIntentos(){
        this.contadorIntentos--
    }

    fun restablecerContadorIntentos(){
        this.contadorIntentos=3
    }

    fun grabarCambiosFich(context: Context) {
        val file = File(context.filesDir, "login.txt")
        file.delete()
        for (item in getListaLoginIntroducidos()) {
            file.appendText("${item.email}\n${item.password}\n")
        }
    }

    fun leerDatosFich(context: Context) {
        getListaLoginIntroducidos().clear()
        val file = File(context.filesDir, "login.txt")
        if (file.exists()) {
            val lista = file.readLines()
            var indice = 0
            while (indice < lista.size) {
                val itemNew = Login(lista.get(indice), lista.get(indice + 1))


                if (!getListaLoginIntroducidos().contains(itemNew)) {
                    getListaLoginIntroducidos().add(itemNew)


                }
                indice += 2
            }
        }
        else {
            Toast.makeText(context,"el fichero NO existe->"+file.path, Toast.LENGTH_SHORT).show()
        }
    }












}