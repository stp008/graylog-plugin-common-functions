package org.graylog.plugins.common.plugin;

import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import org.graylog.plugins.common.functions.regex.ReplaceRegexConstFunction;
import org.graylog.plugins.common.functions.regex.ReplaceRegexSha512Function;
import org.graylog.plugins.common.functions.xml.ReplaceXpathConstFunction;
import org.graylog.plugins.common.functions.xml.ReplaceXpathSha512Function;
import org.graylog.plugins.common.functions.xml.XmlNodeParseFunction;
import org.graylog.plugins.common.functions.xml.XmlNodeToStringFunction;
import org.graylog.plugins.pipelineprocessor.ast.functions.Function;
import org.graylog2.plugin.PluginConfigBean;
import org.graylog2.plugin.PluginModule;

import java.util.Collections;
import java.util.Set;

/**
 * @author Stepan Pogosyan
 * @since 06.11.2017.
 */
public class CommonPluginModule extends PluginModule {

    @Override
    public Set<? extends PluginConfigBean> getConfigBeans() {
        return Collections.emptySet();
    }

    @Override
    protected void configure() {
        addMessageProcessorFunction(ReplaceRegexConstFunction.NAME, ReplaceRegexConstFunction.class);
        addMessageProcessorFunction(ReplaceRegexSha512Function.NAME, ReplaceRegexSha512Function.class);
        addMessageProcessorFunction(XmlNodeParseFunction.NAME, XmlNodeParseFunction.class);
        addMessageProcessorFunction(XmlNodeToStringFunction.NAME, XmlNodeToStringFunction.class);
        addMessageProcessorFunction(ReplaceXpathSha512Function.NAME, ReplaceXpathSha512Function.class);
        addMessageProcessorFunction(ReplaceXpathConstFunction.NAME, ReplaceXpathConstFunction.class);
    }

    private void addMessageProcessorFunction(String name, Class<? extends Function<?>> functionClass) {
        addMessageProcessorFunction(binder(), name, functionClass);
    }

    private static MapBinder<String, Function<?>> processorFunctionBinder(Binder binder) {
        return MapBinder.newMapBinder(binder, TypeLiteral.get(String.class), new TypeLiteral<Function<?>>() {});
    }


    private static void addMessageProcessorFunction(Binder binder, String name, Class<? extends Function<?>> functionClass) {
        processorFunctionBinder(binder).addBinding(name).to(functionClass);

    }
}
