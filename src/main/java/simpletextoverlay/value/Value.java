package simpletextoverlay.value;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;

import simpletextoverlay.client.gui.overlay.Info;
import simpletextoverlay.client.gui.overlay.OverlayManager;
import simpletextoverlay.SimpleTextOverlay;
import simpletextoverlay.tag.registry.TagRegistry;
import simpletextoverlay.value.registry.ValueRegistry;

public abstract class Value {
    private static final Pattern PATTERN = Pattern.compile("\\{([a-z0-9]+)\\}", Pattern.CASE_INSENSITIVE);
    private static final Matcher MATCHER = PATTERN.matcher("");
    protected static List<Info> info;

    private String name = null;
    private String[] aliases = new String[0];
    protected String value = "";
    public final List<Value> values = new ArrayList<>();

    public Value setName(final String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Value setAliases(final String... aliases) {
        this.aliases = aliases;
        return this;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public String getType() {
        return ValueRegistry.INSTANCE.forClass(getClass());
    }

    public Value setRawValue(final String value, final boolean isText) {
        this.value = "";
        return this;
    }

    public String getRawValue(final boolean isText) {
        return this.value;
    }

    protected String replaceVariables(String str) {
        MATCHER.reset(str);

        while (MATCHER.find()) {
            str = str.replace(MATCHER.group(0), getVariableValue(MATCHER.group(1)));
        }

        return str;
    }

    public abstract boolean isSimple();

    public abstract boolean isValidSize();

    public abstract String getValue();

    public boolean isValid() {
        return true;
    }

    protected String getValue(final int index) {
        return this.values.get(index).getReplacedValue();
    }

    protected int getIntValue() {
        return Integer.parseInt(getReplacedValue());
    }

    protected int getIntValue(final int index) {
        return Integer.parseInt(getValue(index));
    }

    protected double getDoubleValue() {
        return Double.parseDouble(getReplacedValue());
    }

    protected double getDoubleValue(final int index) {
        return Double.parseDouble(getValue(index));
    }

    protected boolean getBooleanValue() {
        return Boolean.parseBoolean(getReplacedValue());
    }

    protected boolean isTruthy(final int index) {
        String value = getValue(index);

        if (value.equals("false")
                || value.equals("")
                || value.equals("0")
                || value.equals("0.0")
                || value.equals("0.00")) {
            return false;
        }

        return true;
    }

    protected boolean getBooleanValue(final int index) {
        return Boolean.parseBoolean(getValue(index));
    }

    protected String getVariableValue(final String var) {
        Set<String> tagBlacklist = OverlayManager.INSTANCE.getTagBlacklist();

        if (tagBlacklist.contains(var)) {
            return "disabled";
        }

        try {
            final String value = TagRegistry.INSTANCE.getValue(var);
            if (value != null) {
                return value;
            }
        } catch (final Exception e) {
            SimpleTextOverlay.logger.debug("Failed to get value!", e);
            return "null";
        }

        return "{" + var + "}";
    }

    public String getReplacedValue() {
        return replaceVariables(getValue());
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "[%s] '%s'", getClass(), this.value);
    }

    public static void setInfo(final List<Info> info) {
        Value.info = info;
    }

    public static Value fromString(final String str) {
        return ValueRegistry.INSTANCE.forName(str);
    }

    public static String toString(final Value value) {
        return ValueRegistry.INSTANCE.forClass(value.getClass());
    }
}
