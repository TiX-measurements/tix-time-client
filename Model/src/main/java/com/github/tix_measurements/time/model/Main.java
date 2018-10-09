package com.github.tix_measurements.time.model;

import com.github.tix_measurements.time.model.reporting.Reporter;
import org.apache.commons.lang3.SerializationUtils;

import java.security.KeyPair;
import java.util.prefs.Preferences;

public class Main {

    public static Reporter reporter;
    public static Preferences preferences = Preferences.userRoot().node("/com/tix/model");
    private static String username = null;
    private static String password = null;
    private static String installation = null;
    private static int port = -1;
    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                username = args[0].replace("\"", "\\\"");
            } catch (RuntimeException e) {
                System.err.println("Username missing.");
                System.exit(1);
            }
            try {
                password = args[1].replace("\"", "\\\"");
            } catch (RuntimeException e) {
                System.err.println("Password missing.");
                System.exit(1);
            }
            try {
                installation = args[2].replace("\"", "\\\"");
            } catch (RuntimeException e) {
                System.err.println("Installation name missing.");
                System.exit(1);
            }
            try {
                port = Integer.parseInt(args[3]);
            } catch (RuntimeException e) {
                System.err.println("Port number missing or cannot be parsed.");
                System.exit(1);
            }
            preferences = Preferences.userRoot().node("/com/tix/model" + installation);
            Setup.cliLogin(username, password);
            Setup.cliInstall(installation, port);
            startReporting();
        }
    }

    /**
     * Creates a new Reporter instance and activates it.
     */
    public static void startReporting() {
        final long USER_ID = Main.preferences.getLong("userID", 0L);
        final long INSTALLATION_ID = Main.preferences.getLong("installationID", 0L);
        final byte[] keyPairBytes = Main.preferences.getByteArray("keyPair", null);
        final KeyPair KEY_PAIR = SerializationUtils.deserialize(keyPairBytes);
        final int CLIENT_PORT = Main.preferences.getInt("clientPort", -1);
        final boolean SAVE_LOGS_LOCALLY = Main.preferences.getBoolean("saveLogsLocally", false);

        reporter = new Reporter(USER_ID, INSTALLATION_ID, KEY_PAIR, CLIENT_PORT, SAVE_LOGS_LOCALLY);
        reporter.run();
    }
}