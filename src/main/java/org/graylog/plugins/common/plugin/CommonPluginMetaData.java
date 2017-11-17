package org.graylog.plugins.common.plugin;

import org.graylog2.plugin.PluginMetaData;
import org.graylog2.plugin.ServerStatus;
import org.graylog2.plugin.Version;

import java.net.URI;
import java.util.Collections;
import java.util.Set;

/**
 * @author Stepan Pogosyan
 * @since 06.11.2017.
 */
public class CommonPluginMetaData implements PluginMetaData {
    private static final String PLUGIN_PROPERTIES = "org/graylog/plugins/common/graylog-plugin.properties";

    @Override
    public String getUniqueId() {
        return CommonPlugin.class.getName();
    }

    @Override
    public String getName() {
        return "Graylog common functions plugin";
    }

    @Override
    public String getAuthor() {
        return "Stepan Pogosyan";
    }

    @Override
    public URI getURL() {
        return URI.create("https://github.com/stp008");
    }

    @Override
    public Version getVersion() {
        return Version.fromPluginProperties(getClass(), PLUGIN_PROPERTIES, "version", Version.from(1, 0, 0));
    }

    @Override
    public String getDescription() {
        return "Graylog plugin with common functions (xml parsing, xpath evaluation, multiple regex replacement and so on) which are not part of OOB delivery.";
    }

    @Override
    public Version getRequiredVersion() {
        return Version.fromPluginProperties(getClass(), PLUGIN_PROPERTIES, "graylog.version", Version.from(2, 2, 0));
    }

    @Override
    public Set<ServerStatus.Capability> getRequiredCapabilities() {
        return Collections.emptySet();
    }
}
