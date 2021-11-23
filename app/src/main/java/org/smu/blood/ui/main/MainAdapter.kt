package org.smu.blood.ui.main

import org.smu.blood.ui.board.BoardData
import org.smu.blood.ui.main.MainData
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.R

class MainAdapter(private val context:Context) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var datas = mutableListOf<MainData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_card_request,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtHospital: TextView = itemView.findViewById(R.id.req_hospital)
        private val txtReqBlood: TextView = itemView.findViewById(R.id.req_blood)
        private val txtReqTime: TextView = itemView.findViewById(R.id.req_time)
        private val txtReqDate: TextView = itemView.findViewById(R.id.req_date)
        private val txtReqCount: TextView = itemView.findViewById(R.id.req_count)
        private val txtCircleBlood: TextView = itemView.findViewById(R.id.circle_blood)


        fun bind(item: MainData) {
            txtHospital.text = item.reqHospital
            txtReqBlood.text = item.reqBlood
            txtReqTime.text = item.reqTime
            txtReqDate.text = item.reqDate
            txtReqCount.text = item.reqCount.toString()
            txtCircleBlood.text = item.circleBlood

            itemView.setOnClickListener {

            }



        }
    }
}