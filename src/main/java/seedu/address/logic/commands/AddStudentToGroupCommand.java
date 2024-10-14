package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;

/**
 * Adds a student to a group.
 */
public class AddStudentToGroupCommand extends Command {

    public static final String COMMAND_WORD = "add_student_grp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to a group. "
            + "Parameters: "
            + PREFIX_STUDENT_NUMBER + "STUDENT_NUMBER "
            + PREFIX_GROUP_NAME + "GROUP_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STUDENT_NUMBER + "A02345678J "
            + PREFIX_GROUP_NAME + "Group 1";

    public static final String MESSAGE_SUCCESS = "Added student: (%1$s) from (%2$s)";

    public static final String MESSAGE_DUPLICATE_STUDENT_IN_GROUP = "This student is already in the group";

    private final StudentNumber toAdd;

    private final GroupName toAddInto;

    /**
     * Creates an AddStudentToGroupCommand to add the specified {@code Student} to the specified {@code Group}
     */
    public AddStudentToGroupCommand(StudentNumber studentNumber, GroupName groupName) {
        requireNonNull(studentNumber);
        requireNonNull(groupName);
        toAdd = studentNumber;
        toAddInto = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Student student = model.getPersonByNumber(toAdd);
        Group group = model.getGroupByName(toAddInto);

        if (model.hasPersonInGroup(student, group)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT_IN_GROUP);
        }

        model.addPersonToGroup(student, group);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(student), Messages.format(group)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddStudentToGroupCommand)) {
            return false;
        }

        AddStudentToGroupCommand otherAddStudentToGroupCommand = (AddStudentToGroupCommand) other;
        return toAdd.equals(otherAddStudentToGroupCommand.toAdd)
                && toAddInto.equals(otherAddStudentToGroupCommand.toAddInto);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .add("toAddInto", toAddInto)
                .toString();
    }
}
