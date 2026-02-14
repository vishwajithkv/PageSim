package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


// Bottom bar nav
data class BottomNavItem(
    val label: String,
    val icon: ImageVector
)


data class Feature(
    val icon: ImageVector,
    val title: String,
    val description: String
)

data class AlgorithmInfo(
    val name: String,
    val description: String,
    val referenceString: List<Int>,
    val frameSize: Int,
    val memoryState: List<Int>,
    val nextPage: Int,
    val replaceText: String,
    val advantages: List<String>,
    val disadvantages: List<String>
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PageFlip()
        }
    }
}

@Composable
fun PageFlip() {

    val items = listOf(
        BottomNavItem("Info", Icons.Default.Info),
        BottomNavItem("Simulate", Icons.Default.PlayArrow),
        BottomNavItem("Settings", Icons.Default.Settings)
    )

    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        },
                        label = { Text(item.label) }
                    )
                }
            }
        },
        content = { innerPadding ->
            when (selectedItem) {
                0 -> InfoScreen(Modifier.padding(innerPadding))
                1 -> RunScreen(Modifier.padding(innerPadding))
                2 -> SettingsScreen(Modifier.padding(innerPadding))
            }
        }
    )
}

@Composable
fun ExampleBox(
    algorithm: AlgorithmInfo
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Reference String: ${
                    algorithm.referenceString.joinToString(" ")
                }",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
                text = "Frame Size: ${algorithm.frameSize}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            // Memory Chips Section
            Text(
                text = "Memory:",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                algorithm.memoryState.forEach { page ->
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary
                    ) {
                        Text(
                            text = page.toString(),
                            modifier = Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 8.dp
                            ),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AlgorithmCard(algorithm: AlgorithmInfo) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = algorithm.name,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = algorithm.description,
                style = MaterialTheme.typography.bodyLarge
            )

            ExampleBox(algorithm = algorithm)

            Text(
                text = "Next Page: ${algorithm.nextPage}",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = algorithm.replaceText,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Advantages:",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = algorithm.advantages.joinToString("\n"),
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Disadvantages:",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = algorithm.disadvantages.joinToString("\n"),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun FeatureItem(
    icon: ImageVector,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(27.dp)
        )

        Spacer(modifier = Modifier.width(15.dp))

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun InfoScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "About App",
            modifier = Modifier
                .padding(top = 55.dp, start = 30.dp, bottom = 18.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Left
        )

        Card(
            shape = CardDefaults.elevatedShape,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "PageSim",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "A powerful mobile application for simulating and visualizing various algorithms. Learn, practice, and understand algorithms through interactive simulations.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Card(
            shape = CardDefaults.elevatedShape,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(top = 24.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val features = listOf(
                    Feature(
                        Icons.Default.Code,
                        "Multiple Algorithms",
                        "Support for sorting, searching, and graph algorithms"
                    ),
                    Feature(
                        Icons.Default.Speed,
                        "Real-time Simulation",
                        "Perfect for students and developers learning algorithms"
                    ),
                    Feature(
                        Icons.Default.School,
                        "Educational",
                        "Perfect for students and developers learning algorithms"
                    )
                )

                features.forEach { feature ->
                    FeatureItem(
                        icon = feature.icon,
                        title = feature.title,
                        description = feature.description,
                        modifier = Modifier
                    )

                }
            }
        }

        val algorithms = listOf(

            AlgorithmInfo(
                name = "FIFO",
                description = "Replaces the page that has been in memory the longest.",
                referenceString = listOf(7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2),
                frameSize = 3,
                memoryState = listOf(7, 0, 1),
                nextPage = 2,
                replaceText = "Replace 7 (oldest)",
                advantages = listOf("Simple to implement"),
                disadvantages = listOf("Can suffer from Belady’s Anomaly")
            ),

            AlgorithmInfo(
                name = "LRU",
                description = "Replaces the page that has not been used for the longest time.",
                referenceString = listOf(7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2),
                frameSize = 3,
                memoryState = listOf(2, 0, 1),
                nextPage = 3,
                replaceText = "Replace 1 (least recently used)",
                advantages = listOf("Better performance than FIFO", "No Belady’s Anomaly"),
                disadvantages = listOf("Requires tracking usage history")
            ),

            AlgorithmInfo(
                name = "Optimal",
                description = "Replaces the page that will not be used for the longest time in the future.",
                referenceString = listOf(7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2),
                frameSize = 3,
                memoryState = listOf(2, 0, 3),
                nextPage = 4,
                replaceText = "Replace 0 (Appears later in future)",
                advantages = listOf("Produces minimum page faults"),
                disadvantages = listOf("Not practical in real systems")
            )
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Header Section
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Replacement Algorithms",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "Explore how FIFO, LRU, and Optimal manage memory frames.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Algorithm Cards
            algorithms.forEach { algorithm ->
                AlgorithmCard(algorithm = algorithm)
            }
        }
    }
}

@Composable
fun RunScreen(padding: Modifier) {
    Text(
        text = "Simulate",
        modifier = Modifier
    )
}

@Composable
fun SettingsScreen(padding: Modifier) {
    Text(
        text = "Settings",
        modifier = Modifier
    )
}

