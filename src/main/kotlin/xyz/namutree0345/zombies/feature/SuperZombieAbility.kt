package xyz.namutree0345.zombies.feature

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.plugin.java.JavaPlugin
import xyz.namutree0345.zombies.Zombies
import xyz.namutree0345.zombies.superZombieTeam
import xyz.namutree0345.zombies.zombieTeam

val summonTicks: HashMap<Player, Int> = HashMap()
val summonWaitingTicks: HashMap<Player, Int> = HashMap()
val summonCooltime: ArrayList<Player> = ArrayList()

class SuperZombieAbility : Listener {

    private fun cancelSummon(player: Player) {
        Bukkit.getScheduler().cancelTask(summonTicks[player]!!)
        summonTicks.remove(player)
        if(summonWaitingTicks.containsKey(player)) {
            summonWaitingTicks.remove(player)
        }
        if (summonCooltime.contains(player)) {
            summonCooltime.remove(player)
        }
        player.sendMessage(Component.text("소환술이 취소되었습니다.", NamedTextColor.RED))
    }

    @EventHandler
    fun onShift(event: PlayerInteractEvent) {
        if(event.action == Action.RIGHT_CLICK_AIR) {
            if(event.item?.type == Material.DIAMOND) {
                if (!summonCooltime.contains(event.player)) {
                    if (superZombieTeam?.hasEntry(event.player.name) == true) {
                        summonCooltime.add(event.player)
                        summonTicks[event.player] =
                            Bukkit.getScheduler().scheduleSyncRepeatingTask(JavaPlugin.getPlugin(Zombies::class.java), {
                                if (!summonWaitingTicks.containsKey(event.player)) {
                                    summonWaitingTicks[event.player] = 3
                                }
                                event.player.sendMessage(
                                    Component.text(
                                        "소환: ${summonWaitingTicks[event.player]}초 남음",
                                        NamedTextColor.GOLD
                                    )
                                )
                                summonWaitingTicks[event.player] = summonWaitingTicks[event.player]!! - 1

                            }, 0L, 20L)
                        Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(Zombies::class.java), {
                            if (summonTicks.containsKey(event.player)) {
                                event.item?.subtract(1)
                                event.player.world.strikeLightningEffect(event.player.location)
                                Bukkit.getScheduler().cancelTask(summonTicks[event.player]!!)
                                for (plr in Bukkit.getOnlinePlayers()) {
                                    if (zombieTeam?.hasEntry(plr.name) == true || superZombieTeam?.hasEntry(plr.name) == true) {
                                        plr.teleport(event.player)
                                    }
                                }
                                summonTicks.remove(event.player)
                                if (summonWaitingTicks.containsKey(event.player)) {
                                    summonWaitingTicks.remove(event.player)
                                }

                                Bukkit.getScheduler()
                                    .scheduleSyncDelayedTask(JavaPlugin.getPlugin(Zombies::class.java), {
                                        if (summonCooltime.contains(event.player)) {
                                            summonCooltime.remove(event.player)
                                        }
                                    }, 20 * 60 * 5)
                            }
                        }, 20 * 3)
                    }
                }
            }
        } else {
            if(superZombieTeam?.hasEntry(event.player.name) == true && summonTicks.containsKey(event.player)) {
                cancelSummon(event.player)
            }
        }
    }
}