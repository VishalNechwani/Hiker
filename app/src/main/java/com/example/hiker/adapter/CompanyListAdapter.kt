package com.example.hiker.adapter

import android.content.Context
import android.graphics.Color
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.hiker.R
import com.example.hiker.databinding.FragmentCompanyListBinding
import com.example.hiker.model.HikeEntity
import com.example.hiker.viewmodel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class CompanyListAdapter(val hikeList:HashMap<Int,HikeEntity>,val companyListBinding: FragmentCompanyListBinding,val context:Context,val viewModel: MainViewModel) : RecyclerView.Adapter<CompanyListAdapter.ViewHolder>()  {


    var isEnable = false
    var positionHikeArr : ArrayList<Int> =  ArrayList()
    var isSamePosition : ArrayList<Int> =  ArrayList()
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardviewlist, parent, false)

        materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)

        val alert = materialAlertDialogBuilder.create()
        companyListBinding.menuDelete.setOnClickListener {
            materialAlertDialogBuilder.setMessage("Do you really want to delete this hiker ?")
            materialAlertDialogBuilder.setPositiveButton(android.R.string.yes){ dialog, which ->
               deleteHiker()
            }
            materialAlertDialogBuilder.setNegativeButton(android.R.string.no) { dialog, which ->
                alert.dismiss()
             }
            materialAlertDialogBuilder.show()
        }
       return ViewHolder(view)
  }

    private fun deleteHiker() {
        for (position in positionHikeArr){
            hikeList.remove(position)
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.companyName.text = hikeList.get(position)?.company_name
        holder.inHandSalary.text = hikeList.get(position)?.inHandNew
//        holder.componentList.adapter = CompanyListSubComponentAdapter(hikeList.get(position).component_arr)
        holder.card.isLongClickable = true
        holder.card.setOnLongClickListener {
            if (!isEnable)
            {
                clickItemShadowing(holder)
                companyListBinding.menuDelete.visibility = View.VISIBLE
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
                    }
                    positionHikeArr.remove(holder.adapterPosition)
                    clickItemUnShadowing(holder)
                }
            }
        }
    }






    override fun getItemCount(): Int {
        return hikeList.size;
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

}

