CREATE TABLE ACCOUNT (
    Account_id int NOT NULL,
    Username varchar(50) NOT NULL,
    Password varchar(100) NOT NULL,
    Role varchar(50) default 'Waiter' check (Role in ( 'Cashier', 'Cooker', 'Waiter')),
    PRIMARY KEY (Account_id)
);
-- cashiers
CREATE TABLE Cashier (
    Cashier_ID int NOT NULL,
    Name varchar(50) NOT NULL,
    Account_id int NOT NULL,
    PRIMARY KEY (Cashier_ID),
    FOREIGN KEY (Account_id) REFERENCES Account(Account_id)
);
-- cookers
CREATE TABLE Cooker (
    Cooker_ID int NOT NULL,
    Name varchar(50) NOT NULL,
    Account_id int NOT NULL,
    PRIMARY KEY (Cooker_ID),
    FOREIGN KEY (Account_id) REFERENCES Account(Account_id)
);
-- waiters
CREATE TABLE Waiter (
    Waiter_ID int NOT NULL,
    Name varchar(50) NOT NULL,
    Account_id int NOT NULL,
    PRIMARY KEY (Waiter_ID),
    FOREIGN KEY (Account_id) REFERENCES Account(Account_id)
); 
-- orders
CREATE TABLE Orders (
    Order_ID int NOT NULL,
    Table_Number int NOT NULL,
    Waiter_ID int NOT NULL,
    Cashier_ID int ,
    Cooker_ID int ,
    Order_Date date default sysdate(),
    Order_Status varchar(50) default 'Pending' check (Order_Status in ('Pending', 'Cooking','Cooked', 'Served')) ,
    PRIMARY KEY (Order_ID),
    FOREIGN KEY (Waiter_ID) REFERENCES Waiter(Waiter_ID),
    FOREIGN KEY (Cashier_ID) REFERENCES Cashier(Cashier_ID),
    FOREIGN KEY (Cooker_ID) REFERENCES Cooker(Cooker_ID)
);
-- basket table
CREATE TABLE Basket (
    -- id int NOT NULL,
    Order_ID int NOT NULL,
    Food_ID int NOT NULL,
    Quantity int NOT NULL,
    PRIMARY KEY (Food_ID, Order_ID),
    FOREIGN KEY (Food_ID) REFERENCES Food(Food_ID),
    FOREIGN KEY (Order_ID) REFERENCES Orders(Order_ID),
);
-- food
CREATE TABLE Food (
    Food_ID int NOT NULL,
    Name varchar(50) NOT NULL,
    Price float NOT NULL,
    PRIMARY KEY (Food_ID)
);
-- delete all previous tables
DROP TABLE  OrderDetails;
DROP TABLE  OrderRequest;
DROP TABLE  Orders;

DROP TABLE  Basket;

-- good
DROP TABLE  Food;
DROP TABLE  Waiter;
DROP TABLE  Cooker;
DROP TABLE  Cashier;
DROP TABLE  Account;

COMMIT;
desc ORDERS;





-- order request
-- CREATE TABLE OrderRequest (
--     Request_Order_ID int NOT NULL,
--     Basket_ID int NOT NULL,
--     Waiter_ID int NOT NULL,
--     PRIMARY KEY (Request_Order_ID),
--     FOREIGN KEY (Basket_ID) REFERENCES Basket(Basket_ID),
--     FOREIGN KEY (Waiter_ID) REFERENCES Waiter(Waiter_ID)
-- );

-- order details
-- CREATE TABLE OrderDetails (
--     Order_ID int NOT NULL,
--     Basket_ID int NOT NULL,
--     PRIMARY KEY (Order_ID, Basket_ID),
--     FOREIGN KEY (Order_ID) REFERENCES Orders(Order_ID),
--     FOREIGN KEY (Basket_ID) REFERENCES Basket(Basket_ID)
-- );