package com.example.cubaturnicklesa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cubaturnicklesa.databinding.ListRecycleDataBinding

class CalcDialogAdapter: RecyclerView.Adapter<CalcDialogAdapter.RecycleHolder>(){

    val dialogList = ArrayList<CalcDialogForRecycleView>()

    class RecycleHolder(item: View): RecyclerView.ViewHolder(item) {

        val binding = ListRecycleDataBinding.bind(item)
        fun bind(calcDialogForRecycleView: CalcDialogForRecycleView){
            binding.dlinafromdialog.text = "Длина: " + calcDialogForRecycleView.dlina.toString() + " м"
            binding.diametrfromdialog.text = "Диаметр: " + calcDialogForRecycleView.diametr.toString() + " см"
            binding.countfromdialog.text = "Количество: " + calcDialogForRecycleView.count.toString() + " шт"
            binding.cubageResultfromdialog.text = "Кубатура: " + calcDialogForRecycleView.cubageReult.toString() + " м³"
        }
    }

    //берет разметку и создает ее по надутой разметке RecycleHolder(item:View)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_recycle_data,parent,false)
        return RecycleHolder(view)
    }

    //заполнение элементами
    override fun onBindViewHolder(holder: RecycleHolder, position: Int) {
        holder.bind(dialogList[position])
    }

    override fun getItemCount(): Int {
        return dialogList.size
    }

//    @SuppressLint("NotifyDataSetChanged")
    fun addDataFromDialog(calcDialogForRecycleView: CalcDialogForRecycleView){
        dialogList.add(calcDialogForRecycleView)
//        notifyDataSetChanged()
    }
}