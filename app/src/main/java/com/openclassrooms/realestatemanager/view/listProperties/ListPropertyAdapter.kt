package com.openclassrooms.realestatemanager.view.listProperties

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.entity.PropertyWithAllData
import com.openclassrooms.realestatemanager.utils.Currency

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
class ListPropertyAdapter(var properties: List<PropertyWithAllData>,
                          var currency: Currency?,
                          val glide: RequestManager, private var isDoubleScreen: Boolean)
    : RecyclerView.Adapter<ListPropertyViewHolder>(){

    private lateinit var context: Context
    private val viewHolders = mutableListOf<ListPropertyViewHolder>()
    var itemSelected: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPropertyViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.card_view_item, parent, false)
        val holder = ListPropertyViewHolder(view)
        viewHolders.add(holder)

        return holder
    }

    override fun getItemCount(): Int {
        return properties.size
    }

    override fun onBindViewHolder(holder: ListPropertyViewHolder, position: Int) {
        val isSelected = position == itemSelected
        holder.updateWithProperty(properties[position], glide, currency, context, isDoubleScreen, isSelected)
    }

    fun update(properties: List<PropertyWithAllData>){
        this.properties = properties
        notifyDataSetChanged()
    }

    fun getProperty(position: Int): PropertyWithAllData {
        return properties[position]
    }

    fun updateCurrency(newCurrency: Currency?){
        currency = newCurrency
        notifyDataSetChanged()
    }

    fun updateSelection(itemSelected: Int?){
        this.itemSelected = itemSelected
        viewHolders.forEach { it.updateSelection(itemSelected) }
    }
}