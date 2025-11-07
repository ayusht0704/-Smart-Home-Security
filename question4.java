import java.util.*;
public class question4 {
    static boolean[][] warshall(boolean[][] r){
        int n=r.length;
        boolean[][] c=new boolean[n][n];
        for(int i=0;i<n;i++) for(int j=0;j<n;j++) c[i][j]=r[i][j];
        for(int k=0;k<n;k++) for(int i=0;i<n;i++) if(c[i][k]) for(int j=0;j<n;j++) if(c[k][j]) c[i][j]=true;
        return c;
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt(), m=sc.nextInt();
        boolean[][] r = new boolean[n][n];
        for(int i=0;i<m;i++){ int a=sc.nextInt(), b=sc.nextInt(); r[a][b]=true; }
        boolean[][] reach = warshall(r);
        System.out.println("Hasse edges (covers):");
        for(int a=0;a<n;a++) for(int b=0;b<n;b++){
            if(a==b) continue;
            if(reach[a][b]){
                boolean covered=true;
                for(int c=0;c<n;c++) if(c!=a && c!=b && reach[a][c] && reach[c][b]) covered=false;
                if(covered) System.out.println(a + " -> " + b);
            }}
        boolean isLattice = true;
        for(int x=0;x<n && isLattice;x++) for(int y=0;y<n && isLattice;y++){
            List<Integer> upp = new ArrayList<>();
            for(int u=0;u<n;u++) if(reach[x][u] && reach[y][u]) upp.add(u);
            List<Integer> lubs = new ArrayList<>();
            for(int a:upp){
                boolean minimal=true;
                for(int b:upp) if(a!=b && reach[b][a]) { minimal=false; break; }
                if(minimal) lubs.add(a); }
            if(lubs.size()!=1){ isLattice=false; System.out.println("No unique join for "+x+","+y+" -> candidates: "+lubs); break; }
            List<Integer> low = new ArrayList<>();
            for(int u=0;u<n;u++) if(reach[u][x] && reach[u][y]) low.add(u);
            List<Integer> glbs = new ArrayList<>();
            for(int a:low){
                boolean maximal=true;
                for(int b:low) if(a!=b && reach[a][b]) { maximal=false; break; }
                if(maximal) glbs.add(a);
            }
            if(glbs.size()!=1){ isLattice=false; System.out.println("No unique meet for "+x+","+y+" -> candidates: "+glbs); break; }
        }
        if(isLattice) System.out.println("The poset is a lattice.");
        else System.out.println("Not a lattice.");
        sc.close();}}
