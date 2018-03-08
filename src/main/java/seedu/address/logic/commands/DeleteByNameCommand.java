package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Finds and deletes all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class DeleteByNameCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete-by-name";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    public static final String MESSAGE_DELETE_PERSONS_SUCCESS = "Deleted Persons: ";

    private final NameContainsKeywordsPredicate predicate;

    private List<Person> personToDeleteList;

    ArrayList<String> deletedNames;

    StringBuilder deleteMessage;


    public DeleteByNameCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
        deletedNames = new ArrayList<String>();
        deleteMessage = new StringBuilder(MESSAGE_DELETE_PERSONS_SUCCESS);
    }

    //@@author kaisertanqr
    //*** Needs more abstraction
    //*** Still buggy, unable to delete last 2 instances of the person
    @Override
    public CommandResult executeUndoableCommand() {

        for(int i = 0; i < personToDeleteList.size(); i++) {
            Person personToDelete = personToDeleteList.get(0);
            requireNonNull(personToDelete);
            deletedNames.add(personToDelete.getName().toString());
            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }

            deleteMessage.append(personToDelete.getName());
        }

        return new CommandResult(deleteMessage.toString());
    }


    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        model.updateFilteredPersonList(predicate);
        personToDeleteList = model.getFilteredPersonList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteByNameCommand // instanceof handles nulls
                && this.predicate.equals(((DeleteByNameCommand) other).predicate)); // state check
    }
}
