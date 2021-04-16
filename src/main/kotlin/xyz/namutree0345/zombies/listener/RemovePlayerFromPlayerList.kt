package xyz.namutree0345.zombies.listener

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
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
        event.player.playerListName(Component.text("abcdefgh", NamedTextColor.WHITE, TextDecoration.OBFUSCATED))

        for(p in Bukkit.getOnlinePlayers()) {
            val pc = p as CraftPlayer

            (event.player as CraftPlayer).handle.playerConnection.sendPacket(PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, pc.handle))
        }
    }

}