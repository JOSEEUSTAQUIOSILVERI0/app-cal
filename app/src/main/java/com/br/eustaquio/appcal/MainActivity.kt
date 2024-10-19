package com.br.eustaquio.appcal

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var operator: String? = null
    private var firstOperand: String? = null
    private var secondOperand: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa o campo de exibição
        display = findViewById(R.id.display)

        // Configura o botão C
        val buttonClear: Button = findViewById(R.id.buttonClear)
        buttonClear.setOnClickListener { clearDisplay() }

        // Configura o botão de igual
        val buttonEqual: Button = findViewById(R.id.buttonEqual)
        buttonEqual.setOnClickListener { calculateResult() }

        // Configura os botões numéricos
        setupNumberButtons()
        // Configura os botões de operador
        setupOperatorButtons()
    }

    private fun setupNumberButtons() {
        val buttons = listOf(
            R.id.button0, R.id.button1, R.id.button2,
            R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8,
            R.id.button9
        )
        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { appendNumber((it as Button).text.toString()) }
        }
    }

    private fun setupOperatorButtons() {
        val operators = listOf(
            R.id.buttonAdd, R.id.buttonSubtract,
            R.id.buttonMultiply, R.id.buttonDivide
        )
        for (id in operators) {
            findViewById<Button>(id).setOnClickListener { setOperator((it as Button).text.toString()) }
        }
    }

    private fun clearDisplay() {
        display.text = ""
        firstOperand = null
        secondOperand = null
        operator = null
    }

    private fun appendNumber(number: String) {
        display.append(number)
    }

    private fun setOperator(op: String) {
        if (firstOperand == null) {
            firstOperand = display.text.toString()
            operator = op
            display.append(op)
        }
    }

    private fun calculateResult() {
        if (firstOperand != null) {
            secondOperand = display.text.toString().substringAfter(operator!!)
            val result = when (operator) {
                "+" -> firstOperand!!.toDouble() + secondOperand!!.toDouble()
                "-" -> firstOperand!!.toDouble() - secondOperand!!.toDouble()
                "*" -> firstOperand!!.toDouble() * secondOperand!!.toDouble()
                "/" -> if (secondOperand!!.toDouble() != 0.0) {
                    firstOperand!!.toDouble() / secondOperand!!.toDouble()
                } else {
                    display.text = "Erro: Divisão por Zero"
                    return
                }
                else -> null
            }
            display.text = result.toString()
            firstOperand = result.toString()
            operator = null
        }
    }
}
