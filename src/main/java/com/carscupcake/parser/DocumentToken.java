package com.carscupcake.parser;

public enum DocumentToken implements IToken {
    Document("doctag"),
    OTSL("otsl"),
    Chart("chart"),
    OrderedList("ordered_list") {
        @Override
        public boolean hasLocationData() {
            return false;
        }
    },
    UnorderedList("unordered_list") {
        @Override
        public boolean hasLocationData() {
            return false;
        }
    },
    PageBreak("page_break"),
    Smiles("smiles"),
    Inline("inline"),
    Caption("caption"),
    Footnote("footnote"),
    Formula("formula"),
    ListItem("list_item"),
    PageFooter("page_footer"),
    PageHeader("page_header"),
    Picture("picture"),
    Table("table"),
    Text("text"),
    Title("title"),
    DocumentIndex("document_index"),
    Code("code"),
    CheckboxSelected("checkbox_selected"),
    CheckboxUnselected("checkbox_unselected"),
    Form("form"),
    KeyValueRegion("key_value_region"),
    Paragraph("paragraph"),
    Reference("reference"),
    LocToken("loc_", true),
    SectionHeader("section_header_level_", true),;

    private final String token;
    private final boolean isInfinite;
    DocumentToken(String token) {
        this.token = token;
        isInfinite = false;
    }

    DocumentToken(String token, boolean isInfinite) {
        this.token = token;
        this.isInfinite = isInfinite;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public boolean isInfinite() {
        return isInfinite;
    }
}
