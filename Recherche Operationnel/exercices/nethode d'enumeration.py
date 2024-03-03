import tkinter as tk
import numpy as np

def draw_solution():
    # Get the function and constraints from the user
    function = function_entry.get()
    constraints = constraints_entry.get("1.0", tk.END).strip().split("\n")

    # Convert the constraints to equality form
    equality_constraints = []
    variables = {}
    variable_count = 0
    for constraint in constraints:
        if "<=" in constraint:
            parts = constraint.split("<=")
            equality_constraints.append(parts[0] + "-(" + parts[1] + ")")
        elif ">=" in constraint:
            parts = constraint.split(">=")
            equality_constraints.append("-(" + parts[0] + "-(" + parts[1] + "))")
        elif "=" in constraint:
            equality_constraints.append(constraint)
        else:
            variable_name = f'X{variable_count}'
            variables[variable_name] = constraint
            equality_constraints.append(variable_name + "-0")
            variable_count += 1

    # Solve the linear programming problem
    c = np.array([int(coefficient) if coefficient.isdigit() else coefficient for coefficient in function.split()])
    A_eq = []
    b_eq = []
    for constraint in equality_constraints:
        try:
            A_eq.append([int(coefficient) if coefficient.isdigit() else coefficient for coefficient in constraint.split()])
        except ValueError:
            A_eq.append([variables.get(coefficient, coefficient) for coefficient in constraint.split()])
        b_eq.append(0)

    # Solve the linear programming problem from scratch
    num_vars = len(c)
    num_constraints = len(A_eq)
    tableau = np.zeros((num_constraints + 1, num_vars + 1))
    tableau[:-1, :-1] = np.array(A_eq)
    tableau[:-1, -1] = b_eq
    tableau[-1, :-1] = -c

    while np.any(tableau[-1, :-1] < 0):
        pivot_col = np.argmin(tableau[-1, :-1])
        ratios = tableau[:-1, -1] / tableau[:-1, pivot_col]
        pivot_row = np.argmin(ratios)
        pivot_value = tableau[pivot_row, pivot_col]
        tableau[pivot_row, :] /= pivot_value
        for i in range(num_constraints + 1):
            if i != pivot_row:
                multiplier = tableau[i, pivot_col]
                tableau[i, :] -= multiplier * tableau[pivot_row, :]

    result = tableau[-1, -1]

    # Draw the solution
    canvas.delete("all")
    canvas.create_line(0, result * 100, 100, 0, fill="red", width=2)

# Rest of the code...
window = tk.Tk()
window.title("Linear Programming Enumeration Approach")
window.geometry("400x400")

# Create the function input field
function_label = tk.Label(window, text="Function:")
function_label.pack()
function_entry = tk.Entry(window)
function_entry.pack()

# Create the constraints input field
constraints_label = tk.Label(window, text="Constraints:")
constraints_label.pack()
constraints_entry = tk.Text(window, height=5, width=30)
constraints_entry.pack()

# Create the draw button
draw_button = tk.Button(window, text="Draw Solution", command=draw_solution)
draw_button.pack()

# Create the canvas to draw the solution
canvas = tk.Canvas(window, width=300, height=300)
canvas.pack()

# Start the tkinter event loop
window.mainloop()