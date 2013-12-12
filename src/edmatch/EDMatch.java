/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edmatch;

import java.io.FileWriter;
import java.util.ArrayList;
import java.lang.Exception;
import java.io.IOException;

/**
 *
 * @author rohit
 */
public class EDMatch {

    /**
     * @param args the command line arguments
     */
    
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
    
    static void printtokens(Token [] tks){
        for(int i=0;i<tks.length;i++){
            System.out.print(tks[i].getText()+" ");
        }
        System.out.println();
    }
    
    static ArrayList<MatchStore> extractMatch(ArrayList<Token []> inputtokens, ArrayList<Token []> tmsrctokens,ArrayList<String> tgtTM, ArrayList<String> tgtinput){
        ArrayList<MatchStore> listms=new ArrayList();
        try{
             FileWriter fw=new FileWriter("//Users//rohit//expert//programs//corpus//output//enfr2013releaseutf8.en.topmatch.500.xml");
             int sno=-1;
            for(Token [] sen:inputtokens){
             //   MatchStore m=new MatchStore(sen);
                sno++;
                System.out.print("Executing->"+sno+" :");
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
                fw.write("<segment id="+sno+">");
                for(int i=0;i<sen.length;i++)
                    fw.write(sen[i].getText()+" ");
                fw.write('\n');
                fw.write("<exptd>");
                fw.write(tgtinput.get(sno));
                fw.write("</exptd>\n");
                String ms=maxsim+"";
                fw.write("<match score="+ms.substring(0, 4)+" id="+maxmatchid+">");
                        Token [] bestmatch=tmsrctokens.get(maxmatchid);
                        String french=tgtTM.get(maxmatchid);
                        for(int i=0;i<bestmatch.length;i++)
                            fw.write(bestmatch[i].getText()+" ");
                        fw.write("</match>\n");
                        fw.write("<retvd lang=FR>");
                        fw.write(french);fw.write("</trans>\n");
                        fw.write("</segment>\n");
                
         //       listms.add(m);
            }
            fw.close();
            }catch(IOException e){
                System.err.print(e);
            }
        return listms;
    }
    
    
    public static void main(String[] args) {
      //  double TH=90.0; // Threshold for fuzzy matching
        String inputfilename="//Users//rohit//expert//programs//corpus//enfr2013releaseutf8.en.fil.txt.500";
        String tmsrcfilename="//Users//rohit//expert//programs//corpus//enfr2011_710_12releaseutf8.en.fil.txt";
       // String tmsrcfilename="//Users//rohit//expert//programs//levdistance//enfrppsTMsrc.txt";
        String tmtgtfilename="//Users//rohit//expert//programs//corpus//enfr2011_710_12releaseutf8.fr.fil.txt";
        String inputtgtfilename="//Users//rohit//expert//programs//corpus//enfr2013releaseutf8.fr.fil.txt.500";
        ReadTargetFile rtf=new ReadTargetFile(tmtgtfilename);
        ArrayList<String> tgtTM=rtf.get();
        ReadTargetFile rtf2=new ReadTargetFile(inputtgtfilename);
        ArrayList<String> tgtinput=rtf2.get();
        
        ReadFile rf=new ReadFile(inputfilename);
        ArrayList<Token []> inputtokens=rf.get();
        ReadFile rf2=new ReadFile(tmsrcfilename);
        ArrayList<Token []> tmsrctokens=rf2.get();
       // ArrayList<MatchStore> matches= new ArrayList();
        extractMatch(inputtokens,tmsrctokens,tgtTM,tgtinput);
        
    }
    
}
