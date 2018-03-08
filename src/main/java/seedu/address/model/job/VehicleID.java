package seedu.address.model.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@author owzhenwei
/**
 * Represents a Vehicle ID in the Job
 */
public class VehicleID {
    public static final String MESSAGE_VEHICLE_ID_CONSTRAINTS =
            "Vehicle ID should only contain alphanumeric characters and should not be blank";

    public static final String VEHICLE_ID_VALIDATION_REGEX = "[\\p{Alnum}]*";

    public final String vehicleID;

    public VehicleID(String vehicleID) {
        requireNonNull(vehicleID);
        checkArgument(isValidVehicleID(vehicleID), MESSAGE_VEHICLE_ID_CONSTRAINTS);
        this.vehicleID = vehicleID;
    }

    /**
     * Returns true if a given string is a valid vehicle ID
     */
    public static boolean isValidVehicleID(String test) {
        return test.matches(VEHICLE_ID_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return vehicleID;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VehicleID // instanceof handles nulls
                && this.vehicleID.equals(((VehicleID) other).vehicleID)); // state check
    }

    @Override
    public int hashCode() {
       return vehicleID.hashCode();
    }


}
