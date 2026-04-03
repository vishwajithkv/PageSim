package com.leoxvis.pagesim.ui.run

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leoxvis.pagesim.Step
import com.leoxvis.pagesim.firstInFirstOutAlgorithm
import com.leoxvis.pagesim.leastRecentlyUsedAlgorithm
import com.leoxvis.pagesim.optimalAlgorithm
import com.leoxvis.pagesim.ui.components.SimulationResults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunScreen(modifier: Modifier = Modifier) {

    var inputString by remember { mutableStateOf("7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2") }
    var inputFrame by remember { mutableStateOf("3") }
    var steps by remember { mutableStateOf<List<Step>>(emptyList()) }

    val algorithms = listOf(
        "FIFO (First-In-First-Out)",
        "LRU (Least Recently Used)",
        "Optimal (Future Look Up)"
    )

    val frameSize = inputFrame.toIntOrNull()
    val referenceString =
        inputString.split(",").map { it.trim() }.filter { it.matches(Regex("\\d+")) }

    var expanded by remember { mutableStateOf(false) }
    var selectedAlgorithm by remember { mutableStateOf(algorithms[0]) }
    var simFrameSize by remember { mutableStateOf<Int?>(null) }
    var simReferenceList by remember { mutableStateOf<List<String>>(emptyList()) }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()

    ) {
        item {
            Text(
                text = "Simulate",
                modifier = Modifier
                    .padding(top = 55.dp, start = 30.dp, bottom = 18.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.headlineLarge.copy(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                ),
            ) {
                Row(modifier = Modifier.padding(8.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Select an algorithm and press run to start the simulation",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainerHigh)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    // Algorithm Dropdown
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {

                        OutlinedTextField(
                            value = selectedAlgorithm,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Algorithm") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                            },
                            modifier = Modifier
                                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable)
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {

                            algorithms.forEach { algorithm ->
                                DropdownMenuItem(
                                    text = { Text(algorithm) },
                                    onClick = {
                                        selectedAlgorithm = algorithm
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }


                    // Reference String
                    OutlinedTextField(
                        value = inputString,
                        onValueChange = {
                            inputString = it
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text("Reference String (comma separated)") },
                        supportingText = { Text("Enter page numbers separated by commas") },
                        modifier = Modifier.fillMaxWidth()
                    )


                    // Frame Size
                    OutlinedTextField(
                        value = inputFrame.toString(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            if (it.all { ch -> ch.isDigit() }) {
                                inputFrame = it
                            }
                        },
                        label = { Text("Frame Size") },
                        supportingText = { Text("Number of frames in memory") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Button(
                            onClick = {
                                if (frameSize == null || frameSize <= 0) return@Button
                                simFrameSize = frameSize
                                simReferenceList = referenceString
                                steps = when (selectedAlgorithm) {
                                    "FIFO (First-In-First-Out)" ->
                                        firstInFirstOutAlgorithm(
                                            list = referenceString,
                                            frameSize = frameSize
                                        )

                                    "LRU (Least Recently Used)" ->
                                        leastRecentlyUsedAlgorithm(
                                            list = referenceString,
                                            frameSize = frameSize
                                        )

                                    "Optimal (Future Look Up)" ->
                                        optimalAlgorithm(
                                            list = referenceString,
                                            frameSize = frameSize
                                        )

                                    else -> emptyList()
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(28.dp)
                        ) {
                            Icon(
                                Icons.Filled.PlayArrow,
                                contentDescription = "Run",
                                modifier = Modifier.size(22.dp)
                            )

                            Spacer(Modifier.width(6.dp))

                            Text(
                                "Run Algorithm",
                                fontSize = 12.sp,
                                maxLines = 1,
                            )
                        }

                        Button(
                            onClick = { steps = emptyList() },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(28.dp)
                        ) {
                            Icon(
                                Icons.Filled.Replay,
                                contentDescription = "Reset",
                                modifier = Modifier.size(22.dp)
                            )

                            Spacer(Modifier.width(8.dp))

                            Text(
                                "Reset",
                                fontSize = 12.sp,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
            if (steps.isNotEmpty() && simFrameSize != null && simFrameSize!! > 0) {
                SimulationResults(
                    steps = steps,
                    frameSize = simFrameSize!!,
                    referenceString = simReferenceList
                )
            }
        }
    }
}
