package net.whg.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Offers an allocation-free way to supply a readonly object to classes outside
 * the module what keeping the array list itself still modifiable.
 */
public class PartialReadonlyList<E> extends ArrayList<E> {
    private static final long serialVersionUID = -3174412135304495454L;

    private final transient List<E> readonly;

    /**
     * Creates a new empty list.
     */
    public PartialReadonlyList() {
        super();
        readonly = Collections.unmodifiableList(this);
    }

    /**
     * Returns a readonly wrapper for this list without creating any object
     * allocations.
     * 
     * @return The readonly wrapper.
     */
    public List<E> readonly() {
        return readonly;
    }
}
