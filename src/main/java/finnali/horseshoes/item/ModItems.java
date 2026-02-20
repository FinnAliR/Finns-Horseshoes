package finnali.horseshoes.item;

import finnali.horseshoes.FinnsHorseshoes;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.Equippable;

import java.util.function.Function;

public class ModItems {

    public static void initialize() {
        HORSESHOE = register("horseshoe", Item::new, new Item.Properties()
                .durability(165)
                .enchantable(15)
                .component(
                        DataComponents.ATTRIBUTE_MODIFIERS,
                        ItemAttributeModifiers.builder()
                                .add(
                                        Attributes.ARMOR,
                                        new AttributeModifier(
                                                Identifier.fromNamespaceAndPath(FinnsHorseshoes.MOD_ID, "horseshoe_armor_bonus"),
                                                2.0,
                                                AttributeModifier.Operation.ADD_VALUE
                                        ),
                                        EquipmentSlotGroup.FEET
                                )
                                .build()
                )
                .component(
                        DataComponents.EQUIPPABLE,
                        Equippable.builder(EquipmentSlot.FEET)
                                .setAllowedEntities(EntityType.HORSE)
                                .build()
                ));
    }

    public static <T extends Item> T register(
            String name,
            Function<Item.Properties, T> factory,
            Item.Properties props
    ) {
        Identifier id = Identifier.fromNamespaceAndPath(FinnsHorseshoes.MOD_ID, name);

        // Correct: create the entry key using the registry instance's key
        ResourceKey<Item> key = ResourceKey.create(BuiltInRegistries.ITEM.key(), id);

        T item = factory.apply(props.setId(key));

        // Register using id (simplest + most consistent)
        Registry.register(BuiltInRegistries.ITEM, id, item);

        return item;
    }
    public static Item HORSESHOE;// = register("horseshoe", Item::new, new Item.Properties());
}
