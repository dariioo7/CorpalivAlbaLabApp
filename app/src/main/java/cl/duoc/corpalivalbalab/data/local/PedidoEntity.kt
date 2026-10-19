package cl.duoc.corpalivalbalab.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pedidos")
data class PedidoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productoNombre: String,
    val productoPrecio: Int,
    val cantidad: Int,
    val total: Int,
    val nombreCliente: String,
    val correoCliente: String,
    val telefonoCliente: String,
    val fechaRegistro: String
)