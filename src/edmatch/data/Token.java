/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch.data;

/**
 *
 * @author rohit
 */
import java.util.regex.Pattern;

/**
 * Offset marks the display offset of character - this might be different than
 * the characters position in the char array due existence of multi-char
 * characters.
 * <p>
 * Since 1.6 strips '&' in given token text.
 * 
 * @author Keith Godfrey
 * @author Maxym Mykhalchuk
 */
public class Token {
    /**
     * Two tokens are thought equal if their hash code is equal.
     * 
     * @author Henry Pijffers (henry.pijffers@saxnot.com)
     */
    public boolean equals(Object other) {
        return ((this == other) || ((other instanceof Token) && (hash == ((Token) other).hash)));
    }

    /**
     * -1 if text is null, text's hashcode otherwise.
     */
    private int hash;

    public int hashCode() {
        return hash;
    }

    private static Pattern AMP = Pattern.compile("\\&");

    private final String stripAmpersand(String s) {
        return AMP.matcher(s).replaceAll("");
    }

    /**
     * Creates a new token.
     * 
     * @param _text
     *            the text of the token
     * @param _offset
     *            the starting position of this token in parent string
     */
    public Token(String _text, int _offset) {
        this(_text, _offset, _text.length());
    }

    /**
     * Creates a new token.
     * 
     * @param _text
     *            the text of the token
     * @param _offset
     *            the starting position of this token in parent string
     * @param _length
     *            length of token
     */
    public Token(String _text, int _offset, int _length) {
        length = _length;
        hash = (_text == null) ? -1 : stripAmpersand(_text).hashCode();
   //     hash = (_text == null) ? -1 :_text.hashCode();
        offset = _offset;
        text=_text;
    }

    private int length;
    private int offset;
    private String text;

    /** Returns the length of a token. */
    public final int getLength() {
        return length;
    }
    
    /** Returns token's text */
    public final String getText(){
        return text;
    }
    /** Returns token's offset in a source string. */
    public final int getOffset() {
        return offset;
    }

    public final String toString() {
        return hash + "@" + offset;
    }

    public final boolean isvalid(){
        return offset==0;
    }
    /**
     * Return the section of the string denoted by the token
     */
    public String getTextFromString(String input) {
        return input.substring(offset, length + offset);
    }
}
