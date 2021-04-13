package xyz.namutree0345.zombies.listener

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityTargetEvent
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.player.PlayerInteractEvent
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

    @EventHandler
    fun chat(event: io.papermc.paper.event.player.AsyncChatEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun enchant(event: PlayerInteractEvent) {
        if(event.hasBlock() && (event.clickedBlock?.type == Material.ANVIL || event.clickedBlock?.type == Material.ENCHANTING_TABLE)) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onEquip(event: PlayerArmorChangeEvent) {
        if(zombieTeam?.hasEntry(event.player.name) == true && superZombieTeam?.hasEntry(event.player.name) == true) {
            val disabledItems = listOf<Material>(
                Material.DIAMOND_HELMET,
                Material.DIAMOND_CHESTPLATE,
                Material.DIAMOND_LEGGINGS,
                Material.DIAMOND_BOOTS,
                Material.NETHERITE_HELMET,
                Material.NETHERITE_CHESTPLATE,
                Material.NETHERITE_LEGGINGS,
                Material.NETHERITE_BOOTS
            )
            for (t in disabledItems) {
                if (event.newItem?.type == t) {
                    event.player.inventory.remove(event.newItem!!)
                    break
                }
            }
        }
    }

    @EventHandler
    fun onCraft(event: CraftItemEvent) {
        if(zombieTeam?.hasEntry(event.whoClicked.name) == true || superZombieTeam?.hasEntry(event.whoClicked.name) == true) {
            val disabledItems = listOf<Material>(
                Material.ENCHANTING_TABLE,
                Material.ANVIL,
                Material.DIAMOND_SWORD,
                Material.DIAMOND_AXE,
                Material.DIAMOND_SHOVEL,
                Material.DIAMOND_HOE,
                Material.DIAMOND_HELMET,
                Material.DIAMOND_CHESTPLATE,
                Material.DIAMOND_LEGGINGS,
                Material.DIAMOND_BOOTS,
                Material.NETHERITE_SWORD,
                Material.NETHERITE_AXE,
                Material.NETHERITE_SHOVEL,
                Material.NETHERITE_HOE,
                Material.NETHERITE_HELMET,
                Material.NETHERITE_CHESTPLATE,
                Material.NETHERITE_LEGGINGS,
                Material.NETHERITE_BOOTS
            )
            for (t in disabledItems) {
                if (event.recipe.result.type == t) {
                    event.isCancelled = true
                    return
                }
            }
        }
    }

}