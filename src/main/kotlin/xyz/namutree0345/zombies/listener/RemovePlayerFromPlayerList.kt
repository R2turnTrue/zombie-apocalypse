package xyz.namutree0345.zombies.listener

import net.kyori.adventure.text.Component
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.server.ServerListPingEvent

class RemovePlayerFromPlayerList : Listener {

    @EventHandler
    fun serverListPing(event: ServerListPingEvent) {
        val it = event.iterator()
        while(it.hasNext()) {
            it.next()
            it.remove()
        }
    }

    @EventHandler
    fun join(event: PlayerJoinEvent) {
        event.joinMessage(Component.empty())

        for(p in Bukkit.getOnlinePlayers()) {
            val pc = p as CraftPlayer
            val packet = PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, pc.handle)

            (event.player as CraftPlayer).handle.playerConnection.sendPacket(packet)
        }
    }

}