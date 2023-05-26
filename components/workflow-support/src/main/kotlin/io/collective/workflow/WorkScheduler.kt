package io.collective.workflow

import org.slf4j.LoggerFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * A work scheduler that assigns tasks to workers for execution.
 *
 * @param T The type of tasks the work scheduler deals with.
 * @property finder The work finder responsible for finding tasks.
 * @property workers The list of workers available for task execution.
 * @property delay The delay between consecutive work checks in seconds. Default is 10 seconds.
 */
class WorkScheduler<T> @JvmOverloads constructor(private val finder: WorkFinder<T>, private val workers: List<Worker<T>>, private val delay: Long = 10L) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * Create a ScheduledThreadPoolExecutor with the number of workers
     */
    private val pool = Executors.newScheduledThreadPool(workers.size)

    /**
     * Create a fixed-size thread pool with 10 threads
     */
    private val service = Executors.newFixedThreadPool(10)

    /**
     * Starts the work scheduler by scheduling workers to check for and execute tasks.
     */
    fun start() {
        // Iterate over each worker in the workers list
        workers.forEach { worker ->
            // Log a message indicating the scheduling of the worker
            logger.info("scheduling worker {}", worker.name)

            // Schedule the worker to check for and execute tasks with a fixed delay
            pool.scheduleWithFixedDelay(checkForWork(worker), 0, delay, TimeUnit.SECONDS)
        }
    }

    /**
     * Shuts down the work scheduler by terminating the worker threads and releasing associated resources.
     */
    fun shutdown() {
        // Shutdown the service executor, preventing submission of new tasks
        service.shutdown()
        // Shutdown the pool of worker threads, allowing active tasks to complete but not accepting new ones
        pool.shutdown()
    }

    /**
     * Creates a lambda function that checks for work for the given worker.
     * The lambda function is used to submit the worker tasks to the service executor.
     *
     * @param worker The worker for which to check for work.
     * @return The lambda function that checks for work and executes tasks.
     */
    private fun checkForWork(worker: Worker<T>): () -> Unit {
        return {
            // Log a debug message indicating the worker being checked for work
            logger.debug("checking for work for {}", worker.name)

            // Find all requested tasks for the worker
            finder.findRequested(worker.name).forEach {

                // Log a debug message indicating work found for the worker
                logger.info("found work for {}", worker.name)

                // Submit a task execution to the service executor
                service.submit {
                    try {
                        // Execute the task using the worker
                        worker.execute(it)

                        // Mark the task as completed using the finder
                        finder.markCompleted(it)

                        // Log a debug message indicating task completion
                        logger.info("completed work.")

                    } catch (e: Throwable) {
                        // Log an error message if unable to complete the task
                        logger.error("unable to complete work", e)
                    }
                }
            }
            // Log a debug message indicating the completion of checking for work for the worker
            logger.debug("done checking for work for {}", worker.name)
        }
    }
}
