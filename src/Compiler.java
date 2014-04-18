/* ================================================================== *
    
    Universidade Federal de Sao Carlos - UFSCar, Sorocaba

    Disciplina: Compiladores
    Prof. Tiemi Christine Sakata

    Compilador 0-3

    Autor: Giulianno Raphael Sbrugnera RA: 408093
    Autor: Matheus Casarin Paez        RA: 438308
 
 * ================================================================== */

import ast.*;
import java.util.ArrayList;

public class Compiler {

    public Program compile(char []p_input)
    {
        input = p_input;
        tokenPos = 0;
        nextToken();

        Program e = program();
        
        if (tokenPos != input.length)
        {
            error();
        }

        return e;
    }

    // Program ::= {Line}
    private Program program()
    {
        Line line = null;
        ArrayList lines = null;
        
    	while (((input[tokenPos - 1] == 'w') && (input[tokenPos] == 'r') && (input[tokenPos + 1] == 'i') && (input[tokenPos + 2] == 't') && (input[tokenPos + 3] == 'e')) || (Character.isLetter(token)))
        {
            line = line();

            if (lines == null)
            {
                lines = new ArrayList<Line>();
            }

            lines.add(line);

            if (token == ';')
            {
                nextToken();
            }

            if (tokenPos >= input.length)
            {
                break;
            }

        }

    	return new Program(lines);
    }

    // Line ::= (Write | Atrib) ';'
    private Line line()
    {
        Write write = null;
        Atrib atrib = null;

        if ((input[tokenPos - 1] == 'w') && (input[tokenPos] == 'r') && (input[tokenPos + 1] == 'i') && (input[tokenPos + 2] == 't') && (input[tokenPos + 3] == 'e'))
        {
            // comando write
            write = write();

            if (token != ';')
            {
                error();
            }
        }
        else
        {
            if (Character.isLetter(token))
            {
                // atribuição
                atrib = atrib();

                if (token != ';')
                {
                    error();
                }
            }
            else
            {
                error();
            }
        }

        return new Line(write, atrib);
    }

    // Write ::= 'Write' '(' expr ')'
    private Write write()
    {
        Expr expr = null;
        
        nextToken();
        nextToken();
        nextToken();
        nextToken();
        nextToken();

        if (token == '(')
        {
            nextToken();

            if ((Character.isLetter(token)) || (Character.isDigit(token)))
            {
                expr = expr();
            }
            else
            {
                error();
            }

            if (token != ')')
            {
                error();
            }
            
            nextToken();

            return new Write(expr);
        }
        else
        {
            error();
        }

        return new Write(expr);
    }

    // Atrib ::= var '=' expr
    private Atrib atrib()
    {
        char var = 0;
        Expr expr = null;

        if (Character.isLetter(token))
        {
            var = var();
        }
        else
        {
            error();
        }

        if (token == '=')
        {
            nextToken();
        }
        else
        {
            error();
        }

        if ((Character.isLetter(token)) || (Character.isDigit(token)))
        {
            expr = expr();
        }
        else
        {
            error();
        }

        return new Atrib(var, expr);
    }

    // expr ::= t ei
    private Expr expr() 
    {
        T t = null;
        Ei ei = null;

        if ((Character.isLetter(token)) || (Character.isDigit(token))) 
        {
            t = t();
        }
        else 
        {
            error();
        }

        if (token == '+')
        {
            ei = ei();
        }

        return new Expr(t, ei);
    }

    // ei ::= '+' t ei |
    private Ei ei()
    {
        T t = null;
        Ei ei = null;

        if (token == '+')
        {
            nextToken();

            if ((Character.isLetter(token)) || (Character.isDigit(token))) 
            {
                t = t();
            }
            else
            {
                error();
            }

            if (token == '+')
            {
                ei = ei();
            }
        }

        return new Ei(t, ei);
    }

    // t ::= f ti
    private T t()
    {
        F f = null;
        Ti ti = null;

        if ((Character.isLetter(token)) || (Character.isDigit(token)))
        {
            f = f();
        }
        else
        {
            error();
        }

        if (token == '*')
        {
            ti = ti();
        }

        return new T(f, ti);
    }

    // ti ::= '*' f ti |
    private Ti ti()
    {
        F f = null;
        Ti ti = null;

        if (token == '*')
        {
            nextToken();

            if ((Character.isLetter(token)) || (Character.isDigit(token)))
            {
                f = f();
            }
            else
            {
                error();
            }

            if (token == '*')
            {
                ti = ti();
            }
        }

        return new Ti(f, ti);
    }

    // f ::= number | var
    private F f()
    {
        ArrayList<Character> number = null;
        char var = 0;

        if (Character.isDigit(token))
        {
            number = number();
        }
        else
        {
            if (Character.isLetter(token))
            {
                var = var();
            }
            else
            {
                error();
            }
        }

        return new F(number, var);
    }

    // number ::= digit {[digit]}
    private ArrayList<Character> number() 
    {
        ArrayList<Character> number = null;

        if (Character.isDigit(token)) 
        {
            number = new ArrayList<Character>();
            number.add(digit());
        }
        else 
        {
            error();
        }

        while (Character.isDigit(token))
        {
            number.add(digit());
        }

        return number;
    }

    // digit ::= '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9'
    private char digit()
    {
        char aux = 0;

        if (Character.isDigit(token)) 
        {
            aux = token;
            flag = 1;
            nextToken();
        }
        else 
        {
            error();
        }

        return aux;
    }

    // var ::= 'a' | ... | 'z'
    private char var()
    {
        char aux = 0;

        if (Character.isLetter(token))
        {
            aux = token;
            nextToken();
        }
        else
        {
            error();
        }
        
        return aux;
    }

    private void nextToken() 
    {
        while (tokenPos < input.length && input[tokenPos] == ' ') 
        {
            if (input[tokenPos] == ' ' && flag == 1)
            {
                flag = 2;
            }

            tokenPos++;
        }
        
        if((flag == 2) && ((Character.isDigit(input[tokenPos]))))
        {
              error();
        }

        flag = 0;

        if (tokenPos < input.length) 
        {
            token = input[tokenPos];
            tokenPos++;
        }
    }
    
    private void error() {
        if ( tokenPos == 0 ) 
          tokenPos = 1; 
        else 
          if ( tokenPos >= input.length )
            tokenPos = input.length;
        
        String strInput = new String( input, tokenPos - 1, input.length - tokenPos + 1 );
        String strError = "Error at \"" + strInput + "\"";
        System.out.println( strError );
        throw new RuntimeException(strError);
    }
    
    private char token;
    private int  tokenPos;
    private char []input;
    private int flag = 0;
      
}
