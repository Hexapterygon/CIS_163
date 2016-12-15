/*This program is a simple implementation of the UNIX
 * "wc" utility. Counts the number of words, chars, 
 * and lines in a file*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(){
		
	int c;
	int lines = 0;
	int words = 0;
	int chars = 0;

	//Loops until c hits the end of file. Counts chars
	while((c = getchar()) != EOF){	
		++chars;
		
		//Uses tabs and whitespaces to determine word count
		if(c == '\t' || c == ' '){
			++words;

			
			while((c == '\t' || c == ' ') && c != EOF){
				++chars;
				c = getchar();

			}

		}

		//Uses the newline character to count number of lines
		if(c == '\n'){
			++lines;
			}
	}
	printf("%d Lines\n", lines);
	printf("%d Words\n", words);
	printf("%d Chars\n", chars);

	return 0;
}
