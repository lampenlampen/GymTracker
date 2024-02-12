package eu.lampenlampen.common.result

/**
 * A result that can represent a successful or failed computation
 */
sealed class Result<out V, out E : IError> {
	abstract val kind: ResultType

	fun <X> unfold(onSuccess: (V) -> X, onFailure: (E) -> X) = when (this) {
		is Success -> onSuccess(value)
		is Failure -> onFailure(error)
	}

	fun onSuccess(block: (V) -> Unit) = if (this is Success) {
		block(this.value)
		this
	} else this

	fun onFailure(block: (E) -> Unit) = if (this is Failure) {
		block(this.error)
		this
	} else this

	fun getValueOrNull(): V? = when (this) {
		is Success -> this.value
		else -> null
	}

	fun getValueOrThrow(): V = when (this) {
		is Success -> this.value
		else -> throw IllegalStateException(this.toString())
	}


	data class Success<out S> constructor(val value: S) : Result<S, Nothing>() {

		override val kind: ResultType = ResultType.Success

		override fun equals(other: Any?): Boolean {
			if (this === other) return true
			return other is Success<*> && value == other.value
		}

		override fun hashCode(): Int = value.hashCode()

		override fun toString(): String = "[Success: $value]"
	}

	data class Failure<out E : IError> constructor(val error: E) : Result<Nothing, E>() {

		override val kind: ResultType = ResultType.Failure

		override fun equals(other: Any?): Boolean {
			if (this === other) return true
			return other is Failure<*> && error == other.error
		}

		override fun hashCode(): Int = error.hashCode()

		override fun toString(): String = "[Failure: $error]"
	}
}

enum class ResultType {
	Success,
	Failure
}


interface IError {
	val message: String
	val data: Map<String, Any>
	val errors: List<IError>
}

open class ErrorBase private constructor(override val message: String) : IError {

	constructor(message: String, errors: List<IError>, metadata: Map<String, Any>) : this(message) {
		_errors.addAll(errors)
		_data.putAll(metadata)
	}

	private val _errors: MutableList<IError> = mutableListOf()
	private val _data: MutableMap<String, Any> = mutableMapOf()

	override val errors: List<IError> = _errors

	override val data: Map<String, Any> = _data

	override fun toString(): String {
		return buildString {
			appendLine("Error: $message")
			_data.forEach { appendLine("        with: ${it.key}=${it.value}") }
			_errors.forEach { appendLine("    causedBy:$it") }
		}
	}
}

class ErrorBuilder {
	private var message = ""
	private var errors: MutableList<IError> = mutableListOf()
	private var metadata: MutableMap<String, Any> = mutableMapOf()

	fun withMessage(message: String): ErrorBuilder {
		this.message = message
		return this
	}

	fun withMetadata(key: String, value: Any): ErrorBuilder {
		metadata[key] = value
		return this
	}

	fun causedBy(error: IError): ErrorBuilder {
		errors.add(error)
		return this
	}

	fun create() = ErrorBase(message, errors, metadata)


}
