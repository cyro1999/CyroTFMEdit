package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "DevChat - Talk privately with other developers.  Using <command> itself will toggle DevChat on and off for all messages", usage = "/<command> [message...]", aliases = "devchat")
public class Command_devchat extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (!TFM_Util.DEVELOPERS.contains(sender.getName()))
        {
            sender.sendMessage(ChatColor.RED + TotalFreedomMod.MSG_NO_PERMS);
            return true;
        }
        if (args.length == 1)
        {
            for (final Player player : Bukkit.getOnlinePlayers())
            {
                if (!TFM_Util.DEVELOPERS.contains(sender.getName()))
                {
                    player.sendMessage(ChatColor.DARK_PURPLE + "[DeveloperChat]" + ChatColor.DARK_AQUA + "[" + sender.getName() + "]" + StringUtils.join(args, " "));
                }
            }
        }

        return true;
    }
}
