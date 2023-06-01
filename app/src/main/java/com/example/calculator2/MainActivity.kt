package com.example.calculator2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.calculator2.databinding.ActivityMainBinding
import androidx.databinding.DataBindingUtil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentNumber = ""
    private var currentOperation = ""
    private var result = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Обработчики событий для кнопок с числами
        val numberButtons = listOf(
            binding.button0, binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6, binding.button7,
            binding.button8, binding.button9
        )

        for ((index, button) in numberButtons.withIndex()) {
            button.setOnClickListener {
                currentNumber += index.toString()
                result = currentNumber.toDouble()
                binding.textView.text = result.toString()
            }
        }

        val operationButtons = mapOf(
            binding.buttonPlus to "+",
            binding.buttonMinus to "-",
            binding.buttonMultiply to "*",
            binding.buttonDivide to "/"
        )

        for ((button, operation) in operationButtons) {
            button.setOnClickListener {
                performOperation()
                currentOperation = operation
                binding.textView.text = "${result.toString()}$currentOperation"
            }
        }


        // Обработчик событий для кнопки равно
        binding.buttonEquals.setOnClickListener {
            performOperation()
            currentOperation = ""
            binding.textView.text = result.toString()
        }

        // Обработчик событий для кнопки +/- (меняет знак текущего числа)
        binding.buttonPlusMinus.setOnClickListener {
            currentNumber = if (currentNumber.startsWith("-")) {
                currentNumber.substring(1)
            } else {
                "-$currentNumber"
            }
            binding.textView.text = currentNumber
        }

        // Обработчик событий для кнопки % (делит текущее число на 100)
        binding.buttonPercent.setOnClickListener {
            currentNumber = (currentNumber.toDouble() / 100).toString()
            binding.textView.text = currentNumber
        }

        binding.buttonAC.setOnClickListener {
            currentNumber = ""
            currentOperation = ""
            result = 0.0
            binding.textView.text = ""
        }
    }



    private fun performOperation() {
        val number = currentNumber.toDoubleOrNull() ?: return
        when (currentOperation) {
            "+" -> result += number
            "-" -> result -= number
            "*" -> result *= number
            "/" -> if (number != 0.0) {
                result /= number
            } else {
                Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                return
            }
            else -> result = number
        }
        currentNumber = ""
    }
}
