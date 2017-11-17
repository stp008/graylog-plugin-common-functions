package org.graylog.plugins.common.functions.xml;

import org.graylog.plugins.common.xml.XPathMatcher;
import org.graylog.plugins.pipelineprocessor.EvaluationContext;
import org.graylog.plugins.pipelineprocessor.ast.functions.AbstractFunction;
import org.graylog.plugins.pipelineprocessor.ast.functions.FunctionArgs;
import org.graylog.plugins.pipelineprocessor.ast.functions.FunctionDescriptor;
import org.graylog.plugins.pipelineprocessor.ast.functions.ParameterDescriptor;
import org.w3c.dom.Node;

/**
 * @author Stepan Pogosyan
 * @since 10.11.2017.
 */
public class ReplaceXpathConstFunction extends AbstractFunction<Node>{

    public static final String NAME = "replace_all_xpath_const";

    private final ParameterDescriptor<Node, Node> sourceNodeDescriptor;
    private final ParameterDescriptor<String, String> expressionDescriptor;
    private final ParameterDescriptor<String, String> replacementValueDescriptor;

    //TODO replace with @Inject
    private final XPathMatcher xPathMatcher;

    public ReplaceXpathConstFunction() {
        sourceNodeDescriptor = ParameterDescriptor.type("source_node", Node.class).description("XML node where xPath evaluation will take place").build();
        expressionDescriptor = ParameterDescriptor.string("expression").description("The xPath expression").build();
        replacementValueDescriptor = ParameterDescriptor.string("replacement_value").description("Substitute value which will be applied for each match").build();
        xPathMatcher = new XPathMatcher();
    }

    @Override
    public Node evaluate(FunctionArgs args, EvaluationContext context) {
        Node sourceNode = sourceNodeDescriptor.required(args, context);
        String expression = expressionDescriptor.required(args, context);
        String replacementValue = replacementValueDescriptor.required(args, context);
        return xPathMatcher.replaceAll(expression, sourceNode, matchedValue -> replacementValue);
    }

    @Override
    public FunctionDescriptor<Node> descriptor() {
        return FunctionDescriptor.<Node>builder()
                .name(NAME)
                .description("Replaces each found string of source message that matches the given xpath expression with the given replacement value.")
                .params(sourceNodeDescriptor, expressionDescriptor, replacementValueDescriptor)
                .returnType(Node.class)
                .build();
    }
}
