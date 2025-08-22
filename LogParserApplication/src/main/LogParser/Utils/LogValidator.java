package Utils;

import java.util.List;
import java.util.Map;

public class LogValidator {
    public static boolean validate(Map<String, String> attributes, List<String> requiredFields, String logLine) {
        for (String field : requiredFields) {
            if (!attributes.containsKey(field)) {
                return false;
            }
        }
        return true;
    }
}
