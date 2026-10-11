package dk.jsdfj.hud_pctl.client

import dev.ngspace.hudder.Hudder
import dev.ngspace.hudder.api.variableregistry.DataVariableRegistry
import dev.ngspace.hudder.utils.HudFileUtils
import dk.jsdfj.hud_pctl.Hud_pctl
import dk.jsdfj.hud_pctl.Hud_pctl.Companion.LOGGER
import dk.jsdfj.hud_pctl.PlayerctlManager
import net.fabricmc.api.ClientModInitializer
import java.io.IOException
import java.nio.file.Files


class Hud_pctlClient : ClientModInitializer {

    override fun onInitializeClient() {
        createVars()

        val dest = HudFileUtils.FOLDER.resolve("media.hud")
        if (!Files.exists(dest)) {
            try {
                Files.copy(
                    Hud_pctlClient::class.java.getResourceAsStream(Hud_pctl.ASSETS + "huds/media.hud")!!,
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
        DataVariableRegistry.registerStringVariable(
            { _: String? -> PlayerctlManager.playerName },
            "pctl_player"
        )
        DataVariableRegistry.registerStringVariable(
            { _: String? -> PlayerctlManager.title },
            "pctl_title"
        )
        DataVariableRegistry.registerStringVariable(
            { _: String? -> PlayerctlManager.artist },
            "pctl_artist"
        )
        DataVariableRegistry.registerStringVariable(
            { _: String? -> PlayerctlManager.album },
            "pctl_album"
        )
        DataVariableRegistry.registerStringVariable(
            { _: String? -> PlayerctlManager.status },
            "pctl_status"
        )
        DataVariableRegistry.registerStringVariable(
            { _: String? -> PlayerctlManager.position },
            "pctl_position"
        )
        DataVariableRegistry.registerStringVariable(
            { _: String? -> PlayerctlManager.duration },
            "pctl_duration"
        )
        DataVariableRegistry.registerNumberVariable(
            { _: String? -> PlayerctlManager.volume },
            "pctl_volume"
        )
        DataVariableRegistry.registerNumberVariable(
            { _: String? -> PlayerctlManager.volumeRaw },
            "pctl_volume_raw"
        )
        DataVariableRegistry.registerNumberVariable(
            { _: String? -> PlayerctlManager.positionRaw },
            "pctl_position_raw"
        )
        DataVariableRegistry.registerNumberVariable(
            { _: String? -> PlayerctlManager.durationRaw },
            "pctl_duration_raw"
        )
        DataVariableRegistry.registerBooleanVariable(
            { _: String? -> PlayerctlManager.shuffle },
            "pctl_shuffle"
        )
        DataVariableRegistry.registerStringVariable(
            { _: String? -> PlayerctlManager.loop },
            "pctl_loop"
        )
    }
}
