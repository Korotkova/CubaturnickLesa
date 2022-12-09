package com.example.cubaturnicklesa

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Clients")
data class Clients(
    @PrimaryKey(autoGenerate = true)
    var idClients: Long?,
    @ColumnInfo(name = "fio_clients")
    var fio_clients: String,
    @ColumnInfo(name = "price")
    var price: Double,
    @ColumnInfo(name = "tip_dereva")
    var tip_dereva: String,
    @ColumnInfo(name = "data")
    var data: String
)