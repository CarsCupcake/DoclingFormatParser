package com.carscupcake.parser;

/**
 * Representation of a token
 * Docling Tokens are defined in <a href="https://github.com/docling-project/docling-core/blob/main/docling_core/types/doc/tokens.py">tokens.py</a>
 */
public interface IToken {
    String getToken();
    boolean isInfinite();
}
