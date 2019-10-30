package se.tink.repository.changeObservers

import se.tink.repository.ChangeObserver


abstract class ListManagingChangeObserver<T> : ChangeObserver<T> {

    private val itemList: MutableList<T> = mutableListOf()

    @Synchronized
    override fun onCreate(items: MutableList<T>?) {

        items?.let { itemList += it }

        update(ArrayList(itemList))
    }

    @Synchronized
    override fun onRead(items: MutableList<T>?) {

        itemList.clear()
        items?.let { itemList += it }

        update(ArrayList(itemList))
    }

    @Synchronized
    override fun onUpdate(items: MutableList<T>?) {

        items?.let {
            itemList -= it
            itemList += it
        }

        update(ArrayList(itemList))
    }

    @Synchronized
    override fun onDelete(items: MutableList<T>?) {

        items?.let { itemList += it }

        update(ArrayList(itemList))
    }

    abstract fun update(updatedItems: List<T>)
}