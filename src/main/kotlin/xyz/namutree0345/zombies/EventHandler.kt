package xyz.namutree0345.zombies

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class EventHandler : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        zombieTeam?.addEntry(event.player.name)
    }

}