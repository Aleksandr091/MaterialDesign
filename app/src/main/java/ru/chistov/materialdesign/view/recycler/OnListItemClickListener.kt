package ru.chistov.materialdesign.view.recycler

interface OnListItemClickListener {
    fun onItemClick(data: Data)
    fun onAddBtnClick(position: Int)
    fun onRemoveBtnClick(position: Int)
}