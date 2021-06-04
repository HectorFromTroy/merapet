package com.nazipov.merapet.logging;

import java.util.Arrays;

public class LoggingStrings {

    private LoggingStrings() {}
    
    public static String errorString(Throwable e) {
        return String.format("Error: %s%nStack trace: %s", e, Arrays.toString(e.getStackTrace()));
    }
}
