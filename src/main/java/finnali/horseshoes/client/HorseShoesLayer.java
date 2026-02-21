package finnali.horseshoes.client;

import com.mojang.blaze3d.vertex.PoseStack;
import finnali.horseshoes.FinnsHorseshoes;
import finnali.horseshoes.item.ModItems;
import finnali.horseshoes.mixin.client.AbstractEquineModelAccessor;
import net.minecraft.client.model.animal.equine.HorseModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class HorseShoesLayer extends RenderLayer<HorseRenderState, HorseModel> {
    private static final Identifier SHOES_TEXTURE = Identifier.fromNamespaceAndPath(
            FinnsHorseshoes.MOD_ID,
            "textures/entity/horse/horse_shoes_boots.png"
    );

    private final HorseShoesModel shoesModel;

    public HorseShoesLayer(RenderLayerParent<HorseRenderState, HorseModel> parent, HorseShoesModel shoesModel) {
        super(parent);
        this.shoesModel = shoesModel;
    }

    @Override
    public void submit(
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            int packedLight,
            HorseRenderState horseRenderState,
            float yRot,
            float xRot
    ) {
        if (horseRenderState.isInvisible) {
            return;
        }

        HorseShoesRenderStateAccess access = (HorseShoesRenderStateAccess) horseRenderState;
        if (!access.finnshorseshoes$getHorseshoeItem().is(ModItems.HORSESHOE)) {
            return;
        }

        AbstractEquineModelAccessor equineModel = (AbstractEquineModelAccessor) this.getParentModel();
        this.shoesModel.syncToHorseLegs(equineModel);

        submitNodeCollector.order(2)
                .submitModel(
                        this.shoesModel,
                        horseRenderState,
                        poseStack,
                        RenderTypes.entityCutoutNoCull(SHOES_TEXTURE),
                        packedLight,
                        LivingEntityRenderer.getOverlayCoords(horseRenderState, 0.0F),
                        -1,
                        null,
                        horseRenderState.outlineColor,
                        null
                );
    }
}
