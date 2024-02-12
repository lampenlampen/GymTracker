package eu.lampenlampen.common.myLibrary

object PrivateMyLibraryExamples {
	private val _series = listOf("Igniter Pollywog’s", "Domain")

	val books = listOf(
		MyLibraryBookModel(
			listOf(1),
			"Pokie Punk",
			null,
			null,
			null,
			"Christian Counseling Center",
			null,
			"978-9763661154",
			false,
			null,
			null,
			3,
			null,
			null,
			0
		),
		MyLibraryBookModel(
			listOf(2, 3),
			"Sweet Talker 8",
			MyLibrarySeriesModel(_series[0], 1.0),
			listOf("Fantasy"),
			"06/03/1998",
			"La Frontera",
			249,
			"978-7950381007",
			true,
			null,
			null,
			2,
			null,
			null,
			0
		),
		MyLibraryBookModel(
			listOf(4),
			"Flame",
			MyLibrarySeriesModel(_series[0], 2.0),
			listOf("Fantasy"),
			"07/03/1998",
			"La Frontera",
			391,
			"978-6067074291",
			false,
			null,
			null,
			4,
			null,
			null,
			0
		),
		MyLibraryBookModel(
			listOf(3, 4),
			"Ms Piggy Revenge",
			MyLibrarySeriesModel(_series[1], 2.0),
			listOf("Science Fiction"),
			"01/11/1843",
			"Cygnus Pub",
			9136,
			"978-7873881493",
			false,
			null,
			"In post mean shot ye. There out her child sir his lived. Design at uneasy me season of branch on praise esteem. Abilities discourse believing consisted remaining to no. Mistaken no me denoting dashwood as screened. Whence or esteem easily he on. Dissuade husbands at of no if disposal.\n" +
					"\n" +
					"At as in understood an remarkably solicitude. Mean them very seen she she. Use totally written the observe pressed justice. Instantly cordially far intention recommend estimable yet her his. Ladies stairs enough esteem add fat all enable. Needed its design number winter see. Oh be me sure wise sons no. Piqued ye of am spirit regret. Stimulated discretion impossible admiration in particular conviction up.",
			5,
			null,
			null,
			0
		)
	)

	val authors = listOf(
		MyLibraryAuthorModel(1, "Zachary", "Turner"),
		MyLibraryAuthorModel(2, "Ace", "Colon"),
		MyLibraryAuthorModel(3, "Reuben", "Jacobs"),
		MyLibraryAuthorModel(4, "Karen", "Castro"),
	)

	val series = listOf(
		MyLibrarySeriesModel("Igniter Pollywog’s", 2.0),
		MyLibrarySeriesModel("Domain", 1.0)
	)
}