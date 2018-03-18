package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.job.Job;

//@author yuhongherald
/**
 * An UI component that displays information of a {@code Employee}.
 */
public class JobCard extends UiPart<Region> {

    private static final String FXML = "JobListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Job job;

    @FXML
    private HBox cardPane;
    @FXML
    private Label customer;
    @FXML
    private Label id;
    @FXML
    private Label vehicleNumber;
    @FXML
    private Label startDate;
    @FXML
    private FlowPane employees;

    public JobCard(Job job) {
        super(FXML);
        this.job = job;
        id.setText(job.getJobNumber().toString() + ". ");
        customer.setText(job.getCustomer().getName().toString());
        job.getAssignedEmployees().forEach(employee -> employees.getChildren().add(
                new Label(employee.getName().toString())));
        vehicleNumber.setText(job.getVehicleNumber().toString());
        startDate.setText(job.getDate().toString());
        // remarks are not supported in this version, might want to expand into new window due to space constraints
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JobCard)) {
            return false;
        }

        // state check
        JobCard card = (JobCard) other;
        return id.getText().equals(card.id.getText())
                && job.equals(card.job);
    }
}
