import java.util.*;
import java.io.*;

class Matriks {
	public int baris;
	public int kolom;
	public double[][] M;

	/* GETTER */
	public int GetBaris() {
		//mendapatkan jumlah baris pada matriks augmented SPL
		return baris;
	}
	public int GetKolom() {
		//mendapatkan jumlah kolom pada matriks augmented SPL
		return kolom;
	}
	public double GetElmt(int m, int n) {
		//mendapatkan elemen dari matriks augmented SPL
		return M[m][n];
	}

	/* SETTER */
	public void SetBaris(int x) {
		//set baris matriks augmented menjadi x
		this.baris = x;
	}
	public void SetKolom(int y) {
		//set kolom matriks augmented menjadi x
		this.kolom = y;
	}
	public void SetElmt(int x, int y, double z) {
		//set elemen matriks augmented menjadi z
		this.M[x][y] = z;
	}

	/* KONSTRUKTOR */
	Matriks() {
		M = new double[105][105];
	}

	Matriks(int m, int n) {
		this.SetBaris(m);
		this.SetKolom(n);
		M = new double[m+1][n+1];
	}

	/* I/O */
	public void BacaMATRIKS(File inputfile) { //Baca Matriks dari File Eksternal
		int brs = 0, klm = 0;

		try 
		{
			Scanner in = new Scanner(inputfile);
			while (in.hasNextLine()) {
				String line = in.nextLine();
				brs++;

				Scanner n = new Scanner(line);
				while(n.hasNextDouble() && brs == 1) {
					n.nextDouble();
					klm++;
				}
			}

			in.close();
			in = new Scanner(inputfile);
			this.SetBaris(brs); this.SetKolom(klm);
			for (int i = 1; i <= brs; i++) {
				for (int j = 1; j <= klm; j++) {
					SetElmt(i,j,in.nextDouble());
				}
			}
		}
		catch (FileNotFoundException ex) {
			System.out.println("File tidak ditemukan");
		}
}

	public void BacaMATRIKS() { //Baca Matriks dari keyboard		
		Scanner in = new Scanner(System.in);
		for (int i = 1; i<=this.GetBaris(); i++) {
			for (int j = 1; j<=this.GetKolom(); j++) {
				double x = in.nextDouble();
				SetElmt(i,j,x);
			}
		} 
	} 

	public void TulisMATRIKS() { //Tulis Matriks dari keyboard
		for (int i = 1; i<=this.GetBaris(); i++) {
			for (int j = 1; j<=this.GetKolom(); j++) {
				System.out.print(this.GetElmt(i,j) + " ");
			}
			System.out.println();
		} 
	}

	public void tukarBaris(int x,int y){	//prosedur untuk menukar baris x dan y pada sebuah matriks
		double temp;
		for (int i = 1; i <= this.kolom; i++) {
			temp = this.GetElmt(x,i);
			SetElmt(x, i, this.GetElmt(y,i));
			SetElmt(y, i, temp);
		}
	} 

	public boolean isAllzero(int x) {		
		//fungsi untuk mengecek apakah baris x pada sebuah matriks
		//adalah row zero atau tidak 
		for (int i = 1; i <= this.kolom-1; i++){
			if (this.GetElmt(x,i) != 0) return false;
		}
		return true;
	}
}

class Solusi extends Matriks {

	// Prosedur untuk menuliskan solusi SPL
	// Solusi dari SPL sendiri sudah dituliskan ke dalam array dua dimensi "solusi"

	public void TulisSOLUSI(double[][] solusi, int variabel){	
		System.out.println("Solusi dari persamaan tersebut adalah: ");
		for (int i = 1; i <= variabel; i++){
			System.out.print("X[" + i + "] = ");
			boolean cetakFirst = true;
			for (int j = 1; j <= variabel; j++){
				if (solusi[i][j] != 0) {
					if (cetakFirst) {
						cetakFirst = false;
						System.out.printf("(%.4f)T[%d]",solusi[i][j],j);
					}
					else System.out.printf(" + (%.4f)T[%d]",solusi[i][j],j);
				}
			}
			if (solusi[i][variabel+1] != 0) {
				if (cetakFirst) System.out.println(solusi[i][variabel+1]);
				else System.out.printf(" + (%.4f)%n",solusi[i][variabel+1]);
			}
			else {
				if (cetakFirst) System.out.println((double)0);
				else System.out.println();
			}	
		}
	}

	//Prosedur eliminasi gauss
	//Matriks M akan menjadi echelon matriks dan array dua dimensi solusi akan berisi solusi dari SPL
	//adaSolusi akan memberikan informasi apakah suatu persamaan memiliki solusi tunggal, solusi parametrik, atau tidak ada solusi
	//adaSolusi[0] = 0 -> SPL tidak memiliki solusi
	//adaSolusi[0] = 1 -> SPL memiliki solusi tunggal
	//adaSolusi[0] = 2 -> SPL memiliki solusi parametrik
	/* array solusi[][] akan berisi solusi dari SPL yang ada pada matriks M
	Misal array dua dimensi solusi tersebut adalah:
			1		2		3		4		5
	1		0   2   2   1  10
	2		0   0   1   3  12
	3   0   0   0   4  5
	4   0   0   0   0  0

	Maka : 
	X[4] = 5
	X[2] = X[3] + 3X[4] + 12 = X[3] + 27
	X[3] = X[3]
	X[1] = 2X[2] + 2X[3] + X[4] + 10 = 2X[3] + 54 + 2X[3] + 5 + 10 = 4X[3] + 69
	*/
	public void EliminasiGauss(Matriks M, double[][] solusi, int[] adaSolusi){
		int batasBaris = M.GetBaris();
		int batasKolom = M.GetKolom();
		
		int baris = 1;	//inisialisasi pivot baris
		int kolom = 1;	//inisialisasi pivot kolom

		while (baris <= batasBaris && kolom <= batasKolom){
			int indeksMaks=-1;
			//cari indeks pertama yang tidak nol
			for (int i = baris; i <= batasBaris && indeksMaks == -1; i++){
				if ((M.GetElmt(i, kolom)) != 0){
					indeksMaks = i;
				}
			}
			if (indeksMaks == -1)	//tidak ada bilangan non zero pada kolom ini, lanjut pada kolom berikutnya
				kolom++;
			else{
				M.tukarBaris(baris, indeksMaks); //tukar baris (OBE)
				
				//bagi semua bilangan pada pivot baris dengan bilangan non zero pertamanya agar leadingnya menjadi 1
				double bil = M.GetElmt(baris, kolom);
				for (int i = kolom; i <= batasKolom; i++){
					M.SetElmt(baris, i, M.GetElmt(baris,i)/bil);
				}

				//buat semua bilangan yang ada pada pivot kolom dan di bawah pivot baris menjadi 0
				//lakukan juga untuk semua bilangan yang tersisa pada baris di bawah pivot baris sekarang
				for (int i = baris+1; i <= batasBaris; i++){
					double ratio = (-1*M.GetElmt(i, kolom)) / M.GetElmt(baris,kolom);
					for (int j = kolom; j <= batasKolom; j++)
						M.SetElmt(i, j, M.GetElmt(i, j) + ratio*M.GetElmt(baris, j));
				}

				//lanjutkan pengubahan untuk baris dan kolom selanjutnya
				baris++;
				kolom++;
			}
		}

		//cek apakah ada solusi atau tidak
		boolean isSolutionExist = true;
		//variabel untuk menampung banyak baris nol
		int rowzero = 0;
		for (int i = 1; i <= batasBaris; i++){
			//jika terdapat row zero tetapi hasil dari bilangan tersebut non zero, maka tidak ada solusi
			if (M.isAllzero(i) && M.GetElmt(i,batasKolom) != 0) {
				isSolutionExist = false;
			}
			//hitung banyaknya row zero pada matriks echelon
			if (M.isAllzero(i)) rowzero++;
		}
		if (!isSolutionExist) {
			//SPL tidak memiliki solusi
			adaSolusi[0] = 0;
		}
		else {
			//SPL memiliki solusi tunggal
			if (batasBaris-rowzero == batasKolom-1) adaSolusi[0] = 1;
			//SPL memiliki solusi parametrik
			else adaSolusi[0] = 2;
			
			//variabel untuk mendapatkan solusi SPL dengan gauss
			//first pivot adalah tanda sudah ditemukannya baris non-zero atau tidak
			//pivot row adalah indeks pertama ditemukannya elemen bukan nol pada suatu baris
			boolean firstPivot = false;
			int pivotRow;
			int lastPivot = 0;

			//Mengisi array dua dimensi solusi dengan solusi dari SPL itu sendiri
			for (int i = batasBaris; i >= 1; i--){
				//jika matriks tersebut adalah matriks nol, maka skip baris tersebut
				if (M.isAllzero(i)) continue;
				else {
					//baris ini adalah bukan row zero
					if (!firstPivot) {
						//first pivot row ditemukan
						firstPivot = true;
						int j = 1;
						//cari indeks kolom pertama yang nilai elemennya bukan nol
						while (j <= batasKolom-1 && M.GetElmt(i, j) == 0) j++;
						pivotRow = j;
						//inisialisasi solusi
						for (int k = 1; k <= batasKolom-1; k++) solusi[pivotRow][k] = 0;
						//semua variabel setelah indeks pivotRow akan menjadi variabel bebas
						j++;
						while (j <= batasKolom-1){
							if (M.GetElmt(i, j) != 0) {
								for (int k = 1; k <= batasKolom-1; k++){
									if (k != j) solusi[j][k] = 0;
									else solusi[j][k] = 1;
								}
								solusi[j][batasKolom] = 0;
								solusi[pivotRow][j] = -1*M.GetElmt(i, j);
							}
							j++;
						}
						//jadikan nilai pivotRow menjadi lastPivot 
						lastPivot = pivotRow;
						solusi[pivotRow][batasKolom] = M.GetElmt(i, batasKolom);
					}
					else {
						int j = 1;
						while (j <= batasKolom-1 && M.GetElmt(i, j) == 0) j++;
						pivotRow = j;
						for (int k = 1; k <= batasKolom-1; k++) solusi[pivotRow][k] = 0;
						for (int k = pivotRow+1; k <= lastPivot-1; k++){
							for (int l = 1; l <= batasKolom-1; l++){
									if (l != k) solusi[k][l] = 0;
									else solusi[k][l] = 1;
							}
							solusi[k][batasKolom] = 0;
						}
						solusi[pivotRow][batasKolom] = M.GetElmt(i, batasKolom);
						for (int k = pivotRow+1; k <= batasKolom-1; k++){
							for (int l = 1; l <= batasKolom; l++){
								solusi[pivotRow][l] += -1*M.GetElmt(i, k)*solusi[k][l];
							}
						}
						lastPivot = pivotRow;
					}
				}
			}
		}
	}

	//Prosedur untuk menjalankan Gauss Jordan
	//Matriks M akan menjadi Matriks RREF
	//Array dua dimensi solusi akan berisi solusi dari SPL
	//adaSolusi adalah informasi dari solusi SPL tersebut
	//adaSolusi[0] = 0 -> SPL tidak memiliki solusi
	//adaSolusi[0] = 1 -> SPL memiliki solusi tunggal
	//adaSolusi[0] = 2 -> SPL memiliki solusi parametrik
	public void GaussJordan(Matriks M,double[][] solusi, int[] adaSolusi){

		//variabel untuk mengubah matriks M menjadi Matriks RREF 
		int batasBaris = M.GetBaris();
		int batasKolom = M.GetKolom();
		int baris = 1;
		int kolom = 1;

		//Algoritma untuk mengubah matriks M menjadi Matriks RREF
		while (baris <= batasBaris && kolom <= batasKolom){
			int indeksMaks=-1;
			for (int i = baris; i <= batasBaris && indeksMaks == -1; i++){
				if ((M.GetElmt(i, kolom)) != 0){
					indeksMaks = i;
				}
			}
			if (indeksMaks == -1)
				kolom++;
			else{
				M.tukarBaris(baris, indeksMaks);
				double bil = M.GetElmt(baris, kolom);
				for (int i = kolom; i <= batasKolom; i++){
					M.SetElmt(baris, i, M.GetElmt(baris, i) / bil);
				}
				for (int i = 1; i <= batasBaris; i++){
					if (i != baris) {
						double ratio = (-1*M.GetElmt(i, kolom)) / M.GetElmt(baris,kolom);
						for (int j = kolom; j <= batasKolom; j++)
							M.SetElmt(i, j, M.GetElmt(i, j) + ratio*M.GetElmt(baris, j));
					}	
				}
				baris++;
				kolom++;
			}
		}

		//Algoritma untuk mengecek apakah suatu SPL memiliki solusi atau tidak
		int rowzero = 0;
		boolean isSolutionExist = true;
		for (int i = batasBaris; i >= 1; i--){
			if (M.isAllzero(i)) rowzero++;
			if (M.isAllzero(i) && M.GetElmt(i,batasKolom) != 0) {
				isSolutionExist = false;
			}
		}
		if (!isSolutionExist){
			//SPL tidak memiliki solusi
			adaSolusi[0] = 0;
		}
		else{
			//SPL memiliki solusi tunggal
			if (batasBaris-rowzero == batasKolom-1) adaSolusi[0] = 1;
			//SPL memiliki solusi parametrik
			else adaSolusi[0] = 2;

			//Mengisi array dua dimensi solusi dengan solusi dari SPL itu sendiri
			for (int i = 1; i <= batasKolom-1; i++)
				for (int j = 1; j <= batasKolom; j++){
					if (j == i) solusi[i][j] = 1;
					else solusi[i][j] = 0;
				}
			int pivotRow = 0;
			for (int i = batasBaris; i >= 1; i--){
				if (M.isAllzero(i)) continue;
				else {
					int j = 1;
					while (j <= batasKolom-1 && M.GetElmt(i, j) == 0) j++;
					pivotRow = j;
					solusi[pivotRow][pivotRow] = 0;
					j++;
					while (j <= batasKolom-1){
						solusi[pivotRow][j] = -1*M.GetElmt(i, j);
						j++;
					}
					solusi[pivotRow][batasKolom] = M.GetElmt(i, batasKolom);
				}
			}
		}
	}

	//Menghitung fungsi interpolasi
	public double hitungFungsi(double[][] solusi, int derajat, double x){
		double res = 0;
		for (int i = 1; i <= derajat+1; i++){
			res += Math.pow(x, i-1)*solusi[i][derajat+2];
		}
		return res;
	}

	//prosedur untuk menjalankan interpolasi
	public void interpolasi(int X, String filename){
		/* KAMUS */
		Matriks t = new Matriks(100,3);
		Matriks spl = new Matriks();
		double xtaksir;
		double[][] solusi = new double[105][105];
		int[] adaSolusi = new int[1];
		int i, j, n = 0; //n menampung derajat interpolasi. i , j untuk idx looping

		/* ALGORITMA */
		/* Input derajat interpolasi */
		if (!filename.equals("-")) {
			try {
				Scanner in = new Scanner(new File(filename));
				while (in.hasNextLine()) {
					String line = in.nextLine();
					n++;
					Scanner g = new Scanner(line);
					while (g.hasNextDouble()) {
						double x = g.nextDouble();
						t.SetElmt(n,1,x);
						double y = g.nextDouble();
						t.SetElmt(n,2,y);
					}
				}
				n--; //derajat interpolasi = jumlahtitik - 1;
			}
			catch (FileNotFoundException ex) {
				System.out.println("File tidak ditemukan");
			}
		}
		else {
			Scanner in = new Scanner(System.in);
			System.out.print("Derajat interpolasi : ");
			n = in.nextInt();

			/* Input banyak titik */
			System.out.println("Input titik :");
			for(i = 1; i <= n + 1; i++) {
				double x = in.nextDouble();
				double y = in.nextDouble(); 
				t.SetElmt(i, 1, x);
				t.SetElmt(i, 2, y);
			}
		}

		System.out.print("Nilai yang akan ditaksir : ");
		Scanner x = new Scanner(System.in);
		xtaksir = x.nextDouble();

		/* Pindahkan ke dalam bentuk SPL */
		for(i = 1; i <= n + 1; i++) {
			for(j = 1; j <= n + 2; j++) {
				if(j == 1) {
					spl.SetElmt(i, j, 1);
				} else if(j == n + 2) {
					spl.SetElmt(i, j, t.GetElmt(i,2));
				} else {
					spl.SetElmt(i,j, Math.pow(t.GetElmt(i,1), j - 1));
				}
			}
		}
		spl.SetBaris(n+1);
		spl.SetKolom(n+2);

		//jalankan algoritma interpolasi sesuai perintah user
		//1 -> Dengan Eliminasi Gauss
		//2 -> Dengan Gauss Jordan
		if (X == 1) EliminasiGauss(spl, solusi, adaSolusi);
		else GaussJordan(spl, solusi, adaSolusi);

		if (adaSolusi[0] == 0) {
			//Tidak ada koefisien a0, a1, ..., an-1 yang memenuhi sehingga tidak ada fungsi yang dimaksud
			System.out.println("Tidak ada fungsi p(x) yang memenuhi");
		}
		else {
			for (i = 1; i <= n+1; i++){
				for (j = 1; j <= n+1; j++){
					if (solusi[i][j] != 0) solusi[i][j] = 0;
				} 
			}
			System.out.print("Polinom interpolasi yang melewati " + (n+1) + " titik tersebut adalah p(x) = ");
			for (i = 1; i <= n+1; i++){
				if (solusi[i][n+2] != 0) {
					if (i != 1) {
						System.out.print(" + ");
						System.out.printf("(%.4f)x^%d", solusi[i][n+2],(i-1));
					} else {
						System.out.printf("%.4f", solusi[i][n+2]);
					}
				}
			}
			System.out.println();
			System.out.printf("Maka nilai taksiran fungsi pada x = %.4f adalah %.4f%n",xtaksir,hitungFungsi(solusi, n, xtaksir));			
			System.out.print("Masukan nama file untuk file output (dengan .txt dibelakangnya): ");
			Scanner filex = new Scanner(System.in);
			String namafile = filex.nextLine();
			TulisInterpolasi(namafile, solusi, n+1, xtaksir, hitungFungsi(solusi, n, xtaksir));	
		}
	}

	//prosedur untuk menuliskan solusi dari persamaan kepada suatu file
	public void TulisFILE(String Fileout, double[][] solusi, int N) {
		try {
			Formatter x = new Formatter(Fileout);
			x.format("Solusi dari persamaan tersebut adalah:%n");
			for (int i = 1; i <= N; i++){
				x.format("X[%d] = ", i);
				boolean cetakFirst = true;
				for (int j = 1; j <= N; j++){
					if (solusi[i][j] != 0) {
						if (cetakFirst) {
							cetakFirst = false;
							x.format("(%.4f)T[%d]", solusi[i][j], j);
						}
						else x.format(" + (%.4f)T[%d]", solusi[i][j], j);
					}
				}
				if (solusi[i][N+1] != 0) {
					if (cetakFirst) x.format("%.4f%n", solusi[i][N+1]);
					else x.format(" + %.4f%n", solusi[i][N+1]);
				}
				else {
					if (cetakFirst) x.format("%d%n", 0);
					else x.format("%n");
				}
			}
			x.close();
		}
		catch (FileNotFoundException ex){
			System.out.println("Error Occured. Cannot writing into file");
		}
	}

	//prosedur untuk menuliskan hasil interpolasi pada sebuah file
	public void TulisInterpolasi(String Fileout, double[][] solusi, int n, double taksir, double hasil) {
		try {
			Formatter x = new Formatter(Fileout);
			x.format("Fungsi tersebut adalah p(x) = ");
			for (int i = 1; i <= n; i++) {
				if (solusi[i][n+1] != 0) {
					if (i != 1) {
						x.format(" + ");
						x.format("(%.4f)x^%d", solusi[i][n+1],(i-1));
					}
					else {
						x.format("%.4f", solusi[i][n+1]);
					}
				}
			}
			x.format("%nMaka nilai taksiran fungsi pada %f adalah p(%f) = %f", taksir, taksir, hasil);
			x.close();
		}
		catch (Exception e) {
			System.out.println("Error Occured. Cannot writing into file");
		}
	}
}