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

import com.atombuilt.atomkt.spigot.listener.KotlinListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class ShulkerListener : KotlinListener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun openShulkerInHand(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_AIR) return
        val player = event.player
        val shulker = player.getShulkerInHand() ?: return
        ShulkerInventory.openFor(shulker, player)
    }

    private fun Player.getShulkerInHand(): ItemStack? {
        with(inventory.itemInMainHand) { if (isShulker()) return this }
        with(inventory.itemInOffHand) { if (isShulker()) return this }
        return null
    }
}
