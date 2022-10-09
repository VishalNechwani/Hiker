package com.example.hiker.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hiker.R
import com.example.hiker.model.HikeEntity
import com.example.hiker.utils.Component

class SalaryComponentAdapter(val component:ArrayList<Component>,val companyListCallBack: SalaryComponentCallBack) : RecyclerView.Adapter<SalaryComponentAdapter.ViewHolder>(){

    var isEnable = false
    var positionHikeArr : ArrayList<Int> =  ArrayList()
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
        holder.card.isLongClickable = true
        holder.card.setOnLongClickListener {
            if (!isEnable)
            {
                clickItemShadowing(holder)
                companyListCallBack.showDeleteIcon()
                isEnable = true
                positionHikeArr.add(holder.adapterPosition)
            }
            return@setOnLongClickListener true
        }




    }

    private fun clickItemShadowing(holder: SalaryComponentAdapter.ViewHolder) {
        holder.card.setBackgroundColor(Color.GRAY)
    }


    override fun getItemCount(): Int {
        return component.size
    }

    fun adapterUpdate(componentUpdate : Component){
        component.add(componentUpdate)
        notifyDataSetChanged()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val card: TextView = itemView.findViewById(R.id.card_view)
        val componentName: TextView = itemView.findViewById(R.id.component_name)
        val componentValue: TextView = itemView.findViewById(R.id.component_value)
    }

}