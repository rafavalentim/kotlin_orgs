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
        fun instance(context : Context) : AppDatabase{
            //Criando e definindo o banco de dados.
           return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "orgs.db"
            ).allowMainThreadQueries() //Não é boa prática. Está aqui para fins didáticos.
             .build()
        }
    }
}