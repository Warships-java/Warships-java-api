package de.floribe2000.warships_java.requests

import java.io.Closeable
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A rate limiter to avoid request limit errors while accessing the api.
 *
 * Limits the requests per second to 10 requests per second.
 *
 * In theory a limit of 10 requests per seconds should be fine but this causes problems with the WG api.
 *
 *
 * If disabled, you have to handle the rate limit on your own.
 */
class SimpleRateLimiter(enabled: Boolean, private var type: ApiType) : Closeable {

    private var semaphore: Semaphore

    private val enabled = AtomicBoolean(false)

    private val timer: ScheduledExecutorService

    private val resetDelay: Long = 1000

    /**
     * Waits for a permit to execute the request.
     */
    fun waitForPermit() {
        if (!enabled.get()) {
            return
        }
        try {
            semaphore.acquire()
            scheduleDelete()
        } catch (e: InterruptedException) {
            throw IllegalStateException("Failed to wait for semaphore.")
        }
    }

    /**
     * Schedules the release task for a previous acquire task.
     */
    private fun scheduleDelete() {
        timer.schedule({ semaphore.release() }, resetDelay, TimeUnit.MILLISECONDS)
    }

    /**
     * Tells if the rate limiter is enabled or not.
     *
     * @return true if enabled, false if not
     */
    fun isEnabled(): Boolean {
        return enabled.get()
    }

    /**
     * Enables the rate limiter.
     */
    fun enable() {
        enabled.set(true)
    }

    /**
     * Tries to disable the rate limiter.
     *
     * To disable the rate limiter there must be no threads waiting for it!
     *
     * @return true if disabling was successful, false if not
     */
    fun disable(): Boolean {
        return if (semaphore.queueLength < 1) {
            enabled.set(false)
            true
        } else {
            false
        }
    }

    @Throws(IOException::class)
    fun connectToApi(url: String): InputStream {
        try {
            semaphore.acquire()
        } catch (e: InterruptedException) {
            throw IllegalStateException("Failed to wait for semaphore.")
        }
        val stream = URL(url).openStream()
        scheduleDelete()
        return stream
    }

    /**
     * Allows to switch the api client type between client and server to change the request limit according to the selected settings.
     *
     * To change the apy type, the rate limiter has to be disabled first!
     *
     * @param type the ApiType to set
     * @return true if the change was successful, false if not
     */
    fun setClientType(type: ApiType): Boolean {
        return if (enabled.get()) {
            false
        } else {
            this.type = type
            semaphore = Semaphore(type.rateLimit)
            true
        }
    }

    override fun close() {
        enabled.set(false)
        try {
            semaphore.acquire()
        } catch (e: Exception) {
            //
        }
        timer.shutdownNow()
    }

    enum class ApiType(val rateLimit: Int) {
        CLIENT(10), SERVER(20);

    }

    companion object {
        fun waitForPermit(limiter: SimpleRateLimiter) {
            limiter.waitForPermit()
        }
    }

    init {
        semaphore = Semaphore(type.rateLimit)
        timer = Executors.newScheduledThreadPool(type.rateLimit)
        this.enabled.set(enabled)
    }
}