package com.br.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.br.orgs.ui.recyclerview.adapter.model.Produto

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    suspend fun buscaTodos() : List<Produto>

    @Insert(onConflict = OnConflictStrategy.REPLACE) //Esse código diz para atualizar caso já tenha
    suspend fun salva(vararg produto: Produto)               // o registro no banco de dados.

    @Delete
    suspend fun remove(produto: Produto)

    @Update
    suspend fun altera(produto: Produto)

    @Query("SELECT * FROM Produto WHERE id = :id")
    suspend fun buscaPorId(id: Long) : Produto?
}