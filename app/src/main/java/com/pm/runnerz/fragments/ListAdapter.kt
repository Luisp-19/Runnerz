package com.pm.runnerz.fragments

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pm.runnerz.R
import com.pm.runnerz.data.entities.Running
import kotlinx.android.synthetic.main.recyclerview.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    private var runningList = emptyList<Running>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview, parent, false))
    }

    override fun onBindViewHolder(holder: ListAdapter.MyViewHolder, position: Int) {
        val currentItem = runningList[position]
        holder.itemView.lst_runningId.text = currentItem.id.toString()
        holder.itemView.lst_runningName.text = currentItem.name
        /*
        * DURATION
        * KMS
        * DATA
        * */

        if(position%2 == 0){
            holder.itemView.rowLayout.setBackgroundColor(Color.parseColor("#d6d4e0"))
        }
        else {
            holder.itemView.rowLayout.setBackgroundColor(Color.parseColor("#b8a9c9"))
        }

        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return runningList.size
    }

    fun setData(runnings: List<Running>){
        this.runningList = runnings
        notifyDataSetChanged()
    }
}