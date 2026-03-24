package com.carscupcake.parser.elements;

import com.carscupcake.parser.DocumentToken;
import com.carscupcake.parser.IElement;
import com.carscupcake.parser.IToken;

public record TextElement(String value) implements IElement {
    @Override
    public IToken getToken() {
        return DocumentToken.Text;
    }
}
