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
public class XmlNodeToStringFunction extends AbstractFunction<String> {

    public static final String NAME = "parse_xml_to_string";

    private final ParameterDescriptor<Node, Node> sourceNodeDescriptor;

    //TODO replace with @Inject
    private final XmlNodeParser xmlNodeParser;

    public XmlNodeToStringFunction() {
        sourceNodeDescriptor = ParameterDescriptor.type("source_node", Node.class).description("XML representation in org.w3c.dom.Node").build();
        xmlNodeParser = new XmlNodeParser();
    }

    @Override
    public String evaluate(FunctionArgs args, EvaluationContext context) {
        Node sourceNode = sourceNodeDescriptor.required(args, context);

        return xmlNodeParser.parseFromNode(sourceNode);
    }

    @Override
    public FunctionDescriptor<String> descriptor() {
        return FunctionDescriptor.<String>builder()
                .name(NAME)
                .description("Converts XML representation from org.w3c.dom.Node to string")
                .params(sourceNodeDescriptor)
                .returnType(String.class)
                .build();
    }
}
