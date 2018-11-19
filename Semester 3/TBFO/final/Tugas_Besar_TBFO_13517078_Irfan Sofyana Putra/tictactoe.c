/*Tic-Tac-Toe Games*/
/* Created on 3 October 2018 by irfansofyana */

#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>

/* Tipe data bentukan untuk membentuk STATES */
typedef struct {
	char States[20];
}STATES;


/*Deklarasi Variabel yang diperlukan program */
char arr[20], now[20], smt[20];
STATES TransitionStates[205][20], DaftarStates[205], FinalStates[120], InitialStates[5], DaftarSimbol[15];
STATES StatesLewat[20];
FILE *filestates, *ftmp;
int counter, NStates, NFinal, NInitial, NSimbol, Nlewat;
bool isPlaced;

/*Prosedur untuk membaca file yang berisi states*/
/*I.S : File "FileStates.txt" belum dibaca */
/*F.S : File "FileStates.txt" sudah dibaca dan datanya disalin pada variabel yang bersesuaian */
void BacaFileStates(){
	/*Membuka file yang berisi daftar states, final states, initial states,
	Daftar simbol, dan transition Table*/
	filestates = fopen("FileStates.txt", "r");

	fscanf(filestates, "%s", &arr);
	if (strcmp(arr, "DaftarStates:") == 0){
		/* Membaca semua daftar states yang digunakan pada program */
		NStates = 1;
		do{
			fscanf(filestates, "%s", &arr);
			if (strcmp(arr, "InitialStates:") == 0) break;
			else {
				strcpy(DaftarStates[NStates].States, arr);
				NStates++;
			}
		}while (strcmp(arr, "InitialStates:") != 0);
		NStates--;
	}
	if (strcmp(arr, "InitialStates:") == 0){
		/* Membaca semua initial states yang digunakan pada program */
		NInitial = 1;
		do{
			fscanf(filestates,"%s", &arr);
			if (strcmp(arr, "FinalStates:") == 0) break;
			else {
				strcpy(InitialStates[NInitial].States, arr);
				NInitial++;
			}
		}while (strcmp(arr, "FinalStates:") != 0);
		NInitial--;
	}
	if (strcmp(arr, "FinalStates:") == 0){
		/* Membaca semua final states yang digunakan pada program */
		NFinal = 1;
		do{
			fscanf(filestates, "%s", &arr);
			if (strcmp(arr, "DaftarSimbol:") == 0) break;
			else {
					strcpy(FinalStates[NFinal].States, arr);
					NFinal++;
			}
		}while(strcmp(arr, "DaftarSimbol:") != 0);
		NFinal--;
	}
	if (strcmp(arr, "DaftarSimbol:") == 0) {
		/* Membaca semua daftar simbol yang digunakan pada program */
		NSimbol = 1;
		do{
			fscanf(filestates, "%s", &arr);
			if (strcmp(arr, "TransitionsTable:") == 0) break;
			else {
					strcpy(DaftarSimbol[NSimbol].States, arr);
					NSimbol++;
			}
		}while(strcmp(arr, "TransitionsTable:") != 0);
		NSimbol--;
	}
	if (strcmp(arr, "TransitionsTable:") == 0){
		/*Membaca transition table dari states-states yang digunakan pada program */
		NStates = 1;
		counter = 0;
		while (fscanf(filestates, "%s", &arr) != EOF){
			if (counter > 9) {
				counter = 0;
				NStates++;
			}
			strcpy(TransitionStates[NStates][counter].States, arr);
			counter++;
		}
	}
}

/*Prosedur untuk mencetak papan permainan yang sedang berlangsung */
/*I.S : kondisi permainan dideskripsi kan dengan array of char */
/*F.S : Tercetak kondisi papan permainan pada layar */
void cetakPapan(char arr[]){
	printf("Kondisi Papan Tic-Tac-Toe Sekarang adalah: \n");
	printf("-------\n");
	for (int i = 0; i < 9; i++){
		if (i%3 == 0){
			if (arr[i] == 'A') 
				printf("| |");
			else if (arr[i] == 'O')
				printf("|O|");
			else 
				printf("|X|");
		}else if (i%3 == 1){
			if (arr[i] == 'A') 
				printf(" |");
			else if (arr[i] == 'O')
				printf("O|");
			else 
				printf("X|");
		}else{
			if (arr[i] == 'A') 
				printf(" |\n");
			else if (arr[i] == 'O')
				printf("O|\n");
			else 
				printf("X|\n");
		}
	}
	printf("-------\n");
}

/*Mencari states pada DaftarStates */
/*Pre-kondisi : s[] adalah states yang terdefinisi */
/*Hasil				: indeks dimana states s[] berada */
int cariStates(char s[]){
	for (int i = 1; i <= NStates; i++){
		if (strcmp(DaftarStates[i].States, s) == 0) return i;
	}
}

/*Mengecek apakah posisi "pos" pada papan permainan sekarang masih kosong atau tidak*/
/*Pre-kondosi : s[] adalah sebuah states yang terdefinisi dan pos adalah posisi yang valid pada papan */
/*Hasil : True, jika s[pos] masih kosong dan false, jika sudah terisi */
bool CanPlaced(char s[], int pos){
	if (s[pos] != 'A') return false;
	return true;
}

/*Prosedur untuk memainkan permainan tic-tac-toe dimana komputer akan bermain pertama */
/*I.S : komputer akan bermain pertama */
/*F.S : komputer tidak akan kalah dalam permainan*/
void comFirst(){
	system("cls");
	printf("Komputer bermain terlebih dahulu!\n");
	printf("\n");
	
	//now adalah states dari permainan yang sedang berlangsung
	strcpy(now, TransitionStates[1][0].States);
	int x = 0;
	int Nlewat = 1;

	//selama states sekarang belum final states
	while (strlen(now) == 9){
		if (x == 0) {
			//Giliran komputer yang bermain
			printf("Giliran Komputer: \n");
			
			//cetak papan permainan
			cetakPapan(now);
			printf("\n");
			
			//salin states sekarang ke array yang berisi states yang sudah dilewati selama permainan
			strcpy(StatesLewat[Nlewat].States, now);
			Nlewat++;
			//Giliran selanjutnya adalah user
			x = 1-x;
		}
		else {
			//Giliran user yang bermain
			printf("Giliran Anda: \n");
			
			//meminta masukan dari user untuk memilih posisi dimana simbol x akan ditempatkan
			int bil;
			printf("Masukkan tempat yang ingin diisi(1-9): ");
			scanf("%d", &bil);
			bil--;

			//jika posisi yang diinputkan belum diisi, maka isi tempat itu dengan 'X'
			if (CanPlaced(now, bil)) {
				//mencari states sekarang di daftarStates
				int indeks = cariStates(now);
				
				//isi posisi yang diinputkan user dengan simbol 'X'
				bil++;
				now[bil-1] = 'X';
				
				//cetak papan permainan sekarang
				cetakPapan(now);
				printf("\n");
				
	
				//States sekarang berubah sesuai dengan transition table dan input dari user
				strcpy(now, TransitionStates[indeks][bil].States);
				
				//Giliran selanjutnya adalah komputer
				x = 1-x;
			}
			//posisi yang diinputkan oleh user sudah ditempati, maka states kembali ke dirinya sendiri.
			//input dari user akan diulang
			else {
				//salin states sekarang ke array yang berisi states yang sudah dilewati selama permainan
				strcpy(StatesLewat[Nlewat].States, now);
				Nlewat++;
				
				printf("Tolong fokus ya:( udah diisi itu tempatnya:(\n");
				printf("ulangi lagi yaa\n\n");
			}
		}
	}
	/*Permainan berakhir */

	//jika karakter terakhir dari final states adalah '*', maka komputer menang
	if (now[9] == '*') printf("Mohon maaf, Anda Kalah!\n");
	//jika karakter terakhir dari final states adalah '#', maka permainan berakhir seri
	else printf("Permainan berakhir seri!\n");
	
	//cetak kondisi papan permainan
	cetakPapan(now);
	
	//salin final states ke array yang berisi states yang sudah dilewati selama permainan
	strcpy(StatesLewat[Nlewat].States, now);
	
	//mencetak semua states yang telah dilewati selama permainan berlangsung ke layar
	printf("\n");
	printf("States yang dilewati: \n");
	for (int i = 1; i <= Nlewat; i++)
		printf("%d. %s\n", i, StatesLewat[i].States);
}

/*Prosedur untuk memainkan permainan tic-tac-toe dimana user akan bermain pertama */
/*I.S : user akan bermain pertama */
/*F.S : komputer tidak akan kalah dalam permainan*/
void playerFirst(){
	system("cls");
	printf("Anda bermain terlebih dahulu!\n");
	printf("\n");
	int no;
	//User akan diminta untuk mengisi posisi nomor 5 terlebih dahulu sesuai dengan batasan permasalahan
	printf("Isi posisi nomor 5 terlebih dahulu ya\n");
	do{
		printf("Masukkan posisi yang ingin diisi: ");
		scanf("%d", &no);
		if (no != 5) printf("Tolong isi nomor 5 terlebih dahulu yaa :)\n");
	}while(no != 5);

	//Now adalah states dari permainan yang sedang berlangsung
	strcpy(now, TransitionStates[105][0].States);
	int x = 0;
	int Nlewat = 1;

	//Selama states belum final states atau permainan masih belum selesai
	while (strlen(now) == 9){
		if (x == 0) {
			//Giliran komputer yang bermain
			printf("Giliran Komputer: \n");

			//cetak papan permainan
			cetakPapan(now);
			printf("\n");

			//salin states sekarang ke array yang berisi states yang sudah dilewati selama permainan
			strcpy(StatesLewat[Nlewat].States, now);
			Nlewat++;

			//Giliran selanjutnya adalah user
			x = 1-x;
		}
		else {
			//Giliran user yang bermain
			printf("Giliran Anda: \n");
			
			//meminta masukan dari user untuk memilih posisi dimana simbol x akan ditempatkan
			int bil;
			printf("Masukkan tempat yang ingin diisi(1-9): ");
			scanf("%d", &bil);
			bil--;

			//jika posisi yang diinputkan belum diisi, maka isi tempat itu dengan 'X'
			if (CanPlaced(now, bil)) {
				//mencari states sekarang di daftarStates
				int indeks = cariStates(now);
				
				//isi posisi yang diinputkan user dengan simbol 'X'
				bil++;
				now[bil-1] = 'X';
				
				//cetak papan permainan sekarang
				cetakPapan(now);
				printf("\n");

				//States sekarang berubah sesuai dengan transition table dan input dari user
				strcpy(now, TransitionStates[indeks][bil].States);
				
				//Giliran selanjutnya adalah komputer
				x = 1-x;
			}
			//posisi yang diinputkan oleh user sudah ditempati, maka states kembali ke dirinya sendiri.
			//input dari user akan diulang
			else {
				//salin states sekarang kepada array yang berisi states yang sudah dilewati selama permainan
				strcpy(StatesLewat[Nlewat].States, now);
				Nlewat++;
				printf("Tolong fokus ya :( udah diisi itu tempatnya:(\n");
				printf("ulangi lagi yaa\n\n");
			}
		}
	}
	/*Permainan berakhir */
	
	//jika karakter terakhir dari final states adalah '*', maka komputer menang
	if (now[9] == '*') printf("Mohon maaf, Anda Kalah!\n");
	//jika karakter terakhir dari final states adalah '#', maka permainan berakhir seri
	else printf("Permainan berakhir seri!\n");

	//cetak kondisi papan permainan
	cetakPapan(now);

	//salin final states ke array yang berisi states yang sudah dilewati selama permainan
	strcpy(StatesLewat[Nlewat].States, now);
	printf("\n");
	
	//mencetak semua states yang telah dilewati selama permainan berlangsung ke layar
	printf("States yang dilewati: \n");
	for (int i = 1; i <= Nlewat; i++)
		printf("%d. %s\n", i, StatesLewat[i].States);
}

//Deklarasi prosedur untuk menampilkan interface permainan
void TampilanAwal();

//Prosedur menu games tic-tac-toe
//I.S : user akan memasuki permainan tic-tac-toe dan diminta untuk memilih memulai permainan terlebih dahulu atau tidak
//F.S : games tic tac toe berakhir dengan komputer tidak akan kalah. Setelah itu user bisa memilih kembali ke main menu,
//			bermain kembali, atau keluar langsung dari program
void games(){
	system("cls");
	printf("Anda memasuki permainan Tic-Tac-Toe!\n\n");
	int no;

	//Meminta masukan user apakah ingin bermain terlebih dahulu atau tidak
	printf("Pilih 1 jika Anda ingin main terlebih dahulu\n");
	printf("Pilih 2 jika Anda ingin komputer main terlebih dahulu\n");

	//Masukan akan diulang selama input dari user tidak valid
	do{
		printf("Masukkan Pilihan: ");
		scanf("%d", &no);
		if (no != 1 && no != 2) printf("Pilihan tidak valid!\n");
	}while (no != 1 && no != 2);
	printf("\n");
	
	//jika user memilih 1, artinya dia akan bermain terlebih dahulu
	if (no == 1) playerFirst();
	//jika user memilih 2, artinya komputer akan bermain terlebih dahulu
	else if (no == 2) comFirst();

	/*Permainan selesai*/

	//Menampilkan pilihan kepada user untuk kembali bermain, atau kembali ke main menu, atau keluar langsung dari program
	printf("\n");
	printf("Pilih 1 untuk kembali ke main menu\n");
	printf("Pilih 2 untuk kembali bermain\n");
	printf("Pilih 3 untuk Keluar\n");
	
	//input dari user akan diulang jika input tidak valid
	do{
		printf("Masukkan Pilihan: ");
		scanf("%d", &no);
		if (no != 1 && no != 2 && no != 3) printf("Pilihan tidak valid!\n");
	}while (no != 1 && no != 2 && no != 3);

	//jika user memilih 1, maka program akan menampilkan main menu
	if (no == 1) TampilanAwal();
	//jika user memilih 2, maka program akan kembali menunjukan permainan tic-tac-toe
	else if (no == 2) games();
	//jika user memilih 3, maka program telah selesai digunakan
	else return;
}

//Deklarasi prosedur untuk menampilkan cara bermain dari aplikasi tic-tac-toe yang dibuat ini
void caraBermain();

//Prosedur untuk menampilkan interface dari aplikasi tic-tac-toe (main menu)
//I.S : Program akan menampilkan interface dari aplikasi tic-tac-toe
//F.S : Program akan menjalankan instruksi berdasarkan pilihan yang dipilih oleh user
void TampilanAwal(){
	system("cls");
	printf(" /$$$$$$$$ /$$$$$$  /$$$$$$        /$$$$$$$$ /$$$$$$   /$$$$$$        /$$$$$$$$ /$$$$$$  /$$$$$$$$        /$$$$$$\n");
	printf("|__  $$__/|_  $$_/ /$$__  $$      |__  $$__//$$__  $$ /$$__  $$      |__  $$__//$$__  $$| $$_____/       /$$__  $$\n");
	printf("   | $$     | $$  | $$  \\__/         | $$  | $$  \\ $$| $$  \\__/         | $$  | $$  \\ $$| $$            | $$  \\__/  /$$$$$$  /$$$$$$/$$$$   /$$$$$$   /$$$$$$$\n");
	printf("   | $$     | $$  | $$               | $$  | $$$$$$$$| $$               | $$  | $$  | $$| $$$$$         | $$ /$$$$ |____  $$| $$_  $$_  $$ /$$__  $$ /$$_____/\n");
	printf("   | $$     | $$  | $$               | $$  | $$__  $$| $$               | $$  | $$  | $$| $$__/         | $$|_  $$  /$$$$$$$| $$ \\ $$ \\ $$| $$$$$$$$|  $$$$$$ \n");
	printf("   | $$     | $$  | $$    $$         | $$  | $$  | $$| $$    $$         | $$  | $$  | $$| $$            | $$  \\ $$ /$$__  $$| $$ | $$ | $$| $$_____/ \\____  $$\n");
	printf("   | $$    /$$$$$$|  $$$$$$/         | $$  | $$  | $$|  $$$$$$/         | $$  |  $$$$$$/| $$$$$$$$      |  $$$$$$/|  $$$$$$$| $$ | $$ | $$|  $$$$$$$ /$$$$$$$/\n");
	printf("     |__/   |______/ \\______/          |__/  |__/  |__/ \\______/          |__/   \\______/ |________/       \\______/  \\_______/|__/ |__/ |__/ \\_______/|_______/\n");
	printf("\n\n");
	printf(" /$$$$$$$                  /$$            /$$$$$$                                               /$$$$$$\n");
	printf("| $$__  $$                |__/           /$$__  $$                                             /$$__  $$\n");
	printf("| $$  \\ $$ /$$   /$$       /$$  /$$$$$$ | $$  \\__//$$$$$$  /$$$$$$$         /$$$$$$$  /$$$$$$ | $$  \\__//$$   /$$  /$$$$$$  /$$$$$$$   /$$$$$$\n");
	printf("| $$$$$$$ | $$  | $$      | $$ /$$__  $$| $$$$   |____  $$| $$__  $$       /$$_____/ /$$__  $$| $$$$   | $$  | $$ |____  $$| $$__  $$ |____  $$\n");
	printf("| $$__  $$| $$  | $$      | $$| $$  \\__/| $$_/    /$$$$$$$| $$  \\ $$      |  $$$$$$ | $$  \\ $$| $$_/   | $$  | $$  /$$$$$$$| $$  \\ $$  /$$$$$$$\n");
	printf("| $$  \\ $$| $$  | $$      | $$| $$      | $$     /$$__  $$| $$  | $$       \\____  $$| $$  | $$| $$     | $$  | $$ /$$__  $$| $$  | $$ /$$__  $$\n");
	printf("| $$$$$$$/|  $$$$$$$      | $$| $$      | $$    |  $$$$$$$| $$  | $$       /$$$$$$$/|  $$$$$$/| $$     |  $$$$$$$|  $$$$$$$| $$  | $$|  $$$$$$$ \n");
	printf("|_______/  \\____  $$      |__/|__/      |__/     \\_______/|__/  |__/      |_______/  \\______/ |__/      \\____  $$ \\_______/|__/  |__/ \\_______/ \n");
	printf("           /$$  | $$                                                                                    /$$  | $$   \n");
	printf("          |  $$$$$$/                                                                                   |  $$$$$$/\n");
	printf("           \\______/                                                                                     \\______/\n");

	printf("\n");
	printf("=======================================================================  1. Cara Bermain ==================================================================\n");
	printf("=======================================================================  2. Main         ==================================================================\n");
	printf("=======================================================================  3. Keluar       ==================================================================\n");
	int no;
	//program akan menerima pilihan dari user dan terus mengulangnya selama tidak valid
	do {
		printf("Masukan Pilihan : ");
		scanf("%d", &no);
		if (no != 1 && no != 2 && no != 3) printf("Pilihan tidak valid\n");
	}while (no != 1 && no != 2 && no != 3);

	//jika user memilih 1, maka program akan menampilkan cara bermain dari aplikasi ini
	if (no == 1) {
		printf("\n");
		caraBermain();
	}
	//jika user memilih 2, maka program akan langsung dialihkan untuk bermain tic-tac-toe
	else if (no == 2){
		printf("\n");
		games();
	}
	//jika user memilih 3, maka program berhenti dan program telah selesai digunakan
	else return;
}

//Prosedur untuk menampilan cara bermain/cara menggunakan aplikasi tic-tac-toe yang dibuat ini
//I.S : -
//F.S : Pada layar, akan diberikan informasi mengenai cara untuk menggunakan/bermain tic-tac-toe pada program ini
void caraBermain(){
	system("cls");

	//Menampilkan cara bermain dari tic-tac-toe pada program ini
	printf("Cara bermain Tic-Tac-Toe ini sangat mudah sekali!\n");
	printf("\n\n");
	printf("1. Pertama Pilih terlebih dahulu, Anda ingin main duluan atau Komputer terlebih dahulu\n");
	printf("2. Anda akan menggunakan simbol X, sedangkan komputer akan menggunakan simbol O\n");
	printf("3. Disaat giliran Anda bermain, Anda diminta untuk menginputkan bilangan (1-9) yaitu posisi untuk menyimpan simbol X\n");
	printf("4. Posisi papan Tic-Tac-Toe bisa digambarkan menjadi gambar di bawah ini: \n");
	printf("   -------\n");
	for (int i = 0; i < 9; i++){
		if (i%3 == 0) printf("   ");
		if (i%3 == 0){
				printf("|%d|", i+1);
		}else if (i%3 == 1){
				printf("%d|", i+1);
		}else{
				printf("%d|\n", i+1);
		}
	}
	printf("   -------\n");
	printf("\n");

	//Meminta masukan user untuk kembali ke main menu atau langsung bermain
	printf("Pilih 1 untuk kembali\n");
	printf("Pilih 2 untuk bermain\n");
	int no;

	//Selama masukan dari user tidak valid, maka input akan terus diulang
	do{
		printf("Masukan Pilihan: ");
		scanf("%d", &no);
		if (no != 1 && no != 2) 
			printf("Pilihan tidak valid\n");
	}while (no != 1 && no != 2);

	//jika user memilih 1 maka program akan kembali ke main menu
	if (no == 1) TampilanAwal();
	//jika user memilih 2 maka permainan tic-tac-toe akan dimulai
	else games();
}

//program utama dari games tic-tac-toe yang dibuat
int main(){
	//membaca informasi mengenai states permainan tic-tac-toe
	BacaFileStates();
	//Menampilkan main menu kepada user
	TampilanAwal();
	return 0;
}