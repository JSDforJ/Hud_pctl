package dk.jsdfj.hud_pctl.commands

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import java.util.concurrent.CompletableFuture

class LoopArgumentType : ArgumentType<LoopAction> {
    companion object {
        private val EXAMPLES: MutableCollection<String> = mutableListOf("none", "playlist", "track")

        fun loopAction(): LoopArgumentType {
            return LoopArgumentType()
        }

        fun getLoopAction(context: CommandContext<*>, name: String?): LoopAction {
            return context.getArgument(name, LoopAction::class.java)
        }
    }

    override fun parse(reader: StringReader?): LoopAction {
        return LoopAction.valueOf(reader?.readString() ?: "toggle")
    }

    override fun <S> listSuggestions(
        context: CommandContext<S?>?,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions?>? {
        LoopAction.entries.forEach {
            builder.suggest(it.name)
        }
        return builder.buildFuture()
    }

    override fun getExamples(): MutableCollection<String> {
        return EXAMPLES
    }
}

enum class LoopAction {
    NONE,
    PLAYLIST,
    TRACK,
}