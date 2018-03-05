package seedu.servicing.model;

import javafx.collections.ObservableList;
import seedu.servicing.model.person.Person;
import seedu.servicing.model.tag.Tag;

/**
 * Unmodifiable view of an servicing book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
