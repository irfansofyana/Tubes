import java.util.*;
import java.io.*;

class SPL {
	public static void main(String[] args) {
		try {
   		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (Exception e) {
   		 System.out.println("Beda");
		}
		Scanner in = new Scanner(System.in);
		System.out.println("MENU");
		System.out.println("1. Sistem Persamaan Linear");
		System.out.println("2. Interpolasi Polinom");
		System.out.println("3. Keluar");
		int menu = in.nextInt();

		while (menu != 3) {
			if (menu == 1) {
				System.out.println("1. Metode Eliminasi Gauss");
				System.out.println("2. Metode Eliminasi Gauss-Jordan");
				int submenu = in.nextInt();

				while (submenu != 1 && submenu != 2) {
					System.out.println("Masukkan salah");
					submenu = in.nextInt();
				}

				if (submenu == 1) {
					//CEK FILE EKSTERNAL
					Scanner filex = new Scanner(System.in);
					System.out.print("Masukkan direktori File Eksternal : ");
					String namafile = filex.nextLine();
					File file = new File(namafile);

					while (!file.exists() && !namafile.equals("-")) {
						System.out.println("File tidak ada");
						System.out.print("Masukkan direktori File Eksternal : ");
						namafile = filex.nextLine();
						file = new File (namafile);
					}

					if (!namafile.equals("-")) {
						double[][] solusi = new double[105][105]; 
						Solusi hasil = new Solusi();	
						Matriks M = new Matriks();
						M.BacaMATRIKS(file);
						int[] adaSolusi = new int[1];
						hasil.EliminasiGauss(M, solusi, adaSolusi);
						if (adaSolusi[0] == 1) {
							hasil.TulisSOLUSI(solusi, M.GetKolom()-1);
							System.out.print("Masukan nama file untuk file output (dengan .txt dibelakangnya): ");
							namafile = filex.nextLine();
							hasil.TulisFILE(namafile, solusi, M.GetKolom()-1);
						}else if (adaSolusi[0] == 0){
							System.out.println("Sistem persamaan tersebut tidak memiliki solusi");
						}
						else if (adaSolusi[0] == 2){
							hasil.TulisSOLUSI(solusi, M.GetKolom()-1);
							System.out.print("Masukan nama file untuk file output (dengan .txt dibelakangnya): ");
							namafile = filex.nextLine();
							hasil.TulisFILE(namafile, solusi, M.GetKolom()-1);
						}
					}
					else {
						int m, n;
						double[][] solusi = new double[105][105]; 
						Solusi hasil = new Solusi();
						int[] adaSolusi = new int[1];
						System.out.print("Masukan banyak persamaan: ");
						m = in.nextInt();
						System.out.print("Masukan banyak variabel: ");
						n = in.nextInt();
						Matriks M = new Matriks(m,n+1);
						M.BacaMATRIKS();
						hasil.EliminasiGauss(M, solusi, adaSolusi);
						if (adaSolusi[0] == 1) { 
							hasil.TulisSOLUSI(solusi, M.GetKolom()-1);
							System.out.print("Masukan nama file untuk file output (dengan .txt dibelakangnya): ");
							namafile = filex.nextLine();
							hasil.TulisFILE(namafile, solusi, M.GetKolom()-1);
						}
						else if (adaSolusi[0] == 0){
							System.out.println("Sistem persamaan tersebut tidak memiliki solusi");
						}
						else if (adaSolusi[0] == 2){
							hasil.TulisSOLUSI(solusi, M.GetKolom()-1);
							System.out.print("Masukan nama file untuk file output (dengan .txt dibelakangnya): ");
							namafile = filex.nextLine();
							hasil.TulisFILE(namafile, solusi, M.GetKolom()-1);
						}
					}
					menu = 3;
				}
				else if (submenu == 2) {
					// JALANIN ELMINASI GAUSS-JORDAN
					Scanner filex = new Scanner(System.in);
					System.out.println("Masukkan direktori File Eksternal : ");
					String namafile = filex.nextLine();
					File file = new File (namafile);

					while (!file.exists() && !namafile.equals("-")) {
						System.out.println("File tidak ada");
						System.out.print("Masukkan direktori File Eksternal : ");
						namafile = filex.nextLine();
						file = new File (namafile);
					}

					if (!namafile.equals("-")) {
						double[][] solusi = new double[105][105]; 
						int[] adaSolusi = new int[1];
						Solusi hasil = new Solusi();
						Matriks M = new Matriks();
						M.BacaMATRIKS(file);
						hasil.GaussJordan(M, solusi, adaSolusi);
						if (adaSolusi[0] == 1) { 
							hasil.TulisSOLUSI(solusi, M.GetKolom()-1);
							System.out.print("Masukan nama file untuk file output (dengan .txt dibelakangnya): ");
							namafile = filex.nextLine();
							hasil.TulisFILE(namafile, solusi, M.GetKolom()-1);
						}
						else if (adaSolusi[0] == 0){
							System.out.println("Sistem persamaan tersebut tidak memiliki solusi");
						}
						else if (adaSolusi[0] == 2){
							hasil.TulisSOLUSI(solusi, M.GetKolom()-1);
							System.out.print("Masukan nama file untuk file output (dengan .txt dibelakangnya): ");
							namafile = filex.nextLine();
							hasil.TulisFILE(namafile, solusi, M.GetKolom()-1);
						}
					}
					else {
						int m, n;
						double[][] solusi = new double[105][105];
						int[] adaSolusi = new int[1]; 
						Solusi hasil = new Solusi();
						System.out.print("Masukan banyak persamaan: ");
						m = in.nextInt();
						System.out.print("Masukan banyak variabel: ");
						n = in.nextInt();
						Matriks M = new Matriks(m,n+1);
						M.BacaMATRIKS();
						hasil.GaussJordan(M, solusi, adaSolusi);
						if (adaSolusi[0] == 1) { 
							hasil.TulisSOLUSI(solusi, M.GetKolom()-1);
							System.out.print("Masukan nama file untuk file output (dengan .txt dibelakangnya): ");
							namafile = filex.nextLine();
							hasil.TulisFILE(namafile, solusi, M.GetKolom()-1);
						}
						else if (adaSolusi[0] == 0){
							System.out.println("Sistem persamaan tersebut tidak memiliki solusi");
						}
						else if (adaSolusi[0] == 2){
							hasil.TulisSOLUSI(solusi, M.GetKolom()-1);
							System.out.print("Masukan nama file untuk file output (dengan .txt dibelakangnya): ");
							namafile = filex.nextLine();
							hasil.TulisFILE(namafile, solusi, M.GetKolom()-1);
						}
					}
					menu = 3;
				} 
			}
			else if (menu == 2) {
				System.out.println("1. Metode Eliminasi Gauss");
				System.out.println("2. Metode Eliminasi Gauss-Jordan");
				int submenu = in.nextInt();
				Solusi inter = new Solusi();

				while (submenu != 1 && submenu != 2) {
					System.out.println("Masukkan salah");
					submenu = in.nextInt();
				}

				Scanner filex = new Scanner(System.in);
				System.out.println("Masukkan direktori File Eksternal : ");
				String namafile = filex.nextLine();
				File file = new File (namafile);

				while (!file.exists() && !namafile.equals("-")) {
					System.out.println("File tidak ada");
					System.out.print("Masukkan direktori File Eksternal : ");
					namafile = filex.nextLine();
					file = new File (namafile);
				}

				inter.interpolasi(submenu, namafile);

				menu = 3;
			}
			else {
				System.out.println("Masukkan salah");
				menu = in.nextInt();
			}
		}
	}	
}