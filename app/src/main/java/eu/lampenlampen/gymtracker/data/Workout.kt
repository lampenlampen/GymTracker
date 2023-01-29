package eu.lampenlampen.gymtracker.data

import kotlinx.datetime.Instant
import java.util.*
import kotlin.time.Duration

data class Workout(
    val id: UUID,
    val date: Instant,
    val end: Instant? = null,
    val exercises: List<ExerciseWithSets>
) {
    constructor(date: Instant, end: Instant? = null, exercises: List<ExerciseWithSets>) : this(
        UUID.randomUUID(),
        date,
        end,
        exercises
    )
}

data class WorkoutPlan(
    val name: String,
    val exercises: List<Exercise>,
    val estimatedDuration: Duration,
    val targetedMuscles: List<Muscle>
)