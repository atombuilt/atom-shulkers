package com.atombuilt.shulkers

import com.atombuilt.atomkt.commons.ATOMBUILT_BANNER
import com.atombuilt.atomkt.spigot.KotlinPlugin
import org.bstats.bukkit.Metrics

class AtomShulkersPlugin : KotlinPlugin() {

    private lateinit var metrics: Metrics

    override suspend fun onLoaded() {
        linkListener(ShulkerListener())
    }

    override suspend fun onEnabled() {
        log.info { ATOMBUILT_BANNER }
        metrics = Metrics(this, 20507)
    }

    override suspend fun onDisabled() {
        metrics.shutdown()
    }
}
