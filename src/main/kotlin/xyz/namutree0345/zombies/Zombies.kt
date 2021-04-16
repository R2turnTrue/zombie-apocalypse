package xyz.namutree0345.zombies

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.*
import org.bukkit.entity.Firework
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.meta.FireworkMeta
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team
import xyz.namutree0345.firework.tabCompleter.PlayerListTabCompleter
import xyz.namutree0345.zombies.command.*
import xyz.namutree0345.zombies.feature.SuperZombieAbility
import xyz.namutree0345.zombies.feature.ToSuperZombie
import xyz.namutree0345.zombies.feature.VaccineFeature
import xyz.namutree0345.zombies.listener.DamageListener
import xyz.namutree0345.zombies.listener.EventListener
import xyz.namutree0345.zombies.listener.RemovePlayerFromPlayerList
import java.util.*
import kotlin.collections.HashMap


var humanTeam: Team? = null
var zombieTeam: Team? = null
var superZombieTeam: Team? = null
var board: Scoreboard? = null

var vaccine: ItemStack? = null
var noczep: ItemStack? = null

val teams = HashMap<UUID, String>()

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
        server.pluginManager.registerEvents(RemovePlayerFromPlayerList(), this)
        server.pluginManager.registerEvents(ToSuperZombie(), this)
        server.pluginManager.registerEvents(DamageListener(), this)
        server.pluginManager.registerEvents(VaccineFeature(), this)
        server.pluginManager.registerEvents(SuperZombieAbility(), this)
        getCommand("sethuman")?.also {
            it.setExecutor(SetHuman())
            it.tabCompleter = PlayerListTabCompleter()
        }
        getCommand("setzombie")?.also {
            it.setExecutor(SetZombie())
            it.tabCompleter = PlayerListTabCompleter()
        }
        getCommand("setsuperzombie")?.also {
            it.setExecutor(SetSuperZombie())
            it.tabCompleter = PlayerListTabCompleter()
        }
        getCommand("startparming")?.also {
            it.setExecutor(StartParming())
        }
        getCommand("forcereleasemujuk")?.also {
            it.setExecutor(ForceReleaseMujuk())
        }

        noczep = ItemStack(Material.SUSPICIOUS_STEW).also {
            it.itemMeta = it.itemMeta.let { it2 ->
                it2.displayName(Component.text("녹즙", NamedTextColor.DARK_GREEN, TextDecoration.BOLD, TextDecoration.ITALIC))
                it2
            }
        }

        vaccine = ItemStack(Material.FIREWORK_ROCKET).also {
            it.itemMeta = (it.itemMeta as FireworkMeta).let { it2 ->
                val builder = FireworkEffect.builder()
                builder.withTrail().withFlicker().withFade(
                    Color.GREEN, Color.WHITE, Color.YELLOW, Color.BLUE,
                    Color.FUCHSIA, Color.PURPLE, Color.MAROON, Color.LIME, Color.ORANGE)
                    .with(FireworkEffect.Type.BALL_LARGE).withColor(Color.AQUA);
                it2.addEffect(builder.build())
                it2.displayName(Component.text("백신", TextColor.color(0x58A8B4), TextDecoration.BOLD, TextDecoration.ITALIC))
                it2
            }
        }

        val noczepRecipe = ShapedRecipe(NamespacedKey(getPlugin(Zombies::class.java), "noczep_recipe"), noczep!!)
        noczepRecipe.shape( "A  ",
                                    "B  ",
                                    "C  ")
        noczepRecipe.setIngredient('A', Material.OAK_SAPLING)
        noczepRecipe.setIngredient('B', Material.SPRUCE_SAPLING)
        noczepRecipe.setIngredient('C', Material.DARK_OAK_SAPLING)

        val vaccineRecipe = ShapedRecipe(NamespacedKey(getPlugin(Zombies::class.java), "vaccine_recipe"), vaccine!!)
        vaccineRecipe.shape(" # ",
                                    "GBO",
                                    " S ")
        vaccineRecipe.setIngredient('#', Material.ZOMBIE_HEAD)
        vaccineRecipe.setIngredient('G', Material.GOLDEN_CARROT)
        vaccineRecipe.setIngredient('B', Material.GLASS_BOTTLE)
        vaccineRecipe.setIngredient('O', Material.GOLDEN_APPLE)
        vaccineRecipe.setIngredient('S', noczep!!)

        server.addRecipe(noczepRecipe)
        server.addRecipe(vaccineRecipe)
    }

}