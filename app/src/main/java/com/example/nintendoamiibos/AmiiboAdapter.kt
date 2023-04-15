package com.example.nintendoamiibos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AmiiboAdapter(private val amiiboList: MutableList<Triple<String, String, String>>) : RecyclerView.Adapter<AmiiboAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var amiiboImage: ImageView
        val amiiboName: TextView
        val amiiboSeries: TextView

        init {
            amiiboImage = view.findViewById(R.id.amiibo_image)
            amiiboName = view.findViewById(R.id.amiibo_name)
            amiiboSeries = view.findViewById(R.id.series)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.amiibo_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return amiiboList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val seriesText = "Series: "

        Glide.with(holder.itemView)
            .load(amiiboList[position].first)
            .fitCenter()
            .into(holder.amiiboImage)

        holder.amiiboName.text = amiiboList[position].second
        holder.amiiboSeries.text = String.format("%s%s", seriesText, amiiboList[position].third)

        holder.amiiboImage.setOnClickListener {
            Toast.makeText(holder.itemView.context, "${holder.amiiboName.text}'s image clicked", Toast.LENGTH_SHORT).show()
        }
    }
}