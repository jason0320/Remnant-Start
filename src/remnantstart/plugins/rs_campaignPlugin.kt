package data.remnantstart.plugins

import com.fs.starfarer.api.PluginPick
import com.fs.starfarer.api.campaign.*
import com.fs.starfarer.api.campaign.CampaignPlugin.PickPriority

class rs_campaignPlugin: BaseCampaignPlugin() {

    override fun pickAICoreOfficerPlugin(commodityId: String): PluginPick<AICoreOfficerPlugin>? {
        return when (commodityId) {
            "rs_playercore" -> PluginPick<AICoreOfficerPlugin>(rs_coreThingyPlugin(), PickPriority.MOD_SET)
            else -> null
        }
    }




}