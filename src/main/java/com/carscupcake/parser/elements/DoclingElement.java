package com.carscupcake.parser.elements;

import com.carscupcake.parser.*;

import java.util.List;
import java.util.Objects;

public final class DoclingElement extends AbstractElement {
    private final List<IElement> elements;

    public DoclingElement(List<IElement> elements) {
        super(new Pos2d(0, 0), new Pos2d(0, 0));
        this.elements = elements;
    }

    @Override
    public IToken getToken() {
        return DocumentToken.Document;
    }

    public List<IElement> elements() {
        return elements;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DoclingElement) obj;
        return Objects.equals(this.elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

    @Override
    public String toString() {
        return "DoclingElement[" +
                "elements=" + elements + ']';
    }

}
