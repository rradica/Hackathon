package ch.opendata.hack.energy.sources;

import ch.opendata.hack.energy.Datatype;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public final class ParseUtils {

    private final static Pattern PATTERN_STRING = Pattern.compile("(?=[a-zA-ZüÜöÖäÄ])(.*)");

    private final static Pattern PATTERN_DATE = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    private final static Pattern PATTERN_DOUBLE = Pattern.compile("\\d*.\\d*");

    private final static Pattern PATTERN_INT = Pattern.compile("\\d*");

    private ParseUtils() {

    }

    public static Map<String, Object> parseRawDataObject(RawDataObject rawDataObject, Map<String, Datatype> datatypeMap) {
        final Map<String, Object> ret = new HashMap<>();

        for(Entry<String, String> entry : rawDataObject.getData().entrySet()) {

            final String key = entry.getKey();
            final String value = entry.getValue();

            ret.put(key, convertValueToCorrectDataType(datatypeMap.get(key),value));
        }

        return ret;

    }

    public static Map<String, Datatype> parseDataTypes(RawDataObject rawDataObject) {
        final Map<String, Datatype> ret = new HashMap<>();

        for(Entry<String, String> entry : rawDataObject.getData().entrySet()) {

            final String key = entry.getKey();
            final String value = entry.getValue();

            ret.put(key, detectAttributeType(value));
        }

        return ret;

    }

    public static Datatype detectAttributeType(String value) {

        if(value == null || value.isEmpty()) {
            return Datatype.STRING;
        }

        if(PATTERN_STRING.matcher(value).matches()) {
            return Datatype.STRING;
        } else if(PATTERN_DATE.matcher(value).matches()) {
            return Datatype.DATE;
        } else if(PATTERN_DOUBLE.matcher(value).matches()) {
            return Datatype.DOUBLE;
        } else if(PATTERN_INT.matcher(value).matches()) {
            return Datatype.INTEGER;
        }

        return Datatype.STRING;

    }
    private static Object convertValueToCorrectDataType(Datatype datatype, String value) {

        if(value == null || value.isEmpty()) {
            return "";
        }

        switch (datatype) {
            case STRING: {
                return value;
            }
            case DATE: {
                return LocalDate.parse(value);
            }
            case DOUBLE: {
                return Double.parseDouble(value);
            }
            case INTEGER: {
                return Integer.parseInt(value);
            }
        }

        return value;
    }
}
