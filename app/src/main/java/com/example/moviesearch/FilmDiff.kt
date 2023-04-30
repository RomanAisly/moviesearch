package com.example.moviesearch

import androidx.recyclerview.widget.DiffUtil

class FilmDiff(val oldList: ArrayList<Film>, val newList: ArrayList<Film>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.poster == new.poster && old.title == new.title && old.description == new.description
    }
}