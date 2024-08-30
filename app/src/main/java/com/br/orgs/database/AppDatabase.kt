package com.br.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.br.orgs.database.converters.Converters
import com.br.orgs.database.dao.ProdutoDao
import com.br.orgs.ui.recyclerview.adapter.model.Produto

@Database(entities = [Produto::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun produtoDao(): ProdutoDao

    //O companion transforma o método em estático.
    companion object {
        @Volatile
        private var db : AppDatabase? = null
        fun instance(context : Context) : AppDatabase{
            //Criando e definindo o banco de dados.
           return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "orgs.db"
            ).build()
               .also {
                   db = it
               }
        }
    }
}