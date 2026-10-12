package dk.jsdfj.hud_pctl.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import dk.jsdfj.hud_pctl.Hud_pctl.Companion.LOGGER
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.ClientCommands
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.commands.CommandBuildContext

object PctlCommandManager {
    fun createCommands() {
        ClientCommandRegistrationCallback.EVENT.register(ClientCommandRegistrationCallback { dispatcher: CommandDispatcher<FabricClientCommandSource>, _: CommandBuildContext? ->
            dispatcher.register(
                ClientCommands.literal("playerctl").then(ClientCommands.literal("stop").executes {
                    try {
                        ProcessBuilder()
                            .command("playerctl", "stop")
                            .redirectErrorStream(true)
                            .start()
                    } catch (e: Exception) {
                        LOGGER.error("Failed to play media", e)
                    }
                    1
                }).then(ClientCommands.literal("toggle").executes {
                    try {
                        ProcessBuilder()
                            .command("playerctl", "play-pause")
                            .redirectErrorStream(true)
                            .start()
                    } catch (e: Exception) {
                        LOGGER.error("Failed to play media", e)
                    }
                    1
                }).then(ClientCommands.literal("next").executes {
                    try {
                        ProcessBuilder()
                            .command("playerctl", "next")
                            .redirectErrorStream(true)
                            .start()
                    } catch (e: Exception) {
                        LOGGER.error("Failed to play media", e)
                    }
                    1
                }).then(ClientCommands.literal("previous").executes {
                    try {
                        ProcessBuilder()
                            .command("playerctl", "previous")
                            .redirectErrorStream(true)
                            .start()
                    } catch (e: Exception) {
                        LOGGER.error("Failed to play media", e)
                    }
                    1
                }).then(ClientCommands.literal("pause").executes {
                    try {
                        ProcessBuilder()
                            .command("playerctl", "pause")
                            .redirectErrorStream(true)
                            .start()
                    } catch (e: Exception) {
                        LOGGER.error("Failed to play media", e)
                    }
                    1
                }).then(ClientCommands.literal("play").executes {
                    try {
                        ProcessBuilder()
                            .command("playerctl", "play")
                            .redirectErrorStream(true)
                            .start()
                    } catch (e: Exception) {
                        LOGGER.error("Failed to play media", e)
                    }
                    1
                }).then(ClientCommands.literal("shuffle").executes {
                    try {
                        ProcessBuilder()
                            .command("playerctl", "shuffle", "toggle")
                            .redirectErrorStream(true)
                            .start()
                    } catch (e: Exception) {
                        LOGGER.error("Failed to play media", e)
                    }
                    1
                }.then(ClientCommands.argument("action", BoolArgumentType.bool()).executes {
                    try {
                        ProcessBuilder()
                            .command(
                                "playerctl",
                                "shuffle",
                                if (BoolArgumentType.getBool(it, "action")) "on" else "off"
                            )
                            .redirectErrorStream(true)
                            .start()
                    } catch (e: Exception) {
                        LOGGER.error("Failed to play media", e)
                    }
                    1
                })).then(
                    ClientCommands.literal("loop")
                        .then(ClientCommands.argument("action", LoopArgumentType.loopAction()).executes {
                            try {
                                ProcessBuilder()
                                    .command("playerctl", "loop", LoopArgumentType.getLoopAction(it, "action").name)
                                    .redirectErrorStream(true)
                                    .start()
                            } catch (e: Exception) {
                                LOGGER.error("Failed to play media", e)
                            }
                            1
                        })
                ).then(ClientCommands.literal("forward").executes {
                    try {
                        ProcessBuilder()
                            .command("playerctl", "position", "5+")
                            .redirectErrorStream(true)
                            .start()
                    } catch (e: Exception) {
                        LOGGER.error("Failed to play media", e)
                    }
                    1
                }.then(ClientCommands.argument("amount", IntegerArgumentType.integer(0, Integer.MAX_VALUE)).executes {
                    try {
                        ProcessBuilder()
                            .command(
                                "playerctl",
                                "position",
                                IntegerArgumentType.getInteger(it, "action").toString() + "+"
                            )
                            .redirectErrorStream(true)
                            .start()
                    } catch (e: Exception) {
                        LOGGER.error("Failed to play media", e)
                    }
                    1
                })).then(ClientCommands.literal("backward").executes {
                    try {
                        ProcessBuilder()
                            .command("playerctl", "position", "5-")
                            .redirectErrorStream(true)
                            .start()
                    } catch (e: Exception) {
                        LOGGER.error("Failed to play media", e)
                    }
                    1
                }.then(ClientCommands.argument("amount", IntegerArgumentType.integer(0, Integer.MAX_VALUE)).executes {
                    try {
                        ProcessBuilder()
                            .command(
                                "playerctl",
                                "position",
                                IntegerArgumentType.getInteger(it, "action").toString() + "-"
                            )
                            .redirectErrorStream(true)
                            .start()
                    } catch (e: Exception) {
                        LOGGER.error("Failed to play media", e)
                    }
                    1
                }))
            )
        })
    }
}