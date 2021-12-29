package com.pm.runnerz.fragments.runz_list

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pm.runnerz.R
import com.pm.runnerz.api_runnerz.models.Run
import kotlinx.android.synthetic.main.custom_row_runs_list.view.*

class RunListAdapter(userIdInSession: String?) :
    RecyclerView.Adapter<RunListAdapter.MyViewHolder>() {
    private var runsList = emptyList<Run>()
    private val _userIdInSession = userIdInSession
    private var _ctx: Context? = null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        _ctx = parent.context

        return RunListAdapter.MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_row_runs_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = runsList[position]
        holder.itemView.runs_list_name.text = currentItem.name
        holder.itemView.runs_list_data.text = currentItem.data
        holder.itemView.runs_list_duration.text = currentItem.duration
        holder.itemView.runs_list_kms.text = currentItem.kms
        holder.itemView.runs_list_createdby.text = currentItem.user_name

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#d6d4e0"))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#b8a9c9"))
        }

        /*holder.itemView.layout_run_list.setOnClickListener {
            if(_userIdInSession == currentItem.users_id.toString()){
                val action =
                    RunsListFragmentDirections.actionRunsListFragmentToUpdateReportFragment(
                        currentItem
                    )
                holder.itemView.findNavController().navigate(action)
            }
            else {
                Toast.makeText(_ctx,R.string.ony_edit_your_reports, Toast.LENGTH_LONG).show()
            }
        }*/
    }

    override fun getItemCount(): Int {
        return runsList.size
    }

    fun setData(runsList: List<Run>) {
        this.runsList = runsList
        notifyDataSetChanged()
    }
}