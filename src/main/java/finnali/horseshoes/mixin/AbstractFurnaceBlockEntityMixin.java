package finnali.horseshoes.mixin;

import finnali.horseshoes.item.ModItems;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {
    private static final int HORSESHOE_NUGGET_YIELD = 16;

    @Inject(method = "canBurn", at = @At("RETURN"), cancellable = true)
    private static void finnshorseshoes$requireRoomForFullYield(
            RegistryAccess registryAccess,
            RecipeHolder<? extends AbstractCookingRecipe> recipeHolder,
            SingleRecipeInput singleRecipeInput,
            NonNullList<ItemStack> stacks,
            int maxStackSize,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (!cir.getReturnValueZ() || !isHorseshoeNuggetRecipe(registryAccess, recipeHolder, singleRecipeInput)) {
            return;
        }

        ItemStack output = stacks.get(2);
        if (output.isEmpty()) {
            return;
        }

        int maxOutputSize = Math.min(maxStackSize, output.getMaxStackSize());
        int freeSpace = maxOutputSize - output.getCount();
        if (freeSpace < HORSESHOE_NUGGET_YIELD) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "burn", at = @At("RETURN"))
    private static void finnshorseshoes$boostSmeltYield(
            RegistryAccess registryAccess,
            RecipeHolder<? extends AbstractCookingRecipe> recipeHolder,
            SingleRecipeInput singleRecipeInput,
            NonNullList<ItemStack> stacks,
            int maxStackSize,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (!cir.getReturnValueZ() || !isHorseshoeNuggetRecipe(registryAccess, recipeHolder, singleRecipeInput)) {
            return;
        }

        ItemStack output = stacks.get(2);
        if (!output.is(Items.GOLD_NUGGET)) {
            return;
        }

        int extraNuggets = Math.min(HORSESHOE_NUGGET_YIELD - 1, output.getMaxStackSize() - output.getCount());
        if (extraNuggets > 0) {
            output.grow(extraNuggets);
        }
    }

    private static boolean isHorseshoeNuggetRecipe(
            RegistryAccess registryAccess,
            RecipeHolder<? extends AbstractCookingRecipe> recipeHolder,
            SingleRecipeInput singleRecipeInput
    ) {
        if (recipeHolder == null || !singleRecipeInput.item().is(ModItems.HORSESHOE)) {
            return false;
        }

        ItemStack recipeResult = recipeHolder.value().assemble(singleRecipeInput, registryAccess);
        return recipeResult.is(Items.GOLD_NUGGET);
    }
}
