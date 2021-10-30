% PROYECTO 3 DANIEL DANIEL
:- use_module(library(clpfd)). %Libreria para realizar difentes operaciones sobre el conjunto de los numeros reales.

%Funcion inicial que obtiene la matriz final del juego, con la solucion de la misma
%si la matriz inicial o tablero inicial es incorrecto, retorna falso.
%Entrada: Un tablero o matriz 9x9 y una variable para respuesta.
sudoku(MatrizSudoku,MatrizRespuesta) :-
        length(MatrizSudoku, 9), maplist(same_length(MatrizSudoku), MatrizSudoku),
        append(MatrizSudoku, Vs), Vs ins 1..9,  %Se valida que solo se permitan numeros del 1 al 9
        %Se valida que por filas sean diferentes
        maplist(all_distinct, MatrizSudoku),    %Todos los elementos de las filas deben ser diferentes.
       %Se transpone la matriz para poder realizar la validacion por columna
        transpose(MatrizSudoku, Columnas),
        maplist(all_distinct, Columnas),        %Se transpone la matriz para que las colum
        %Se validan las diagonales
        validaDiagonal(MatrizSudoku, MatrizDiagonal),
        maplist(all_distinct, MatrizDiagonal),
        MatrizSudoku = [M1,M2,M3,M4,M5,M6,M7,M8,M9],
        subMatriz(M1, M2, M3),
        subMatriz(M4, M5, M6),
        subMatriz(M7, M8, M9),
        maplist(label,MatrizSudoku),
        MatrizRespuesta=MatrizSudoku.

%SubMatrices, se inicializan vacias.
subMatriz([], [], []).
subMatriz([N1,N2,N3|Ns1], [N4,N5,N6|Ns2], [N7,N8,N9|Ns3]) :-
        all_distinct([N1,N2,N3,N4,N5,N6,N7,N8,N9]),
        subMatriz(Ns1, Ns2, Ns3).

obtenerElemento(L, L1, [H|L1]):- 
                length(L1, N1), 
                length(L2, N1), 
                append(L2, [H|_], L).
%Se obtiene la primer diagonal
diagonal1(MatrizEntrada, MatrizResultado):- 
                foldl(obtenerElemento, MatrizEntrada, [], Res), 
                reverse(Res,MatrizResultado).
%Se obtiene la segunda diagonal
diagonal2(MatrizEntrada, MatrizResultado):- 
                reverse(MatrizEntrada, Res),
                foldl(obtenerElemento, Res, [], MatrizResultado).

validaDiagonal(Matriz,MatrizDiagonal):-
                diagonal1(Matriz,ResDiagonal1),
                diagonal2(Matriz,ResDiagonal2),
                append([ResDiagonal1],[ResDiagonal2],MatrizDiagonal).



    problem(1,
        [[7, _, _, 4, _, _, _, _, _], 
        [_, _, 4, _, _, _, _, _, _], 
        [5, _, _, _, _, 9, 6, _, _], 
        [_, _, _, 9, _, _, _, 8, _], 
        [_, 9, 5, _, _, _, _, 3, _], 
        [6, _, _, _, 8, 1, _, _, _], 
        [_, _, 8, _, _, _, _, _, _], 
        [_, _, 7, _, _, 2, _, _, _], 
        [_, _, _, _, _, _, _, _, _]]).
                
problem(1,
        [[7, _, _, 4, _, _, _, _, _], 
        [_, _, 4, _, _, _, _, _, _], 
        [5, _, _, _, _, 9, 6, _, _], 
        [_, _, _, 9, _, _, _, 8, _], 
        [_, 9, 5, _, _, _, _, 3, _], 
        [6, _, _, _, 8, 1, _, _, _], 
        [_, _, 8, _, _, _, _, _, _], 
        [_, _, 7, _, _, 2, _, _, _], 
        [_, _, _, _, _, _, _, _, _]]).