package com.example.hiker.adapter

import android.icu.text.NumberFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hiker.R
import com.example.hiker.utils.Component
import java.math.BigDecimal
import java.util.*

class ComponentListAdapter(val componentList : List<Component>) : RecyclerView.Adapter<ComponentListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComponentListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.component_list_in_show_fragment, parent, false)
        return ComponentListAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComponentListAdapter.ViewHolder, position: Int) {
       holder.componentName.text = componentList.get(position).namer
       holder.componentValue.text = currencyFormat(componentList.get(position).valuer)
    }

    override fun getItemCount(): Int {
       return componentList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val componentName: TextView = itemView.findViewById(R.id.component_name)
        val componentValue: TextView = itemView.findViewById(R.id.component_value)
    }

    fun currencyFormat(value:String):String{
        val format = NumberFormat.getCurrencyInstance(Locale("en","in"))
        return format.format(BigDecimal(value))
    }
}