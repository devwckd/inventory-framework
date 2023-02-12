package me.devnatan.inventoryframework.pipeline;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public final class PipelineContext<S> {

    @Getter
    private final @Nullable PipelinePhase phase;

    private final List<PipelineInterceptor<S>> interceptors;

    private S subject;
    private int index;

    /** Finishes current pipeline execution */
    public void finish() {
        index = -1;
    }

    private void loop() {
        do {
            final int pointer = index;
            if (pointer == -1) break;

            final List<PipelineInterceptor<S>> safeInterceptors = interceptors;
            if (pointer >= safeInterceptors.size()) {
                finish();
                break;
            }

            final PipelineInterceptor<S> nextInterceptor = safeInterceptors.get(pointer);
            index = pointer + 1;

            System.out.printf("[%s] intercepted: %s%n", phase, subject);
            nextInterceptor.intercept(this, subject);
        } while (true);
    }

    public void proceed() {
        System.out.println("proceed");
        if (index >= interceptors.size()) {
            finish();
            System.out.println("finished " + interceptors);
            return;
        }

        loop();
    }

    public void execute(S initial) {
        index = 0;
        subject = initial;
        System.out.println("execute " + initial);
        proceed();
    }
}
