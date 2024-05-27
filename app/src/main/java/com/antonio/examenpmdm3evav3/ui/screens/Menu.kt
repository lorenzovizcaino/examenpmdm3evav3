package com.antonio.examenpmdm3evav3.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.antonio.examenpmdm3evav3.R
import com.antonio.examenpmdm3evav3.ui.model.ItemSer
import com.antonio.examenpmdm3evav3.ui.model.Login
import com.antonio.examenpmdm3evav3.ui.navigation.Screens
import com.antonio.examenpmdm3evav3.ui.viewmodel.ItemViewModel
import com.antonio.examenpmdm3evav3.ui.viewmodel.LoginViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Contenido(
    navController: NavController,
    viewModel: ItemViewModel,
    viewModelLogin: LoginViewModel
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            MyTopBar(navController, viewModel , viewModelLogin)

        },
        bottomBar = {
            MyBottomBar(viewModel)

        }
    ) {// (1)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it) // (3)
            ,
            verticalArrangement = Arrangement.Top
        ) {

            AdministrarItems(navController,viewModel)
        }
    }
}



@Composable
fun Menu(navController: NavController, viewModelItem: ItemViewModel, viewModelLogin: LoginViewModel) {
    if(viewModelItem.banderaIniciar){
        Contenido(navController,viewModelItem, viewModelLogin)
    }else{
        MenuLogin(navController, viewModelLogin, viewModelItem)
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(navController: NavController, viewModel: ItemViewModel, viewModelLogin: LoginViewModel) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            IconButton(onClick = {
                viewModelLogin.set_Email("")
                viewModelLogin.set_Password("")
                viewModelLogin.restablecerContadorIntentos()
                viewModel.set_banderaIniciar()
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Ir hacia atras",

                    )
            }
        },
        title = { Text(text = viewModelLogin.email, fontSize = 16.sp) },
        actions = {

            IconButton(onClick = {
                viewModel.getListaSelecionados().clear()
                viewModel.getLista().forEach {

                    if(it.selecionado){
                        viewModel.getListaSelecionados().add(it)
                    }
                }
                showDialog=true

            }) {
                Icon(imageVector = Icons.Filled.Android,
                    contentDescription = "Ir hacia atras",

                    )
            }
            if(showDialog){
                MyDialogMostrarItems(
                    viewModel,
                    onDismiss = { showDialog = false },
                    onAccept = {  showDialog = false },




                )
            }

            Checkbox(checked = viewModel.isCheckedScafold,
                onCheckedChange = {
                    viewModel.set_isCheckedScafold(it)

                    viewModel.getLista().forEach { item ->
                        item.selecionado = viewModel.isCheckedScafold
                        viewModel.set_Objeto(
                            ItemSer(
                                item.nombre,
                                item.selecionado,

                                )
                        )


                        viewModel.getListaAux().add(viewModel.objeto)

                    }

                    viewModel.escribirFichero(context,false)
                    navController.navigate(route = Screens.Menu.route)

                })
        }
    )
}

@Composable
fun MyDialogMostrarItems(viewmodel: ItemViewModel,onDismiss: () -> Unit, onAccept: () -> Unit) {
    val colorRojo=Color(232, 18, 36)
    val colorAzul=Color(10, 48, 100)
    val colorAmarillo = Color(235, 203, 73)
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Listado de Items selecionados")
        },
        text = {

            LazyColumn(){
                    items(viewmodel.getListaSelecionados()){
                        Column(modifier=Modifier.fillMaxSize()){
                            Text("Nombre: ${it.nombre}")

                            Spacer(modifier = Modifier.size(10.dp))
                        }

                    }
            }

        },
        confirmButton = {
            Button(
                onClick = {
                    onAccept()
                },
                colors = ButtonDefaults.buttonColors(containerColor =colorRojo),
                shape = RectangleShape

            ) {
                Text("Aceptar")
            }
        },

    )


}


@Composable
fun MyBottomBar(viewModel: ItemViewModel) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        Text(text = "Numero de elementos: ")
        Text(text = viewModel.getLista().size.toString())
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdministrarItems(navController: NavController, viewModel: ItemViewModel) {
    val context = LocalContext.current

    if(viewModel.banderaFichero){
        viewModel.guardarListaEnFichero(context)
        viewModel.set_banderaFichero(false)
    }

    Column(

    ) {
        OutlinedTextField(value = viewModel.nombre,
            onValueChange = { viewModel.set_nombre(it)},
            label = { Text("Nombre de item") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        
        Button(onClick = {


            val nuevoItemSer = ItemSer(viewModel.nombre,  viewModel.selecionado)
            viewModel.getLista().add(nuevoItemSer)

            val path = context.getFilesDir()


            viewModel.guardarItemEnFichero(context,nuevoItemSer)

            viewModel.set_nombre("")
            viewModel.set_descr("")
        }, modifier = Modifier.fillMaxWidth().padding(5.dp)) {
            Text(text = "Agregar", /*modifier = Modifier.fillMaxWidth()*/)
        }




            LazyColumn(){
                items(viewModel.getLista()){
                    MostrarItem(
                        viewModel=viewModel,
                        objeto=it ,
                        editNombre = { viewModel.set_nombre(it) },
                        editDescr = { viewModel.set_descr(it) }
                    )
                }
            }

    }
}




@Composable
fun MostrarItem(
    objeto: ItemSer,
    editNombre: (String) -> Unit,
    editDescr: (String) -> Unit,
    viewModel: ItemViewModel
) {
    val context = LocalContext.current
    var isChecked by remember { mutableStateOf(objeto.selecionado) }
    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(){
            Text(text = "Nombre: "+objeto.nombre)

            Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Checkbox(checked = isChecked, onCheckedChange = {
                    isChecked=it
                    objeto.selecionado=isChecked
                    viewModel.set_IndiceLista(viewModel.getLista().indexOf(objeto))
                    if (isChecked) {

                        viewModel.getLista()[viewModel.indiceLista].selecionado=true
                        viewModel.escribirFichero(context, true)

                    } else {

                        viewModel.getLista()[viewModel.indiceLista].selecionado=false
                        viewModel.escribirFichero(context, true)

                    }


                })

                IconButton(onClick = {
                    showDialog=true

                }, modifier = Modifier.padding(start = 10.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Borrar",
                        tint = Color.Red,


                        )
                }

                IconButton(
                    onClick = {
                        Toast.makeText(context, "Modifica Datos Contacto seleccionado.", Toast.LENGTH_SHORT).show()
                        viewModel.getLista().remove(objeto)
                        viewModel.escribirFichero(context, true)
                        editNombre(objeto.nombre)


                    },
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar",
                        tint = Color.Black,

                        )
                }





            }

        }










        if(showDialog){
            MyDialogBorrarItem(
                onDismiss = { showDialog = false },
                onAccept = {

                    viewModel.getLista().remove(objeto)
                    viewModel.escribirFichero(context, true)
                    showDialog = false


                }
            )





        }













    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .width(4.dp), color = Color.Black
    )

}


@Composable
fun MyDialogBorrarItem(onDismiss: () -> Unit, onAccept: () -> Unit) {
    val colorRojo=Color(232, 18, 36)
    val colorAzul=Color(10, 48, 100)
    val colorAmarillo = Color(235, 203, 73)
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Borrar Item")
        },
        text = {
            Text(text = "¿Estás seguro de que deseas Borrar este Item?")
        },
        confirmButton = {
            Button(
                onClick = {
                    onAccept()
                },
                colors = ButtonDefaults.buttonColors(containerColor =colorRojo),
                shape = RectangleShape

            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor =colorAzul),
                shape = RectangleShape
            ) {
                Text("Cancelar")
            }
        }
    )
}

//Empieza aqui  los composables del login



@Composable
fun MenuLogin(navController: NavController, viewModel: LoginViewModel, viewModelItem: ItemViewModel) {

    Box(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Header(Modifier.align(Alignment.TopEnd))
        Body(
            Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),viewModel,navController,viewModelItem
        )
        Footer(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            navController
        )
    }

}


@Composable
fun Footer(modifier: Modifier, navController: NavController) {
    Column(modifier = modifier) {
        Divider(Modifier.background(Color(0xFFF9F9F9)))
        Spacer(modifier = Modifier.size(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

            Text(
                text = "Ver emails introducidos",
                color = Color(0xFF4EA8E9),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    navController.navigate(Screens.ListaLogin.route)
                })

        }
        Spacer(modifier = Modifier.size(25.dp))


    }

}

@Composable
fun Body(
    modifier: Modifier,
    viewModel: LoginViewModel,
    navController: NavController,
    viewModelItem: ItemViewModel
) {

    Column(modifier = modifier) {
        ImageLogo(modifier)
        Spacer(modifier = Modifier.size(16.dp))
        Email(viewModel.email, { viewModel.set_Email(it)

            viewModel.set_Loginenabled(viewModel.loginenabled)
        }, modifier)
        Spacer(modifier = Modifier.size(4.dp))
        Password(viewModel.password, { viewModel.set_Password(it)

            viewModel.set_Loginenabled(viewModel.loginenabled)
        }, modifier)
        Spacer(modifier = Modifier.size(8.dp))
        ForgotPassword()
        Spacer(modifier = Modifier.size(16.dp))

        LoginButton(viewModel, navController, modifier,viewModelItem)
        Spacer(modifier = Modifier.size(16.dp))




    }

}





@Composable
fun LoginButton(
    viewModel: LoginViewModel,
    navController: NavController,
    modifier: Modifier,
    viewModelItem: ItemViewModel
) {
    var context= LocalContext.current
    Button(
        onClick = {
            if(viewModel.contadorIntentos>0){

                viewModel.getListaLoginIntroducidos().add(Login(viewModel.email,viewModel.password))
                viewModel.grabarCambiosFich(context)

                viewModel.set_ContadorIntentos()
                viewModel.getListaLogin().forEach{ item ->
                    if(item.email.equals(viewModel.email) && item.password.equals(viewModel.password)){
                        viewModel.set_UsuarioCorrecto(true)
                    }


                }
                if(viewModel.usuarioCorrecto){

                    viewModel.set_UsuarioCorrecto(false)
                    viewModelItem.set_banderaIniciar()



                }else{

                    Toast.makeText(context,"Usuario no recococido, te quedan ${viewModel.contadorIntentos} intentos",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context,"Has agotado los intentos paara logearte",Toast.LENGTH_SHORT).show()
            }



        },
        enabled = viewModel.loginenabled,
        modifier = modifier,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            disabledContentColor = Color.White
        )
    ){
        Text(text = "Log in")
    }

}



@Composable
fun ForgotPassword() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Forgot password?",
        textAlign = TextAlign.End,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = Color(0xFF4EA8E9)
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Password(password: String, onTextChanged: (String) -> Unit, modifier: Modifier) {
    var passwordVisibility by rememberSaveable {
        mutableStateOf(false)
    }
    TextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = modifier,
        placeholder = { Text(text = "Password") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.textFieldColors(

            textColor = Color(0xFFB2B2B2),
            focusedIndicatorColor =  Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent

        ),
        trailingIcon = {
            val image = if(passwordVisibility){
                Icons.Filled.VisibilityOff
            }else{
                Icons.Filled.Visibility
            }
            IconButton(onClick = {passwordVisibility=!passwordVisibility}) {
                Icon(imageVector = image, contentDescription ="show Password" )

            }
        },
        visualTransformation = if(passwordVisibility) {
            VisualTransformation.None
        }else{
            PasswordVisualTransformation()
        }

    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Email(email: String, onTextChanged: (String) -> Unit, modifier: Modifier) {
    TextField(
        value = email,
        onValueChange = { onTextChanged(it) },
        modifier = modifier,
        placeholder = { Text(text = "Email") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.textFieldColors(

            textColor = Color(0xFFB2B2B2),
            focusedIndicatorColor =  Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent

        )
    )
}

@Composable
fun ImageLogo(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.examen),
        contentDescription = "logo",
        modifier = modifier.size(350.dp)
    )
}

@Composable
fun Header(modifier: Modifier) {
    val activity = LocalContext.current as Activity
    //En Android normal cuando queremos cerrar una aplicacion se pone "finish"
    //en este caso entonces  se coge el contexto y se convierte a Activity y asi si nos deja utilizar el "finish"

    Icon(
        imageVector = Icons.Default.Close,
        contentDescription = "close app",
        modifier = modifier.clickable { activity.finish() })
}
