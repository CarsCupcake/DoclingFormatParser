import com.carscupcake.parser.DoclingTextParser;
import com.carscupcake.parser.DocumentToken;
import com.carscupcake.parser.IElement;
import com.carscupcake.parser.Pos2d;
import com.carscupcake.parser.elements.TextElement;
import org.junit.jupiter.api.Test;

public class TestGeneralDoclingParsing {
    @Test
    public void testParse() {
        var text = """
                <doctag>
                <text><loc_1><loc_1><loc_11><loc_11>A Text</text>
                <section_header_level_1><loc_11><loc_11><loc_21><loc_21>Section 1</section_header_level_1>
                </doctag>
                """;
        var parsed = DoclingTextParser.parse(text);
        var root = parsed.root();

        assert root != null;
        assert root.getToken() == DocumentToken.Document;
        assert root.elements().size() == 2;
        assert root.elements().get(0).getToken() == DocumentToken.Text;
        assertElementPos(root.elements().get(0), 1, 1, 11, 11);
        assert ((TextElement) root.elements().get(0)).value().equals("A Text");
        assert root.elements().get(1).getToken() == DocumentToken.SectionHeader;
        assertElementPos(root.elements().get(1), 11, 11, 21, 21);
    }

    private void assertElementPos(IElement element, int x1, int y1, int x2, int y2) {
        assert element.getA().equals(new Pos2d(x1, y1));
        assert element.getB().equals(new Pos2d(x2, y2));
    }
}
