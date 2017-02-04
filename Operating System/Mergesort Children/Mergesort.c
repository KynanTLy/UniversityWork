#include <stdio.h>
#include <stdlib.h>
#include <sys/shm.h>
#include <string.h>
#include <sys/wait.h>
#include <unistd.h>

#define SHM_SIZE 100000
#define SHM_MODE 0600

/** @brief Merges left and right arrays back together in shared (orignal) array .
 *  @param array, is the orginal shared array segement
 *  @param left, is left most bound
 *  @param right, is the right most bound
 *  @param midde, is the middle value
 *  @return void.
 */
void merge(int *array, int left, int middle, int right){
    	int i = 0;  		//Loop index for L
   	int j = 0;		//Loop index for R
    	int k = left;		
    	int leftIndex = middle - left + 1;
    	int rightIndex =  right - middle;
 
	//Create memory location for left and right array
	int *leftArray  =  malloc(leftIndex* sizeof(int));
	int *rightArray =  malloc(rightIndex* sizeof(int));
	 
	//Load array into created memory space
	memcpy(leftArray, array + left, sizeof(int)*(leftIndex));
	memcpy(rightArray, array + middle + 1, sizeof(int)*(rightIndex));

    	//Merge Temp Arrays of left and right back together
    	while (i < leftIndex && j < rightIndex){
        	if (leftArray[i] <= rightArray[j]){
            		array[k] = leftArray[i];
           		i++;
       		} else {
            		array[k] = rightArray[j];  
            		j++;
        	}//end if statement
     		k++;
	}//end while loop
 
    	//Place the remaining (from left) elements into array if there is any left over
    	while (i < leftIndex){
      		array[k] = leftArray[i];
      	  	i++;
        	k++;
    	}//end while loop
 
    	//Place the remaining (from right )elements into array if there is any left over
    	while (j < rightIndex) {
        	array[k] = rightArray[j];
        	j++;
       		k++;
    	}//end while loop

	//Free memory of left and right
	free(leftArray);
	free(rightArray);

}//end merge


/** @brief printArray prints the value of the array
 *  @param A, is the array in question
 *  @param size, is the array size
 *  @return void, prints out array.
 */
void printArray(int *A, int size)
{
    int i;
    for (i=0; i < size; i++)
        printf("%d ", A[i]);
    printf("\n");
}//end printArray


/** @brief mergeSort Creats the 2 chidlren and pass them their segment of the array they need abd afterwords the parent run merge
 *  @param array, is the orginal shared array segement
 *  @param left, is left most bound
 *  @param right, is the right most bound
 *  @param midde, is the middle value
 *  @return void.
 */
void mergeSort(int *array, int left, int right)
{
    pid_t pid; 		 		//First child ID
    pid_t pid2;		 		//Second child ID
    int middle = (left+right)/2; 	
    if (left < right)
    {
	if ((pid = fork()) < 0){
		perror("fork error");	
	} else if (pid == 0) {
		//first child
		mergeSort(array, left, middle);
		exit(0);
	} else {
		if ((pid2 = fork()) < 0){perror("fork error");	
		} else if (pid2 == 0) {
			//second child
			mergeSort(array, middle+1, right);	
			exit(0);
		} 
	}
	//Wait for both children to finish before merging 	
	if (waitpid(pid2, NULL, 0) != pid2) {perror("waitpid error");}
	if (waitpid(pid, NULL, 0) != pid) {perror("waitpid error");}
	merge(array, left, middle, right);

    }//end if statement

}//end mergeSort


/** @brief mian, reads the file, creats the shared memory segment for it and then runs the mergesort
 *  @param argc, is the amount  of arguments
 *  @parma argv, used when opening files
 *  @return void.
 */
int main(int argc, char *argv[]){

	int shmid;      
	int *shmptr;	
	int count =0;
	int index =0;
	int *array;
	int value;

	FILE *file;
	
	if (argc > 2){
		printf("Too many arguement \n");
		return (0);	
	} else if (argc == 1){
		printf("Missing Filename \n");
		return (0);
	}//end check for arguements

	file = fopen(argv[1], "r");
	if (file == NULL) {
		printf("error opening file\n");
		return 0;
	}//end file is null

	while (fscanf(file, "%d", &value) != EOF) {	
		if (count == 0){
			count += 1;
			array = (int *) malloc(count * sizeof(int));
			array[index] = value;	
			index += 1;
		} else {
			count += 1;
			array = (int *) realloc(array, count * sizeof(int));
			array[index] = value;
			index += 1;	
		}//end if statement
	
	}//end while loop

	//close the file
	fclose(file);

	//return the memory ID 
	if ((shmid = shmget(IPC_PRIVATE, sizeof(int)*(index), 0666)) < 0) perror("shmget error");

	//return the base address of the memory 
	if ((shmptr = shmat(shmid, NULL, 0)) == (void *)-1) perror("shmat error");
	
	memcpy(shmptr, array , sizeof(int)*(index));

        free(array);


	printf("\n");
	printf("Sorting file: data \n");
	printf("%d elements read \n", index);
	printf("\nInput Numbers: \n");
	printArray(shmptr, index);
	mergeSort(shmptr, 0, index -1);
	printf("\nSorted Numbers: \n");
	printArray(shmptr, index);
	

	if (shmdt(shmptr) == -1) perror("shmdt error"); //detach sm
	if (shmctl(shmid, IPC_RMID, 0) < 0) perror("shmctl error"); //delete sm	

	return 0;

}//end main











