package xyz.namutree0345.zombies.feature

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FireworkExplodeEvent

class VaccineFeature : Listener {

    @EventHandler
    fun onShotArrowByEntity(event: FireworkExplodeEvent) {
        if(event.entity.name == PlainComponentSerializer.plain().serialize(Component.text("백신", TextColor.color(0x58A8B4), TextDecoration.BOLD, TextDecoration.ITALIC))) {
            val entities = event.entity.getNearbyEntities(10.0, 10.0, 10.0)
            val playersList = listOf<String>()
            for(e in entities) {
                if(e is Player) {

                }
            }
        }
    }

}
