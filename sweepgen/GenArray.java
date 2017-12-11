package sweepgen;

import java.util.*;
import java.lang.Number.*;
import java.util.regex.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.io.*;

/**
 * Generate number arrays for parametric sweep
 */
public class GenArray {
	// Give two options for data arrays
	// 1. 1:1:3->1 2 3
	// 2. 1 2 3
	// Return count and also the value
	private String input;
	private double [] output;
	private int nv;

	/**
     * @param in can be either in the form of 1:1:3 or 1 2 3 
     */
	public GenArray(String in) {
		input = in;
		if (input.indexOf(":") != -1) { // For input params defined by : 
			System.out.println(input);
			String[] out=new String[3];
            out=input.split(":");
	        double vars=Double.valueOf(out[0]);
	        double vare=Double.valueOf(out[2]);
	        double vard=Double.valueOf(out[1]);
            //varl=(vare-vars)/vard+1;
            int len=(int)((vare-vars)/vard+1);
			nv = len;
			output = new double [len];
            int ii=0;
			output[0]=vars;
			System.out.println(len);
			while (ii<len-1) {//TODO
				output[ii+1]=output[ii]+vard;
                //System.out.println(output[ii+1]);
				ii++;
			}
	    }
		else { // defined by spaced number
			System.out.println(input);
			String [] out=input.split(" ");
			int len = out.length;
			nv = len;
			output = new double[len];
			int ii = 0;
			System.out.println(len);
			while (ii< len){
				output[ii]=Double.valueOf(out[ii]);
				ii++;
			}
		}
	}

	/**
         * @return the generated double number array
         */
	public double [] getArray() {
		return output;
	}

	/**
         * @return length of the array
         */
	public int getArraylen() {
		return nv;
	}

	/**
         * print the generated array
         */
	public void showArray() {
		int ii=0;
		System.out.println(nv);
		while (ii<nv) {
			
			System.out.println(output[ii]);
			ii++;
		}
	}

}
