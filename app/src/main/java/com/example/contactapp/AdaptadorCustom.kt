package com.example.contactapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class AdaptadorCustom(var context: Context, items:ArrayList<Contact>): BaseAdapter() {

    var items:ArrayList<Contact>?= null
    var copiaItems:ArrayList<Contact>? = null

    init {
        this.items = ArrayList(items)
        this.copiaItems = items
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder:ViewHolder?
        var vista:View? = convertView

        if(vista == null){
            vista = LayoutInflater.from(context).inflate(R.layout.template_contacto,  null)
            viewHolder = ViewHolder(vista)
            vista.tag = viewHolder
        }else{
            viewHolder= vista.tag as? ViewHolder
        }

        val item = getItem(position) as Contact

        viewHolder?.nombre?.text = item.nombre + " " + item.apellidos
        viewHolder?.estado?.text = item.estado
        viewHolder?.numero?.text = item.telefono
        viewHolder?.foto?.setImageResource(item.foto)

        return vista!!
    }

    override fun getCount(): Int {
        return this.items?.count()!!
    }

    fun filtrar(str:String){
        items?.clear()

        if(str.isEmpty()){
            items = ArrayList(copiaItems!!)
            notifyDataSetChanged()
            return
        }

        var busqueda = str
        busqueda = busqueda.toLowerCase()

        for(item in copiaItems!!){
            val nombre = item.nombre.toLowerCase()

            if(nombre.contains(busqueda)){
                items?.add(item)
            }
        }

        notifyDataSetChanged()
    }

    fun addItem(item:Contact){
        copiaItems?.add(item)
        items = ArrayList(copiaItems!!)
        notifyDataSetChanged()
    }

    fun removeItem(index:Int){
        copiaItems?.removeAt(index)
        items = ArrayList(copiaItems!!)
        notifyDataSetChanged()
    }

    fun updateItem(index:Int, newItem:Contact){
        copiaItems?.set(index, newItem)
        items = ArrayList(copiaItems!!)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Any {
        return this.items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private class ViewHolder(vista:View){
        var nombre: TextView? = null
        var foto: ImageView? = null
        var estado:TextView? = null
        var numero:TextView? = null
        init {
            this.nombre = vista.findViewById(R.id.tvDireccion)
            this.estado = vista.findViewById(R.id.tvEstado)
            this.numero = vista.findViewById(R.id.tvNumero)
            this.foto = vista.findViewById(R.id.ivPhoto)
        }
    }
}