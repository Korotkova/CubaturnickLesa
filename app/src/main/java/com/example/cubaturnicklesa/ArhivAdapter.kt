package com.example.cubaturnicklesa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cubaturnicklesa.databinding.ListRecycleArhivBinding

class ArhivAdapter(private val itemClickedListener: (String) -> Unit) : RecyclerView.Adapter<ArhivAdapter.RecycleHolder>(){

    val arhiv = ArrayList<ArhivForRecycleView>()

    class RecycleHolder(item: View): RecyclerView.ViewHolder(item) {

        val binding = ListRecycleArhivBinding.bind(item)

        fun bind(arhivForRecycleView: ArhivForRecycleView){
            binding.fio.text = arhivForRecycleView.fio
            binding.dataSave.text = arhivForRecycleView.data
        }

        fun listen(event: (position: Int, type: Int) -> Unit): RecycleHolder {
            itemView.setOnClickListener {
                event.invoke(adapterPosition, itemViewType)
            }
            return this
        }
    }
    //берет разметку и создает ее по надутой разметке RecycleHolder(item:View)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_recycle_arhiv,parent,false)
        return RecycleHolder(view).listen{ pos, type ->
            val item = arhiv.get(pos)
            itemClickedListener.invoke(item.fio)
        }
    }

    //заполнение элементами
    override fun onBindViewHolder(holder: RecycleHolder, position: Int) {
        holder.bind(arhiv[position])
    }

    override fun getItemCount(): Int {
        return arhiv.size
    }

    //    @SuppressLint("NotifyDataSetChanged")
    fun addDataFromDialog(arhivForRecycleView: ArhivForRecycleView){
        arhiv.add(arhivForRecycleView)
//        notifyDataSetChanged()
    }
}