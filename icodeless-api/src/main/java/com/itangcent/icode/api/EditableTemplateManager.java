package com.itangcent.icode.api;


import java.util.List;

public interface EditableTemplateManager extends TemplateManager {

    /**
     * Find template by name.
     * the Template get from EditableTemplateManager should be editable
     *
     * @param name -template name
     * @return the template which is editable
     */
    @Override
    EditableTemplate findByName(String name);

    /**
     * Find template by name and context.
     * The Template get from EditableTemplateManager should be editable
     *
     * @param name -template name
     * @return the template which is editable
     */
    @Override
    EditableTemplate findByName(String name, TemplateContext context);

    /**
     * Return true if any template was modified.
     */
    boolean isAnyModified();

    /**
     * Gets all templates which has be edited and modified.
     */
    List<EditableTemplate> allModified();

    /**
     * discard all changes
     */
    void reset();

    /**
     * Save all changes.
     */
    void save();
}
