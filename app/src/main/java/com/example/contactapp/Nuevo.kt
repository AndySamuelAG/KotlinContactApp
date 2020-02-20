package com.example.contactapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar

class Nuevo : AppCompatActivity() {
    var fotoIndex:Int = 0

    val fotos = arrayOf(R.drawable.foto_01, R.drawable.foto_02, R.drawable.foto_03, R.drawable.foto_04, R.drawable.foto_05, R.drawable.foto_06)

    var foto : ImageView? = null

    var index: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var actionBar= supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        foto = findViewById<ImageView>(R.id.ivPhoto)

        foto?.setOnClickListener(){
            seleccionarFoto()
        }

        if(intent.hasExtra("ID")){
            index = intent.getStringExtra("ID").toInt()
            rellenarDatos(index)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nuevo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            android.R.id.home -> {
                finish()
                return true
            }

            R.id.iCrearNuevo ->{
                val nombre = findViewById<EditText>(R.id.tvNombre)
                val apellidos = findViewById<EditText>(R.id.tvApellidos)
                val estado = findViewById<EditText>(R.id.tvEstado)
                val edad = findViewById<EditText>(R.id.tvEdad)
                val peso = findViewById<EditText>(R.id.tvPeso)
                val telefono = findViewById<EditText>(R.id.tvTelefono)
                val email = findViewById<EditText>(R.id.tvEmail)
                val direccion = findViewById<EditText>(R.id.tvDireccion)

                //Validar campos
                var campos = ArrayList<String>()
                campos.add(nombre.text.toString())
                campos.add(apellidos.text.toString())
                campos.add(estado.text.toString())
                campos.add(edad.text.toString())
                campos.add(peso.text.toString())
                campos.add(direccion.text.toString())
                campos.add(telefono.text.toString())
                campos.add(email.text.toString())

                var flag = 0
                for(campo in campos){
                    if(campo.isNullOrEmpty())
                        flag++
                }

                if(flag>0){
                    Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                }else{
                    if(index > -1){
                        MainActivity.actualizarContacto(index, Contact(campos.get(0), campos.get(1), campos.get(2), campos.get(3).toInt(), campos.get(4).toFloat(), campos.get(5), campos.get(6), campos.get(7), obtenerFoto(fotoIndex)))
                    }else{
                        MainActivity.addContact(Contact(campos.get(0), campos.get(1), campos.get(2), campos.get(3).toInt(), campos.get(4).toFloat(), campos.get(5), campos.get(6), campos.get(7), obtenerFoto(fotoIndex)))
                    }
                    finish()
                }
                return true
            }
            else->{
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun seleccionarFoto(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona imagen de perfil")

        val adaptadorDialogo = ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item)
        adaptadorDialogo.add("Foto 01")
        adaptadorDialogo.add("Foto 02")
        adaptadorDialogo.add("Foto 03")
        adaptadorDialogo.add("Foto 04")
        adaptadorDialogo.add("Foto 05")
        adaptadorDialogo.add("Foto 06")

        builder.setAdapter(adaptadorDialogo){
            dialog, which ->
            fotoIndex = which
            foto?.setImageResource(obtenerFoto(fotoIndex))

        }

        builder.setNegativeButton("Cancelar"){
                dialog, wich->
            dialog.dismiss()
        }

        builder.show()
    }

    fun obtenerFoto(index:Int):Int{
        return fotos.get(index)
    }

    fun rellenarDatos(index:Int){
        val contacto = MainActivity.obtenerContacto(index)

        val nombre = findViewById<TextView>(R.id.tvNombre)
        val apellidos = findViewById<TextView>(R.id.tvApellidos)
        val estado = findViewById<TextView>(R.id.tvEstado)
        val edad = findViewById<TextView>(R.id.tvEdad)
        val peso = findViewById<TextView>(R.id.tvPeso)
        val telefono = findViewById<TextView>(R.id.tvTelefono)
        val email = findViewById<TextView>(R.id.tvEmail)
        val direccion = findViewById<TextView>(R.id.tvDireccion)
        val ivFoto = findViewById<ImageView>(R.id.ivPhoto)

        nombre.setText(contacto.nombre, TextView.BufferType.EDITABLE)
        apellidos.setText(contacto.apellidos, TextView.BufferType.EDITABLE)
        estado.setText(contacto.estado, TextView.BufferType.EDITABLE)
        edad.setText(contacto.edad.toString(), TextView.BufferType.EDITABLE)
        peso.setText(contacto.peso.toString(), TextView.BufferType.EDITABLE)
        telefono.setText(contacto.telefono, TextView.BufferType.EDITABLE)
        email.setText(contacto.email, TextView.BufferType.EDITABLE)
        direccion.setText(contacto.direccion, TextView.BufferType.EDITABLE)

        ivFoto.setImageResource(contacto.foto)

        var posicion = 0
        for(foto in fotos){
            if(contacto.foto == foto){
                fotoIndex = posicion
            }
            posicion++
        }

    }
}
