package me.StevenLawson.TotalFreedomMod.Commands;

/*
* Implementing LibDisguises Instead - hypertechHD
*/

import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "Undisguises all players", usage = "/<command>")
public class Command_uall extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if(Bukkit.getPluginManager().getPlugin("LibsDisguises") == null)
        {
            sender.sendMessage(ChatColor.RED + "LibsDigsuises could not be found.");
            return true;
        }
        for(Player player : Bukkit.getOnlinePlayers())
        {
                DisguiseAPI.undisguiseToAll(player);
        }
        return true;
    }
}
