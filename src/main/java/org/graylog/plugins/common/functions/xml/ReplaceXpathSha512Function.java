package org.graylog.plugins.common.functions.xml;

import org.apache.commons.codec.digest.DigestUtils;
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
public class ReplaceXpathSha512Function extends AbstractFunction<Node> {
    public static final String NAME = "replace_all_xpath_sha512";

    private final ParameterDescriptor<Node, Node> sourceNodeDescriptor;
    private final ParameterDescriptor<String, String> expressionDescriptor;

    //TODO replace with @Inject
    private XPathMatcher xPathMatcher;

    public ReplaceXpathSha512Function() {
        sourceNodeDescriptor = ParameterDescriptor.type("source_node", Node.class).description("XML node where xPath evaluation will take place").build();
        expressionDescriptor = ParameterDescriptor.string("expression").description("The xPath expression").build();
        xPathMatcher = new XPathMatcher();
    }

    @Override
    public Node evaluate(FunctionArgs args, EvaluationContext context) {
        Node sourceNode = sourceNodeDescriptor.required(args, context);
        String expression = expressionDescriptor.required(args, context);

        return xPathMatcher.replaceAll(expression, sourceNode, DigestUtils::sha512Hex);
    }

    @Override
    public FunctionDescriptor<Node> descriptor() {
        return FunctionDescriptor.<Node>builder()
                .name(NAME)
                .description("Replaces each found string of source message that matches the given xpath expression with sha512 hash of matched value.")
                .params(sourceNodeDescriptor, expressionDescriptor)
                .returnType(Node.class)
                .build();
    }
}
