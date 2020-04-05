package com.itangcent.icode.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public interface UIContainer {

    /**
     * Appends the specified component to the end of this container.
     * <p>
     * This method changes layout-related information, and therefore,
     * invalidates the component hierarchy. If the container has already been
     * displayed, the hierarchy must be validated thereafter in order to
     * display the added component.
     *
     * @param comp the component to be added
     * @return the component argument
     * @throws NullPointerException if {@code comp} is {@code null}
     * @see javax.swing.JComponent#revalidate()
     */
    Component add(Component comp);

    /**
     * Removes the <code>Viewport</code>s one lightweight child.
     */
    void remove(Component child);

    /**
     * remove all sub components
     */
    void clear();


    static UIContainer of(Container container) {
        if (container instanceof JScrollPane) {
            return new JScrollPaneUIContainer((JScrollPane) container);
        } else {
            return new BaseUIContainer(container);
        }
    }

    abstract class AbstractUIContainer implements UIContainer {

        private List<Component> subs = new ArrayList<>();

        @Override
        public Component add(Component comp) {
            subs.add(comp);
            return doAdd(comp);
        }

        @Override
        public void remove(Component child) {
            subs.remove(child);
            doRemove(child);
        }

        abstract Component doAdd(Component comp);

        abstract void doRemove(Component child);

        @Override
        public void clear() {
            if (!subs.isEmpty()) {
                for (Component sub : subs) {
                    doRemove(sub);
                }
            }
            subs.clear();
        }
    }

    class BaseUIContainer extends AbstractUIContainer {

        private Container container;

        private BaseUIContainer(Container container) {
            this.container = container;
        }

        @Override
        public Component doAdd(Component comp) {
            return container.add(comp);
        }

        @Override
        public void doRemove(Component child) {
            container.remove(child);
        }

    }

    class JScrollPaneUIContainer extends AbstractUIContainer {


        private JScrollPane container;

        private JScrollPaneUIContainer(JScrollPane container) {
            this.container = container;
        }

        @Override
        public Component doAdd(Component comp) {
            return container.getViewport().add(comp);
        }

        @Override
        public void doRemove(Component child) {
            container.getViewport().remove(child);
        }
    }

}
