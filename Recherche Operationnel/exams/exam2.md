### Exercice 1



|       | Profit unitaire | Ni | Ge | Cp | Mg | hours par jour |
| ----- | --------------- | -- | -- | -- | -- | -------------- |
| P1    | 250             | 2  | 5  | 5  | 2  | 1              |
| P2    | 100             | 3  | 2  | 3  | 1  | 2              |
| P3    | 100             | 1  | 0  | 0  | 1  |                |
| disponibilité | |7  | 11 | 10 | 6 |    6            |
##### Variables:

$x_{1}$ nombre d'unité de tricycle produit chaque mois

$x_{2}$ nombre d'unité de camions produit chaque mois

$x_{3}$ nombre d'unité de poupées produit chaque mois
##### Critère:

$$min(Z)=250x_{1}+100x_{2}+100x_{3}$$
##### Sous contraint:

$$
\begin{equation}
\begin{cases}
2x_1+3x_2+1x_3\leq 7 \\
5x_1+2x_2\leq 10\\
2x_2+x_2+x_3\leq 6 \\
x_1+2x_2\leq 6 \\
\end{cases}
\end{equation}
$$
###### Contraint de positivité

$x_{1},x_{2},x_3\geq 0$  

### Exercice 2
##### Critère:

$$min(Z)=2x_{1}+3x_{2}+3x_{5}$$
##### Sous contraint:

$$
\begin{equation}
\begin{cases}
3x_1+2x_2\leq 60 \\
-x_1+x_2+4x_3\leq 10\\
2x_2-2x_2+5x_3\leq 50 \\
\end{cases}
\end{equation}
$$

###### Contraint de positivité

$x_{1},x_{2},x_3\geq 0$  

##### PLS:

$$
\begin{equation}
\begin{cases}
3x_1+2x_2 +e_1 = 60 \\
-x_1+x_2+4x_3 + e_2 = 10\\
2x_2-2x_2+5x_3 + e_3= 50 \\
\end{cases}
\end{equation}
$$

$x_{1},x_{2},x_3,e_1,e_2,e_3\geq 0$  

##### Graphe cartésien:



|         | $x_{1}$ | $x_{2}$ | $x_{3}$ | $e_{1}$ | $e_{2}$ | $e_{3}$ | $b$     | $Rakov$ |
| ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
| $e_{1}$ | 2       | 3       | 0       | 1       | 0       | 0       | 60      | $\infty$|
| $e_{2}$ | -1      | 1       | 4       | 0       | 1       | 0       | 10      | 5/2     |
| $e_{3}$ | 2       | -2      | 5       | 0       | 0       | 1       | 50      | 10      |
| $Z$     | 2       | 3       | 3       | 0       | 0       | 0       | 0       |         |

le pivot est 1

variable entrante $x_{2}$
variable sortante $e_{2}$

|         | $x_{1}$ | $x_{2}$ | $x_{3}$ | $e_{1}$ | $e_{2}$ | $e_{3}$ | $b$     | $Rakov$ |
| ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
| $e_{1}$ | 5       | 0       | -8      | 1       | -2      | 0       | 40      | 8       |
| $x_{2}$ | -1      | 1       | 4       | 0       | 1       | 0       | 10      | $\infty$|
| $e_{3}$ | 0       | 0       | 13      | 0       | 2       | 1       | 70      | $\infty$|
| $Z$     | 5       | 0       | -9     | 0       | -3      | 0       | Z-30     |         |

le pivot est 5

variable entrante $x_{1}$
variable sortante $e_{1}$

|         | $x_{1}$ | $x_{2}$ | $x_{3}$ | $e_{1}$ | $e_{2}$ | $e_{3}$ | $b$     |
| ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | 
| $x_{1}$ | 1       | 0       | -8/5    | 1/5     | -2/5    | 0       | 8       | 
| $x_{2}$ | 0       | 1       | 12/5    | 1/5     | 3/5     | 0       | 18      |
| $e_{3}$ | 0       | 0       | 13      | 0       | 2       | 1       | 70      |
| $Z$     | 0       | 0       | -1      | -31     | -1      | 0       | Z-70    |

on tout les coefficients de la fonction objectif sont négatif donc on a atteint la solution optimal

valuer optimal de la fonction objectif est $Z=70$

sommet optimal est (8,18,0)


#### creation du problem dual

##### Variables:

$y_{1}$ nombre d'unité de tricycle produit chaque mois

$y_{2}$ nombre d'unité de camions produit chaque mois

$y_{3}$ nombre d'unité de poupées produit chaque mois

##### Critère:

$$max(Z)=60y_{1}+10y_{2}+50y_{3}$$

##### Sous contraint:

$$
\begin{equation}
\begin{cases}
3y_1-x_1+2x_2\geq 2 \\
2y_1+x_2-2x_2\geq 3\\
y_1+4y_3+5x_3\geq 3 \\
\end{cases}
\end{equation}
$$


### Exercice 3

##### Critère:

$$min(Z)=100x_{1}+200x_{2}$$

##### Sous contraint:

$$
\begin{equation}
\begin{cases}
3x_1+x_2\leq 23 \\
5x_1+6x_2\geq 52\\
3x_1-6x_2\leq 12 \\
x_2 \leq 7 \\
\end{cases}
\end{equation}
$$

###### Contraint de positivité

$x_{1},x_{2}\geq 0$

##### PLS:

$$
\begin{equation}
\begin{cases}
3x_1+x_2+e_1 = 23 \\
5x_1+6x_2-e_2 = 52\\
3x_1-6x_2+e_3 = 12 \\
x_2 + e_4 = 7 \\
\end{cases}
\end{equation}
$$

$x_{1},x_{2},e_1,e_2,e_3,e_4\geq 0$

##### PLF:

$$
\begin{equation}
\begin{cases}
3x_1+x_2+e_1 = 23 \\
5x_1+6x_2-e_2 + a_2 = 52\\
3x_1-6x_2+e_3 \\
x_2 + e_4 = 7 \\
\end{cases}
\end{equation}
$$

$x_{1},x_{2},e_1,e_2,e_3,e_4,a_2\geq 0$


##### Phase 1:

|         | $x_{1}$ | $x_{2}$ | $e_{1}$ | $e_{2}$ | $e_{3}$ | $e_{4}$ | $a_{2}$ | $b$     | $Rakov$ |
| ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
| $e_{1}$ | 3       | 1       | 1       | 0       | 0       | 0       | 0       | 23      | 23      |
| $e_{2}$ | 5       | 6       | 0       | -1      | 0       | 0       | 1       | 52      | 26/3     |
| $e_{3}$ | 3       | -6      | 0       | 0       | 1       | 0       | 0       | 12      | $\infty$|
| $e_{4}$ | 0       | 1       | 0       | 0       | 0       | 1       | 0       | 7       | 7       |
| $Z$     | -5      | -6      | 0       | 0       | 0       | 0       | 0       | 0       |         |

on min $a_2$ = 52 - 5 $x_1$ - 6 $x_2$ + $e_2$

la variable entrante est $x_2$

la variable sortante est $e_4$

|         | $x_{1}$ | $x_{2}$ | $e_{1}$ | $e_{2}$ | $e_{3}$ | $e_{4}$ | $a_{2}$ | $b$     | $Rakov$ |
| ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
| $e_{1}$ | 3       | 0       | 1       | 0       | 0       | -1    | 0       | 16      | 16/3      |
| $e_{2}$ | 5       | 0       | 0       | -1      | 0       | -6     | 1       | 10      | 2       |
| $e_{3}$ | 3       | 0       | 0       | 0       | 1       | 6      | 0       | 54       | 18      |
| $x_{2}$ | 0      | 1       | 0       | 0       | 0       | 1      | 0       | 7       | $\infty$|
| $Z$     | -5      | 0       | 0       | 1       | 0       | 6      | 0       | Z-10      |         |

la variable entrante est $x_1$

la variable sortante est $e_2$

|         | $x_{1}$ | $x_{2}$ | $e_{1}$ | $e_{2}$ | $e_{3}$ | $e_{4}$ | $a_{2}$ | $b$     | 
| ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | 
| $e_{1}$ | 0       | 0       | 1       | 3/5     | 0       | 13/5    | -3/5    | 10      | 
| $x_{1}$ | 1       | 0       | 0       | -1/5    | 0       | -6/5    | 1/5     | 2       |
| $e_{3}$ | 0       | 0       | 0       | 3/5     | 1       | 48/5    | -3/5    | 48      |
| $x_{2}$ | 0       | 1       | 0       | 0       | 0       | 1       | 0       | 7       |
| $Z$     | 0       | 0       | 0       | 0       | 0       | 0       | 1       | 0       |

puitque tout les coefficients de la fonction objectif sont négatif donc on a atteint la solution optimal

le sommet optimal est (2,7)

la valeur optimal de la fonction objectif est $Z=0$

#### Phase 2:

$max : Z=100x_{1}+200x_{2}$

|         | $x_{1}$ | $x_{2}$ | $e_{1}$ | $e_{2}$ | $e_{3}$ | $e_{4}$  | $b$     | $Rakov$ |
| ------- | ------- | ------- | ------- | ------- | ------- | -------  | ------- | ------- |
| $e_{1}$ | 0       | 0       | 1       | 3/5     | 0       | 13/5     | 10      | 50/3     |
| $x_{1}$ | 1       | 0       | 0       | -1/5    | 0       | -6/5     | 2       | $\infty$|
| $e_{3}$ | 0       | 0       | 0       | 3/5     | 1       | 48/5     | 48      | 80      |
| $x_{2}$ | 0       | 1       | 0       | 0       | 0       | 1        | 7       | $\infty$|
| $Z$     | 100     | 200     | 0       | 0       | 0       | 0        | Z-0       |         |
| Z(adjust)| 0      | 0       | 0       | 20       | 0       | -80        | z-100        |         |


la variable entrante est $e_2$

la variable sortante est $e_1$

|         | $x_{1}$ | $x_{2}$ | $e_{1}$ | $e_{2}$ | $e_{3}$ | $e_{4}$  | $b$     | 
| ------- | ------- | ------- | ------- | ------- | ------- | -------  | ------- | 
| $e_{2}$ | 0       | 0       | 5/3     | 1       | 0       | 13/3     | 50/3    |
| $x_{1}$ | 1       | 0       | -1/3    | 0       | 0       | -1/3     | 16/3    |
| $e_{3}$ | 0       | 0       | -1      | 0       | 1       | 7        | 38      |
| $x_{2}$ | 0       | 1       | 0       | 0       | 0       | 1        | 7       |
| $Z$     | 0       | 0       | -100/3  | 0       | 0       | -500/3   | -5800/3 |


conclusion:

 la valeur optimal de la fonction objectif est $Z=-5800/3$

le sommet optimal est (16/3,7)

la solution de base realisable est (16/3,7,0,50/3,0,38)

