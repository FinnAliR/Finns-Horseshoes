package finnali.horseshoes.mixin.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractMountInventoryScreen;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.equine.Horse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractMountInventoryScreen.class)
public abstract class AbstractMountInventoryScreenMixin {
    @Shadow
    protected LivingEntity mount;

    @Shadow
    protected abstract void drawSlot(GuiGraphics guiGraphics, int i, int j);

    @Inject(method = "renderBg", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void finnshorseshoes$renderHorseshoeSlot(
            GuiGraphics guiGraphics,
            float delta,
            int mouseX,
            int mouseY,
            CallbackInfo ci,
            int left,
            int top
    ) {
        if (!(this.mount instanceof Horse horse) || !horse.canUseSlot(EquipmentSlot.FEET)) {
            return;
        }

        this.drawSlot(guiGraphics, left + 7, top + 53);
    }
}
