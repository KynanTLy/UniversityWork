
# The following format is required for all submissions in CMPUT 229
	
#---------------------------------------------------------------
# Assignment:           1
# Due Date:             January 26, 2015
# Name:                 Kynan
# Unix ID:              kynan
# Lecture Section:      B1
# Instructor:           Jeeva Paudel
# Lab Section:          H01 (Monday 1400 - 1650)
# Teaching Assistant:   Raphael Rodrigues
#---------------------------------------------------------------

#---------------------------------------------------------------
# This program perform endianness conversion, an operation that is commonly  
# required when sending data over a computer network on a single integer
#
# Spim Quick Reference https://www.cs.tcd.ie/~waldroj/itral/spim_ref.html
# 
# Inputs:
# 	Takes in an int from the user 
#
# Register Usage:
#
#	v0: receive the read_int syscall
#       t0: contains the orginal input number
#       t1: contains the next byte needed to be transfer to t2
#	t2: contains the new big-endian integer
#	a0: receive and prints out final answer
#---------------------------------------------------------------
.text

main:	
#	Set t2 for the program as well as take in the int

	move $zero, $t2		#Make t2 have a value of 0
	li $v0, 5		#Return to the OS by call sys call no. 5
	syscall			#System call
	move $t0, $v0		#Move out the input value to t0
	
loop:
#	Checks to see if the conversation is complete

	beqz $t0, done 		#Check if t0 is equal to 0

#	Shifts t2 in preperation for the next input

	sll  $t2, $t2, 8 	#Shift left 8-bits of t2
	andi $t1, $t0, 0x00FF	#Create a mask

#	Shifts t0 in preperation to transfer the next byte, also
#	used to indicate (when empty) if the converstion is complete

	srl  $t0, $t0, 8	#Shifts the values of t0 right by 8 bits

#	add the byte into t2

	or   $t2, $t1, $t2	#Add the value of t1 to t2
	j loop			#Loops 
done:
#	Print the new value

	li $v0, 1		#Return to the OS by call sys call no. 1
	move $a0, $t2		#Move value at t2 to a0
	syscall			#Make the actual call
	
	

	
