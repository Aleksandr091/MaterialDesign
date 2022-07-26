package ru.chistov.materialdesign.view.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import ru.chistov.materialdesign.databinding.FragmentRecyclerBinding
import ru.chistov.materialdesign.utils.TYPE_EARTH
import ru.chistov.materialdesign.utils.TYPE_HEADER
import ru.chistov.materialdesign.utils.TYPE_MARS


class RecyclerFragment : Fragment(), OnListItemClickListener {
    lateinit var adapter: RecyclerFragmentAdapter
    private var _binding: FragmentRecyclerBinding? = null
    private val binding: FragmentRecyclerBinding
        get() = _binding!!
    private var isNewList = false
    val list = arrayListOf(
        Pair(Data(0,"HEADER", "", TYPE_HEADER,1000000000), false),
        Pair(Data(1,"Earth", "Earth des", TYPE_EARTH), false),
        Pair(Data(2,"Earth", "Earth des", TYPE_EARTH), false),
        Pair(Data(3,"Mars", "Mars des", TYPE_MARS), false),
        Pair(Data(4,"Earth", "Earth des", TYPE_EARTH), false),
        Pair(Data(5,"Earth", "Earth des", TYPE_EARTH), false),
        Pair(Data(6,"Earth", "Earth des", TYPE_EARTH), false),
        Pair(Data(7,"Mars", "Mars des", TYPE_MARS), false)
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecyclerFragmentAdapter(list,this)
        adapter.setList(list)
        binding.recyclerView.adapter = adapter

        binding.recyclerActivityFAB.setOnClickListener {
            onAddBtnClick(list.size)
            binding.recyclerView.smoothScrollToPosition(list.size - 1)
        }
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView)
        binding.recyclerActivityDiffUtilFAB.setOnClickListener {
            changeAdapterData()
        }
    }

    private fun changeAdapterData() {
        adapter.setList(createItemList(isNewList).map { it })
        isNewList = !isNewList

    }
    private fun createItemList(instanceNumber: Boolean): List<Pair<Data, Boolean>> {
        return when (instanceNumber) {
            false -> listOf(
                Pair(Data(0, "Header"), false),
                Pair(Data(1, "Mars", ""), false),
                Pair(Data(2, "Mars", ""), false),
                Pair(Data(3, "Mars", ""), false),
                Pair(Data(4, "Mars", ""), false),
                Pair(Data(5, "Mars", ""), false),
                Pair(Data(6, "Mars", ""), false)
            )
            true -> listOf(
                Pair(Data(0, "Header"), false),
                Pair(Data(1, "Mars", ""), false),
                Pair(Data(2, "Jupiter", ""), false),
                Pair(Data(3, "Mars", ""), false),
                Pair(Data(4, "Neptune", ""), false),
                Pair(Data(5, "Saturn", ""), false),
                Pair(Data(6, "Mars", ""), false)
            )
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {

        @JvmStatic
        fun newInstance() = RecyclerFragment()
    }

    override fun onItemClick(data: Data) {

    }

    override fun onAddBtnClick(position: Int) {
        list.add(position, Pair(Data(0,"Mars", "Mars des", TYPE_MARS), false))
        adapter.setAddToList(list, position)
    }

    override fun onRemoveBtnClick(position: Int) {
        list.removeAt(position)
        adapter.setRemoveToList(list, position)
    }

}