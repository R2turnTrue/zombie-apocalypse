package xyz.namutree0345.zombies

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class EventListener : Listener {

    @EventHandler
    fun join(event: PlayerJoinEvent) {
        event.player.scoreboard = board!!
        zombieTeam?.addEntry(event.player.name)
    }

}