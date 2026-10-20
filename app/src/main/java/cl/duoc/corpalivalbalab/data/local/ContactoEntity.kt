package cl.duoc.corpalivalbalab.data.local

import androidx.room.ColumnInfo
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

    @ColumnInfo(name = "recibirCorreo")
    val requiereBoleta: Boolean,

    @ColumnInfo(name = "voluntarioPrimera")
    val suscribirBoletin: Boolean
)