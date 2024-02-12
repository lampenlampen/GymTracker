package commonMainTest.cqrs

import eu.lampenlampen.common.cqrs.Query
import eu.lampenlampen.common.cqrs.QueryHandler
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private class TestQuery : Query<Boolean>

class QueryHandlerTest : FunSpec({
	test("Test QueryHandler returns true") {
		val expected = true

		val a: QueryHandler<TestQuery, Boolean> = object : QueryHandler<TestQuery, Boolean> {
			override fun execute(query: TestQuery) = expected
		}

		val result = a.execute(TestQuery())

		result shouldBe expected
	}
})