package com.easybank.message.ordermanagerapi.util;


import com.easybank.message.ordermanagerapi.entity.Order;
import com.easybank.message.ordermanagerapi.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil {

    private static final Logger logger = LogManager.getLogger(LogUtil.class);

    public static void logOrderCompleted(Order order) {
        logger.info("Order #" + order.getId() + " completed. User: " + order.getUser().getEmail());
    }

    public static void logEmailSent(User user, Order order) {
        logger.info("Email sent to " + user.getEmail() + " for Order #" + order.getId());
    }

    public static void logError(String message) {
        logger.error(message);
    }

}
