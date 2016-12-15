/*This program takes a user entered integer, calculates
 * the nth fibonacci number of said number, and determines
 * an integer and floating point square root*/
#include <stdio.h>
#include <math.h>

//Functional prototypes
int fib(int);
float fsqrt(int);
int isqrt(int);

//Driver program that loops until user quits
int main(){

	int x = 1;
	
	do{	

			fputs("Enter i: ", stdout);
			scanf("%d", &x);
			if( x > 0){
			printf("F(%d): %d\n", x, fib(x));
			printf("isqrt(%d): %d\n", x, isqrt(x));
			printf("sqrt(%d): %f\n", x, fsqrt(x));
		}	

	}while(x > 0);

	printf("quiting\n");
	
	return 0;
}

//Naive recursive fibonacci. Runs at O(2^n). Not efficient
int fib(int x){
	
	if( x == 0)
		return 0;

	if(x == 1)
		return 1;

	else
		return (fib(x-1) + fib(x-2));
}

float fsqrt(int i){

	return sqrtf(i);
}

//Determines integer square root by dividing over and over again.
//I believe this is O(n). Not very efficient
int isqrt(int i){

	int x = 1;

	while(x*x <= i){	
		++x;
	}
	return --x;
}
