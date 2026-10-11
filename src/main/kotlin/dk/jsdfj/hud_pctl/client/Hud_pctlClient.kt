package dk.jsdfj.hud_pctl.client

import dev.ngspace.hudder.Hudder
import dev.ngspace.hudder.api.variableregistry.DataVariable
import dev.ngspace.hudder.api.variableregistry.DataVariableRegistry
import dev.ngspace.hudder.api.variableregistry.VariableTypes
import dev.ngspace.hudder.utils.HudFileUtils
import dk.jsdfj.hud_pctl.Hud_pctl
import dk.jsdfj.hud_pctl.Hud_pctl.Companion.LOGGER
import dk.jsdfj.hud_pctl.PlayerctlManager
import net.fabricmc.api.ClientModInitializer
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException


class Hud_pctlClient : ClientModInitializer {

    override fun onInitializeClient() {
        createVars()

        val dest = File(HudFileUtils.FOLDER, "media.hud")
        if (!dest.exists()) {
            try {
                FileUtils.copyURLToFile(
                    Hud_pctlClient::class.java.getResource(Hud_pctl.ASSETS + "huds/" + "media.hud"),
                    dest
                )
            } catch (e: IOException) {
                if (Hudder.IS_DEBUG) e.printStackTrace()
                Hudder.error("Failed to generate default hud " + "media.hud")
            }
        }

        LOGGER.info("Initialized Hud_pctl")
    }

    fun createVars() {
        DataVariableRegistry.registerVariable(
            DataVariable { _: String? -> PlayerctlManager.playerName },
            VariableTypes.STRING,
            "pctl_player"
        )
        DataVariableRegistry.registerVariable(
            DataVariable { _: String? -> PlayerctlManager.title },
            VariableTypes.STRING,
            "pctl_title"
        )
        DataVariableRegistry.registerVariable(
            DataVariable { _: String? -> PlayerctlManager.artist },
            VariableTypes.STRING,
            "pctl_artist"
        )
        DataVariableRegistry.registerVariable(
            DataVariable { _: String? -> PlayerctlManager.album },
            VariableTypes.STRING,
            "pctl_album"
        )
        DataVariableRegistry.registerVariable(
            DataVariable { _: String? -> PlayerctlManager.status },
            VariableTypes.STRING,
            "pctl_status"
        )
        DataVariableRegistry.registerVariable(
            DataVariable { _: String? -> PlayerctlManager.position },
            VariableTypes.STRING,
            "pctl_position"
        )
        DataVariableRegistry.registerVariable(
            DataVariable { _: String? -> PlayerctlManager.duration },
            VariableTypes.STRING,
            "pctl_duration"
        )
        DataVariableRegistry.registerVariable(
            DataVariable { _: String? -> PlayerctlManager.volume },
            VariableTypes.NUMBER,
            "pctl_volume"
        )
        DataVariableRegistry.registerVariable(
            DataVariable { _: String? -> PlayerctlManager.volumeRaw },
            VariableTypes.NUMBER,
            "pctl_volume_raw"
        )
        DataVariableRegistry.registerVariable(
            DataVariable { _: String? -> PlayerctlManager.positionRaw },
            VariableTypes.NUMBER,
            "pctl_position_raw"
        )
        DataVariableRegistry.registerVariable(
            DataVariable { _: String? -> PlayerctlManager.durationRaw },
            VariableTypes.NUMBER,
            "pctl_duration_raw"
        )
        DataVariableRegistry.registerVariable(
            DataVariable { _: String? -> PlayerctlManager.shuffle },
            VariableTypes.BOOLEAN,
            "pctl_shuffle"
        )
        DataVariableRegistry.registerVariable(
            DataVariable { _: String? -> PlayerctlManager.loop },
            VariableTypes.STRING,
            "pctl_loop"
        )
    }
}
