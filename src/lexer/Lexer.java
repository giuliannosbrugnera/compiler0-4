package lexer;

public class Lexer {
	
    public char token;
    private String stringValue;
    private int numberValue;
    private char charValue;
    public int flag = 0;
    
    public int  tokenPos;
    private int lastTokenPos;
    private char []input;
    
    private int lineNumber;
    
    public Lexer(char []input) {
        this.input = input;
          // add an end-of-file label to make it easy to do the lexer
        input[input.length - 1] = '\0';
        
//        System.out.println("Input:\n" + new String(input) + "\nLexer method.\n");
          // number of the current line
        lineNumber = 1;
        tokenPos = 0;
    }

    public void nextToken() {
        while ((tokenPos < input.length) && 
        	   ((input[tokenPos] == ' ') ||
        	   (input[tokenPos] == '\n'))) {
            if (input[tokenPos] == ' ' && flag == 1) {
                flag = 2;
            }

            tokenPos++;
        }
        
        if ((flag == 2) && ((Character.isDigit(input[tokenPos])))) {
              error("Número esperado");
        }

        flag = 0;

        if (tokenPos < input.length) {
            token = input[tokenPos];
            tokenPos++;
        }
    }

    // return the line number of the last token got with getToken()
    public int getLineNumber() {
        return lineNumber;
    }
        
    public String getCurrentLine() {
        int i = lastTokenPos;
        if ( i == 0 ) 
          i = 1; 
        else 
          if ( i >= input.length )
            i = input.length;
            
        StringBuffer line = new StringBuffer();
          // go to the beginning of the line
        while ( i >= 1 && input[i] != '\n' )
          i--;
        if ( input[i] == '\n' )
          i++;
          // go to the end of the line putting it in variable line
        while ( input[i] != '\0' && input[i] != '\n' && input[i] != '\r' ) {
            line.append( input[i] );
            i++;
        }
        return line.toString();
    }

    public String getStringValue() {
       return stringValue;
    }
    
    public int getNumberValue() {
       return numberValue;
    }
    
    public char getCharValue() {
       return charValue;
    }
    
    public void error(String errorCause) {
        if ( tokenPos == 0 ) {
          tokenPos = 1; 
        }
        else { 
          if ( tokenPos >= input.length ) {
            tokenPos = input.length;
          }
        }
        
        String strInput = new String( input, tokenPos - 1, input.length - tokenPos + 1 );
        String strError = "Error at \"" + strInput + "\"";
        System.out.println( strError );
        System.out.println(errorCause);
        throw new RuntimeException(strError);
    }
}