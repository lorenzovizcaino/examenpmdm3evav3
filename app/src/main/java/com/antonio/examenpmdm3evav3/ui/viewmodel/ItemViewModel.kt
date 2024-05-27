package com.antonio.examenpmdm3evav3.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonio.examenpmdm3evav3.ui.model.ItemSer
import com.antonio.examenpmdm3evav3.ui.model.getListaAuxClass
import com.antonio.examenpmdm3evav3.ui.model.getListaSelecionadosClass

import com.antonio.examenpmdm3evav3.ui.model.getListaClass
import com.antonio.examenpmdm3evav3.ui.model.itemsSer
import com.antonio.examenpmdm3evav3.ui.model.itemsSerAux
import kotlinx.coroutines.launch

import java.io.EOFException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class ItemViewModel:ViewModel(){



    fun getLista():MutableList<ItemSer>{
        return  getListaClass()
    }

    fun getListaAux():MutableList<ItemSer>{
        return  getListaAuxClass()
    }

    fun getListaSelecionados():MutableList<ItemSer>{
        return  getListaSelecionadosClass()
    }



    var nombre by mutableStateOf("")
        private set



    var selecionado by mutableStateOf(false)
        private set

    var objeto by mutableStateOf(ItemSer("",false))
        private set

    var banderaFichero by mutableStateOf(true)
        private set

    var isCheckedScafold by mutableStateOf(false)
        private set
    var banderaIniciar by mutableStateOf(false)
        private set

    var indiceLista by mutableStateOf(0)
        private set



    val nombreArchivo="objeto_7.dat"

    fun set_nombre(nombre:String){
        this.nombre=nombre
    }



    fun set_selecionado(selecionado:Boolean){
        this.selecionado=selecionado
    }

    fun set_Objeto(objeto:ItemSer){
        this.objeto=objeto
    }
    fun set_banderaFichero(banderaFichero:Boolean){
        this.banderaFichero=banderaFichero
    }

    fun set_banderaIniciar(){
        viewModelScope.launch {
            banderaIniciar=!banderaIniciar
        }

    }
    fun set_isCheckedScafold(isCheckedScafold:Boolean){
        this.isCheckedScafold=isCheckedScafold
    }

    fun set_IndiceLista(indiceLista:Int){
        this.indiceLista=indiceLista
    }







//DESDE AQUI PARA GUARDAR EN FICHERO

    fun guardarListaEnFichero(context: Context) {
        var archivo = File(context.filesDir, nombreArchivo)
        if(!archivo.exists()){
            val objectOutputStream = ObjectOutputStream(FileOutputStream(archivo))
            var contador = 0
            itemsSer.forEach { item ->
                //contador++

                var itemSer = ItemSer(
                    item.nombre,item.selecionado
                )

                // Serializar objeto
                serializarObjeto(itemSer, objectOutputStream)
            }
            objectOutputStream.close()
        }
        else{
            leerItemArchivo(context)
        }

    }

    fun guardarItemEnFichero(context: Context, itemSer: ItemSer){
        try{
            var archivo = File(context.filesDir, nombreArchivo)

            val objectOutputStream = object : ObjectOutputStream(FileOutputStream(archivo,true)) {
                override fun writeStreamHeader() {}  //para no sobreescribir la cabecera del archivo
            }
            serializarObjeto(itemSer, objectOutputStream)
            objectOutputStream.close()
            println("Objeto agregado correctamente al archivo.")
        } catch (ex: IOException) {
            println("Error al escribir el objeto en el archivo: ${ex.message}")
        }

    }

    fun escribirFichero(context: Context, bandera: Boolean){
        var archivo = File(context.filesDir, nombreArchivo)



        val objectOutputStream = ObjectOutputStream(FileOutputStream(archivo))
        if(!bandera){
            itemsSer.clear()
            itemsSer.addAll(itemsSerAux)
            itemsSerAux.clear()

        }
        itemsSer.forEach(){ item->

            serializarObjeto(item, objectOutputStream)
        }
        objectOutputStream.close()
    }

    fun serializarObjeto(objeto: ItemSer, objectOutputStream: ObjectOutputStream) {
        objectOutputStream.writeObject(objeto)
    }

    fun leerItemArchivo(context: Context) {
        var archivo = File(context.filesDir, nombreArchivo)
        itemsSer.clear()
        deserializarObjeto(archivo)
        itemsSer.sortBy { it.nombre }

    }

    fun deserializarObjeto(archivo: File) {
        try {
            val objectInputStream = ObjectInputStream(FileInputStream(archivo))

            while (true) {
                try {
                    val itemSer = objectInputStream.readObject()
                    if (itemSer is ItemSer) {
                        itemsSer.add(itemSer)

                    } else {
                        break;
                    }

                } catch (ex: EOFException) {
                    break
                }
            }

            objectInputStream.close()
        } catch (ex: IOException) {
            println("Error al leer el archivo: ${ex.message}")
        } catch (ex: ClassNotFoundException) {
            println("Clase no encontrada: ${ex.message}")
        }


    }

//HASTA AQUI PARA GUARDAR EN FICHERO





}