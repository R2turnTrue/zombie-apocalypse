package xyz.namutree0345.zombies.listener

import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import xyz.namutree0345.zombies.command.mujuk
import xyz.namutree0345.zombies.superZombieTeam
import xyz.namutree0345.zombies.zombieTeam

class DamageListener : Listener {
    @EventHandler
    fun damage(event: EntityDamageEvent) {

        if(mujuk) {
            event.isCancelled = true
            return
        }

        if(event.entityType == EntityType.PLAYER && event.cause == EntityDamageEvent.DamageCause.FALL) {
            event.damage = 0.0
            (event.entity as Player).addPotionEffect(PotionEffect(PotionEffectType.SLOW, 20, 5, false, false))
        }
    }

    @EventHandler
    fun damageByZombie(event: EntityDamageByEntityEvent) {

        if(mujuk) {
            event.isCancelled = true
            return
        }

        if(event.damager is Projectile && event.entity is Player) {
            if((event.damager as Projectile).shooter is Player) {
                if (zombieTeam?.hasEntry(((event.damager as Projectile).shooter as Player).name) == true || superZombieTeam?.hasEntry(((event.damager as Projectile).shooter as Player).name) == true) {
                    event.damage = event.damage - (event.damage / 3)
                }
            }
        }
        if(event.damager is Player && event.entity is Player) {
            if(zombieTeam?.hasEntry(event.damager.name) == true || superZombieTeam?.hasEntry(event.damager.name) == true) {
                if(zombieTeam?.hasEntry(event.entity.name) == true || superZombieTeam?.hasEntry(event.entity.name) == true) {
                    event.isCancelled = true
                    return
                } else {
                    event.damage = event.damage - (event.damage / 3)
                }
            }
        }
    }
}