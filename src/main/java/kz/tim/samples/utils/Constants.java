package kz.tim.samples.utils;

/**
 * Constant values for response code and messages.
 *
 * @author Timur Tibeyev.
 */
public class Constants {
    public static final Integer WS_SUCCESS_CODE  = 100;
    public static final String WS_SUCCESS_MESSAGE  = "Request was successfully processed";

    public static final Integer WS_UNKNOWN_ACTION_ERROR_CODE  = 101;
    public static final String WS_UNKNOWN_ACTION_ERROR_MESSAGE  = "Unsupported action";

    public static final Integer WS_DIVISION_BY_ZERO_ERROR_CODE  = 102;
    public static final String WS_DIVISION_BY_ZERO_ERROR_MESSAGE  = "Division by zero";

    public static final Integer WS_INTERNAL_ERROR_CODE  = 103;
    public static final String WS_INTERNAL_ERROR_MESSAGE  = "Internal server error";
}
