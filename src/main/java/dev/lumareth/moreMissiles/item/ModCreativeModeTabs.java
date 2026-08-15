package dev.lumareth.moreMissiles.item;

import dev.lumareth.moreMissiles.MoreMissiles;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> WARIUM_MORE_MISSILES = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreMissiles.MOD_ID);
    public static final RegistryObject<CreativeModeTab> MORE_MISSILES_TAB = WARIUM_MORE_MISSILES.register("more_missiles_creative_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_groups." + MoreMissiles.MOD_ID + ".more_missiles"))
            .icon(() -> CrustyChunksModItems.EMPTY_MISSILE_HARDPOINT.get().getDefaultInstance())
            .displayItems((params, output) -> {
            output.accept(ModItems.EMPTY_WINGTIP_MISSILE_HARDPOINT.get());
            })
            .build()
    );
}
