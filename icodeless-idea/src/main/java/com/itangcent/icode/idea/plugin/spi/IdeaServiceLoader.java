package com.itangcent.icode.idea.plugin.spi;

import com.intellij.openapi.components.ServiceManager;
import com.itangcent.icode.spi.ServiceLoader;

public class IdeaServiceLoader implements ServiceLoader {

    @Override
    public <T> T loadService(Class<T> serviceClass) {
        return ServiceManager.getService(serviceClass);
    }
}
