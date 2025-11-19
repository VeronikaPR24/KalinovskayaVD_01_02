package com.bignerdranch.android.kalinovskayavd_01_02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val shapePicker = findViewById<Spinner>(R.id.shapePicker)
        val formulaImage = findViewById<ImageView>(R.id.formulaImage)
        val inputLayout = findViewById<EditText>(R.id.inputLayout)
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val shapes = arrayOf("Треугольник", "Круг")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, shapes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        shapePicker.adapter = adapter
        shapePicker.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                if (position == 0) {
                    formulaImage.setBackgroundResource(R.drawable.perim)
                    inputLayout.hint = "Введите a и b через запятую"
                } else {
                    formulaImage.setBackgroundResource(R.drawable.circle)
                    inputLayout.hint = "Введите периметр P"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        calculateButton.setOnClickListener {
            val inputText = inputLayout.text.toString().trim()
            if (inputText.isEmpty()) {
                showAlert("Ошибка", "Введите данные")
            }

            try {
                val result = if (shapePicker.selectedItemPosition == 0) {
                    val parts = inputText.split(",")
                    if (parts.size != 2) {
                        showAlert("Ошибка", "Для треугольника введите две стороны через запятую: a,b")
                    }
                    val a = parts[0].trim().toDouble()
                    val b = parts[1].trim().toDouble()
                    val perimeter = 2 * a + b
                    "Периметр треугольника: $perimeter"
                } else {
                    val p = inputText.toDouble()
                    val radius = p / (2 * Math.PI)
                    "Радиус круга:${"%.2f".format(radius)}"
                }

                showAlert("Результат", result)

            } catch (e: Exception) {
                showAlert("Ошибка", "Неправильные данные")
            }
        }
    }

    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

}