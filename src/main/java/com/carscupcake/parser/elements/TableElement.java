package com.carscupcake.parser.elements;

import com.carscupcake.parser.AbstractElement;
import com.carscupcake.parser.DocumentToken;
import com.carscupcake.parser.IToken;
import com.carscupcake.parser.Pos2d;

public final class TableElement extends AbstractElement {
    private final String[][] elements;

    public TableElement(String[][] elements, Pos2d a, Pos2d b) {
        super(a, b);
        this.elements = elements;
    }

    @Override
    public IToken getToken() {
        return DocumentToken.Table;
    }

    public String[][] elements() {
        return elements;
    }

    @Override
    public String toString() {
        return "TableElement[" + "elements=" + elements + ']';
    }

}
