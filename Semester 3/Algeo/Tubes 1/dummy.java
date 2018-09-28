import java.util.*;

class Matriks{
 	double[][] mat = new double[105][105];
	int persamaan, variabel;

	void inputMatriks(Matriks M){
		Scanner in = new Scanner(System.in);
		System.out.print("Masukan banyak persamaan: ");
		M.persamaan = in.nextInt();
		System.out.print("Masukan banyak variabel: ");
		M.variabel = in.nextInt();
		System.out.println("Silahkan masukan persamaan tersebut");
		for (int i = 1; i <= M.persamaan; i++)
			for (int j = 1; j <= M.variabel+1; j++)
				M.mat[i][j] = in.nextDouble();
	}

	void tukarBaris(Matriks M, int x,int y){
		double[] temp = new double[105];
		for (int i = 1; i <= M.variabel+1; i++)
			temp[i] = M.mat[x][i];
		for (int i = 1; i <= M.variabel+1; i++){
			M.mat[x][i] = M.mat[y][i];
			M.mat[y][i] = temp[i];
		}
	}

	void tulisMatriks(Matriks M){
		for (int i = 1; i <= M.persamaan; i++){
			for (int j = 1; j <= M.variabel+1; j++){
				System.out.print(M.mat[i][j] + " ");
			}
			System.out.println();
		}
	}

	boolean isAllzero(Matriks M, int x){
		for (int i = 1; i <= M.variabel; i++){
			if (M.mat[x][i] != 0) return false;
		}
		return true;
	}
}

class Tubes{
	public static void EliminasiGauss(Matriks M, double[] solusi){
		int batasBaris = M.persamaan;
		int batasKolom = M.variabel+1;
		int baris = 1;
		int kolom = 1;
		while (baris <= batasBaris && kolom <= batasKolom){
			int indeksMaks=-1;
			for (int i = baris; i <= batasBaris && indeksMaks == -1; i++){
				if ((M.mat[i][kolom]) != 0){
					indeksMaks = i;
				}
			}
			if (indeksMaks == -1)
				kolom++;
			else{
				M.tukarBaris(M, baris, indeksMaks);
				double bil = M.mat[baris][kolom];
				// System.out.println(baris + " baris " + bil);
				for (int i = kolom; i <= batasKolom; i++){
					M.mat[baris][i] /= bil;
				}
				for (int i = baris+1; i <= batasBaris; i++){
					double ratio = (-1*M.mat[i][kolom]) / M.mat[baris][kolom];
					for (int j = kolom; j <= batasKolom; j++)
						M.mat[i][j] += ratio*M.mat[baris][j];
				}
				baris++;
				kolom++;
			}
		}
		// M.tulisMatriks(M);
		boolean isSolutionExist = true;
		for (int i = 1; i <= baris && isSolutionExist; i++){
			if (M.isAllzero(M, i) && M.mat[i][batasKolom] != 0) {
				isSolutionExist = false;
			}
		}
		if (isSolutionExist) {
			M.tulisMatriks(M);
			GaussJordan(M, solusi);
		}else{
			System.out.println("No solution exist for that equations!");
		}
	}
	public static void GaussJordan(Matriks M,double[] solusi){
		int batasBaris = M.persamaan;
		int batasKolom = M.variabel+1;
		int baris = 1;
		int kolom = 1;
		while (baris <= batasBaris && kolom <= batasKolom){
			int indeksMaks=-1;
			for (int i = baris; i <= batasBaris && indeksMaks == -1; i++){
				if ((M.mat[i][kolom]) != 0){
					indeksMaks = i;
				}
			}
			if (indeksMaks == -1)
				kolom++;
			else{
				M.tukarBaris(M, baris, indeksMaks);
				double bil = M.mat[baris][kolom];
				System.out.println(baris + " baris " + bil);
				for (int i = kolom; i <= batasKolom; i++){
					M.mat[baris][i] /= bil;
				}
				for (int i = 1; i <= batasBaris; i++){
					if (i!=baris){
						double ratio = (-1*M.mat[i][kolom]) / M.mat[baris][kolom];
						for (int j = kolom; j <= batasKolom; j++)
							M.mat[i][j] += ratio*M.mat[baris][j];
					}
				}
				baris++;
				kolom++;
			}
		}
		System.out.println("Matriks RREFnya adalah ");
		M.tulisMatriks(M);
		int rowzero = 0;
		for (int i = batasBaris; i >= 1; i--){
			if (M.isAllzero(M,i)) rowzero++;
		}
		if (batasBaris-rowzero == batasKolom-1) {
			for (int i = 1; i <= batasBaris; i++){
					solusi[i] = M.mat[i][batasKolom];
			}
		}else{
			boolean[] sudah = new boolean[105];
			for (int i = 1; i <= batasKolom-1; i++)
				sudah[i] = false;
			for (int i = 1; i <= batasBaris; i++){
					int j = 1;
					while (j < batasKolom && M.mat[i][j] == 0) j++;
					if (j >= batasKolom || i > batasBaris) {
						// System.out.println("X["+i+"] = X["+i+"]");
						// cek[i] = false;
						continue;
					}
					sudah[j] = true;
					System.out.print("X["+j+"] = ");
					boolean pertamaOutput = true;
					for (int k = j+1; k <= batasKolom-1; k++){
						if (M.mat[i][k] != 0) {
							if (pertamaOutput){
								System.out.print("("+ -1*M.mat[i][k] +")"+ "*X["+k+"]");
								pertamaOutput = false;
							}else{
								System.out.print(" + "+"("+ -1*M.mat[i][k] +")"+ "*X["+k+"]");
							}
						} 
					}
					if (M.mat[i][batasKolom] != 0 && !pertamaOutput) System.out.println(" + ("+ -1*M.mat[i][batasKolom] +")");
					else if (M.mat[i][batasKolom] != 0) System.out.println(M.mat[i][batasKolom]);
					else System.out.println();
			}
			for (int i = 1; i <= batasKolom-1; i++){
				if (!sudah[i]){
					System.out.println("X["+i+"] = X["+i+"]");
				}
			}

		}
	}
	public static double hitungFungsi(double[] solusi, int derajat, double x){
		double res = 0;
		for (int i = 1; i <= derajat+1; i++){
			res += Math.pow(x, i-1)*solusi[i];
		}
		return res;
	}

	public static void interpolasi(){
		/* KAMUS */
		Matriks t = new Matriks();
		Matriks spl = new Matriks();
		double xtaksir;
		double[] solusi = new double[105];
		// Tubes tbs = new Tubes();

		/* ALGORITMA */
		/* Input derajat interpolasi */
		Scanner in = new Scanner(System.in);
		System.out.print("Derajat interpolasi : ");
		int n = in.nextInt();

		/* Input banyak titik */
		System.out.println("Input titik :");
		for(int i = 1; i <= n + 1; i++) {
			double x = in.nextDouble();
			double y = in.nextDouble();
			t.mat[i][1] = x;
			t.mat[i][2] = y;
		}

		System.out.print("Nilai yang akan ditaksir : ");
		xtaksir = in.nextDouble();
		/* Pindahkan ke dalam bentuk SPL */
		for(int i = 1; i <= n + 1; i++) {
			for(int j = 1; j <= n + 2; j++) {
				if(j == 1) {
					spl.mat[i][j] = 1;
				} else if(j == n + 2) {
					spl.mat[i][j] = t.mat[i][2];
				} else {
					spl.mat[i][j] = Math.pow(t.mat[i][1], j - 1);
				}
			}
		}
		spl.persamaan = n+1;
		spl.variabel = n+1;

		/* Tulis matriks SPL */
		for(int i = 1; i <= n + 1; i++) {
			for(int j = 1; j <= n + 2; j++) {
				System.out.printf("%f", spl.mat[i][j]);
				if(j != n + 2) {
					System.out.print(" ");
				} else if(i != n + 1) {
					System.out.println();
				}
			}
		}
		System.out.println();
		EliminasiGauss(spl, solusi);
		System.out.println("Maka nilai taksiran fungsi pada x = "+ xtaksir + " adalah " + hitungFungsi(solusi, n, xtaksir));
	}

	public static void main(String[] args){
		Matriks M = new Matriks();
		double[] solusi = new double[1005];
		M.inputMatriks(M);
		EliminasiGauss(M, solusi);
		// for (int i = 1; i <= M.persamaan; i++){
		// 	System.out.println("X["+i+"] = " + solusi[i]);
		// }
		// interpolasi();
	}
}