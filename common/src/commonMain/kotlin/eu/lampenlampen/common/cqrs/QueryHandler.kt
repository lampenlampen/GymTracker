package eu.lampenlampen.common.cqrs

interface QueryHandler<TQuery : Query<TResult>, TResult> {
	fun execute(query: TQuery): TResult
}