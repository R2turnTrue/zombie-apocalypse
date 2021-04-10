package xyz.namutree0345.zombies

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class SetHuman : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if(sender.isOp && zombieTeam?.hasEntry(sender.name) == true) {
            zombieTeam?.removeEntry(sender.name)
            humanTeam?.addEntry(sender.name)
        }

        return true

    }
}