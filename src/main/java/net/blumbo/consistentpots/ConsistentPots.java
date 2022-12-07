package net.blumbo.consistentpots;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.blumbo.consistentpots.commands.ConsistentPotsCmd;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConsistentPots implements ModInitializer {

    public static int accuracyPercentage = 50;
    public static String accuracyPercentageKey = "accuracyPercentage";

    private static final String dir = FabricLoader.getInstance().getConfigDir().toString() + "/consistentpots";
    public static String fileName = "consistentpots.json";

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(ConsistentPotsCmd::register);
        load();
    }

    private static void load() {
        try {
            String jsonString = Files.readString(Paths.get(dir + "/" + fileName));
            JsonElement element = JsonParser.parseString(jsonString);
            accuracyPercentage = ((JsonObject)element).get(accuracyPercentageKey).getAsInt();
        } catch (Exception ignored) {}
    }

    public static void save() {
        try {
            JsonObject object = new JsonObject();
            object.addProperty(accuracyPercentageKey, accuracyPercentage);

            Files.createDirectories(Paths.get(dir));
            BufferedWriter writer = new BufferedWriter(new FileWriter(dir + "/" + fileName));

            writer.write(object.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
