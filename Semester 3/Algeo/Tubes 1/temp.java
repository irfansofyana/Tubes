import java.util.*;

class Matriks{
	double[][] mat = new double[105][105];
	int persamaan, variabel;

	Matriks inputMatriks(){
		Matriks ret = new Matriks();
		Scanner in = new Scanner(System.in);
		System.out.print("Masukan banyak persamaan: ");
		ret.persamaan = in.nextInt();
		System.out.print("Masukan banyak variabel: ");
		ret.variabel = in.nextInt();

		System.out.println("Silahkan masukan persamaan tersebut");
		for (int i = 1; i <= ret.persamaan; i++)
			for (int j = 1; j <= ret.variabel+1; j++)
				ret.mat[i][j] = in.nextDouble();
		return ret;
	}

}

class EliminasiGauss{
	public static void Swap(Matriks M,int i,int j){
		double[] arr= new double[105];
		for (int a = 1; a <= M.variabel+1; a++)
			arr[a] = M.mat[i][a];
		for (int a = 1; a <= M.variabel+1; a++){
			M.mat[i][a] = M.mat[j][a];
			M.mat[j][a] = arr[a];
		}
	}

	void Solve(Matriks M){
		int mini;
		int persamaan = M.persamaan;
		int variabel = M.variabel;
		int idx = 1;
		double[] arr= new double[105];
		boolean isExistSolution = true;
		if (persamaan < variabel+1) mini = persamaan;
		else mini = variabel+1;
		for (int i = 1; i <= persamaan; i++){
			for (int j = i+1; j <= persamaan; j++){
				int f1 = -1;
				for (int k = 1; k <= variabel+1 && f1 == -1; k++)
					if (M.mat[i][k] != 0) f1 = k;
				int f2 = -1;
				for (int k = 1; k <= variabel+1 && f2 == -1; k++)
					if (M.mat[i][k] != 0) f2 = k;

				if (f1 == -1 || f2 < f1){
					for (int a = 1; a <= M.variabel+1; a++)
					arr[a] = M.mat[i][a];
					for (int a = 1; a <= M.variabel+1; a++){
						M.mat[i][a] = M.mat[j][a];
						M.mat[j][a] = arr[a];
					}
				}
			}
		}
		for (int i = 1; i <= persamaan; i++){
			boolean test = true;
			for (int j = 1; j <= variabel && test; j++){
				if (M.mat[i][j] != 0) test = false;
			}
			if (test && M.mat[i][variabel+1] != 0) isExistSolution = false;
		}
		int idxAllzero = -1;
		for (int i = persamaan; i >= 1 && idxAllzero == -1; i--){
			boolean cek = true;
			for (int j = 1; j <= variabel && cek; j++){
				if (M.mat[i][j] != 0) cek = false;
			}
			if (!cek) idxAllzero = i;
		}
		if (!isExistSolution) System.out.println("Tidak ada solusi");
		else {
			for (int i = 1; i <= mini; i++){
				int j = i;
				while (j <= persamaan && M.mat[j][i] == 0) j++;
				if (j > persamaan) { 
					continue;
				}

				for (int a = 1; a <= M.variabel+1; a++)
					arr[a] = M.mat[i][a];
				for (int a = 1; a <= M.variabel+1; a++){
					M.mat[i][a] = M.mat[j][a];
					M.mat[j][a] = arr[a];
				}

				double bil = M.mat[i][i];
				for (int a = 1; a <= variabel+1; a++)
					M.mat[i][a] /= bil;

				for (int a = i+1; a <= persamaan; a++){
					double ratio = (-1*M.mat[a][i])/M.mat[i][i];
					int cnt = 0;
					for (int b = 1; b <= variabel+1; b++){
						M.mat[a][b] += ratio*M.mat[i][b];
						if (M.mat[a][b] == 0) cnt++;
					}
					if (cnt == variabel+1 && idxAllzero > a){
						for (int aa = 1; aa <= M.variabel+1; aa++)
							arr[aa] = M.mat[a][aa];
						for (int aa = 1; aa <= M.variabel+1; aa++){
							M.mat[a][aa] = M.mat[idxAllzero][aa];
							M.mat[idxAllzero][aa] = arr[aa];
						}
						idxAllzero--;
					}
				}
			}
			for (int i = 1; i <= persamaan; i++){
				boolean test = true;
				for (int j = 1; j <= variabel && test; j++){
					if (M.mat[i][j] != 0) test = false;
				}
				if (test && M.mat[i][variabel+1] != 0) isExistSolution = false;
			}
			if (!isExistSolution) System.out.println("Tidak ada solusi");
			else {
				System.out.println("Echelon Matrixnya adalah ");
				for (int a = 1; a <= persamaan; a++){
					for (int b = 1; b <= variabel+1; b++){
						System.out.print(M.mat[a][b]+" ");
					}
					System.out.println();
				}
				/*
				double[] solusi = new double[105];
				int indeks = variabel;
				for (int i = persamaan; i >= 1; i--){
					double sum = 0;
					for (int j = variabel; j > indeks; j--) 
						sum += solusi[j]*M.mat[i][j];
					sum = M.mat[i][variabel+1] - sum;
					solusi[indeks] = sum / M.mat[i][indeks];
					indeks--;
				}
				for (int i = 1; i <= variabel; i++){
					System.out.println("Nilai X[" + i + "] = " + solusi[i]);
				}*/
				for (int i = persamaan; i >= 1; i--){
					int j = 1;
					while (j <= variabel && M.mat[i][j] == 0) j++;
					if (j > variabel) continue;
					for (int k = i-1; k >= 1; k--){
						double ratio = -M.mat[k][j]/M.mat[i][j];
						for (int a = 1; a <= variabel+1; a++)
							M.mat[k][a] += ratio*M.mat[i][a];
					}
				}
				System.out.println("Row Reduced Echelon Form Matrixnya adalah ");
				for (int i = 1; i <= persamaan; i++){
					for (int j = 1; j <= variabel+1; j++){
						System.out.print(M.mat[i][j] + " ");
					}
					System.out.println();
				}
				for (int i = 1; i <= persamaan; i++){
					int j = 1;
					while (j <= variabel && M.mat[i][j] == 0) j++;
					if (j > variabel) {
						System.out.println("X["+i+"] = X["+i+"]");
						continue;
					}
					System.out.print("X["+i+"] = ");
					for (int k = j+1; k <= variabel; k++){
						if (M.mat[i][k] != 0) {
							System.out.print(-1*M.mat[i][k] + "*X["+k+"] + ");
						} 
					}
					System.out.println(M.mat[i][variabel+1]);
				}
			}
		}
	}
}

class driver{
	public static void main(String[] args){
		Matriks M = new Matriks();
		Matriks matriks;
		matriks = M.inputMatriks();
		EliminasiGauss E = new EliminasiGauss();
		E.Solve(matriks);
	}
}