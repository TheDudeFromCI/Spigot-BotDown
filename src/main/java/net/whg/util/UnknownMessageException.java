package net.whg.util;

/**
 * Called when a message cannot be called from the translations file.
 */
public class UnknownMessageException extends RuntimeException {
    private static final long serialVersionUID = -6934705136453053488L;

    /**
     * Creates a new UnknownMessageException.
     * 
     * @param path - The translation path.
     */
    public UnknownMessageException(String path) {
        super("Unknown message at path: '" + path + "'");
    }
}
