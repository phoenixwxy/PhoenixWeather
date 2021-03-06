package com.phoenix.phoenixweather.ui.place

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.phoenix.phoenixweather.R
import com.phoenix.phoenixweather.logic.model.Place
import com.phoenix.phoenixweather.logic.model.Weather
import com.phoenix.phoenixweather.ui.weather.WeatherActivity

class PlaceAdapter(private val fragment: Fragment, private val placeList: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>(){

    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.place_name)
        val placeAddress: TextView = view.findViewById(R.id.place_address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)

        context = parent.context

//        val holder = ViewHolder(view)
//        holder.itemView.setOnClickListener {
//            val position = holder.bindingAdapterPosition
//            val place = placeList[position]
//            val intent = Intent(parent.context, WeatherActivity::class.java).apply {
//                putExtra("location_lng", place.location.lng)
//                putExtra("location_lat", place.location.lat)
//                putExtra("place_name", place.name)
//            }
//            fragment.startActivity(intent)
//            fragment.activity?.finish()
//        }

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address

        holder.itemView.setOnClickListener {
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            fragment.startActivity(intent)
            fragment.activity?.finish()
        }
    }

    override fun getItemCount(): Int {
        return placeList.size
    }
}