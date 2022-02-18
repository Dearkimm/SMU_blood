package org.smu.blood.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.api.database.MainRequest
import org.smu.blood.databinding.ItemCardRequestBinding
import org.smu.blood.model.BloodType
import org.smu.blood.model.Hospital
import org.smu.blood.ui.board.BoardData
import org.smu.blood.ui.main.MainFragment
import org.smu.blood.ui.main.MainFragment.Companion.donationType

class MainRequestAdapter: RecyclerView.Adapter<MainRequestAdapter.MainRequestViewHolder>(),
    Filterable {

    val request = mutableListOf<MainRequest>()
    private lateinit var itemClickListener: ItemClickListener

    var unFilteredList = mutableListOf<MainRequest>()
    var filteredList = mutableListOf<MainRequest>()

    override fun getItemCount(): Int = filteredList.size

    //필터링
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                filteredList = if (charString.isEmpty()) {
                    unFilteredList
                } else {
                    var filteringList = mutableListOf<MainRequest>()
                    for (item in unFilteredList) {
                        if (item.bloodType.toString() == charString) filteringList.add(item) //내 id = 글 id
                    }
                    filteringList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as MutableList<MainRequest>
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainRequestViewHolder {
        val binding = ItemCardRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainRequestViewHolder(binding)
    }

    inner class MainRequestViewHolder(
        private val binding: ItemCardRequestBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(requestInfo: MainRequest, position: Int) {
            val rh = if (requestInfo.rhType) "+" else "-"
            /*var blood = ""
            var hospital = ""

            BloodType.values().forEach {
                if (requestInfo.bloodType == it.id) blood = it.bloodType
            }
            Hospital.values().forEach {
                if (requestInfo.hospitalId == it.id) hospital = it.hospitalName
            }
            */

            val blood = BloodType.values().first { it.id == requestInfo.bloodType }.bloodType
            val hospital = Hospital.values().first { it.id == requestInfo.hospitalId }.hospitalName

            binding.apply {
                reqHospital.text = hospital
                reqBlood.text = "RH${rh} ${blood}형 ${requestInfo.donationType}"
                reqDate.text = "${requestInfo.startDate}~${requestInfo.endDate}"
                reqCount.text = "${requestInfo.count}명"
                circleBlood.text = "RH $blood$rh"
                reqTime.text = requestInfo.updatedDate

                itemView.setOnClickListener {
                    itemClickListener.onClick(request[position])
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MainRequestViewHolder, position: Int) {
        holder.onBind(filteredList[position], position)
    }

    // 원래 있던거 필터때문에 잠시 주석
    // override fun getItemCount(): Int = request.size

    fun setItems(newItems: List<MainRequest>) {
        request.clear()
        request.addAll(newItems)
        notifyDataSetChanged()
    }

    fun addItems(newItems: List<MainRequest>) {
        request.addAll(newItems)
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onClick(requestInfo: MainRequest)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}