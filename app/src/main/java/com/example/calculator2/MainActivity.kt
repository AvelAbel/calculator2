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
        val sound2 = soundPool.load(this, R.raw.pressed, 1)


        setButtonTouchListener(
            binding.buttonDivide,
            sound1,
            sound2,
            R.drawable.pressed_divide,
            R.drawable.unpressed_divide,
            {
                currentExpression += "/"
                binding.textView.text = currentExpression
            }
        )

        setButtonTouchListener(
            binding.buttonMultiply,
            sound1,
            sound2,
            R.drawable.pressed_multiply,
            R.drawable.unpressed_multiply,
            {
                // Добавляем операцию сложения в выражение
                currentExpression += "*"
                binding.textView.text = currentExpression
            }
        )

        setButtonTouchListener(
            binding.buttonMinus,
            sound1,
            sound2,
            R.drawable.pressed_minus,
            R.drawable.unpressed_minus,
            {
                // Добавляем операцию сложения в выражение
                currentExpression += "-"
                binding.textView.text = currentExpression
            }
        )

        setButtonTouchListener(
            binding.buttonPlus,
            sound1,
            sound2,
            R.drawable.pressed_plus,
            R.drawable.unpressed_plus,
            {
                // Добавляем операцию сложения в выражение
                currentExpression += "+"
                binding.textView.text = currentExpression
            }
        )

        setButtonTouchListener(
            binding.buttonDot,
            sound1,
            sound2,
            R.drawable.pressed_dot,
            R.drawable.unpressed_dot,
            {
                if (!currentExpression.contains(".")) {
                    currentExpression += "."
                    binding.textView.text = currentExpression
                }
            }
        )

        setButtonTouchListener(
            binding.buttonEquals,
            sound1,
            sound2,
            R.drawable.pressed_equall,
            R.drawable.unpressed_equall,
            {
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
        )

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
            binding.buttonPlusMinus,
            sound1,
            sound2,
            R.drawable.pressed_minusplus,
            R.drawable.unpressed_minusplus,
            {
                // Добавляем число 1 в выражение
                val expression = Expression(currentExpression)
                val result = -expression.calculate()
                currentExpression = result.toString()
                binding.textView.text = currentExpression
            }
        )

        setButtonTouchListener(
            binding.buttonPercent,
            sound1,
            sound2,
            R.drawable.pressed_percent,
            R.drawable.unpressed_percent,
            {
                // Добавляем число 1 в выражение
                val expression = Expression(currentExpression)
                val result = expression.calculate() / 100
                currentExpression = result.toString()
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
                currentExpression += "1"
                binding.textView.text = currentExpression
            }
        )

        setButtonTouchListener(
            binding.button2,
            sound1,
            sound2,
            R.drawable.pressed2,
            R.drawable.unpressed2,
            {
                currentExpression += "2"
                binding.textView.text = currentExpression
            }
        )

        setButtonTouchListener(
            binding.button3,
            sound1,
            sound2,
            R.drawable.pressed3,
            R.drawable.unpressed3,
            {
                currentExpression += "3"
                binding.textView.text = currentExpression
            }
        )

        setButtonTouchListener(
            binding.button4,
            sound1,
            sound2,
            R.drawable.pressed4,
            R.drawable.unpressed4,
            {
                currentExpression += "4"
                binding.textView.text = currentExpression
            }
        )

        setButtonTouchListener(
            binding.button5,
            sound1,
            sound2,
            R.drawable.pressed5,
            R.drawable.unpressed5,
            {
                currentExpression += "5"
                binding.textView.text = currentExpression
            }
        )

        setButtonTouchListener(
            binding.button6,
            sound1,
            sound2,
            R.drawable.pressed6,
            R.drawable.unpressed6,
            {
                currentExpression += "6"
                binding.textView.text = currentExpression
            }
        )

        setButtonTouchListener(
            binding.button7,
            sound1,
            sound2,
            R.drawable.pressed7,
            R.drawable.unpressed7,
            {
                currentExpression += "7"
                binding.textView.text = currentExpression
            }
        )

        setButtonTouchListener(
            binding.button8,
            sound1,
            sound2,
            R.drawable.pressed8,
            R.drawable.unpressed8,
            {
                currentExpression += "8"
                binding.textView.text = currentExpression
            }
        )

        setButtonTouchListener(
            binding.button9,
            sound1,
            sound2,
            R.drawable.pressed9,
            R.drawable.unpressed9,
            {
                currentExpression += "9"
                binding.textView.text = currentExpression
            }
        )

        setButtonTouchListener(
            binding.button0,
            sound1,
            sound2,
            R.drawable.pressed0,
            R.drawable.unpressed0,
            {
                currentExpression += "0"
                binding.textView.text = currentExpression
            }
        )
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
                    // Изменяем размер текста
                    val length = binding.textView.text.length
                    val size = 80 - (length / 11) * 10
                    binding.textView.textSize = size.toFloat()
                    v.performClick()
                    true
                }
                MotionEvent.ACTION_UP -> {
                    // Воспроизводим звук отпускания
                    //soundPool.play(soundUp, 1F, 1F, 0, 0, 1F)
                    // Возвращаем исходную картинку
                    button.setImageResource(imageUp)
                    true
                }
                else -> false
            }
        }
    }
}
