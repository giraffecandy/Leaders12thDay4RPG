package app.babachan.tryinkconstitutiion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class InkAdapter(

    private val context: Context,
    private var inkList: OrderedRealmCollection<Ink>,
    private val autoUpdate: Boolean,
    private val listener: ListListener
) :
RealmRecyclerViewAdapter<Ink, InkAdapter.ViewHolder>(inkList, autoUpdate) {
    //recyclerviewに表示するリストを宣言
    //val items: MutableList<Ink> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_information_data_cell, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return inkList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // val ink: Ink = inkList.get(position) : return
        if(inkList.get(position).isValid()) {
            val ink: Ink = inkList[position]
            //holder.data.
            holder.nameTextView.text = ink.makerName
            holder.colorTextView.text = ink.inkColor
            holder.thickTextView.text = ink.thickness.toString()
            holder.numberTextView.text = ink.stock.toString()
            holder.frequencyTextView.text = ink.speed

            holder.itemView.setOnClickListener {
                listener.onClickRow(it, inkList[position])
            }
        }
    }


    interface ListListener {
        fun onClickRow(view: View, rowModel: Ink)
    }

//    fun addAll(items: MutableList<Ink>) {
//        this.items.addAll(items)
//        notifyDataSetChanged()
//    }


//    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
//        val nameTextView: TextView = view.findViewById(R.id.makerName)
//        val colorTextView: TextView = view.findViewById(R.id.inkColor)
//        val thickTextView: TextView = view.findViewById(R.id.thickness)
//        val numberTextView: TextView = view.findViewById(R.id.stock)
//        val frequencyTextView: TextView = view.findViewById(R.id.speed)
//
//    }
}