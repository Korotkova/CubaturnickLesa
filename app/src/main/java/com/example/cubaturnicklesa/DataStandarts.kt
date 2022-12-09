package com.example.cubaturnicklesa

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "DataStandarts",
        foreignKeys = [ForeignKey(
            entity = Clients::class,
            parentColumns = ["idClients"],
            childColumns = ["id_clients"],
        )]
)
data class DataStandarts(
    @PrimaryKey(autoGenerate = true)
    var idStandart: Int? = null,
    @ColumnInfo(name = "dlina")
    var dlina: Double,
    @ColumnInfo(name = "diametr")
    var diametr: Int,
    @ColumnInfo(name = "count")
    var count: Int,
    @ColumnInfo(name = "cubatura")
    var cubatura: Double,
    @ColumnInfo(name = "standart")
    var standart: String,
    @ColumnInfo(name = "id_clients", index = true)
    var id_clients: Long?

)