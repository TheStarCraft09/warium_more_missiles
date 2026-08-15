package dev.lumareth.moreMissiles.block;

import dev.lumareth.moreMissiles.MoreMissiles;
import dev.lumareth.moreMissiles.block.types.WingtipHardpointBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MoreMissiles.MOD_ID);

    public static final RegistryObject<Block> EMPTY_WINGTIP_MISSILE_HARDPOINT = BLOCKS.register("empty_wingtip_missile_hardpoint", () -> new WingtipHardpointBlock(Block.Properties.of().strength(1.0f).noOcclusion()));
}
