/**************************************************
 * This program neatly prints out the powers of the 
 *integers 1 - 20 up through the 5th power using
 *printf and some loops
 *
 *@author Nathan Anderle 
 **************************************************/
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//Function responsible for printing the table headers
void pcolumn(char *title[]);
//Function responsible for doing the math
void mathstuff();

//Main function that drives the program
int main(){

	printf(" :::::   A TABLE OF POWERS   :::::\n\n");

	char* columns [] = {"Integer", "Square", "3rd Power", "4th Power", "5th Power"};	

	pcolumn(columns);
	mathstuff();
	
	return 0;
}


//Prints the headers
void pcolumn(char *title[]){

	int i = 0;
	
	char* dashes[] = {"-------", "------", "---------","---------", "---------"};

	//prints the headers with 8-space padding
	for(; i < 5; ++i){ 
			
		printf("%8s\t", title[i]);
	}

	printf("\n");
	
	//prints the dashes
	for(i = 0; i < 5; i++){
		
		printf("%8s\t", dashes[i]);

	}

	printf("\n");
}

//Does the math
void mathstuff(){

	int i = 1;

	//Counts from 1 to 20
	for(; i < 20; i++){
		int x = 1;
		int r = i;
		
		//prints the integers
		printf("%8d\t", i);
		
		//Multiplies r * i repetitively to generate powers
		for(; x < 5; x++){
			
			r *= i;
			printf("%8d\t", r);

		}
	printf("\n");
	}
}
