package com.example.karans_calc  // Replace with your actual package name

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorApp()
        }
    }
}

@Composable
fun CalculatorApp() {
    var result by remember { mutableStateOf("0") }  // Use String directly since we display the result in a TextField
    var firstNumber by remember { mutableStateOf(0.0f) }
    var secondNumber by remember { mutableStateOf(0.0f) }
    var operation by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = result,
            onValueChange = { result = it },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        val buttons = listOf("7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "C", "0", "=", "+")
        buttons.chunked(4).forEach { row ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                row.forEach { button ->
                    Button(
                        onClick = {
                            when (button) {
                                "C" -> {
                                    result = "0"
                                    firstNumber = 0.0f
                                    secondNumber = 0.0f
                                    operation = ""
                                }
                                "+", "-", "*", "/" -> {
                                    firstNumber = result.toFloat()
                                    operation = button
                                    result = ""
                                }
                                "=" -> {
                                    secondNumber = result.toFloat()
                                    result = when (operation) {
                                        "+" -> (firstNumber + secondNumber).toString()
                                        "-" -> (firstNumber - secondNumber).toString()
                                        "*" -> (firstNumber * secondNumber).toString()
                                        "/" -> if (secondNumber != 0.0f) (firstNumber / secondNumber).toString() else "Error"
                                        else -> result
                                    }
                                    operation = ""
                                    firstNumber = result.toFloat()
                                }
                                else -> {
                                    result = if (result == "0") button else result + button
                                }
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(button)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculatorApp()
}
