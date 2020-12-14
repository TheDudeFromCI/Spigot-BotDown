package net.whg.util;

public class UnknownMessageException extends RuntimeException {
    private static final long serialVersionUID = -6934705136453053488L;

    public UnknownMessageException(String path) {
        super("Unknown message at path: '" + path + "'");
    }
}
