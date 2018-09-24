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
	public static void EliminasiGauss(Matriks M){
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
			GaussJordan(M);
		}else{
			System.out.println("No solution exist for that equations!");
		}
	}
	public static void GaussJordan(Matriks M){
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
		for (int i = 1; i <= batasBaris; i++){
			int j = 1;
			while (j <= batasKolom && M.mat[i][j] == 0) j++;
			if (j > batasKolom) {
				System.out.println("X["+i+"] = X["+i+"]");
				continue;
			}
			System.out.print("X["+i+"] = ");
			for (int k = j+1; k <= batasKolom-1; k++){
				if (M.mat[i][k] != 0) {
					System.out.print(-1*M.mat[i][k] + "*X["+k+"] + ");
				} 
			}
			System.out.println(M.mat[i][batasKolom]);
		}
	}
	public static void main(String[] args){
		Matriks M = new Matriks();
		M.inputMatriks(M);
		EliminasiGauss(M);
	}
}