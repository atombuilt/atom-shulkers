package com.atombuilt.shulkers

import org.bukkit.inventory.ItemStack

fun ItemStack.isShulker() = type.name.endsWith("shulker_box", ignoreCase = true)
