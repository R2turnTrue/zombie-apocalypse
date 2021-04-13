package xyz.namutree0345.zombies.listener

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityTargetEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import xyz.namutree0345.zombies.*
import xyz.namutree0345.zombies.feature.floatingItemPool

class EventListener : Listener {

    @EventHandler
    fun join(event: PlayerJoinEvent) {
        event.player.scoreboard = board!!
        zombieTeam?.addEntry(event.player.name)

        val taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(JavaPlugin.getPlugin(Zombies::class.java), {
            if(humanTeam?.hasEntry(event.player.name) == false) {
                event.player.addPotionEffect(PotionEffect(PotionEffectType.POISON, 5, 0, false, false))
                event.player.addPotionEffect(PotionEffect(PotionEffectType.NIGHT_VISION, 40, 255, false, false))
            }
            if(superZombieTeam?.hasEntry(event.player.name) == true) {
                floatingItemPool[event.player]?.teleport(Location(event.player.world, event.player.location.x, event.player.location.y + 3, event.player.location.z))
            }
        }, 0, 1)
        Bukkit.getScheduler().scheduleSyncRepeatingTask(JavaPlugin.getPlugin(Zombies::class.java), {
            if(!Bukkit.getOnlinePlayers().contains(event.player)) {
                Bukkit.getScheduler().cancelTask(taskId)
            }
        }, 0, 1)
    }

    @EventHandler
    fun mobTarget(event: EntityTargetEvent) {
        if(event.entity is Monster) {
            if(event.target is Player) {
                if(zombieTeam?.hasEntry(event.target?.name!!) == true || superZombieTeam?.hasEntry(event.target?.name!!) == true) {
                    event.isCancelled = true
                }
            }
        }
    }

}