package ru.chistov.materialdesign.view.recycler

import ru.chistov.materialdesign.utils.TYPE_MARS

data class Data(
    val id: Int = 0,
    val someText: String = "Text",
    val someDescription: String? = "Description",
    val type: Int = TYPE_MARS
)