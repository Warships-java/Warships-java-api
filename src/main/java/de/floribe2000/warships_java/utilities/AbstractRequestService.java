package de.floribe2000.warships_java.utilities;

import de.floribe2000.warships_java.direct.api.ApiBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractRequestService {

    protected static final AtomicBoolean initialized = new AtomicBoolean(false);

    protected static final String INSTANCE = "RequestService";

    protected static final Logger LOG = LoggerFactory.getLogger("RequestService");

    public static void initialize(String apiKey) {
        if (initialized.getAndSet(true)) {
            return;
        }
        ApiBuilder.createInstance(apiKey, INSTANCE);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                ApiBuilder.shutdown();
            } catch (IOException e) {
                LOG.error("An exception occurred while shutting down the api instance.", e);
            }
        }));
    }
}