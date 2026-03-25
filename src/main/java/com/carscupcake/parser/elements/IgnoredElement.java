package com.carscupcake.parser.elements;

import com.carscupcake.parser.AbstractElement;
import com.carscupcake.parser.IToken;
import com.carscupcake.parser.Pos2d;

import java.util.Objects;

public final class IgnoredElement extends AbstractElement {
    private final IToken token;

    public IgnoredElement(IToken token, Pos2d a, Pos2d b) {
        super(a, b);
        this.token = token;
    }

    @Override
    public IToken getToken() {
        return token;
    }

    public IToken token() {
        return token;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (IgnoredElement) obj;
        return Objects.equals(this.token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

    @Override
    public String toString() {
        return "IgnoredElement[" + "token=" + token + ']';
    }

}
