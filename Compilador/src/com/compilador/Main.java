package com.compilador;

import java.io.File;


public class Main {
	
	public static void main(String[] args) {
		if(args.length < 2) {
			System.out.println("Argumentos invalidos");
			return;
		} 
		
		switch (args[0]) {
		case "lexico":
			jflex.Main.generate(new File(args[1]));
			break;

		default:
			break;
		}
		
	}
	
}
