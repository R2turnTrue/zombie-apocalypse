package xyz.namutree0345.zombies.feature

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FireworkExplodeEvent
import xyz.namutree0345.zombies.humanTeam
import xyz.namutree0345.zombies.superZombieTeam
import xyz.namutree0345.zombies.zombieTeam

class VaccineFeature : Listener {

    @EventHandler
    fun onShotArrowByEntity(event: FireworkExplodeEvent) {
        if(event.entity.fireworkMeta.displayName() == Component.text("백신", TextColor.color(0x58A8B4), TextDecoration.BOLD, TextDecoration.ITALIC)) {
            val entities = event.entity.getNearbyEntities(10.0, 10.0, 10.0)
            val playersList = arrayListOf<String>()
            for(e in entities) {
                if(e is Player) {
                    if(humanTeam?.hasEntry(e.name) == false) {
                        if(e.name != (event.entity.shooter as Player).name) {
                            playersList.add(e.name)
                        }
                    }
                }
            }

            for(plr in playersList) {
                if(superZombieTeam?.hasEntry(plr) == true) {
                    superZombieTeam?.removeEntry(plr)
                }
                if(zombieTeam?.hasEntry(plr) == true) {
                    zombieTeam?.removeEntry(plr)
                }
                humanTeam?.addEntry(plr)
                for (player in Bukkit.getOnlinePlayers()) {
                    player.sendMessage(Component.text("${plr}님이 해방되었습니다!", NamedTextColor.GREEN))
                }
            }
        }
    }

}
