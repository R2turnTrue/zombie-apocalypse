package xyz.namutree0345.zombies.feature

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack
import xyz.namutree0345.firework.entity.FloatingItem
import xyz.namutree0345.zombies.humanTeam
import xyz.namutree0345.zombies.superZombieTeam

val floatingItemPool = HashMap<Player, FloatingItem>()

class ToSuperZombie : Listener {

    @EventHandler
    fun died(event: PlayerDeathEvent) {
        if(humanTeam?.hasEntry(event.entity.name) == false && humanTeam?.hasEntry(event.entity.killer?.name!!) == true) {
            event.entity.world.dropItem(event.entity.location, ItemStack(Material.ZOMBIE_HEAD))
        }
        for (player in Bukkit.getOnlinePlayers()) {
            if(humanTeam?.hasEntry(event.entity.name) == true) {
                humanTeam?.removeEntry(event.entity.name)
                superZombieTeam?.addEntry(event.entity.name)

                floatingItemPool[event.entity] = FloatingItem(Material.REDSTONE_BLOCK)
                floatingItemPool[event.entity]?.spawn(
                    Location(
                        event.entity.world,
                        event.entity.location.x,
                        event.entity.location.y + 3,
                        event.entity.location.z
                    )
                )

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