package dk.jsdfj.hud_pctl.client

import com.mojang.blaze3d.platform.InputConstants
import dev.ngspace.hudder.Hudder
import dev.ngspace.hudder.api.variableregistry.DataVariableRegistry
import dev.ngspace.hudder.utils.HudFileUtils
import dk.jsdfj.hud_pctl.Hud_pctl
import dk.jsdfj.hud_pctl.Hud_pctl.Companion.LOGGER
import dk.jsdfj.hud_pctl.PlayerctlManager
import dk.jsdfj.hud_pctl.commands.LoopAction
import dk.jsdfj.hud_pctl.commands.PctlCommandManager
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper
import net.minecraft.client.KeyMapping
import net.minecraft.resources.Identifier
import java.io.IOException
import java.nio.file.Files
import java.util.Locale.getDefault


class Hud_pctlClient : ClientModInitializer {
    companion object {
        val CATEGORY: KeyMapping.Category = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath("hud_pctl", "media_controls")
        )
        val playKey: KeyMapping = KeyMappingHelper.registerKeyMapping(
            KeyMapping(
                "key.hud_pctl.play",
                InputConstants.Type.KEYBOARD,
                InputConstants.UNKNOWN.value,
                this.CATEGORY
            )
        )
        val stopKey: KeyMapping = KeyMappingHelper.registerKeyMapping(
            KeyMapping(
                "key.hud_pctl.stop",
                InputConstants.Type.KEYBOARD,
                InputConstants.UNKNOWN.value,
                this.CATEGORY
            )
        )
        val pauseKey: KeyMapping = KeyMappingHelper.registerKeyMapping(
            KeyMapping(
                "key.hud_pctl.pause",
                InputConstants.Type.KEYBOARD,
                InputConstants.UNKNOWN.value,
                this.CATEGORY
            )
        )
        val toggleKey: KeyMapping = KeyMappingHelper.registerKeyMapping(
            KeyMapping(
                "key.hud_pctl.toggle",
                InputConstants.Type.KEYBOARD,
                InputConstants.UNKNOWN.value,
                this.CATEGORY
            )
        )
        val nextKey: KeyMapping = KeyMappingHelper.registerKeyMapping(
            KeyMapping(
                "key.hud_pctl.next",
                InputConstants.Type.KEYBOARD,
                InputConstants.UNKNOWN.value,
                this.CATEGORY
            )
        )
        val previousKey: KeyMapping = KeyMappingHelper.registerKeyMapping(
            KeyMapping(
                "key.hud_pctl.previous",
                InputConstants.Type.KEYBOARD,
                InputConstants.UNKNOWN.value,
                this.CATEGORY
            )
        )
        val shuffleKey: KeyMapping = KeyMappingHelper.registerKeyMapping(
            KeyMapping(
                "key.hud_pctl.shuffle",
                InputConstants.Type.KEYBOARD,
                InputConstants.UNKNOWN.value,
                this.CATEGORY
            )
        )
        val loopKey: KeyMapping = KeyMappingHelper.registerKeyMapping(
            KeyMapping(
                "key.hud_pctl.loop",
                InputConstants.Type.KEYBOARD,
                InputConstants.UNKNOWN.value,
                this.CATEGORY
            )
        )
        val forwardKey: KeyMapping = KeyMappingHelper.registerKeyMapping(
            KeyMapping(
                "key.hud_pctl.forward",
                InputConstants.Type.KEYBOARD,
                InputConstants.UNKNOWN.value,
                this.CATEGORY
            )
        )
        val backwardKey: KeyMapping = KeyMappingHelper.registerKeyMapping(
            KeyMapping(
                "key.hud_pctl.backward",
                InputConstants.Type.KEYBOARD,
                InputConstants.UNKNOWN.value,
                this.CATEGORY
            )
        )
    }

    override fun onInitializeClient() {
        createVars()
        PctlCommandManager.createCommands()

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

        ClientTickEvents.END_CLIENT_TICK.register({ client ->
            if (client.player == null) return@register
            while (playKey.consumeClick()) {
                PctlCommandManager.play()
            }
            while (stopKey.consumeClick()) {
                PctlCommandManager.stop()
            }
            while (pauseKey.consumeClick()) {
                PctlCommandManager.pause()
            }
            while (toggleKey.consumeClick()) {
                PctlCommandManager.toggle()
            }
            while (nextKey.consumeClick()) {
                PctlCommandManager.next()
            }
            while (previousKey.consumeClick()) {
                PctlCommandManager.previous()
            }
            while (shuffleKey.consumeClick()) {
                PctlCommandManager.shuffle()
            }
            while (forwardKey.consumeClick()) {
                PctlCommandManager.forward()
            }
            while (backwardKey.consumeClick()) {
                PctlCommandManager.backward()
            }
            while (loopKey.consumeClick()) {
                when (PlayerctlManager.loop) {
                    LoopAction.NONE -> PctlCommandManager.loop(LoopAction.PLAYLIST)
                    LoopAction.PLAYLIST -> PctlCommandManager.loop(LoopAction.TRACK)
                    LoopAction.TRACK -> PctlCommandManager.loop(LoopAction.NONE)
                    null -> {}
                }
            }
        })

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
            { _: String? ->
                PlayerctlManager.loop?.name?.lowercase()
                    ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString() } ?: "N/A"
            },
            "pctl_loop"
        )
    }
}
