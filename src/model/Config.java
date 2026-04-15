package model;

import java.io.*;
public class Config {

    /**
     * Sets the API status to determine if user is in offline or online mode
     * @param state Used to determine if the API is used or not
     * @return returns the current status of the API
     * True for online, False for offline
     */
    public boolean setAPIActive(boolean state) {
        //Online/offline mode toggled in settings?
        apiActive = state;
        return state;
    }

    /**
     * Gives the current state of the API
     * @return Returns True if active, False if inactive
     */
    public boolean isApiActive() {
        return apiActive;
    }

    /**
     * Sets the state of dark mode
     * @param state The state of dark mode set by the user
     * @return Returns the current state of dark mode
     */
    public boolean setDarkMode(boolean state) {
        isDark = state;
        return state;
    }

    public boolean isDarkMode() {
        return isDark;
    }

    private boolean apiActive;
    private boolean isDark;
}