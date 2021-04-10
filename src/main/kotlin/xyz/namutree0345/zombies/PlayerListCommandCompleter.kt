package xyz.namutree0345.zombies

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class PlayerListCommandCompleter : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        val a = mutableListOf<String>()
        for (player in Bukkit.getOnlinePlayers()) {
            a.add(player.name)
        }
        return a
    }
}