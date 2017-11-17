package org.graylog.plugins.common.plugin;

import org.graylog2.plugin.Plugin;
import org.graylog2.plugin.PluginMetaData;
import org.graylog2.plugin.PluginModule;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Stepan Pogosyan
 * @since 06.11.2017.
 */
public class CommonPlugin implements Plugin {
    @Override
    public PluginMetaData metadata() {
        return new CommonPluginMetaData();
    }

    @Override
    public Collection<PluginModule> modules() {
        return Collections.singletonList(new CommonPluginModule());
    }
}
