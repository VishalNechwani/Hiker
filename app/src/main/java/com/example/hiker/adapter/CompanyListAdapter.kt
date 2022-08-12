package com.example.hiker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hiker.R
import com.example.hiker.model.HikeEntity


class CompanyListAdapter(val hikeList:List<HikeEntity>) : RecyclerView.Adapter<CompanyListAdapter.ViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardviewlist, parent, false)
       return ViewHolder(view)
  }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.companyName.text = hikeList.get(position).company_id.toString()
        holder.inHandSalary.text = hikeList.get(position).inHandNew
        holder.componentList.adapter = CompanyListSubComponentAdapter(hikeList.get(position).component_arr)
    }

    override fun getItemCount(): Int {
        return hikeList.size;
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val companyName: TextView = itemView.findViewById(R.id.company_name)
        val inHandSalary: TextView = itemView.findViewById(R.id.in_hand_salary)
        val componentList:RecyclerView = itemView.findViewById(R.id.component_list)

    }
}