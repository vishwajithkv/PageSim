package com.leoxvis.pagesim.ui.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.leoxvis.pagesim.ui.components.AlgorithmCard
import com.leoxvis.pagesim.ui.components.FeatureItem
import com.leoxvis.pagesim.ui.data.model.AlgorithmInfo
import com.leoxvis.pagesim.ui.data.model.Feature

@Composable
fun InfoScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    )
    {
        item {
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
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    val features = listOf(
                        Feature(
                            Icons.Default.Code,
                            "Multiple Algorithms",
                            "Support for FIFO, LRU, and Optimal page replacement"
                        ),
                        Feature(
                            Icons.Default.Speed,
                            "Real-time Simulation",
                            "Watch page replacement algorithms execute step by step"
                        ),
                        Feature(
                            Icons.Default.School,
                            "Educational",
                            "Perfect for students learning page replacement algorithms"
                        )
                    )

                    features.forEachIndexed { index, feature ->
                        FeatureItem(
                            icon = feature.icon,
                            title = feature.title,
                            description = feature.description,
                        )
                        if (index < features.lastIndex) {
                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                                thickness = 1.dp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
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
}

