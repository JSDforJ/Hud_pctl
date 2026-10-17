package dk.jsdfj.hud_pctl.config

import dev.isxander.yacl3.config.v3.JsonFileCodecConfig
import dev.isxander.yacl3.config.v3.register
import dev.isxander.yacl3.dsl.YetAnotherConfigLib
import dev.isxander.yacl3.dsl.controller
import dev.isxander.yacl3.dsl.slider
import dev.isxander.yacl3.platform.YACLPlatform
import dk.jsdfj.hud_pctl.Hud_pctl
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component


@Suppress("UnstableApiUsage")
object PctlConfig : JsonFileCodecConfig<PctlConfig>(YACLPlatform.getConfigDir().resolve("hud_pctl.json")) {
    val forwardAmount by register<Int>(5, INT)
    val backwardAmount by register<Int>(5, INT)

    init {
        if (!loadFromFile()) {
            saveToFile()
        }
    }

    fun generateConfigScreen(lastScreen: Screen?) = YetAnotherConfigLib(Hud_pctl.MOD_ID) {
        save {
            saveToFile()
        }

        val generalCategory by categories.registering {
            tooltip {
                +Component.translatable(Hud_pctl.MOD_ID + ".config.general.tooltip")
            }

            val forwardOption = rootOptions.register(forwardAmount) {
                controller = slider(range = 1..60, step = 1)
            }

            val backwardOption = rootOptions.register(backwardAmount) {
                controller = slider(range = 1..60, step = 1)
            }
        }
    }.generateScreen(lastScreen)
}