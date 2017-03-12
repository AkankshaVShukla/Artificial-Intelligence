%Facts
edge(human,ako,creature).
edge(bird,ako,creature).
edge(man,ako,human).
edge(turkey,ako,bird).
edge(louis,isa,man).
edge(albert,isa,man).
edge(frank,isa,turkey).

%Rules
rel(Source,Relation,Destination):- edge(Source,Relation,Destination).
rel(Source,Relation,Destination):-
	edge(Source,isa,Other),
	rel(Other,Relation,Destination).
rel(Source,Relation,Destination):-
	edge(Source,ako,Other),
	rel(Other,Relation,Destination).
