package com.example.helloworld

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

import android.widget.SeekBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                imageView.setImageBitmap(generateImage(progress))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        // Setta l'immagine iniziale
        imageView.setImageBitmap(generateImage(4))

        val generateButton = findViewById<Button>(R.id.generateButton)
        generateButton.setOnClickListener {
            // Rigeneriamo l'immagine con nuovi colori
            imageView.setImageBitmap(generateImage(seekBar.progress))
        }
    }

    private fun generateImage(numQuadrati: Int): Bitmap {
        val dim = 400
        val latoQuadrato = dim / numQuadrati
        val bitmap = Bitmap.createBitmap(dim, dim, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()

        val colors = listOf(randomColor(), randomColor(), randomColor())

        for (x in 0 until numQuadrati) {
            for (y in 0 until numQuadrati) {
                val color = colors.random()
                paint.color = color

                // Calcoliamo le coordinate in modo esplicito
                val left = x * latoQuadrato
                val top = y * latoQuadrato
                val right = left + latoQuadrato
                val bottom = top + latoQuadrato

                canvas.drawRect(
                    left.toFloat(), top.toFloat(),
                    right.toFloat(), bottom.toFloat(),
                    paint
                )

                // Colorazione speculare
                if (x != numQuadrati - x - 1) {
                    paint.color = color
                    canvas.drawRect(
                        (dim - right).toFloat(), top.toFloat(),
                        (dim - left).toFloat(), bottom.toFloat(),
                        paint
                    )
                }
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
