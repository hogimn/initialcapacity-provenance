package io.collective.workflow

/**
 * An interface representing a worker that executes tasks of type T.
 *
 * @param T The type of tasks the worker can execute.
 */
interface Worker<T> {
    /**
     * The name of the worker.
     */
    val name: String

    /**
     * Executes the given task.
     *
     * @param task The task to be executed.
     */
    @Throws(Exception::class)
    fun execute(task: T)
}
