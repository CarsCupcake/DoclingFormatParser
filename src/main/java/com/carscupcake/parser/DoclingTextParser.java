package com.carscupcake.parser;


import com.carscupcake.parser.elements.DoclingElement;
import com.carscupcake.parser.elements.IgnoredElement;
import com.carscupcake.parser.elements.TableElement;
import com.carscupcake.parser.elements.TextElement;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class DoclingTextParser {
    public static DoclingDocument parse(String text) {
        return parse(new ByteArrayInputStream(text.getBytes()));
    }

    public static DoclingDocument parse(InputStream inputStream) {
        var elements = new ArrayList<IElement>();
        var tables = new ArrayList<TableElement>();
        var buffer = new TextBuffer(inputStream);
        buffer.seek('<');
        var tag = buffer.seek('>');
        if (tag != null && tag.equals(DocumentToken.OTSL.getToken())) {
                return new DoclingDocument(null, new TableElement[] {buildOtsl(buffer)});
        }
        if (tag == null || !tag.equals(DocumentToken.Document.getToken())) {
            throw new IllegalArgumentException("Not a valid docling string");
        }
        tokenize(buffer, (token, s) -> {
            if (token == DocumentToken.Text) {
                elements.add(buildTextTag(buffer));
            } else if (token == DocumentToken.OTSL) {
                var table = buildOtsl(buffer);
                tables.add(table);
                elements.add(table);
            }else {
                String skipped = "";
                while (!skipped.endsWith(s)) {
                    buffer.seek('/');
                    skipped = buffer.seek('>');
                    if (skipped == null) {
                        throw new IllegalArgumentException("Token " + s + " never ends!");
                    }
                }
                elements.add(new IgnoredElement(token));
            }
        }, DocumentToken.Document, DocumentToken.values());
        var doclingElement = new DoclingElement(elements);
        return new DoclingDocument(doclingElement, tables.toArray(TableElement[]::new));
    }

    private static void tokenize(TextBuffer buffer, BiConsumer<IToken, String> handle, IToken expectedEndToken, IToken... validTokens) {
        boolean isFinished = false;
        while (!isFinished) {
            buffer.seek('<');
            Node<IToken> possebilities = null;
            Node<IToken> last = null;

            for (var token : validTokens) { // TODO Handle Nodes more efficiently (like not discarding nodes)
                if (possebilities == null) {
                    possebilities = new Node<>(token);
                    last = possebilities;
                } else {
                    var node = new Node<>(token);
                    last.next = node;
                    node.prev = last;
                    last = node;
                }

            }
            var endToken = new Node<>((IToken) new EndToken(expectedEndToken.getToken()));
            endToken.prev = last;
            last.next = endToken;

            int charIndex = 0;
            char current = buffer.next();
            var builder = new StringBuilder();
            do {
                builder.append(current);
                var n = possebilities;
                while (n != null) {
                    var tokenString = n.item.getToken();
                    if ((tokenString.length() <= charIndex && !n.item.isInfinite()) || charIndex < tokenString.length() && tokenString.charAt(
                            charIndex) != current) {
                        if (possebilities == n) {
                            possebilities = n.next;
                        }
                        if (n.prev != null) n.prev.next = n.next;
                        if (n.next != null) n.next.prev = n.prev;
                        n.prev = null;
                        n.next = null;
                    }
                    n = n.next;
                }
                if (possebilities == null) {
                    var rest = buffer.seek('>');
                    if (rest != null) builder.append(rest);
                    throw new IllegalArgumentException("No valid token: " + builder);
                }
                charIndex++;
                current = buffer.next();
            } while (current != '>');
            var s = builder.toString();
            IToken token = null;
            var n = possebilities;
            while (n != null) {
                if (n.item.getToken().equals(s) || n.item.isInfinite() && s.startsWith(n.item.getToken())) {
                    token = n.item;
                    break;
                }
                n = n.next;
            }
            if (token == null) {
                throw new IllegalArgumentException("No valid token: " + s);
            }
            if (token instanceof EndToken)
                isFinished = true;
            else {
                handle.accept(token, s);
            }
        }
    }

    private static IElement buildTextTag(TextBuffer buffer) {
        String skipped = "";
        StringBuilder text = new StringBuilder();
        while (!skipped.equals("/" + DocumentToken.Text.getToken())) {
            text.append(buffer.seek('<'));
            skipped = buffer.seek('>');
            if (skipped == null) {
                throw new IllegalArgumentException("Text tag never ends!");
            }
        }
        return new TextElement(text.toString());
    }

    private static TableElement buildOtsl(TextBuffer buffer) {
        var header = new ArrayList<String>();
        var content = new ArrayList<ArrayList<String>>();
        content.add(new ArrayList<>());
        tokenize(buffer, (token, s) -> {
            if (token == DocumentToken.LocToken) return; //TODO Add Location Information
            var builder = new StringBuilder();
            while (buffer.peek() != '<') {
                builder.append(buffer.next());
            }
            if (token == TableToken.OtslColumnHeaderCell) {
                header.add(builder.toString());
            }
            if (token == TableToken.OtslNewLine) {
                if (!content.get(0).isEmpty()) {
                    content.add(new ArrayList<>());
                }
            }
            if (token == TableToken.OtslContentCell) {
                content.get(content.size() - 1).add(builder.toString());
            }
            if (token == TableToken.OtslEmptyCell) {
                content.get(content.size() - 1).add("");
            }
        }, DocumentToken.OTSL, TableToken.OTSL_TOKENS);
        var strings = new String[content.size()][];
        strings[0] = header.toArray(String[]::new);
        for (int i = 0; i < content.size() - 1; i++) {
            strings[i + 1] = content.get(i).toArray(String[]::new);
        }
        return new TableElement(strings);
    }

    private record EndToken(String original) implements IToken {
        @Override
        public String getToken() {
            return "/" + original;
        }

        @Override
        public boolean isInfinite() {
            return false;
        }
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        public Node(E element) {
            this.item = element;
        }
    }
}
