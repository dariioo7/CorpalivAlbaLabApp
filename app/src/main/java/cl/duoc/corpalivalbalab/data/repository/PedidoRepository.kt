package cl.duoc.corpalivalbalab.data.repository

import cl.duoc.corpalivalbalab.data.local.PedidoDao
import cl.duoc.corpalivalbalab.data.local.PedidoEntity

class PedidoRepository(private val pedidoDao: PedidoDao) {

    suspend fun insertarPedido(pedido: PedidoEntity) {
        pedidoDao.insertarPedido(pedido)
    }

    suspend fun obtenerTodosLosPedidos(): List<PedidoEntity> {
        return pedidoDao.obtenerTodosLosPedidos()
    }
}