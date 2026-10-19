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
                GeoQuizScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeoQuizScreen() {
    var currentIndex by remember { mutableStateOf(0) }
    var isAnswered by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }
    var showResultDialog by remember { mutableStateOf(false) }

    val currentQuestion = questionList[currentIndex]

    // Scaffold — это "каркас" экрана (шапка + контент)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GeoTest", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        // Внутри контента учитываем отступы от шапки (innerPadding)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Важно: чтобы шапка не перекрывала текст
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Текст вопроса
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
                            isAnswered = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Next")
                    }
                } else {
                    // Если это был последний вопрос — кнопка вызова результата
                    Button(
                        onClick = { showResultDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Показать результат")
                    }
                }
            }
        }
    }

    // Всплывающее окно с результатом
    if (showResultDialog) {
        AlertDialog(
            onDismissRequest = { showResultDialog = false },
            title = { Text("Тест завершён!") },
            text = {
                Text("Правильных ответов: $score из ${questionList.size}", fontSize = 18.sp)
            },
            confirmButton = {
                TextButton(onClick = {
                    showResultDialog = false
                    currentIndex = 0
                    score = 0
                    isAnswered = false
                }) {
                    Text("Начать заново")
                }
            }
        )
    }
}