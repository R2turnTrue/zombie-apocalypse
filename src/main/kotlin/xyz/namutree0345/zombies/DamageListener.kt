package xyz.namutree0345.zombies

import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class DamageListener : Listener {
    @EventHandler
    fun damage(event: EntityDamageEvent) {
        if(event.entityType == EntityType.PLAYER && event.cause == EntityDamageEvent.DamageCause.FALL) {
            event.damage = 0.0
            (event.entity as Player).addPotionEffect(PotionEffect(PotionEffectType.SLOW, 60, 1, false, false))
        }
    }
}