package eu.lampenlampen.gymtracker.domain

import eu.lampenlampen.gymtracker.data.Exercise
import java.util.*

class ExerciseRepoTestImpl : ExerciseRepo {
	override suspend fun getExercise(exerciseId: UUID): Exercise {
		return Exercise.pushUp
	}
}