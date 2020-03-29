package com.itangcent.icode.api;

public interface CodeRenderProviderManager {

    /**
     * find CodeRenderProvider by type
     */
    CodeRenderProvider findByType(String type);
}
