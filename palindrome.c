//|test_palindrome.c|: test |is_palindrome()| function

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>

#define MAX_PAL_LEN     20

bool is_palindrome(const char[]);
    /* supposed to return |true| i.e. 1 if the given string is a palindrome,
 *      * |false| i.e. 0 otherwise.
 *           */

int
main()
{
    const char data[][MAX_PAL_LEN] =
	{ "hello", "a", "did", "noon", "level", "redder", "" };
    for ( unsigned int i = 0 ; i < sizeof data / sizeof *data ; ++i )
	printf("is_palindrome(\"%s\"): %i\n", data[i], is_palindrome(data[i]));
}


bool
is_palindrome(const char s[])
{
    int l = strlen(s);

			puts(s[0]);
			puts(s[l]);
				
    for ( int i = 0 ; i < l/2 ; ++i )
        if ( s[i] != s[l-i] )
            return  false;
    return  true;
}
