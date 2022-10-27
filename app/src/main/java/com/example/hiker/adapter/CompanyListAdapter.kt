package com.example.hiker.adapter

import android.graphics.Color
import android.icu.text.NumberFormat
import android.os.Build
import android.view.*
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.hiker.R
import com.example.hiker.model.HikeEntity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class CompanyListAdapter(val hikeArrList : ArrayList<HikeEntity>,val companyListCallBack: CompanyListCallBack) : RecyclerView.Adapter<CompanyListAdapter.ViewHolder>()  {

    var isEnable = false
    var positionHikeArr : ArrayList<Int> =  ArrayList()
    var isSamePosition : ArrayList<Int> =  ArrayList()
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardviewlist, parent, false)
       return ViewHolder(view)
  }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.companyName.text = hikeArrList.get(position).company_name
        holder.inHandSalary.text = currencyFormat(hikeArrList.get(position).inHandNew)
        holder.card.isLongClickable = true
        clickItemUnShadowing(holder)
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
        holder.card.setOnClickListener {
            if (isEnable){
                if(!positionHikeArr.contains(holder.adapterPosition)){
                    positionHikeArr.add(holder.adapterPosition)
                    clickItemShadowing(holder)
                }
                else{
                    if(positionHikeArr.size == 1){
                        isEnable = false
                        companyListCallBack.HideDeleteIcon()
                    }
                    positionHikeArr.remove(holder.adapterPosition)
                    clickItemUnShadowing(holder)
                }
            }else{
                companyListCallBack.navigateToCompanyShowComponent(holder.adapterPosition,hikeArrList.get(holder.adapterPosition))
            }
        }
    }

    override fun getItemCount(): Int {
        return hikeArrList.size;
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val card: CardView = itemView.findViewById(R.id.card_view)
        val companyName: TextView = itemView.findViewById(R.id.company_name)
        val inHandSalary: TextView = itemView.findViewById(R.id.in_hand_salary)
    }

    private fun clickItemShadowing(holder: CompanyListAdapter.ViewHolder) {
        holder.card.setBackgroundColor(Color.GRAY)
    }

    private fun clickItemUnShadowing(holder: CompanyListAdapter.ViewHolder) {
        holder.card.setBackgroundColor(Color.WHITE)
    }

    fun deleteHikerInAdapter() {
        //deleting the hiker
        val hikeEntityForDelete: ArrayList<HikeEntity> = ArrayList()
        val hikeEntityDeleteId: ArrayList<Int> = ArrayList()
        var count = 0
        for(eachHikePosition in positionHikeArr){
            hikeEntityDeleteId.add(hikeArrList.get(eachHikePosition).company_id)
            hikeEntityForDelete.add(hikeArrList.get(eachHikePosition))
        }
        hikeArrList.removeAll(hikeEntityForDelete)
        companyListCallBack.deleteHiker(hikeEntityDeleteId)
        isEnable = false
        companyListCallBack.HideDeleteIcon()
        //clearing all the list as not needed
        positionHikeArr.clear()
        notifyDataSetChanged()
    }

    fun currencyFormat(value:String):String{
        val format = NumberFormat.getCurrencyInstance(Locale("en","in"))
        val formatted = format.format(BigDecimal(value))
        return formatted.substring(0,formatted.length-3)
    }
}

