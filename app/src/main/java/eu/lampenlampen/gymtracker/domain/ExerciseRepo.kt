package eu.lampenlampen.gymtracker.domain

import eu.lampenlampen.gymtracker.data.Exercise
import java.util.UUID

interface ExerciseRepo {
	suspend fun getExercise(exerciseId: UUID): Exercise

	suspend fun getAllExercises() : List<Exercise>
}