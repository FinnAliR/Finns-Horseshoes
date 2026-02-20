package finnali.horseshoes.mixin;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.animal.equine.Horse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorse.class)
public class AbstractHorseMixin {
    @Inject(method = "canUseSlot", at = @At("HEAD"), cancellable = true)
    private void finnshorseshoes$allowFeetSlot(EquipmentSlot slot, CallbackInfoReturnable<Boolean> cir) {
        if (slot == EquipmentSlot.FEET && (Object) this instanceof Horse) {
            cir.setReturnValue(true);
        }
    }
}
