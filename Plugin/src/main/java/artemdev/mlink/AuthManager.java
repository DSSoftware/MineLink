package artemdev.mlink;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.codec.binary.Hex;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

public class AuthManager extends BukkitCommand {

    public AuthManager(String permission, String name, String description, String usage, List<String> aliases) {
        super(name, description, usage, aliases);
        this.setName(name);
        this.setDescription(description);
        this.setUsage(usage);
        this.setAliases(aliases);
        this.setPermission(permission);
        try {
            Field f;
            f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            CommandMap commandMap = (CommandMap) f.get(Bukkit.getServer());
            commandMap.register(name, this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(CommandSender commandSender, String name, String[] args) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            String secret_key = MineLink.sec_key;
            String player_nick = sender.getName();
            String timestamp = String.valueOf(System.currentTimeMillis());

            String player_key = player_nick + "_" + secret_key + "_" + timestamp;

            try {
                final MessageDigest digest = MessageDigest.getInstance("SHA-256");
                final byte[] hashbytes = digest.digest(player_key.getBytes(StandardCharsets.UTF_8));
                String sha3Hex = Hex.encodeHexString(hashbytes);

                String verification_link;
                if(MineLink.debug){
                    verification_link = "https://example.com/api.php?player_nick="+ player_nick;
                }
                else {
                    verification_link = MineLink.api_link + "?method=verify&player_nick=" + player_nick + "&code=" + sha3Hex + "&timestamp=" + timestamp;
                }

                TextComponent Link = new TextComponent(ChatColor.translateAlternateColorCodes('&', MineLink.lnk_hider));
                Link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, verification_link));

                TextComponent first_string = new TextComponent(ChatColor.translateAlternateColorCodes('&', MineLink.del_top));
                TextComponent second_string = new TextComponent(ChatColor.translateAlternateColorCodes('&', MineLink.ver_msg));
                second_string.addExtra(Link);
                second_string.addExtra(new TextComponent(ChatColor.translateAlternateColorCodes('&', MineLink.ver_msg2)));
                TextComponent third_string = new TextComponent(ChatColor.translateAlternateColorCodes('&', MineLink.del_btm));

                sender.spigot().sendMessage(new TextComponent(""));
                sender.spigot().sendMessage(first_string);
                sender.spigot().sendMessage(second_string);
                sender.spigot().sendMessage(third_string);
                sender.spigot().sendMessage(new TextComponent(""));
            } catch(Exception e){
                e.printStackTrace();
            }
            return true;
        }

        return true;
    }
}