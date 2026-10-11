package dk.jsdfj.hud_pctl

import com.mojang.brigadier.CommandDispatcher
import dk.jsdfj.hud_pctl.Hud_pctl.Companion.LOGGER
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.ClientCommands
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.commands.CommandBuildContext

object PctlCommandManager {
    fun createCommands() {
        ClientCommandRegistrationCallback.EVENT.register(ClientCommandRegistrationCallback { dispatcher: CommandDispatcher<FabricClientCommandSource>, _: CommandBuildContext? ->
            PctlCommandManager::class.java.declaredMethods.forEach { method ->
                val commandName = method.getAnnotation(PctlCommand::class.java)?.name ?: return@forEach
                dispatcher.register(
                    ClientCommands.literal("playerctl").then(ClientCommands.literal(commandName).executes {
                        method.invoke(this)
                        1
                    })
                )
            }
        })
    }


    @PctlCommand("stop")
    fun stopMedia() {
        try {
            ProcessBuilder()
                .command("playerctl", "stop")
                .redirectErrorStream(true)
                .start()
        } catch (e: Exception) {
            LOGGER.error("Failed to play media", e)
        }
    }

    @PctlCommand("toggle")
    fun toggleMedia() {
        try {
            ProcessBuilder()
                .command("playerctl", "play-pause")
                .redirectErrorStream(true)
                .start()
        } catch (e: Exception) {
            LOGGER.error("Failed to play media", e)
        }
    }

    @PctlCommand("next")
    fun nextMedia() {
        try {
            ProcessBuilder()
                .command("playerctl", "next")
                .redirectErrorStream(true)
                .start()
        } catch (e: Exception) {
            LOGGER.error("Failed to play media", e)
        }
    }

    @PctlCommand("previous")
    fun previousMedia() {
        try {
            ProcessBuilder()
                .command("playerctl", "previous")
                .redirectErrorStream(true)
                .start()
        } catch (e: Exception) {
            LOGGER.error("Failed to play media", e)
        }
    }

    @PctlCommand("pause")
    fun pauseMedia() {
        try {
            ProcessBuilder()
                .command("playerctl", "pause")
                .redirectErrorStream(true)
                .start()
        } catch (e: Exception) {
            LOGGER.error("Failed to play media", e)
        }
    }

    @PctlCommand("play")
    fun playMedia() {
        try {
            ProcessBuilder()
                .command("playerctl", "play")
                .redirectErrorStream(true)
                .start()
        } catch (e: Exception) {
            LOGGER.error("Failed to play media", e)
        }
    }

    @PctlCommand("shuffle")
    fun shuffleMedia() {
        try {
            ProcessBuilder()
                .command("playerctl", "shuffle", "toggle")
                .redirectErrorStream(true)
                .start()
        } catch (e: Exception) {
            LOGGER.error("Failed to play media", e)
        }
    }
}