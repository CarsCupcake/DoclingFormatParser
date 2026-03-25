package com.carscupcake.parser;

public enum TableToken implements IToken {
    CellColumnHeader("column_header"),
    CellRowHeader("row_header"),
    CellSectionHeader("shed"),
    CellData("data"),
    //OTSL Tokens
    OtslEmptyCell("ecel"),
    OtslContentCell("fcel"),
    OtslLeftLookingCell("lcel"),
    OtslUpLookingCell("ucel"),
    Otsl2dExtensionCell("xcel"),
    OtslNewLine("nl"),
    OtslColumnHeaderCell("ched"),
    OtslRowHeaderCell("rhed"),
    OtslSectionRowCell("srow");

    public static final IToken[] OTSL_TOKENS = {DocumentToken.LocToken, OtslEmptyCell, OtslContentCell, OtslLeftLookingCell, OtslUpLookingCell, Otsl2dExtensionCell, OtslNewLine, OtslColumnHeaderCell, OtslRowHeaderCell, OtslSectionRowCell};
    public static final TableToken[] TABLE_TOKENS = {CellColumnHeader, CellRowHeader, CellSectionHeader, CellData};

    private final String token;
    TableToken(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public boolean isInfinite() {
        return false;
    }

    @Override
    public boolean hasLocationData() {
        return false;
    }
}
