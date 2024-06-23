
SET NOCOUNT ON
USE master
GO

IF exists (select * from sysdatabases where name='JewelryStore')
BEGIN
  RAISERROR('Dropping existing JewelryStore database ....',0,1)
  DROP database JewelryStore
END
GO

CHECKPOINT
GO

RAISERROR('Creating JewelryStore database....',0,1)
GO
   CREATE DATABASE JewelryStore
GO

CHECKPOINT
GO

USE JewelryStore
GO

/* Table [User] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[User_Id] [int] IDENTITY(1,1),
	[Email] [varchar](255) NOT NULL,
	[Password] [char](64) NOT NULL,
	[Role_Id] [int] NOT NULL,
	[Status] bit NOT NULL,
CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[User_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


/* Table [Employee] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employee](
	[Employee_Id] [nvarchar] (255) NOT NULL,
	[Full_Name] [nvarchar](255) NOT NULL,
	[User_Id] [int] REFERENCES [Users]([User_Id]),
CONSTRAINT [PK_Employee] PRIMARY KEY CLUSTERED 
(
	[Employee_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* 
Origin: Natural
Color: D, E, F,J
Clarity: IF, VSS1, VSS2, VS1, VS2
Cut: Excellent, Shallow, Poor
Proportions: Ideal
Polish: Excellent
Symmetry: Excellent
Flourescence: None
*/

/* Table [Customer] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customer](
	[Customer_Id] [nvarchar] (255) NOT NULL,
	[Full_Name] [nvarchar](255) NOT NULL,
	[Address] [nvarchar](max) NOT NULL,
	[Phone_Number] [varchar](10) NOT NULL,
	[User_Id] [int] REFERENCES [Users]([User_Id]),
CONSTRAINT [PK_Customer] PRIMARY KEY CLUSTERED 
(
	[Customer_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [Category] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category](
	[Category_Id] int identity(1,1) NOT NULL,
	[Category_Name] [nvarchar](255) NOT NULL,
	[Status] [bit]  NOT NULL,
	[Img_Url] varchar(255) NOT NULL,
CONSTRAINT [PK_Category] PRIMARY KEY CLUSTERED 
(
	[Category_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [Collection] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Collections](
	[Collection_Id] int IDENTITY(1,1),
	[Collection_Name] [nvarchar](255) NOT NULL,
	[Status] [bit] NOT NULL,
	[Img_Url] varchar(255) NOT NULL,
CONSTRAINT [PK_Collections] PRIMARY KEY CLUSTERED 
(
	[Collection_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [Blogs] */
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Blogs](
    [Blog_Id] [int] IDENTITY(1,1),
	[Title] varchar(100) NOT NULL,
	[Content] [nvarchar](max) NOT NULL,
	[Date] [date] NOT NULL,
	[Employee_Id] [nvarchar] (255) REFERENCES [Employee]([Employee_Id]),
	[status] bit NOT NULL,
	[Img_Url] varchar(255) NOT NULL,
CONSTRAINT [PK_Blogs] PRIMARY KEY CLUSTERED 
(
	[Blog_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [Material] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Material](
	[Material_Id] int IDENTITY(1,1),
	[Material_Code] [nvarchar] (255) NOT NULL,
	[Material_Name] [nvarchar](255) NOT NULL,
	[status] bit NOT NULL,
	[Img_Url] varchar(255),
CONSTRAINT [PK_Material] PRIMARY KEY CLUSTERED 
(
	[Material_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [MaterialPriceList] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Material_Price_List](
	[id] int IDENTITY(1,1),
	[Price] [float] NOT NULL,
	[Eff_Date] [date] NOT NULL,
	[Material_Id] int REFERENCES [Material]([Material_Id])
CONSTRAINT [PK_MaterialPriceList] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [Product] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product](
	[Product_Id] [int] IDENTITY(1,1),
	[Product_Code] [nvarchar] (max),
	[Product_Name] [nvarchar](255),
	[Collection_Id] int REFERENCES Collections([Collection_Id]),
	[Category_Id] int REFERENCES Category([Category_Id]),
	[Description] [nvarchar](max),

	[Gender] [nvarchar](50) ,
	[Size] [int] ,
	[Img_Url] varchar(255) ,

	[Status] bit,
	--[Warranty_Card_Id] int,
	
CONSTRAINT [PK_Product] PRIMARY KEY CLUSTERED 
(
	[Product_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [ProductionOrder] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Production_Order](
	[Production_Order_Id] nvarchar(255),
	[Date] datetime NOT NULL,

	[Customer_Id] [nvarchar] (255) REFERENCES [Customer](Customer_Id),
	[Category_Id] int REFERENCES [Category](Category_Id),
	[Product_Size] [int],
	[Img_Url] varchar(255),

	[Description] [nvarchar](255),

	[Q_Diamond_Amount] float,
	[Q_Material_Amount] float,
	[Q_Production_Amount] float,
	[Q_Total_Amount] float,

	[O_Diamond_Amount] float,
	[O_Material_Amount] float,
	[O_Production_Amount] float,
	[O_Total_Amount] float,

	[Sales_Staff_Id] [nvarchar](255),
	[Design_Staff_Id] [nvarchar](255),
	[Production_Staff_Id] [nvarchar](255),

	[Status] [nvarchar](10),  --Created,
							  --Requested, 
							  --Quoted (NAM) |Quoted (RJM)| -> Quoted (NAC) |Quoted (RJC)| ->  Quoted (NP), 
							  --Ordered
							  --Confirmed
							  --Completed
							  --Delivered

	[Product_Id] int REFERENCES Product([Product_Id])
CONSTRAINT [PK_Production_Order] PRIMARY KEY CLUSTERED 
(
	[Production_Order_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [Warranty_Card] */ 
/*SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Warranty_Card](
	[Warranty_Card_Id] [int] IDENTITY(1,1),
	[Comp_Name] [nvarchar] (255) NOT NULL,
	[Comp_Address] [nvarchar](255) NOT NULL,
	[Comp_Phone] [nvarchar] (255) NOT NULL,
	[Comp_Email] [nvarchar](255) NOT NULL,

	[From_Date] date NOT NULL,
	[To_Date] date NOT NULL,

CONSTRAINT [PK_Warranty_Card] PRIMARY KEY CLUSTERED 
(
	[Warranty_Card_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
)  ON [PRIMARY]
GO*/





/* Table [ProductMaterial] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product_Material](
	Product_Id int REFERENCES Product([Product_Id]),
	Material_Id int REFERENCES Material([Material_Id]),
	Material_Weight float,
	[Q_Price] float,
	[O_Price] float,

CONSTRAINT [PK_Product_Material] PRIMARY KEY CLUSTERED 
(
	Product_Id ASC, Material_Id ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


/* Table [GemPriceList] */
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Diamond_Price_List] (
    [Id] [int] IDENTITY(1,1),
	[Origin] [nvarchar] (255) NOT NULL,
    [Carat_Weight_From] [float] NOT NULL,
	[Carat_Weight_To] [float] NOT NULL,
	[Color] [char](10) NOT NULL,
	[Clarity] [char](10) NOT NULL,
	[Cut] [nvarchar] (255) NOT NULL,
	[Price] [float] NOT NULL,
	[Eff_Date] [date] NOT NULL,
	
CONSTRAINT [PK_Gem_Price_List] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [Diamond] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Diamond](
	[Dia_Id] [int] IDENTITY(1,1),
	[Dia_Code] [nvarchar](255),
	[Dia_Name] [nvarchar](255) ,

	[Origin] [nvarchar] (255),
    [Carat_Weight] [float] ,
	[Color] [char](10),
	[Clarity] [char](10),
	[Cut] [nvarchar] (255) ,

	[Proportions] [nvarchar](255),
	[Polish] [nvarchar](255),
	[Symmetry] [nvarchar](255) ,
	[Fluorescence] [char](10),
	[Status] [bit] NOT NULL, -- 1/Active | 0/Inactive (Used by some Pro)
	[Img_Url] varchar(255) NOT NULL,

	[Q_Price] float,
	[O_Price] float,

	[Product_Id] int REFERENCES Product([Product_Id])

CONSTRAINT [PK_Diamond] PRIMARY KEY CLUSTERED 
	(
		[Dia_Id] ASC
	) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
	--CONSTRAINT [FK_Diamond_Product] FOREIGN KEY ([Product_Id]) REFERENCES [dbo].[Product] ([Product_Id])
) ON [PRIMARY]
GO

/* Table [ProductDesignShell] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product_Design_Shell](
	[Product_Design_Shell_Id] int IDENTITY(1,1),
	[Material_Id] int,
	[Weight] float,
	
CONSTRAINT [PK_ProductDesignShell] PRIMARY KEY CLUSTERED 
(
	[Product_Design_Shell_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [ProductDesign] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product_Design](
	[Product_Design_Id] [int] IDENTITY(1,1),
	[Product_Design_Code] [nvarchar] (max),
	[Product_Design_Name] [nvarchar](255),
	[Category_Id] int REFERENCES [Category](Category_Id),
	[Collection_Id] int REFERENCES [Collections](Collection_Id),
	[Description] [nvarchar](max),

	[Gender] [nvarchar](50),
	[Product_Size] [int],

	[Product_Design_Shell_Id] int REFERENCES [Product_Design_Shell]([Product_Design_Shell_Id]),
	[Gem_Min_Size] float,
	[Gem_Max_Size] float,

	[Status] bit,
	[Product_Id] int REFERENCES Product([Product_Id])
CONSTRAINT [PK_Product_Design] PRIMARY KEY CLUSTERED 
(
	[Product_Design_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/*================ INSERT DATA ================*/

--Insert data into the Users table
SET IDENTITY_INSERT [Users] ON;
INSERT INTO [dbo].[Users] (User_Id, Email, Password, Role_ID, Status)
VALUES 

 

-- 10 Customers
(1, 'truongantnh@gmail.com', 'andhtSE180230@fpt', 1, 1),
(2, 'customer1@example.com', 'Cust1#Secure', 1, 1),
(3, 'customer2@example.com', 'Cust2*Strong', 1, 1),
(4, 'customer3@example.com', 'Cust3@Safe!', 1, 0),
(5, 'customer4@example.com', 'Cust4%Tough', 1, 1),
(6, 'customer5@example.com', 'Cust5@Solid', 1, 1),
(7, 'customer6@example.com', 'Cust6&Hard!', 1, 0),
(8, 'customer7@example.com', 'Cust7!Secure', 1, 1),
(9, 'customer8@example.com', 'Cust8#Strong', 1, 1),
(10, 'customer9@example.com', 'Cust9*Safe!', 1, 0),
(11, 'customer10@example.com', 'Cust10%Tough', 1, 1),
-- 4 Admins
(12, 'admin1@example.com', 'Admin1#Secure', 2, 1),
(13, 'admin2@example.com', 'Admin2*Strong', 2, 1),
(14, 'admin3@example.com', 'Admin3@Safe!', 2, 1),
(15, 'admin4@example.com', 'Admin4%Tough', 2, 1),
-- 4 Managers
(16, 'manager1@example.com', 'Mgr1#Secure!', 3, 1),
(17, 'manager2@example.com', 'Mgr2*Strong', 3, 1),
(18, 'manager3@example.com', 'Mgr3@Safe!', 3, 1),
(19, 'manager4@example.com', 'Mgr4%Tough', 3, 1),
-- 4 Sales Staff
(20, 'sales1@example.com', 'Sales1#Good!', 4, 1),
(21, 'sales2@example.com', 'Sales2*Best', 4, 1),
(22, 'sales3@example.com', 'Sales3@Fine!', 4, 1),
(23, 'sales4@example.com', 'Sales4%Great', 4, 1),
-- 4 Design Staff
(24, 'design1@example.com', 'Design1#Art!', 5, 1),
(25, 'design2@example.com', 'Design2*Skill', 5, 1),
(26, 'design3@example.com', 'Design3@Work!', 5, 1),
(27,'design4@example.com', 'Design4%Craft', 5, 1),
-- 4 Production Staff
(28, 'prod1@example.com', 'Prod1#Make!', 6, 1),
(29,'prod2@example.com', 'Prod2*Build', 6, 1),
(30, 'prod3@example.com', 'Prod3@Form!', 6, 1),
(31, 'prod4@example.com', 'Prod4%Forge', 6, 1);
SET IDENTITY_INSERT [Users] OFF;

-- Insert data into the Employee table
INSERT INTO [dbo].[Employee] (Employee_Id, Full_Name, [User_Id])
VALUES 
-- Admins
('AD001', 'Alice Johnson', 12),
('AD002', 'Bob Smith', 13),
('AD003', 'Charlie Brown', 14),
('AD004', 'Diana White', 15),
-- Managers
('MA001', 'Edward King', 16),
('MA002', 'Fiona Green', 17),
('MA003', 'George Black', 18),
('MA004', 'Hannah Blue', 19),
-- Sales Staff
('SS001', 'Ivy Scott', 20),
('SS002', 'Jack Turner', 21),
('SS003', 'Karen Walker', 22),
('SS004', 'Leo Adams', 23),
-- Design Staff
('DE001', 'Mia Nelson', 24),
('DE002', 'Nate Perez', 25),
('DE003', 'Olivia Hill', 26),
('DE004', 'Paul Young', 27),
-- Production Staff
('PR001', 'Quinn Baker', 28),
('PR002', 'Rachel Carter', 29),
('PR003', 'Sam Edwards', 30),
('PR004', 'Tina Flores', 31);

-- Insert data into the Customer table
INSERT INTO [dbo].[Customer] (Customer_Id, Full_Name, Address, Phone_Number, [User_Id])
VALUES
('CUS001', 'Ursula Grey', '123 Maple St', '5551110001', 1),
('CUS002', 'Victor Hale', '456 Oak St', '5551110002', 2),
('CUS003', 'Wendy Lane', '789 Pine St', '5551110003', 3),
('CUS004', 'Xander Reed', '101 Birch St', '5551110004', 4),
('CUS005', 'Yvonne Fox', '202 Cedar St', '5551110005', 5),
('CUS006', 'Zachary Stone', '303 Spruce St', '5551110006', 6),
('CUS007', 'Anna Carter', '404 Elm St', '5551110007', 7),
('CUS008', 'Brian Torres', '505 Willow St', '5551110008', 8),
('CUS009', 'Clara Davis', '606 Walnut St', '5551110009', 9),
('CUS010', 'David Evans', '707 Ash St', '5551110010', 10),
('CUS011', 'An Dao', '125 Valeria St', '0908234123', 11);


SET IDENTITY_INSERT [Category] ON;
-- Insert data into Category table
INSERT INTO [dbo].[Category] (Category_Id, Category_Name, Status, Img_Url) VALUES
(1, 'ring', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fring%2FDefault_22K_Gold_Diamond_Wedding_Band_A_22K_gold_wedding_band_0.jpg?alt=media&token=2619ffed-9c99-4e1e-95c7-7545e638ecf9'),
(2, 'necklace', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fnecklace%2FDefault_22K_Gold_Diamond_Pendant_Necklace_A_22K_gold_pendant_n_0.jpg?alt=media&token=61c6b5b1-fc4a-4ba2-adf7-f50f8ab58732'),
(3, 'earrings', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fearrings%2FDefault_22K_Gold_Diamond_Hoops_Stylish_22K_gold_hoop_earrings_0.jpg?alt=media&token=e5214a2e-6f63-4ced-aa0b-d02dcd82c08e'),
(4, 'bracelet', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fbracelet%2FDefault_A_10K_gold_bangle_encrusted_with_diamonds_for_a_sophis_0.jpg?alt=media&token=8d6cf4f7-2757-41e9-82d5-201dbc8d2a95'),
(5, 'cufflinks', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fearrings%2FDefault_A_white_gold_cufflink_encrusted_with_diamonds_for_a_so_0.jpg?alt=media&token=3788b7b8-e46f-4552-a574-b6fa0e095287');

SET IDENTITY_INSERT [Category] OFF

SET IDENTITY_INSERT [Blogs] ON;
INSERT INTO [dbo].[Blogs] ([Blog_Id], [Title], [Content], [Date], [Employee_Id], status, Img_Url) VALUES
(1, 'The Brilliance of Diamonds', 
 'Explore the mesmerizing brilliance of diamonds, discussing their formation, characteristics, and symbolism in various cultures. Highlight the unique features that make diamonds a timeless choice for jewelry.', 
 '2024-05-27', 'SS001', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Blogs%2FDefault_generate_for_me_a_picture_of_Explore_the_mesmerizing_b_1.jpg?alt=media&token=3e64a95d-6782-461f-85e8-74cf786fc1d5'),

(2, 'Crafting Beauty: The Art of Gold Jewelry Making', 
 'Delve into the craftsmanship behind gold jewelry, showcasing the intricate techniques used by artisans to create stunning pieces. Discuss the versatility of gold and its significance in different jewelry designs.', 
 '2024-05-28', 'DE001', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Blogs%2FDefault_generate_for_me_a_picture_of_Delve_into_the_craftsmans_0.jpg?alt=media&token=27de7360-b0d0-412c-955d-61371d27aaa1'),

(3, 'Unveiling the Mystique of Precious Diamonds', 
 'Take your readers on a journey through the world of precious diamonds. Explore their origins, colors, and cultural meanings, offering insights into why these diamonds are treasured.', 
 '2024-05-29', 'SS003', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Blogs%2FDefault_generate_for_me_a_picture_of_a_journey_through_the_wor_0.jpg?alt=media&token=00672747-51f2-4d57-b843-0343cdfa04e6'),

(4, 'From Mine to Market: The Diamond Supply Chain', 
 'Shed light on the journey of diamonds from the mines to the market, discussing ethical sourcing practices, sustainability efforts, and the role of certification in ensuring transparency and integrity.', 
 '2024-05-30', 'PR001', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Blogs%2FDefault_generate_for_me_a_picture_of_The_Diamond_Supply_Chain_0.jpg?alt=media&token=0b045f91-d503-44ef-acaf-a4cb53c5b524'),

(5, 'The Language of Jewelry: Symbols and Meanings', 
 'Explore the symbolic meanings behind common jewelry motifs such as hearts, infinity symbols, and flowers. Discuss how these symbols resonate with individuals and convey messages of love, hope, and empowerment.', 
 '2024-06-01', 'DE003', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Blogs%2FDefault_generate_for_me_a_picture_of_symbolic_meanings_behind_0.jpg?alt=media&token=d7e1fc68-3fac-4ed0-b58e-4cadf2f64b1a');

SET IDENTITY_INSERT [Blogs] OFF
SET IDENTITY_INSERT [Collections] ON;
 -- Insert collections into the Collection table
INSERT INTO [dbo].[Collections] ([Collection_Id], [Collection_Name], [Status], [Img_Url]) VALUES
(1, 'Summer Collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Collections%2F1A-3.jpg?alt=media&token=b921cd83-f8d5-4b8b-a8e5-9b49d7c184b4'),
(2, 'Spring Collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Collections%2F2.jpg?alt=media&token=12c89fa6-c177-404f-a957-a54309ce8aa5'),
(3, 'Winter Collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Collections%2F2022-10-2910.13.16_e3d0212c-a298-4ed4-a661-1aee76c86592_1500x.jpg?alt=media&token=f20b895b-2539-45ae-8b9f-46864f170896'),
(4, 'Autumn Collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Collections%2F893e69de69123ff7862ff40d56999db0%20(1).jpg?alt=media&token=4e742a68-b296-4dc1-810d-459836674748'),
(5, 'Wedding Collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Collections%2F893e69de69123ff7862ff40d56999db0.jpg?alt=media&token=6335f0bd-e19b-4db0-9aa3-5b15c8e7448e'),
(6, 'Wildcard Collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Collections%2FDefault_A_closeup_photo_of_a_summer_collection_of_gold_jewelry_2.jpg?alt=media&token=1e38754a-7b5c-4fde-a91c-ee82e2dddc42'),
(7, 'Galaxy Collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Collections%2Fimg_proxy_7420ce1a-960b-414c-a928-b427c54582d7.jpg?alt=media&token=dc9d6ed1-16ce-4548-8d39-b764ec1eb7a8'),
(8, 'Mountain Collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Collections%2Fil_794xN.2261231212_j1mk_7c7bfe28-fbb7-46ac-8db9-e705d39128f9.jpg?alt=media&token=d8c79722-3a9e-46c6-bddc-9570f3b71583'),
(9, 'Ocean Collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Collections%2Fcd094e599243fa7e760e1fc0323fd1e9.jpg?alt=media&token=39319424-92e5-4b50-909f-a6706a574a84'),
(10, 'Disney Collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Collections%2FT098_grande.jpg?alt=media&token=f3a03997-8958-4632-af12-fe52971e84c9');

SET IDENTITY_INSERT [Collections] OFF
SET IDENTITY_INSERT [Material] ON;
-- Insert data into Material table
INSERT INTO [dbo].[Material] (Material_Id, [Material_Code], [Material_Name], [status], [Img_Url])
VALUES
    (1, 'Gold8K', 'Gold 8K', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fgold%2FDefault_generate_for_me_a_picture_of_gold_piece_that_are_shini_1.jpg?alt=media&token=98b56f35-3d44-4c42-b607-3461c9744e48'),
    (2, 'Gold9K', 'Gold 9K', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fgold%2FDefault_generate_for_me_a_picture_of_gold_shining_and_luxiriou_3.jpg?alt=media&token=334fa94a-bd35-44ed-a82d-6be17d0bf228'),
    (3, 'Gold10K', 'Gold 10K', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fgold%2FDefault_generate_for_me_a_picture_of_gold_shining_and_luxiriou_0%20(1).jpg?alt=media&token=4c426d8a-2643-47c2-8557-39fbc15095e2'),
    (4, 'Gold14K', 'Gold 14K', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fgold%2FDefault_generate_for_me_a_picture_of_gold_shining_and_luxiriou_0%20(2).jpg?alt=media&token=fc6da730-cd10-4568-9e24-26f3e5dda339'),
    (5, 'Gold15.6K', 'Gold 15.6K', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fgold%2FDefault_generate_for_me_a_picture_of_gold_shining_and_luxiriou_0.jpg?alt=media&token=ce614337-0b76-4127-a92a-19c9dff22926'),
    (6, 'Gold16.3K', 'Gold 16.3K', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fgold%2FDefault_generate_for_me_a_picture_of_gold_shining_and_luxiriou_2%20(1).jpg?alt=media&token=7cb892a8-ee7a-4214-882d-cb976e39544d'),
    (7, 'Gold18K', 'Gold 18K', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fgold%2FDefault_generate_for_me_a_picture_of_gold_shining_and_luxiriou_1.jpg?alt=media&token=3a9de95d-e6d8-429c-80d1-b80348638b5e'),
    (8, 'Gold22K', 'Gold 22K', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fgold%2FDefault_generate_for_me_a_picture_of_gold_shining_and_luxiriou_2.jpg?alt=media&token=bc853b17-aff7-4ec6-8a6b-ea7ea5d8a29e'),
    (9, 'SJC', 'SJC Gold Piece', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fgold%2FDefault_generate_for_me_a_picture_of_gold_shining_and_luxiriou_1%20(2).jpg?alt=media&token=3623bca4-01b1-4258-a177-0d90fdb72929'),
    (10, 'PNJ24K', 'PNJ Ring Gold 24K', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fgold%2FDefault_generate_for_me_a_picture_of_gold_shining_and_luxiriou_3%20(1).jpg?alt=media&token=190a5afa-5207-420a-9be5-827127fc88d2');

-- Insert data into MaterialPriceList table
DECLARE @StartDate DATE = '2024-06-11'; -- Start date for generating data
DECLARE @EndDate DATE = '2024-06-11';   -- End date for generating data

WHILE @StartDate <= @EndDate
BEGIN
    -- Gold prices for each type of gold on the current date
    INSERT INTO [dbo].[Material_Price_List] ([Price], [Eff_Date], [Material_Id])
    VALUES
        (92.65, @StartDate, 1),  -- Gold8K
        (105.96, @StartDate, 2), -- Gold9K
        (118.09, @StartDate, 3), -- Gold10K
        (168.03, @StartDate, 4), -- Gold14K
        (187.27, @StartDate, 5), -- Gold15.6K
        (196.11, @StartDate, 6), -- Gold16.3K
        (216.83, @StartDate, 7), -- Gold18K
        (269.25, @StartDate, 8), -- Gold22K
        (350.99, @StartDate, 9), -- SJC
        (292.88, @StartDate, 10);-- PNJ24K

    SET @StartDate = DATEADD(day, 1, @StartDate);
END;

-- Insert data for [GemPriceList] table (Diamond prices for different dates)
DECLARE @StartDateGem DATE = '2024-06-17'; -- Start date for generating data
DECLARE @EndDateGem DATE = '2024-06-17';   -- End date for generating data
-- Loop through dates to generate data for each day
WHILE @StartDateGem <= @EndDateGem
BEGIN
    -- Generate data for GemPriceList table (Diamond prices for different dates)
INSERT INTO [dbo].[Diamond_Price_List] ([Origin], [Carat_Weight_From], [Carat_Weight_To], [Color], [Clarity], [Cut], [Price], [Eff_Date])
VALUES
	
		--0.04-0.14
	('Natural',  0.04, 0.07, 'F', 'IF', 'Ideal',  9.5 , @StartDateGem),
	('Natural',  0.04, 0.07, 'E', 'VVS1', 'Ideal',   9.5 , @StartDateGem),
	('Natural',  0.04, 0.07, 'J', 'VVS1', 'Shallow',  9.5 , @StartDateGem),
	('Natural',  0.04, 0.07, 'D', 'VS1', 'Ideal',  9.5 , @StartDateGem),
	('Natural',  0.04, 0.07, 'F', 'VVS1', 'Shallow',  9.5 , @StartDateGem),
		
	('Natural', 0.08, 0.14, 'J', 'VVS1', 'Ideal', 10.6, @StartDateGem),
	('Natural',  0.08, 0.14, 'F', 'IF', 'Shallow', 10.6, @StartDateGem),
	('Natural',  0.08, 0.14, 'E', 'VVS2', 'Ideal', 10.6, @StartDateGem),
	('Natural',  0.08, 0.14, 'D', 'VS1', 'Ideal', 10.6, @StartDateGem),
	('Natural', 0.08, 0.14, 'J', 'IF', 'Ideal',  8.4, @StartDateGem),

		-- 0.15 - 0.49 ct
	('Natural', 0.15, 0.17, 'D', 'IF', 'Deep', 12.5, @StartDateGem),
	('Natural',  0.15, 0.17, 'F', 'VVS1', 'Shallow', 12.5, @StartDateGem),
	('Natural',  0.15, 0.17, 'E', 'VVS2', 'Poor', 12.5, @StartDateGem),
	('Natural',  0.15, 0.17, 'D', 'VS1', 'Ideal', 12.5, @StartDateGem),
	('Natural',  0.15, 0.17, 'J', 'VS2', 'Ideal', 9.9, @StartDateGem),
		
	('Natural',  0.18, 0.22, 'E', 'IF', 'Ideal', 14.6, @StartDateGem),
	('Natural',  0.18, 0.22, 'E', 'VVS1', 'Ideal', 14.6, @StartDateGem),
	('Natural',  0.18, 0.22, 'F', 'VVS2', 'Shallow', 14.6, @StartDateGem),
	('Natural',  0.18, 0.22, 'D', 'VS1', 'Ideal', 14.6, @StartDateGem),
	('Natural',  0.18, 0.22, 'E', 'VS2', 'Ideal', 14.6, @StartDateGem),
		
	('Natural', 0.23, 0.29, 'F', 'IF', 'Ideal', 17.4, @StartDateGem),
	('Natural', 0.23, 0.29, 'D', 'VVS1', 'Ideal', 17.4, @StartDateGem),
	('Natural', 0.23, 0.29, 'E', 'VVS2', 'Ideal', 17.4, @StartDateGem),
	('Natural', 0.23, 0.29, 'J', 'VS1', 'Ideal', 12.5, @StartDateGem),
	('Natural', 0.23, 0.29, 'F', 'VS2', 'Shallow', 17.4, @StartDateGem),
		
	('Natural', 0.40, 0.49, 'J', 'IF', 'Ideal', 22, @StartDateGem),
	('Natural', 0.40, 0.49, 'D', 'VVS1', 'Ideal', 33, @StartDateGem),
	('Natural', 0.40, 0.49, 'E', 'VVS2', 'Shallow', 28, @StartDateGem),
	('Natural', 0.40, 0.49, 'F', 'VS1', 'Ideal', 24, @StartDateGem),
	('Natural', 0.40, 0.49, 'J', 'VS2', 'Shallow', 15, @StartDateGem),


	-- 0.5 - 0.1.49 ct
	('Natural', 0.50, 0.69, 'D', 'IF', 'Shallow', 65, @StartDateGem),
	('Natural',  0.50, 0.69, 'F', 'VVS1', 'Shallow', 43, @StartDateGem),
	('Natural',  0.50, 0.69, 'E', 'VVS2', 'Ideal', 39, @StartDateGem),
	('Natural',  0.50, 0.69, 'J', 'IF', 'Ideal', 23, @StartDateGem),
	('Natural',  0.50, 0.69, 'D', 'VS2', 'Ideal', 43, @StartDateGem),
		
	('Natural', 0.70, 0.89, 'E', 'IF', 'Ideal', 70, @StartDateGem),
	('Natural', 0.70, 0.89, 'J', 'VVS1', 'Ideal', 31, @StartDateGem),
	('Natural', 0.70, 0.89, 'D', 'VVS2', 'Ideal', 58, @StartDateGem),
	('Natural', 0.70, 0.89, 'F', 'VS1', 'Shallow', 45, @StartDateGem),
	('Natural', 0.70, 0.89, 'E', 'VS2', 'Ideal', 41, @StartDateGem),
		-- F Color
	('Natural', 0.90, 0.99, 'F', 'IF', 'Ideal', 100, @StartDateGem),
	('Natural', 0.90, 0.99, 'E', 'VVS1', 'Ideal', 99, @StartDateGem),
	('Natural', 0.90, 0.99, 'J', 'VVS1', 'Shallow', 46, @StartDateGem),
	('Natural', 0.90, 0.99, 'D', 'VS1', 'Ideal', 71, @StartDateGem),
	('Natural', 0.90, 0.99, 'F', 'VVS1', 'Shallow', 91, @StartDateGem),
		-- J Color
	('Natural', 1.00, 1.49, 'J', 'IF', 'Ideal', 57, @StartDateGem),
	('Natural', 1.00, 1.49, 'F', 'IF', 'Shallow', 126, @StartDateGem),
	('Natural', 1.00, 1.49, 'E', 'VVS2', 'Ideal', 112, @StartDateGem),
	('Natural', 1.00, 1.49, 'D', 'VS1', 'Ideal', 108, @StartDateGem),
	('Natural', 1.00, 1.49, 'J', 'IF', 'Ideal', 58, @StartDateGem),


	-- 1.5 - 4.99 ct
	('Natural', 1.50, 1.99, 'D', 'IF', 'Shallow', 243, @StartDateGem),
	('Natural',  1.50, 1.99, 'F', 'VVS1', 'Shallow', 171, @StartDateGem),
	('Natural',  1.50, 1.99, 'E', 'VVS2', 'Ideal', 160, @StartDateGem),
	('Natural',  1.50, 1.99, 'J', 'IF', 'Ideal', 85, @StartDateGem),
	('Natural',  1.50, 1.99, 'D', 'VS2', 'Ideal', 126, @StartDateGem),
		
	('Natural', 2.00, 2.99, 'E', 'IF', 'Ideal', 300, @StartDateGem),
	('Natural', 2.00, 2.99, 'J', 'VVS1', 'Ideal', 110, @StartDateGem),
	('Natural', 2.00, 2.99, 'D', 'VVS2', 'Ideal', 260, @StartDateGem),
	('Natural', 2.00, 2.99, 'F', 'VS1', 'Shallow', 195, @StartDateGem),
	('Natural', 2.00, 2.99, 'E', 'VS2', 'Ideal', 175, @StartDateGem),
		
	('Natural', 3.00, 3.99, 'F', 'IF', 'Ideal', 450, @StartDateGem),
	('Natural', 3.00, 3.99, 'E', 'VVS1', 'Ideal', 485, @StartDateGem),
	('Natural', 3.00, 3.99, 'J', 'VVS1', 'Shallow', 225, @StartDateGem),
	('Natural', 3.00, 3.99, 'D', 'VS1', 'Ideal', 400, @StartDateGem),
	('Natural', 3.00, 3.99, 'F', 'VVS1', 'Shallow', 425, @StartDateGem),
		
	('Natural', 4.00, 4.99, 'J', 'IF', 'Ideal', 230, @StartDateGem),
	('Natural', 4.00, 4.99, 'F', 'IF', 'Shallow', 580, @StartDateGem),
	('Natural', 4.00, 4.99, 'E', 'VVS2', 'Ideal', 550, @StartDateGem),
	('Natural', 4.00, 4.99, 'D', 'VS1', 'Ideal', 535, @StartDateGem),
	('Natural', 4.00, 4.99, 'J', 'IF', 'Ideal', 230, @StartDateGem),

	-- 1.5 - 4.99 ct
	('Natural', 5.00, 5.99, 'D', 'IF', 'Shallow', 1130, @StartDateGem),
	('Natural',  5.00, 5.99, 'F', 'VVS1', 'Shallow', 715, @StartDateGem),
	('Natural',  5.00, 5.99, 'E', 'VVS2', 'Ideal', 730, @StartDateGem),
	('Natural',  5.00, 5.99, 'J', 'IF', 'Ideal', 300, @StartDateGem),
	('Natural',  5.00, 5.99, 'D', 'VS2', 'Ideal', 835, @StartDateGem),

	('Natural', 10.00, 10.99, 'E', 'IF', 'Ideal', 1380, @StartDateGem),
	('Natural', 10.00, 10.99, 'J', 'VVS1', 'Ideal', 430, @StartDateGem),
	('Natural', 10.00, 10.99, 'D', 'VVS2', 'Ideal', 1300, @StartDateGem),
	('Natural', 10.00, 10.99, 'F', 'VS1', 'Shallow', 875, @StartDateGem),
	('Natural', 10.00, 10.99, 'E', 'VS2', 'Ideal', 875, @StartDateGem);
	

    SET @StartDateGem = DATEADD(day, 1, @StartDateGem);
END;

SET IDENTITY_INSERT [Material] OFF


-- Insert data into the [Diamond] table
SET IDENTITY_INSERT [Diamond] ON;
INSERT INTO [dbo].[Diamond] ([Dia_Id], [Dia_Code], [Dia_Name], [Origin], [Carat_Weight], [Color], [Clarity], [Cut], [Proportions], [Polish], [Symmetry], [Fluorescence], [Status], [Q_Price], [O_Price], [Img_Url])
VALUES
-- Gemstones with D Color
(1, 'DIA001', 'Round Brilliant Cut Diamond', 'Natural', 1.85, 'D', 'IF', 'Shallow', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 243, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=b0c525b0-1f57-4bfd-a9be-d4ad5bfb300d'),
(2, 'DIA002', 'Round Brilliant Cut Diamond', 'Natural', 0.49, 'D', 'VVS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 33, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=5fdd9030-1600-46f2-9642-3710806c131f'),
(3, 'DIA003', 'Round Brilliant Cut Diamond', 'Natural', 2.99, 'E', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 300, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=a05edcef-6e3b-4e6c-a487-0e9164895fa9'),
(4, 'DIA004', 'Round Brilliant Cut Diamond', 'Natural', 2.75, 'J', 'VVS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 110, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=cbac271e-76e7-4d3d-ae9e-9383e9a9b874'),
(5, 'DIA005', 'Round Brilliant Cut Diamond', 'Natural', 2.99, 'D', 'VVS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 260, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=41823da8-ca9f-4507-baf7-75729beded75'),
 
-- Gemstones with E Color
(6, 'DIA006', 'Round Brilliant Cut Diamond', 'Natural', 4.5, 'E', 'VVS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 550, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2%20(1).jpg?alt=media&token=2acf8ac0-8299-42be-8c45-2c8b88ba50b5'),
(7, 'DIA007', 'Round Brilliant Cut Diamond', 'Natural', 4.25, 'D', 'VS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 535, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(4).jpg?alt=media&token=a60a7f36-76ba-4d3b-aee2-c1388db933f8'),
(8, 'DIA008', 'Round Brilliant Cut Diamond', 'Natural', 3.25, 'F', 'VVS1', 'Shallow', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 425, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(3).jpg?alt=media&token=5abf0b4b-7fbd-47a2-ad80-cbdb0675b614'),
(9, 'DIA009', 'Round Brilliant Cut Diamond', 'Natural', 10, 'E', 'VS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 875, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(2).jpg?alt=media&token=fcf5b459-37e5-438a-b8d9-ac10648cdb0b'),
(10, 'DIA010', 'Round Brilliant Cut Diamond', 'Natural', 2.21, 'E', 'VS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 175, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(2).jpg?alt=media&token=2fa766eb-5963-44c9-8022-b9bb7e4d4fb3'),

-- Gemstones with F Color
(11, 'DIA011', 'Round Brilliant Cut Diamond', 'Natural', 2.5, 'E', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 300, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=1c8f4268-c8d1-43ff-9be6-4732bdec7bde'),
(12, 'DIA012', 'Round Brilliant Cut Diamond', 'Natural', 3.5, 'E', 'VVS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 485, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=87042c63-0701-49e9-b7c4-60ec0437f150'),
(13, 'DIA013', 'Round Brilliant Cut Diamond', 'Natural', 1.5, 'J', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 85, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=cb6cacce-4d3e-4531-905b-6d2d36c58995'),
(14, 'DIA014', 'Round Brilliant Cut Diamond', 'Natural', 1.25, 'D', 'VS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 108, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=95349540-6f34-4d02-b934-466e1d14cf47'),
(15, 'DIA015', 'Round Brilliant Cut Diamond', 'Natural', 5.5, 'F', 'VVS1', 'Shallow', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 715, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=ef69675e-35c7-463f-bceb-d70e96063993'),

-- Gemstones with J Color
(16, 'DIA016', 'Round Brilliant Cut Diamond', 'Natural', 2.25, 'J', 'VVS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 110, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=2d1fac7d-419a-479e-b429-a5e598ead86b'),
(17, 'DIA017', 'Round Brilliant Cut Diamond', 'Natural', 5.25, 'J', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 300, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=b6cf71d6-3292-4dec-9f0d-d4e6cb84752c'),
(18, 'DIA018', 'Round Brilliant Cut Diamond', 'Natural', 10.25, 'D', 'VVS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 430, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=35a9e414-0f63-4a86-aed7-8488ec8e22d7'),
(19, 'DIA019', 'Round Brilliant Cut Diamond', 'Natural', 3.25, 'J', 'VSS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 225, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=95210446-0cac-412c-b7f1-d1c33152b79b'),
(20, 'DIA020', 'Round Brilliant Cut Diamond', 'Natural', 4.75, 'J', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 230, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=5c853e49-3426-4c6b-9174-cbec99f5078f'),


(21, 'DIA021', 'Round Brilliant Cut Diamond', 'Natural', 0.45, 'J', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 22, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=b0c525b0-1f57-4bfd-a9be-d4ad5bfb300d'),
(22, 'DIA022', 'Round Brilliant Cut Diamond', 'Natural', 0.75, 'D', 'VVS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 58, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=5fdd9030-1600-46f2-9642-3710806c131f'),
(23, 'DIA023', 'Round Brilliant Cut Diamond', 'Natural', 3.55, 'D', 'VS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 400, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=a05edcef-6e3b-4e6c-a487-0e9164895fa9'),
(24, 'DIA024', 'Round Brilliant Cut Diamond', 'Natural', 2.65, 'F', 'VS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 175, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=41823da8-ca9f-4507-baf7-75729beded75'),
(25, 'DIA025', 'Round Brilliant Cut Diamond', 'Natural', 5.34, 'D', 'VS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 835, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=cbac271e-76e7-4d3d-ae9e-9383e9a9b874'),

-- Gemstones with E Color
(26, 'DIA026', 'Round Brilliant Cut Diamond', 'Natural', 3.45, 'F', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 450, 0,'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(2).jpg?alt=media&token=2fa766eb-5963-44c9-8022-b9bb7e4d4fb3'),
(27, 'DIA027', 'Round Brilliant Cut Diamond', 'Natural', 3.35, 'J', 'VVS1', 'Shallow', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 225, 0,'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(2).jpg?alt=media&token=fcf5b459-37e5-438a-b8d9-ac10648cdb0b'),
(28, 'DIA028', 'Round Brilliant Cut Diamond', 'Natural', 4.53, 'E', 'VVS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 550, 0,'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(3).jpg?alt=media&token=5abf0b4b-7fbd-47a2-ad80-cbdb0675b614'),
(29, 'DIA029', 'Round Brilliant Cut Diamond', 'Natural', 2.75, 'E', 'VS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 175, 0,'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(4).jpg?alt=media&token=a60a7f36-76ba-4d3b-aee2-c1388db933f8'),
(30, 'DIA030', 'Round Brilliant Cut Diamond', 'Natural', 1.55, 'D', 'VS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 126, 0,'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2%20(1).jpg?alt=media&token=2acf8ac0-8299-42be-8c45-2c8b88ba50b5'),

-- Gemstones with F Color
(31, 'DIA031', 'Round Brilliant Cut Diamond', 'Natural', 3.45, 'F', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 450, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=1c8f4268-c8d1-43ff-9be6-4732bdec7bde'),
(32, 'DIA032', 'Round Brilliant Cut Diamond', 'Natural', 4.87, 'E', 'VVS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 550, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=87042c63-0701-49e9-b7c4-60ec0437f150'),
(33, 'DIA033', 'Round Brilliant Cut Diamond', 'Natural', 4.99, 'J', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 230, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=cb6cacce-4d3e-4531-905b-6d2d36c58995'),
(34, 'DIA034', 'Round Brilliant Cut Diamond', 'Natural', 5.99, 'F', 'VVS1', 'Shallow', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 715, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=95349540-6f34-4d02-b934-466e1d14cf47'),
(35, 'DIA015', 'Round Brilliant Cut Diamond', 'Natural', 5.99, 'D', 'VS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 835, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=ef69675e-35c7-463f-bceb-d70e96063993'),

-- Gemstones with J Color
(36, 'DIA036', 'Round Brilliant Cut Diamond', 'Natural', 1.75, 'J', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 58, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=5c853e49-3426-4c6b-9174-cbec99f5078f'),
(37, 'DIA037', 'Round Brilliant Cut Diamond', 'Natural', 2.75, 'J', 'VVS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 110, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=95210446-0cac-412c-b7f1-d1c33152b79b'),
(38, 'DIA038', 'Round Brilliant Cut Diamond', 'Natural', 3.5, 'F', 'VVS1', 'Shallow', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 425, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=35a9e414-0f63-4a86-aed7-8488ec8e22d7'),
(39, 'DIA039', 'Round Brilliant Cut Diamond', 'Natural', 4.3, 'D', 'VS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 535, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=b6cf71d6-3292-4dec-9f0d-d4e6cb84752c'),
(40, 'DIA040', 'Round Brilliant Cut Diamond', 'Natural', 4.99, 'J', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 230, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=2d1fac7d-419a-479e-b429-a5e598ead86b'),

-- Gemstones with D Color
(41, 'DIA041', 'Round Brilliant Cut Diamond', 'Natural', 0.85, 'F', 'VS1', 'Shallow', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 45, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=b0c525b0-1f57-4bfd-a9be-d4ad5bfb300d'),
(42, 'DIA042', 'Round Brilliant Cut Diamond', 'Natural', 2.5, 'D', 'VVS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 260, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=5fdd9030-1600-46f2-9642-3710806c131f'),
(43, 'DIA043', 'Round Brilliant Cut Diamond', 'Natural', 10.5, 'D', 'VVS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 1300, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=a05edcef-6e3b-4e6c-a487-0e9164895fa9'),
(44, 'DIA044', 'Round Brilliant Cut Diamond', 'Natural', 3.5, 'F', 'VVS1', 'Shallow', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 425, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=cbac271e-76e7-4d3d-ae9e-9383e9a9b874'),
(45, 'DIA045', 'Round Brilliant Cut Diamond', 'Natural', 10.5, 'F', 'VS1', 'Shallow', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 875, 0, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=41823da8-ca9f-4507-baf7-75729beded75');
SET IDENTITY_INSERT [Diamond] OFF


SET IDENTITY_INSERT [Product] ON;
-- Insert data into the Product table
INSERT INTO [dbo].[Product] (
	[Product_Id], 
	[Product_Code], 
	[Product_Name], 
	[Description], 
	[Gender], 
	[Size], 
	[Status], 
	[Img_Url],
	[Collection_Id])
VALUES
(1, 'PO00001', 'Radiant Sunburst Diamond Ring', 'Radiant sunburst design featuring a brilliant round cut diamond center stone.', 'Unisex', 7, 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fring%2FDefault_A_classic_22K_gold_ring_with_a_brilliant_diamond_cente_0.jpg?alt=media&token=2de10ada-0f96-4fe3-8a0d-691ed9a65a50', NULL),
(2, 'PO00002', 'Ethereal Dream Diamond Necklace', 'An ethereal dream necklace showcasing a mesmerizing round brilliant cut diamond pendant.', 'Women', 18, 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fnecklace%2FDefault_A_24K_gold_pendant_with_a_diamond_complemented_by_whit_0.jpg?alt=media&token=0b6c421a-5468-413f-aac3-7a658a9a6934', NULL),
(3, 'PO00003', 'Starry Night Diamond Earrings', 'Starry night inspired diamond earrings featuring dazzling round brilliant cut diamonds.', 'Women', 8, 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fearrings%2FDefault_10K_Gold_Diamond_Ear_Cuffs_Stylish_10K_gold_ear_cuffs_0.jpg?alt=media&token=f58d81f6-4322-4344-a340-4411babc3235', NULL),
(4, 'PO00004', 'Celestial Cascade Diamond Bracelet', 'Celestial cascade bracelet adorned with cascading round brilliant cut diamonds.', 'Women', 7, 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fbracelet%2FDefault_10K_Gold_Diamond_Charm_Bracelet_A_10K_gold_charm_brace_0.jpg?alt=media&token=e4ef44db-812c-4e48-a0cf-08fba66f5e07', NULL),
(5, 'PO00005', 'Lunar Elegance Diamond Cufflinks', 'Lunar elegance cufflinks crafted with precision and featuring round brilliant cut diamonds.', 'Men', 10, 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fcufflinks%2FDefault_A_Gold_PNJ_9999_tennis_cufflink_lined_with_evenly_spac_0.jpg?alt=media&token=6595906e-39ca-415b-8034-d03b09bf833a', NULL),
(6, 'PO00006', 'Diamond Galaxy Engagement Ring', 'Embark on a journey with this diamond galaxy engagement ring, featuring a stunning round brilliant cut diamond.', 'Women', 7, 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fring%2FDefault_22K_Gold_Diamond_Wedding_Band_A_22K_gold_wedding_band_0.jpg?alt=media&token=2619ffed-9c99-4e1e-95c7-7545e638ecf9', NULL),
(7, 'PO00007', 'Aurora Borealis Diamond Pendant', 'Capture the beauty of the northern lights with this aurora borealis diamond pendant.', 'Women', 18, 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fnecklace%2FDefault_22K_Gold_Diamond_Pendant_Necklace_A_22K_gold_pendant_n_0.jpg?alt=media&token=61c6b5b1-fc4a-4ba2-adf7-f50f8ab58732', NULL),
(8, 'PO00008', 'Eternal Love Diamond Earrings',  'Symbolize eternal love with these breathtaking diamond earrings featuring round brilliant cut diamonds.', 'Women', 8, 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fearrings%2FDefault_22K_Gold_Diamond_Hoops_Stylish_22K_gold_hoop_earrings_0.jpg?alt=media&token=e5214a2e-6f63-4ced-aa0b-d02dcd82c08e', NULL),
(9, 'PO00009', 'Twilight Serenade Diamond Bracelet', 'Serene elegance meets twilight allure in this stunning diamond bracelet.', 'Women', 7, 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fbracelet%2FDefault_A_10K_gold_bangle_encrusted_with_diamonds_for_a_sophis_0.jpg?alt=media&token=8d6cf4f7-2757-41e9-82d5-201dbc8d2a95', NULL),
(10, 'PO00010', 'Galactic Odyssey Diamond Cufflinks', 'Embark on a galactic odyssey with these exquisite diamond cufflinks.', 'Men', 10, 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2Fcufflinks%2FDefault_A_Gold_SJC_9999_cufflink_with_a_single_diamond_for_a_0.jpg?alt=media&token=c5e09f05-594b-4d92-927c-e879af788623', NULL);

SET IDENTITY_INSERT [Product] OFF

-- Insert data into the ProductMaterial table
INSERT INTO [dbo].[Product_Material] (Product_Id, Material_Id, Material_Weight, [Q_Price], [O_Price])
VALUES
-- Combining products with materials and assigning weights
(1, 1, 1, 300, NULL), 
(2, 2, 1, 300, NULL), 
(3, 3, 1, 300, NULL),  
(4, 4, 2, 300, NULL),
(5, 5, 2, 300, NULL), 
(6, 6, 2, 300, NULL),  
(7, 7, 3, 300, NULL),  
(8, 8, 3, 300, NULL),   
(9, 9, 3, 300, NULL),   
(10, 10, 3, 300, NULL);

/*
INSERT INTO [dbo].[Production_Order] (
	[Production_Order_Id], 
	[Date], 

	[Customer_Id], 
	[Category_Id], 

	[Product_Size], 
	[Description], 

	[Img_Url],

	[Q_Diamond_Amount], 
	[Q_Material_Amount], 
	[Q_Production_Amount], 
	[Q_Total_Amount],

	[O_Diamond_Amount], 
	[O_Material_Amount], 
	[O_Production_Amount], 
	[O_Total_Amount], 

	[Sales_Staff_Id], 
	[Design_Staff_Id], 
	[Production_Staff_Id], 
	[Status], 
	[Product_Id])
VALUES 
('POI00001', '2024-05-28', 'CUS010', 3, 14, 'https://firebasestorage.googleapis.com/v0/b/demofirebase-b958b.appspot.com/o/540dc9d4-aba3-4268-acce-a8c8ea4c2f8a.jpg?alt=media', 'Custom earrings design', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'created', NULL),
('POI00002', '2024-05-28', 'CUS010', 3, 14, 'https://firebasestorage.googleapis.com/v0/b/demofirebase-b958b.appspot.com/o/540dc9d4-aba3-4268-acce-a8c8ea4c2f8a.jpg?alt=media', 'Pair of gold cufflinks featuring white diamond stones', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'created', NULL),

('POI00003', '2024-05-28', 'CUS001', 1, 14, 'https://firebasestorage.googleapis.com/v0/b/demofirebase-b958b.appspot.com/o/540dc9d4-aba3-4268-acce-a8c8ea4c2f8a.jpg?alt=media', 'Custom earrings design', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'requested', NULL),
('POI00004', '2024-05-28', 'CUS002', 2, 44, 'https://firebasestorage.googleapis.com/v0/b/demofirebase-b958b.appspot.com/o/540dc9d4-aba3-4268-acce-a8c8ea4c2f8a.jpg?alt=media', 'Pair of gold cufflinks featuring white diamond stones', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Karen Walker', NULL, NULL, 'requested', NULL),

('POI00005', '2024-05-28', 'CUS010', 3, 24, 'https://firebasestorage.googleapis.com/v0/b/demofirebase-b958b.appspot.com/o/540dc9d4-aba3-4268-acce-a8c8ea4c2f8a.jpg?alt=media', 'Pair of gold cufflinks featuring white diamond stones', 100, 100, 100, 300, 100, 100, 100, 300, 'Karen Walker', NULL, NULL, 'quoted (NAC)', NULL),
('POI00006', '2024-05-28', 'CUS010', 3, 34, 'https://firebasestorage.googleapis.com/v0/b/demofirebase-b958b.appspot.com/o/540dc9d4-aba3-4268-acce-a8c8ea4c2f8a.jpg?alt=media', 'Custom earrings design', 100, 100, 100, 300, 100, 100, 100, 300, 'Jack Turner', NULL, NULL, 'quoted (NAM)', NULL),

('POI00007', '2024-05-28', 'CUS001', 1, 34, 'https://firebasestorage.googleapis.com/v0/b/demofirebase-b958b.appspot.com/o/540dc9d4-aba3-4268-acce-a8c8ea4c2f8a.jpg?alt=media', 'Custom earrings design', 100, 100, 100, 300, 100, 100, 100, 300, 'Karen Walker', 'Mia Nelson', NULL, 'ordered', 'PO010');
*/

--SET IDENTITY_INSERT [Product] OFF
SET IDENTITY_INSERT [Product_Design_Shell] ON;


-- Insert data into the ProductDesignShell table
INSERT INTO [dbo].[Product_Design_Shell] (Product_Design_Shell_Id, Material_Id, Weight)
VALUES

-- Gold 10K (MaterialId = 3)
(1, 3, 1),
(2, 3, 2),
(3, 3, 3),
(4, 3, 4),

-- Gold 15.6K (MaterialId = 5)
(5, 5, 1),
(6, 5, 2),
(7, 5, 3),
(8, 5, 4),

-- Gold 18K (MaterialId = 7)
(9, 7, 1),
(10, 7, 2),
(11, 7, 3),
(12, 7, 4),

-- Gold 22K (MaterialId = 8)
(13, 8, 1),
(14, 8, 2),
(15, 8, 3),
(16, 8, 4),

-- SJC Gold Piece (MaterialId = 9)
(17, 9, 1),
(18, 9, 2),
(19, 9, 3),
(20, 9, 4),

-- PNJ Ring Gold 24K (MaterialId = 10)
(21, 10, 1),
(22, 10, 2),
(23, 10, 3),
(24, 10, 4);
SET IDENTITY_INSERT [Product_Design_Shell] OFF;

--select * from Diamond_Price_List where price = 110

select * from Users
select * from Customer
select * from Employee
select * from Category
select * from Blogs
select * from Collections
select * from Material
select * from Material_Price_List
select * from Diamond_Price_List
select * from Diamond
select * from Product
select * from Production_Order
select * from Product_Material

select * from Product_Design_Shell
select * from Product_Design

use JewelryStore
