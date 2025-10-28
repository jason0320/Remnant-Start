package data.remnantstart.plugins

import com.fs.starfarer.api.BaseModPlugin
import com.fs.starfarer.api.Global
import com.fs.starfarer.api.campaign.GenericPluginManagerAPI
import com.fs.starfarer.api.combat.ShipAPI
import com.fs.starfarer.api.combat.ShipHullSpecAPI
import com.fs.starfarer.api.impl.campaign.DerelictShipEntityPlugin
import com.fs.starfarer.api.impl.campaign.ids.Abilities
import com.fs.starfarer.api.impl.campaign.ids.Factions
import com.fs.starfarer.api.impl.campaign.ids.Tags
import com.fs.starfarer.api.loading.Description
import data.remnantstart.scripts.*
import exerelin.campaign.DiplomacyManager

class RS_modPlugin: BaseModPlugin() {

    override fun onGameLoad(newGame: Boolean) { // the mod plugin is TA but most things are labelled SB because i refactored wayyy too fucking late kill me
        val plugins: GenericPluginManagerAPI = Global.getSector().genericPlugins
        val sector = Global.getSector()
        val intmgr = Global.getSector().intelManager
        // if (!plugins.hasPlugin(rs_campaignPlugin::class.java)) plugins.addPlugin(rs_campaignPlugin())
        Global.getSector().registerPlugin(rs_campaignPlugin())

        if ( Global.getSector().memoryWithoutUpdate.getBoolean("\$rs_nexusStart")){
            val variants = Global.getSettings().allVariantIds
            Global.getSector().getFaction(Factions.REMNANTS).knownShips.forEach {

                if (Global.getSettings().getHullSpec(it).manufacturer != "Remnant" && it != "rat_genesis") { // vague attempt to force remnants to re-learn hulls on save load if they don't have a default role
                    var role = "combatSmall" // we had to blacklist genesis because it uses a boss script that makes the game shit itself, i think?
                    when (Global.getSettings().getHullSpec(it).hullSize) {
                        ShipAPI.HullSize.CAPITAL_SHIP -> role = "combatCapital"
                        ShipAPI.HullSize.CRUISER -> role = "combatLarge"
                        ShipAPI.HullSize.DESTROYER -> role = "combatMedium"
                        else -> role = "combatSmall"
                    }
                    for (variant in variants) {
                        //if (Global.getSettings().getVariant(variant).hullSpec.hullId == it && Global.getSettings().getVariant(variant).isGoalVariant) {
                        if (Global.getSettings().getVariant(variant).hullSpec.hullId == it && Global.getSettings().getVariant(variant).isGoalVariant) {
                            Global.getSettings().addDefaultEntryForRole(role, variant, 0f) // set 0 weight so it doesn't bleed over into other fleets (if we learned the eternity and set it to >0 weight, it would spawn in enigma fleets. this is bad!)
                            Global.getSettings().addEntryForRole(Factions.REMNANTS, role, variant, (0.5f)) // 1 weight is actually pretty high
                        }
                    }
                }
            }

        }
    }

    override fun onNewGameAfterEconomyLoad() {
        if ( Global.getSector().memoryWithoutUpdate.getBoolean("\$rs_nexusStart")) {
            val player = Global.getSector().getFaction(Factions.PLAYER)
            for (faction in Global.getSector().getAllFactions()) {
                val factionId = faction.getId()
                if (factionId == Factions.PLAYER) continue
                if (factionId == Factions.DERELICT) continue
                if (factionId == "nex_derelict") continue
                if (factionId == Factions.REMNANTS) continue
                if (factionId == Factions.OMEGA) continue
                if (factionId == Factions.TRITACHYON) continue
                if (factionId == "sotf_dustkeepers") continue
                if (factionId == "sotf_dustkeepers_proxies") continue
                if (factionId == "sotf_sierra_faction") continue
                if (factionId == "sotf_dreaminggestalt") continue

                player.setRelationship(factionId, DiplomacyManager.STARTING_RELATIONSHIP_HOSTILE)
            }
            player.setRelationship(Factions.DERELICT, 0f)
            player.setRelationship(Factions.OMEGA, 0f)

            val remmy = Global.getSector().getFaction(Factions.REMNANTS)
            remmy.setRelationship(Factions.DERELICT, 0f)
            remmy.setRelationship(Factions.OMEGA, 0f)

        }
    }

}