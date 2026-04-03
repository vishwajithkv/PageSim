package com.leoxvis.pagesim.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leoxvis.pagesim.Step

@Composable
fun SimulationResults(
    steps: List<Step>,
    frameSize: Int,
    referenceString: List<String>,
) {

    if (steps.isEmpty()) return

    val pageFaults = steps.count { !it.isHit }
    val pageHits = steps.count { it.isHit }
    val hitRate =
        if (steps.isNotEmpty()) (pageHits.toFloat() / steps.size) * 100 else 0f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {

            Column(
                modifier = Modifier.padding(32.dp)
            ) {

                Text(
                    text = "Results Summary",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {
                        Text(
                            "Page Faults",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            pageFaults.toString(),
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Column {
                        Text(
                            "Page Hits",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            pageHits.toString(),
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Column {
                        Text(
                            "Hit Rate",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            "${"%.1f".format(hitRate)}%",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(20.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    "Simulation Steps",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(12.dp))

                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            "Step",
                            modifier = Modifier.width(40.dp),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            "Page",
                            modifier = Modifier.width(60.dp),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            "Memory Frames",
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            "Status",
                            modifier = Modifier.width(70.dp),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }


                    steps.forEachIndexed { index, step ->
                        val page = referenceString[index]
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            /*
                            Step number
                             */

                            Text(
                                (index + 1).toString(),
                                modifier = Modifier.width(40.dp)
                            )

                            Box(
                                modifier = Modifier
                                    .width(60.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            MaterialTheme.colorScheme.primary,
                                            RoundedCornerShape(50)
                                        )
                                        .padding(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        )
                                ) {
                                    Text(
                                        page ?: "-",
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .horizontalScroll(rememberScrollState())
                            ) {

                                repeat(frameSize) { frameIndex ->

                                    val value =
                                        step.currentFrame.getOrNull(frameIndex)

                                    val background =
                                        if (value == null)
                                            Color.LightGray
                                        else
                                            MaterialTheme.colorScheme.primary

                                    Box(
                                        modifier = Modifier
                                            .padding(end = 6.dp)
                                            .size(36.dp)
                                            .background(
                                                background,
                                                RoundedCornerShape(10.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            value ?: "-",
                                            color = if (value == null) Color.DarkGray else MaterialTheme.colorScheme.onPrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .width(70.dp),
                                contentAlignment = Alignment.Center
                            ) {

                                val color =
                                    if (step.isHit)
                                        Color(0xFF2E7D32)
                                    else
                                        Color(0xFFD32F2F)

                                Box(
                                    modifier = Modifier
                                        .background(
                                            color,
                                            RoundedCornerShape(50)
                                        )
                                        .padding(
                                            horizontal = 12.dp,
                                            vertical = 4.dp
                                        )
                                ) {

                                    Text(
                                        if (step.isHit) "HIT" else "FAULT",
                                        color = Color.White,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}