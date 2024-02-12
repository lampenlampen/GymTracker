package commonMainTest.result

import commonMainTest.kotest.shouldNotExecute
import eu.lampenlampen.common.result.ErrorBase
import eu.lampenlampen.common.result.IError
import eu.lampenlampen.common.result.Result
import eu.lampenlampen.common.result.ResultType
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf

class ResultTest : FunSpec({
	val message = "TestMessage"
	val metadata = mapOf<String, Any>("TestKey1" to "TestValue1")
	val errors = listOf<IError>(TestError(message, emptyList(), metadata))

	val error = TestError(message, errors, metadata)

	val returnSuccessResult: () -> Result<Int, TestError> = { Result.Success(2) }
	val returnFailureResult: () -> Result<Int, TestError> = { Result.Failure(error) }

	test("onSuccessTest") {
		returnSuccessResult().onSuccess { it shouldBe 2 }
	}

	test("OnFailureTest") {
		returnFailureResult().onFailure {
			it.shouldBeTypeOf<TestError>()
			it.message shouldBe message
			it.data shouldBe metadata
			it.errors shouldBe errors
		}
	}

	test("Unfold successful Result") {
		returnSuccessResult().unfold(
			onSuccess = {
				it shouldBe 2
			},
			onFailure = {
				shouldNotExecute()
			}
		)
	}

	test("Unfold failed Result") {
		returnFailureResult().unfold(
			onSuccess = {
				shouldNotExecute()
			},
			onFailure = {
				it shouldBe error
			}
		)
	}

	test("check ValueOrThrow") {
		shouldThrow<IllegalStateException>(returnFailureResult()::getValueOrThrow)
	}

	test("ResultWithoutReturnValue") {
		val result: Result<Unit, TestError> = Result.Success(Unit)

		result.kind shouldBe ResultType.Success
		result.getValueOrThrow() shouldBe Unit
	}
})

private class TestError(message: String, errors: List<IError>, metadata: Map<String, Any>) : ErrorBase(message, errors, metadata)

