package com.possible_triangle.dye_the_world

import com.possible_triangle.multikulti.datagen.conditions.Condition
import com.possible_triangle.multikulti.datagen.conditions.Conditional
import com.possible_triangle.multikulti.datagen.conditions.ModLoaded

fun Any.withCondition(condition: Condition, block: () -> Unit) {
    val conditional = Conditional.of(this)
    conditional.with(listOf(condition), block)
}

fun Any.withNamespace(namespace: String, block: () -> Unit) =
    withCondition(ModLoaded(namespace), block)