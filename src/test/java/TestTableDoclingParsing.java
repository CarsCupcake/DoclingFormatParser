import com.carscupcake.parser.DoclingTextParser;
import org.junit.jupiter.api.Test;

public class TestTableDoclingParsing {
    @Test
    public void basicCellParsingTest() {
        var text = """
                <otsl>
                <loc_0><loc_0><loc_500><loc_500><ched>Col1<ched>Col2<nl><fcel>Content11<fcel>Content12<nl><fcel>Content21<fcel>Content22<nl>
                </otsl>
                """;
        var parsed = DoclingTextParser.parse(text);
        assert parsed.root() == null;
        assert parsed.tables().length == 1;
        var table = parsed.tables()[0];
        assert table.elements().length == 3;
        assertRow(table.elements()[0], "Col1", "Col2");
        assertRow(table.elements()[1], "Content11", "Content12");
        assertRow(table.elements()[2], "Content21", "Content22");
    }

    private void assertRow(String[] row, String... cells) {
        assert row.length == cells.length;
        for (int i = 0; i < cells.length; i++) {
            assert cells[i].equals(row[i]);
        }
    }
}
