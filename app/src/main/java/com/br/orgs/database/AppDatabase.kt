package com.br.orgs.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.br.orgs.database.converters.Converters
import com.br.orgs.database.dao.ProdutoDao
import com.br.orgs.ui.recyclerview.adapter.model.Produto

@Database(entities = [Produto::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun produtoDao(): ProdutoDao

}