package eu.lampenlampen.common.cqrs

interface CommandHandler<TCommand> {
	fun execute(command: TCommand)
}