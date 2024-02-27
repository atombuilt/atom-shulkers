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
import net.minecraft.world.item.BlockItem
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.craftbukkit.v1_20_R1.block.CraftShulkerBox
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BlockStateMeta
import org.koin.core.component.KoinComponent
import net.kyori.adventure.sound.Sound as AdventureSound
import net.minecraft.world.item.ItemStack as NMSItemStack

class ShulkerInventory private constructor(
    private val itemStack: ItemStack,
    private val player: Player
) : KotlinListener, KoinComponent {

    private val inventoryHolder = InventoryHolder { throw IllegalStateException() }
    private val meta = itemStack.itemMeta as BlockStateMeta
    private val blockState = meta.blockState as CraftShulkerBox
    private var inventory = createInventory()

    private fun createInventory(): Inventory {
        val displayName = meta.displayName()
        val inventory = if (displayName != null) {
            Bukkit.createInventory(inventoryHolder, InventoryType.SHULKER_BOX, displayName)
        } else {
            Bukkit.createInventory(inventoryHolder, InventoryType.SHULKER_BOX)
        }
        blockState.tileEntity.contents.forEachIndexed { index, itemStack ->
            inventory.setItem(index, CraftItemStack.asBukkitCopy(itemStack))
        }
        return inventory
    }

    private fun open() {
        playSound(Sound.BLOCK_SHULKER_BOX_OPEN)
        register()
        player.openInventory(inventory)
    }

    @EventHandler(ignoreCancelled = true)
    fun onInventoryClose(event: InventoryCloseEvent) {
        if (event.inventory.holder !== inventoryHolder) return
        playSound(Sound.BLOCK_SHULKER_BOX_CLOSE)
        updateItemStack()
        unregister()
    }

    private fun playSound(soundType: Sound) {
        val sound = AdventureSound.sound().type(soundType).build()
        player.playSound(sound)
    }

    private fun updateItemStack() {
        val tileEntity = blockState.tileEntity
        inventory.contents.forEachIndexed { index, itemStack ->
            tileEntity.setItem(index, itemStack.toNMS())
        }
        BlockItem.setBlockEntityData(itemStack.toNMS(), tileEntity.type, tileEntity.saveWithFullMetadata())
    }

    private fun ItemStack?.toNMS(): NMSItemStack {
        if (this == null) return CraftItemStack.asNMSCopy(ItemStack(Material.AIR))
        return (this as CraftItemStack).handle
    }

    companion object {

        fun openFor(itemStack: ItemStack, player: Player) = ShulkerInventory(itemStack, player).also { it.open() }
    }
}
