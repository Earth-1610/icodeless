package com.itangcent.icode.api;

public interface CodeRenderProvider {

    /**
     * compile content
     */
    CodeRender buildRender(String content);
}
