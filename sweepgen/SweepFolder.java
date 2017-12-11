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
 * Add the sweepfolder if not existed and create the txt
 * for sweep param/variables
 */
public class SweepFolder {
    //Add the sweepfolder if not existed and create the txt
    //for sweep param/variables
    private String folder;
    private String output;
    private File contain;
    private String path;
    
    /**
     * The sweepfolder is read from input.txt
     * @param strSweepFolder the string of the current folder
     */
    public SweepFolder(String strSweepFolder) { 	
        folder = strSweepFolder;
        output = folder+".sweep";
        contain = new File(folder);
    }
    
    /**
     * Create the folder storing all the comsol models to be run
     * if the dir does not exist yet 
     */
    public void createFolder() {
        if (!contain.exists()) {
            System.out.println("creating dir");
            boolean result = contain.mkdir();
            if(result) {
                System.out.println("dir created");
            }
        }
    }

    /**
     * Create the text file storing all the comsol model name to be run
     * if the file does not exist yet 
     */
    public void createOutTxt() {
        File f = new File(contain, output);
        System.out.println(f.getPath());
        try {
            if (f.createNewFile()) {
                System.out.println("sweep text created");
            }
                else {
                System.out.println("sweep text existed");
                }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * Return current working dir in string
     */
    public String getPath() {
        path = System.getProperty("user.dir");
        return path;
    }
    
    /**
     * Return the full dir that would include all the COMSOL models
     */ 
    public String getSweepPath() {
        path = System.getProperty("user.dir") + "/"+folder+"/"+output+"/";
        return path;
    }
    
}
