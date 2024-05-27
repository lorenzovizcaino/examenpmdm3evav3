package com.antonio.examenpmdm3evav3.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.antonio.examenpmdm3evav3.ui.model.Login
import com.antonio.examenpmdm3evav3.ui.viewmodel.LoginViewModel

@Composable
fun ListaLogin(navController: NavHostController, viewModelLogin: LoginViewModel) {
    var context = LocalContext.current
    viewModelLogin.leerDatosFich(context)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Listado de email´s",
            color = Color.Blue,
            fontSize = 20.sp)
        LazyColumn(modifier = Modifier.padding(15.dp)) {
            items(viewModelLogin.getListaLoginIntroducidos()) {
                MostrarDatos(viewModelLogin, it)
            }
        }

    }

}

@Composable
fun MostrarDatos(viewModelLogin: LoginViewModel, it: Login) {


    Text(text = "Email: ${it.email}")
    Text(text = "Password: ${it.password}")

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .width(4.dp)
            .padding(5.dp),
        color = Color.Black


    )

}
