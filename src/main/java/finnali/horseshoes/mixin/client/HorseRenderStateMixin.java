package finnali.horseshoes.mixin.client;

import finnali.horseshoes.client.HorseShoesRenderStateAccess;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(HorseRenderState.class)
public class HorseRenderStateMixin implements HorseShoesRenderStateAccess {
    @Unique
    private ItemStack finnshorseshoes$horseshoeItem = ItemStack.EMPTY;

    @Override
    public ItemStack finnshorseshoes$getHorseshoeItem() {
        return this.finnshorseshoes$horseshoeItem;
    }

    @Override
    public void finnshorseshoes$setHorseshoeItem(ItemStack itemStack) {
        this.finnshorseshoes$horseshoeItem = itemStack;
    }
}
