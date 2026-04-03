package com.leoxvis.pagesim.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leoxvis.pagesim.ui.data.model.AlgorithmInfo

@Composable
fun AlgorithmCard(algorithm: AlgorithmInfo) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                alpha = 0.5f
            )
        )
    ) {
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