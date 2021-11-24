package org.smu.blood.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.api.database.MainRequest
import org.smu.blood.databinding.ItemCardRequestBinding
import org.smu.blood.model.BloodType

class MainRequestAdapter: RecyclerView.Adapter<MainRequestAdapter.MainRequestViewHolder>() {

    val request = mutableListOf<MainRequest>()
    private lateinit var itemClickListener: ItemClickListener

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
            var blood = ""
            BloodType.values().forEach {
                if (requestInfo.bloodType == it.id) blood = it.bloodType

            }
            binding.apply {
                reqHospital.text = requestInfo.hospitalId.toString()
                reqBlood.text = "RH${rh} ${blood}형 전혈"
                reqDate.text = "${requestInfo.startDate}~${requestInfo.endDate}"
                reqCount.text = "${requestInfo.count}명"
                circleBlood.text = "RH $blood$rh"

                itemView.setOnClickListener {
                    itemClickListener.onClick(request[position])
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MainRequestViewHolder, position: Int) {
        holder.onBind(request[position], position)
    }

    override fun getItemCount(): Int = request.size

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