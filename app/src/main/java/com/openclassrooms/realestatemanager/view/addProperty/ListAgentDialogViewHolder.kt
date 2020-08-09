package com.openclassrooms.realestatemanager.view.addProperty

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.entity.Agent


/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
class ListAgentDialogViewHolder(view: View) : RecyclerView.ViewHolder(view){

    @BindView(R.id.list_agent_tv_picture) lateinit var pictureAgent: ImageView
    @BindView(R.id.list_agent_tv_name) lateinit var nameAgent: TextView

    init {
        ButterKnife.bind(this, view)
    }

    fun updateWithAgent(agent: Agent, glide: RequestManager){
        val nameToDisplay = "${agent.firstName} ${agent.lastName}"
        nameAgent.text = nameToDisplay

        agent.urlProfilePicture?.let {
            glide.load(it).apply(RequestOptions.circleCropTransform()).into(pictureAgent)
        }
    }

}