
package edmatch.data;
import java.util.ArrayList;

public class ExtToken{

    Token tk;
    String ppalkey;
    
    //ArrayList<Paraphrase> al= new ArrayList();
    //ArrayList<hash> al2=new ArrayList();
  /* public ExtToken(Token tk, ArrayList<Paraphrase> al){
        this.tk=tk;
        this.al=al;
    }*/
   public ExtToken(Token tk, String ppalkey){
        this.tk=tk;
        this.ppalkey=ppalkey;
    }
  public ExtToken(Token tk){
        this.tk=tk;
        ppalkey=null; //no paraphrase exists
    }
   public ExtToken(){
        tk=null;
        ppalkey=null;
    }
    /*int addParaphrase(Paraphrase pp){
        al.add(pp);
        return al.size();
    }*/
   /*public ArrayList<Paraphrase> getpplist(){
        return al;
    }*/
   public Token getToken(){
        return tk;
    }
   public String getKey(){
       return ppalkey;
   }
   public void print(){
        System.out.print(tk.getText());
        /*for(Paraphrase p:al){
            System.out.print(p.lensrc+" "+p.getleft()+" "+p.lensrcpp+" "+p.getright());
        }*/
    }
}