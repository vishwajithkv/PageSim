package com.example.myapplication

import java.util.concurrent.ArrayBlockingQueue

data class Step(
    val isHit: Boolean,
    val currentFrame: List<String>,
    val evictedItem: String?
)

fun firstInFirstOutAlgorithm(list: List<String>, frameSize: Int): List<Step> {
    val steps = ArrayList<Step>()
    val queue = ArrayBlockingQueue<String>(frameSize)
    for (item in list) {
        if (queue.contains(item)) {
            steps.add(
                Step(
                    isHit = true,
                    currentFrame = queue.toList(),
                    evictedItem = null
                )
            )
        } else if (queue.size != frameSize) {
            queue.add(item)
            steps.add(
                Step(
                    isHit = false,
                    currentFrame = queue.toList(),
                    evictedItem = null
                )
            )
        } else {
            val evictedItem = queue.remove()
            queue.add(item)
            steps.add(
                Step(
                    isHit = false,
                    currentFrame = queue.toList(),
                    evictedItem = evictedItem
                )
            )
        }
    }
    return steps
}