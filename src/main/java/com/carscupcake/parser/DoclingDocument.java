package com.carscupcake.parser;

import com.carscupcake.parser.elements.DoclingElement;
import com.carscupcake.parser.elements.TableElement;
import org.jetbrains.annotations.Nullable;

public record DoclingDocument(@Nullable DoclingElement root, TableElement[] tables) {
}
