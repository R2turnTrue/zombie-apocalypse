package xyz.namutree0345.zombies

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class ToSuperZombie : Listener {

    @EventHandler
    fun died(event: PlayerDeathEvent) {
        for (player in Bukkit.getOnlinePlayers()) {
            if(humanTeam?.hasEntry(event.entity.name) == true) {
                humanTeam?.removeEntry(event.entity.name)
                superZombieTeam?.addEntry(event.entity.name)
                for (player in Bukkit.getOnlinePlayers()) {
                    player.sendTitle(
                        "${ChatColor.RED}생존자 사망",
                        "${ChatColor.RED}${event.entity.name}님이 슈퍼 좀비가 되었습니다!",
                        10,
                        70,
                        20
                    )
                }

            }
        }
    }

}