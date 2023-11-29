
-- insert to table Account
INSERT INTO ACCOUNT (Account_id, Username, Password, Role) VALUES (2, 'cooker', 'cooker', 'Cooker');
INSERT INTO ACCOUNT (Account_id, Username, Password, Role) VALUES (3, 'waiter', 'waiter', 'Waiter');
INSERT INTO ACCOUNT (Account_id, Username, Password, Role) VALUES (4, 'cashier', 'cashier', 'Cashier');

-- insert to table Cashier
INSERT INTO Cashier (Cashier_ID, Name, Account_id) VALUES (1, 'Adam Bano', 4);

-- insert to table Cooker
INSERT INTO Cooker (Cooker_ID, Name, Account_id) VALUES (1, 'Yassir Zahi', 2);

-- insert to table Waiter
INSERT INTO Waiter (Waiter_ID, Name, Account_id) VALUES (1, 'Mohamed Tabo', 3);

-- insert to table Orders
INSERT INTO Orders (Order_ID, Table_Number, Waiter_ID, Cashier_ID, Cooker_ID, Order_Date) 
    VALUES (1, 1, 1, 1, 1, to_date('2019-12-12', 'yyyy-mm-dd'));
INSERT INTO Orders (Order_ID, Table_Number, Waiter_ID, Cashier_ID, Cooker_ID, Order_Date) 
    VALUES (2, 2, 1, 1, 1, to_date('2019-12-12', 'yyyy-mm-dd'));
INSERT INTO Orders (Order_ID, Table_Number, Waiter_ID, Cashier_ID, Cooker_ID, Order_Date, Order_Status) 
    VALUES (3, 3, 1, 1, 1, to_date('2019-12-12', 'yyyy-mm-dd'), 'Served');
INSERT INTO orders (Order_ID, Table_Number, Waiter_ID) 
    VALUES(4,4,1);
-- insert to table Food
INSERT INTO Food (Food_ID, Name, Price) VALUES (1, 'Bisara', 10000);
INSERT INTO Food (Food_ID, Name, Price) VALUES (2, 'Kofta', 20000);
INSERT INTO Food (Food_ID, Name, Price) VALUES (3, 'Kabab', 30000);
-- insert to table basket
INSERT INTO Basket (ORDER_ID, Food_ID, Quantity) VALUES (1, 1, 1);
INSERT INTO Basket (ORDER_ID, Food_ID, Quantity) VALUES (1, 2, 7);
INSERT INTO Basket (ORDER_ID, Food_ID, Quantity) VALUES (1, 3, 3);
INSERT INTO Basket (ORDER_ID, Food_ID, Quantity) VALUES (2, 3, 3);




--  Get the the count of orders for each month in the year 2022 by waiter id
SELECT to_char(Order_Date,'mm') as month_, count(Order_ID) as ORDER_of_waiter ,Waiter_ID as waiter 
FROM orders
where to_char(Order_Date,'yyyy') = '2022'
GROUP BY Order_Date,Waiter_ID ORDER BY Order_Date ASC;

-- set pending order to cooked
select order_id from orders where Order_Status = 'Pending' and Cooker_ID ='cooker'

-- get all the baskets for cooker to cook 
SELECT * FROM basket WHERE order_id in (select order_id from orders where Order_Status = 'Pending' and Cooker_ID =1);

-- display orders
select * from orders;







BEGIN
   FOR employee_record IN (SELECT Waiter_ID, Name FROM Waiter WHERE Waiter_ID NOT IN (SELECT Account_id FROM Account)) 
   LOOP
    INSERT INTO Account (Account_id, Username, Password, Role)
    VALUES (employee_record.Waiter_ID, employee_record.Name, 'password' || employee_record.Waiter_ID, 'Waiter');
    INSERT INTO Waiter (Waiter_ID, Name, Account_id)
    VALUES (employee_record.Waiter_ID, employee_record.Name, employee_record.Waiter_ID);
   END LOOP;
   
   FOR employee_record IN (SELECT Cooker_ID, Name FROM Cooker WHERE Cooker_ID NOT IN (SELECT Account_id FROM Account)) 
   LOOP
    INSERT INTO Account (Account_id, Username, Password, Role)
    VALUES (employee_record.Cooker_ID, employee_record.Name, 'password' || employee_record.Cooker_ID, 'Cooker');
    INSERT INTO Cooker (Cooker_ID, Name, Account_id)
    VALUES (employee_record.Cooker_ID, employee_record.Name, employee_record.Cooker_ID);
   END LOOP;

   FOR employee_record IN (SELECT Cashier_ID, Name FROM Cashier WHERE Cashier_ID NOT IN (SELECT Account_id FROM Account)) 
   LOOP
    INSERT INTO Account (Account_id, Username, Password, Role)
    VALUES (employee_record.Cashier_ID, employee_record.Name, 'password' || employee_record.Cashier_ID, 'Cashier');
    INSERT INTO Cashier (Cashier_ID, Name, Account_id)
    VALUES (employee_record.Cashier_ID, employee_record.Name, employee_record.Cashier_ID);
   END LOOP;
END;
