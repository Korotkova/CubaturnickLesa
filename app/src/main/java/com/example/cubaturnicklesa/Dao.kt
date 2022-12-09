package com.example.cubaturnicklesa

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface Dao {
    @Insert
    fun insertClients(clients: Clients)
    @Insert
    fun insertDataStandarts(standarts: DataStandarts)

    @Query("Select * from Clients ")
    fun getAllClients(): Flow<List<Clients>>

    @Query("Select idClients from Clients order by idClients desc LIMIT 1")
    fun getLastIdClient(): Long

    @Query("Select price from Clients where idClients = :id")
    fun getLastPriceClient(id: Long): Double

    @Query("Select * from Clients, DataStandarts where DataStandarts.id_clients == Clients.idClients and idClients =  :idClients")
    fun getData(idClients: Long): Cursor

    @Query("DELETE FROM DataStandarts WHERE id_clients = :idClients")
    fun deleteFromDataStandart(idClients: Long): Int

    @Query("DELETE FROM Clients WHERE idClients =  :idClients")
    fun deleteClients(idClients: Long): Int

    @Query("Select idClients from Clients order by fio_clients = :fio")
    fun getAllDataIDClients(fio: String): Cursor
}