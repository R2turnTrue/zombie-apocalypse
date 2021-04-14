package xyz.namutree0345.zombies.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import xyz.namutree0345.zombies.humanTeam
import xyz.namutree0345.zombies.superZombieTeam
import xyz.namutree0345.zombies.zombieTeam

class SetSuperZombie : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if(args.isNotEmpty()) {
            if (sender.isOp) {
                if (humanTeam?.hasEntry(args[0]) == true) {
                    humanTeam?.removeEntry(args[0])
                    superZombieTeam?.addEntry(args[0])
                    sender.sendMessage(Component.text("성공적으로 설정되었습니다!", NamedTextColor.RED))
                }
                if (zombieTeam?.hasEntry(args[0]) == true) {
                    zombieTeam?.removeEntry(args[0])
                    superZombieTeam?.addEntry(args[0])
                    sender.sendMessage(Component.text("성공적으로 설정되었습니다!", NamedTextColor.RED))
                }
            }

        }

        return true

    }
}