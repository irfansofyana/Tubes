#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>

typedef struct {
	char States[11];
}STATES;


char arr[11], now[11], smt[11], DaftarSimbol[205];
STATES TransitionStates[205][11], DaftarStates[205], FinalStates[120], InitialStates[5];
STATES StatesLewat[11];
FILE *ftransition, *fdaftarStates, *ffinalStates, *finitialStates, *fsimbol;
int counter, NStates, NFinal, NInitial, NSimbol, Nlewat;
bool isPlaced;

void BacaDaftarStates(){
	fdaftarStates = fopen("daftar_states.txt", "r");
	NStates = 1;
	while (fscanf(fdaftarStates, "%s", &arr) != EOF){
		strcpy(DaftarStates[NStates].States, arr);
		NStates++;
	}
}

void BacaFinalStates(){
	ffinalStates = fopen("final_states.txt", "r");
	NFinal = 1;
	while (fscanf(ffinalStates, "%s", &arr) != EOF){
		strcpy(FinalStates[NFinal].States, arr);
		NFinal++;
	}
}

void BacaInitialStates(){
	finitialStates = fopen("initial_states.txt", "r");
	NInitial = 1;
	while (fscanf(finitialStates, "%s", &arr) != EOF){
		strcpy(InitialStates[NInitial].States, arr);
		NInitial++;
	}
}

void BacaSimbol(){
	fsimbol = fopen("daftar_simbol.txt", "r");
	NSimbol = 1;
	char arr;
	while (fscanf(fsimbol, "%c", &arr) != EOF){
		DaftarSimbol[NSimbol] = arr;
		NSimbol++;
	}
}

void BacaTransitionStates(){
	ftransition = fopen("Transition_states.txt", "r");
	NStates = 1;
	counter = 0;
	while (fscanf(ftransition, "%s", &arr) != EOF){
		if (counter > 9) {
			counter = 0;
			NStates++;
		}
		strcpy(TransitionStates[NStates][counter].States, arr);
		counter++;
	}
}

void cetakPapan(char arr[]){
	printf("Kondisi Papan Tic-Tac-Toe Sekarang adalah: \n");
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
}

int cariStates(char s[]){
	for (int i = 1; i <= NStates; i++){
		if (strcmp(TransitionStates[i][0].States, s) == 0) return i;
	}
}

void Placed(char s[], int pos, bool *isPlaced){
	if (s[pos] != 'A') {
		*isPlaced = false;
	}else{
		*isPlaced = true;
	}
}

void comFirst(){
	system("cls");
	printf("Komputer bermain terlebih dahulu!\n");
	printf("\n");
	strcpy(now, TransitionStates[1][0].States);
	int x = 0;
	int Nlewat = 1;
	while (strlen(now) == 9){
		if (x == 0) {
			printf("Giliran Komputer: \n");
			cetakPapan(now);
			strcpy(StatesLewat[Nlewat].States, now);
			Nlewat++;
			printf("\n");
			x = 1-x;
		}
		else {
			printf("Giliran Anda: \n");
			int bil;
			printf("Masukkan tempat yang ingin diisi(1-9): ");
			scanf("%d", &bil);
			bil--;
			isPlaced = false;
			Placed(now, bil, &isPlaced);
			if (isPlaced) {
				int indeks = cariStates(now);
				bil++;
				now[bil-1] = 'X';
				cetakPapan(now);
				printf("\n");
				strcpy(StatesLewat[Nlewat].States, now);
				Nlewat++;
				strcpy(now, TransitionStates[indeks][bil].States);
				x = 1-x;
			}
			else {
				printf("Serius mas:( udah diisi itu tempatnya:(\n");
				printf("ulangi lagi yaa\n\n");
			}
		}
	}
	if (now[9] == '*') printf("Mohon maaf, Anda Kalah!\n");
	else printf("Permainan berakhir seri!\n");
	cetakPapan(now);
	strcpy(StatesLewat[Nlewat].States, now);
	printf("\n");
	printf("States yang dilewati: \n");
	for (int i = 1; i <= Nlewat; i++)
		printf("%d. %s\n", i, StatesLewat[i].States);
}

void playerFirst(){
	system("cls");
	printf("Anda bermain terlebih dahulu!\n");
	printf("\n");
	int no;
	printf("Isi posisi nomor 5 terlebih dahulu ya\n");
	do{
		printf("Masukkan posisi yang ingin diisi: ");
		scanf("%d", &no);
		if (no != 5) printf("Tolong isi nomor 5 terlebih dahulu yaa :)\n");
	}while(no != 5);
	strcpy(now, TransitionStates[105][0].States);
	int x = 0;
	int Nlewat = 1;
	while (strlen(now) == 9){
		if (x == 0) {
			printf("Giliran Komputer: \n");
			cetakPapan(now);
			printf("\n");
			strcpy(StatesLewat[Nlewat].States, now);
			Nlewat++;
			x = 1-x;
		}
		else {
			printf("Giliran Anda: \n");
			int bil;
			printf("Masukkan tempat yang ingin diisi(1-9): ");
			scanf("%d", &bil);
			bil--;
			isPlaced = false;
			Placed(now, bil, &isPlaced);
			if (isPlaced) {
				int indeks = cariStates(now);
				bil++;
				now[bil-1] = 'X';
				cetakPapan(now);
				printf("\n");
				strcpy(StatesLewat[Nlewat].States, now);
				Nlewat++;
				strcpy(now, TransitionStates[indeks][bil].States);
				x = 1-x;
			}
			else {
				printf("Serius mas:( udah diisi itu tempatnya:(\n");
				printf("ulangi lagi yaa\n\n");
			}
		}
	}
	if (now[9] == '*') printf("Mohon maaf, Anda Kalah!\n");
	else printf("Permainan berakhir seri!\n");
	cetakPapan(now);
	strcpy(StatesLewat[Nlewat].States, now);
	printf("\n");
	printf("States yang dilewati: \n");
	for (int i = 1; i <= Nlewat; i++)
		printf("%d. %s\n", i, StatesLewat[i].States);
}

void TampilanAwal();

void games(){
	system("cls");
	printf("Anda memasuki permainan Tic-Tac-Toe!\n\n");
	int no;
	printf("Pilih 1 jika Anda ingin main terlebih dahulu\n");
	printf("Pilih 2 jika Anda ingin komputer main terlebih dahulu\n");
	do{
		printf("Masukkan Pilihan: ");
		scanf("%d", &no);
		if (no != 1 && no != 2) printf("Pilihan tidak valid!\n");
	}while (no != 1 && no != 2);
	printf("\n");
	if (no == 1) playerFirst();
	else if (no == 2) comFirst();
	printf("\n");
	printf("Pilih 1 untuk kembali ke main menu\n");
	printf("Pilih 2 untuk kembali bermain\n");
	printf("Pilih 3 untuk Keluar\n");
	do{
		printf("Masukkan Pilihan: ");
		scanf("%d", &no);
		if (no != 1 && no != 2 && no != 3) printf("Pilihan tidak valid!\n");
	}while (no != 1 && no != 2 && no != 3);
	if (no == 1) TampilanAwal();
	else if (no == 2) games();
	else return;
}

void caraBermain();

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
	printf("1. Cara Bermain\n");
	printf("2. Main\n");
	printf("3. Keluar\n");
	int no;
	do {
		printf("Masukan Pilihan : ");
		scanf("%d", &no);
		if (no != 1 && no != 2 && no != 3) printf("Pilihan tidak valid\n");
	}while (no != 1 && no != 2 && no != 3);
	if (no == 1) {
		printf("\n");
		caraBermain();
	}
	else if (no == 2){
		printf("\n");
		games();
	}
	else return;
}

void caraBermain(){
	system("cls");
	printf("Cara bermain Tic-Tac-Toe ini sangat mudah sekali!\n");
	printf("\n\n");
	printf("1. Pertama Pilih terlebih dahulu, Anda ingin main duluan atau Komputer terlebih dahulu\n");
	printf("2. Anda akan menggunakan simbol X, sedangkan komputer akan menggunakan simbol O\n");
	printf("3. Disaat giliran Anda bermain, Anda diminta untuk menginputkan bilangan (1-9) yaitu posisi untuk menyimpan simbol X\n");
	printf("4. Posisi papan Tic-Tac-Toe bisa digambarkan menjadi gambar di bawah ini: \n");
	for (int i = 0; i < 9; i++){
		if (i%3 == 0){
				printf("|%d|", i+1);
		}else if (i%3 == 1){
				printf("%d|", i+1);
		}else{
				printf("%d|\n", i+1);
		}
	}
	printf("\n");
	printf("Pilih 1 untuk kembali\n");
	printf("Pilih 2 untuk bermain\n");
	int no;
	do{
		printf("Masukan Pilihan: ");
		scanf("%d", &no);
		if (no != 1 && no != 2) 
			printf("Pilihan tidak valid\n");
	}while (no != 1 && no != 2);
	if (no == 1) TampilanAwal();
	else games();
}

int main(){
	BacaDaftarStates();
	BacaFinalStates();
	BacaInitialStates();
	BacaSimbol();
	BacaTransitionStates();
	TampilanAwal();
	return 0;
}