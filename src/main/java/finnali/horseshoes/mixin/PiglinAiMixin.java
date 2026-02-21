package finnali.horseshoes.mixin;

import finnali.horseshoes.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
@SuppressWarnings("unused")
public class PiglinAiMixin {
    @Inject(method = "isBarterCurrency", at = @At("RETURN"), cancellable = true)
    @SuppressWarnings("unused")
    private static void finnshorseshoes$allowHorseshoeBartering(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ() && itemStack.is(ModItems.HORSESHOE)) {
            cir.setReturnValue(true);
        }
    }
}
