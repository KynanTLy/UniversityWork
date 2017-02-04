# The following format is required for all submissions in CMPUT 229
	
#---------------------------------------------------------------
# Assignment:           2
# Due Date:             Feb 13, 2015
# Name:                 Kynan Ly
# Unix ID:              kynan
# Lecture Section:      B1
# Instructor:           Jeeva Paudel 
# Lab Section:          H01 (Monday 1400 - 1650)
# Teaching Assistant:   Raphael Ernani Rodrigues
#---------------------------------------------------------------

#---------------------------------------------------------------
#	The purpose of this program is to take a MIPS branch instruction and print out the instruction in fully
#	lowercase, prefixing registers with '$', separating registers and addresses with a comma and a space, 
#	only including register $t if it is used in the #branch, and printing the absolute address of the branch 
#	target, in 0xffffffff form. This is done by disassembling a MIPS branch instructions given to the program.
#
# Register Usage:
#
#       a0: Contains the address of the MIPS assembly instruction, Also used as output 
#       v0: Used for syscall
#	t0: Holds the Value at the address
#	t1: Holds the Actual Address of the instruction
#	t2: Holds the Branch Offset
#	t3: Holds the T register
#	t4: Holds the S register
#	t5: Used as a temp register for printing Hex dec for the final answer
#	s0: Used to hold a counter to determind if the T Reg needs to be printed or not
#	s1: Used as a counter to count how many Hex dec address at the end is already printed
#
#
#References: 
#	Spim Quick Reference https://www.cs.tcd.ie/~waldroj/itral/spim_ref.html
#	Convert Dec to Hex   https://www.physicsforums.com/threads/mips-decimal-to-hex.488079/
#			     http://forum.codecall.net/topic/78967-mips-how-to-convert-hex-to-decimal/
#	Ascii Table          http://www.asciitable.com/
#
#---------------------------------------------------------------
	
	.data	
l_bgez:
	.asciiz "bgez "
l_bgezal:
	.asciiz "bgezal "
l_bltz:
	.asciiz "bltz "
l_bltzal:
	.asciiz "bltzal "
l_beq:
	.asciiz "beq "
l_bne:
	.asciiz "bne "
l_blez:
	.asciiz "blez "
l_bgtz:
	.asciiz "bgtz "
l_Comma:
	.asciiz ", "
l_Reg:
	.asciiz "$"
l_Ox:
	.asciiz "0x"

	.text

disassembleBranch: 
	

	move $t1, $a0        #Moves the Address or PC
	lw   $t0, 0($a0)     #Save the Value at $a0

	move $s1, $zero
	move $s0, $zero		#t reg counter
	addi $s1, 8		#Hex coutner



# This section stores the address


	andi $t2, $t0, 0xFFFF #Branch Address
	srl  $t0, $t0, 16
	
	andi $t3, $t0, 0x001F #T Register
	srl  $t0, $t0, 5

	andi $t4, $t0, 0x001F #S Register
	srl  $t0, $t0, 5
	
	#Opcode is store in $t0
	

	

# end store address
	
	beq  $t0, 1, F_Top  	#equal to 1
	beq  $t0, 4, F_Beq 	#equal to 4
	beq  $t0, 5, F_Bne   	#equal to 5
	beq  $t0, 6, F_Blez 	#equal to 6
	beq  $t0, 7, F_Bgtz   	#equal to 7
	jr Exit
F_Top:
	beq  $t3, 0, F_Bltz 	#equal to 0
	beq  $t3, 1, F_Bgez   	#equal to 1
	beq  $t3, 16, F_Bltzal 	#equal to 16
	beq  $t3, 17, F_Bgezal  #equal to 17	

F_Beq:
	la   $a0, l_beq
	addi $s0, $s0, 1
	jr Results
F_Bne: 
	la   $a0, l_bne
	addi $s0, $s0, 1
	jr Results
F_Blez:  
	la   $a0, l_blez
	jr Results
F_Bgtz:  
	la   $a0, l_bgtz
	jr Results
F_Bltz:  
	la   $a0, l_bltz
	jr Results
F_Bgez:  
	la   $a0, l_bgez
	jr Results
F_Bltzal:  
	la   $a0, l_bltzal
	jr Results
F_Bgezal:  
	la   $a0, l_bgezal
	jr Results


#####

Results:
	li   $v0, 4
	syscall  	   #print opcode
	la   $a0, l_Reg	
	li   $v0, 4
	syscall	           #Prints $
	move   $a0, $t4
	li   $v0, 1
	syscall            #Print the S register
	la   $a0, l_Comma 
	li   $v0, 4 
	syscall		   #Prints the comma
	
	beq  $s0, 1, TReg  #Checks if we need the TReg
	jr Results2
TReg: 
	la   $a0, l_Reg	
	li   $v0, 4
	syscall
	move   $a0, $t3
	li   $v0, 1
	syscall
	la   $a0, l_Comma 
	li   $v0, 4 
	syscall	

Results2:
	
	srl  $s3, $t1, 16  
	sll  $s3, $s3, 16   #Saves the first most 16 bits (to prevent it from being changed from overflow)

	sll  $t2, $t2, 2    #Offset the Branch Address the x4
	addi $t2, 4   	    #Does all the changes for Branch Code
	add  $t2, $t1, $t2  #Add the two address together (orginal address of the MIPS instruction with the changed branch offset)
	sll  $t2, $t2, 16
	srl  $t2, $t2, 16   #Removes overflow 

	add $t1, $s3, $t2   #Combines back the 16 bit first most digits of the orginal address with the last 16 bit of the new address

	
	la   $a0, l_Ox	    #Prints the 0x
	li   $v0, 4
	syscall	

Next_Hex:
	beqz $s1, Exit		       #If there is no more hex to print exit
	srl  $t5, $t1, 28
	bgt  $t5, 10, Convert_B        #If the hex is less than 10

#For hex lower than 10
Convert_L:
	addi  $t5, 48     #Turns it into ascii key	 	
	move $a0, $t5
	li   $v0, 11
	syscall	 
	sll  $t1, $t1, 4
	subu  $s1, $s1, 1
	jr Next_Hex

#Hex bigger than 10
Convert_B:	
	addi  $t5,  87     #Turns it into ascii key	 
	move $a0, $t5
	li   $v0, 11
	syscall	 
	sll  $t1, $t1, 4	
	subu  $s1, $s1, 1
	jr Next_Hex

Exit:
	jr $ra


		
   
	


