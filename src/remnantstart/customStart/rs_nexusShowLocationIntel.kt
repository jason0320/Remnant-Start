package data.remnantstart.customStart

import com.fs.starfarer.api.Global
import com.fs.starfarer.api.campaign.CampaignFleetAPI
import com.fs.starfarer.api.campaign.SectorEntityToken
import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin
import com.fs.starfarer.api.ui.SectorMapAPI
import com.fs.starfarer.api.ui.TooltipMakerAPI
import second_in_command.misc.getAndLoadSprite

class rs_nexusShowLocationIntel(var fleet: CampaignFleetAPI): BaseIntelPlugin() {

    override fun getIcon(): String {
        val path = "graphics/factions/crest_ai_remnant.png"
        Global.getSettings().getAndLoadSprite(path)
        return path
    }

    override fun getMapLocation(map: SectorMapAPI?): SectorEntityToken {
        return fleet
    }

    override fun getName(): String {
        if (fleet.memoryWithoutUpdate.contains("\$damagedStation")) return "Nexus (Damaged)"
        return "Nexus"
    }

    override fun createSmallDescription(info: TooltipMakerAPI?, width: Float, height: Float) {
        var name = "Nexus"
        if (fleet.memoryWithoutUpdate.contains("\$damagedStation")) name = "Damaged Nexus"
        info!!.addPara("A $name is located in the ${fleet.starSystem.name}.", 5f)
    }

    override fun setNew(isNew: Boolean) {
        setNew(false)
    }

    override fun isNew(): Boolean {
        return false
    }

    override fun getIntelTags(map: SectorMapAPI?): MutableSet<String>? {
        return null
    }


}