package me.StevenLawson.TotalFreedomMod.Commands;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

@CommandPermissions(level = AdminLevel.SENIOR, source = SourceType.BOTH)
@CommandParameters(description = "Manage plugins", usage = "/<command> <<enable | disable | reload> <pluginname>> | list>", aliases = "plc")
public class Command_plugincontrol extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0 || args.length > 2)
        {
            return false;
        }

        final PluginManager pm = Bukkit.getPluginManager();

        if (args.length == 1)
        {
            if (args[0].equalsIgnoreCase("list"))
            {
                for (Plugin serverPlugin : pm.getPlugins())
                {
                    final String version = serverPlugin.getDescription().getVersion();
                    sender.sendMessage(ChatColor.GRAY + "- " + (serverPlugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED) + serverPlugin.getName()
                            + ChatColor.GOLD + (version != null && !version.isEmpty() ? " v" + version : "") + " by "
                            + StringUtils.join(serverPlugin.getDescription().getAuthors(), ", "));
                }

                return true;
            }

            return false;
        }

        if ("enable".equals(args[0]))
        {
            final Plugin target = getPlugin(args[1]);
            if (target == null)
            {
                sender.sendMessage(ChatColor.RED + "Plugin not found!");
                return true;
            }

            if (target.isEnabled())
            {
                sender.sendMessage(ChatColor.RED + "Plugin is already enabled.");
                return true;
            }

            pm.enablePlugin(target);

            if (!pm.isPluginEnabled(target))
            {
                sender.sendMessage(ChatColor.RED + "Error enabling plugin " + target.getName());
                return true;
            }

            sender.sendMessage(ChatColor.GREEN + target.getName() + " is now enabled.");
            return true;
        }

        if ("disable".equals(args[0]))
        {
            final Plugin target = getPlugin(args[1]);
            if (target == null)
            {
                sender.sendMessage(ChatColor.RED + "Plugin not found!");
                return true;
            }

            if (!target.isEnabled())
            {
                sender.sendMessage(ChatColor.RED + "Plugin is already disabled.");
                return true;
            }

            if (target.getName().equals(plugin.getName()))
            {
                sender.sendMessage(ChatColor.RED + "You cannot disable " + plugin.getName());
                return true;
            }

            pm.disablePlugin(target);

            if (pm.isPluginEnabled(target))
            {
                sender.sendMessage(ChatColor.RED + "Error disabling plugin " + target.getName());
                return true;
            }

            sender.sendMessage(ChatColor.GREEN + target.getName() + " is now disabled.");
            return true;
        }

        if ("reload".equals(args[0]))
        {
            final Plugin target = getPlugin(args[1]);
            if (target == null)
            {
                sender.sendMessage(ChatColor.RED + "Plugin not found!");
                return true;
            }

            if (target.getName().equals(plugin.getName()))
            {
                sender.sendMessage(ChatColor.RED + "Use /tfm reload to reload instead.");
                return true;
            }

            pm.disablePlugin(target);
            pm.enablePlugin(target);
            sender.sendMessage(ChatColor.GREEN + target.getName() + " reloaded.");
            return true;
        }

        return false;
    }

    public Plugin getPlugin(String name)
    {
        for (Plugin serverPlugin : Bukkit.getPluginManager().getPlugins())
        {
            if (serverPlugin.getName().equalsIgnoreCase(name))
            {
                return serverPlugin;
            }
        }

        if (name.length() >= 3)
        {
            for (Plugin serverPlugin : Bukkit.getPluginManager().getPlugins())
            {
                if (serverPlugin.getName().toLowerCase().contains(name.toLowerCase()))
                {
                    return serverPlugin;
                }
            }
        }

        return null;
    }
}
