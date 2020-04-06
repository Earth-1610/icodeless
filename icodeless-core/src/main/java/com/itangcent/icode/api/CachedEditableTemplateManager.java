package com.itangcent.icode.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CachedEditableTemplateManager implements EditableTemplateManager {

    protected List<EditableTemplate> templateCache = new ArrayList<>();

    @Override
    public EditableTemplate findByName(String name) {
        return findByName(name, null);
    }

    @Override
    public EditableTemplate findByName(String name, TemplateContext context) {
        EditableTemplate editableTemplate = templateCache
                .stream().filter(it -> it.name().equals(name))
                .findFirst()
                .orElse(null);
        if (editableTemplate == null) {
            editableTemplate = doFindByName(name, context);
            templateCache.add(editableTemplate);
        }
        return editableTemplate;
    }

    protected abstract EditableTemplate doFindByName(String name, TemplateContext context);

    @Override
    public boolean isAnyModified() {
        return templateCache.stream()
                .anyMatch(EditableTemplate::isAnyModified);
    }

    @Override
    public List<EditableTemplate> allModified() {
        return templateCache
                .stream()
                .filter(EditableTemplate::isAnyModified)
                .collect(Collectors.toList());
    }

    /**
     * discard modified template in cache
     */
    @Override
    public void reset() {
        templateCache.removeIf(EditableTemplate::isAnyModified);
    }

    @Override
    public void save() {
        doSave(allModified());
        templateCache.clear();
    }

    protected abstract void doSave(List<EditableTemplate> editableTemplates);
}
