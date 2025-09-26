package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.AMENDMENTS
import com.possible_triangle.dye_the_world.Constants.Mods.SUPPLEMENTARIES
import com.possible_triangle.dye_the_world.Constants.Mods.SUPPLEMENTARIES_SQUARED
import com.possible_triangle.dye_the_world.DyedRegistrate
import com.possible_triangle.dye_the_world.data.*
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.loot
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.translation
import com.possible_triangle.dye_the_world.extensions.withItem
import net.mehvahdjukaar.supplementaries.common.block.blocks.AwningBlock
import net.mehvahdjukaar.supplementaries.common.block.blocks.SackBlock
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

object DyedSupplementaries {

    private val DYES = dyesFor(SUPPLEMENTARIES)

    private val REGISTRATE = DyedRegistrate.create(SUPPLEMENTARIES)
    private val REGISTRATE_AMENDMENTS = DyedRegistrate.create(AMENDMENTS)
    private val SQUARED_REGISTRATE = DyedRegistrate.create(SUPPLEMENTARIES_SQUARED)

    val SACKS = DYES.associateWith { dye ->
        SQUARED_REGISTRATE.`object`("sack_${dye}")
            .block(::SackBlock)
            .lang("${dye.translation} Sack")
            .sackBlockstate(dye)
            .loot(SUPPLEMENTARIES) { t, b -> t.add(b, t.createShulkerBoxDrop(b)) }
            .withItem {
                sackItemModel()
            }
            .register()
    }

    val CEILING_BANNERS = DYES.associateWith { dye ->
        REGISTRATE_AMENDMENTS.`object`("ceiling_banner_${dye}")
            .block(::Block)
            .lang("${dye.translation} Banner")
            .optionalTag(DyedTags.Blocks.CEILING_BANNERS)
            .blockstate { context, provider ->
                val model = provider.models().getExistingFile(ResourceLocation("block/banner"))
                provider.simpleBlock(context.get(), model)
            }
            .register()
    }

    val BUNTINGS = DYES.associateWith { dye ->
        REGISTRATE.`object`("bunting_$dye")
            .item(::Item)
            .lang("${dye.translation} Bunting")
            .dyedBuntingItemModel(dye)
            .dyedBuntingRecipe(dye)
            .register()
    }

    val BUNTING = REGISTRATE.`object`("bunting")
        .item(::Item)
        .buntingItemModel()
        .register()

    val AWNINGS = DYES.associateWith { dye ->
        REGISTRATE.`object`("awning_$dye")
            .block { AwningBlock(dye, it) }
            .lang("${dye.translation} Awning")
            .optionalTag(DyedTags.Blocks.MINEABLE_SHEAR)
            .optionalTag(DyedTags.Blocks.BOUNCY_BLOCKS)
            .optionalTag(DyedTags.Blocks.AWNINGS)
            .awningBlockstate(dye)
            .withItem {
                optionalTag(DyedTags.Items.AWNINGS)
                awningItemModel(dye)
                awningRecipe(dye)
            }
            .register()
    }

    fun register() {
        REGISTRATE.register()
        SQUARED_REGISTRATE.register()
    }

}