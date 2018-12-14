package org.venompvp.staff.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.venompvp.staff.Staff;
import org.venompvp.staff.objs.StaffPlayer;
import org.venompvp.venom.commands.Command;
import org.venompvp.venom.commands.ParentCommand;
import org.venompvp.venom.commands.arguments.Argument;
import org.venompvp.venom.module.Module;

import java.util.Collections;
import java.util.List;

public class StaffModeCommand extends Command implements ParentCommand {

    public StaffModeCommand(Module module) {
        super(module, "staffmode", "toggleable staff mode", Collections.emptyList(), "venom.staffmode", true);
    }

    @Override
    public void execute(CommandSender sender, List<Argument> args, String label) {
        StaffPlayer staffPlayer = Staff.getInstance().getStaffPlayer((Player) sender);
        if (staffPlayer != null) {
            if (staffPlayer.isStaffMode()) {
                staffPlayer.removeStaffMode();
            } else {
                staffPlayer.addStaffMode();
            }
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
