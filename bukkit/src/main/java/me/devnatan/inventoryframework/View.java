package me.devnatan.inventoryframework;

import me.devnatan.inventoryframework.component.BukkitItemComponentBuilder;
import me.devnatan.inventoryframework.context.CloseContext;
import me.devnatan.inventoryframework.context.Context;
import me.devnatan.inventoryframework.context.OpenContext;
import me.devnatan.inventoryframework.context.RenderContext;
import me.devnatan.inventoryframework.context.SlotClickContext;
import me.devnatan.inventoryframework.context.SlotContext;
import me.devnatan.inventoryframework.internal.ElementFactory;
import me.devnatan.inventoryframework.pipeline.CancelledCloseInterceptor;
import me.devnatan.inventoryframework.pipeline.GlobalClickInterceptor;
import me.devnatan.inventoryframework.pipeline.ItemClickInterceptor;
import me.devnatan.inventoryframework.pipeline.ItemCloseOnClickInterceptor;
import me.devnatan.inventoryframework.pipeline.Pipeline;
import me.devnatan.inventoryframework.pipeline.StandardPipelinePhases;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit platform {@link PlatformView} implementation.
 */
@ApiStatus.OverrideOnly
public class View
        extends PlatformView<
                BukkitItemComponentBuilder,
                Context,
                OpenContext,
                CloseContext,
                RenderContext,
                SlotContext,
                SlotClickContext>
        implements InventoryHolder {

    @Override
    public final @NotNull ElementFactory getElementFactory() {
        return super.getElementFactory();
    }

    @Override
    public final void registerPlatformInterceptors() {
        final Pipeline<? super VirtualView> pipeline = getPipeline();
        pipeline.intercept(StandardPipelinePhases.CLICK, new ItemClickInterceptor());
        pipeline.intercept(StandardPipelinePhases.CLICK, new GlobalClickInterceptor());
        pipeline.intercept(StandardPipelinePhases.CLICK, new ItemCloseOnClickInterceptor());
        pipeline.intercept(StandardPipelinePhases.CLOSE, new CancelledCloseInterceptor());
    }

    @Override
    public final void nextTick(Runnable task) {
        Bukkit.getServer().getScheduler().runTask(((ViewFrame) getFramework()).getOwner(), task);
    }

    @NotNull
    @Override
    public final Inventory getInventory() {
        throw new UnsupportedOperationException("Cannot get inventory from RootView");
    }

    @Override
    public final int hashCode() {
        return super.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected final Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected final void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public final String toString() {
        return super.toString();
    }
}
