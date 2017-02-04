#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <string.h>
#include <pwd.h>
#include <grp.h>
#include <time.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>

/** @brief Prints file indicators symbol for file
 *        
 *  @param filebuf The stat structure of current file
 *         
 *  @param name The name of the current file
 *         
 *  @return void.
 */
void printIndicator(struct stat filebuf, char *name){
        //====Initialize Variables====//
        char linkName[1024];
	ssize_t r;
	struct stat linkfilebuf;  

	//====Print File Type Indicators====//
	if (S_ISDIR(filebuf.st_mode)){
		printf("%s/ ", name);
	} else if (S_ISCHR(filebuf.st_mode)){
		printf("%s ", name);
	} else if (S_ISBLK(filebuf.st_mode)){
		printf("%s ", name);
	} else if (S_ISFIFO(filebuf.st_mode)){
		printf("%s| ", name);
	} else if (S_ISSOCK(filebuf.st_mode)){
		printf("%s= ", name);
	} else if (S_ISLNK(filebuf.st_mode)){ 

	        //=====Check Link Target is Executable====// 
		r = readlink(name, linkName, (filebuf.st_size) + 1);
		if (r < 0) {
		        perror("lstat");
		        exit(EXIT_FAILURE);
		}//end lstat if 
		if (r > filebuf.st_size) {
        		fprintf(stderr, "symlink increased in size "
                        "between lstat() and readlink()\n");
        		exit(EXIT_FAILURE);
   		}//end check size if
		
		linkName[filebuf.st_size] = '\0';
		stat(linkName, &linkfilebuf);
		
		//====If Executable/Directory/Other Print Indicator====//
		if(S_IXUSR & linkfilebuf.st_mode && !(S_ISDIR(linkfilebuf.st_mode))){
			printf("%s -> %s*", name, linkName);
		} else if(S_ISDIR(linkfilebuf.st_mode) && (stat(linkName, &linkfilebuf) >= 0) ) {
			printf("%s -> %s/", name, linkName);
		} else {
		  printf("%s -> %s", name, linkName);
		}//end if
	} else if (filebuf.st_mode & S_IXUSR){
		printf("%s* ", name);
	} else if (S_ISREG(filebuf.st_mode)){
		printf("%s ", name);
	} else {
		printf("Unknown ");
	}//end final if

}//end printIndicator

/** @brief Print all relavent file information (not file symbol)
 *         
 *  @param filebuf The stat struct of the current file
 *        
 *  @param col The name of the current file
 *         
 *  @return void.
 */
void displayFileInfo(struct stat filebuf, char* name, int MaxSize){
        //====Initialize Variables====//
	struct passwd *pw;
	struct group *gp;
	char date[30];
	int NumDecPlace = 0;
	

	//====Print Type Bit====//
	if (S_ISDIR(filebuf.st_mode)){ 
		printf("d");
	} else if (S_ISCHR(filebuf.st_mode)){ 
		printf("c");
	} else if (S_ISBLK(filebuf.st_mode)){ 
		printf("b");
	} else if (S_ISFIFO(filebuf.st_mode)){
		printf("p");
	} else if (S_ISLNK(filebuf.st_mode)){ 
		printf("l");
	} else if (S_ISSOCK(filebuf.st_mode)){ 
		printf("s");
	} else if (S_ISREG(filebuf.st_mode)){
		printf("-");
	} else {
		//For Unknown Type
		printf("?");
	}//end type if

	//====Print User Access/Permission====//
	printf( (filebuf.st_mode & S_IRUSR) ? "r" : "-");
	printf( (filebuf.st_mode & S_IWUSR) ? "w" : "-");
	if((filebuf.st_mode & S_ISUID) && (filebuf.st_mode & S_IXUSR)){
		printf("s");
	} else if (!(filebuf.st_mode & S_ISUID) && (filebuf.st_mode & S_IXUSR)){
		printf("x");
	} else if ((filebuf.st_mode & S_ISUID) && !(filebuf.st_mode & S_IXUSR)){
		printf("S");
	} else {
		printf("-");
	}//end user if

	//====Print Group Access/Permission====//
	printf( (filebuf.st_mode & S_IRGRP) ? "r" : "-");
	printf( (filebuf.st_mode & S_IWGRP) ? "w" : "-");
	if((filebuf.st_mode & S_ISGID) && (filebuf.st_mode & S_IXGRP)){
		printf("s");
	} else if (!(filebuf.st_mode & S_ISGID) && (filebuf.st_mode & S_IXGRP)){
		printf("x");
	} else if ((filebuf.st_mode & S_ISGID) && !(filebuf.st_mode & S_IXGRP)){
		printf("S");
	} else {
		printf("-");
	}//end group if

	//====Print Other Access/Permission====//
	printf( (filebuf.st_mode & S_IROTH) ? "r" : "-");
	printf( (filebuf.st_mode & S_IWOTH) ? "w" : "-");
	if((filebuf.st_mode & S_ISVTX) && (filebuf.st_mode & S_IXOTH)){
		printf("t");
	} else if (!(filebuf.st_mode & S_ISVTX) && (filebuf.st_mode & S_IXOTH)){
		printf("x");
	} else if ((filebuf.st_mode & S_ISVTX) && !(filebuf.st_mode & S_IXOTH)){
		printf("T");
	} else {
		printf("-");
	}//end other if
	
	//====Print Link/Not Link '+'====//
	if(!(S_ISLNK(filebuf.st_mode))){
	  printf("+");
	} else {
	  printf(" ");
	}//end if 

	//=====Prints # of Hard Link=====// 
	printf(" %d", (int)filebuf.st_nlink);

	//=========Print User ID=========//
	if ((pw = getpwuid(filebuf.st_uid)) == NULL) {
		printf("FAIL: uid %d does not exist\n", filebuf.st_uid);
	}
	printf(" %s", pw->pw_name);
	
	//=======Print Group Name========//
	if ((gp = getgrgid(filebuf.st_gid)) == NULL) {
		printf("FAIL: gid %d does not exist\n", filebuf.st_gid);
	}//end if
	printf(" %s", gp->gr_name);
	
	//=====Print File Size (Byte)====//
        while(1){
		//Get decimal place for %*D
	        if(MaxSize > 0){
		  MaxSize = MaxSize/10;
		  NumDecPlace += 1;
		} else {
		  break;
		}//end if decimal place
	}//end loop
	printf(" %*d", NumDecPlace,(int)filebuf.st_size);

	//==========Print Date===========//
	strftime(date, 30, "%b %d %H:%M ", localtime(&(filebuf.st_mtime)));
	printf(" %s", date);

	//=======Print File Indicator====//
	printIndicator(filebuf, name);
	printf("\n");

}//end displayFileInfo


/** @brief Display relavent information about directory and
 *         calls displayFileInfo for file information
 *  @param filestruct A pointer to a char of a path
 *         
 *  @param directoryName The directory path name
 *      
 *  @return void.
 */
void displayDir(char *filestruct, char *directoryName){
        //=====Initialize Variables====//
	DIR *pDir;
	struct dirent **pDirent;	
	struct stat filebuf;  
	int i;
	int totalKByte = 0;
	int MaxSize = 0;

	//==(Variables) Used to hold the new size of buffer==//	
	size_t newDirSize;


	//==(Variables) Used to hold directory name to print==//
	char baseDirName[1024];
	strcpy(baseDirName,directoryName);
	
	//====Print Directory Name====//
	printf("\n%s: ", directoryName);
	pDir = opendir(filestruct);
	if(pDir == NULL){
		printf("Cannot open directory ");
    		exit(1);
  	}//end open directory
   	printf("\n");

	//====Sort/Go to directory====//
  	int n = scandir(filestruct, &pDirent, 0, alphasort);
  	chdir(filestruct);
 	
	//=====Print Total KByte=====//
	for(i = 0; i < n; i++){
		if(lstat(pDirent[i]->d_name, &filebuf) < 0){
      			printf("Stat error with %s in total \n", pDirent[i]->d_name);
     		 	continue;
    		}//end stat error
   		totalKByte += (int)filebuf.st_blocks;

		if (MaxSize <= (int)filebuf.st_size){
		  	MaxSize = (int)filebuf.st_size;
		}//end if for maxsize

 	}//end loop Total
	printf("Total: %d\n", ((totalKByte+1)/2));
     
	//=====Print All File Information====//
  	for(i = 0; i < n; i++){
   		if(lstat(pDirent[i]->d_name, &filebuf) < 0){
      			printf("Stat error with %s in print file info\n", pDirent[i]->d_name);
     		 	continue;
    		}//end stat error
   		displayFileInfo(filebuf, pDirent[i]->d_name, MaxSize);
 	}//end loop File Information
       	
	//======Run displayDir on Local Directories====//
  	for(i = 0; i < n; i++){

    		if(lstat(pDirent[i]->d_name, &filebuf) < 0){
      			printf("Stat error with %s in directory\n", pDirent[i]->d_name);
			free(pDirent[i]);
     			continue;
    		}//end stat error
    		if(S_ISDIR(filebuf.st_mode)){
			if(strcmp(pDirent[i]->d_name, "..") == 0 || strcmp(pDirent[i]->d_name, ".") == 0){
				free(pDirent[i]);
				continue;
     			}//end inner if
			newDirSize = sizeof(directoryName) + sizeof(pDirent[i]->d_name);
			//Holds the new directory name
			char *newDirName = (char*)malloc (newDirSize);
			snprintf(newDirName, newDirSize, "%s%s%s", directoryName, "/", pDirent[i]->d_name);
			displayDir(pDirent[i]->d_name, newDirName);
			free(newDirName);				
			chdir("..");
		}//end if		
		strcpy(directoryName,baseDirName);
	       	free(pDirent[i]);
  	}//end loop Directory
	//====Free/Close Directory====/
  	free(pDirent);
  	closedir(pDir);

}//end displayDir

/** @brief Checks for correct input argument and then open directory
 *         of path given
 *  @param 1st Argument is path to directory
 *       
 *  @return int.
 */
int main(int argc, char **argv){
        //====Initialize Variable====//
	struct stat fileStat;
	char directoryFile[1024];
	strcpy(directoryFile, argv[1]);

	//====Check For Correct # of Arg====//
	if(argc != 2) {
		printf("NOT ENOUGH ARGUMENT \n");
		return 1;
	}//end if
	if(lstat(argv[1], &fileStat) < 0) {
		printf("FILE NO SIZE\n");		
		return 1;
	}//end if
		
	//====Open Given Directory====//
	if(S_ISDIR(fileStat.st_mode)){
		displayDir(argv[1],directoryFile);
	}//end if 
	printf("\n\n");
	return 0;
}//end main 
