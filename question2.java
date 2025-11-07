import java.util.*;
public class question2 {
    static int prec(char op){
        switch(op){
            case '!': return 4;
            case '&': return 3;
            case '|': return 2;
            case '>': return 1; 
            case '=': return 0; 
        }
        return -1;
    }
    static boolean isOp(char c){ return c=='!'||c=='&'||c=='|'||c=='>'||c=='='; }

    static List<String> toRPN(String s){
        List<String> out = new ArrayList<>();
        Stack<Character> st = new Stack<>();
        for(int i=0;i<s.length();){
            char c = s.charAt(i);
            if(c==' ') { i++; continue; }
            if(c=='p' || c=='q' || c=='r'){
                out.add(String.valueOf(c)); i++; continue;
            }
            if(c=='('){ st.push(c); i++; continue; }
            if(c==')'){
                while(!st.isEmpty() && st.peek()!='(') out.add(String.valueOf(st.pop()));
                if(!st.isEmpty() && st.peek()=='(') st.pop();
                i++; continue;
            }
            if(isOp(c)){
                while(!st.isEmpty() && isOp(st.peek()) &&
                      ((c!='!' && prec(st.peek())>=prec(c)) || (c=='!' && prec(st.peek())>prec(c)))){
                    out.add(String.valueOf(st.pop()));
                }
                st.push(c); i++; continue;
            }
            i++;
        }
        while(!st.isEmpty()) out.add(String.valueOf(st.pop()));
        return out;
    }
    static boolean evalRPN(List<String> rpn, boolean p, boolean q, boolean r){
        Stack<Boolean> st = new Stack<>();
        for(String tok : rpn){
            if(tok.equals("p")) st.push(p);
            else if(tok.equals("q")) st.push(q);
            else if(tok.equals("r")) st.push(r);
            else {
                char op = tok.charAt(0);
                if(op=='!'){
                    boolean a = st.pop();
                    st.push(!a);
                } else {
                    boolean b = st.pop();
                    boolean a = st.pop();
                    boolean res=false;
                    switch(op){
                        case '&': res = a && b; break;
                        case '|': res = a || b; break;
                        case '>': res = (!a) || b; break;
                        case '=': res = (a==b); break;
                    }
                    st.push(res);}}}
        return st.pop();
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter formula (use p,q,r and operators !,&,|,>,= ):");
        String line = sc.nextLine().trim();
        List<String> rpn = toRPN(line);
        System.out.println("Truth table:");
        boolean anyTrue=false, anyFalse=false;
        String satAssign = null;
        for(int pi=0; pi<2; pi++){
            for(int qi=0; qi<2; qi++){
                for(int ri=0; ri<2; ri++){
                    boolean val = evalRPN(rpn, pi==1, qi==1, ri==1);
                    System.out.printf("p=%d q=%d r=%d -> %d\n", pi, qi, ri, val?1:0);
                    if(val) { anyTrue=true; if(satAssign==null) satAssign = "p="+pi+" q="+qi+" r="+ri; }
                    else anyFalse=true;
                }}}
        if(anyTrue && !anyFalse) System.out.println("Formula is a TAUTOLOGY");
        else if(!anyTrue && anyFalse) System.out.println("Formula is a CONTRADICTION");
        else System.out.println("Formula is SATISFIABLE but not tautology. Example: " + satAssign);
        sc.close();
    }
}
