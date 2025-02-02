package me.devnatan.inventoryframework.feature;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Simple HashMap-backed feature installer implementation.
 *
 * @param <P> The feature installer platform.
 */
@RequiredArgsConstructor
public class DefaultFeatureInstaller<P> implements FeatureInstaller<P> {

    private final Map<Class<?>, Feature<?, ?, P>> featureList = new HashMap<>();

    @Getter
    private final @NotNull P platform;

    @Override
    public Collection<Feature<?, ?, P>> getInstalledFeatures() {
        return Collections.unmodifiableCollection(featureList.values());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C, R> @NotNull R install(@NotNull Feature<C, R, P> feature, @NotNull UnaryOperator<C> configure) {
        final Class<?> type = feature.getClass();
        if (featureList.containsKey(type))
            throw new IllegalStateException("Feature already installed, cannot install feature multiple times");

        @SuppressWarnings("unchecked")
        final Feature<C, R, P> value = (Feature<C, R, P>) feature.install(platform, configure);
        synchronized (featureList) {
            featureList.put(type, value);
        }

        return (R) value;
    }

    @Override
    public void uninstall(@NotNull Feature<?, ?, P> feature) {
        final Class<?> type = feature.getClass();
        if (!featureList.containsKey(type))
            throw new IllegalStateException(String.format("Feature %s not installed", type.getSimpleName()));

        synchronized (featureList) {
            featureList.remove(type).uninstall(platform);
        }
    }
}
