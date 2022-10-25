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


class CompanyListAdapter(val hikeMap:HashMap<Int,HikeEntity>,val companyListCallBack: CompanyListCallBack) : RecyclerView.Adapter<CompanyListAdapter.ViewHolder>()  {


    var isEnable = false
    var positionHikeArr : ArrayList<Int> =  ArrayList()
    var isSamePosition : ArrayList<Int> =  ArrayList()
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardviewlist, parent, false)
//        materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
//        val alert = materialAlertDialogBuilder.create()
//        companyListBinding.menuDelete.setOnClickListener {
//
//        }
       return ViewHolder(view)
  }

    private fun deleteHiker() {
        for (position in positionHikeArr){
            hikeMap.remove(position)
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.companyName.text = hikeMap.get(position)?.company_name
        holder.inHandSalary.text = currencyFormat(hikeMap.get(position)?.inHandNew!!)
//        holder.componentList.adapter = CompanyListSubComponentAdapter(hikeMap.get(position).component_arr)
        holder.card.isLongClickable = true
        holder.card.setOnLongClickListener {
            if (!isEnable)
            {
                clickItemShadowing(holder)
                companyListCallBack.showDeleteIcon()
                companyListCallBack.showShareIcon()
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
                if(positionHikeArr.size > 1 || positionHikeArr.size==0){
                    companyListCallBack.HideShareIcon()
                }
                if(positionHikeArr.size == 1){
                    companyListCallBack.showShareIcon()
                }
            }else{
                companyListCallBack.navigateToCompanyShowComponent(holder.adapterPosition,hikeMap.get(holder.adapterPosition))
            }
        }
    }

    override fun getItemCount(): Int {
        return hikeMap.size;
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val card: CardView = itemView.findViewById(R.id.card_view)
        val companyName: TextView = itemView.findViewById(R.id.company_name)
        val inHandSalary: TextView = itemView.findViewById(R.id.in_hand_salary)
//        val componentList:RecyclerView = itemView.findViewById(R.id.component_list)

    }

    private fun clickItemShadowing(holder: CompanyListAdapter.ViewHolder) {
//        val adapter = holder.adapterPosition
        holder.card.setBackgroundColor(Color.GRAY)
    }

    private fun clickItemUnShadowing(holder: CompanyListAdapter.ViewHolder) {
//        val adapter = holder.adapterPosition
        holder.card.setBackgroundColor(Color.WHITE)
    }

    fun deleteHikerInAdapter() {
        //deleting the hiker
        var hikeEntityForDelete: ArrayList<HikeEntity>? = ArrayList()
        var count = 0
        for(eachHikePosition in positionHikeArr){
            hikeEntityForDelete!!.add(hikeMap.get(eachHikePosition)!!)
            hikeMap.remove(eachHikePosition)
        }
        notifyDataSetChanged()
        companyListCallBack.deleteHiker(hikeEntityForDelete!!)
    }

    fun currencyFormat(value:String):String{
        val format = NumberFormat.getCurrencyInstance(Locale("en","in"))
        val formatted = format.format(BigDecimal(value))
        return formatted.substring(0,formatted.length-3)
    }
}

