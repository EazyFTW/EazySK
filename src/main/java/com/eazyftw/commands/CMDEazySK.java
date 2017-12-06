package com.eazyftw.commands;

import com.eazyftw.EazySK;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CMDEazySK implements CommandExecutor{

    private EazySK eazySK;

    public CMDEazySK(EazySK eazySK) {
        this.eazySK = eazySK;
    }

    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        Player p = (Player)sender;

        String Prefix = eazySK.getConfig().getString("Prefix").replace("&", "§");
        String CmdPerm = eazySK.getConfig().getString("Cmd-Perm");
        String NoPerm = eazySK.getConfig().getString("No-Permission-Msg").replace("&", "§").replace("%perm%", CmdPerm);


        if (alias.equalsIgnoreCase("eazysk") || alias.equalsIgnoreCase("ezsk")) {
            if (args.length == 0) {
                if(!p.hasPermission(CmdPerm)) {
                    p.sendMessage(Prefix + " " + NoPerm);
                    return true;
                }
                p.sendMessage(Prefix + " §7Opening GUI...");
                eazySK.eazySKGUI(p);
            }
        }
        return false;
    }
}