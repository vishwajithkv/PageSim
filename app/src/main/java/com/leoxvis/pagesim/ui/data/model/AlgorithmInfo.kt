package com.leoxvis.pagesim.ui.data.model

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