import numpy as np
def pevot(m,i,j):
    n = m.copy()
    l,c = np.shape(m)
    for k in range(l):
        if k == i:
            n[k,:] = m[k,:]/m[i,j]
        else:
            n[k,:] = m[k,:] - m[k,j]*m[i,:]/m[i,j]

    return n


# a fucntion to get i and j
def get_ij(m):
    l,c = np.shape(m)
    i = 0
    j = 0
    for k in range(c-1):
        # get index of max value in the last column
        if m[-1,k] > m[-1,j]:
            j = k
    # calculate z values
    z = m[:-1,-1]/m[:-1,j]
    # get index of min value in z using the function argmin
    i = np.argmin(z)
    return i,j



def solve(c):
    x =[]
    while np.max(c[-1,:-1]) > 0:
        i,j = get_ij(c)
        print("la base est: line (i = "+str(i)+" , j = "+str(j)+") --> ",c[i,j])
        x.append(i)
        c = pevot(c,i,j)
        print("-------------------")
        print(c)
        print("-------------------")
    print("the solution matrix is: ")
    print(c)
    for i in range(len(x)):
        print("x"+str(x[i]+1)+" = "+str(c[i,-1]))
        
    # print the result
    print("z = "+str(-c[-1,-1]))

c = np.array([[5/2,5,1,0,150],[5,2,0,1,120],[3/2,1,0,0,0]])
s = np.array([[1,1,3,1,0,0,4],[2,0,2,0,1,0,5],[2,1,3,0,0,1,7],[3,2,3,0,0,0,0]])
solve(c)
solve(s)


        