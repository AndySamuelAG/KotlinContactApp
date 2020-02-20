package com.example.contactapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class Detalle : AppCompatActivity() {
    var index = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var actionBar= supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        index = intent.getStringExtra("ID").toInt()
//        Log.d("Index", index.toString())

        mapearDatos()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalle, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.iEditar -> {
                val intent = Intent(this, Nuevo::class.java)
                intent.putExtra("ID", index.toString())
                startActivity(intent)
                return true
            }

            R.id.iEliminar -> {
                MainActivity.eliminarContacto(index)
                Toast.makeText(this, "El contacto ha sido eliminado", Toast.LENGTH_LONG).show()
                finish()
                return true
            }

            else->{
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun mapearDatos(){
        val contacto = MainActivity.obtenerContacto(index)

        val nombre = findViewById<TextView>(R.id.tvNombre)
        val estado = findViewById<TextView>(R.id.tvEstado)
        val edad = findViewById<TextView>(R.id.tvEdad)
        val peso = findViewById<TextView>(R.id.tvPeso)
        val telefono = findViewById<TextView>(R.id.tvTelefono)
        val email = findViewById<TextView>(R.id.tvEmail)
        val direccion = findViewById<TextView>(R.id.tvDireccion)
        val ivFoto = findViewById<ImageView>(R.id.ivPhoto)

        nombre.text = contacto.nombre + " " + contacto.apellidos
        estado.text = contacto.estado
        edad.text = contacto.edad.toString() + " años"
        peso.text = contacto.peso.toString() + " Kg"
        telefono.text = contacto.telefono
        email.text = contacto.email
        direccion.text = contacto.direccion
        ivFoto.setImageResource(contacto.foto)
    }

    override fun onResume() {
        super.onResume()
        mapearDatos()
    }
}
