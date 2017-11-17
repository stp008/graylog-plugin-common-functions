package org.graylog.plugins.common.regex;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO write tests for matcher

/**
 * @author Stepan Pogosyan
 * @since 08.11.2017.
 */
public class RegexMatcher {

    public String replaceAll(Pattern regex, String source, Function<String, String> function, int replacementGroup) {
        Matcher matcher = regex.matcher(source);
        StringBuffer valueBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(valueBuffer, "");
            for (int i = 1; i < replacementGroup; i++) {
                valueBuffer.append(matcher.group(i));
            }
            valueBuffer.append(function.apply(matcher.group(replacementGroup)));
            for (int i = replacementGroup + 1; i <= matcher.groupCount(); i++) {
                valueBuffer.append(matcher.group(i));
            }
        }

        matcher.appendTail(valueBuffer);
        return valueBuffer.toString();
    }

}
