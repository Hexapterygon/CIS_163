/*This program takes a file of 255 characters
 *or less and prints it out backwards*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>


int main(){

char str[255];
	
	//Starts at the back of the array and prints char by char
	while(fgets(str, 255, stdin) != NULL){

		int len = strlen(str);
		int i = len-1;

		for(; i >= 0 ; --i){
			putchar(str[i]);	
		}
			
	}
		putchar('\n');
		return 0;		
}
