package cl.duoc.corpalivalbalab.model

import androidx.annotation.DrawableRes

data class Producto(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val precio: Int,
    @DrawableRes val imagenResId: Int
)