 # The following format is required for all submissions in CMPUT 229
	
#---------------------------------------------------------------
# Assignment:           4
# Due Date:             March 23, 2015
# Name:                 Kynan Ly
# Unix ID:              Kynan
# Lecture Section:      B1
# Instructor:           Jeeva Paudel 
# Lab Section:          H01 (Monday 1400 - 1650)
# Teaching Assistant:   Raphael Ernani Rodrigues
#---------------------------------------------------------------

#---------------------------------------------------------------
#	The purpose of this program is to write a MIPS program creates a timer that can be interrupt by timer or a keyboard input
#		
#	It should check if it encounters a keybaord or timer interrupt and act accordingly. q quits the program, r resets the timer, the timer counts up
#	
#
# Register Usage:
#
#	In Main code:
#	$t0: temp used to set $9 to 0
#	$t1: temp used to enable keyboard interrupt in control reg
#	$t2: temp used to enable keyboard and timer interrupt
#	$t3: Holds the backspace counter
#	$t4: Holds ascii for backspace
#	$t5: Holds the address of ascii of timer
#	$t6: Pointer to address of ascii of timer
#	$t7: temp used to check if display is ready
#
#	In Exception:
#	$t0: Reads cause register / Read if it was timer interrupt
#	$t1: temp if it was a keyboard interrupt
#	$t2: temp that held the key input / address of ascii timer
#	$t3: Hold values of key to be checked / counted for the timer
#	$t4: temp that was used to reset timer / temp used to check max digit for timer
#	$t5: Held ascii for 0
#
#References: 
#	Spim Quick Reference https://www.cs.tcd.ie/~waldroj/itral/spim_ref.html
#
#---------------------------------------------------------------


	.data 
D_Time:
	.asciiz "00:00"
	.text

.globl __start

#== Start ==#

__start:
	mtc0 $zero, $9		#Set co-processor = 0
	addi $t0, $zero, 100	#Set the timer to interrupt every min
	mtc0 $t0, $11

	lw   $t1, 0xFFFF0000	#Reads the keyboard control
	ori  $t1, $t1, 0x0002   
	sw   $t1, 0xFFFF0000	#enable keybaord interrupt 

	mfc0 $t2, $12		#Read the status Reg
	ori  $t2, $t2, 0x8801	#Enable timer and keyboard interupt	
	mtc0 $t2, $12		#Save it back in

Forever:			#This code runs the timer forever (keeps it updating)
	la $t5, D_Time		#Create a Temp to be used in Display_String
	j Display_String	#Jump to Display String for timer

	
Display_String:
	lb   $t6, 0($t5)	#Create pointer 
	addi $t4, $zero, 8	#t4 hold ascii for backspace
	addi $t3, $zero, 6	#Hold counter for blakcspace
	Backspace:
		beqz $t3, POLL	
		lw   $t7, 0xffff0008	#Create a temo of Display control value		
		andi $t7, $t7, 0x1	#Reads if the register is ready
		beqz $t7, Backspace	#Checks if the register is ready, if not 						#try again
		sw   $t4, 0xffff000c	#Store it into display
		addi $t3, $t3, -1	#Count down backspace counter
		j Backspace

	POLL:
		lw   $t7, 0xffff0008	#Create a temo of Display control value	
		andi $t7, $t7, 0x1	#Reads if the register is ready
		beqz $t7, POLL		#Checks if the register is ready, if not 						#try again
		sw   $t6, 0xffff000c	#Saves the pointer to data reg
		addi $t5, $t5, 1	#Increment the address up one
		lb   $t6, 0($t5)	#Create pointer 
		beq  $t6, $zero, Return	#When all the clock char are printed exit

		j POLL			#If not all printed keep going
	Return:
		j Forever	 	#Return to the loop


.kdata
	Zero:				#Used as placeholder to store temp ref value
		.word 0
	One:
		.word 0
	Two:
		.word 0
	Three:	
		.word 0 
	Four:
		.word 0 
	Five:
		.word 0


.ktext 0x80000180

mfc0 $t0, $13				#Reads cause register
andi $t0, $t0, 0x00FC 			#Check to see if it is Hardware Inter
bnez $t0, Exit_Now			#If it is Exit	
					
sw   $t0, Zero				#Saves the Temp reg
sw   $t1, One
sw   $t2, Two
sw   $t3, Three
sw   $t4, Four
sw   $t5, Five


mfc0 $t0, $13				#Reads cause register
andi $t1, $t0, 0x0800			#Masked the interrupt to check for Key
andi $t0, $t0, 0x8000			#Masked the interrupt to check for Time
srl  $t1, $t1, 11	  		#Isolate for keyboard
srl  $t0, $t0, 15			#Isolate for Time
bnez $t1, Key_int  			#Checks for keyboard
bnez $t0, Time_int			#Check for Timer
j Exit_Now				#If it is not any of these check exit


Key_int:

	lw  $t2, 0xFFFF0004		#Load key input
	addi $t3, $zero, 113		#t3 store ascii of "q"
	beq $t2, $t3, Exit_End		#If it is pressed exit
	addi $t3, $zero, 114		#t3 store ascii of "r"
	beq $t2, $t3, Reset		#Run Reset timer
	j Exit_Except			#If it is neither exit

	Reset: 
		la $t2, D_Time		#Load Address of D_time
		addi $t4, $zero, 48	#Ascii Key for 0
		sb $t4, 0($t2)		#Store 0 in the correct places
		sb $t4, 1($t2)
		sb $t4, 3($t2)
		sb $t4, 4($t2)
		j Exit_Except		#Exit

Time_int:
	la $t2, D_Time			#Load Addess of D_Time
	move $t4, $zero
	addi $t4, $t4, 57		#Load ascii for 9 
	move $t5, $zero
	addi $t5, $t5, 48		#Load ascci for 0


	lb $t3, 4($t2)			#Load the second value
	beq $t3, $t4, Ten_Sec		#If it is 9 sec jump
	addi $t3, $t3, 1		#++ The Single Sec
	sb $t3, 4($t2)			#Store it back in
	mtc0 $zero, $9			#Restore Reg 9
	j Exit_Except			#Exit

	Ten_Sec:
		sb $t5, 4($t2)		#Store 0 into the old num placement
		lb $t3, 3($t2)		#Load the next one
	   	move $t4, $zero		
		addi $t4, $t4, 53	#Change the max counter to 5
		beq $t3, $t4, Sing_Min	#If it is = 5 go to min
		addi $t3, $t3, 1	#If not ++ 10s of sec
		sb  $t3, 3($t2)		#Store it back
		mtc0 $zero, $9		#Restore Co-proccessor Reg
		j Exit_Except		#Exit
	Sing_Min:
		sb $t5, 3($t2)		#Store 0 in 10s of second place
		lb $t3, 1($t2)		#Load the Min bit
	   	move $t4, $zero		
		addi $t4, $t4, 57	#Change max back to 9
		beq $t3, $t4, Ten_Min	#Check if it is = 9
		addi $t3, $t3, 1	#Add 1 to min 
		sb  $t3, 1($t2)		#Store it back in 
		mtc0 $zero, $9		#Restore Co-proccessor Reg
		j Exit_Except		#Exit
	Ten_Min:
		sb $t5, 1($t2)		#Stores 0 in the 1s min place
		lb $t3, 0($t2)		#Load the 10s place
	   	move $t4, $zero		
		addi $t4, $t4, 53	#Change the max counter to 5
		beq $t3, $t4, Over_hour	#If it goes over the 9 
		addi $t3, $t3, 1	#If not ++ 10s of hour
		sb  $t3, 0($t2)		#Store the new value
		mtc0 $zero, $9		#Restore Co-processor Reg
		j Exit_Except		#Exit
	Over_hour:
		sb $t5, 0($t2)		#Store 0 in the 10s of hour
		mtc0 $zero, $9		#Retore Co-Processpr Reg
		j Exit_Except		#Exit


Exit_Except:

	lw   $t0, Zero		#Restoreing temp variables
	lw   $t1, One
	lw   $t2, Two
	lw   $t3, Three
	lw   $t4, Four
	lw   $t5, Five

	mtc0 $zero, $13
	mfc0 $t1, $12		#Read the status Reg
	ori  $t1, $t1, 0x8801	#Enable timer and keyboard interupt	
	mtc0 $t1, $12		#Save it back in
	eret			#Return to main program

Exit_Now:
	mtc0 $zero, $13
	mfc0 $t1, $12		#Read the status Reg
	ori  $t1, $t1, 0x8801	#Enable timer and keyboard interupt	
	mtc0 $t1, $12		#Save it back in
	eret			#Return to main program
 
Exit_End:
	li $v0, 10		#Exit the program
	syscall



