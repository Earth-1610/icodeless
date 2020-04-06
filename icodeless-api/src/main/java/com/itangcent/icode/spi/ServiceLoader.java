package com.itangcent.icode.spi;

import java.util.Iterator;
import java.util.WeakHashMap;

public interface ServiceLoader {

    /**
     * Loads the provider of the serviceClass.
     *
     * @param serviceClass The interface or abstract class representing the service
     * @return the provider of the serviceClass.
     */
    <T> T loadService(Class<T> serviceClass);

    static <T> T getService(Class<T> serviceClass) {
        return SERVICE_LOADER_INSTANCE.getService(serviceClass);
    }

    ServiceLoaderInstance SERVICE_LOADER_INSTANCE = new ServiceLoaderInstance();

    class ServiceLoaderInstance {

        /**
         * Singleton instance of JDKServiceLoader
         */
        private static JDKServiceLoader jdkServiceLoader = new JDKServiceLoader();

        private WeakHashMap<Class, Object> serviceCache = new WeakHashMap<>();
        private WeakHashMap<ClassLoader, ServiceLoader> loaderCache = new WeakHashMap<>();

        /**
         * Get thr provider from cache or load it by serviceLoader.
         *
         * @param serviceClass The interface or abstract class representing the service
         * @return the provider of the serviceClass.
         */
        @SuppressWarnings("unchecked")
        <T> T getService(Class<T> serviceClass) {
            Object service = serviceCache.get(serviceClass);
            if (service == null) {
                service = loadService(serviceClass);
                if (service != null) {
                    serviceCache.put(serviceClass, service);
                }
            }
            return (T) service;
        }

        /**
         * Loads the provider of the serviceClass by ClassLoader.
         *
         * @param serviceClass The interface or abstract class representing the service
         */
        <T> T loadService(Class<T> serviceClass) {
            ClassLoader classLoader = serviceClass.getClassLoader();
            ServiceLoader serviceLoader = loaderCache.get(classLoader);
            if (serviceLoader == null) {
                serviceLoader = loadServiceLoaderFromJDKSpi(classLoader);
                if (serviceLoader != null) {
                    loaderCache.put(classLoader, serviceLoader);
                }
            }
            return serviceLoader == null ? null : serviceLoader.loadService(serviceClass);
        }

        /**
         * Load the implementation of the custom ServiceLoader by the Java standard ServiceLoader.
         * If no implementation of a custom ServiceLoader is provided.Use the {@code JDKServiceLoader}
         *
         * @param classLoader The class loader to be used to load provider-configuration files
         *                    and provider classes, or <tt>null</tt> if the system class
         *                    loader (or, failing that, the bootstrap class loader) is to be
         *                    used
         * @return The ServiceLoader for the classLoader
         */
        private ServiceLoader loadServiceLoaderFromJDKSpi(ClassLoader classLoader) {
            java.util.ServiceLoader<ServiceLoader> serviceLoader = java.util.ServiceLoader.load(ServiceLoader.class, classLoader);
            Iterator<ServiceLoader> iterator = serviceLoader.iterator();
            if (iterator.hasNext()) {
                return iterator.next();
            }
            return jdkServiceLoader;
        }
    }

    /**
     * Adaptor of the Java standard ServiceLoader
     */
    class JDKServiceLoader implements ServiceLoader {
        @Override
        public <T> T loadService(Class<T> serviceClass) {
            java.util.ServiceLoader<T> serviceLoader = java.util.ServiceLoader.load(serviceClass);
            Iterator<T> iterator = serviceLoader.iterator();
            if (iterator.hasNext()) {
                return iterator.next();
            }
            return null;
        }
    }

}
