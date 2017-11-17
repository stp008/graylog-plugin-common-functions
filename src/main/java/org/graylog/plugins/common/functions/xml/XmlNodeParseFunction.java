package org.graylog.plugins.common.functions.xml;

import org.graylog.plugins.common.xml.XmlNodeParser;
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
public class XmlNodeParseFunction extends AbstractFunction<Node> {

    public static final String NAME = "parse_xml_to_node";

    private final ParameterDescriptor<String, String> sourceStringDescriptor;

    //TODO replace with @Inject
    private final XmlNodeParser xmlNodeParser;

    public XmlNodeParseFunction() {
        sourceStringDescriptor = ParameterDescriptor.string("source_string").description("XML representation in string").build();
        xmlNodeParser = new XmlNodeParser();
    }

    @Override
    public Node evaluate(FunctionArgs args, EvaluationContext context) {
        String sourceString = sourceStringDescriptor.required(args, context);

        return xmlNodeParser.parseFromString(sourceString);
    }

    @Override
    public FunctionDescriptor<Node> descriptor() {
        return FunctionDescriptor.<Node>builder()
                .name(NAME)
                .description("Converts XML representation from string to org.w3c.dom.Node")
                .params(sourceStringDescriptor)
                .returnType(Node.class)
                .build();
    }
}
