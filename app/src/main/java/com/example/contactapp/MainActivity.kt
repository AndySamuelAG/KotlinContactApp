package com.example.contactapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    var lista:ListView? = null
    var grid:GridView? = null
    var viewSwitcher:ViewSwitcher? = null

    companion object{
        var contactos:ArrayList<Contact>? = null
        var adaptador:AdaptadorCustom? = null
        var adaptadorGrid:AdaptadorCustomGrid? = null

        fun addContact(contacto:Contact){
            adaptador?.addItem(contacto)
            adaptadorGrid?.addItem(contacto)
        }
        fun obtenerContacto(index:Int):Contact{
            return adaptador?.getItem(index) as Contact
        }

        fun eliminarContacto(index:Int){
            adaptador?.removeItem(index)
            adaptadorGrid?.removeItem(index)
        }

        fun actualizarContacto(index:Int, nuevoContacto: Contact){
            adaptador?.updateItem(index, nuevoContacto)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        contactos = ArrayList()
        contactos?.add(Contact("Andy", "Alvarado", "Ocupado", 22, 70.0F, "Ladire 223", "133556443", "andy@yopmail.com", R.drawable.foto_01))
        contactos?.add(Contact("Samuel", "Cruz", "Viajando", 32, 77.20F, "Direccion 223", "21354674", "an434@yopmail.com", R.drawable.foto_02))
        contactos?.add(Contact("Mario", "Castaño", "Holap. :)", 21, 54.17F, "Jejeje 223", "11233214", "mario@yopmail.com", R.drawable.foto_03))

        lista = findViewById<ListView>(R.id.lista)
        grid = findViewById<GridView>(R.id.grid)
        adaptador = AdaptadorCustom(this, contactos!!)
        adaptadorGrid = AdaptadorCustomGrid(this, contactos!!)
        viewSwitcher = findViewById(R.id.switch_view)

        lista?.adapter = adaptador
        grid?.adapter = adaptadorGrid

        lista?.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, Detalle::class.java)
            intent.putExtra("ID", position.toString())
            startActivity(intent)
        }
        grid?.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, Detalle::class.java)
            intent.putExtra("ID", position.toString())
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val itemSwitch = menu?.findItem(R.id.switch_view)
        itemSwitch?.setActionView(R.layout.switch_item)
        val switchView = itemSwitch?.actionView?.findViewById<Switch>(R.id.sCambiaVista)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itemBusqueda = menu?.findItem(R.id.searchView)
        val searchView = itemBusqueda?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.queryHint = "Buscar contacto..."
        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->

        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText:String?):Boolean{
                //filtrar
                adaptador?.filtrar(newText!!)
                adaptadorGrid?.filtrar(newText!!)
                return true
            }
            override fun onQueryTextSubmit(query:String?):Boolean{
                //filtrar
                return true
            }
        })

        switchView?.setOnCheckedChangeListener { buttonView, isChecked ->
            viewSwitcher?.showNext()
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.iNuevo ->{
             val intent = Intent(this, Nuevo::class.java)
                startActivity(intent)
                return true
            }

            else->{
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        adaptador?.notifyDataSetChanged()
    }
}
