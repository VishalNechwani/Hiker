package com.example.hiker.adapter

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.ListView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hiker.R
import com.example.hiker.model.HikeEntity


class CompanyListAdapter(val hikeList:List<HikeEntity>) : RecyclerView.Adapter<CompanyListAdapter.ViewHolder>()  {


    var isEnable = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardviewlist, parent, false)
       return ViewHolder(view)
  }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.companyName.text = hikeList.get(position).company_name
        holder.inHandSalary.text = hikeList.get(position).inHandNew
//        holder.componentList.adapter = CompanyListSubComponentAdapter(hikeList.get(position).component_arr)
        holder.card.isLongClickable = true
        holder.card.setOnClickListener {
            findNavController(holder.itemView).navigate(R.id.action_companyListFragment_to_showComponentListFragment,null,null)
            true
        }
    }

    private fun clickItem(holder: CompanyListAdapter.ViewHolder) {
        val adapter = holder.adapterPosition
        holder.card.setBackgroundColor(Color.GRAY)
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
}

