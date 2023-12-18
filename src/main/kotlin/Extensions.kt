/*
 * Copyright © AtomBuilt 2023. All rights reserved.
 *
 * This software has been developed by AtomBuilt.
 * Visit our website at https://atombuilt.com.
 * For inquiries, please contact us at contact@atombuilt.com.
 *
 * All rights to this software are held by the respective owners.
 */

package com.atombuilt.shulkers

import org.bukkit.inventory.ItemStack

fun ItemStack.isShulker() = type.name.endsWith("shulker_box", ignoreCase = true)
