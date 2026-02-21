package finnali.horseshoes.mixin;

import finnali.horseshoes.FinnsHorseshoes;
import finnali.horseshoes.item.ModItems;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseInventoryMenu.class)
public abstract class HorseInventoryMenuMixin extends AbstractContainerMenu {
    protected HorseInventoryMenuMixin(MenuType<?> type, int syncId) {
        super(type, syncId);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void finnshorseshoes$addHorseshoeSlot(int syncId, Inventory playerInventory, Container inventory, AbstractHorse horse, int slotColumnCount, CallbackInfo ci) {
        if (!(horse instanceof Horse)) {
            return;
        }

        Container feetSlotContainer = horse.createEquipmentSlotContainer(EquipmentSlot.FEET);
        addSlot(new Slot(feetSlotContainer, 0, 8, 54) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItems.HORSESHOE) && horse.isEquippableInSlot(stack, EquipmentSlot.FEET);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public boolean isActive() {
                return horse.isAlive();
            }

            @Override
            public Identifier getNoItemIcon() {
                return Identifier.fromNamespaceAndPath(FinnsHorseshoes.MOD_ID, "container/slot/horseshoe");
            }
        });
    }
}
