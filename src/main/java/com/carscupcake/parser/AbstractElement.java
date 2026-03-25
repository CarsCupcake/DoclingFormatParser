package com.carscupcake.parser;

public abstract class AbstractElement implements IElement {
    protected final Pos2d a;
    protected final Pos2d b;
    public AbstractElement(Pos2d a, Pos2d b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Pos2d getA() {
        return a;
    }

    @Override
    public Pos2d getB() {
        return b;
    }
}
