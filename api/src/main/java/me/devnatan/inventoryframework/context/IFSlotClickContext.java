package me.devnatan.inventoryframework.context;

import org.jetbrains.annotations.NotNull;

/**
 * The click context represents an interaction between the actor and the container, even if an item
 * is not present, it stores information about the click, and it's origin.
 *
 * @see IFSlotContext
 */
public interface IFSlotClickContext extends IFSlotContext {

    int getClickedSlot();

    /**
     * If the click was using the left mouse button.
     *
     * @return If the click was using the left mouse button.
     */
    boolean isLeftClick();

    /**
     * If the click was using the right mouse button.
     *
     * @return If the click was using the right mouse button.
     */
    boolean isRightClick();

    /**
     * If the click was using the middle mouse button, commonly known as the scroll button.
     *
     * @return If the click was using the middle mouse button.
     */
    boolean isMiddleClick();

    /**
     * If the click was accompanied by a click holding the keyboard shift button.
     *
     * @return If it was a click holding the keyboard shift button.
     */
    boolean isShiftClick();

    /**
     * If the click source came from a keyboard, e.g. the player's toolbar number.
     *
     * @return If the click source came from a keyboard.
     */
    boolean isKeyboardClick();

    /**
     * If the click did not occur within any containers.
     *
     * @return If the click did not occur within any containers.
     */
    boolean isOutsideClick();

    /**
     * The click identifier, available only in cases where the library does not cover all types of
     * clicks, so you can discover the type of click through its identifier.
     *
     * @return The click type identifier.
     */
    @NotNull
    String getClickIdentifier();

    /**
     * If the click was cancelled.
     *
     * @return If the click was cancelled.
     */
    boolean isCancelled();

    /**
     * Cancels the click.
     *
     * @param cancelled If the click should be cancelled.
     */
    void setCancelled(boolean cancelled);
}
