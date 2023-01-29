package eu.lampenlampen.gymtracker.domain

import eu.lampenlampen.gymtracker.data.*
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.minutes

class WorkoutRepoTestImpl : WorkoutRepo {
    private val workouts = mutableListOf<Workout>()

    override suspend fun getAllWorkouts(): List<Workout> {
        return workouts
    }

    override suspend fun getWorkoutForDate(date: Instant): List<Workout> {
        return workouts.filter { it.date < date }
    }

    override suspend fun saveWorkout(workout: Workout) {
        workouts.add(workout)
    }

    override suspend fun deleteWorkout(workout: Workout) {
        workouts.remove(workout)
    }

    companion object {
        @Deprecated("Only for testing")
        fun getTestWorkout() = Workout(
            Clock.System.now(),
            Clock.System.now() + 2.minutes,
            listOf(
                ExerciseWithSets(Exercise.pushUp, listOf(Sets(Weight(10.0), 5), Sets(Weight(15.0), 5))),
                ExerciseWithSets(Exercise.dip, listOf()),
                ExerciseWithSets(Exercise.pushUp, listOf(Sets(Weight(10.0), 5), Sets(Weight(15.0), 5))),
                ExerciseWithSets(Exercise.dip, listOf(Sets(Weight(25.0), 7)))
            ))
    }
}