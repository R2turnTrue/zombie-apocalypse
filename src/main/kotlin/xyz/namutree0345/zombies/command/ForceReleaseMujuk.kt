package xyz.namutree0345.zombies.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ForceReleaseMujuk : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if(sender.isOp) {
            mujuk = false
        }

        return true

    }
}