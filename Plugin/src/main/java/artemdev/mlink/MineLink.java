package artemdev.mlink;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

public final class MineLink extends JavaPlugin {

    public static String api_link = "";
    public static String sec_key = "";
    public static String del_top = "";
    public static String ver_msg = "";
    public static String ver_msg2 = "";
    public static String lnk_hider = "";
    public static String del_btm = "";
    public static boolean debug = false;
    public BukkitCommand cmdhandler;

    @Override
    public void onEnable() {
        this.getLogger().info("Loading Config File...");
        FileConfiguration config = this.getConfig();

        File f = new File(this.getDataFolder() + "/config.yml");
        if(!f.exists()){
            this.getLogger().warning("Config file is missing!");
            saveDefaultConfig();
        }

        if(config.getBoolean("unconfigured")){
            this.getLogger().warning("You didn't complete the plugin setup! Please, check if you've followed all the instructions!");
            debug = true;
        }

        api_link = config.getString("auth-api");
        sec_key = config.getString("secret-key");
        del_top = config.getString("delimiter-top");
        ver_msg = config.getString("verification-message");
        ver_msg2 = config.getString("verification-message2");
        lnk_hider = config.getString("link-hider");
        del_btm = config.getString("delimiter-bottom");

        this.getLogger().info("Loading AuthManager... ");
        registerCommands();
    }

    @Override
    public void onDisable() {

    }

    public void registerCommands() {
        if (cmdhandler != null) cmdhandler.unregister(getCommandMap());

        registerRecipeCommand();
    }

    private void registerRecipeCommand() {
        ArrayList<String> aliases = new ArrayList<>();
        aliases.add("verify");

        String usage = "/<command>";
        String description = "Shows the GUI with all custom recipes.";
        String permission = "";

        cmdhandler = new AuthManager(permission, aliases.get(0), description, usage, aliases);
    }

    public CommandMap getCommandMap() {
        CommandMap commandMap = null;

        try {
            Field f = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);

            commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
        }

        return commandMap;
    }
}
