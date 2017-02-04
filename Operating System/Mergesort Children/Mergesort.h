
#include <stdio.h>
#include <stdlib.h>
#include <sys/shm.h>
#include <string.h>
#include <sys/wait.h>
#include <unistd.h>

extern	void	merge(int *array, int left, int middle, int right);
extern  void    printArray(int *A, int size);
extern	void	mergeSort(int *array, int left, int right);

#endif /*Mergesort_h*/
