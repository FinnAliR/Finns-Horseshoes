package finnali.horseshoes.client;

import com.mojang.blaze3d.vertex.PoseStack;
import finnali.horseshoes.item.ModItems;
import finnali.horseshoes.mixin.client.AbstractEquineModelAccessor;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.animal.equine.HorseModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.item.equipment.EquipmentAssets;

public class HorseShoesLayer extends RenderLayer<HorseRenderState, HorseModel> {
    private final EquipmentLayerRenderer equipmentRenderer;
    private final HumanoidModel<HumanoidRenderState> bootModel;
    private final HumanoidRenderState bootRenderState = new HumanoidRenderState();

    public HorseShoesLayer(RenderLayerParent<HorseRenderState, HorseModel> parent, EquipmentLayerRenderer equipmentRenderer, HumanoidModel<HumanoidRenderState> bootModel) {
        super(parent);
        this.equipmentRenderer = equipmentRenderer;
        this.bootModel = bootModel;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, HorseRenderState horseRenderState, float yRot, float xRot) {
        HorseShoesRenderStateAccess access = (HorseShoesRenderStateAccess) horseRenderState;
        if (!access.finnshorseshoes$getHorseshoeItem().is(ModItems.HORSESHOE)) {
            return;
        }

        bootRenderState.isBaby = horseRenderState.isBaby;
        bootRenderState.outlineColor = horseRenderState.outlineColor;
        bootRenderState.ageInTicks = horseRenderState.ageInTicks;

        AbstractEquineModelAccessor model = (AbstractEquineModelAccessor) this.getParentModel();
        renderBoot(model.finnshorseshoes$getLeftFrontLeg(), true, poseStack, submitNodeCollector, packedLight, horseRenderState);
        renderBoot(model.finnshorseshoes$getRightFrontLeg(), false, poseStack, submitNodeCollector, packedLight, horseRenderState);
        renderBoot(model.finnshorseshoes$getLeftHindLeg(), true, poseStack, submitNodeCollector, packedLight, horseRenderState);
        renderBoot(model.finnshorseshoes$getRightHindLeg(), false, poseStack, submitNodeCollector, packedLight, horseRenderState);
    }

    private void renderBoot(ModelPart legPart, boolean leftSide, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, HorseRenderState horseRenderState) {
        bootModel.setAllVisible(false);
        bootModel.leftLeg.visible = leftSide;
        bootModel.rightLeg.visible = !leftSide;

        poseStack.pushPose();
        legPart.translateAndRotate(poseStack);

        // Cancel humanoid leg pivot and move down to hoof area.
        float pivotCancelX = leftSide ? -1.9F : 1.9F;
        // Move boots down from the top of the leg segment toward the joint/connection area.
        poseStack.translate(pivotCancelX / 16.0F, -7.2F / 16.0F, 0.0F);
        poseStack.scale(0.70F, 0.70F, 0.74F);

        equipmentRenderer.renderLayers(
                EquipmentClientInfo.LayerType.HUMANOID,
                EquipmentAssets.GOLD,
                bootModel,
                bootRenderState,
                ((HorseShoesRenderStateAccess) horseRenderState).finnshorseshoes$getHorseshoeItem(),
                poseStack,
                submitNodeCollector,
                packedLight,
                horseRenderState.outlineColor
        );
        poseStack.popPose();
    }
}
