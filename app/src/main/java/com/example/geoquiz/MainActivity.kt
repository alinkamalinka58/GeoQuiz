package com.example.geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GeoQuizScreen()
                }
            }
        }
    }
}

@Composable
fun GeoQuizScreen() {
    var currentIndex by remember { mutableStateOf(0) }
    var isAnswered by remember { mutableStateOf(false) } // Ответил ли пользователь на текущий вопрос
    var score by remember { mutableStateOf(0) } // Счётчик правильных ответов

    val currentQuestion = questionList[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = currentQuestion.text,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Кнопки ответов (скрываются, если уже ответили)
        if (!isAnswered) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    isAnswered = true
                    if (currentQuestion.isTrueAnswer) score++
                }) {
                    Text("True")
                }
                Button(onClick = {
                    isAnswered = true
                    if (!currentQuestion.isTrueAnswer) score++
                }) {
                    Text("False")
                }
            }
        } else {
            // Если ответили, показываем кнопку Next (кроме последнего вопроса)
            if (currentIndex < questionList.size - 1) {
                Button(
                    onClick = {
                        currentIndex++
                        isAnswered = false // Сбрасываем состояние для нового вопроса
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Next")
                }
            } else {
                // Если это был последний вопрос — выводим результат
                Button(
                    onClick = {
                        // Здесь будет всплывающее окно (сделаем на следующем шаге)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Показать результат")
                }
            }
        }
    }
}