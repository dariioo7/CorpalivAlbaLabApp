package cl.duoc.corpalivalbalab.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contactos")
data class ContactoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreCompleto: String,
    val correo: String,
    val telefono: Int,
    val fechaContacto: String,
    val tipoColaboracion: String,
    val requiereBoleta: Boolean,
    val suscribirBoletin: Boolean
)