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
import com.google.android.material.card.MaterialCardView
import java.util.HashMap

class SalaryComponentAdapter(val componentArrayList: ArrayList<Component>,val cTotal : Int,val companyListCallBack: SalaryComponentCallBack) : RecyclerView.Adapter<SalaryComponentAdapter.ViewHolder>(){

    var isEnable = false
    var positionHikeArr : ArrayList<Int> =  ArrayList()
    var positionHikerComponent : ArrayList<Component> = ArrayList()
    var componentTotal = cTotal

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SalaryComponentAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.salarycomponentcardlist, parent, false)
        return SalaryComponentAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SalaryComponentAdapter.ViewHolder, position: Int) {
        holder.componentName.text = componentArrayList.get(position).namer
        holder.componentValue.text = componentArrayList.get(position).valuer
//        componentTotal += componentArrayList.get(position).valuer.toInt()
        holder.card.isLongClickable = true
        clickItemUnShadowing(holder)
        isEnable = false
        holder.card.setOnLongClickListener {
            if (!isEnable)
            {
                clickItemShadowing(holder)
                companyListCallBack.deleteComponentButtonEnable()
                isEnable = true
                positionHikeArr.add(holder.adapterPosition)
            }
            return@setOnLongClickListener true
        }
        holder.card.setOnClickListener {
            if (isEnable){
                if(!positionHikeArr.contains(holder.adapterPosition)){
                    positionHikeArr.add(holder.adapterPosition)
                    clickItemShadowing(holder)
                }
                else{
                    if(positionHikeArr.size == 1){
                        isEnable = false
                        companyListCallBack.deleteComponentButtonDisEnable()
                    }
                    positionHikeArr.remove(holder.adapterPosition)
                    clickItemUnShadowing(holder)
                }
            }
        }
//        if(position == (componentArrayList.size-1)){
//            companyListCallBack.componentTotal(componentTotal)
//        }
    }


    private fun clickItemShadowing(holder: SalaryComponentAdapter.ViewHolder) {
        holder.card.setBackgroundColor(Color.GRAY)
    }

    private fun clickItemUnShadowing(holder: SalaryComponentAdapter.ViewHolder) {
        holder.card.setBackgroundColor(Color.WHITE)
    }

    override fun getItemCount(): Int {
        return componentArrayList.size
    }

    fun adapterUpdate(componentUpdate : Component){
        val n = componentArrayList.size
        componentArrayList.add(componentUpdate)
        notifyDataSetChanged()
        componentTotal = componentTotal + componentUpdate.valuer.toInt()
        companyListCallBack.componentTotal(componentTotal)
    }

    fun getFinalComponentList():ArrayList<Component>{
       return componentArrayList
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val card: MaterialCardView = itemView.findViewById(R.id.card_view)
        val componentName: TextView = itemView.findViewById(R.id.component_name)
        val componentValue: TextView = itemView.findViewById(R.id.component_value)
    }

    fun deleteHikerInAdapter() {
        //deleting the hiker
        var hikeEntityForDelete: ArrayList<Component> = ArrayList()
        var tComponentTotal = 0
        for(eachHikePosition in positionHikeArr){
            hikeEntityForDelete.add(componentArrayList.get(eachHikePosition))
            tComponentTotal += componentArrayList.get(eachHikePosition).valuer.toInt()
        }
        componentArrayList.removeAll(hikeEntityForDelete)
        positionHikeArr.clear()
        companyListCallBack.deleteComponentButtonDisEnable()
        companyListCallBack.deleteRedundantComponent(positionHikerComponent)
        notifyDataSetChanged()
        componentTotal = componentTotal - tComponentTotal
        companyListCallBack.componentTotal(componentTotal)
    }
}