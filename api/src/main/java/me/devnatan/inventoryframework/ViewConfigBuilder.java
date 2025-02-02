package me.devnatan.inventoryframework;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.devnatan.inventoryframework.exception.InvalidLayoutException;
import me.devnatan.inventoryframework.internal.LayoutSlot;
import org.jetbrains.annotations.NotNull;

@Getter(AccessLevel.PACKAGE)
@NoArgsConstructor
public final class ViewConfigBuilder {

    private String title = "";
    private int size = 0;
    private ViewType type;
    private final Set<ViewConfig.Option<?>> options = new HashSet<>();
    private String[] layout = null;
    private final Set<LayoutSlot> patterns = new HashSet<>();
    private final Set<ViewConfig.Modifier> modifiers = new HashSet<>();

    /**
     * Inherits all configuration from another {@link ViewConfigBuilder} value.
     * <p>
     * Note that the values will be merged and not replaced, however, the values of the setting to
     * be inherited take precedence over those of that setting.
     *
     * @param other The configuration that will be inherited.
     * @return This config.
     */
    public ViewConfigBuilder inheritFrom(@NotNull ViewConfigBuilder other) {
        throw new UnsupportedOperationException("Inheritance is not yet supported");
    }

    /**
     * Defines the type of the container.
     * <p>
     * If applied in view scope, it will be the default value for all contexts originated from it.
     *
     * @param type The container type.
     * @return This config.
     */
    public ViewConfigBuilder type(ViewType type) {
        this.type = type;
        return this;
    }

    /**
     * Defines the title of the container.
     * <p>
     * If applied in view scope, it will be the default value for all contexts originated from it.
     *
     * @param title The container title.
     * @return This configuration builder.
     */
    public ViewConfigBuilder title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Defines the size of the container.
     * <p>
     * If applied in view scope, it will be the default value for all contexts originated from it.
     *
     * @param size The container size.
     * @return This configuration builder.
     */
    public ViewConfigBuilder size(int size) {
        this.size = size;
        return this;
    }

    // TODO needs documentation
    public ViewConfigBuilder maxSize() {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Add a modifier to this setting.
     *
     * @param modifier The modifier that'll be added.
     * @return This configuration builder.
     */
    public ViewConfigBuilder with(@NotNull ViewConfig.Modifier modifier) {
        this.modifiers.add(modifier);
        return this;
    }

    /**
     * Defines the layout that will be used.
     *
     * @param layout The layout.
     * @return This configuration builder.
     * @throws InvalidLayoutException If the layout does not respect the container contracts of the
     *                                context in which it was applied (e.g. if the layout size
     *                                differs from the container size).
     */
    public ViewConfigBuilder layout(String... layout) {
        this.layout = layout;
        return this;
    }

    public ViewConfigBuilder options(ViewConfig.Option<?>... options) {
        this.options.addAll(Arrays.asList(options));
        return this;
    }

    private ViewConfigBuilder addOption(ViewConfig.Option<?> option) {
        options.add(option);
        return this;
    }

    public ViewConfigBuilder cancelOnClick() {
        return addOption(ViewConfig.CancelOnClick);
    }

    /**
     * Cancels any item pickup by the player while the view is open.
     *
     * @return This configuration builder.
     */
    public ViewConfigBuilder cancelOnPickup() {
        return addOption(ViewConfig.CANCEL_ON_PICKUP);
    }

    /**
     * Cancels any item drops by the player while the view is open.
     *
     * @return This configuration builder.
     */
    public ViewConfigBuilder cancelOnDrop() {
        return addOption(ViewConfig.CANCEL_ON_DROP);
    }

    public ViewConfigBuilder cancelOnDrag() {
        throw new UnsupportedOperationException();
    }

    public ViewConfig build() {
        final Map<ViewConfig.Option<?>, Object> optionsMap = options.stream()
                .map(option -> new AbstractMap.SimpleImmutableEntry<ViewConfig.Option<?>, Object>(
                        option, option.defaultValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new ViewConfig(title, size, type, optionsMap, layout, modifiers);
    }
}
