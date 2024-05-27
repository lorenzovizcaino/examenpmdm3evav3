package com.antonio.examenpmdm3evav3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.antonio.examenpmdm3evav3.ui.screens.ListaLogin
import com.antonio.examenpmdm3evav3.ui.screens.Menu
import com.antonio.examenpmdm3evav3.ui.viewmodel.ItemViewModel
import com.antonio.examenpmdm3evav3.ui.viewmodel.LoginViewModel


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModelLogin=remember{LoginViewModel()}
    val viewModelObjeto=remember{ ItemViewModel() }
    NavHost(navController, startDestination = Screens.Menu.route) {
        //pantalla principal con la navegación
        composable(route = Screens.Menu.route) {
            Menu(navController, viewModelObjeto, viewModelLogin) }

        composable(route = Screens.ListaLogin.route) {
            ListaLogin(navController, viewModelLogin) //Nombre de la función composable a la que navegar
        }
//        composable(route = Screens.ResumenCompras.route) {
//            ResumenCompras(navController,viewModelProducto) //Nombre de la función composable a la que navegar
//        }
//        composable(route = Screens.Registro.route) {
//            Registro(navController,viewModelLogin) //Nombre de la función composable a la que navegar
//        }
//        composable(route = Screens.ListaEmail.route) {
//            ListaEmail(navController,viewModelLogin) //Nombre de la función composable a la que navegar
//        }



    }
}








