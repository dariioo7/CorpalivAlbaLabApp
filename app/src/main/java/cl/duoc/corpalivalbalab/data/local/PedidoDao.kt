package cl.duoc.corpalivalbalab.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PedidoDao {
    @Insert
    suspend fun insertarPedido(pedido: PedidoEntity)

    @Query("SELECT * FROM pedidos ORDER BY id DESC")
    suspend fun obtenerTodosLosPedidos(): List<PedidoEntity>
}