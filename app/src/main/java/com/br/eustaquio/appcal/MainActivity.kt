package com.br.eustaquio.appcal

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var firstOperand: Double? = null
    private var operator: String? = null

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
        display.text = "0"
        firstOperand = null
        operator = null
    }

    private fun appendNumber(number: String) {
        // Se o display estiver zerado, substitui por um número
        if (display.text == "0" || display.text.isEmpty()) {
            display.text = number
        } else {
            display.append(number) // Adiciona o número ao final
        }
    }

    private fun setOperator(op: String) {
        // Salva o primeiro operando e o operador, e mostra no display
        if (firstOperand == null) {
            firstOperand = display.text.toString().toDoubleOrNull()
            operator = op
            display.append(" $op ") // Adiciona o operador ao display
        } else {
            // Se já há um operador, só substitui pelo novo
            operator = op
            // Substitui o operador anterior
            val currentText = display.text.toString().split(" ")
            if (currentText.size == 3) { // Exemplo: "2 + 2"
                display.text = "${currentText[0]} $op ${currentText[2]}"
            }
        }
    }

    private fun calculateResult() {
        val currentText = display.text.toString().split(" ")
        if (currentText.size == 3) {
            val secondOperand = currentText[2].toDoubleOrNull()
            if (firstOperand != null && secondOperand != null) {
                val result = when (operator) {
                    "+" -> firstOperand!! + secondOperand
                    "-" -> firstOperand!! - secondOperand
                    "*" -> firstOperand!! * secondOperand
                    "/" -> if (secondOperand != 0.0) {
                        firstOperand!! / secondOperand
                    } else {
                        display.text = "Erro: Divisão por Zero"
                        return
                    }
                    else -> null
                }
                display.text = result.toString() // Mostra o resultado no display
                firstOperand = result // Permite continuar com o resultado
                operator = null
            }
        }
    }
}
