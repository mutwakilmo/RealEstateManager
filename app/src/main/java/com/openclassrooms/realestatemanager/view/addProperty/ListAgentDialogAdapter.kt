package com.openclassrooms.realestatemanager.view.addProperty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.entity.Agent

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
class ListAgentDialogAdapter(var agents: List<Agent>, private val glide: RequestManager) :
        RecyclerView.Adapter<ListAgentDialogViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAgentDialogViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.card_agent_dialog_item, parent, false)

        return ListAgentDialogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return agents.size
    }

    override fun onBindViewHolder(holder: ListAgentDialogViewHolder, position: Int) {
        holder.updateWithAgent(agents[position], glide)
    }

    fun getAgent(position: Int): Agent {
        return agents[position]
    }

}