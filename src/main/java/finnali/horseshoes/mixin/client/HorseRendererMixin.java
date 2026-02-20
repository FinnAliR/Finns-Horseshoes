package finnali.horseshoes.mixin.client;

import finnali.horseshoes.client.HorseShoesLayer;
import finnali.horseshoes.client.HorseShoesRenderStateAccess;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseRenderer.class)
public abstract class HorseRendererMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void finnshorseshoes$addLayer(EntityRendererProvider.Context context, CallbackInfo ci) {
        ArmorModelSet<HumanoidModel<HumanoidRenderState>> armorModels = ArmorModelSet.bake(ModelLayers.PLAYER_ARMOR, context.getModelSet(), HumanoidModel::new);
        ((LivingEntityRendererAccessor) this).finnshorseshoes$getLayers().add(
                new HorseShoesLayer(
                        (HorseRenderer) (Object) this,
                        context.getEquipmentRenderer(),
                        armorModels.get(EquipmentSlot.FEET)
                )
        );
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/animal/equine/Horse;Lnet/minecraft/client/renderer/entity/state/HorseRenderState;F)V", at = @At("TAIL"))
    private void finnshorseshoes$extractHorseshoeState(Horse horse, HorseRenderState horseRenderState, float partialTick, CallbackInfo ci) {
        HorseShoesRenderStateAccess access = (HorseShoesRenderStateAccess) horseRenderState;
        ItemStack shoes = horse.getItemBySlot(EquipmentSlot.FEET).copy();
        access.finnshorseshoes$setHorseshoeItem(shoes);
    }
}
