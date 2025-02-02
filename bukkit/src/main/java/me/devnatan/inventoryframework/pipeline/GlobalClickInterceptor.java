package me.devnatan.inventoryframework.pipeline;

import static me.devnatan.inventoryframework.ViewConfig.CancelOnClick;

import me.devnatan.inventoryframework.PlatformView;
import me.devnatan.inventoryframework.VirtualView;
import me.devnatan.inventoryframework.context.SlotClickContext;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Intercepted when a player clicks on the view container.
 * If the click is canceled, this interceptor ends the pipeline immediately.
 */
public final class GlobalClickInterceptor implements PipelineInterceptor<VirtualView> {

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void intercept(@NotNull PipelineContext<VirtualView> pipeline, @NotNull VirtualView subject) {
        if (!(subject instanceof SlotClickContext)) return;

        final SlotClickContext context = (SlotClickContext) subject;
        final InventoryClickEvent event = context.getClickOrigin();

        // inherit cancellation so we can un-cancel it
        context.setCancelled(
                event.isCancelled() || context.getRoot().getConfig().isOptionSet(CancelOnClick));
        ((PlatformView) context.getRoot()).onClick(context);
        event.setCancelled(context.isCancelled());
    }
}
