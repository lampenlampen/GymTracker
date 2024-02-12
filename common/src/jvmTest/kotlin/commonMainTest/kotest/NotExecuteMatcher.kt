package commonMainTest.kotest

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should

class NotExecuteMatcher : Matcher<Unit> {
	override fun test(value: Unit): MatcherResult {
		return MatcherResult(
			false,
			{ "This Code should not be executed" },
			{ "" }
		)
	}
}

fun Any.shouldNotExecute(): Any {
	Unit should notExecute()
	return this
}

fun notExecute() = NotExecuteMatcher()