package com.example.reelcounter.domain

import com.example.reelcounter.data.ReelRepository

/**
 * Use case for incrementing the reel count.
 * Part of the domain layer following clean architecture.
 */
class IncrementReelUseCase(
    private val repository: ReelRepository
) {
    /**
     * Increments the current day's reel count by 1.
     */
    fun execute() {
        repository.increment()
    }
}
