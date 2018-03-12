package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

//@author owzhenwei
/**
 * An event request to set a new theme
 */
public class SetThemeRequestEvent extends BaseEvent {

    private final int selectedIndex;

    public SetThemeRequestEvent(Index selectedIndex) {
        this.selectedIndex = selectedIndex.getOneBased();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
