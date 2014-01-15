
package edmatch;
import java.util.ArrayList;

class ExtToken{

    Token tk;
    ArrayList<Paraphrase> al= new ArrayList();
    ExtToken(Token tk, ArrayList<Paraphrase> al){
        this.tk=tk;
        this.al=al;
    }
    ExtToken(Token tk){
        this.tk=tk;
        al=null; //no paraphrase exists
    }
    ExtToken(){
        tk=null;
        al=null;
    }
    int addParaphrase(Paraphrase pp){
        al.add(pp);
        return al.size();
    }
    ArrayList<Paraphrase> getpplist(){
        return al;
    }
    Token getToken(){
        return tk;
    }
    void print(){
        System.out.print(tk.getText());
        for(Paraphrase p:al){
            System.out.print(p.lensrc+" "+p.getleft()+" "+p.lensrcpp+" "+p.getright());
        }
    }
}