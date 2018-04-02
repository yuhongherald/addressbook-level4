package seedu.carvicim.storage.session;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.carvicim.commons.exceptions.IllegalValueException;
import seedu.carvicim.model.Carvicim;
import seedu.carvicim.model.ReadOnlyCarvicim;
import seedu.carvicim.storage.XmlAdaptedEmployee;
import seedu.carvicim.storage.XmlAdaptedJob;
import seedu.carvicim.storage.XmlAdaptedTag;

/**
 * An Immutable Carvicim that is serializable to XML format
 */
@XmlRootElement(name = "carvicim")
public class XmlSerializableSessionData {

    @XmlElement
    private List<XmlAdaptedEmployee> employees;
    @XmlElement
    private List<XmlAdaptedTag> tags;
    @XmlElement
    private List<XmlAdaptedJob> jobs;

    /**
     * Creates an empty XmlSerializableCarvicim.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableSessionData() {
        employees = new ArrayList<>();
        tags = new ArrayList<>();
        jobs = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableSessionData(SessionData src) {
        this();
        employees.addAll(src.getEmployeeList().stream().map(XmlAdaptedEmployee::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
        jobs.addAll(src.getJobList().stream().map(XmlAdaptedJob::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code Carvicim} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedEmployee} or {@code XmlAdaptedTag}.
     */
    public SessionData toModelType() throws IllegalValueException {
        Carvicim carvicim = new Carvicim();
        for (XmlAdaptedTag t : tags) {
            carvicim.addTag(t.toModelType());
        }
        for (XmlAdaptedEmployee p : employees) {
            carvicim.addEmployee(p.toModelType());
        }
        for (XmlAdaptedJob j : jobs) {
            carvicim.addJob(j.toModelType());
        }
        return carvicim;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableSessionData)) {
            return false;
        }

        XmlSerializableSessionData otherAb = (XmlSerializableSessionData) other;
        return employees.equals(otherAb.employees) && tags.equals(otherAb.tags) && jobs.equals(otherAb.jobs);
    }
}
