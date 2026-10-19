package cl.duoc.corpalivalbalab.data.repository

import cl.duoc.corpalivalbalab.data.local.ContactoDao
import cl.duoc.corpalivalbalab.data.local.ContactoEntity
import kotlinx.coroutines.flow.Flow

class ContactoRepository(private val contactoDao: ContactoDao) {

    // leer registros
    val todosLosContactos: Flow<List<ContactoEntity>> = contactoDao.obtenerTodosLosContactos()

    // guardar registro
    suspend fun guardarContacto(contacto: ContactoEntity) {
        contactoDao.insertarContacto(contacto)
    }

    // borrar registro
    suspend fun eliminarContacto(contacto: ContactoEntity) {
        contactoDao.eliminarContacto(contacto)
    }
}