package com.algar.repository.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

interface CoroutineLaunch {
    fun io(work: suspend () -> Unit): Job
    fun main(work: suspend () -> Unit): Job
}

class CoroutineLaunchImpl : CoroutineLaunch {
    override fun io(work: suspend (() -> Unit)): Job = CoroutineScope(IO).launch { work() }
    override fun main(work: suspend (() -> Unit)): Job = CoroutineScope(Main).launch { work() }
}