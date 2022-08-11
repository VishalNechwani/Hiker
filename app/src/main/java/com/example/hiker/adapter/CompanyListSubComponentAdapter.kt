package com.example.hiker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hiker.R
import com.example.hiker.utils.Component

 class CompanyListSubComponentAdapter(val componentArr:List<Component>) : RecyclerView.Adapter<CompanyListSubComponentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompanyListSubComponentAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.component_arr_layout, parent, false)
        return CompanyListSubComponentAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CompanyListSubComponentAdapter.ViewHolder,
        position: Int
    ) {
        holder.componentName.text = componentArr[position].namer
        holder.componentValue.text = componentArr[position].valuer
    }

    override fun getItemCount(): Int {
        return componentArr.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val componentName: TextView = itemView.findViewById(R.id.component_name_in_company_list)
        val componentValue: TextView = itemView.findViewById(R.id.component_value_in_company_list)
    }
}