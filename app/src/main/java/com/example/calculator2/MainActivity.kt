package com.example.calculator2

import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import com.example.calculator2.databinding.ActivityMainBinding
import org.mariuszgromada.math.mxparser.Expression
import androidx.databinding.DataBindingUtil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentExpression = ""
    private lateinit var soundPool: SoundPool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        soundPool = SoundPool.Builder().setMaxStreams(2).build()  // Инициализируйте soundPool здесь

        // Здесь используйте soundPool
        val sound1 = soundPool.load(this, R.raw.pressed, 1)
        val sound2 = soundPool.load(this, R.raw.unpressed, 1)


        // Обработчики событий для кнопок с числами
        val numberButtons = listOf(
            binding.button0, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6, binding.button7,
            binding.button8, binding.button9
        )

        for ((index, button) in numberButtons.withIndex()) {
            button.setOnClickListener {
                currentExpression += index.toString()
                binding.textView.text = currentExpression
            }
        }

        // Обработчики событий для кнопок с операциями
        val operationButtons = mapOf(
            binding.buttonPlus to "+",
            binding.buttonMinus to "-",
            binding.buttonMultiply to "*",
            binding.buttonDivide to "/"
        )

        for ((button, operation) in operationButtons) {
            button.setOnClickListener {
                currentExpression += operation
                binding.textView.text = currentExpression
            }
        }

        binding.buttonDot.setOnClickListener {
            // Проверяем, содержит ли текущее число уже точку
            if (!currentExpression.contains(".")) {
                currentExpression += "."
                binding.textView.text = currentExpression
            }
        }

        // Обработчик событий для кнопки равно
        binding.buttonEquals.setOnClickListener {
            val expression = Expression(currentExpression)
            val result = expression.calculate()
            if (result % 1 == 0.0) {
                // Если результат является целым числом, преобразовать его в Int
                currentExpression = result.toInt().toString()
            } else {
                currentExpression = result.toString()
            }
            binding.textView.text = currentExpression
        }


        setButtonTouchListener(
            binding.imageViewAC,
            sound1,
            sound2,
            R.drawable.pressed_clear,
            R.drawable.unpressed_clear,
            {
                // Очищаем поле ввода
                currentExpression = ""
                binding.textView.text = currentExpression
            }
        )

        setButtonTouchListener(
            binding.button1,
            sound1,
            sound2,
            R.drawable.pressed1,
            R.drawable.unpressed1,
            {
                // Добавляем число 1 в выражение
                currentExpression += "1"
                binding.textView.text = currentExpression
            }
        )


        // Обработчик событий для кнопки +/-
        binding.buttonPlusMinus.setOnClickListener {
            val expression = Expression(currentExpression)
            val result = -expression.calculate()
            currentExpression = result.toString()
            binding.textView.text = currentExpression
        }

        // Обработчик событий для кнопки %
        binding.buttonPercent.setOnClickListener {
            val expression = Expression(currentExpression)
            val result = expression.calculate() / 100
            currentExpression = result.toString()
            binding.textView.text = currentExpression
        }
    }

    private fun setButtonTouchListener(
        button: ImageView,
        soundDown: Int,
        soundUp: Int,
        imageDown: Int,
        imageUp: Int,
        action: () -> Unit
    ) {
        button.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Воспроизводим звук нажатия
                    soundPool.play(soundDown, 1F, 1F, 0, 0, 1F)
                    // Меняем картинку на нажатую
                    button.setImageResource(imageDown)
                    // Выполняем действие
                    action()
                    v.performClick()
                    true
                }
                MotionEvent.ACTION_UP -> {
                    // Воспроизводим звук отпускания
                    soundPool.play(soundUp, 1F, 1F, 0, 0, 1F)
                    // Возвращаем исходную картинку
                    button.setImageResource(imageUp)
                    true
                }
                else -> false
            }
        }
    }

}
