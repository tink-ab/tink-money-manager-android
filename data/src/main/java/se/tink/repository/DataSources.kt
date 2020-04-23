package se.tink.repository

import androidx.lifecycle.LiveData

interface LiveDataSource<T> {
    fun read(): LiveData<T>
}

interface DataSource<T> {
    fun read(): T
}