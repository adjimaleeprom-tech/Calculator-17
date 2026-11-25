package com.example.calculator_17

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView //แสดงผลลัพธ์ตัวแรก
    private var current = "" //ค่าที่กำลังพิมพ์อยู่
    private var operator = "" //เครื่องหมายที่เลือก เช่น +,-,*,/
    private var firstNumber = 0.0 //ตัวเลขแรก
    private var secondNumber = 0.0
    private var result = 0.0
    private var inNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        display = findViewById(R.id.txtDisplay)

        val numberButton = listOf(
            R.id.btn0,
            R.id.btn1,
            R.id.btn2,
            R.id.btn3,
            R.id.btn4,
            R.id.btn5,
            R.id.btn6,
            R.id.btn7,
            R.id.btn8,
            R.id.btn9,
        )

        for (id in numberButton) {
            findViewById<Button>(id).setOnClickListener {
                numberClicked((it as Button).text.toString())
            }
        }

        //เขื่อมโยงปุ่มคำนวณ
        findViewById<Button>(R.id.btnPlus).setOnClickListener { operatorClicked("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { operatorClicked("-") }
        findViewById<Button>(R.id.btnmul).setOnClickListener { operatorClicked("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { operatorClicked("/") }
        findViewById<Button>(R.id.btnMod).setOnClickListener { operatorClicked("%") }

        //เชื่อมโยงปุ่มพิเศษ
        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.btnAc).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnDel).setOnClickListener { backspace() }
        findViewById<Button>(R.id.btnpms).setOnClickListener { toggleSign() }
        findViewById<Button>(R.id.btnj).setOnClickListener { addDot() }
    }

    private fun numberClicked(value: String) {
        if (inNewOperation) {
            display.text = "" //ล้างค่าใน TextView
            inNewOperation = false
        }
        current += value
        display.text = current
    }
    private fun   operatorClicked(op: String) {
        if (current.isNotEmpty()){
            firstNumber = current.toDouble()
            operator = op //เก็บเครื่องหมายที่เลือก
            current = "" //ล้างค่าใน TextView
            inNewOperation = true
        }
    }
    //ฟังก์ชันของปุ่ม AC (all clear)
    private fun calculateResult() {
        if (current.isNotEmpty()) {
            secondNumber = current.toDouble()
            result = when (operator) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "*" -> firstNumber * secondNumber
                "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else Double.NaN
                "%" -> if (secondNumber != 0.0) firstNumber % secondNumber else Double.NaN
                else -> 0.0
            }
            display.text = if (result.isNaN()) "Cannot divide by zero" else result.toString()
            current = result.toString()
            inNewOperation = true
        }

    }
    private fun clearAll(){
        current = ""
        operator = ""
        firstNumber = 0.0
        secondNumber = 0.0
        result = 0.0
        display.text = "0"
        inNewOperation = true
    }

    private fun addDot() {
        if (!current.contains(".")) {
            if (current.isEmpty()) current = "0."
            current += "."
            display.text = current
        }
    }

    private fun toggleSign() {
        if (current.isNotEmpty() && current != "0") {
            current = if (current.startsWith("-")) {
                current.substring(1)
            } else {
                "-$current"
            }
            display.text = current
        }
    }

    private fun backspace() {
        if (inNewOperation) return

        if (current.isNotEmpty()) {
            current = current.dropLast(1)

            if (current == "-" || current.isEmpty()) {
                current = ""
                display.text = "0"
            } else {
                display.text = current
            }
        }
    }




}






