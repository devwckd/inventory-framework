package me.devnatan.inventoryframework.internal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.TestOnly;

public interface Job {

    /**
     * Whether the job has already started or not.
     *
     * @return <code>true</code> if job was started or <code>false</code> otherwise.
     */
    boolean isStarted();

    /**
     * Starts this job.
     */
    void start();

    /**
     * Cancels this job if started.
     */
    void cancel();

    /**
     * Internal loop.
     *
     * <p><b><i> This is an internal inventory-framework API that should not be used from outside of
     * this library. No compatibility guarantees are provided. </i></b>
     */
    @ApiStatus.Internal
    default void loop() {}

    @ApiStatus.Internal
    @RequiredArgsConstructor
    @ToString
    class InternalJobImpl implements Job {

        private final Runnable job;

        @Getter
        private boolean started;

        @Override
        public void start() {
            this.started = true;
        }

        @Override
        public void cancel() {
            this.started = false;
        }

        @Override
        public void loop() {
            job.run();
        }

        @TestOnly
        public void setStarted(boolean started) {
            this.started = started;
        }
    }
}
