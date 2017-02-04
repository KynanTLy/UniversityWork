;QUESTION 1
;Takes in an argument X and determinds if it exist in list Y
;Returns 'T' if it does
;Returns 'NIL' if it does not

;TESTCASES
;(xmember '1 '(1)) -> T
;(xmember '1 '( (1) 2 3)) -> NIL
;(xmember '(nil) '(1 2 3 (nil))) -> T
;(xmember '(nil) '(nil)) -> NIL


(defun xmember (X Y)
	(if (NULL Y)
		NIL
		(if (equal X (car Y))
			T
			(xmember X (cdr Y))
		)	
	)
)

;QUESTION 2
;(Question 1 should not use any functions beyond this point)
;Takes in an argument x, returns a list of 'atoms' with te roperty that all the atoms will apear in order and without nested list

;TESTCASES
;(flatten '(a (b c) d)) -> (A B C D)
;(flatten '(a (b c) (d ((e)) f))) -> (A B C D E F)

;(if statement is true) Condition that the outer most list contain 1 elements (atom or nested list)
;(if statement is false) Condition that there is more than 1 element in outer most list 
(defun flatten (x)
	(if (equal nil (cdr x))
		(cond
			((atom (car x)) (cons (car x) nil))
			(t (flatten (car x)))
		)
		(if (atom (car x))
			(cons (car x)(flatten (cdr x)))
			(append (flatten(car x))(flatten (cdr x))) 
		)
	)
)

:QUESTION 3
;(question 2 should not use any function beyond this point)
;Creates a new list where it chooses elements from L1 and L2 alternatingly, if one list is shorter append the rest of the longer list as remainder

;TESTCASES
;(mix '(a b c) '(d e f)) -> (A D B E C F)
;(mix '((a) (b c)) '(d e f g h))  -> ((A) D (B C) E F G H)
;(mix '(1 2 3) '(nil)) -> (1 NIL 2 3)

(defun mix (L1 L2)
	(cond
		((equal L2 nil) L1)
		((equal L1 nil) L2)
		;Create a list with the first elements of both list 
		(t (append (cons (car L1) (list (car L2)) ) (mix (cdr L1)(cdr L2))  ))
	)
)

:QUESTION 4
;(question 3 should not use any function beyond this point)
;Splits the argurment of L into two sublist of L1 and L2 putting elements from L into each of them alternatingly 

;TESTCASES
;(split '(1 2 3 4 5 6)) -> ((1 3 5) (2 4 6))
;(split '()) -> (NIL NIL)

;This helper function deals with every second element (since we start at index 0, this represent odd numbers in list)
(defun even (L)
	(cond
		((equal (cdr L) nil) nil)
		(t ( cons (cadr L) (even(cddr L))  )  )
	)
)

;This helper function deals with every first element (since we start at index 0, this represent even numbers in list)
(defun odd (L)
	(cond
		((equal (car L) nil) nil )
		(t ( cons (car L) (odd(cddr L))  )  )
	)
)

;(1st condition) Check if the 1st element of L is nil
;(2nd condition) Check if the remainder is L
(defun split (L)
	(cond
		((equal (car L) nil) (cons nil (list nil ))    )
		((equal (cdr L) nil)  (cons L (list nil ))      )
		(t ( cons (odd L) (list(even L)) )  )	

	)
)

;QUESTION 5.1
#|
	It is false, if both L1 and L2 are the same length then it would return (L1 L2):
	(split (mix '(a b c) '(d e f))) -> ((A B C)(D E F))
	but if L2 or L1 is larger than the other list, then the result will be:
	(split (mix '(a b c) '(d e f g h i))) -> ((A B C G I) (D E F H))
	Therefore it is turn when L1 and L2 are of similar length 
|#

;Question 5.2
#|
	It is true that (mix (car (split L)) (cadr (split L))) returns L
	this is beacuse the first argument passed into mix is going to use L1 of the 2 list returned by split
	This is because split returns 2 list, L1, L2 so calling (car (result of split) would return L1 and (cadr (split result) returns L2
	the second argument passed into mix is going to use L2 of the of the 2 list return by split
	Therefore at this point we have (mix L1 L2) since the content of L1 is then every other number in the original list
	and L2 contains the remaining numbers that are not in L1 (think of it as one list has all even numbers while the others have all odd)
	now mix will alternate these values from both list back into one
	For example we start with orignal list (1 2 3 4) we split it into 2 list (1 3) (2 4)
	now when we call mix it would first take '1' from the first list and then '2' then '3' and fianlly '4'
	then after we have a list of '1 2 3 4'
|#

;QUESTION 6 
;(question 4 should not use any function beyond this point)
;Takes in a list L and a sum S and solves for subset sum

;TESTCASES
;(subsetsum '(1 16 2 8 4) 29) -> (1 16 8 4)
;(subsetsum '(1 10 100 1000 10000) 5) -> NIL
;TESTCASE HARDER EXAMPLE on https://eclass.srv.ualberta.ca/mod/page/view.php?id=1734997
;3rd last example result: (1856672462 830344358 2991012918 1110022518 3162482966 2963950958 2867044966 3613088166)
;3rd last example time: 0.757 Seconds
;2nd last example result: (3053682294 989689934 1515966654 3190353094 2881716470 46278310 2677728118 2498916686 995636870)
;2nd last example time: 1.750 Seconds
;last example result: (409222606 3110790126 605755950 702212686 982160526 1893632342 4144279214 621612798 4254335638 3610001574)
;last example time: 0.045 Seconds


;Removes any element in the list that is bigger than the sum 
(defun toobig (L S)
	(cond
		((equal L nil) nil )
		(t 	( let  (  (x  (toobig (cdr L) S) )	)
				(if (> (car L) S)
					x
					(cons (car L) x)
				)
			)
		)
	)
) 


;A helper function of returnList that checks if an element exist in a list
(defun isInList (E L)
	(cond
		((null L) nil)
		((equal E (car L))  T  )
		(t(isInList E (cdr L)) )
	)
)


;Return a list of values that are a subset of the sum in the orignal order given
;L1 is orginalList
;L2 is the sorted (Largest to smallest) subsetsumlist
(defun returnList (L1 L2)
	(cond 
		((null L2)  nil)
		((null L1) nil)
		( t	(if (isInList (car L1) L2)
				(cons (car L1) (returnList (cdr L1) L2))
				(returnList (cdr L1) L2)
			)
		)
	)
)


;Does most of the recursion involving the list
;(1st condition) If the element is part of the subset sum return T (also at the last element of the subset sum this 'T' gets appended to the list)
;(2nd condition) Therefore needing the removeT function to get rid of it.  
;(3rd condition) If not part of the subset (sum < 0) then return nil	
;(the t condition) Check to see if the first element of the list is part of subset sum (return T if it is, nil otherwise) 
(defun sub (L S)
	(cond
		(  (= S 0) (list T)                   			 )  	
		(  (< S 0) nil             	 				 )
		(  (equal L nil) nil						 )
		(t	(let  (  (x  (sub (cdr L) (- S (car L)) ) ) 	  )	
				(if x			
					;If T, then construct the list with the 1st element and repeat the process with the remaining elements
					;If nil, then the 1st element is not part of the subset sum, therefore move onto the next element 
					(cons (car L) x)
					(sub (cdr L) S)
				)
			)		
		)
	)	
)

;The function which calls the helper functions
;Inner most argument calls toobig to remove elements that are too big
;Middle arguement runs through 'sub' to determind the subset sum
;Outer call returnList sorts the subset sum list back into its orginal order given
;x is a copy of the orignal list
(defun subsetsum (L S)
	(let ( (x (sort (copy-list L) #'>) ) (y S))
		(returnList L (sub (toobig x y) y)))
)









(subsetsum '(2689830222 2889728646 306236574 3833593622 615250798 506980646 2573438398 174965942 1838208910 1123038662 3929869534 2632794198 1095647662 971196518 3795610110 791286262 3216923086 3797139718 497992990 245750166 2892578798 1307197350 887045694 1960326966 1722094606 2572510278 4147574110 2932787926 2947937838 3404320486 3294431870 3670881398 1592657486 1463951238 592879006 3689747478 600663150 3957257766 3081162430 599656886) 12784270306)







