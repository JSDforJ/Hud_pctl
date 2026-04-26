package dk.jsdfj.hud_pctl.client

import dev.ngspace.hudder.api.variableregistry.DataVariable
import dev.ngspace.hudder.api.variableregistry.DataVariableRegistry
import dev.ngspace.hudder.api.variableregistry.VariableTypes
import dk.jsdfj.hud_pctl.Hud_pctl.Companion.LOGGER
import dk.jsdfj.hud_pctl.PlayerctlManager
import net.fabricmc.api.ClientModInitializer

class Hud_pctlClient : ClientModInitializer {

    override fun onInitializeClient() {
        // create vars
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

        LOGGER.info("Initialized Hud_pctl")
    }
}
