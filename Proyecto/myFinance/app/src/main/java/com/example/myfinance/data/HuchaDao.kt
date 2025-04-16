package com.example.myfinance.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myfinance.model.Hucha

@Dao
interface HuchaDao {
    @Insert
    suspend fun insertHucha(hucha: Hucha): Long

    @Query("SELECT * FROM huchas WHERE id = :huchaId")
    suspend fun getHucha(huchaId: Int): Hucha?

    @Update
    suspend fun updateHucha(hucha: Hucha)

    @Query("SELECT * FROM huchas")
    suspend fun getAllHuchas(): List<Hucha>
}
