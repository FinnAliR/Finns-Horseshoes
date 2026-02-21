package finnali.horseshoes.client;

import finnali.horseshoes.mixin.client.AbstractEquineModelAccessor;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.HorseRenderState;

public class HorseShoesModel extends EntityModel<HorseRenderState> {
    private static final String LEFT_FRONT_SHOE = "left_front_shoe";
    private static final String RIGHT_FRONT_SHOE = "right_front_shoe";
    private static final String LEFT_HIND_SHOE = "left_hind_shoe";
    private static final String RIGHT_HIND_SHOE = "right_hind_shoe";
    private static final float GLOBAL_X_ALIGNMENT = 0.0F;
    private static final float FRONT_Z_ALIGNMENT = -0.875F;
    private static final float HIND_Z_ALIGNMENT = 0.8125F;

    private final ModelPart leftFrontShoe;
    private final ModelPart rightFrontShoe;
    private final ModelPart leftHindShoe;
    private final ModelPart rightHindShoe;

    public HorseShoesModel() {
        super(createLayer().bakeRoot());
        ModelPart root = this.root();
        this.leftFrontShoe = root.getChild(LEFT_FRONT_SHOE);
        this.rightFrontShoe = root.getChild(RIGHT_FRONT_SHOE);
        this.leftHindShoe = root.getChild(LEFT_HIND_SHOE);
        this.rightHindShoe = root.getChild(RIGHT_HIND_SHOE);
    }

    public void syncToHorseLegs(AbstractEquineModelAccessor horseModel) {
        copyPose(horseModel.finnshorseshoes$getLeftFrontLeg(), this.leftFrontShoe);
        copyPose(horseModel.finnshorseshoes$getRightFrontLeg(), this.rightFrontShoe);
        copyPose(horseModel.finnshorseshoes$getLeftHindLeg(), this.leftHindShoe);
        copyPose(horseModel.finnshorseshoes$getRightHindLeg(), this.rightHindShoe);

        this.leftFrontShoe.x += GLOBAL_X_ALIGNMENT;
        this.rightFrontShoe.x += GLOBAL_X_ALIGNMENT;
        this.leftHindShoe.x += GLOBAL_X_ALIGNMENT;
        this.rightHindShoe.x += GLOBAL_X_ALIGNMENT;

        this.leftFrontShoe.z += FRONT_Z_ALIGNMENT;
        this.rightFrontShoe.z += FRONT_Z_ALIGNMENT;
        this.leftHindShoe.z += HIND_Z_ALIGNMENT;
        this.rightHindShoe.z += HIND_Z_ALIGNMENT;
    }

    @Override
    public void setupAnim(HorseRenderState horseRenderState) {
    }

    private static void copyPose(ModelPart source, ModelPart target) {
        target.x = source.x;
        target.y = source.y;
        target.z = source.z;
        target.xRot = source.xRot;
        target.yRot = source.yRot;
        target.zRot = source.zRot;
        target.xScale = source.xScale;
        target.yScale = source.yScale;
        target.zScale = source.zScale;
        target.visible = source.visible;
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        CubeDeformation deformation = new CubeDeformation(0.57F);
        float outwardShift = 0.25F;

        root.addOrReplaceChild(
                LEFT_FRONT_SHOE,
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F + outwardShift, 8.0F, -1.9F, 4.0F, 2.0F, 4.0F, deformation),
                PartPose.offset(4.0F, 14.0F, -10.0F)
        );
        root.addOrReplaceChild(
                RIGHT_FRONT_SHOE,
                CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F - outwardShift, 8.0F, -1.9F, 4.0F, 2.0F, 4.0F, deformation),
                PartPose.offset(-4.0F, 14.0F, -10.0F)
        );
        root.addOrReplaceChild(
                LEFT_HIND_SHOE,
                CubeListBuilder.create().texOffs(16, 0).addBox(-3.0F + outwardShift, 8.0F, -1.0F, 4.0F, 2.0F, 4.0F, deformation),
                PartPose.offset(4.0F, 14.0F, 7.0F)
        );
        root.addOrReplaceChild(
                RIGHT_HIND_SHOE,
                CubeListBuilder.create().texOffs(16, 6).addBox(-1.0F - outwardShift, 8.0F, -1.0F, 4.0F, 2.0F, 4.0F, deformation),
                PartPose.offset(-4.0F, 14.0F, 7.0F)
        );

        return LayerDefinition.create(mesh, 32, 16);
    }
}
