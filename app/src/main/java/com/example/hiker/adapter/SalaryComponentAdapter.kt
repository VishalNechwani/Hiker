package com.example.hiker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hiker.R
import com.example.hiker.model.HikeEntity
import com.example.hiker.utils.Component

class SalaryComponentAdapter(val component:ArrayList<Component>) : RecyclerView.Adapter<SalaryComponentAdapter.ViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SalaryComponentAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.salarycomponentcardlist, parent, false)
        return SalaryComponentAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SalaryComponentAdapter.ViewHolder, position: Int) {
        holder.componentName.text = component.get(position).namer
        holder.componentValue.text = component.get(position).valuer
    }

    override fun getItemCount(): Int {
        return component.size
    }

    fun adapterUpdate(componentUpdate : Component){
        component.add(componentUpdate)
        notifyDataSetChanged()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val componentName: TextView = itemView.findViewById(R.id.component_name)
        val componentValue: TextView = itemView.findViewById(R.id.component_value)
    }

}