package com.example.cubaturnicklesa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cubaturnicklesa.databinding.ListRecycleItogBinding

class ItogAdapter : RecyclerView.Adapter<ItogAdapter.RecycleHolder>() {

    val list = ArrayList<ItogForRecycleView>()

    class RecycleHolder(item: View) : RecyclerView.ViewHolder(item) {

        val binding = ListRecycleItogBinding.bind(item)
        fun bind(itogForRecycleView: ItogForRecycleView) {
            binding.dlinafromitog.text = itogForRecycleView.dlina.toString() + " м"
            binding.diametrfromitog.text = itogForRecycleView.diametr.toString() + " см"
            binding.countfromitog.text = itogForRecycleView.count.toString() + " шт"
            binding.cubageResultfromitog.text = itogForRecycleView.cubageReult.toString() + " м³"
            binding.priceCubage.text = itogForRecycleView.priceCubage.toString() + " ₽"
        }
    }

    //берет разметку и создает ее по надутой разметке RecycleHolder(item:View)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_recycle_itog, parent, false)
        return RecycleHolder(view)
    }

    //заполнение элементами
    override fun onBindViewHolder(holder: RecycleHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addDataFromDialog(itogForRecycleView: ItogForRecycleView) {
        list.add(itogForRecycleView)
//        notifyDataSetChanged()
    }
}
