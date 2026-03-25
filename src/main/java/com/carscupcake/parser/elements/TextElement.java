package com.carscupcake.parser.elements;

import com.carscupcake.parser.*;

import java.util.Objects;

public final class TextElement extends AbstractElement {
    private final String value;

    public TextElement(String value, Pos2d a, Pos2d b) {
        super(a, b);
        this.value = value;
    }

    @Override
    public IToken getToken() {
        return DocumentToken.Text;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TextElement) obj;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "TextElement[" +
                "value=" + value + ']';
    }

}
