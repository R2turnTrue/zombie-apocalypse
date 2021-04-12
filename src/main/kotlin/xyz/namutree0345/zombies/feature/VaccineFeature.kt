package xyz.namutree0345.zombies.feature

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FireworkExplodeEvent
import xyz.namutree0345.zombies.humanTeam

class VaccineFeature : Listener {

    @EventHandler
    fun onShotArrowByEntity(event: FireworkExplodeEvent) {
        if(event.entity.fireworkMeta.displayName() == Component.text("백신", TextColor.color(0x58A8B4), TextDecoration.BOLD, TextDecoration.ITALIC)) {
            val entities = event.entity.getNearbyEntities(10.0, 10.0, 10.0)
            val playersList = arrayListOf<String>()
            for(e in entities) {
                if(e is Player) {
                    if(humanTeam?.hasEntry(e.name) == false) {
                        playersList.add(e.name)
                    }
                }
            }
            Bukkit.broadcastMessage("ah")
        }
    }

}
