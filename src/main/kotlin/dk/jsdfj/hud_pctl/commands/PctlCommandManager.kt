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
                    stop()
                    1
                }).then(ClientCommands.literal("toggle").executes {
                    toggle()
                    1
                }).then(ClientCommands.literal("next").executes {
                    next()
                    1
                }).then(ClientCommands.literal("previous").executes {
                    previous()
                    1
                }).then(ClientCommands.literal("pause").executes {
                    pause()
                    1
                }).then(ClientCommands.literal("play").executes {
                    play()
                    1
                }).then(ClientCommands.literal("shuffle").executes {
                    shuffle()
                    1
                }.then(ClientCommands.argument("action", BoolArgumentType.bool()).executes {
                    shuffle(BoolArgumentType.getBool(it, "action"))
                    1
                })).then(
                    ClientCommands.literal("loop")
                        .then(ClientCommands.argument("action", LoopArgumentType.loopAction()).executes {
                            loop(LoopArgumentType.getLoopAction(it, "action"))
                            1
                        })
                ).then(ClientCommands.literal("forward").executes {
                    forward()
                    1
                }.then(ClientCommands.argument("amount", IntegerArgumentType.integer(0, Integer.MAX_VALUE)).executes {
                    forward(IntegerArgumentType.getInteger(it, "amount"))
                    1
                })).then(ClientCommands.literal("backward").executes {
                    backward()
                    1
                }.then(ClientCommands.argument("amount", IntegerArgumentType.integer(0, Integer.MAX_VALUE)).executes {
                    backward(IntegerArgumentType.getInteger(it, "amount"))
                    1
                }))
            )
        })
    }

    fun execute(vararg args: String) {
        try {
            ProcessBuilder()
                .command("playerctl", *args)
                .redirectErrorStream(true)
                .start()
        } catch (e: Exception) {
            LOGGER.error("Failed to play media", e)
        }
    }

    fun stop() {
        execute("stop")
    }

    fun toggle() {
        execute("play-pause")
    }

    fun next() {
        execute("next")
    }

    fun previous() {
        execute("previous")
    }

    fun pause() {
        execute("pause")
    }

    fun play() {
        execute("play")
    }

    fun shuffle(action: Boolean? = null) {
        if (action == null) {
            execute("shuffle", "toggle")
        } else {
            execute("shuffle", if (action) "on" else "off")
        }
    }

    fun loop(action: LoopAction) {
        execute("loop", action.name)
    }

    fun forward(amount: Int = 5) {
        execute("position", "$amount+")
    }

    fun backward(amount: Int = 5) {
        execute("position", "$amount-")
    }
}