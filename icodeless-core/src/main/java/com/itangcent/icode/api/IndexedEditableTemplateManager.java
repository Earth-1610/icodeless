package com.itangcent.icode.api;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides an indexed list of template names.
 * Name of the template can be loaded and displayed without loading all templates in the manager.
 */
public abstract class IndexedEditableTemplateManager extends CachedEditableTemplateManager implements ListableEditableTemplateManager {

    @Override
    public List<EditableTemplate> allTemplates() {
        List<NamedElement> templateNames = allTemplateNames();
        if (templateNames == null || templateNames.isEmpty()) {
            return Collections.emptyList();
        }
        return templateNames.stream()
                .map(IndexedEditableTemplate::new)
                .collect(Collectors.toList());
    }

    /**
     * The name of templates which has be stored in the indexes
     */
    public abstract List<NamedElement> allTemplateNames();

    public class IndexedEditableTemplate implements EditableTemplate {

        private NamedElement namedElement;

        private EditableTemplate rowEditableTemplate = null;

        public IndexedEditableTemplate(NamedElement name) {
            this.namedElement = name;
        }

        private EditableTemplate getRowEditableTemplate() {
            if (rowEditableTemplate == null) {
                rowEditableTemplate = findByName(namedElement.name());
            }
            return rowEditableTemplate;
        }

        @Override
        public void setName(String name) {
            getRowEditableTemplate().setName(name);
        }

        @Override
        public void setDisplayName(String displayName) {
            getRowEditableTemplate().setDisplayName(displayName);
        }

        @Override
        public void setParams(List<TemplateParam> params) {
            getRowEditableTemplate().setParams(params);
        }

        @Override
        public void setAlwaysCheckBeforeRender(boolean alwaysCheckBeforeRender) {
            getRowEditableTemplate().setAlwaysCheckBeforeRender(alwaysCheckBeforeRender);
        }

        @Override
        public void setType(String type) {
            getRowEditableTemplate().setType(type);
        }

        @Override
        public void setContent(String content) {
            getRowEditableTemplate().setContent(content);
        }

        @Override
        public List<TemplateParam> params() {
            return getRowEditableTemplate().params();
        }

        @Override
        public boolean alwaysCheckBeforeRender() {
            return getRowEditableTemplate().alwaysCheckBeforeRender();
        }

        @Override
        public String type() {
            return getRowEditableTemplate().type();
        }

        @Override
        public String content() {
            return getRowEditableTemplate().content();
        }

        @Override
        public String name() {
            return rowEditableTemplate == null ? namedElement.name() : rowEditableTemplate.name();
        }

        @Override
        public String displayName() {
            return rowEditableTemplate == null ? namedElement.displayName() : rowEditableTemplate.displayName();
        }

        @Override
        public boolean isAnyModified() {
            return rowEditableTemplate != null && rowEditableTemplate.isAnyModified();
        }
    }
}
