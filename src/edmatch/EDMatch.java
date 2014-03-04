/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;

import edmatch.data.PPPair;
import edmatch.data.ExtToken;
import edmatch.data.Token;
import edmatch.data.LdPPSPair;
import edmatch.data.Match;
import edmatch.data.Paraphrase;
//import edmatch.matching.LevenshteinDistancePPall;
import edmatch.matching.LevenshteinDistance;
//import edmatch.matching.LevenshteinDistance100;
import edmatch.matching.LevenshteinDistance100ctd;
//import edmatch.matching.LevenshteinDistance200;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.Comparator;
import java.lang.Exception;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.text.NumberFormat;

/**
 *
 * @author rohit
 */
public class EDMatch {

    /**
     * @param str
     * @param cand
     */
    
   static  HashMap<String , ArrayList<Paraphrase> > usedPP=null;
    
   public static ArrayList<Paraphrase> getUsedPPbyKey(String key){
       if(key==null)return null;
        return usedPP.get(key);
    
    }
    public static double calcSimilarity(final Token[] str,
            final Token cand[]) {
        LevenshteinDistance dc= new LevenshteinDistance();
        if (str.length == 0 && cand.length == 0) {
            // empty token lists - can't calculate similarity
            return 0;
        }
        int ld = dc.compute(str, cand);
        double similarity = (100.0 * (Math.max(str.length, cand.length) - ld)) / Math.max(str.length, cand.length);
        return similarity;
    }
    
    public static <T> T[] reverse(T[] array) {
    T[] copy = array.clone();
    Collections.reverse(Arrays.asList(copy));
    return copy;
    }
    /**
     *
     * @param str
     * @param cand
     * @param score edit distance
     * @return 
     */
    public static double calcSimilarityPP(int str,int cand, int score
            ) {
        
        double similarity = (100.0 * (Math.max(str, cand) - score)) / Math.max(str, cand);
       // System.out.println("ED:"+score+"Sim:"+similarity+" input:"+str+" Match:"+cand);
        return similarity;
    }
    
    public static double calcSimilarityPP(int str,int cand, double score
            ) {
        
        double similarity = (100.0 * (Math.max(str, cand) - score)) / Math.max(str, cand);
       // System.out.println("ED:"+score+"Sim:"+similarity+" input:"+str+" Match:"+cand);
        return similarity;
    }
    
    /**
     *
     * @param tks token
     * @return
     */
    
    static void printtokens(Token [] tks){
        for (Token tk : tks) {
            System.err.print(tk.getText() + " ");
        }
        System.err.println();
    }
    
    
            /*class SIM_SORT implements Comparator<match>{
                @Override
                public int compare(match m1, match m2){
                    if(m1.sim <m2.sim) return -1;
                    if(m1.sim>m2.sim) return 1;
                    return 0;
                }
            }*/
    
    
    /**
     * extract matches in two steps
     * step1: Filter using length 
     * step2: find matches using simple edit distance
     * step3: Apply paraphrasing to matches find from step 1
     * @param th threshold for step 1 filtering using edit distance
     * @param max filtering too many extracted using simple edit distance
     * @param flag type of paraphrasing '-p' or '-pa' 
     * @param inputtokens user input file tokens
     * @param tmsrctokens tm source tokens
     * @param tgtTM   tm target tokens
     * @param tgtinput expected target for user input file
     * @param outfile  output file in xml format 
     * @return 
    */
    
    static ArrayList<MatchStore>  extractMatchAboveTH(double lenTH,double beamTH, double tmTH, int nbest, ArrayList<Token []> inputtokens, ArrayList<Token []> tmsrctokens,ArrayList<SentencePP> tmsrcexttokens, ArrayList<String> tgtTM,ArrayList<String> tgtinput, String outfile){
        ArrayList<MatchStore> listms=new ArrayList();
             
            class match implements Comparable<match> {
                double sim;
                int index;
                match(double sim,int index){
                    this.sim=sim;
                    this.index=index;
                }
                @Override
                public int compareTo(match other){
                    return Double.valueOf(this.sim).compareTo(other.sim);
                }
                
            }    
            
        try{
            FileWriter fw = new FileWriter(outfile);
                int sno=-1;
                fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                fw.write("<document>");
                System.err.println("TM size "+tmsrctokens.size()+" Input size "+inputtokens.size());
                for(Token [] sen:inputtokens){
                       MatchStore m=new MatchStore(sen);
                    sno++;
                    System.err.print("Executing->"+sno+" :");
                    printtokens(sen);
                    
                    //double maxsim=0.0;
                    //int maxmatchid=0;
                    int index=0;
                    List<match> thmatches=new ArrayList();
                    for(Token [] tmsen:tmsrctokens){
                        double lenratio=(100.0*Math.min(tmsen.length, sen.length))/(1.0*Math.max(tmsen.length, sen.length));
                    //    System.out.println("LenRatio:"+lenratio);
                        if(lenratio >=lenTH){
                            double sim=calcSimilarity(tmsen, sen);
                     //    printtokens(tmsen);
                        // printtokens(sen);
                      //    System.out.println(" SIM:"+sim+" th:"+th);
                            if(lenTH<sim){
                              thmatches.add(new match(sim,index));
                            }
                            
                        }
                        index++;
                    }
                    System.err.println(thmatches.size());
                    Collections.sort(thmatches);
                    Collections.reverse(thmatches);
                    ArrayList<SentencePP> topmatches=new ArrayList();
                    if(!thmatches.isEmpty()){
                        double maxsim=thmatches.get(0).sim;
                        System.err.print("maxsim="+maxsim+" :");
                        for(int i=0;i<nbest && i<thmatches.size() && beamTH>=(maxsim-thmatches.get(i).sim); i++){
                                //System.out.println("th="+th+" "+maxsim+" "+thmatches.get(i).sim+" "+);
                                System.err.print(thmatches.get(i).index+" "+thmatches.get(i).sim+":");
                          //      printtokens(tmsrctokens.get(thmatches.get(i).index));
                            topmatches.add(tmsrcexttokens.get(thmatches.get(i).index));
                            
                        }
                    }
                    double maxsim=-1.0;
                    int maxmatchid=-1;
                    LdPPSPair bestmatch=new LdPPSPair();
                    index=0;
                    //System.out.print("Processing:");
                    int tmsno=0;
                    double [] pppenalty=new double[4];
                    for(SentencePP tmsen:topmatches){
                        System.err.print(++tmsno+"=");
                        LdPPSPair ldpair;
                        double sim;
                            LevenshteinDistance100ctd dc= new LevenshteinDistance100ctd();
                            ldpair = dc.compute(sen,tmsen.get(),pppenalty);
                            if(ldpair==null)continue;
                            sim=calcSimilarityPP(sen.length,ldpair.length(),ldpair.getEditDistance());
                        // printtokens(tmsen);
                        // printtokens(sen);
                        //  System.out.println(" SIM:"+sim);
                        if(maxsim<sim){
                            maxsim=sim;
                            maxmatchid=index;
                            bestmatch=ldpair;
                            
                        }
                        if(thmatches.get(index).sim>tmTH){
                                m.add(new Match(tmsen.get(),thmatches.get(index).index, thmatches.get(index).sim));
                            }
                        if(sim>tmTH){
                            m.add(new Match(tmsen.get(),thmatches.get(index).index, sim,ldpair));
                        }
                        index++;
                        
                    }                   
                    
                    fw.write("<segment id=\""+sno+"\">");
                    fw.write("<input>");
                    for (Token sen1 : sen) {
                        fw.write(sen1.getText() + " ");
                    }
                    fw.write("</input>\n");                    
                    fw.write("<exptd>");
                    fw.write(tgtinput.get(sno));
                    fw.write("</exptd>\n");
                    String ms=Math.round(maxsim*100.0)/100.0 + "";
                    fw.write("<match score=\""+ms+"\" prevrank=\""+maxmatchid+"\">");
                    //Token [] bestmatch=tmsrctokens.get(maxmatchid);
                    for (short i=0;i<bestmatch.length();i++) {
                        fw.write(bestmatch.getTokenAt(i).getText() + " ");
                    }
                    fw.write("</match>\n");
                    fw.write("<paraphrases>");
                    for(PPPair item : bestmatch.getMatchedParaphrases()){
                        fw.write("<pp index=\"");
                        fw.write(item.getLocation()+"\""+" text=\""+item.getParaphrase().getleft()+"\" "+"para=\""+item.getParaphrase().getright()+"\"");
                        fw.write("/>");
                    }
                    fw.write("</paraphrases>\n");
                    fw.write("<retvd lang=\"FR\">");
                    int id=-1;
                    if(!topmatches.isEmpty()){
                        String french=tgtTM.get(thmatches.get(maxmatchid).index);
                        System.out.println(french);
                        id=thmatches.get(maxmatchid).index;
                    fw.write(french);}
                    fw.write("</retvd>\n");
                    fw.write("<retvd lang=\"EN\" id=\""+id+"\">");
                    if(!topmatches.isEmpty()){
                            SentencePP spp=topmatches.get(maxmatchid);
                            ExtToken [] english=spp.get();
                            for(ExtToken extk:english){
                                fw.write(extk.getToken().getText()+" ");
                            }
                    }
                    fw.write("</retvd>\n");
                    
                    fw.write("<prevretvd lang=\"FR\">");
                    id=-1;
                    double prevscore=0.0;
                    if(!topmatches.isEmpty()){
                        String french=tgtTM.get(thmatches.get(0).index);
                        id=thmatches.get(0).index;
                        prevscore=thmatches.get(0).sim;
                        fw.write(french);
                    }
                    fw.write("</prevretvd>\n");
                    fw.write("<prevretvd lang=\"EN\" id=\""+id+"\" prevscore=\""+prevscore+"\">");
                    if(!topmatches.isEmpty()){
                            SentencePP spp=topmatches.get(0);
                            ExtToken [] english=spp.get();
                            for(ExtToken extk:english){
                                fw.write(extk.getToken().getText()+" ");
                            }
                    }
                    fw.write("</prevretvd>\n");
                    fw.write("<thmatches th="+"\""+tmTH+"\" >\n");
                    fw.write("<edmatches>");
                    for(Match mch:m.getMatches()){
                        if(!mch.isppApplied()){
                            fw.write("<edmatch id=\""+mch.getId()+"\" score=\""+mch.similarity()+"\" >");
                            for (ExtToken edMatch : mch.getEDMatch()) {
                                fw.write(edMatch.getToken().getText()+" ");
                            }
                            fw.write("</edmatch>\n");
                            fw.write("<target lang=\"FR\">");
                            fw.write(tgtTM.get(mch.getId()));
                            fw.write("</target>\n");
                        }
                    }
                    fw.write("</edmatches>\n");
                    fw.write("<ppmatches>");
                    for( Match mch: m.getMatches()){
                        if(mch.isppApplied()){
                           fw.write("<ppmatch id=\""+mch.getId()+"\" score=\""+mch.similarity()+"\" >");
                           for (ExtToken ppMatch : mch.getPPMatch()) {
                                fw.write(ppMatch.getToken().getText()+" ");
                            }
                            fw.write("</ppmatch>\n");
                            fw.write("<target lang=\"FR\">");
                            fw.write(tgtTM.get(mch.getId()));
                            fw.write("</target>\n");  
                        }
                    }
                    fw.write("</ppmatches>\n");
                    fw.write("</thmatches>\n");
                    fw.write("</segment>\n");               
                  } 
                    
                   fw.write("</document>");
                   fw.close();
            
            }catch(IOException e){
                System.err.print(e);
            }
        return listms;
    }

    
    
    /**
     * extract matches in two steps
     * step1: Filter using length 
     * step2: find matches using simple edit distance
     * step3: Apply paraphrasing to matches find from step 1
     * @param th threshold for step 1 filtering using edit distance
     * @param max filtering too many extracted using simple edit distance
     * @param flag type of paraphrasing '-p' or '-pa' 
     * @param inputtokens user input file tokens
     * @param tmsrctokens tm source tokens
     * @param tgtTM   tm target tokens
     * @param tgtinput expected target for user input file
     * @param outfile  output file in xml format 
     * @return 
    */
    
 /*   static ArrayList<MatchStore>  extractMatchDouble(double lenTH,double beamTH, int max, ArrayList<Token []> inputtokens, ArrayList<Token []> tmsrctokens,ArrayList<SentencePP> tmsrcexttokens, ArrayList<String> tgtTM,ArrayList<String> tgtinput, String outfile){
        ArrayList<MatchStore> listms=new ArrayList();
             
            class match implements Comparable<match> {
                double sim;
                int index;
                match(double sim,int index){
                    this.sim=sim;
                    this.index=index;
                }
                @Override
                public int compareTo(match other){
                    return Double.valueOf(this.sim).compareTo(other.sim);
                }
                
            }    
            
        try{
            FileWriter fw = new FileWriter(outfile);
                int sno=-1;
                fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                fw.write("<document>");
                System.out.println("TM size "+tmsrctokens.size()+" Input size "+inputtokens.size());
                for(Token [] sen:inputtokens){
                    //   MatchStore m=new MatchStore(sen);
                    sno++;
                    System.out.print("Executing->"+sno+" :");
                    printtokens(sen);
                    
                    //double maxsim=0.0;
                    //int maxmatchid=0;
                    int index=0;
                    List<match> thmatches=new ArrayList();
                    for(Token [] tmsen:tmsrctokens){
                        double lenratio=(100.0*Math.min(tmsen.length, sen.length))/(1.0*Math.max(tmsen.length, sen.length));
                    //    System.out.println("LenRatio:"+lenratio);
                        if(lenratio >=lenTH){
                            double sim=calcSimilarity(tmsen, sen);
                     //    printtokens(tmsen);
                        // printtokens(sen);
                      //    System.out.println(" SIM:"+sim+" th:"+th);
                            if(lenTH<sim){
                              thmatches.add(new match(sim,index));
                            }
                        }
                        index++;
                    }
                    System.out.println(thmatches.size());
                    Collections.sort(thmatches);
                    Collections.reverse(thmatches);
                    ArrayList<SentencePP> topmatches=new ArrayList();
                    if(!thmatches.isEmpty()){
                        double maxsim=thmatches.get(0).sim;
                        System.out.print("maxsim="+maxsim+" :");
                        for(int i=0;i<max && i<thmatches.size() && beamTH>=(maxsim-thmatches.get(i).sim); i++){
                                //System.out.println("th="+th+" "+maxsim+" "+thmatches.get(i).sim+" "+);
                                System.out.print(thmatches.get(i).index+" "+thmatches.get(i).sim+":");
                          //      printtokens(tmsrctokens.get(thmatches.get(i).index));
                            topmatches.add(tmsrcexttokens.get(thmatches.get(i).index));
                        }
                    }
                    double maxsim=-1.0;
                    int maxmatchid=-1;
                    LdPPSPair bestmatch=new LdPPSPair();
                    index=0;
                    //System.out.print("Processing:");
                    int tmsno=0;
                    char winner='0';
                    for(SentencePP tmsen:topmatches){
                        System.out.print(++tmsno+"=");
                        LdPPSPair ldpair;
                        double sim;
                            LevenshteinDistance100ctd dc= new LevenshteinDistance100ctd();
                            ldpair = dc.compute(sen,tmsen.get());
                            if(ldpair==null)continue;
                            sim=calcSimilarityPP(sen.length,ldpair.length(),ldpair.getEditDistance());
                        
                        LdPPSPair ldpair2;
                        double sim2;
                            System.err.println("Double Not implemented yet");
                            System.exit(1);
                            LevenshteinDistance100ctd dc2= new LevenshteinDistance100ctd();
                            ldpair2 = dc2.compute(reverse(sen),reverse(tmsen.get()));
                            if(ldpair2==null)continue;
                            sim2=calcSimilarityPP(sen.length,ldpair2.length(),ldpair2.getEditDistance());
                         winner='1';
                        if(sim2>sim)winner='2';
                        // printtokens(tmsen);
                        // printtokens(sen);
                        //  System.out.println(" SIM:"+sim);
                        if(winner=='1'){
                            if(maxsim<sim){
                                maxsim=sim;
                                maxmatchid=index;
                                bestmatch=ldpair;
                            }
                        }else if(winner=='2'){
                            if(maxsim<sim2){
                                maxsim=sim2;
                                maxmatchid=index;
                                bestmatch=ldpair2;
                            }
                        }else {System.err.print("error EDMatch line 212\n");System.exit(1);}
                        index++;
                        
                    }                   
                    
                    fw.write("<segment id=\""+sno+"\">");
                    for (Token sen1 : sen) {
                        fw.write(sen1.getText() + " ");
                    }
                    fw.write('\n');
                    fw.write("<exptd>");
                    fw.write(tgtinput.get(sno));
                    fw.write("</exptd>\n");
                    String ms=Math.round(maxsim*100.0)/100.0 + "";
                    fw.write("<match score=\""+ms+"\" prevrank=\""+maxmatchid+"\" winner=\""+winner+"\">");
                    //Token [] bestmatch=tmsrctokens.get(maxmatchid);
                    for (short i=0;i<bestmatch.length();i++) {
                        fw.write(bestmatch.getTokenAt(i).getText() + " ");
                    }
                    fw.write("</match>\n");
                    fw.write("<paraphrases>");
                    for(PPPair item : bestmatch.getMatchedParaphrases()){
                        fw.write("<pp index=\"");
                        fw.write(item.getLocation()+"\""+" text=\""+item.getParaphrase().getleft()+"\" "+"para=\""+item.getParaphrase().getright()+"\"");
                        fw.write("/>");
                    }
                    fw.write("</paraphrases>\n");
                    fw.write("<retvd lang=\"FR\">");
                    int id=-1;
                    if(!topmatches.isEmpty()){
                        String french=tgtTM.get(thmatches.get(maxmatchid).index);
                        id=thmatches.get(maxmatchid).index;
                    fw.write(french);}
                    fw.write("</retvd>\n");
                    fw.write("<retvd lang=\"EN\" id=\""+id+"\">");
                    if(!topmatches.isEmpty()){
                            SentencePP spp=topmatches.get(maxmatchid);
                            ExtToken [] english=spp.get();
                            for(ExtToken extk:english){
                                fw.write(extk.getToken().getText()+" ");
                            }
                    }
                    fw.write("</retvd>\n");
                    
                    fw.write("<prevretvd lang=\"FR\">");
                    id=-1;
                    double prevscore=0.0;
                    if(!topmatches.isEmpty()){
                        String french=tgtTM.get(thmatches.get(0).index);
                        id=thmatches.get(0).index;
                        prevscore=thmatches.get(0).sim;
                        fw.write(french);
                    }
                    fw.write("</prevretvd>\n");
                    fw.write("<prevretvd lang=\"EN\" id=\""+id+"\" prevscore=\""+prevscore+"\">");
                    if(!topmatches.isEmpty()){
                            SentencePP spp=topmatches.get(0);
                            ExtToken [] english=spp.get();
                            for(ExtToken extk:english){
                                fw.write(extk.getToken().getText()+" ");
                            }
                    }
                    fw.write("</prevretvd>\n");
                    
                    
                    fw.write("</segment>\n");               
                  } 
                    
                   fw.write("</document>");
                   fw.close();
            
            }catch(IOException e){
                System.err.print(e);
            }
        return listms;
    }

    /**
     * extract matches in two steps
     * step1: Filter using length 
     * step2: find matches using simple edit distance
     * step3: Apply paraphrasing to matches find from step 1
     * @param th threshold for step 1 filtering using edit distance
     * @param max filtering too many extracted using simple edit distance
     * @param flag type of paraphrasing '-p' or '-pa' 
     * @param inputtokens user input file tokens
     * @param tmsrctokens tm source tokens
     * @param tgtTM   tm target tokens
     * @param tgtinput expected target for user input file
     * @param outfile  output file in xml format 
     * @return 
    */
    
    static ArrayList<MatchStore>  extractTopMatchOnly(double lenTH,double beamTH, int nbest, ArrayList<Token []> inputtokens, ArrayList<Token []> tmsrctokens,ArrayList<SentencePP> tmsrcexttokens, ArrayList<String> tgtTM,ArrayList<String> tgtinput, String outfile){
        ArrayList<MatchStore> listms=new ArrayList();
             
            class match implements Comparable<match> {
                double sim;
                int index;
                match(double sim,int index){
                    this.sim=sim;
                    this.index=index;
                }
                @Override
                public int compareTo(match other){
                    return Double.valueOf(this.sim).compareTo(other.sim);
                }
                
            }    
            
        try{
            FileWriter fw = new FileWriter(outfile);
                int sno=-1;
                fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                fw.write("<document>");
                System.err.println("TM size "+tmsrctokens.size()+" Input size "+inputtokens.size());
                for(Token [] sen:inputtokens){
                    //   MatchStore m=new MatchStore(sen);
                    sno++;
                    System.err.print("Executing->"+sno+" :");
                    printtokens(sen);
                    
                    //double maxsim=0.0;
                    //int maxmatchid=0;
                    int index=0;
                    List<match> thmatches=new ArrayList();
                    for(Token [] tmsen:tmsrctokens){
                        double lenratio=(100.0*Math.min(tmsen.length, sen.length))/(1.0*Math.max(tmsen.length, sen.length));
                    //    System.out.println("LenRatio:"+lenratio);
                        if(lenratio >=lenTH){ //earlier multiplied by 1.4
                            double sim=calcSimilarity(tmsen, sen);
                     //    printtokens(tmsen);
                        // printtokens(sen);
                      //    System.out.println(" SIM:"+sim+" th:"+th);
                            if(lenTH<sim){
                              thmatches.add(new match(sim,index));
                            }
                        }
                        index++;
                    }
                    System.err.println(thmatches.size());
                    Collections.sort(thmatches);
                    Collections.reverse(thmatches);
                    ArrayList<SentencePP> topmatches=new ArrayList();
                    if(!thmatches.isEmpty()){
                        double maxsim=thmatches.get(0).sim;
                       // System.out.print("maxsim="+maxsim+" :");
                        for(int i=0;i<nbest && i<thmatches.size() && beamTH>=(maxsim-thmatches.get(i).sim); i++){
                                //System.out.println("th="+th+" "+maxsim+" "+thmatches.get(i).sim+" "+);
                                System.err.print(thmatches.get(i).index+" "+thmatches.get(i).sim+":");
                          //      printtokens(tmsrctokens.get(thmatches.get(i).index));
                            topmatches.add(tmsrcexttokens.get(thmatches.get(i).index));
                        }
                    }
                    double maxsim=-1.0;
                    int maxmatchid=-1;
                    LdPPSPair bestmatch=new LdPPSPair();
                    index=0;
                    //System.out.print("Processing:");
                    int tmsno=0;
                    double [] pppenalty=new double[4];
                    
                    for(SentencePP tmsen:topmatches){
                        System.err.print(++tmsno+"=");
                        LdPPSPair ldpair;
                        double sim;
                            LevenshteinDistance100ctd dc= new LevenshteinDistance100ctd();
                            ldpair = dc.compute(sen,tmsen.get(),pppenalty);
                            if(ldpair==null)continue;
                            sim=calcSimilarityPP(sen.length,ldpair.length(),ldpair.getEditDistance());
                        // printtokens(tmsen);
                        // printtokens(sen);
                        //  System.out.println(" SIM:"+sim);
                        if(maxsim<sim){
                            maxsim=sim;
                            maxmatchid=index;
                            bestmatch=ldpair;
                        }
                        index++;
                        
                    }                   
                    
                    fw.write("<segment id=\""+sno+"\">");
                    for (Token sen1 : sen) {
                        fw.write(sen1.getText() + " ");
                    }
                    fw.write('\n');
                    fw.write("<exptd>");
                    fw.write(tgtinput.get(sno));
                    fw.write("</exptd>\n");
                    String ms=Math.round(maxsim*100.0)/100.0 + "";
                    fw.write("<match score=\""+ms+"\" prevrank=\""+maxmatchid+"\">");
                    //Token [] bestmatch=tmsrctokens.get(maxmatchid);
                    for (short i=0;i<bestmatch.length();i++) {
                        fw.write(bestmatch.getTokenAt(i).getText() + " ");
                    }
                    fw.write("</match>\n");
                    fw.write("<paraphrases>");
                    for(PPPair item : bestmatch.getMatchedParaphrases()){
                        fw.write("<pp index=\"");
                        fw.write(item.getLocation()+"\""+" text=\""+item.getParaphrase().getleft()+"\" "+"para=\""+item.getParaphrase().getright()+"\"");
                        fw.write("/>");
                    }
                    fw.write("</paraphrases>\n");
                    fw.write("<retvd lang=\"FR\">");
                    int id=-1;
                    if(!topmatches.isEmpty()){
                        String french=tgtTM.get(thmatches.get(maxmatchid).index);
                        System.out.println(french);
                        id=thmatches.get(maxmatchid).index;
                    fw.write(french);}
                    fw.write("</retvd>\n");
                    fw.write("<retvd lang=\"EN\" id=\""+id+"\">");
                    if(!topmatches.isEmpty()){
                            SentencePP spp=topmatches.get(maxmatchid);
                            ExtToken [] english=spp.get();
                            for(ExtToken extk:english){
                                fw.write(extk.getToken().getText()+" ");
                            }
                    }
                    fw.write("</retvd>\n");
                    
                    fw.write("<prevretvd lang=\"FR\">");
                    id=-1;
                    double prevscore=0.0;
                    if(!topmatches.isEmpty()){
                        String french=tgtTM.get(thmatches.get(0).index);
                        id=thmatches.get(0).index;
                        prevscore=thmatches.get(0).sim;
                        fw.write(french);
                    }
                    fw.write("</prevretvd>\n");
                    fw.write("<prevretvd lang=\"EN\" id=\""+id+"\" prevscore=\""+prevscore+"\">");
                    if(!topmatches.isEmpty()){
                            SentencePP spp=topmatches.get(0);
                            ExtToken [] english=spp.get();
                            for(ExtToken extk:english){
                                fw.write(extk.getToken().getText()+" ");
                            }
                    }
                    fw.write("</prevretvd>\n");
                    
                    
                    fw.write("</segment>\n");               
                  } 
                    
                   fw.write("</document>");
                   fw.close();
            
            }catch(IOException e){
                System.err.print(e);
            }
        return listms;
    }

    /**
     * extract matches without paraphrasing
     * @param inputtokens user input file tokens
     * @param tmsrctokens tm source tokens
     * @param tgtTM   tm target tokens
     * @param tgtinput expected target for user input file
     * @param outfile  output file in xml format 
     * @return 
     */
    
    static ArrayList<MatchStore>  extractMatch(ArrayList<Token []> inputtokens, ArrayList<Token []> tmsrctokens,ArrayList<String> tgtTM, ArrayList<String> tgtinput, String outfile){
        ArrayList<MatchStore> listms=new ArrayList();
        try{
            FileWriter fw = new FileWriter(outfile);
                int sno=-1;
                fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                fw.write("<document>");
                
                for(Token [] sen:inputtokens){
                    //   MatchStore m=new MatchStore(sen);
                    sno++;
                    System.err.print("Executing->"+sno+" :");
                    printtokens(sen);
                    
                    double maxsim=0.0;
                    int maxmatchid=0;
                    int index=0;
                    for(Token [] tmsen:tmsrctokens){
                        double sim=calcSimilarity(tmsen, sen);
                        // printtokens(tmsen);
                        // printtokens(sen);
                        //  System.out.println(" SIM:"+sim);
                        if(maxsim<sim){
                            maxsim=sim;
                            maxmatchid=index;
                        }
                        /* if(sim>TH){
                        m.add(tmsen);
                        fw.write(sim+" ||| ");
                        for(int i=0;i<tmsen.length;i++)
                        fw.write(tmsen[i].getText()+" ");
                        fw.write("\n");
                        }*/
                        index++;
                        
                    }
                    fw.write("<segment id=\""+sno+"\">");
                    for(int i=0;i<sen.length;i++)
                        fw.write(sen[i].getText()+" ");
                    fw.write('\n');
                    fw.write("<exptd>");
                    fw.write(tgtinput.get(sno));
                    fw.write("</exptd>\n");
                    String ms=Math.round(maxsim*100.0)/100.0 + "";
                    fw.write("<match score=\""+ms+"\" id=\""+maxmatchid+"\">");
                    Token [] bestmatch=tmsrctokens.get(maxmatchid);
                    String french=tgtTM.get(maxmatchid);
                    for (Token bestmatch1 : bestmatch) {
                        fw.write(bestmatch1.getText() + " ");
                    }
                    fw.write("</match>\n");
                    fw.write("<retvd lang=\"FR\">");
                    fw.write(french);fw.write("</retvd>\n");
                    fw.write("</segment>\n");
                    
                    //       listms.add(m);
                }
                   fw.write("</document>");
                   fw.close();
            
            }catch(IOException e){
                System.err.print(e);
            }
        return listms;
    }
    
    /**
     * extract matches with paraphrasing
     * @param inputtokens user input file tokens
     * @param tmsrctokens tm source tokens
     * @param tgtTM   tm target tokens
     * @param tgtinput expected target for user input file
     * @param outfile  output file in xml format 
     * @param flag  paraphrasing version 1 or 2
     * @return 
     */
    
 /*   static int extractMatch(char flag, ArrayList<Token []> inputtokens, ArrayList<SentencePP> tmsrcexttokens,ArrayList<String> tgtTM, ArrayList<String> tgtinput, String outfile){
        try{
            FileWriter fw = new FileWriter(outfile);
                int sno=-1;
                fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                fw.write("<document>");
                for(Token [] sen:inputtokens){
                    //   MatchStore m=new MatchStore(sen);
                    sno++;
                    System.out.print("Executing->"+sno+" :");
                    printtokens(sen);
                    
                    double maxsim=0.0;
                    int maxmatchid=0;
                    LdPPSPair bestmatch=null;
                    int index=0;
                    //System.out.print("Processing:");
                    int tmsno=0;
                    for(SentencePP tmsen:tmsrcexttokens){
                        // double sim=calcSimilarity(tmsen.get(), sen);
                        //System.out.println("tmsen:");
                        //tmsen.print();
                        System.out.print(++tmsno+"=");
                        LdPPSPair ldpair;
                        double sim;
                        if(flag=='a'){
                            LevenshteinDistance100ctd dc= new LevenshteinDistance100ctd();
                            ldpair = dc.compute(sen,tmsen.get());
                            if(ldpair==null)continue;
                            sim=calcSimilarityPP(sen.length,ldpair.length(),ldpair.getEditDistance());
                        }else {
                            System.err.println("All not implemented yet");
                            System.exit(1);
                            LevenshteinDistance100ctd dc= new LevenshteinDistance100ctd();
                            ldpair = dc.compute(sen,tmsen.get());
                            if(ldpair==null)continue;
                            sim=calcSimilarityPP(sen.length,ldpair.length(),ldpair.getEditDistance());                        
                        }
                        // printtokens(tmsen);
                        // printtokens(sen);
                        //  System.out.println(" SIM:"+sim);
                        if(maxsim<sim){
                            maxsim=sim;
                            maxmatchid=index;
                            bestmatch=ldpair;
                        }
                        /* if(sim>TH){
                        m.add(tmsen);
                        fw.write(sim+" ||| ");
                        for(int i=0;i<tmsen.length;i++)
                        fw.write(tmsen[i].getText()+" ");
                        fw.write("\n");
                        }*/
     /*                   index++;
                        
                    }
                    System.out.println();
                    fw.write("<segment id=\""+sno+"\">");
                    for (Token sen1 : sen) {
                        fw.write(sen1.getText() + " ");
                    }
                    fw.write('\n');
                    fw.write("<exptd>");
                    fw.write(tgtinput.get(sno));
                    fw.write("</exptd>\n");
                    String ms=Math.round(maxsim*100.0)/100.0 + "";
                    fw.write("<match score=\""+ms+"\" id=\""+maxmatchid+"\">");
                    //Token [] bestmatch=tmsrctokens.get(maxmatchid);
                    for (short i=0;i<bestmatch.length();i++) {
                        fw.write(bestmatch.getTokenAt(i).getText() + " ");
                    }
                    fw.write("</match>\n");
                    fw.write("<paraphrases>");
                    for(PPPair item : bestmatch.getMatchedParaphrases()){
                        fw.write("<pp index=\"");
                        fw.write(item.getLocation()+"\""+" text=\""+item.getParaphrase().getleft()+"\" "+"para=\""+item.getParaphrase().getright()+"\"");
                        fw.write("/>");
                    }
                    fw.write("</paraphrases>\n");
                    fw.write("<retvd lang=\"FR\">");
                    String french=tgtTM.get(maxmatchid);
                    fw.write(french);fw.write("</retvd>\n");
                    fw.write("<retvd lang=\"EN\">");
                            SentencePP spp=tmsrcexttokens.get(maxmatchid);
                            ExtToken [] english=spp.get();
                    for(ExtToken extk:english){
                        fw.write(extk.getToken().getText()+" ");
                    }
                    fw.write("</retvd>\n");
                    fw.write("</segment>\n");
                    
                    //       listms.add(m);
                }
                fw.write("</document>");
                fw.close();
            
            }catch(IOException e){
                System.err.print(e);
            }
        return 0;
    }
    */

    
    private static int efficientparaphrasing(boolean enabledouble,boolean placeholder, boolean paraphrasing, boolean filtering,  double lenTH,double beamTH, double tmTH,int nbest, String ppfilename,String tmsrcfilename,String tmtgtfilename,String inputfilename ,String inputtgtfilename,String outfile){
    
        long starttime=System.nanoTime();
        // read input source file
        ReadFile rf=new ReadFile(inputfilename,placeholder);
        ArrayList<Token []> inputtokens=rf.get();
        //read input target file
        ReadTargetFile rtf2=new ReadTargetFile(inputtgtfilename);
        ArrayList<String> tgtinput=rtf2.get(); 
        //read TM target file 
        ReadTargetFile rtf=new ReadTargetFile(tmtgtfilename);
        ArrayList<String> tgtTM=rtf.get();
        //read TM source file
        ReadFile rf2=new ReadFile(tmsrcfilename,placeholder);
        ArrayList<Token []> tmsrctokens=rf2.get();
        long endtime1;
        if(paraphrasing){
            
        // read PP dictionary and collect Paraphrases
        CollectPP cpp=new CollectPP(ppfilename, placeholder);
        HashMap<String, ArrayList<String> > ppdict=cpp.getPPDictionary();        
        System.err.print("PPDICT "+ppdict.entrySet().size());
        // Paraphrase translation memory
        ParaphraseTM cspp=new ParaphraseTM(tmsrctokens,ppdict);
        ArrayList<SentencePP> alspp=cspp.getSentencePP();
        usedPP=cspp.getUsedPPDictionary();
        // delete PP dictionary
        ppdict.clear();
        System.err.println("alspp finished");
        //alspp.get(200).print();
        System.err.println();
        
        endtime1=System.nanoTime();
        System.err.println("Total time in Collecting tokens and parphrases(Seconds)="+TimeUnit.SECONDS.convert(endtime1-starttime,TimeUnit.NANOSECONDS));
        
        if(!filtering){  //without filtering executing old code
                System.err.println("Error:Fltering is off, execution will be too slow");
                System.err.println("Calling ExtractMatch with filtering off");
             //   extractMatch('a',inputtokens,alspp,tgtTM,tgtinput,outfile);
                            System.err.println("NOT implemented");

        }else{   //with filtering     
        
        if(enabledouble){
            System.err.println("Calling: extractMatchDouble filtering:"+filtering+" placeholder:"+placeholder);
            System.err.println("NOT implemented");
           // extractMatchDouble(lenTH,beamTH, nbest,inputtokens,tmsrctokens, alspp,tgtTM,tgtinput,outfile);
        }
            else if(tmTH>0.0){
                System.err.println("Calling: extractMatchAboveTH filtering:"+filtering+" placeholder:"+placeholder);
                extractMatchAboveTH(lenTH,beamTH, tmTH, nbest,inputtokens,tmsrctokens, alspp,tgtTM,tgtinput,outfile);}
            else {
                System.err.println("Calling: extractTopMatchOnly filtering:"+filtering+" placeholder:"+placeholder);
                            System.err.println("NOT implemented");

               // extractTopMatchOnly(lenTH,beamTH, nbest,inputtokens,tmsrctokens, alspp,tgtTM,tgtinput,outfile);
            }
        }
        }else {    // without paraphrasing
            endtime1=System.nanoTime();
            if(!filtering){
                System.err.println("Total time in Collecting tokens (Seconds)="+TimeUnit.SECONDS.convert(endtime1-starttime,TimeUnit.NANOSECONDS));
                System.err.println("Calling ExtractMatch with filtering off, simple edit-distance, placeholder:"+placeholder);
                extractMatch(inputtokens,tmsrctokens,tgtTM,tgtinput,outfile);
            }else {
                System.err.println("Error:Simple ED match without filtering not implemented yet");
                System.exit(1);
            }
        
        }
        long endtime2=System.nanoTime();        
        System.err.println("Total time in EditdistancePP(Seconds)="+TimeUnit.SECONDS.convert(endtime2-endtime1,TimeUnit.NANOSECONDS));
        System.err.println("Complete time(Seconds)="+TimeUnit.SECONDS.convert(endtime2-starttime,TimeUnit.NANOSECONDS));

        return 0;
    }
   
    public static void main(String[] args) {
     
        boolean placeholder=false;
        boolean paraphrasing=false;
        boolean enabledouble=false;
        boolean filtering=true;
        int nbestsize=100;
        String  tmsrcfilename="";
        String ppfilename="";
        String tmtgtfilename="";
        String inputfilename="";
        String inputtgtfilename="";
        String outfilename="";
        double lenTH=50.0;
        double beamTH=35.0;
        double tmTH=70.0;
    /*    
        args=new String[20];
        args[0]="-bth";
        args[1]="35.0";
        args[2]="-lth";
        args[3]="49.0";
        args[4]="-nb";
        args[5]="100";
        args[6]="-tmth";
        args[7]="65.00";
        args[8]="-pp";
        args[9]="//Users//rohit//expert//corpusparaphrase//lexlallp+l.txt";
        args[10]="-tms";
        args[11]="//Users//rohit//expert//programs//corpus//test12//2011.en.txt";
        args[12]="-tmt";
        args[13]="//Users//rohit//expert//programs//corpus//test12//2011.fr.txt";
        args[14]="-ins";
        args[15]="//Users//rohit//expert//programs//corpus//test12//2013.en.txt";
        args[16]="-int";
        args[17]="//Users//rohit//expert//programs//corpus//test12//2013.fr.txt";
        args[18]="-o";
        args[19]="//Users//rohit//expert//programs//corpus//test12//2013tmp.xml";
      */ 
        for(int i=0;i<args.length;i++){
            
           // System.err.println("Processing:"+args[i]);
            if("-pholder".equals(args[i]))placeholder=true;
            else if("-off".equals(args[i]))filtering=false;
            else if("-tms".equals(args[i])){
                if(args.length<=i+1||args[i+1].startsWith("-")){
                    System.err.println("Please provide TM source file name");
                    System.exit(1);
                }else {tmsrcfilename=args[++i];}
            }
            else if("-pp".equals(args[i])){
                if(args.length<=i+1||args[i+1].startsWith("-")){
                    System.err.println("Please provide TM source file name");
                    System.exit(1);
                }else {ppfilename=args[++i];paraphrasing=true;}
            }
            else if("-pd".equals(args[i])){
                if(args.length<=i+1||args[i+1].startsWith("-")){
                    System.err.println("Please provide TM source file name");
                    System.exit(1);
                }else {ppfilename=args[++i];paraphrasing=true;enabledouble=true;}
            }
            else if("-tmt".equals(args[i])){
                if(args.length<=i+1||args[i+1].startsWith("-")){
                    System.err.println("Please provide TM target file name");
                }else {tmtgtfilename=args[++i];}
            }
            else if("-ins".equals(args[i])){
                if(args.length<=i+1||args[i+1].startsWith("-")){
                    System.err.println("Please provide input source file name");
                    System.exit(1);
                }else {inputfilename=args[++i];}
            }
            else if("-int".equals(args[i])){
                if(args.length<=i+1||args[i+1].startsWith("-")){
                    System.err.println("Please provide input target file name");
                    System.exit(1);
                }else {inputtgtfilename=args[++i];}
            }
            else if("-o".equals(args[i])){
                if(args.length<=i+1||args[i+1].startsWith("-")){
                    System.err.println("Please provide output file name");
                    System.exit(1);
                }else {outfilename=args[++i];}
            }
            else if("-lth".equals(args[i])){
                if(args.length<=i+1||args[i+1].startsWith("-")){
                    System.err.println("Please provide threshold for filtering based on length");
                    System.exit(1);
                }else {lenTH=Double.parseDouble(args[++i]);}
            }
            else if("-bth".equals(args[i])){
                if(args.length<=i+1||args[i+1].startsWith("-")){
                    System.err.println("Please provide threshold for filtering based on maximum gap allowed in edit-distance(beam th)");
                    System.exit(1);
                }else {beamTH=Double.parseDouble(args[++i]);}
            }
            else if("-tmth".equals(args[i])){
                if(args.length<=i+1||args[i+1].startsWith("-")){
                    System.err.println("Please provide cut off threshold for TM matching ");
                    System.exit(1);
                }else {tmTH=Double.parseDouble(args[++i]);}
            }
            else if("-nb".equals(args[i])){
                if(args.length<=i+1||args[i+1].startsWith("-")){
                    System.err.println("Please provide cut off threshold for TM matching ");
                    System.exit(1);
                }else {nbestsize=Integer.parseInt(args[++i]);}
            }
            else {
                    System.err.println("Invalid option "+args[i]+" at "+i+"th place");
                    System.exit(1);
            }
        }
                  
        System.err.println("Calling: efficientparaphrasing with following parameters:");
        System.err.println("enabledouble:"+enabledouble);
        System.err.println("placeholder:"+placeholder);
        System.err.println("paraphrasing:"+paraphrasing);
        System.err.println("filtering:"+filtering);
        System.err.println("lenTH:"+lenTH);
        System.err.println("beamTH:"+beamTH);
        System.err.println("tmTH:"+tmTH);
        System.err.println("nbestsize:"+nbestsize);
        System.err.println("ppfilename:"+ppfilename);
        System.err.println("tmsrcfilename:"+tmsrcfilename);
        System.err.println("tmtgtfilename:"+tmtgtfilename);
        System.err.println("inputfilename:"+inputfilename);
        System.err.println("inputtgtfilename:"+inputtgtfilename);
        System.err.println("outfilename:"+outfilename);
        efficientparaphrasing(enabledouble,placeholder,paraphrasing, filtering,lenTH,beamTH,tmTH,nbestsize,ppfilename,tmsrcfilename,tmtgtfilename,inputfilename ,inputtgtfilename,outfilename);        
      
       
    }
   
}
