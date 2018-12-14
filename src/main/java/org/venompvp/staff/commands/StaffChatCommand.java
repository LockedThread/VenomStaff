package org.venompvp.staff.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.venompvp.staff.Staff;
import org.venompvp.staff.enums.Messages;
import org.venompvp.staff.objs.StaffPlayer;
import org.venompvp.venom.commands.Command;
import org.venompvp.venom.commands.ParentCommand;
import org.venompvp.venom.commands.arguments.Argument;
import org.venompvp.venom.module.Module;

import java.util.Collections;
import java.util.List;

public class StaffChatCommand extends Command implements ParentCommand {

    public StaffChatCommand(Module module) {
        super(module, "staffchat", "allows people to toggle staff chat", Collections.emptyList(), "venom.staff", true, "sc");
    }

    @Override
    public void execute(CommandSender sender, List<Argument> args, String label) {
        Player player = (Player) sender;
        StaffPlayer staffPlayer = Staff.getInstance().getStaffPlayer(player);
        if (staffPlayer != null) {
            staffPlayer.setStaffChat(!staffPlayer.isStaffChat());
            sender.sendMessage(staffPlayer.isStaffChat() ? Messages.STAFFCHAT_ENABLE.toString() : Messages.STAFFCHAT_DISABLE.toString());
        }
    }

    @Override
    public String getUsage(String label) {
        return "/" + label;
    }

    @Override
    public void setupSubCommands() {

    }
}
