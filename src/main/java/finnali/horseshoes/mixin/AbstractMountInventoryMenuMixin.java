package finnali.horseshoes.mixin;

import finnali.horseshoes.item.ModItems;
import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AbstractMountInventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMountInventoryMenu.class)
@SuppressWarnings("unused")
public abstract class AbstractMountInventoryMenuMixin extends AbstractContainerMenu {
    @Shadow
    protected Container mountContainer;

    @Shadow
    protected LivingEntity mount;

    protected AbstractMountInventoryMenuMixin() {
        super(null, 0);
    }

    @Inject(method = "quickMoveStack", at = @At("HEAD"), cancellable = true)
    private void finnshorseshoes$handleHorseshoeShiftClick(Player player, int slotIndex, CallbackInfoReturnable<ItemStack> cir) {
        if (!(this.mount instanceof Horse) || slotIndex < 0 || slotIndex >= this.slots.size()) {
            return;
        }

        int playerInventoryStart = 2 + this.mountContainer.getContainerSize();
        int playerInventoryEnd = playerInventoryStart + 36;
        int horseshoeSlotIndex = this.slots.size() - 1;
        if (horseshoeSlotIndex < playerInventoryEnd) {
            return;
        }

        Slot horseshoeSlot = this.slots.get(horseshoeSlotIndex);
        if (!horseshoeSlot.mayPlace(ModItems.HORSESHOE.getDefaultInstance())) {
            return;
        }

        Slot sourceSlot = this.slots.get(slotIndex);
        if (!sourceSlot.hasItem()) {
            return;
        }

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack movedStack = sourceStack.copy();

        if (slotIndex == horseshoeSlotIndex) {
            if (!this.moveItemStackTo(sourceStack, playerInventoryStart, playerInventoryEnd, true)) {
                cir.setReturnValue(ItemStack.EMPTY);
                return;
            }
        } else if (slotIndex >= playerInventoryStart && slotIndex < playerInventoryEnd && sourceStack.is(ModItems.HORSESHOE)) {
            if (horseshoeSlot.hasItem() || !horseshoeSlot.mayPlace(sourceStack)
                    || !this.moveItemStackTo(sourceStack, horseshoeSlotIndex, horseshoeSlotIndex + 1, false)) {
                cir.setReturnValue(ItemStack.EMPTY);
                return;
            }
        } else {
            return;
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.setByPlayer(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        horseshoeSlot.setChanged();
        cir.setReturnValue(movedStack);
    }
}
