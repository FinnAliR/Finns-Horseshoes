package finnali.horseshoes;

import finnali.horseshoes.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class FinnsHorseshoes implements ModInitializer {
	public static final String MOD_ID = "finns_horseshoes";
	private static final float OVERWORLD_STRUCTURE_HORSESHOE_CHANCE = 0.035F;
	private static final int ARMORER_HORSESHOE_PRICE = 16;
	private static final int ARMORER_HORSESHOE_TRADE_LEVEL = 4;
	private static final Set<ResourceKey<LootTable>> OVERWORLD_STRUCTURE_CHEST_TABLES = Set.of(
			BuiltInLootTables.SIMPLE_DUNGEON,
			BuiltInLootTables.ABANDONED_MINESHAFT,
			BuiltInLootTables.STRONGHOLD_CORRIDOR,
			BuiltInLootTables.STRONGHOLD_CROSSING,
			BuiltInLootTables.STRONGHOLD_LIBRARY,
			BuiltInLootTables.DESERT_PYRAMID,
			BuiltInLootTables.JUNGLE_TEMPLE,
			BuiltInLootTables.IGLOO_CHEST,
			BuiltInLootTables.WOODLAND_MANSION,
			BuiltInLootTables.UNDERWATER_RUIN_SMALL,
			BuiltInLootTables.UNDERWATER_RUIN_BIG,
			BuiltInLootTables.BURIED_TREASURE,
			BuiltInLootTables.SHIPWRECK_MAP,
			BuiltInLootTables.SHIPWRECK_SUPPLY,
			BuiltInLootTables.SHIPWRECK_TREASURE,
			BuiltInLootTables.PILLAGER_OUTPOST,
			BuiltInLootTables.ANCIENT_CITY,
			BuiltInLootTables.ANCIENT_CITY_ICE_BOX
	);

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.initialize();
		ResourceKey<CreativeModeTab> combatTab = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.withDefaultNamespace("combat"));
		ItemGroupEvents.modifyEntriesEvent(combatTab).register(entries -> entries.prepend(ModItems.HORSESHOE));
		registerOverworldStructureLoot();
		registerVillagerTrades();
	}

	private static void registerOverworldStructureLoot() {
		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			if (!source.isBuiltin() || !OVERWORLD_STRUCTURE_CHEST_TABLES.contains(key)) {
				return;
			}

			LootPool.Builder pool = LootPool.lootPool()
					.setRolls(ConstantValue.exactly(1.0F))
					.when(LootItemRandomChanceCondition.randomChance(OVERWORLD_STRUCTURE_HORSESHOE_CHANCE))
					.add(LootItem.lootTableItem(ModItems.HORSESHOE));

			tableBuilder.withPool(pool);
		});
	}

	private static void registerVillagerTrades() {
		TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, ARMORER_HORSESHOE_TRADE_LEVEL, factories -> factories.add(
				new VillagerTrades.ItemsForEmeralds(ModItems.HORSESHOE, ARMORER_HORSESHOE_PRICE, 1, 2, 30, 0.05F)
		));
	}
}
