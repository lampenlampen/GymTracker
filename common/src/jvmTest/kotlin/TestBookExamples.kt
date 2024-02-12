import eu.lampenlampen.common.dataModels.*
import java.util.*

object TestBookExamples {
	private val authorId = UUID.randomUUID()

	val books = listOf(
		Book(
			BookId.NewId(),
			"Geheime Botschaften",
			"Die Kunst der Verschlüsselung von der Antike bis in die Zeiten des Internets",
			listOf(Author(UUID.randomUUID(), "Simon", "Singh")),
			"Ob im Krieg, in der Liebe oder im Wirtschaftsleben - seit Jahrtausenden arbeitet die Menscheit mit verschlüsselten Informationen und geheimen Codes. Die Methoden wurden immer raffinierter bis in die Gegenwart, wo im Zeitalter der Computernetze Billionen von Daten kuriseren.",
			null,
			listOf(Category("Technik")),
			Publisher("dtv"),
			ISBN("978-3423330718"),
			BookType.PhysicalBook,
			false,
			"04/02/2015"
		),
		Book(
			BookId.NewId(),
			"Schattenchronik",
			"Das Erwachen",
			listOf(Author(UUID.randomUUID(), "Andreas", "Suchanek")),
			"Die Welt, wie du sie kennst, ist eine Lüge! Seit über einem Jahrhundert verbirgt der Wall die magische Gesellschaftvor Menschenaugen, garantiert Friede und Gleichheit zwischen Menschen und Magiern. doch in den Schatten tobt ein Krieg um die Vorherrschaft. Jenifer Danvers ist eine Lichtkämpferin. Als ihr Freund und Kampfgefährte stirbt, erwacht mit Alexander Kent eine neuer Erbe der Macht, der von ihr in die Welt der Magie eingeführt werden muss. Keiner von beiden ahnt, dass das Gleichgewicht der Kräfte außer Kontrolle geraten ist. Das Böse hälholt zum Schlag aus, um den Wall endgültig zu zerschmettern.",
			BookSeries("Das Erbe der Macht", 1),
			listOf(Category("Fantasy")),
			Publisher("Lindwurm"),
			ISBN("978-3-948695-40-8"),
			BookType.PhysicalBook,
			false,
			"23/06/2004"
		),
		Book(
			BookId.NewId(),
			"Der Bluthund",
			null,
			listOf(Author(authorId, "Lee", "Child")),
			"Der ehemalige Militärpolizist Jack Reacher entdeckt zufällig bei einem Pfandleiher einen Abschlussring der Militärakademie West Point. Warum trennt sich jemand von einer so hart errungenen Trophäe? Einem Impuls folgend beschliest er, die ursprüngliche Besitzerin aufzuspüren und ihr diese Auszeichnung zurückzubringen. Doch der Ring ging bereits durch viele Hände, und plötzlich befindet sich Reacher im Netz einer kriminellen Organisation mit Verbindungen in die höchseten Kreise der Gesellschaft. Ein Preis wird auf sein Kopf ausgestzt, Killer haften sich an seine Fersen. Es gibt eben Leute, mit denen man sich nicht anlegen sollte - zum Beispiel mit Jack Reacher!",
			BookSeries("Jack Reacher", 22),
			listOf(Category("Krimi")),
			Publisher("blanvalet"),
			ISBN("978-3-7341-1077-1"),
			BookType.PhysicalBook,
			false,
			"35/14/1804"
		),
		Book(
			BookId.NewId(),
			"Ausgeliefert",
			null,
			listOf(Author(authorId, "Lee", "Child")),
			"Ein Mann und eine Frau treffen zufällig auf einer Straße in Chicago zusammen. Plötzlich tauchen zwei Männer auf entführen die beiden mit vorgehaltener Waffe. Sie werden mit Handschellen aneinandergekettet, in einen Lieferwagen geworfen und in die tiefen Wälder Montanas gebracht. Die Frau ist Holly Johnson, Agentin des FBI und Tochter eines der ranghöchsten Generäle Washingtons. Der Mann ist Jack Reacher...",
			BookSeries("Jack Reacher", 2),
			listOf(Category("Krimi")),
			Publisher("blanvalet"),
			ISBN("978-3-734-10513-5"),
			BookType.PhysicalBook,
			false,
			"64/03/3046"
		),
		Book(
			BookId.NewId(),
			"Thinking, Fast and Slow",
			null,
			listOf(Author(UUID.randomUUID(), "Daniel", "Kahneman")),
			"The phenomenal New York Times Bestseller by Nobel Prize-winner Daniel Kahneman, Thinking Fast and Slow offers a whole new look at the way our minds work, and how we make decisions. Why is there more chance we'll believe something if it's in a bold type face? Why are judges more likely to deny parole before lunch? Why do we assume a good-looking person will be more competent? The answer lies in the two ways we make choices: fast, intuitive thinking, and slow, rational thinking. This book reveals how our minds are tripped up by error and prejudice (even when we think we are being logical), and gives you practical techniques for slower, smarter thinking. It will enable to you make better decisions at work, at home, and in everything you do.",
			null,
			listOf(Category("Psychologie")),
			Publisher("penguin"),
			ISBN("978-0-141-03357-0"),
			BookType.PhysicalBook,
			false,
			"2016/67/2617"
		),
		Book(
			BookId.NewId(),
			"A Mind for Numbers",
			"How to excel at maths and Science",
			listOf(Author(UUID.randomUUID(), "Barbara", "Oakley")),
			"Whether you are a student struggling to fulfill a math or science requirement, or you are embarking on a career change that requires a new skill set, A Mind for Numbers offers the tools you need to get a better grasp of that intimidating material. Engineering professor Barbara Oakley knows firsthand how it feels to struggle with math. She flunked her way through high school math and science courses, before enlisting in the army immediately after graduation. When she saw how her lack of mathematical and technical savvy severely limited her options—both to rise in the military and to explore other careers—she returned to school with a newfound determination to re-tool her brain to master the very subjects that had given her so much trouble throughout her entire life.\n" +
					" \n" +
					"In A Mind for Numbers, Dr. Oakley lets us in on the secrets to learning effectively—secrets that even dedicated and successful students wish they’d known earlier. Contrary to popular belief, math requires creative, as well as analytical, thinking. Most people think that there’s only one way to do a problem, when in actuality, there are often a number of different solutions—you just need the creativity to see them. For example, there are more than three hundred different known proofs of the Pythagorean Theorem. In short, studying a problem in a laser-focused way until you reach a solution is not an effective way to learn. Rather, it involves taking the time to step away from a problem and allow the more relaxed and creative part of the brain to take over. The learning strategies in this book apply not only to math and science, but to any subject in which we struggle. We all have what it takes to excel in areas that don't seem to come naturally to us at first, and learning them does not have to be as painful as we might think.",
			null,
			listOf(Category("Psychologie")),
			Publisher("tarcher perigee"),
			ISBN("978-0-399-16524-5"),
			BookType.PhysicalBook,
			false,
			"94/02/3048"
		)
	)
}