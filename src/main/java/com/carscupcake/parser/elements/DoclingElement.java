package com.carscupcake.parser.elements;

import com.carscupcake.parser.DocumentToken;
import com.carscupcake.parser.IElement;
import com.carscupcake.parser.IToken;

import java.util.List;

public record DoclingElement(List<IElement> elements) implements IElement {
    @Override
    public IToken getToken() {
        return DocumentToken.Document;
    }
}
