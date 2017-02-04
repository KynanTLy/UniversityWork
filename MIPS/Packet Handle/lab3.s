# The following format is required for all submissions in CMPUT 229
	
#---------------------------------------------------------------
# Assignment:           3
# Due Date:             March 9, 2015
# Name:                 Kynan Ly
# Unix ID:              Kynan
# Lecture Section:      B1
# Instructor:           Jeeva Paudel 
# Lab Section:          H01 (Monday 1400 - 1650)
# Teaching Assistant:   Raphael Ernani Rodrigues
#---------------------------------------------------------------

#---------------------------------------------------------------
#	The purpose of this program is to write a MIPS program that implements the function #	handlePacket. This function takes a single argument in $a0, the address of the start #	of an IP packet in memory. 
#		
#	It should check if the IP version is valid followed by Checksum then finally TTL. If #	it meets all these conditions then the packet should be forwarded with a TTL-1 and a #	new checksum that is created with the new TTL.  
#	
#
# Register Usage:
#
#	$a0: Input Reg that holds the IP Address
#	$v0: Return 0 if there is an error in the packet otherwise 1	
#	$v1: Return what type of error in the from of (0-2) else the address of the packet 
#
#	$t0: temp used to check if IP version is 4
#	$t1: temp used to check if TTL is greater than 1
#	$t2: Used to hold packet header length 
#	$t3: Holds the orginal Header Checksum
#	$t4: The Accumulator
#	$t5: temp pointer used to point at current half word for checksum
#	$t6: The halfword in checksum
#	$t7: temp used for endian conversion
#	$t8: the (carryout, sum)
#	$t9: holds the "sum" from $s1
#
#	$s0: temp that hold packet header length for checksum
#	$s1: Holds counter if packet needs to be forwarded or not
#
#References: 
#	Spim Quick Reference https://www.cs.tcd.ie/~waldroj/itral/spim_ref.html
#
#---------------------------------------------------------------


handlePacket:

	sw $s0, 0($sp)		#Save value of $s0
	sw $s1, 4($sp)		#Save value of $s1	

	move $v0, $zero
	move $v1, $zero	

	lw $t0, 0($a0)		#Store values from IP packet 0-31
	lw $t1, 8($a0)		#Store values from IP packet 64-95
				
	#sll $t0, $t0, 16 	#Gets rid of the right half of the 0-31 bits
	andi $t2, $t0, 0x000F	#Mask out packet header length
	andi $t0, $t0, 0x00F0	#Mask out IP version
	#srl $t2, $t2, 8	#$t2 holds the length of the packet header length
	
	srl $t0, $t0, 4		#Shift $t0 so it only holds IP version

	#===Drop Condition===#	
	bne $t0, 4, DropIP 	#Checks if IP version is 4 if not drop    		
	
	sll $t1, $t1, 24	#shift out not needed bytes (to get only TTL)
	srl $t1, $t1, 24	#$t1 holds TTL
		
	lhu $t3, 10($a0)	#Load halfword Header checksum
	move $s1, $zero		#Set forwarded counter to 0
	sh $zero, 10($a0)	#Set header to 0 in IP packet

Setup:
	move $t4, $zero		#Set Accumulator = 0
	move $t8, $zero		#Set (Carryout, Sum) = 0
	move $t5, $a0 		#Set pointer at $a0
	move $s0, $t2		#temp of packet header length value
	sll  $s0, $s0, 1	#Double the amount that in packet header length for loop 

Checksum:
	beqz $s0, Compare       #If packet value is zero we are done
	lhu $t6, 0($t5)		#Load half word into $t6
	addi $t5, $t5, 2	#Move pointer up 16 to next half word

	#===Convert endian===#
	andi $t7, $t6, 0x00FF	#Save the first half of the half word
	sll $t7, $t7, 8		#Swap places of the 2 bytes
	srl $t6, $t6, 8
	add $t6, $t6, $t7	#Add the two register back together

	#===Addition of Accumulator and (Carryout, Sum)===#
	move $t9, $zero		#Clear reg
	move $t8, $zero		#Clear reg
	add $t8, $t6, $t4	#Add Accumulator + Hi in $t8(Carryout, Sum)
	andi $t9, $t8, 0xFFFF	#Mask Sum
	srl  $t8, $t8, 16	#Shift 16 right to get carryout	
	add $t4, $t9, $t8 	#Accumulator = Sum + Carryout 		
	
	sub $s0, $s0, 1		#Reduce the count of total packet
	j Checksum 		#Loop again

Compare:
	#===Convert endian===#
	andi $t7, $t4, 0x00FF	#Save the first half of the half word
	sll $t7, $t7, 8		#Swap places of the 2 bytes
	srl $t4, $t4, 8
	add $t4, $t4, $t7	#Add the two register back together

	#===Logical Complement===#
	not $t4, $t4		#Finds the Logical complement of Accumulator	
	sll $t4, $t4, 16	
	srl $t4, $t4, 16	#Now $t4 only holds checksum

	bnez $s1, Forward	#If forward counter != 0 then it is ready to be forwarded

	#===Drop conditions===#
	bne $t4, $t3, DropCheck	#Compare Accumulator with Header Checksum
	ble $t1, 1, DropTTL	#Checks if TTL is greater than 1

	#===Set up IP packet to be forward===#
	addi $t1, $t1, -1	#Lower TTL by 1
	sb $t1, 8($a0)		#Store in new TTL in IP packet
	addi $s1, 1		#Set forward counter to 1
	j Setup			#jump to setup to calculate new Header checksum

DropIP:
	move $v0, $zero		#Sets the returns values for IP fail case
	addi $v1, 2
	lw $s1, 4($sp)		#Restore value of $s1
	lw $s0, 0($sp)		#Restore value of $s2
	j $ra	
	
DropTTL:
	move $v0, $zero 	#Sets the return values for TTL fail case
	addi $v1, 1
	lw $s1, 4($sp)		#Restore value of $s1
	lw $s0, 0($sp)		#Restore value of $s2
	j $ra
	
DropCheck:
	move $v0, $zero 	#Sets the return values for Checksum fail case
	addi $v1, 0
	lw $s1, 4($sp)		#Restore value of $s1
	lw $s0, 0($sp)		#Restore value of $s2
	j $ra 

Forward: 
 	
	sh $t4, 10($a0)		#Store Accumulator or new header in header checks
	addi $v0, 1		#Set $v0 to 1
	la $v1, 0($a0)		#Store the source address into $v1
	lw $s1, 4($sp)		#Restore value of $s1
	lw $s0, 0($sp)		#Restore value of $s2
	j $ra




























