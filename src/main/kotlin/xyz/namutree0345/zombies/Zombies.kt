package xyz.namutree0345.zombies

import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team

var humanTeam: Team? = null
var zombieTeam: Team? = null
var superZombieTeam: Team? = null
var board: Scoreboard? = null

class Zombies : JavaPlugin() {

    override fun onEnable() {
        val manager = Bukkit.getScoreboardManager()
        board = manager.newScoreboard

        zombieTeam = board?.registerNewTeam("zombie")
        humanTeam = board?.registerNewTeam("human")
        superZombieTeam = board?.registerNewTeam("superZombie")

        humanTeam?.setCanSeeFriendlyInvisibles(false)
        humanTeam?.setOption(Team.Option.DEATH_MESSAGE_VISIBILITY, Team.OptionStatus.NEVER)
        humanTeam?.color(NamedTextColor.AQUA)
        zombieTeam?.setCanSeeFriendlyInvisibles(false)
        zombieTeam?.setOption(Team.Option.DEATH_MESSAGE_VISIBILITY, Team.OptionStatus.NEVER)
        zombieTeam?.color(NamedTextColor.RED)
        superZombieTeam?.setCanSeeFriendlyInvisibles(false)
        superZombieTeam?.setOption(Team.Option.DEATH_MESSAGE_VISIBILITY, Team.OptionStatus.NEVER)
        superZombieTeam?.color(NamedTextColor.RED)

        server.pluginManager.registerEvents(EventListener(), this)
        server.pluginManager.registerEvents(ToSuperZombie(), this)
        server.pluginManager.registerEvents(DamageListener(), this)
        server.pluginManager.registerEvents(SuperZombieAbility(), this)
        getCommand("sethuman")?.also {
            it.setExecutor(SetHuman())
            it.setTabCompleter(PlayerListCommandCompleter())
        }
    }

}