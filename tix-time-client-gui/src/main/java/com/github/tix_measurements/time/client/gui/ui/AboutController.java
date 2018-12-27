package com.github.tix_measurements.time.client.gui.ui;

import com.github.tix_measurements.time.client.console.reporting.utils.ConfigurationReader;
import javafx.fxml.FXML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.net.URI;

public class AboutController {
    private static ConfigurationReader configurationReader = ConfigurationReader.getInstance();
    private static final Logger logger = LogManager.getLogger();

    @FXML
    private void openWebsite() {
        try {
            Desktop.getDesktop().browse(new URI(configurationReader.getWebApiUrl()));
        } catch (Exception e) {
            logger.error("Error when opening help URL");
        }
    }
}
