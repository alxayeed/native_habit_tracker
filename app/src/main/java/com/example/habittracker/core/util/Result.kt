package com.example.habittracker.core.util


// Simple Result wrapper using Kotlin's built-in Result but making it easier to handle
// You can also create a more complex sealed class Result<T, E> if needed.
// This focuses on just success/failure for Unit results often used in Auth actions.

typealias SimpleResult = Result<Unit>

inline fun runOperationCatching(block: () -> Unit): SimpleResult {
    return try {
        block()
        Result.success(Unit)
    } catch (e: Exception) {
        println("Operation failed: ${e.message}") // Log the error
        Result.failure(e)
    }
}