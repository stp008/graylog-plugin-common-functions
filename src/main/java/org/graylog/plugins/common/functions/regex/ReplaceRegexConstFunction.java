package org.graylog.plugins.common.functions.regex;

import org.graylog.plugins.common.regex.RegexMatcher;
import org.graylog.plugins.pipelineprocessor.EvaluationContext;
import org.graylog.plugins.pipelineprocessor.ast.functions.AbstractFunction;
import org.graylog.plugins.pipelineprocessor.ast.functions.FunctionArgs;
import org.graylog.plugins.pipelineprocessor.ast.functions.FunctionDescriptor;
import org.graylog.plugins.pipelineprocessor.ast.functions.ParameterDescriptor;

import java.util.regex.Pattern;

//TODO write tests for evaluation

/**
 * @author Stepan Pogosyan
 * @since 06.11.2017.
 */
public class ReplaceRegexConstFunction extends AbstractFunction<String>{

    public static final String NAME = "replace_all_regex_const";

    private final ParameterDescriptor<String, Pattern> patternDescriptor;
    private final ParameterDescriptor<String, String> sourceMessageDescriptor;
    private final ParameterDescriptor<String,String> replacementValueDescriptor;

    //TODO replace with inject
    private RegexMatcher regexMatcher;

    public ReplaceRegexConstFunction() {
        patternDescriptor = ParameterDescriptor.string("pattern", Pattern.class).transform(Pattern::compile).description("The regular expression to which source message is to be matched").build();
        sourceMessageDescriptor = ParameterDescriptor.string("source_message").description("Source message where replacement is to be done").build();
        replacementValueDescriptor = ParameterDescriptor.string("replacement_value").description("Substitute value which will be applied for each match").build();
        regexMatcher = new RegexMatcher();
    }

    @Override
    public String evaluate(FunctionArgs args, EvaluationContext context) {
        Pattern pattern = patternDescriptor.required(args, context);
        String sourceMessage = sourceMessageDescriptor.required(args, context);
        String replacementValue = replacementValueDescriptor.required(args, context);

        return regexMatcher.replaceAll(pattern, sourceMessage, matchedValue -> replacementValue, 2);
    }

    @Override
    public FunctionDescriptor<String> descriptor() {
        return FunctionDescriptor.<String>builder()
                .name(NAME)
                .description("Replaces each found substring of source message that matches the given regular expression with the given replacement value.")
                .params(patternDescriptor, sourceMessageDescriptor, replacementValueDescriptor)
                .returnType(String.class)
                .build();
    }

}
