package finnali.horseshoes.mixin.client;

import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.client.model.animal.equine.AbstractEquineModel")
public interface AbstractEquineModelAccessor {
    @Accessor("rightHindLeg")
    ModelPart finnshorseshoes$getRightHindLeg();

    @Accessor("leftHindLeg")
    ModelPart finnshorseshoes$getLeftHindLeg();

    @Accessor("rightFrontLeg")
    ModelPart finnshorseshoes$getRightFrontLeg();

    @Accessor("leftFrontLeg")
    ModelPart finnshorseshoes$getLeftFrontLeg();
}
