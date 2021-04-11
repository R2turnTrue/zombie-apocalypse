package xyz.namutree0345.zombies

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team


var humanTeam: Team? = null
var zombieTeam: Team? = null
var superZombieTeam: Team? = null
var board: Scoreboard? = null

var vaccine: ItemStack? = null

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

        vaccine = ItemStack(Material.FIREWORK_ROCKET).also {
            it.itemMeta = it.itemMeta.let { it2 ->
                it2.displayName(Component.text("백신", TextColor.color(0x58A8B4), TextDecoration.BOLD, TextDecoration.ITALIC))
                it2
            }
        }

        val vaccineRecipe = ShapedRecipe(NamespacedKey(getPlugin(Zombies::class.java), "vaccine_recipe"), vaccine!!)
        vaccineRecipe.shape(" # ",
                                    "GBO",
                                    " S ")
        vaccineRecipe.setIngredient('#', Material.ZOMBIE_HEAD)
        vaccineRecipe.setIngredient('G', Material.GOLDEN_CARROT)
        vaccineRecipe.setIngredient('B', Material.GLASS_BOTTLE)
        vaccineRecipe.setIngredient('O', Material.GOLDEN_APPLE)
        vaccineRecipe.setIngredient('S', Material.OAK_SAPLING)

        server.addRecipe(vaccineRecipe)
    }

}