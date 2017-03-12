creature(X) :- human(X); bird(X).
human(X) :- man(X).
bird(X) :- turkey(X).
man(louis).
man(albert).
turkey(frank).

%Facts
edge(human,ako,creature).
edge(bird,ako,creature).
edge(man,ako,human).
edge(turkey,ako,bird).
edge(louis,isa,man).
edge(albert,isa,man).
edge(frank,isa,turkey).
property(human,legs,two).
property(louis,legs,one).
property(bird,fly,yes).
property(turkey,fly,no).

%Rules
rel(Source,Relation,Destination):- edge(Source,Relation,Destination).
rel(Source,Relation,Destination):-
	edge(Source,isa,Other),
	rel(Other,Relation,Destination).
rel(Source,Relation,Destination):-
	edge(Source,ako,Other),
	rel(Other,Relation,Destination).
rel(Source,isa,Destination):-
	edge(Source,isa,Other),
	rel(Other,ako,Destination).



prop(Source,Property,Value):- property(Source,Property,Value).
prop(Source,legs,Value):-
	edge(Source,ako,Other),
	prop(Other,legs,Value),
	\+ property(Source,legs,_).
prop(Source,legs,Value):-
	edge(Source,isa,Other),
	prop(Other,legs,Value),
	\+ property(Source,legs,_).
prop(Source,fly,Value):-
	edge(Source,ako,Other),
	prop(Other,fly,Value),
	\+ property(Source,fly,_).
prop(Source,fly,Value):-
	edge(Source,isa,Other),
	prop(Other,fly,Value),
	\+ property(Source,fly,_).




















