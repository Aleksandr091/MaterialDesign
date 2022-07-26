package ru.chistov.materialdesign.view.recycler

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.chistov.materialdesign.R
import ru.chistov.materialdesign.databinding.FragmentRecyclerItemEarthBinding
import ru.chistov.materialdesign.databinding.FragmentRecyclerItemHeaderBinding
import ru.chistov.materialdesign.databinding.FragmentRecyclerItemMarsBinding
import ru.chistov.materialdesign.utils.TYPE_EARTH
import ru.chistov.materialdesign.utils.TYPE_HEADER
import ru.chistov.materialdesign.utils.TYPE_MARS

class RecyclerFragmentAdapter(
    private var list: MutableList<Pair<Data, Boolean>>,
    private var onListItemClickListener: OnListItemClickListener,
    private var onListItemFavouriteClickListener: OnListItemFavouriteClickListener
) : RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {


    fun setList(newList: List<Pair<Data, Boolean>>) {
        val result = DiffUtil.calculateDiff(DiffUtilCallback(list, newList))
        result.dispatchUpdatesTo(this)
        this.list = newList.toMutableList()
    }

    fun setAddToList(newList: List<Pair<Data, Boolean>>, position: Int) {
        this.list = newList.toMutableList()
        notifyItemChanged(position)
    }

    fun setRemoveToList(newList: List<Pair<Data, Boolean>>, position: Int) {
        this.list = newList.toMutableList()
        notifyItemRemoved(position)
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].first.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_EARTH -> {
                val view =
                    FragmentRecyclerItemEarthBinding.inflate(LayoutInflater.from(parent.context))
                EarthViewHolder(view.root)
            }
            TYPE_MARS -> {
                val view =
                    FragmentRecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context))
                MarsViewHolder(view.root)
            }
            TYPE_HEADER -> {
                val view =
                    FragmentRecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context))
                HeaderViewHolder(view.root)
            }
            else -> {
                val view =
                    FragmentRecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context))
                HeaderViewHolder(view.root)
            }
        }

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        /*when (getItemViewType(position)) { // TODO WH создать BaseViewHolder
            TYPE_EARTH -> {
                holder.bind(list[position])
            }
            TYPE_MARS -> {
                holder.bind(list[position])
            }
        }*/
        holder.bind(list[position])
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val res = createCombinedPayload(payloads as List<Change<Pair<Data, Boolean>>>)
            (FragmentRecyclerItemMarsBinding.bind(holder.itemView)).apply {
                title.text =  res.newData.first.someText
            /*when (getItemViewType(position)) { // TODO WH создать BaseViewHolder
                TYPE_EARTH -> {
                    //(holder as EarthViewHolder).itemView.findViewById<TextView>(R.id.title).text =
                }
                TYPE_MARS -> {
                    val res = createCombinedPayload(payloads as List<Change<Pair<Data, Boolean>>>)
                    if (res.oldData.first.someText != res.newData.first.someText)
                        holder.itemView.findViewById<TextView>(R.id.title).text =
                            res.newData.first.someText
                }
                TYPE_HEADER -> {
                    // (holder as HeaderViewHolder).myBind(list[position])
                }
            }*/
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) { // TODO WH :BaseViewHolder


        override fun bind(listItem: Pair<Data, Boolean>) {
            (FragmentRecyclerItemEarthBinding.bind(itemView)).apply {
                title.text = listItem.first.someText
                descriptionTextView.text = listItem.first.someDescription
                favourite.setOnClickListener {
                   /* list[layoutPosition].first.weight+=100
                    list.sortByDescending { it.first.weight }
                    notifyItemChanged(layoutPosition)*/
                    onListItemFavouriteClickListener.onItemClick(layoutPosition)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view),
        ItemTouchHelperViewHolder { // TODO WH :BaseViewHolder


        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }

        override fun bind(listItem: Pair<Data, Boolean>) {
            (FragmentRecyclerItemMarsBinding.bind(itemView)).apply {
                title.text = listItem.first.someText
                marsImageView.load(R.drawable.bg_mars)
                addItemImageView.setOnClickListener {
                    onListItemClickListener.onAddBtnClick(layoutPosition)
                    notifyItemChanged(layoutPosition)
                }
                removeItemImageView.setOnClickListener {
                    onListItemClickListener.onRemoveBtnClick(layoutPosition)
                    notifyItemRemoved(layoutPosition)
                }
                moveItemDown.setOnClickListener { // TODO IndexOutOfBoundsException
                    if (list.elementAt(layoutPosition) != list.elementAt(list.size - 1)) {
                        list.removeAt(layoutPosition).apply {
                            list.add(layoutPosition + 1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition + 1)
                    }

                }
                moveItemUp.setOnClickListener { // TODO IndexOutOfBoundsException: Index: -1
                    if (list.elementAt(layoutPosition) != list[1]) {
                        list.removeAt(layoutPosition).apply {
                            list.add(layoutPosition - 1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition - 1)
                    }
                }
                marsImageView.setOnClickListener {
                    list[layoutPosition] = list[layoutPosition].let {
                        it.first to !it.second
                    }
                    marsDescriptionTextView.visibility =
                        if (list[layoutPosition].second) View.VISIBLE else View.GONE
                }
                favourite.setOnClickListener {
                    /*list[layoutPosition].first.weight+=100
                    list.sortByDescending { it.first.weight }
                    notifyItemChanged(layoutPosition)*/
                    onListItemFavouriteClickListener.onItemClick(layoutPosition)


                }
            }
        }
    }

    class HeaderViewHolder(view: View) : BaseViewHolder(view) { // TODO WH :BaseViewHolder

        override fun bind(listItem: Pair<Data, Boolean>) {
            (FragmentRecyclerItemHeaderBinding.bind(itemView)).apply {
                header.text = listItem.first.someText
            }
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        list.removeAt(fromPosition).apply {
            list.add(toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }
}