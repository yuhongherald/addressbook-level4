package seedu.address.model;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandWords;
import seedu.address.model.job.Job;
import seedu.address.model.job.exceptions.JobNotFoundException;
import seedu.address.model.person.Employee;
import seedu.address.model.person.exceptions.DuplicateEmployeeException;
import seedu.address.model.person.exceptions.EmployeeNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Employee> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Job> PREDICATE_SHOW_ALL_JOBS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData, CommandWords newCommandWords);

    /** Returns the command words set by the user. */
    CommandWords getCommandWords();

    /** Returns the command words set by the user. */
    String appendCommandKeyToMessage(String message);


    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Initializes the job number based on the list of jobs */
    void initJobNumber();

    /** Adds the given job */
    void addJob(Job job);

    /** Closes the given job */
    void closeJob(Job target) throws JobNotFoundException;

    /** Deletes the given employee. */
    void deletePerson(Employee target) throws EmployeeNotFoundException;

    /** Adds the given employee */
    void addPerson(Employee employee) throws DuplicateEmployeeException;

    /** Adds a list of (@code Job) into (@code AddressBook), and automatically imports new employees */
    void addJobs(List<Job> job);

    /** Adds employees in list into (@code AddressBook) if it is not present */
    void addMissingEmployees(Set<Employee> employees);

    /** Sort all persons' name in list alphabetically. */
    void sortPersonList();

    /**
     * Replaces the given employee {@code target} with {@code editedEmployee}.
     *
     * @throws DuplicateEmployeeException if updating the employee's details causes the employee to be equivalent to
     *      another existing employee in the list.
     * @throws EmployeeNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(Employee target, Employee editedEmployee)
            throws DuplicateEmployeeException, EmployeeNotFoundException;

    /** Returns an unmodifiable view of the filtered employee list */
    ObservableList<Employee> getFilteredPersonList();

    /**
     * Updates the filter of the filtered employee list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Employee> predicate);

    /** Returns an unmodifiable view of the filtered job list */
    ObservableList<Job> getFilteredJobList();

    /**
     * Updates the filter of the filtered job list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredJobList(Predicate<Job> predicate);
}
