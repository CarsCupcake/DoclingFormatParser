package com.carscupcake.parser.elements;

import com.carscupcake.parser.IElement;
import com.carscupcake.parser.IToken;

public record IgnoredElement(IToken token) implements IElement {

    @Override
    public IToken getToken() {
        return token;
    }
}
