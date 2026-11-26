
package com.zyverra.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Green = Color(0xFF35D07F)
private val Background = Color(0xFF0D1210)
private val Panel = Color(0xFF19221D)
private val SecondaryText = Color(0xFF9BAAA1)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZyverraApp()
        }
    }
}

@Composable
fun ZyverraApp() {
    var selectedTab by remember { mutableIntStateOf(0) }

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Green,
            background = Background,
            surface = Panel,
            onBackground = Color.White,
            onSurface = Color.White
        )
    ) {
        Scaffold(
            containerColor = Background,
            bottomBar = {
                NavigationBar(
                    containerColor = Panel
                ) {
                    val tabs = listOf(
                        "Главная", "Питание", "Тренировки",
                        "Здоровье", "Аналитика"
                    )
                    val symbols = listOf("⌂", "◉", "◆", "♡", "▥")

                    tabs.forEachIndexed { index, title ->
                        NavigationBarItem(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            icon = {
                                Text(
                                    symbols[index],
                                    fontSize = 21.sp
                                )
                            },
                            label = {
                                Text(title, fontSize = 9.sp)
                            }
                        )
                    }
                }
            }
        ) { padding ->
            when (selectedTab) {
                0 -> HomeScreen(Modifier.padding(padding))
                else -> OtherScreen(
                    selectedTab,
                    Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    "ZYVERRA",
                    color = Green,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )
                Text(
                    "YOUR BODY. YOUR PROGRESS.",
                    color = SecondaryText,
                    fontSize = 9.sp,
                    letterSpacing = 1.5.sp
                )
            }
            Text("●", color = Green, fontSize = 22.sp)
        }

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                "Твой прогресс",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Сегодня — новый шаг к цели",
                color = SecondaryText,
                fontSize = 14.sp
            )
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Panel),
            shape = RoundedCornerShape(22.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text("ПИТАНИЕ СЕГОДНЯ", color = Green, fontSize = 11.sp)
                Text(
                    "0 ккал",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )
                LinearProgressIndicator(
                    progress = { 0f },
                    modifier = Modifier.fillMaxWidth(),
                    color = Green,
                    trackColor = Color(0xFF344239)
                )
                Text(
                    "Цель пока не настроена",
                    color = SecondaryText,
                    fontSize = 12.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Nutrient("БЕЛКИ", "0 г")
                    Nutrient("ЖИРЫ", "0 г")
                    Nutrient("УГЛЕВОДЫ", "0 г")
                }
            }
        }

        Text(
            "ТВОЯ АКТИВНОСТЬ",
            color = Green,
            fontSize = 11.sp,
            letterSpacing = 1.5.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetricCard("Тренировка", "Не отмечена", Modifier.weight(1f))
            MetricCard("Сон", "Нет данных", Modifier.weight(1f))
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Panel),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("ТВОЯ ЦЕЛЬ", color = Green, fontSize = 11.sp)
                Text(
                    "Начни с малого",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Заполняй питание, тренировки и показатели здоровья. " +
                        "ZYVERRA поможет отслеживать изменения.",
                    color = SecondaryText,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )
            }
        }

        Spacer(Modifier.height(8.dp))
    }
}

@Composable
fun Nutrient(title: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(title, color = SecondaryText, fontSize = 9.sp)
        Text(value, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun MetricCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Panel),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(title, color = SecondaryText, fontSize = 12.sp)
            Text(
                value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun OtherScreen(index: Int, modifier: Modifier = Modifier) {
    val titles = listOf(
        "Главная", "Питание", "Тренировки",
        "Здоровье", "Аналитика"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            titles[index],
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Раздел ZYVERRA",
            color = SecondaryText
        )
        Text(
            "Этот модуль мы разработаем на следующих этапах.",
            color = SecondaryText
        )
    }
}
