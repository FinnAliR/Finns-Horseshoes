package finnali.horseshoes.item;

import finnali.horseshoes.Horseshoes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {

    public static void initialize() {
        HORSESHOE = register("horseshoe", Item::new, new Item.Properties());
    }

    public static <T extends Item> T register(
            String name,
            Function<Item.Properties, T> factory,
            Item.Properties props
    ) {
        Identifier id = Identifier.fromNamespaceAndPath(Horseshoes.MOD_ID, name);

        // Correct: create the entry key using the registry instance's key
        ResourceKey<Item> key = ResourceKey.create(BuiltInRegistries.ITEM.key(), id);

        T item = factory.apply(props.setId(key));

        // Register using id (simplest + most consistent)
        Registry.register(BuiltInRegistries.ITEM, id, item);

        return item;
    }
    public static Item HORSESHOE;// = register("horseshoe", Item::new, new Item.Properties());
}
