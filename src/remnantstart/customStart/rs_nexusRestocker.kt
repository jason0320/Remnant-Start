package data.remnantstart.customStart

import com.fs.starfarer.api.campaign.listeners.EconomyTickListener
import com.fs.starfarer.api.impl.campaign.procgen.themes.MiscellaneousThemeGenerator

class rs_nexusRestocker : EconomyTickListener { // restocks nexii monthly
    override fun reportEconomyTick(iterIndex: Int) {
    }

    override fun reportEconomyMonthEnd() {
        val nexii = MiscellaneousThemeGenerator.getRemnantStations(true, false)
        nexii.forEach {
            it.cargo.clear()
            it.cargo.addAll(addNexusCargo(it))
        }
    }
}