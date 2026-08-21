package dev.lumareth.moreMissiles.item;

import dev.lumareth.moreMissiles.MoreMissiles;
import dev.lumareth.moreMissiles.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MoreMissiles.MOD_ID);

    public static final RegistryObject<Item> EMPTY_WINGTIP_MISSILE_HARDPOINT = ITEMS.register("empty_wingtip_missile_hardpoint", () -> new BlockItem(ModBlocks.EMPTY_WINGTIP_MISSILE_HARDPOINT.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALUMINUM_MISSILE_HARDPOINT = ITEMS.register("aluminum_wing_hardpoint", () -> new BlockItem(ModBlocks.ALUMINUM_WING_HARDPOINT.get(), new Item.Properties()));
}
