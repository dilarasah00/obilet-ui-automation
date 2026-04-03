package utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHelper {
    private static final Logger logger = LogManager.getLogger(LogHelper.class);

    public static void info(String message) {
        logger.info(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void startTestCase(String testCaseName) {
        info("**********************************************************");
        info("--- TEST STARTS: " + testCaseName + " ---");
        info("**********************************************************");
    }

    public static void endTestCase(String testCaseName) {
        info("--- TEST COMPLETED: " + testCaseName + " ---");
        info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    }
}