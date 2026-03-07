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
    val frames = mutableListOf<String>()
    for (item in list) {
        if (queue.contains(item)) {
            steps.add(
                Step(
                    isHit = true,
                    currentFrame = frames.toList(),
                    evictedItem = null
                )
            )
        } else if (queue.size != frameSize) {
            queue.add(item)
            frames.add(item)
            steps.add(
                Step(
                    isHit = false,
                    currentFrame = frames.toList(),
                    evictedItem = null
                )
            )
        } else {
            val evictedItem = queue.remove()
            val index = frames.indexOf(evictedItem)
            frames[index] = item

            queue.add(item)
            steps.add(
                Step(
                    isHit = false,
                    currentFrame = frames.toList(),
                    evictedItem = evictedItem
                )
            )
        }
    }
    return steps
}

fun leastRecentlyUsedAlgorithm(list: List<String>, frameSize: Int): List<Step> {
    val steps = ArrayList<Step>()
    val frames = mutableListOf<String>()
    val linked = LinkedHashMap<String, String>(frameSize, 0.75F, true)
    for (item in list) {
        if (linked.contains(item)) {
            linked[item]
            steps.add(
                Step(
                    isHit = true,
                    currentFrame = frames.toList(),
                    evictedItem = null
                )
            )
        } else if (linked.size != frameSize) {
            linked[item] = item
            frames.add(item)
            steps.add(
                Step(
                    isHit = false,
                    currentFrame = frames.toList(),
                    evictedItem = null
                )
            )
        } else {
            val lruKey = linked.keys.first()
            linked.remove(lruKey)
            linked[item] = item

            val index = frames.indexOf(lruKey)
            frames[index] = item

            steps.add(
                Step(
                    isHit = false,
                    currentFrame = frames.toList(),
                    evictedItem = lruKey
                )
            )
        }
    }
    return steps
}

fun optimalAlgorithm(list: List<String>, frameSize: Int): List<Step> {
    val steps = ArrayList<Step>()
    val frames = mutableListOf<String>()


    for (i in list.indices) {
        val item = list[i]

        if (frames.contains(item)) {
            steps.add(
                Step(
                    isHit = true,
                    currentFrame = frames.toList(),
                    evictedItem = null
                )
            )
        } else if (frames.size != frameSize) {
            frames.add(item)
            steps.add(
                Step(
                    isHit = false,
                    currentFrame = frames.toList(),
                    evictedItem = null
                )
            )
        } else {
            var victim = frames[0]
            var farthestIndex = -1

            for (page in frames) {
                val nextUse = list.subList(i + 1, list.size).indexOf(page)

                if (nextUse == -1) {
                    victim = page
                    break
                }

                if (nextUse > farthestIndex) {
                    farthestIndex = nextUse
                    victim = page
                }
            }

            val index = frames.indexOf(victim)
            frames[index] = item

            steps.add(
                Step(
                    isHit = false,
                    currentFrame = frames.toList(),
                    evictedItem = victim
                )
            )
        }
    }
    return steps
}

