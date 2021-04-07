package com.pawelsznuradev.f1pitstop.listItem

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object ListItemContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<ListItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, ListItem> = HashMap()

    private val COUNT = 20

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createListItem(i))
        }
    }

    private fun addItem(item: ListItem) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

    private fun createListItem(position: Int): ListItem {
        return ListItem(position.toString(), "Item $position")
    }


    /**
     * A dummy item representing a piece of content.
     */
    data class ListItem(val id: String, val content: String) {
        override fun toString(): String = content
    }
}