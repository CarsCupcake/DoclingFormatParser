package com.carscupcake.parser;

import org.jetbrains.annotations.Nullable;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class TextBuffer implements AutoCloseable {
    private final InputStream inputStream;
    private boolean isFinished = false;
    private int next;

    public TextBuffer(InputStream inputStream) {
        this.inputStream = inputStream;
        try {
            next = inputStream.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (next == -1)
            isFinished = true;
    }

    @Override
    public void close() throws Exception {
        inputStream.close();
    }

    public char next() {
        if (isFinished)
            throw new IllegalStateException("Stream is already finished");
        try {
            var c = (char) next;
            next = inputStream.read();
            if (next == -1)
                isFinished = true;
            return c;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Seek to the next occurrence of the given character.
     * @param c the character
     * @return skipped chars as string, returns null if no character matched
     */
    public @Nullable String seek(char c) {
        var builder = new StringBuilder();
        while (!isFinished) {
            var n = next();
            if (c == n)
                return builder.toString();
            builder.append(n);
        }
        return null;
    }

    public char peek() {
        if (isFinished) throw new IllegalStateException("Stream is already finished");
        return (char) next;
    }

    public void skip(int n)  {
        try {
            inputStream.skipNBytes(n);
        } catch (EOFException e) {
            throw new IllegalStateException("End of Stream!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
