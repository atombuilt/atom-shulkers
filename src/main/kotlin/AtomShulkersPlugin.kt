package com.atombuilt.shulkers

import com.atombuilt.atomkt.spigot.KotlinPlugin
import com.atombuilt.atomkt.spigot.logAtomBuiltBanner
import org.bstats.bukkit.Metrics

class AtomShulkersPlugin : KotlinPlugin() {

    private lateinit var metrics: Metrics

    override suspend fun attachComponents() {
        attachComponent(ShulkerListener())
    }

    override suspend fun onEnabled() {
        log.logAtomBuiltBanner()
        metrics = Metrics(this, 20507)
    }

    override suspend fun onDisabled() {
        metrics.shutdown()
    }
}
