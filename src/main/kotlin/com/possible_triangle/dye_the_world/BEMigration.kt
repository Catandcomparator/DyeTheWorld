package com.possible_triangle.dye_the_world

import com.possible_triangle.dye_the_world.extensions.createId
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.ForgeRegistry

@Suppress("UnstableApiUsage")
fun migrate(name: String, namespace: String) {
    val registry = ForgeRegistries.BLOCK_ENTITY_TYPES as ForgeRegistry<BlockEntityType<*>>
    registry.addAlias(Constants.MOD_ID.createId(name), namespace.createId(name))
}

