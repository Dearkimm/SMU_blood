package org.smu.blood.ui.Notice

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.R
import org.smu.blood.api.database.MainRequest


class NoticeAdapter (private val context: Context) :
    RecyclerView.Adapter<NoticeAdapter.ViewHolder>(){
    var datas = mutableListOf<NoticeData>()
    private lateinit var listener : ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_alarm,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = datas.size

    // 리사이클러뷰 클릭 이벤트
    interface ItemClickListener{
        fun onItemClick(v: View, data: NoticeData, pos : Int)
        fun onDeleteClick(v:View, data: NoticeData, pos : Int)
    }
    fun setItemClickListener(listener : ItemClickListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val rctv: TextView = itemView.findViewById(R.id.alarm_textview)
        private val txtTime: TextView = itemView.findViewById(R.id.alarm_time)
        private val alarmDelete: ImageButton = itemView.findViewById(R.id.alarm_delete)

        @SuppressLint("ResourceAsColor")
        fun bind(item: NoticeData) {
            rctv.text = "나의 헌혈 요청글에 헌혈이 신청되었습니다."
            txtTime.text = item.alert_time

            // 아직 안 읽은 알림이면
            if(item.noticeState)
                itemView.setBackgroundColor(Color.parseColor("#f2f2f2"))

            val position = adapterPosition
            if(position!= RecyclerView.NO_POSITION) {
                alarmDelete.setOnClickListener {
                    listener?.onDeleteClick(itemView,item,position) //수정버튼클릭
                }
                itemView.setOnClickListener{
                    listener?.onItemClick(itemView,item,position)
                }
            }
        }

    }

    fun setItems(newItems: List<NoticeData>) {
        datas.clear()
        datas.addAll(newItems)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        if(position>=0) {
            datas.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }


}
