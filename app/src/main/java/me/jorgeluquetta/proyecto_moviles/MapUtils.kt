package me.jorgeluquetta.proyecto_moviles


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

/**
 * Convierte un vector drawable a BitmapDescriptor (icono de marcador para Google Maps)
 * con manejo de errores para evitar NullPointerException.
 */
fun bitmapDescriptorFromVector(
    context: Context,
    @DrawableRes vectorResId: Int
): BitmapDescriptor? {
    val vectorDrawable: Drawable? = ContextCompat.getDrawable(context, vectorResId)
    if (vectorDrawable == null) {
        println("⚠️ Drawable no encontrado para recurso: $vectorResId")
        return null
    }

    // Renderizar el vector en un Bitmap
    val width = vectorDrawable.intrinsicWidth.takeIf { it > 0 } ?: 64
    val height = vectorDrawable.intrinsicHeight.takeIf { it > 0 } ?: 64

    vectorDrawable.setBounds(0, 0, width, height)
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)

    return try {
        BitmapDescriptorFactory.fromBitmap(bitmap)
    } catch (e: Exception) {
        e.printStackTrace()
        println("❌ Error creando BitmapDescriptorFactory: ${e.message}")
        null
    }
}

