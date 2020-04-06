package com.itangcent.icode.idea.plugin.spi;

import com.intellij.openapi.components.ServiceManager;
import com.itangcent.icode.spi.ServiceLoader;

/**
 * Adaptor of the IDEA ServiceManager
 * https://www.jetbrains.org/intellij/sdk/docs/basics/plugin_structure/plugin_services.html
 */
public class IdeaServiceLoader implements ServiceLoader {

    @Override
    public <T> T loadService(Class<T> serviceClass) {
        return ServiceManager.getService(serviceClass);
    }
}
