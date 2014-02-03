
package edmatch.data;
import java.util.ArrayList;

public class ExtToken{

    Token tk;
    ArrayList<Paraphrase> al= new ArrayList();
    //ArrayList<hash> al2=new ArrayList();
   public ExtToken(Token tk, ArrayList<Paraphrase> al){
        this.tk=tk;
        this.al=al;
    }
  public ExtToken(Token tk){
        this.tk=tk;
        al=null; //no paraphrase exists
    }
   public ExtToken(){
        tk=null;
        al=null;
    }
    int addParaphrase(Paraphrase pp){
        al.add(pp);
        return al.size();
    }
   public ArrayList<Paraphrase> getpplist(){
        return al;
    }
   public Token getToken(){
        return tk;
    }
   public void print(){
        System.out.print(tk.getText());
        for(Paraphrase p:al){
            System.out.print(p.lensrc+" "+p.getleft()+" "+p.lensrcpp+" "+p.getright());
        }
    }
}