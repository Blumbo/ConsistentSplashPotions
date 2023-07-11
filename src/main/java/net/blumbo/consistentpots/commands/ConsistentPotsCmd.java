package net.blumbo.consistentpots.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.blumbo.consistentpots.ConsistentPots;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ConsistentPotsCmd {

    private static final String cmd = "consistentpots";
    private static final String setArg = "accuracyPercentage";

    private static final String info = "§b/" + cmd + " " + setArg + " §7to see current extra splash potion accuracy \n" +
            "§b/" + cmd + " " + setArg + " <value> §7to set extra splash potion accuracy";
    private static final String valueInfo = "§b0% §7- no extra accuracy (vanilla) \n" +
            "§b100% §7- always get full potential from splash potions";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                                CommandRegistryAccess registryAccess,
                                CommandManager.RegistrationEnvironment environment) {

    dispatcher.register(CommandManager.literal(cmd).requires(source -> source.hasPermissionLevel(2))
        .executes(ConsistentPotsCmd::info)
            .then(CommandManager.literal(setArg).executes(ConsistentPotsCmd::get)
                .then(CommandManager.argument("value", IntegerArgumentType.integer(0, 100))
                    .executes(ConsistentPotsCmd::set))
            )
        );
    }

    private static int info(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        source.sendMessage(Text.of("\n" + info + "\n"));
        return 0;
    }

    private static int get(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        source.sendMessage(Text.of("\n§7Extra splash potion accuracy is currently §b" +
                ConsistentPots.accuracyPercentage + "%\n" + valueInfo + "\n"));
        return 0;
    }

    private static int set(CommandContext<ServerCommandSource> context) {
        int value = context.getArgument("value", Integer.class);
        ConsistentPots.accuracyPercentage = value;
        ServerCommandSource source = context.getSource();
        source.sendMessage(Text.of("§7Extra splash potion accuracy set to §b" + value + "%"));
        return 0;
    }

}
