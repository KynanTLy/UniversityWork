# The following format is required for all submissions in CMPUT 229
	
#---------------------------------------------------------------
# Assignment:           5
# Due Date:             April 10, 2015
# Name:                 Kynan Ly
# Unix ID:              Kynan
# Lecture Section:      B1
# Instructor:           Jeeva Paudel 
# Lab Section:          H01 (Monday 1400 - 1650)
# Teaching Assistant:   Raphael Ernani Rodrigues
#---------------------------------------------------------------

#---------------------------------------------------------------
#	The purpose of this program is to write a MIPS program that implements reads a MIPS Code and determinds the amount of Basic Blocks in the code as well as edges. 
#	It should also be able to determinded each Basic Block and their length, the edges of each blocks if it exist, and Dominators of the Blocks 
#		
#	The Program should be able to account for duplicated and delete them as they appear/ Dead Blocks/ Jumps
#	
#
# Register Usage:
#
#	$s0 = Pointer to $a0
#	$s1 = Loads the Actual address of $a0
#	$s2 = Pointer to Temp List
#	$s3 = Pointer to Block List
#	$s4 = Held the Block number / Pointer to Edge List
#	$s5 = Pointer to Temp2 List
#	$s6 = Hold Number of Basic Block
#	$s7 = Hold Number of Edges 
#	$s8 = Pointer to Dominator List
#
#	$t0 = Holds Branch Offset 
#	$t1 = Holds the target address of Branch or Jump instruction
#	$t2 = Temp used to search through temp list
#	$t3 = Temp used for current min
#	$t4 = Temp used for current min of leader address  
#	$t5 = Held first Header address 
#	$t6 = Held second Header address 
#	$t7 = Temp used to search through block / store target address for Edge
#	$t8 = Count bit length / Temp used in Bit vector calculations for source
#	$t9 = Temp used in Bit vector calculations for target
#
#	Reg overwritten in Checking for Duplicate:
#	$t0 = Holds source in Temp2
#	$t1 = Holds target in Temp2
#	$t2 = Holds previous Edge source input
#	$t3 = Holds previous Edge target input
#
#	Reg overwritten in Dominator:
#	$t0 = Holds the change counter
#	$t5 = Reg that finds the block number / temp to search through block
#	$t6 = Holds address of Source / temp used in bit vector calculation / temp see if there was a change
#
#References: 
#	Spim Quick Reference https://www.cs.tcd.ie/~waldroj/itral/spim_ref.html
#
#---------------------------------------------------------------


	.data
Temp: 
	.align 2
	.space 500
Temp2:
	.align 2
	.space 500
Block:
	.align 2
	.space 500
Edge:
	.align 2
	.space 500
Dominator:
	.align 2
	.space 500

	.text

getControlFlow:

	Allocate:
		addi $sp, $sp, -36
		sw $s0, 0($sp)		#Save value of $s0
		sw $s1, 4($sp)		#Save value of $s1	
		sw $s2, 8($sp)		#Save value of $s2
		sw $s3, 12($sp)		#Save value of $s3
		sw $s4, 16($sp)		#Save value of $s4
		sw $s5, 20($sp)		#Save value of $s5
		sw $s6, 24($sp)		#Save value of $s6
		sw $s7, 28($sp)		#Save value of $s7
		sw $s8, 32($sp)		#Save value of $s8

	Start:
		la $s1, 0($a0)			#Creates the pointer to $a0
		la $s2,	Temp			#Creates the pointer to Temp
		lw $s0, 0($s1) 			#Load first line of code
		sw $s1, 0($s2)			#Store the first line of code as leader address
		addi $s1, $s1, 4		#increment to the next line of code
		addi $s2, $s2, 4		#increment the list	
		la $s3, Block			#Pointer to Block
		la $s5, Temp2			#Pointer to Temp2
		move $s6, $zero			#Set Basic Block number to 0
		move $s7, $zero			#Set Number of Egdes to 0
	
	Loop:		
		j CheckNew

	NewBlock:	
		lw   $s0, 0($s1)		#Load new in instruction
		beq  $s0, -1, Cal		#Calculate the block numbers
		j Loop

	CheckNew:	
		lw   $s0, 0($s1) 		#Load the next instruction
		move $t0, $s0			#Save into $t0 the Branch instruction
		srl  $s0, $s0, 26		#Shift branch instruction so only Opcode remains
		beq  $s0,   2, JumpIn		#Check to see if jump instruction
		beq  $s0,   3, JumpIn		#Check to see if jump instruction
		beq  $s0,   1, BranchIn		#Check to see if branch instruction
		beq  $s0,   4, BranchIn		#Check to see if branch instruction
		beq  $s0,   5, BranchIn		#Check to see if branch instruction
		beq  $s0,   6, BranchIn		#Check to see if branch instruction
		beq  $s0,   7, BranchIn		#Check to see if branch instruction

		#=====Check to see if it Jump Register=====#
		andi $t0, $t0, 0xFFFF		#Mask Out the first 16 bits
		srl  $t1, $t0, 16
		andi $t1, $t1, 0xFC1F		#Clear the s bits
		sll  $t1, $t1, 16	
		add  $t0, $t1, $t0		#Restore the rest of the address 
		beq  $t0,   8, JumpReg		#Check to see if jr instruction 

	CheckNew_Loop:
		addi $s1, $s1, 4		#Increment into next instruction
		j NewBlock			#Return back 

	BranchIn:
		lw   $t0, 0($s1)	
		andi $t0, $t0, 0xFFFF		#Mask the Branch Offset
		sll  $t0, $t0, 2
		addi $t0, $t0, 4		#Add 4 to offset	
		sll  $t0, $t0, 14		#Shift right the Branch Offset
		sra  $t0, $t0, 14		#Sign extend the Branch Offset
		addu $t1, $t0, $s1		#Adds the value of Branch to current to get the target address

		#======Storing Headers======#
		sw   $t1, 0($s2)		#Save the target in temp
		addi $s2, $s2, 4		#Increment temp list
		addi $s1, $s1, 4
		sw   $s1, 0($s2)		#Save the next instruction after branch in temp
		addi $s2, $s2, 4		#Increment temp list

		j NewBlock

	JumpIn:
		move $t1, $s1			#Create temp of instruction address in $t1
		addi $t1, $t1, 4		#Add 4 to jump address PC
		lw   $s0, 0($s1) 		#Load the next instruction
		move $t0, $s0			#Create temp of jump instruction
		sll  $t0, $t0, 6
		srl  $t0, $t0, 6
		srl  $t1, $t1, 28	
		sll  $t1, $t1, 28		#Extract bit 28-31	
		sll  $t0, $t0, 2		#Clear OpCode in jump instruction
		or   $t1, $t0, $t1		#Or to generate target address in $t1
	
		#======Storing Header======#			
		sw   $t1, 0($s2)		#Save the target in temp
		addi $s2, $s2, 4		#Increment temp list
		addi $s1, $s1, 4		#Increment into next instruction
		sw   $s1, 0($s2)
		addi $s2, $s2, 4
		j NewBlock
	
	JumpReg:
		addi $s1, $s1, 4		#Increment into next instructio
		j NewBlock

	#=====Sort Temp List=====#
	Cal:
		addi $t3, $zero, 0		#Clear t3
		la  $s2, Temp			#Restore pointer
		lw  $t4, 0($s2)			#Min value in temp
		sw  $t4, 0($s3)			#Store the current smallest in Block
		addi $s3, $s3, 8		#Increment memory
			
	Cal_Ini:		
		lw   $t2, 0($s2)		#Load the value in Temp list
		beqz $t2, Cal_Fin		#If the list is empty go see if we are done sorting
		addi $s2, $s2, 4		#Increment pointer
		bge  $t4, $t2 Cal_Ini		#If the value is less than the Leader address value search again
		move $t3, $t2			#next Min value in temp
		la   $s2, Temp	
		
	Cal_Restart:
		lw   $t2, 0($s2)		#Load the first value of temp list
		beqz $t2, Cal_Fin		#If the list is empty go see if we are done sorting
		addi $s2, $s2, 4		#Go to next value in temp list
		bgt  $t2, $t3, Cal_Restart	#If Temp value is greater than or equal leader min go back
		ble  $t2, $t4, Cal_Restart	#If Temp value is less than or equal min go back
		move $t3, $t2			#make t3 the new min
		j Cal_Restart	

	Cal_Fin: 	
		beqz $t3, Block_Cal
		beq  $t4, $t3, Block_Cal	#If current min and leader min the same there is nothing more to find
		sw   $t3,  0($s3)		#Store the current smallest in Block
		move $t4, $t3
		addi $s3, $s3, 8		#Increment memory
		la   $s2, Temp			
		j Cal_Ini			#Go back to search for next min
	
	#=====Calculating Block Length=====#
	Block_Cal:
		la $s3, Block			#Restart pointer for Block
		la $s1, 0($a0) 			#Restart pointer to search code
		Block_Loop:
			lw   $t5, 0($s3)		#Load the first value of header
			lw   $t6, 8($s3)		#Load the next value of header
			beqz $t6, Block_Find		#Go to Block_Fin for last block 
			sub  $s4, $t6, $t5		#Sub the values to get block number 		
			srl  $s4, $s4, 2		#Divide the answer by 4
			sw   $s4, 4($s3)		#Store the block number in
			addi $s6, $s6, 1		#Add one to number of blocks
			addi $s3, $s3, 8		#Increment the memory 
			j Block_Loop			#Restart loop
		
		Block_Find:
			lw   $s0, 0($s1) 		#See if next line is done
			beq  $s0, -1, Block_Last	#Check if the next instruction is end of code
			addi $s1, $s1, 4		#Go to the next line of code
			j Block_Find			
			
		Block_Last:
			la  $s0, 0($s1)
			beq $s0, $t5, Block_Finish	#If the last header is the last line end
			sub $s4, $s0, $t5		#Sub the last address with last header
			srl $s4, $s4, 2			#Divide the answer by 4
			sw  $s4, 4($s3)			#Store the block number in 
			addi $s6, $s6, 1		#Add one to number of blocks			
			j Edge_S			#Jump to Egde Calculation
		Block_Finish:
			addi $s4, $zero, 1
			sw  $s4, 4($s3)			#Store the block number in 
			addi $s6, $s6, 1		#Add one to number of blocks		
			j Edge_S

	#=====Start of Edge Calculation=====#
	Edge_S:
		la $s3, Block			        #Restart pointer for Block

		Edge_Next:
			lw   $t2,  0($s3)		#Load the leader address 
			beqz $t2, Check_Dup		#Go to Check Duplicate
			la   $s1, 0($a0)

		Edge_Loop:
			beq  $s1, $t2, Edge_Cal		#Check to see if the address matches a leader address		
			addi $s1, $s1, 4		#Go to next line of code
			j Edge_Loop

		Edge_Cal:

			lw   $t3, 4($s3)		#Save the block value into t3
			addi $t3, $t3, -1		#Subtrack 1(to account for header is also part of Block Length)
			sll  $t3, $t3, 2		#Multiply by 4 (we want it in bytes)
			add  $s1, $t3, $s1		#Add the current address with offset to get last address of block

			lw   $s0, 0($s1)		#Load the instruction at the offset 
			beq  $s0, -1, Edge_CodeEnd
		
			j Edge_Check

		Edge_CodeEnd:
			addi $s1, $s1, -4		#Can't read end						
			lw   $s0, 0($s1)		#Read in last line
			j Edge_Check

		Edge_Check:
			move $t0, $s0			#Save into $t0 the Branch instruction
			srl  $s0, $s0, 26		#Shift branch instruction so only Opcode remains

			beq  $s0,   2, JumpEdge		#Check to see if jump instruction
			beq  $s0,   3, JumpEdge2	#Check to see if jump instruction
			beq  $s0,   1, BranchEdge	#Check to see if branch instruction
			beq  $s0,   4, BranchEdge	#Check to see if branch instruction
			beq  $s0,   5, BranchEdge	#Check to see if branch instruction
			beq  $s0,   6, BranchEdge	#Check to see if branch instruction
			beq  $s0,   7, BranchEdge	#Check to see if branch instruction

			#====Check if jr $====#
			andi $t0, $t0, 0xFFFF		#Mask Out the first 16 bits
			srl  $t1, $t0, 16
			andi $t1, $t1, 0xFC1F		#Clear the s bits
			sll  $t1, $t1, 16	
			add  $t0, $t1, $t0		#Restore the rest of the address 
			beq  $t0,   8, JumpRegEdge	#Check to see if jr instruction 

			#======Storing Header======#	
			addi $s1, $s1, 4		#Increment to next line
			sw   $t2, 0($s5)		#Save Header in Temp2	
			sw   $s1, 4($s5)		#Save Target in Temp2
			#addi $s7, $s7, 1		#Add one to number of Edges
			addi $s5, $s5, 8		#Increment List
			addi $s3, $s3, 8		#Increment List
			j Edge_Next			#Jump Back	
			
		BranchEdge:
			lw   $t0, 0($s1)	
			andi $t0, $t0, 0xFFFF		#Mask the Branch Offset
			sll  $t0, $t0, 2
			addi $t0, $t0, 4		#Add 4 to offset	
			sll  $t0, $t0, 14		#Shift right the Branch Offset
			sra  $t0, $t0, 14		#Sign extend the Branch Offset
			addu $t1, $t0, $s1		#Adds the value of Branch to current to get the target address
			addi $t3, $t3, 4		#Increment to Block length back to full
			add  $t4, $t2, $t3		#Add the Block length to Header to get line after block end		

			#=====Ensure ascending order=====#
			bgt  $t1, $t4, Change
			sw    $t2, 0($s5)		#Store the Leader as source address
			sw   $t1, 4($s5)		#Store the target address for source
			sw    $t2, 8($s5)		#Store the Leader as source address
			sw   $t4, 12($s5)		#Store the the next line in for edge	
			addi $s5, $s5, 16		#Increment the List
			addi $s3, $s3, 8		#Move to the next Leader	
			#addi $s7, $s7, 2		#Add two to number of Edges
			j Edge_Next

			Change:
				sw    $t2, 0($s5)		#Store the Leader as source address
				sw   $t4,  4($s5)		#Store the the next line in for edge
				sw    $t2, 8($s5)		#Store the Leader as source address
				sw   $t1, 12($s5)		#Store the target address for source
				addi $s5, $s5, 16		#Increment Temp2
				addi $s3, $s3, 8		#Move to the next Leader
				#addi $s7, $s7, 2		#Add two to number of Edges
				j Edge_Next			#Jump Back
	
		JumpEdge:			
			move $t1, $s1			#Create temp of instruction address in $t1
			addi $t1, $t1, 4		#Add 4 to jump address PC
			lw   $s0, 0($s1) 		#Load the next instruction
			move $t0, $s0			#Create temp of jump instruction
			sll  $t0, $t0, 6
			srl  $t0, $t0, 6
			srl  $t1, $t1, 28	
			sll  $t1, $t1, 28		#Extract bit 28-31	
			sll  $t0, $t0, 2		#Clear OpCode in jump instruction
			or   $t1, $t0, $t1		#Or to generate target address in $t1

			#======Storing Header======#
			sw   $t2, 0($s5)		#Save Header in Temp2
			sw   $t1, 4($s5)		#Save the target in Temp2
			addi $s5, $s5, 8		#Increment temp list
			addi $s3, $s3, 8
			#addi $s7, $s7, 1		#Add one to number of Edges
			j Edge_Next

		JumpEdge2:
			addi $s1, $s1, 4		#Increment to next line
			sw   $t2, 0($s5)		#Save Header in Temp2	
			sw   $s1, 4($s5)		#Save the target in Temp2
			#addi $s7, $s7, 1		#Add one to number of Edges
			addi $s5, $s5, 8		#Increment List
			addi $s3, $s3, 8		#Increment List
			j Edge_Next			#Jump Back

		JumpRegEdge:
			addi $s3, $s3, 8		#Increment pointer
			j Edge_Next

	#=====Check if there are copys in Edge=====#
	Check_Dup:
		la  $s4, Edge				#Set pointer to Edge
		la  $s5, Temp2				#Set pointer to Temp2
		lw  $t0, 0($s5)				#Load first value in Temp2
		beqz $t0, Dominator_S			#If it is empty then there no edge
		lw  $t1, 4($s5)				#Load first target address in Temp2
		sw  $t0, 0($s4)				#Store the first source address in Edge
		sw  $t1, 4($s4)				#Store the first target address in Edge
		addi $s4, $s4, 8			#Increment Edge
		addi $s5, $s5, 8			#Increment Temp
		addi $s7, $s7, 1			#Increase Edge count by 1

		Check_DupLoop:
			lw  $t0, 0($s5)			#Load the next source in Temp2
			beqz $t0, Dominator_S		#If it is empty it is done
			lw  $t1, 4($s5)			#Load the next target of source in Temp2
			lw  $t2, -8($s4)		#Load the previous source stored in Edges
			lw  $t3, -4($s4)		#Load the previous target stored in Edges
			bne $t0, $t2, Check_Insert	#Check to see if sources are different
			bne $t1, $t3, Check_Insert	#Check to see if targets are different
			addi $s5, $s5, 8 		#If they are the same go to next Temp2 value set
			j Check_DupLoop			#Loop

		Check_Insert:
			addi $s7, $s7, 1		#Add one to number of Edges
			sw  $t0, 0($s4)			#Store Temp2 source in Edge
			sw  $t1, 4($s4)			#Store Temp2 target in Edge
			addi $s4, $s4, 8		#Increment Edges
			addi $s5, $s5, 8 		#Tncrement Temp2
			j Check_DupLoop
			
	#=====Start of Dominator Calculations=====#
	Dominator_S:
			la  $s8, Dominator			#Set pointer back to start of Dominator 
			la  $s3, Block				#Set pointer back to start of Block
			move $t8, $zero
		#=====Find bit Vector Length=====#	
		Dom_BitLen:
			lw $t7, 0($s3)			#Load the first value of block
			beq $t7, 0, Dom_Start		#Check if end of list, if so we are end of bit length
			addi $s3, $s3, 8		#Increment memory
			sll $t8, $t8, 1			#Shift t8 left by 1 to increase bit length
			addi $t8, $t8, 1		#Fill in the 0 from the shift
			j Dom_BitLen		
		
		#=====Setup what the Bit vector looks like=====#				
		Dom_Start:
			la  $s3, Block				#Set pointer back to start of Block
			addi $s3, $s3, 8			#Increment block to the second set of values
			addi $t7, $t7, 1			#The first block dominated by itself only
			sw   $t7, 0($s8)			#Store the first block dominator
			addi $s8, $s8, 4			#Increment memory

		Dom_Loop:
			lw $t7, 0($s3)			#Load the first value of block
			beq $t7, 0, Dom_Cal_Re		#Check if end of list, if so we are done
			sw  $t8, 0($s8)			#Store the bit vector with 1s
			addi $s8, $s8, 4		#Increment to next bitvector
			addi $s3, $s3, 8		#Increment to next value in block
			j Dom_Loop

		#=====Start the Calculation for Bit Vector=====#
		Dom_Cal_Re:
			move $t0, $zero			#Set change counter to 0
			la  $s5, Edge			#Restart pointer to Edge in $s5

		Dom_Cal:		
			lw  $t6, 0($s5)			#Load Source address into t6
			la  $s8, Dominator		#Reset dominator pointer 
			beqz $t6, Dom_CheckEnd		#If list is empty go to Dominator end

			lw  $t7, 4($s5)			#Load Target address into t7			
			move $t8, $zero			#Clear Reg
			move $t9, $zero 		#Clear Reg
			la  $s3, Block			#Restart pointer to Block

		Dom_Source:
			lw  $t5, 0($s3)			#Load value at block
			beq $t5, $t6 Dom_reset		#Check if it is empty 
			addi $t8, $t8, 1		#increment the block number for source
			addi $s3, $s3, 8		#Check next value
			j Dom_Source			#Loop

		Dom_reset:		
			la  $s3, Block			#Restart pointer to Block

		Dom_Target:
			lw  $t5, 0($s3)			#Load value at block
			beq $t5, $t7 Dom_ni_Cal		#Check if it is empty 
			addi $t9, $t9, 1		#increment the block number for target
			addi $s3, $s3, 8		#Check next value
			j Dom_Target

		Dom_ni_Cal:	
			move $t6, $t9			#Load t8 into t6
			move $t5, $zero			#Clear t5 
			addi $t5, $t5, 1		#Start the binary vector at 1

			Dom_ni_loop:
				beqz $t6, Dom_change
				sll $t5, $t5, 1		#Shift t5 into the correct block position
				addi $t6, $t6, -1
				j Dom_ni_loop

		Dom_change:
			sll $t8, $t8, 2		#Find the address of the binary vector of source
			sll $t9, $t9, 2		#Find the address of the binary vector of target
			
			la  $s8, Dominator	#Reset Dominator pointer
			add $s8, $s8, $t8 	#Increment memory to the source bit vector
			lw  $t8, 0($s8)		#Loads dominator in source

 			la  $s8, Dominator	#Reset Dominator pointer
			add $s8, $s8, $t9 	#Increment memory to the target bit vector
			lw  $t6, 0($s8)		#Loads dominator in target

			and $t8, $t8, $t6	#And the 2 vectors 
			or  $t8, $t8, $t5 	#Or the remainder with the block number

			la  $s8, Dominator	#Reset Dominator pointer
			add $s8, $s8, $t9 	#Increment memory to the target bit vector
			lw  $t6, 0($s8)		#Load the old target bit vector
			
			beq  $t6, $t8, Dom_change2	#If they are equal skip the counter
			addi $t0, $t0, 1		#Increase the change counter
			 
		Dom_change2:
			la  $s8, Dominator	#Reset Dominator pointer
			add $s8, $s8, $t9 	#find the memory location of the bit edge
			sw  $t8, 0($s8)		#Store the new binary vector
			addi $s5, $s5, 8	#Increment memory in Dominator 
			j Dom_Cal		#Loop back for next value in edge list

		Dom_CheckEnd:	
			la  $s5, Edge		#Reset Edge pointer
			la  $s8, Dominator	#Reset Dominator pointer		
			bnez $t0, Dom_Cal_Re	#If t0 is 0 (uncahnged) we are done if not loop 
			j Done
			
	Done:
		move  $v0, $s6		#Move the Block number to $v0
		move  $v1, $s7		#Move the Edge number to $v1
	
		#=====Restore S Registers=====#
		lw $s0, 0($sp)		#Restore value of $s0
		lw $s1, 4($sp)		#Restore value of $s1	
		lw $s2, 8($sp)		#Restore value of $s2
		lw $s3, 12($sp)		#Restore value of $s3
		lw $s4, 16($sp)		#Restore value of $s4
		lw $s5, 20($sp)		#Restore value of $s5
		lw $s6, 24($sp)		#Restore value of $s6
		lw $s7, 28($sp)		#Restore value of $s7
		lw $s8, 32($sp)		#Restore value of $s8
		addi $sp, $sp, 36

		#=====Return Values=====#
		la  $t0, Block		#Reset Block pointer
		sw  $t0, 0($sp)		#Store the address of Block to return
		la  $t1, Edge		#Reset Edge pointer
		sw  $t1, 4($sp)		#Store the address of Edge to return
		la  $t2, Dominator	#Reset Dominator pointer
		sw  $t2, 8($sp)		#Store the address of Dominator to return
		jr $ra


