package dev.lumareth.moreMissiles.block;

import dev.lumareth.moreMissiles.MoreMissiles;
import dev.lumareth.moreMissiles.block.entity.WingtipHardpointBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MoreMissiles.MOD_ID);

    public static final RegistryObject<BlockEntityType<WingtipHardpointBlockEntity>> WINGTIP_HARDPOINT =
            BLOCK_ENTITIES.register("wingtip_hardpoint",
                    () -> BlockEntityType.Builder.of(
                            WingtipHardpointBlockEntity::new,
                            ModBlocks.EMPTY_WINGTIP_MISSILE_HARDPOINT.get()
                    ).build(null));
}