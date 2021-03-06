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
 * Join multiple parameters in either the "full composition" way
 * or use "1 to 1". The Param list used in this class is usually generated
 * by SweepVar.
 */
public class JoinParam {
    // Join and generate based on running mode
    private int nparam,nvalue;
    private String runningmode;
    private Param [] params;
    private int [][] tmp,tmp1;
    /**
     * Join a Param array in a way defined by mode.
     * @param p Param list usually generated by SweepVar
     * @param mode if mode="a" or mode includes the letter "a"
     *                  it makes full composition run;
     *             otherwise: it will do a 1 to 1 run.
     */
    public JoinParam(Param [] p, String mode) {
        runningmode = mode;
        nparam = p.length;
        params = new Param[nparam];
        params = p;
        int nmodel,nvalue1;
        
        int ii,jj,kk;
        
        if (runningmode.indexOf("a") == -1) {
            // one by one 
            nvalue=p[0].nv;
            nmodel=nvalue;
            tmp = new int [nmodel][nparam];
            for (ii=0;ii<nmodel;ii++) {
                for (jj=0;jj<nparam;jj++) {
                    tmp[ii][jj]= ii;
                }
            }    
        }   else {
            // all composition
            // System.out.println("all composition");
            ii=0;
            nvalue=p[0].nv;
            if (nparam == 1) { 
                // one parameter aka p.length=1
                tmp = new int[p[0].nv][1];
                while (ii<tmp.length) {
                    tmp[ii][0]=ii;
                    ii++;
                }
            } else {
                tmp = new int[p[0].nv][1];
                while (ii<tmp.length) {
                    tmp[ii][0]=ii;
                    ii++;
                }
                for (ii=1;ii<nparam;ii++) {
                    nvalue1=nvalue*p[ii].nv;
                    tmp1 = new int[nvalue1][ii+1];
                    for (kk=0;kk<nvalue;kk++){
                        for (jj=0;jj<p[ii].nv;jj++) {
                            for (int uu=0;uu<=ii;uu++) {
                                if (uu<ii) {
                                    tmp1[kk*p[ii].nv+jj][uu] = tmp[kk][uu];
                                }
                                else {
                                    tmp1[kk*p[ii].nv+jj][uu]=jj;
                                }
                            }
                        }
                    }                   
                    nvalue=nvalue1;
                    tmp = new int[nvalue][ii+1];
                    tmp = tmp1;                   
                }
            }   
            }
            
        
    }

    /**
     * Give the combined 2D LUT. The 1st dim is the index of the actual model.
     * For n total models to be run, the length of 1st dim is n.
     * The 2nd dim is for the parameters to be swept.
     * @return lut which is a 2D array.
     */
    public int[][] getCombine() {
        int[][] lut = tmp;
            // System.out.println(lut.length);
        return lut;
    }
    
    /**
     * This is the length of 1st dimension of the 2D LUT from getCombine().
     * @return Total number of COMSOL models to be run.
     */
    public int getSweepcount() {
        int totalsweepcount=nvalue;
        return totalsweepcount;
    }
    
}
