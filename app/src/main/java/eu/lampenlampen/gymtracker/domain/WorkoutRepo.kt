package eu.lampenlampen.gymtracker.domain

import eu.lampenlampen.gymtracker.data.Workout
import kotlinx.datetime.Instant

interface WorkoutRepo {
	suspend fun getAllWorkouts() : List<Workout>

	suspend fun getWorkoutForDate(date: Instant): List<Workout>

	suspend fun saveWorkout(workout: Workout)

	suspend fun deleteWorkout(workout: Workout)
}
