package xyz.namutree0345.zombies

import org.bukkit.Bukkit
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityTargetEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class EventListener : Listener {

    @EventHandler
    fun join(event: PlayerJoinEvent) {
        event.player.scoreboard = board!!
        zombieTeam?.addEntry(event.player.name)

        val taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(JavaPlugin.getPlugin(Zombies::class.java), {
            if(humanTeam?.hasEntry(event.player.name) == false) {
                event.player.addPotionEffect(PotionEffect(PotionEffectType.POISON, 5, 0, false, false))
                event.player.addPotionEffect(PotionEffect(PotionEffectType.NIGHT_VISION, 5, 255, false, false))
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