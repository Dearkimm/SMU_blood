package org.smu.blood.ui.board

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.R
import org.smu.blood.api.SessionManager

class BoardAdapter(private val context:Context) :
RecyclerView.Adapter<BoardAdapter.ViewHolder>(),Filterable {

    var datas = mutableListOf<BoardData>()

    //필터링 관련 변수들
    private var unFilteredList = datas
    private var filteredList = datas

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("[REVIEW LIST3]","ADD REVIEW")
        val view = LayoutInflater.from(context).inflate(R.layout.item_board,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = datas.size

    //필터링
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString() //내 id
                filteredList = if (charString.isEmpty()) {
                    unFilteredList
                } else {
                    var filteringList = mutableListOf<BoardData>()
                    for (item in unFilteredList) {
                        if (item.nickname == charString) filteringList.add(item) //내 id = 글 id
                    }
                    filteringList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as MutableList<BoardData>
                notifyDataSetChanged()
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("[REVIEW LIST3]","ADD REVIEW")
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var txtTitle: TextView = itemView.findViewById(R.id.board_title)
        private var txtNickname: TextView = itemView.findViewById(R.id.board_nickname)
        private var txtTime: TextView = itemView.findViewById(R.id.board_time)
        private var txtHeartCount: TextView = itemView.findViewById(R.id.board_heart_count)
        private var txtCommentCount: TextView = itemView.findViewById(R.id.board_commments_count)

        fun bind(item: BoardData) {
            Log.d("[REVIEW LIST3]","ADD REVIEW")
            txtTitle.text = item.title
            txtNickname.text = item.nickname
            txtTime.text = item.time
            txtHeartCount.text = item.heartcount.toString()
            txtCommentCount.text = item.commentcount.toString()

            val position = adapterPosition
            if(position!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView,item,position)
                }

                itemView.setOnLongClickListener {
                    longlistener?.onItemLongClick(itemView,item,position)
                    return@setOnLongClickListener true
                }
            }

        }
    }

    // 클릭 이벤트 리스너 인터페이스 정의
    interface OnItemClickListener{
        fun onItemClick(v:View, data: BoardData, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    //꾹 누르기 이벤트 리스너 인터페이스 정의
    interface OnItemLongClickListener{
        fun onItemLongClick(v:View, data: BoardData, pos : Int)
    }
    private var longlistener : OnItemLongClickListener? = null
    fun setOnItemLongClickListener(listener : OnItemLongClickListener) {
        this.longlistener = listener
    }
    //삭제
    fun removeItem(position: Int){
        if(position>=0) {
            datas.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }
}