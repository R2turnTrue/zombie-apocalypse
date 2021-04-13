package xyz.namutree0345.zombies.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import xyz.namutree0345.zombies.Zombies
import xyz.namutree0345.zombies.superZombieTeam
import xyz.namutree0345.zombies.zombieTeam
import java.util.*

var mujuk = true

class StartParming : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender.isOp) {
            val a = getTimeByTick(0, 0, 15)
            var tmp = a
            val taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(JavaPlugin.getPlugin(Zombies::class.java), {
                for (player in Bukkit.getOnlinePlayers()) {
                    player.sendActionBar(Component.text("남은 파밍 시간: $tmp", NamedTextColor.GREEN))
                }
                tmp--
            }, 0, 1L)
            Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(Zombies::class.java), {
                Bukkit.getScheduler().cancelTask(taskId)
                for (player in Bukkit.getOnlinePlayers()) {
                    player.sendActionBar(Component.text("무적 상태가 풀렸습니다. 숙주가 정해집니다.", NamedTextColor.RED))
                }
                val plrs = Bukkit.getOnlinePlayers().toTypedArray()
                val sukjue = plrs[Random().nextInt(plrs.size)]
                superZombieTeam?.addEntry(sukjue.name)
                for (player in Bukkit.getOnlinePlayers()) {
                    player.sendMessage(Component.text("숙주: ${sukjue.name}", NamedTextColor.RED))
                }
                mujuk = false
                tmp--
            }, 1L)
        }
        return true
    }

    private fun getTimeByTick(hour: Int, minute: Int, second: Int) : Int = (((20 * 60) * 60) * hour) + ((20 * 60) * minute) + (20 * second)
}
