package eu.lampenlampen.gymtracker.data

import java.util.*

data class Exercise(
	val id: UUID,
	val name: String,
	val notes: String? = null,
	val targetedMuscles: List<Muscle>,
	val equipment: Equipment
) {
	constructor(
		name: String,
		notes: String? = null,
		targetedMuscles: List<Muscle>,
		equipment: Equipment
	) : this(UUID.randomUUID(), name, notes, targetedMuscles, equipment)

	companion object {
		val pushUp = Exercise(
			"Push-Up",
			"",
			listOf(Muscle.Bicpes, Muscle.Lats, Muscle.LowerBack, Muscle.Traps),
			Equipment("Bodyweight", Equipments.Rack)
		)

		val dip = Exercise(
			"Dip",
			"",
			listOf(Muscle.Chest, Muscle.Tricpes),
			Equipment("Parallel Bar", Equipments.Rack)
		)
	}
}

data class ExerciseWithSets(
	val exercise: Exercise,
	val sets: List<Sets>
)

data class Sets(
	val weight: Weight,
	val repetition: Int
)


data class Equipment(
	val name: String, val equipment: Equipments
)

data class Weight(
	val weight: Double,
	val unit: String = "kg"
)

data class TrainingSession(
	val exercises: List<Exercise> = emptyList(),
	val start: Date?,
	val end: Date?,
)

