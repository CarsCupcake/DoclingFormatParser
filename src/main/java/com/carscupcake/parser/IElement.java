package com.carscupcake.parser;

public interface IElement {
    IToken getToken();
    Pos2d getA();
    Pos2d getB();
}
