%===Question1===
%Define the predicate xreverse(L, R) to reverse a list, where L is a given list and R is either a variable or another given list.

%Test Cases:
%xreverse([7,3,4],[4,3,7]) return yes, 
%xreverse([7,3,4],[4,3,5]) return no, 
%xreverse([7,3,4], R) return R = [4,3,7].

xreverse([A|A2],Temp,C) :- xreverse(A2,[A|Temp],C).
xreverse([],C,C).
xreverse(A,B):- xreverse(A,[],B).

%===Question2===
%Define the predicate xunique(L, Lu) where L is a given list of atoms and Lu is a copy of L where all the duplicates have been removed. Lu can be either a variable, or a given list. The elements of Lu should be in the order in which they first appear in L.

%Test Cases:
%xunique([a,c,a,d], L) return L = [a,c,d], 
%xunique([a,c,a,d], [a,c,d]) return yes, 
%xunique([a,c,a,d], [c,a,d]) return no 
%xunique([a,a,a,a,a,b,b,b,b,b,c,c,c,c,b,a], L) return L = [a,b,c], 
%xunique([], L) return L = [].

xunique(A,B):- xunique(A, [], B).

%If not a memeber append A (first element of first list
xunique([A|A2],B,C):- 
	xNotmember(A,B),
	append(B, [A], D),
	xunique(A2, D, C).

%If you are a member go to next value
xunique([A|A2],B,C):- 
	xMember(A,B),
	xunique(A2, B, C).

xunique([],C,C).

%Check to see if A is not a member of B list
xNotmember(A,[B|B2]):-
	A\== B,
	xNotmember(A,B2). 
xNotmember(_,[]).

%Check to see if A is a member of B list
xMember(A,[B|B2]):-
	A\==B,
	xMember(A,B2).

xMember(A,[A|_]).

%===Question3===
%Define the predicate xunion(L1, L2, L) where L1 and L2 are given lists of atoms, and L contains the unique elements that are contained in both L1 and L2. L should contain the unique elements of L1 (in the same order as in L1) followed by the unique elements of L2 that are not contained in L1 (in the same order as in L2). There should be no redundancy in L. The predicate should work both if L is a variable and if L is a given list. 

%Test Cases:
%xunion([a,c,a,d], [b,a,c], L) return L = [a,c,d,b], 
%xunion([a,c,d], [b,a,c], [a,c,d,b]) return yes, 
%xunion([a,c,d], [b,a,c], [a,c,d,b,a]) return no.

xunion(A, B, C):-
	append(A, B, D),
	xunique(D, C).

%===Question4===
%Define the predicate removeLast(L, L1, Last) where L is a given nonempty list, L1 is the result of removing the last element from L, and Last is that last element. L1 and Last can be either variables, or given values.

%Test Cases:
%removeLast([a,c,a,d], L1, Last) return L1 = [a,c,a], Last = d, 
%removeLast([a,c,a,d], L1, d) return L1 = [a,c,a], 
%removeLast([a,c,a,d], L1, [d]) return no, 
%removeLast([a], L1, Last) return L1 = [], Last = a, 
%removeLast([[a,b,c]], L1, Last) return L1 = [], Last = [a,b,c].

removeLast([A|A2],[B|B2],Result):-
	A = B,
	removeLast(A2,B2,Result).

removeLast([A|A2],B,Result):-
	A2 = [],
	B = [],
	A = Result.

%===Question5.1===
%allConnected(L) is true for an empty list, L= []. The recursive case is: 
%allConnected([A|L]) if A is connected to every node in L and allConnected(L). Thus, you need to define a predicate, say connect(A,L), to test if A is connected to every node in L.

clique(L) :- findall(X,node(X),Nodes),
             xsubset(L,Nodes), allConnected(L).

xsubset([], _).
xsubset([X|Xs], Set) :-
  append(_, [X|Set1], Set),
  xsubset(Xs, Set1).

append([], L, L).
append([H|T], L, [H|R]) :-
  append(T, L, R).

%Determind if element A (first element of list) is connected with values of A2 (remainder of list)
allConnected([A|A2]):-
	xConnect(A,A2),
	allConnected(A2).

allConnected([]).

%Check to see if A is connected to B
%B/B2 is to represent remainder of list
xConnect(A,[B|B2]):-
	xCheckEdge(A,B),
	xConnect(A,B2).

xConnect(_,[]).

%Check to see if A is part of an edge with B
xCheckEdge(A,B):-
	edge(A,B).

xCheckEdge(A,B):-
	edge(B,A).

%===Question5.2====
%Write a predicate maxclique(N, Cliques) to compute all the maximal cliques of size N that are contained in a given graph. N is the given input, Cliques is the output you compute: a list of cliques. A clique is maximal if there is no larger clique that contains it. In the example above, cliques [a,b,c] and [a,d] are maximal, but [a,b] is not, since it is contained in [a,b,c].

%N is input Number
%R is the return value
maxclique(N,R):-
	findall(C, xCheckCliqueSize(C,N), D),
	xfindAllGreater(N,E),
	xSubsetList(D,E,G),
	R = G.

%Nsize is All subset of size N
%Greater is All subset of size > N
%Result is output
xSubsetList(Nsize, Greater, Result):- xSubsetList(Nsize, Greater, [], Result).

%Finish interating through N Size cliques, return result
xSubsetList([], Greater, Temp, Result):- 
    Greater \== [],
    Result = Temp.

%If there is no clique that is greater than N, then return clique of N size
xSubsetList(Nsize, [], _, Nsize).

%If it is a subset of any of the greater value, move to next element of 
%subset of given size N
%Else append it and move to next value
xSubsetList([Nsize|Nsize2],Greater,Temp,Result):-
        Greater \== [],
	( (xSearchForIfSubset(Nsize,Greater))-> 
		append(Temp, [], NewTemp),
		xSubsetList(Nsize2, Greater, NewTemp, Result);
		append(Temp, [Nsize], NewTemp),
    		xSubsetList(Nsize2,Greater,NewTemp,Result)
	).

%Check to see if A (element) is a subset of any of B
xSearchForIfSubset(NElement, [Greater|Greater2]):-
    ( (subset(NElement,Greater))->
       true;
       xSearchForIfSubset(NElement,Greater2)
    ).

%If it does not find a subset, then fail this test
%So we go into the else statement in xSubsetList
xSearchForIfSubset(_,[]):-
      fail.

%Takes in 2 inputs and create 2 dummy variables to keep
%track of temp values
%N is the input number size
%Out is the output variable
xfindAllGreater(N,Out):- 
	N2 is N+1,	
	xfindAllGreater(N,[],N2,Out).

%N2 is a counter to keep track of all values that are
%greater than N(input) and length of max subset
%Appends all subset that size is >N and <(Length node)
%R is a temp variable to hold subset lists
%N2 is a temp variable for N counter that is >N and <(length of node)
%N4 is the next size (N2+1) to search for
xfindAllGreater(N,R,N2,Out):-
	findall(X,node(X),Nodes),
	length(Nodes,NodeLen),
	N2 < NodeLen,
        findall(A, xCheckCliqueSize(A,N2), B),
	N4 is N2+1,	
	append(B,R, NR),
	xfindAllGreater(N,NR,N4,Out).

%If we are at max node length return appended list
xfindAllGreater(_,R,N2,Out):-
	findall(X,node(X),Nodes),
	length(Nodes,NodeLen),
	N2 = NodeLen,
	Out = R.

%Get all subset of Size N and return it as A
xCheckCliqueSize(A,N):-
	clique(A),	
        length(A,N).


	
