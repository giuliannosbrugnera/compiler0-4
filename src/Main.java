/* ================================================================== *
    
    Universidade Federal de Sao Carlos - UFSCar, Sorocaba

    Disciplina: Compiladores
    Prof. Tiemi Christine Sakata

    Compilador 0-4

    Autor: Giulianno Raphael Sbrugnera RA: 408093
    Autor: Matheus Casarin Paez        RA: 438308
 
 * ================================================================== */

import ast.*;

import java.io.*;

public class Main {
    
	public static void main(String []args) {
		int numChRead;
        String error;
        
    	File file = null;
        FileReader stream = null;
        Program program = null;
        Compiler compiler = null;

        if (args.length != 1)  {
            System.out.println("Usage:\nMain input");
            System.out.println("input is the file to be compiled");
        }
        else {
    		file = new File(args[0]);
           
    		if ((!file.exists()) || (!file.canRead())) {
    			System.out.println("Either the file " + args[0] + " does not exist or it cannot be read");
    			throw new RuntimeException();
    		}
           
    		try { 
    			stream = new FileReader(file);  
    		} 
    		catch (FileNotFoundException e) {
                System.out.println("Something wrong: file does not exist anymore");
                throw new RuntimeException();
    		}
            
    		// one more character for '\0' at the end that will be added by the
            // compiler
            char []input = new char[ (int ) file.length() + 1 ];
            
            try {
            	numChRead = stream.read( input, 0, (int ) file.length() );
            } 
            catch (IOException e) {
                System.out.println("Error reading file " + args[0]);
                throw new RuntimeException();
            }
                
            if (numChRead != file.length()) {
                System.out.println("Read error");
                throw new RuntimeException();
            }
            try {
            	stream.close();
            } 
            catch (IOException e) {
                System.out.println("Error in handling the file " + args[0]);
                throw new RuntimeException();
            }

            compiler = new Compiler();

            try {   
            	program  = compiler.compile(input);
            } 
            catch (RuntimeException e) {
            	System.out.println("Ocorreu um erro durante a geração do código em linguagem C.");
            	System.out.println("Log error:");
                e.printStackTrace();
            }
            
            if (program != null) {
            	program.genC();
            }
        }
    }
}