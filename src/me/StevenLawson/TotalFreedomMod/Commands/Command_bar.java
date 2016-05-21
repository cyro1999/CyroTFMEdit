package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import me.confuser.barapi.BarAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
    When compilng please use http://dev.bukkit.org/bukkit-plugins/bar-api/
    else it won't work c:
    Credit to PieGuy
 */
@SuppressWarnings("deprecation")
@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "Change the bar message or clear it", usage = "/bar [clear | message]")
public class Command_bar extends TFM_Command {
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (!TFM_Util.DEVELOPERS.contains(sender.getName())) {
            playerMsg(TotalFreedomMod.MSG_NO_PERMS);
            return true;
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("BarAPI")) {
            playerMsg("BarAPI is not enabled on this server");
            return true;
        }

        if (args.length == 0) {
            return false;
        }

        if (args[0].equalsIgnoreCase("clear")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                BarAPI.removeBar(player);
            }
        } else {
            String message = StringUtils.join(ArrayUtils.subarray(args, 0, args.length), " ");
            BarAPI.setMessage(message.replaceAll("&", "§"), 60);

        }
        return true;
    }
}
