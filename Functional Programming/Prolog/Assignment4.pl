:- use_module(library(clpfd)).

%================Question1=================%  
/*
It's an interesting theorem from number theory that every natural number can be expressed as the sum of four or fewer squares.

The function has a couple of restriction such as S1 #=< S2, S2 #=< S3, S3 #=< S4, and then using label it finds values that fit these condition

TestCases:
fourSquares(2,N) return N = [0, 0, 1, 1] 
fourSquares(3,N) return N = [0, 1, 1, 1] 
fourSquares(37,N) return N = [0, 0, 1, 6] 
fourSquares(2468,N) return N = [0, 2, 9, 49] 

*/

fourSquares(N, [S1, S2, S3, S4]):-
	Vars = [S1, S2, S3, S4],
	Vars ins 0..N,
	S1 #=< S2, 
	S2 #=< S3, 
	S3 #=< S4,
        label(Vars),
	N #= S1*S1 + S2*S2 + S3*S3 + S4*S4.

%================Question2=================%    
/*
Two countries have signed a peace treaty and want to disarm over a period of months, 
but they still don't completely trust each other. Each month one of the countries can choose to dismantle one military division while the other can dismantle two. 
Each division has a certain strength, and both sides want to make sure that the total military strength remains equal at each point during the disarmament process. 
The 3rd new parameter is used to ensure that the previous sum is less than the current sum
Label restrictions for the variables. 
The "select" does 2 part one remove var from list and second ensure var is a member of the list

TestCases:
disarm([],[],[]) = true
disarm([1,3,3,4,6,10,12],[3,4,7,9,16],S) = [[[1, 3], [4]], [[3, 4], [7]], [[12], [3, 9]], [[6, 10], [16]]]
disarm([1,2,3,3,8,5,5],[3,6,4,4,10],S) =[[[1, 2], [3]], [[3, 3], [6]], [[8], [4, 4]], [[5, 5], [10]]]
*/
disarm(Adiv, Bdiv, Solution):- disarm(Adiv, Bdiv,0,Solution).

disarm([],[],_,Solution):- 
    Solution = [].

disarm(Adiv, Bdiv, Value, Solution):-
    Vars = [A1, A2, B1],
    A1 + A2 #= B1,
    A1 #=< A2, 
    Value #=< B1, 
    select(A1, Adiv, NewAdiv),
    select(A2, NewAdiv, NewAdiv2),
    select(B1, Bdiv, NewBdiv),
    disarm(NewAdiv2, NewBdiv, B1,NewSolution),
    append([[[A1,A2],[B1]]] , NewSolution, Solution),
    label(Vars). 

disarm(Adiv, Bdiv, Value,Solution):-
    Vars = [A1, B1, B2],
    B1 + B2 #= A1,
    B1 #=< B2,
    Value #=< A1, 
    select(A1, Adiv, NewAdiv),
    select(B1, Bdiv, NewBdiv),
    select(B2, NewBdiv, NewBdiv2),
    disarm(NewAdiv, NewBdiv2, A1, NewSolution),
    append([[[A1],[B1,B2]]] , NewSolution, Solution),
    label(Vars).



