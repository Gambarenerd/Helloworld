package com.example.helloworld

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

import android.util.Log


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val generateButton = findViewById<Button>(R.id.generateButton)
        val numQuadratiEditText = findViewById<EditText>(R.id.numQuadratiEditText)
        Log.d("MainActivity", "numQuadratiEditText: $numQuadratiEditText")

        generateButton.setOnClickListener {
            val numQuadrati = numQuadratiEditText.text.toString().toIntOrNull() ?: 4 // Default a 4 se non viene inserito un numero valido
            Log.d("setOnClick", "numQuadrati: $numQuadrati")
            imageView.setImageBitmap(generateImage(numQuadrati))
        }
    }

    private fun generateImage(numQuadrati: Int): Bitmap {
        val dim = 400
        val latoQuadrato = dim / numQuadrati
        val bitmap = Bitmap.createBitmap(dim, dim, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()

        // Calcola la metà dei quadrati; questa sarà la "linea mediana" per la riflessione speculare
        val metàQuadrati = numQuadrati / 2

        // Generiamo tre colori casuali
        val colors = listOf(randomColor(), randomColor(), randomColor())

        for (x in 0 until metàQuadrati) {
            for (y in 0 until numQuadrati) {
                // Utilizziamo il modulo 3 per alternare tra i tre colori casuali
                paint.color = colors[(x + y) % 3]

                // Disegna il quadrato sulla sinistra
                var leftUpPoint = x * latoQuadrato to y * latoQuadrato
                canvas.drawRect(
                    leftUpPoint.first.toFloat(),
                    leftUpPoint.second.toFloat(),
                    (leftUpPoint.first + latoQuadrato).toFloat(),
                    (leftUpPoint.second + latoQuadrato).toFloat(),
                    paint
                )
                // Disegna il quadrato speculare sulla destra
                leftUpPoint = (numQuadrati - x - 1) * latoQuadrato to y * latoQuadrato
                canvas.drawRect(
                    leftUpPoint.first.toFloat(),
                    leftUpPoint.second.toFloat(),
                    (leftUpPoint.first + latoQuadrato).toFloat(),
                    (leftUpPoint.second + latoQuadrato).toFloat(),
                    paint
                )
            }
        }

        return bitmap
    }

    private fun randomColor(): Int {
        val red = (0..255).random()
        val green = (0..255).random()
        val blue = (0..255).random()
        return Color.rgb(red, green, blue)
    }
}
