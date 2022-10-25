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

class SalaryComponentAdapter(val component: HashMap<Int, Component>,val componentArrayList: ArrayList<Component>, val companyListCallBack: SalaryComponentCallBack) : RecyclerView.Adapter<SalaryComponentAdapter.ViewHolder>(){

    var isEnable = false
    var positionHikeArr : ArrayList<Int> =  ArrayList()
    var positionHikerComponent : ArrayList<Component> = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SalaryComponentAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.salarycomponentcardlist, parent, false)
        return SalaryComponentAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SalaryComponentAdapter.ViewHolder, position: Int) {
        holder.componentName.text = component.get(position)?.namer
        holder.componentValue.text = component.get(position)?.valuer
        holder.card.isLongClickable = true
        clickItemUnShadowing(holder)
        isEnable = false
        holder.card.setOnLongClickListener {
            if (!isEnable)
            {
                clickItemShadowing(holder)
                companyListCallBack.deleteComponentButtonEnable()
                companyListCallBack.showDeleteIcon()
                isEnable = true
                positionHikeArr.add(holder.adapterPosition)
                positionHikerComponent.add(component.get(holder.adapterPosition)!!)
            }
            return@setOnLongClickListener true
        }
        holder.card.setOnClickListener {
            if (isEnable){
                if(!positionHikeArr.contains(holder.adapterPosition)){
                    positionHikeArr.add(holder.adapterPosition)
                    positionHikerComponent.add(component.get(holder.adapterPosition)!!)
                    clickItemShadowing(holder)
                }
                else{
                    if(positionHikeArr.size == 1){
                        isEnable = false
                        companyListCallBack.deleteComponentButtonDisEnable()
                        companyListCallBack.HideDeleteIcon()
                    }
                    positionHikeArr.remove(holder.adapterPosition)
                    positionHikerComponent.remove(component.get(holder.adapterPosition)!!)
                    clickItemUnShadowing(holder)
                }
            }
        }
    }

    private fun clickItemShadowing(holder: SalaryComponentAdapter.ViewHolder) {
        holder.card.setBackgroundColor(Color.GRAY)
    }

    private fun clickItemUnShadowing(holder: SalaryComponentAdapter.ViewHolder) {
        holder.card.setBackgroundColor(Color.WHITE)
    }

    override fun getItemCount(): Int {
        return component.size
    }

    fun adapterUpdate(componentUpdate : Component){
        val n = component.size
        component.put(n,componentUpdate)
        notifyDataSetChanged()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val card: MaterialCardView = itemView.findViewById(R.id.card_view)
        val componentName: TextView = itemView.findViewById(R.id.component_name)
        val componentValue: TextView = itemView.findViewById(R.id.component_value)
    }

    fun deleteHikerInAdapter() {
        //deleting the hiker
        var hikeEntityForDelete: ArrayList<Component>? = ArrayList()
        var count = 0
        componentArrayList.removeAll(positionHikerComponent)
        rappingCoListToHashMap()
        companyListCallBack.deleteComponentButtonDisEnable()
//        for(eachHikePosition in positionHikeArr){
//            hikeEntityForDelete!!.add(component.get(eachHikePosition)!!)
//            component.remove(eachHikePosition)
//            positionHikeArr.remove(eachHikePosition)
//        }
//            val iterator: MutableIterator<Int> = positionHikeArr.iterator()
//            while (iterator.hasNext()) {
//                val eachHikePosition = iterator.next()
//                hikeEntityForDelete!!.add(component.get(eachHikePosition)!!)
//                component.remove(eachHikePosition)
//                positionHikeArr.remove(eachHikePosition)
//            }
//        component.
        notifyDataSetChanged()
    }

    private fun rappingCoListToHashMap() {
        var i = 0
        for(each in positionHikerComponent){
            component.remove(positionHikeArr.get(i++))
        }
    }
}