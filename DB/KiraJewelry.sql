
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
	[category_Id] int identity(1,1) NOT NULL,
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
	[Description] [nvarchar](max),

	[Gender] [nvarchar](50) NOT NULL,
	[Size] [int] NOT NULL,
	[Img_Url] varchar(255) NOT NULL,

	[Status] bit NOT NULL,
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
	[Date] [date] NOT NULL,

	[Customer_Id] [nvarchar] (255) REFERENCES [Customer](Customer_Id),
	[category_id] int REFERENCES [Category](Category_Id),
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

drop table Cate 

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
	[Dia_Code] [nvarchar](255) NOT NULL,
	[Dia_Name] [nvarchar](255) NOT NULL,

	[Origin] [nvarchar] (255) NOT NULL,
    [Carat_Weight] [float] NOT NULL,
	[Color] [char](10) NOT NULL,
	[Clarity] [char](10) NOT NULL,
	[Cut] [nvarchar] (255) NOT NULL,

	[Proportions] [nvarchar](255) NOT NULL,
	[Polish] [nvarchar](255) NOT NULL,
	[Symmetry] [nvarchar](255) NOT NULL,
	[Fluorescence] [char](10) NOT NULL,
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
	[Product_Design_Code] [nvarchar] (max) NOT NULL,
	[Product_Design_Name] [nvarchar](255) NOT NULL,
	[Category_Id] int REFERENCES [Category](Category_Id),
	[Collection_Id] int REFERENCES [Collections](Collection_Id),
	[Description] [nvarchar](max),

	[Gender] [nvarchar](50) NOT NULL,
	[Product_Size] [int] NOT NULL,

	[Product_Design_Shell_Id] int REFERENCES [Product_Design_Shell]([Product_Design_Shell_Id]),
	[Gem_Min_Size] float,
	[Gem_Max_Size] float,

	[Status] bit NOT NULL,
	[Product_Id] int REFERENCES Product([Product_Id])
CONSTRAINT [PK_Product_Design] PRIMARY KEY CLUSTERED 
(
	[Product_Design_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
drop table product_design 

/*================ INSERT DATA ================*/

--Insert data into the Users table
SET IDENTITY_INSERT [Users] ON;

INSERT INTO [dbo].[Users] (User_Id, Email, Password, Role_ID, Status)
VALUES 
-- 10 Customers
(1, 'truongantnh@gmail.com', 'andhtSE180230@fpt', 1, 11),
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
('AD001', 'Alice Johnson', 11),
('AD002', 'Bob Smith', 12),
('AD003', 'Charlie Brown', 13),
('AD004', 'Diana White', 14),
-- Managers
('MA001', 'Edward King', 15),
('MA002', 'Fiona Green', 16),
('MA003', 'George Black', 17),
('MA004', 'Hannah Blue', 18),
-- Sales Staff
('SS001', 'Ivy Scott', 19),
('SS002', 'Jack Turner', 20),
('SS003', 'Karen Walker', 21),
('SS004', 'Leo Adams', 22),
-- Design Staff
('DE001', 'Mia Nelson', 23),
('DE002', 'Nate Perez', 24),
('DE003', 'Olivia Hill', 25),
('DE004', 'Paul Young', 26),
-- Production Staff
('PR001', 'Quinn Baker', 27),
('PR002', 'Rachel Carter', 28),
('PR003', 'Sam Edwards', 29),
('PR004', 'Tina Flores', 30);

-- Insert data into the Customer table
INSERT INTO [dbo].[Customer] (Customer_Id, Full_Name, Address, Phone_Number, [User_Id])
VALUES
('CUS011', 'An Dao', '125 Valeria St', '0908234123', 11),
('CUS001', 'Ursula Grey', '123 Maple St', '5551110001', 1),
('CUS002', 'Victor Hale', '456 Oak St', '5551110002', 2),
('CUS003', 'Wendy Lane', '789 Pine St', '5551110003', 3),
('CUS004', 'Xander Reed', '101 Birch St', '5551110004', 4),
('CUS005', 'Yvonne Fox', '202 Cedar St', '5551110005', 5),
('CUS006', 'Zachary Stone', '303 Spruce St', '5551110006', 6),
('CUS007', 'Anna Carter', '404 Elm St', '5551110007', 7),
('CUS008', 'Brian Torres', '505 Willow St', '5551110008', 8),
('CUS009', 'Clara Davis', '606 Walnut St', '5551110009', 9),
('CUS010', 'David Evans', '707 Ash St', '5551110010', 10);


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
DECLARE @StartDateGem DATE = '2024-06-11'; -- Start date for generating data
DECLARE @EndDateGem DATE = '2024-06-11';   -- End date for generating data
-- Loop through dates to generate data for each day
WHILE @StartDateGem <= @EndDateGem
BEGIN
    -- Generate data for GemPriceList table (Diamond prices for different dates)
INSERT INTO [dbo].[Diamond_Price_List] ([Origin], [Carat_Weight_From], [Carat_Weight_To], [Color], [Clarity], [Cut], [Price], [Eff_Date])
VALUES
		-- 3.6mm
		-- D Color
	('Vietnam', 0.15, 0.17, 'D', 'IF', 'Excellent', 424.01, @StartDateGem),
	('America',  0.15, 0.17, 'D', 'VVS1', 'Excellent', 384.75, @StartDateGem),
	('Italy',  0.15, 0.17, 'D', 'VVS2', 'Excellent', 345.74, @StartDateGem),
	('Australia',  0.15, 0.17, 'D', 'VS1', 'Excellent', 322.17, @StartDateGem),
	('South Africa',  0.15, 0.17, 'D', 'VS2', 'Excellent', 275.02, @StartDateGem),
		-- E Color
	('Vietnam',  0.15, 0.17, 'E', 'IF', 'Excellent', 412.23, @StartDateGem),
	('America',  0.15, 0.17, 'E', 'VVS1', 'Excellent', 361.19, @StartDateGem),
	('Italy',  0.15, 0.17, 'E', 'VVS2', 'Excellent', 314.31, @StartDateGem),
	('Australia',  0.15, 0.17, 'E', 'VS1', 'Excellent', 282.88, @StartDateGem),
	('South Africa',  0.15, 0.17, 'E', 'VS2', 'Excellent', 196.44, @StartDateGem),
		-- F Color
	('Vietnam', 0.15, 0.17, 'F', 'IF', 'Excellent', 417.02, @StartDateGem),
	('America', 0.15, 0.17, 'F', 'VVS1', 'Excellent', 387.23, @StartDateGem),
	('Italy', 0.15, 0.17, 'F', 'VVS2', 'Excellent', 300.928, @StartDateGem),
	('Australia', 0.15, 0.17, 'F', 'VS1', 'Excellent', 255.32, @StartDateGem),
	('South Africa', 0.15, 0.17, 'F', 'VS2', 'Excellent', 165.96, @StartDateGem),
		-- J Color
	('Vietnam', 0.15, 0.17, 'J', 'IF', 'Excellent', 340.43 , @StartDateGem),
	('America', 0.15, 0.17, 'J', 'VVS1', 'Excellent', 331.91, @StartDateGem),
	('Italy', 0.15, 0.17, 'J', 'VVS2', 'Excellent', 276.60, @StartDateGem),
	('Australia', 0.15, 0.17, 'J', 'VS1', 'Excellent', 263.83, @StartDateGem),
	('South Africa', 0.15, 0.17, 'J', 'VS2', 'Excellent', 225.53, @StartDateGem);

/*		
		--4.5mm
		-- D Color
	('Vietnam', 0.34, 'D', 'IF', 'Excellent', 1055.32, @StartDateGem),
	('America', 0.34, 'D', 'VVS1', 'Excellent', 902.13, @StartDateGem),
	('Italy', 0.34, 'D', 'VVS2', 'Excellent', 842.55, @StartDateGem),
	('Australia', 0.34, 'D', 'VS1', 'Excellent', 787.23, @StartDateGem),
	('South Africa', 0.34, 'D', 'VS2', 'Excellent', 680.85, @StartDateGem),
		-- E Color
	('Vietnam', 0.34, 'E', 'IF', 'Excellent', 961.70, @StartDateGem),
	('America', 0.34, 'E', 'VVS1', 'Excellent', 817.02, @StartDateGem),
	('Italy', 0.34, 'E', 'VVS2', 'Excellent', 744.68, @StartDateGem),
	('Australia', 0.34, 'E', 'VS1', 'Excellent', 685.11, @StartDateGem),
	('South Africa', 0.34, 'E', 'VS2', 'Excellent', 634.04, @StartDateGem),
		-- F Color
	('Vietnam', 0.34, 'F', 'IF', 'Excellent', 931.91, @StartDateGem),
	('America', 0.34, 'F', 'VVS1', 'Excellent', 770.21, @StartDateGem),
	('Italy', 0.34, 'F', 'VVS2', 'Excellent', 680.85, @StartDateGem),
	('Australia', 0.34, 'F', 'VS1', 'Excellent', 629.79, @StartDateGem),
	('South Africa', 0.34, 'F', 'VS2', 'Excellent', 510.64, @StartDateGem),
		-- J Color
	('Vietnam', 0.34, 'J', 'IF', 'Excellent', 595.74, @StartDateGem),
	('America', 0.34, 'J', 'VVS1', 'Excellent', 587.23, @StartDateGem),
	('Italy', 0.34, 'J', 'VVS2', 'Excellent', 561.70, @StartDateGem),
	('Australia', 0.34, 'J', 'VS1', 'Excellent', 548.94, @StartDateGem),
	('South Africa', 0.34, 'J', 'VS2', 'Excellent', 446.81, @StartDateGem),

		--5mm
		-- D Color
	('Vietnam', 0.50, 'D', 'IF', 'Excellent', 1659.57, @StartDateGem),
	('America', 0.50, 'D', 'VVS1', 'Excellent', 1553.19, @StartDateGem),
	('Italy', 0.50, 'D', 'VVS2', 'Excellent', 1489.36, @StartDateGem),
	('Australia', 0.50, 'D', 'VS1', 'Excellent', 1361.70, @StartDateGem),
	('South Africa', 0.50, 'D', 'VS2', 'Excellent', 1297.87, @StartDateGem),
		-- E Color
	('Vietnam', 0.50, 'E', 'IF', 'Excellent', 1621.28, @StartDateGem),
	('America', 0.50, 'E', 'VVS1', 'Excellent', 1523.40, @StartDateGem),
	('Italy', 0.50, 'E', 'VVS2', 'Excellent', 1404.26, @StartDateGem),
	('Australia', 0.50, 'E', 'VS1', 'Excellent', 1280.85, @StartDateGem),
	('South Africa', 0.50, 'E', 'VS2', 'Excellent', 1234.04, @StartDateGem),
		-- F Color
	('Vietnam', 0.50, 'F', 'IF', 'Excellent', 1370.21, @StartDateGem),
	('America', 0.50, 'F', 'VVS1', 'Excellent', 1323.40, @StartDateGem),
	('Italy', 0.50, 'F', 'VVS2', 'Excellent', 1100.928, @StartDateGem),
	('Australia', 0.50, 'F', 'VS1', 'Excellent', 978.72, @StartDateGem),
	('South Africa', 0.50, 'F', 'VS2', 'Excellent', 859.57, @StartDateGem),
		-- J Color
	('Vietnam', 0.50, 'J', 'IF', 'Excellent', 1076.60, @StartDateGem),
	('America', 0.50, 'J', 'VVS1', 'Excellent', 1012.77, @StartDateGem),
	('Italy', 0.50, 'J', 'VVS2', 'Excellent', 1042.55, @StartDateGem),
	('Australia', 0.50, 'J', 'VS1', 'Excellent', 846.81, @StartDateGem),
	('South Africa', 0.50, 'J', 'VS2', 'Excellent', 765.96, @StartDateGem),
    
		-- 5.2mm, 
		-- D Color
    ('Vietnam', 0.52, 'D', 'IF', 'Excellent', 2139.69, @StartDateGem),
    ('Vietnam', 0.52, 'D', 'VVS1', 'Excellent', 2072.94, @StartDateGem),
    ('Vietnam', 0.52, 'D', 'VVS2', 'Excellent', 1956.59, @StartDateGem),
    ('Vietnam', 0.52, 'D', 'VS1', 'Excellent', 1830.86, @StartDateGem),
    ('Vietnam', 0.52, 'D', 'VS2', 'Excellent', 1654.06, @StartDateGem),
		-- E Color
    ('Vietnam', 0.52, 'E', 'IF', 'Excellent', 2041.54, @StartDateGem),
    ('Vietnam', 0.52, 'E', 'VVS1', 'Excellent', 1970.87, @StartDateGem),
    ('Vietnam', 0.52, 'E', 'VVS2', 'Excellent', 2047.83, @StartDateGem),
    ('Vietnam', 0.52, 'E', 'VS1', 'Excellent', 1960.87, @StartDateGem),
    ('Vietnam', 0.52, 'E', 'VS2', 'Excellent', 1773.91, @StartDateGem),
		-- F Color
    ('Vietnam', 0.52, 'F', 'IF', 'Excellent', 1521.74, @StartDateGem),
    ('Vietnam', 0.52, 'F', 'VVS1', 'Excellent', 1448.70, @StartDateGem),
    ('Vietnam', 0.52, 'F', 'VVS2', 'Excellent', 1978.26, @StartDateGem),
    ('Vietnam', 0.52, 'F', 'VS1', 'Excellent', 1878.26, @StartDateGem),
    ('Vietnam', 0.52, 'F', 'VS2', 'Excellent', 1678.26, @StartDateGem),
		-- J Color
    ('Vietnam', 0.52, 'J', 'IF', 'Excellent', 1391.30, @StartDateGem),
    ('Vietnam', 0.52, 'J', 'VVS1', 'Excellent',  1352.17, @StartDateGem),
    ('Vietnam', 0.52, 'J', 'VVS2', 'Excellent', 1869.57, @StartDateGem),
    ('Vietnam', 0.52, 'J', 'VS1', 'Excellent', 1778.26, @StartDateGem),
    ('Vietnam', 0.52, 'J', 'VS2', 'Excellent', 1586.96, @StartDateGem),
    
	--5.4mm
		-- D Color
	('America', 0.6, 'D', 'IF', 'Excellent', 3109.42, @StartDateGem),
    ('America', 0.6, 'D', 'VVS1', 'Excellent', 2952.37, @StartDateGem),
    ('Italy', 0.6, 'D', 'VVS2', 'Excellent', 2746.29, @StartDateGem),
    ('America', 0.6, 'D', 'VS1', 'Excellent', 2471.27, @StartDateGem),
    ('South Africa', 0.6, 'D', 'VS2', 'Excellent', 2153.03, @StartDateGem),
		-- E Color
    ('Australia', 0.6, 'E', 'IF', 'Excellent', 2703.08, @StartDateGem),
    ('America', 0.6, 'E', 'VVS1', 'Excellent', 2604.85, @StartDateGem),
    ('Australia', 0.6, 'E', 'VVS2', 'Excellent', 2538.06, @StartDateGem),
    ('Australia', 0.6, 'E', 'VS1', 'Excellent', 2278.76, @StartDateGem),
    ('South Africa', 0.6, 'E', 'VS2', 'Excellent', 2101.96, @StartDateGem),
		-- F color
	('Italy', 0.6, 'F', 'IF', 'Excellent', 2325.90, @StartDateGem),
    ('Italy', 0.6, 'F', 'VVS1', 'Excellent', 2219.82, @StartDateGem),
    ('Italy', 0.6, 'F', 'VVS2', 'Excellent', 1956.59, @StartDateGem),
    ('Australia', 0.6, 'F', 'VS1', 'Excellent', 1771.93, @StartDateGem),
    ('Italy', 0.6, 'F', 'VS2', 'Excellent', 1673.71, @StartDateGem),
		-- J color
	('South Africa', 0.6, 'J', 'IF', 'Excellent', 1449.76, @StartDateGem),
    ('South Africa', 0.6, 'J', 'VVS1', 'Excellent', 1339.75, @StartDateGem),
    ('Italy', 0.6, 'J', 'VVS2', 'Excellent', 1280.82, @StartDateGem),
    ('South Africa', 0.6, 'J', 'VS1', 'Excellent', 1159.02, @StartDateGem),
    ('South Africa', 0.6, 'J', 'VS2', 'Excellent', 1084.37, @StartDateGem),
	
	 --6mm
	 -- D Color
    ('Italy', 0.84, 'D', 'IF', 'Excellent', 4781.90, @StartDateGem),
    ('Vietnam', 0.84, 'D', 'VVS1', 'Excellent', 4679.83, @StartDateGem),
    ('Italy', 0.84, 'D', 'VVS2', 'Excellent', 4266.77, @StartDateGem),
    ('Vietnam', 0.84, 'D', 'VS1', 'Excellent', 3095.97, @StartDateGem),
    ('South Africa', 0.84, 'D', 'VS2', 'Excellent', 2954.53, @StartDateGem),
    -- E Color
    ('Vietnam', 0.84, 'E', 'IF', 'Excellent', 4698.95, @StartDateGem),
    ('Australia', 0.84, 'E', 'VVS1', 'Excellent', 4655.73, @StartDateGem),
    ('America', 0.84, 'E', 'VVS2', 'Excellent', 4188.20, @StartDateGem),
    ('Australia', 0.84, 'E', 'VS1', 'Excellent', 2993.81, @StartDateGem),
    ('South Africa', 0.84, 'E', 'VS2', 'Excellent', 2868.09, @StartDateGem),
    -- F Color
    ('Australia', 0.84, 'F', 'IF', 'Excellent', 4640.02, @StartDateGem),
    ('Vietnam', 0.84, 'F', 'VVS1', 'Excellent', 4557.51, @StartDateGem),
    ('America', 0.84, 'F', 'VVS2', 'Excellent', 4007.47, @StartDateGem),
    ('Australia', 0.84, 'F', 'VS1', 'Excellent', 2923.09, @StartDateGem),
    ('South Africa', 0.84, 'F', 'VS2', 'Excellent', 2809.16, @StartDateGem),
    -- J Color
    ('Italy', 0.84, 'J', 'IF', 'Excellent', 2960.921, @StartDateGem),
    ('Vietnam', 0.84, 'J', 'VVS1', 'Excellent', 2781.65, @StartDateGem),
    ('Italy', 0.84, 'J', 'VVS2', 'Excellent', 2746.29, @StartDateGem),
    ('Italy', 0.84, 'J', 'VS1', 'Excellent', 2443.77, @StartDateGem),
    ('South Africa', 0.84, 'J', 'VS2', 'Excellent', 2278.76, @StartDateGem),

		--6.3mm
	('Italy', 0.92, 'D', 'IF', 'Excellent', 8930, @StartDateGem),
    ('Italy', 0.92, 'D', 'VVS1', 'Excellent', 8852.17, @StartDateGem),
    ('Italy', 0.92, 'D', 'VVS2', 'Excellent', 8782.61, @StartDateGem),
    ('Vietnam', 0.92, 'D', 'VS1', 'Excellent', 8669.57, @StartDateGem),
    ('Vietnam', 0.92, 'D', 'VS2', 'Excellent', 8613.04, @StartDateGem),
    
	('Vietnam', 0.92, 'E', 'IF', 'Excellent', 8847.83, @StartDateGem),
    ('America', 0.92, 'E', 'VVS1', 'Excellent', 8769.57, @StartDateGem),
    ('America', 0.92, 'E', 'VVS2', 'Excellent', 8604.35, @StartDateGem),
    ('Vietnam', 0.92, 'E', 'VS1', 'Excellent', 8478.26, @StartDateGem),
    ('Vietnam', 0.92, 'E', 'VS2', 'Excellent', 8278.26, @StartDateGem),
    
	('America', 0.92, 'F', 'IF', 'Excellent', 8617.39, @StartDateGem),
    ('America', 0.92, 'F', 'VVS1', 'Excellent', 8495.65, @StartDateGem),
    ('America', 0.92, 'F', 'VVS2', 'Excellent', 8217.39, @StartDateGem),
    ('America', 0.92, 'F', 'VS1', 'Excellent', 8173.91, @StartDateGem),
    ('Vietnam', 0.92, 'F', 'VS2', 'Excellent', 8043.48, @StartDateGem),
    
	('Vietnam', 0.92, 'J', 'IF', 'Excellent', 4452.17, @StartDateGem),
    ('America', 0.92, 'J', 'VVS1', 'Excellent', 4260.87, @StartDateGem),
    ('Vietnam', 0.92, 'J', 'VVS2', 'Excellent', 3695.65, @StartDateGem),
    ('America', 0.92, 'J', 'VS1', 'Excellent', 3147.83, @StartDateGem),
    ('Vietnam', 0.92, 'J', 'VS2', 'Excellent', 2630.43, @StartDateGem),
    
	--6.8mm
	('Vietnam', 1.25, 'D', 'IF', 'Excellent', 14782.61, @StartDateGem),
    ('South Africa', 1.25, 'D', 'VVS1', 'Excellent', 14739.13, @StartDateGem),
    ('South Africa', 1.25, 'D', 'VVS2', 'Excellent', 14260.87, @StartDateGem),
    ('Vietnam', 1.25, 'D', 'VS1', 'Excellent', 13713.04, @StartDateGem),
    ('South Africa', 1.25, 'D', 'VS2', 'Excellent', 13095.65, @StartDateGem),
    
	('South Africa', 1.25, 'E', 'IF', 'Excellent', 14695.65, @StartDateGem),
    ('South Africa', 1.25, 'E', 'VVS1', 'Excellent', 14347.83, @StartDateGem),
    ('South Africa', 1.25, 'E', 'VVS2', 'Excellent', 14135.65, @StartDateGem),
    ('Vietnam', 1.25, 'E', 'VS1', 'Excellent', 13591.30, @StartDateGem),
    ('Vietnam', 1.25, 'E', 'VS2', 'Excellent', 12695.65, @StartDateGem),
    
	('Italy', 1.25, 'F', 'IF', 'Excellent', 12982.61, @StartDateGem),
    ('Italy', 1.25, 'F', 'VVS1', 'Excellent', 12789.13, @StartDateGem),
    ('Italy', 1.25, 'F', 'VVS2', 'Excellent', 12657.83, @StartDateGem),
    ('Vietnam', 1.25, 'F', 'VS1', 'Excellent', 12382.61, @StartDateGem),
    ('Italy', 1.25, 'F', 'VS2', 'Excellent', 12304.35, @StartDateGem),
    
	('America', 1.25, 'J', 'IF', 'Excellent', 6086.96, @StartDateGem),
    ('America', 1.25, 'J', 'VVS1', 'Excellent', 6017.39, @StartDateGem),
    ('America', 1.25, 'J', 'VVS2', 'Excellent', 5901.74, @StartDateGem),
    ('America', 1.25, 'J', 'VS1', 'Excellent', 5811.30, @StartDateGem),
    ('America', 1.25, 'J', 'VS2', 'Excellent', 5026.09, @StartDateGem),
    
	--7.2mm
	('Vietnam', 1.33, 'D', 'IF', 'Excellent', 19565.22, @StartDateGem),
    ('Australia', 1.33, 'D', 'VVS1', 'Excellent', 18695.65, @StartDateGem),
    ('America', 1.33, 'D', 'VVS2', 'Excellent', 18252.17, @StartDateGem),
    ('Australia', 1.33, 'D', 'VS1', 'Excellent', 18086.96, @StartDateGem),
    ('Italy', 1.33, 'D', 'VS2', 'Excellent', 18043.48, @StartDateGem),
    
	('Italy', 1.33, 'E', 'IF', 'Excellent', 18913.04, @StartDateGem),
    ('Vietnam', 1.33, 'E', 'VVS1', 'Excellent', 18086.96, @StartDateGem),
    ('Italy', 1.33, 'E', 'VVS2', 'Excellent', 17555.65, @StartDateGem),
    ('South Africa', 1.33, 'E', 'VS1', 'Excellent', 17478.26, @StartDateGem),
    ('South Africa', 1.33, 'E', 'VS2', 'Excellent', 17391.30, @StartDateGem),
    
	('South Africa', 1.33, 'F', 'IF', 'Excellent', 18261.74, @StartDateGem),
    ('Vietnam', 1.33, 'F', 'VVS1', 'Excellent', 17826.09, @StartDateGem),
    ('South Africa', 1.33, 'F', 'VVS2', 'Excellent', 17511.30, @StartDateGem),
    ('South Africa', 1.33, 'F', 'VS1', 'Excellent', 17408.70, @StartDateGem),
    ('Vietnam', 1.33, 'F', 'VS2', 'Excellent', 17078.26, @StartDateGem),
    
	('Italy', 1.33, 'J', 'IF', 'Excellent', 8643.48, @StartDateGem),
    ('South Africa', 1.33, 'J', 'VVS1', 'Excellent', 8521.74, @StartDateGem),
    ('Italy', 1.33, 'J', 'VVS2', 'Excellent', 7500, @StartDateGem),
    ('Vietnam', 1.33, 'J', 'VS1', 'Excellent', 7304.35, @StartDateGem),
    ('Australia', 1.33, 'J', 'VS2', 'Excellent', 7217.39, @StartDateGem),
    
	--9mm
	('Vietnam', 2.75, 'D', 'IF', 'Excellent', 206956.52, @StartDateGem),
    ('Australia', 2.75, 'D', 'VVS1', 'Excellent', 121739.13, @StartDateGem),
    ('Australia', 2.75, 'D', 'VVS2', 'Excellent', 117391.30, @StartDateGem),
    ('Australia', 2.75, 'D', 'VS1', 'Excellent', 95652.17, @StartDateGem),
    ('Australia', 2.75, 'D', 'VS2', 'Excellent', 82608.70, @StartDateGem),
    
	('Australia', 2.75, 'E', 'IF', 'Excellent', 125217.39, @StartDateGem),
    ('Vietnam', 2.75, 'E', 'VVS1', 'Excellent', 116521.74, @StartDateGem),
    ('Australia', 2.75, 'E', 'VVS2', 'Excellent', 103478.26, @StartDateGem),
    ('Vietnam', 2.75, 'E', 'VS1', 'Excellent', 86086.96, @StartDateGem),
    ('America', 2.75, 'E', 'VS2', 'Excellent', 78260.87, @StartDateGem),
    
	('America', 2.75, 'F', 'IF', 'Excellent', 116956.52, @StartDateGem),
    ('Vietnam', 2.75, 'F', 'VVS1', 'Excellent', 104347.83, @StartDateGem),
    ('Vietnam', 2.75, 'F', 'VVS2', 'Excellent', 95652.17, @StartDateGem),
    ('Vietnam', 2.75, 'F', 'VS1', 'Excellent', 82608.70, @StartDateGem),
    ('Vietnam', 2.75, 'F', 'VS2', 'Excellent', 69565.22, @StartDateGem),
    
	('Vietnam', 2.75, 'J', 'IF', 'Excellent', 22652.17, @StartDateGem),
    ('America', 2.75, 'J', 'VVS1', 'Excellent', 21900, @StartDateGem),
    ('Vietnam', 2.75, 'J', 'VVS2', 'Excellent', 21666.67, @StartDateGem),
    ('America', 2.75, 'J', 'VS1', 'Excellent', 20410.87, @StartDateGem),
    ('Vietnam', 2.75, 'J', 'VS2', 'Excellent', 19043.48, @StartDateGem);*/

    SET @StartDateGem = DATEADD(day, 1, @StartDateGem);
END;

SET IDENTITY_INSERT [Material] OFF
SET IDENTITY_INSERT [Diamond] ON;

-- Insert data into the [Diamond] table
INSERT INTO [dbo].[Diamond] ([Dia_Id], [Dia_Code], [Dia_Name], [Origin], [Carat_Weight], [Color], [Clarity], [Cut], [Proportions], [Polish], [Symmetry], [Fluorescence], [Status], [Q_Price], [O_Price], [Img_Url])
VALUES
-- Gemstones with D Color
(1, 'DIA001', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.17, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=b0c525b0-1f57-4bfd-a9be-d4ad5bfb300d'),
(2, 'DIA002', 'Round Brilliant Cut Diamond - America', 'America', 0.17, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=5fdd9030-1600-46f2-9642-3710806c131f'),
(3, 'DIA003', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.17, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=a05edcef-6e3b-4e6c-a487-0e9164895fa9'),
(4, 'DIA004', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.17, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=cbac271e-76e7-4d3d-ae9e-9383e9a9b874'),
(5, 'DIA005', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.17, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=41823da8-ca9f-4507-baf7-75729beded75'),
 
-- Gemstones with E Color
(6, 'DIA006', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.17, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2%20(1).jpg?alt=media&token=2acf8ac0-8299-42be-8c45-2c8b88ba50b5'),
(7, 'DIA007', 'Round Brilliant Cut Diamond - America', 'America', 0.17, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(4).jpg?alt=media&token=a60a7f36-76ba-4d3b-aee2-c1388db933f8'),
(8, 'DIA008', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.17, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(3).jpg?alt=media&token=5abf0b4b-7fbd-47a2-ad80-cbdb0675b614'),
(9, 'DIA009', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.17, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(2).jpg?alt=media&token=fcf5b459-37e5-438a-b8d9-ac10648cdb0b'),
(10, 'DIA010', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.17, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(2).jpg?alt=media&token=2fa766eb-5963-44c9-8022-b9bb7e4d4fb3'),

-- Gemstones with F Color
(11, 'DIA011', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.17, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=1c8f4268-c8d1-43ff-9be6-4732bdec7bde'),
(12, 'DIA012', 'Round Brilliant Cut Diamond - America', 'America', 0.17, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=87042c63-0701-49e9-b7c4-60ec0437f150'),
(13, 'DIA013', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.17, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=cb6cacce-4d3e-4531-905b-6d2d36c58995'),
(14, 'DIA014', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.17, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=95349540-6f34-4d02-b934-466e1d14cf47'),
(15, 'DIA015', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.17, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=ef69675e-35c7-463f-bceb-d70e96063993'),

-- Gemstones with J Color
(16, 'DIA016', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.17, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=2d1fac7d-419a-479e-b429-a5e598ead86b'),
(17, 'DIA017', 'Round Brilliant Cut Diamond - America', 'America', 0.17, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=b6cf71d6-3292-4dec-9f0d-d4e6cb84752c'),
(18, 'DIA018', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.17, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=35a9e414-0f63-4a86-aed7-8488ec8e22d7'),
(19, 'DIA019', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.17, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=95210446-0cac-412c-b7f1-d1c33152b79b'),
(20, 'DIA020', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.17, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=5c853e49-3426-4c6b-9174-cbec99f5078f');

/*
-- Gemstones with D Color
(21, 'DIA021', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.34, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=b0c525b0-1f57-4bfd-a9be-d4ad5bfb300d'),
(22, 'DIA022', 'Round Brilliant Cut Diamond - America', 'America', 0.34, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=5fdd9030-1600-46f2-9642-3710806c131f'),
(23, 'DIA023', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.34, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=a05edcef-6e3b-4e6c-a487-0e9164895fa9'),
(24, 'DIA024', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.34, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=41823da8-ca9f-4507-baf7-75729beded75'),
(25, 'DIA025', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.34, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=cbac271e-76e7-4d3d-ae9e-9383e9a9b874'),

-- Gemstones with E Color
(26, 'DIA026', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.34, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL,'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(2).jpg?alt=media&token=2fa766eb-5963-44c9-8022-b9bb7e4d4fb3'),
(27, 'DIA027', 'Round Brilliant Cut Diamond - America', 'America', 0.34, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL,'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(2).jpg?alt=media&token=fcf5b459-37e5-438a-b8d9-ac10648cdb0b'),
(28, 'DIA028', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.34, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL,'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(3).jpg?alt=media&token=5abf0b4b-7fbd-47a2-ad80-cbdb0675b614'),
(29, 'DIA029', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.34, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL,'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(4).jpg?alt=media&token=a60a7f36-76ba-4d3b-aee2-c1388db933f8'),
(30, 'DIA030', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.34, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL,'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2%20(1).jpg?alt=media&token=2acf8ac0-8299-42be-8c45-2c8b88ba50b5'),

-- Gemstones with F Color
(31, 'DIA031', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.34, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=1c8f4268-c8d1-43ff-9be6-4732bdec7bde'),
(32, 'DIA032', 'Round Brilliant Cut Diamond - America', 'America', 0.34, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=87042c63-0701-49e9-b7c4-60ec0437f150'),
(33, 'DIA033', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.34, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=cb6cacce-4d3e-4531-905b-6d2d36c58995'),
(34, 'DIA034', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.34, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=95349540-6f34-4d02-b934-466e1d14cf47'),
(35, 'DIA015', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.34, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=ef69675e-35c7-463f-bceb-d70e96063993'),

-- Gemstones with J Color
(36, 'DIA036', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.34, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=5c853e49-3426-4c6b-9174-cbec99f5078f'),
(37, 'DIA037', 'Round Brilliant Cut Diamond - America', 'America', 0.34, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=95210446-0cac-412c-b7f1-d1c33152b79b'),
(38, 'DIA038', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.34, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=35a9e414-0f63-4a86-aed7-8488ec8e22d7'),
(39, 'DIA039', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.34, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=b6cf71d6-3292-4dec-9f0d-d4e6cb84752c'),
(40, 'DIA040', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.34, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=2d1fac7d-419a-479e-b429-a5e598ead86b'),

-- Gemstones with D Color
(41, 'DIA041', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.5, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=b0c525b0-1f57-4bfd-a9be-d4ad5bfb300d'),
(42, 'DIA042', 'Round Brilliant Cut Diamond - America', 'America', 0.5, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=5fdd9030-1600-46f2-9642-3710806c131f'),
(43, 'DIA043', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.5, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=a05edcef-6e3b-4e6c-a487-0e9164895fa9'),
(44, 'DIA044', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.5, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=cbac271e-76e7-4d3d-ae9e-9383e9a9b874'),
(45, 'DIA045', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.5, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=41823da8-ca9f-4507-baf7-75729beded75'),

-- Gemstones with E Color
(46, 'DIA046', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.5, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2%20(1).jpg?alt=media&token=2acf8ac0-8299-42be-8c45-2c8b88ba50b5'),
(47, 'DIA047', 'Round Brilliant Cut Diamond - America', 'America', 0.5, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(2).jpg?alt=media&token=fcf5b459-37e5-438a-b8d9-ac10648cdb0b'),
(48, 'DIA048', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.5, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(3).jpg?alt=media&token=5abf0b4b-7fbd-47a2-ad80-cbdb0675b614'),
(49, 'DIA049', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.5, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2%20(1).jpg?alt=media&token=2acf8ac0-8299-42be-8c45-2c8b88ba50b5'),
(50, 'DIA050', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.5, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(4).jpg?alt=media&token=a60a7f36-76ba-4d3b-aee2-c1388db933f8'),

-- Gemstones with F Color
(51, 'DIA051', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.5, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=cb6cacce-4d3e-4531-905b-6d2d36c58995'),
(52, 'DIA052', 'Round Brilliant Cut Diamond - America', 'America', 0.5, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=1c8f4268-c8d1-43ff-9be6-4732bdec7bde'),
(53, 'DIA053', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.5, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=cb6cacce-4d3e-4531-905b-6d2d36c58995'),
(54, 'DIA054', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.5, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=95349540-6f34-4d02-b934-466e1d14cf47'),
(55, 'DIA055', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.5, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL,'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=ef69675e-35c7-463f-bceb-d70e96063993'),

-- Gemstones with J Color
(56, 'DIA056', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.5, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL,'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=5c853e49-3426-4c6b-9174-cbec99f5078f'),
(57, 'DIA057', 'Round Brilliant Cut Diamond - America', 'America', 0.5, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=95210446-0cac-412c-b7f1-d1c33152b79b'),
(58, 'DIA058', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.5, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=35a9e414-0f63-4a86-aed7-8488ec8e22d7'),
(59, 'DIA059', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.5, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=b6cf71d6-3292-4dec-9f0d-d4e6cb84752c'),
(60, 'DIA060', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.5, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=2d1fac7d-419a-479e-b429-a5e598ead86b'),

-- Gemstones with D Color
(61, 'DIA061', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=b0c525b0-1f57-4bfd-a9be-d4ad5bfb300d'),
(62, 'DIA062', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=5fdd9030-1600-46f2-9642-3710806c131f'),
(63, 'DIA063', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=a05edcef-6e3b-4e6c-a487-0e9164895fa9'),
(64, 'DIA064', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=41823da8-ca9f-4507-baf7-75729beded75'),
(65, 'DIA065', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=cbac271e-76e7-4d3d-ae9e-9383e9a9b874'),

-- Gemstones with E Color
(66, 'DIA066', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2%20(1).jpg?alt=media&token=2acf8ac0-8299-42be-8c45-2c8b88ba50b5'),
(67, 'DIA067', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(2).jpg?alt=media&token=fcf5b459-37e5-438a-b8d9-ac10648cdb0b'),
(68, 'DIA068', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(3).jpg?alt=media&token=5abf0b4b-7fbd-47a2-ad80-cbdb0675b614'),
(69, 'DIA069', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(4).jpg?alt=media&token=a60a7f36-76ba-4d3b-aee2-c1388db933f8'),
(70, 'DIA070', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(2).jpg?alt=media&token=2fa766eb-5963-44c9-8022-b9bb7e4d4fb3'),

-- Gemstones with F Color
(71, 'DIA071', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=cb6cacce-4d3e-4531-905b-6d2d36c58995'),
(72, 'DIA072', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=87042c63-0701-49e9-b7c4-60ec0437f150'),
(73, 'DIA073', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=1c8f4268-c8d1-43ff-9be6-4732bdec7bde'),
(74, 'DIA074', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=95349540-6f34-4d02-b934-466e1d14cf47'),
(75, 'DIA075', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=ef69675e-35c7-463f-bceb-d70e96063993'),

-- Gemstones with J Color
(76, 'DIA076', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=2d1fac7d-419a-479e-b429-a5e598ead86b'),
(77, 'DIA077', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=2d1fac7d-419a-479e-b429-a5e598ead86b'),
(78, 'DIA078', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=35a9e414-0f63-4a86-aed7-8488ec8e22d7'),
(79, 'DIA079', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=95210446-0cac-412c-b7f1-d1c33152b79b'),
(80, 'DIA080', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=5c853e49-3426-4c6b-9174-cbec99f5078f'),

-- Gemstones with D Color
(81, 'DIA081', 'Round Brilliant Cut Diamond - America', 'America', 0.6, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=b0c525b0-1f57-4bfd-a9be-d4ad5bfb300d'),
(82, 'DIA082', 'Round Brilliant Cut Diamond - America', 'America', 0.6, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=5fdd9030-1600-46f2-9642-3710806c131f'),
(83, 'DIA083', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.6, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=a05edcef-6e3b-4e6c-a487-0e9164895fa9'),
(84, 'DIA084', 'Round Brilliant Cut Diamond - America', 'America', 0.6, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=41823da8-ca9f-4507-baf7-75729beded75'),
(85, 'DIA085', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.6, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=cbac271e-76e7-4d3d-ae9e-9383e9a9b874'),

-- Gemstones with E Color
(86, 'DIA086', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.6, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2%20(1).jpg?alt=media&token=2acf8ac0-8299-42be-8c45-2c8b88ba50b5'),
(87, 'DIA087', 'Round Brilliant Cut Diamond - America', 'America', 0.6, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(3).jpg?alt=media&token=5abf0b4b-7fbd-47a2-ad80-cbdb0675b614'),
(88, 'DIA088', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.6, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(4).jpg?alt=media&token=a60a7f36-76ba-4d3b-aee2-c1388db933f8'),
(89, 'DIA089', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.6, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(2).jpg?alt=media&token=fcf5b459-37e5-438a-b8d9-ac10648cdb0b'),
(90, 'DIA090', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.6, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(2).jpg?alt=media&token=2fa766eb-5963-44c9-8022-b9bb7e4d4fb3'),

-- Gemstones with F Color
(91, 'DIA091', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.6, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=1c8f4268-c8d1-43ff-9be6-4732bdec7bde'),
(92, 'DIA092', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.6, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=87042c63-0701-49e9-b7c4-60ec0437f150'),
(93, 'DIA093', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.6, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=cb6cacce-4d3e-4531-905b-6d2d36c58995'),
(94, 'DIA094', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.6, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=95349540-6f34-4d02-b934-466e1d14cf47'),
(95, 'DIA095', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.6, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=ef69675e-35c7-463f-bceb-d70e96063993'),

-- Gemstones with J Color
(96, 'DIA096', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.6, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=b6cf71d6-3292-4dec-9f0d-d4e6cb84752c'),
(97, 'DIA097', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.6, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=b6cf71d6-3292-4dec-9f0d-d4e6cb84752c'),
(98, 'DIA098', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.6, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=35a9e414-0f63-4a86-aed7-8488ec8e22d7'),
(99, 'DIA099', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.6, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=95210446-0cac-412c-b7f1-d1c33152b79b'),
(100, 'DIA100', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.6, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=5c853e49-3426-4c6b-9174-cbec99f5078f'),

-- Gemstones with D Color
(101, 'DIA101', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.84, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=b0c525b0-1f57-4bfd-a9be-d4ad5bfb300d'),
(102, 'DIA102', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.84, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=5fdd9030-1600-46f2-9642-3710806c131f'),
(103, 'DIA103', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.84, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=a05edcef-6e3b-4e6c-a487-0e9164895fa9'),
(104, 'DIA104', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.84, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=cbac271e-76e7-4d3d-ae9e-9383e9a9b874'),
(105, 'DIA105', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.84, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=41823da8-ca9f-4507-baf7-75729beded75'),

-- Gemstones with E Color
(106, 'DIA106', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.84, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2%20(1).jpg?alt=media&token=2acf8ac0-8299-42be-8c45-2c8b88ba50b5'),
(107, 'DIA107', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.84, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(4).jpg?alt=media&token=a60a7f36-76ba-4d3b-aee2-c1388db933f8'),
(108, 'DIA108', 'Round Brilliant Cut Diamond - America', 'America', 0.84, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(3).jpg?alt=media&token=5abf0b4b-7fbd-47a2-ad80-cbdb0675b614'),
(109, 'DIA109', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.84, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(2).jpg?alt=media&token=fcf5b459-37e5-438a-b8d9-ac10648cdb0b'),
(110, 'DIA110', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.84, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(2).jpg?alt=media&token=2fa766eb-5963-44c9-8022-b9bb7e4d4fb3'),

-- Gemstones with F Color
(111, 'DIA111', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.84, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=1c8f4268-c8d1-43ff-9be6-4732bdec7bde'),
(112, 'DIA112', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.84, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=87042c63-0701-49e9-b7c4-60ec0437f150'),
(113, 'DIA113', 'Round Brilliant Cut Diamond - America', 'America', 0.84, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=ef69675e-35c7-463f-bceb-d70e96063993'),
(114, 'DIA114', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.84, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=95349540-6f34-4d02-b934-466e1d14cf47'),
(115, 'DIA115', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.84, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=cb6cacce-4d3e-4531-905b-6d2d36c58995'),

-- Gemstones with J Color
(116, 'DIA116', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.84, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=2d1fac7d-419a-479e-b429-a5e598ead86b'),
(117, 'DIA117', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.84, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=5c853e49-3426-4c6b-9174-cbec99f5078f'),
(118, 'DIA118', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.84, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=35a9e414-0f63-4a86-aed7-8488ec8e22d7'),
(119, 'DIA119', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.84, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=95210446-0cac-412c-b7f1-d1c33152b79b'),
(120, 'DIA120', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.84, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=b6cf71d6-3292-4dec-9f0d-d4e6cb84752c'),

-- Gemstones with D Color
(121, 'DIA121', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.92, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=a05edcef-6e3b-4e6c-a487-0e9164895fa9'),
(122, 'DIA122', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.92, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=5fdd9030-1600-46f2-9642-3710806c131f'),
(123, 'DIA123', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.92, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=cbac271e-76e7-4d3d-ae9e-9383e9a9b874'),
(124, 'DIA124', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.92, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=41823da8-ca9f-4507-baf7-75729beded75'),
(125, 'DIA125', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.92, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=b0c525b0-1f57-4bfd-a9be-d4ad5bfb300d'),

-- Gemstones with E Color
(126, 'DIA126', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.92, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(3).jpg?alt=media&token=5abf0b4b-7fbd-47a2-ad80-cbdb0675b614'),
(127, 'DIA127', 'Round Brilliant Cut Diamond - America', 'America', 0.92, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(4).jpg?alt=media&token=a60a7f36-76ba-4d3b-aee2-c1388db933f8'),
(128, 'DIA128', 'Round Brilliant Cut Diamond - America', 'America', 0.92, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(2).jpg?alt=media&token=fcf5b459-37e5-438a-b8d9-ac10648cdb0b'),
(129, 'DIA129', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.92, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2%20(1).jpg?alt=media&token=2acf8ac0-8299-42be-8c45-2c8b88ba50b5'),
(130, 'DIA130', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.92, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(2).jpg?alt=media&token=2fa766eb-5963-44c9-8022-b9bb7e4d4fb3'),

-- Gemstones with F Color
(131, 'DIA131', 'Round Brilliant Cut Diamond - America', 'America', 0.92, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=1c8f4268-c8d1-43ff-9be6-4732bdec7bde'),
(132, 'DIA132', 'Round Brilliant Cut Diamond - America', 'America', 0.92, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=87042c63-0701-49e9-b7c4-60ec0437f150'),
(133, 'DIA133', 'Round Brilliant Cut Diamond - America', 'America', 0.92, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=cb6cacce-4d3e-4531-905b-6d2d36c58995'),
(134, 'DIA134', 'Round Brilliant Cut Diamond - America', 'America', 0.92, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=95349540-6f34-4d02-b934-466e1d14cf47'),
(135, 'DIA135', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.92, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=ef69675e-35c7-463f-bceb-d70e96063993'),

-- Gemstones with J Color
(136, 'DIA136', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.92, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=2d1fac7d-419a-479e-b429-a5e598ead86b'),
(137, 'DIA137', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.92, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=b6cf71d6-3292-4dec-9f0d-d4e6cb84752c'),
(138, 'DIA138', 'Round Brilliant Cut Diamond - America', 'America', 0.92, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=35a9e414-0f63-4a86-aed7-8488ec8e22d7'),
(139, 'DIA139', 'Round Brilliant Cut Diamond - America', 'America', 0.92, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=95210446-0cac-412c-b7f1-d1c33152b79b'),
(140, 'DIA140', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.92, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=5c853e49-3426-4c6b-9174-cbec99f5078f'),

-- Gemstones with D Color
(141, 'DIA141', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.25, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=b0c525b0-1f57-4bfd-a9be-d4ad5bfb300d'),
(142, 'DIA142', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.25, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=5fdd9030-1600-46f2-9642-3710806c131f'),
(143, 'DIA143', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.25, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=41823da8-ca9f-4507-baf7-75729beded75'),
(144, 'DIA144', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.25, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=cbac271e-76e7-4d3d-ae9e-9383e9a9b874'),
(145, 'DIA145', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.25, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=a05edcef-6e3b-4e6c-a487-0e9164895fa9'),

-- Gemstones with E Color
(146, 'DIA146', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.25, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2%20(1).jpg?alt=media&token=2acf8ac0-8299-42be-8c45-2c8b88ba50b5'),
(147, 'DIA147', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.25, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(2).jpg?alt=media&token=2fa766eb-5963-44c9-8022-b9bb7e4d4fb3'),
(148, 'DIA148', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.25, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(4).jpg?alt=media&token=a60a7f36-76ba-4d3b-aee2-c1388db933f8'),
(149, 'DIA149', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.25, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(3).jpg?alt=media&token=5abf0b4b-7fbd-47a2-ad80-cbdb0675b614'),
(150, 'DIA150', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.25, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(2).jpg?alt=media&token=fcf5b459-37e5-438a-b8d9-ac10648cdb0b'),

-- Gemstones with F Color
(151, 'DIA151', 'Round Brilliant Cut Diamond - Italy', 'Italy', 1.25, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=1c8f4268-c8d1-43ff-9be6-4732bdec7bde'),
(152, 'DIA152', 'Round Brilliant Cut Diamond - Italy', 'Italy', 1.25, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=ef69675e-35c7-463f-bceb-d70e96063993'),
(153, 'DIA153', 'Round Brilliant Cut Diamond - Italy', 'Italy', 1.25, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=95349540-6f34-4d02-b934-466e1d14cf47'),
(154, 'DIA154', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.25, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=cb6cacce-4d3e-4531-905b-6d2d36c58995'),
(155, 'DIA155', 'Round Brilliant Cut Diamond - Italy', 'Italy', 1.25, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=87042c63-0701-49e9-b7c4-60ec0437f150'),

-- Gemstones with J Color
(156, 'DIA156', 'Round Brilliant Cut Diamond - America', 'America', 1.25, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=5c853e49-3426-4c6b-9174-cbec99f5078f'),
(157, 'DIA157', 'Round Brilliant Cut Diamond - America', 'America', 1.25, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=95210446-0cac-412c-b7f1-d1c33152b79b'),
(158, 'DIA158', 'Round Brilliant Cut Diamond - America', 'America', 1.25, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=35a9e414-0f63-4a86-aed7-8488ec8e22d7'),
(159, 'DIA159', 'Round Brilliant Cut Diamond - America', 'America', 1.25, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=b6cf71d6-3292-4dec-9f0d-d4e6cb84752c'),
(160, 'DIA160', 'Round Brilliant Cut Diamond - America', 'America', 1.25, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=2d1fac7d-419a-479e-b429-a5e598ead86b'),

-- Gemstones with D Color
(161, 'DIA161', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.33, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=b0c525b0-1f57-4bfd-a9be-d4ad5bfb300d'),
(162, 'DIA162', 'Round Brilliant Cut Diamond - Australia', 'Australia', 1.33, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=5fdd9030-1600-46f2-9642-3710806c131f'),
(163, 'DIA163', 'Round Brilliant Cut Diamond - America', 'America', 1.33, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=a05edcef-6e3b-4e6c-a487-0e9164895fa9'),
(164, 'DIA164', 'Round Brilliant Cut Diamond - Australia', 'Australia', 1.33, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=cbac271e-76e7-4d3d-ae9e-9383e9a9b874'),
(165, 'DIA165', 'Round Brilliant Cut Diamond - Italy', 'Italy', 1.33, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=41823da8-ca9f-4507-baf7-75729beded75'),

-- Gemstones with E Color
(166, 'DIA166', 'Round Brilliant Cut Diamond - Italy', 'Italy', 1.33, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2%20(1).jpg?alt=media&token=2acf8ac0-8299-42be-8c45-2c8b88ba50b5'),
(167, 'DIA167', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.33, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(4).jpg?alt=media&token=a60a7f36-76ba-4d3b-aee2-c1388db933f8'),
(168, 'DIA168', 'Round Brilliant Cut Diamond - Italy', 'Italy', 1.33, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(3).jpg?alt=media&token=5abf0b4b-7fbd-47a2-ad80-cbdb0675b614'),
(169, 'DIA169', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.33, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(2).jpg?alt=media&token=fcf5b459-37e5-438a-b8d9-ac10648cdb0b'),
(170, 'DIA170', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.33, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(2).jpg?alt=media&token=2fa766eb-5963-44c9-8022-b9bb7e4d4fb3'),

-- Gemstones with F Color
(171, 'DIA171', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.33, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=1c8f4268-c8d1-43ff-9be6-4732bdec7bde'),
(172, 'DIA172', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.33, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=cb6cacce-4d3e-4531-905b-6d2d36c58995'),
(173, 'DIA173', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.33, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=cb6cacce-4d3e-4531-905b-6d2d36c58995'),
(174, 'DIA174', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.33, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=95349540-6f34-4d02-b934-466e1d14cf47'),
(175, 'DIA175', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.33, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=ef69675e-35c7-463f-bceb-d70e96063993'),

-- Gemstones with J Color
(176, 'DIA176', 'Round Brilliant Cut Diamond - Italy', 'Italy', 1.33, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=5c853e49-3426-4c6b-9174-cbec99f5078f'),
(177, 'DIA177', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.33, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=95210446-0cac-412c-b7f1-d1c33152b79b'),
(178, 'DIA178', 'Round Brilliant Cut Diamond - Italy', 'Italy', 1.33, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=35a9e414-0f63-4a86-aed7-8488ec8e22d7'),
(179, 'DIA179', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.33, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=b6cf71d6-3292-4dec-9f0d-d4e6cb84752c'),
(180, 'DIA180', 'Round Brilliant Cut Diamond - Australia', 'Australia', 1.33, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=2d1fac7d-419a-479e-b429-a5e598ead86b'),

-- Gemstones with D Color
(181, 'DIA181', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 2.75, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=b0c525b0-1f57-4bfd-a9be-d4ad5bfb300d'),
(182, 'DIA182', 'Round Brilliant Cut Diamond - Australia', 'Australia', 2.75, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=41823da8-ca9f-4507-baf7-75729beded75'),
(183, 'DIA183', 'Round Brilliant Cut Diamond - Australia', 'Australia', 2.75, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=cbac271e-76e7-4d3d-ae9e-9383e9a9b874'),
(184, 'DIA184', 'Round Brilliant Cut Diamond - Australia', 'Australia', 2.75, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=5fdd9030-1600-46f2-9642-3710806c131f'),
(185, 'DIA185', 'Round Brilliant Cut Diamond - Australia', 'Australia', 2.75, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FD-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=a05edcef-6e3b-4e6c-a487-0e9164895fa9'),

-- Gemstones with E Color
(186, 'DIA186', 'Round Brilliant Cut Diamond - Australia', 'Australia', 2.75, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(2).jpg?alt=media&token=2fa766eb-5963-44c9-8022-b9bb7e4d4fb3'),
(187, 'DIA187', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 2.75, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(2).jpg?alt=media&token=fcf5b459-37e5-438a-b8d9-ac10648cdb0b'),
(188, 'DIA188', 'Round Brilliant Cut Diamond - Australia', 'Australia', 2.75, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(3).jpg?alt=media&token=5abf0b4b-7fbd-47a2-ad80-cbdb0675b614'),
(189, 'DIA189', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 2.75, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2%20(1).jpg?alt=media&token=2acf8ac0-8299-42be-8c45-2c8b88ba50b5'),
(190, 'DIA190', 'Round Brilliant Cupt Diamond - America', 'America', 2.75, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FE-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(4).jpg?alt=media&token=a60a7f36-76ba-4d3b-aee2-c1388db933f8'),

-- Gemstones with F Color
(191, 'DIA191', 'Round Brilliant Cut Diamond - America', 'America', 2.75, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=ef69675e-35c7-463f-bceb-d70e96063993'),
(192, 'DIA192', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 2.75, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=95349540-6f34-4d02-b934-466e1d14cf47'),
(193, 'DIA193', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 2.75, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1%20(1).jpg?alt=media&token=cb6cacce-4d3e-4531-905b-6d2d36c58995'),
(194, 'DIA194', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 2.75, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=87042c63-0701-49e9-b7c4-60ec0437f150'),
(195, 'DIA195', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 2.75, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FF-color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=1c8f4268-c8d1-43ff-9be6-4732bdec7bde'),

-- Gemstones with J Color
(196, 'DIA196', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 2.75, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_3.jpg?alt=media&token=5c853e49-3426-4c6b-9174-cbec99f5078f'),
(197, 'DIA197', 'Round Brilliant Cut Diamond - America', 'America', 2.75, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_2.jpg?alt=media&token=95210446-0cac-412c-b7f1-d1c33152b79b'),
(198, 'DIA198', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 2.75, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_1.jpg?alt=media&token=35a9e414-0f63-4a86-aed7-8488ec8e22d7'),
(199, 'DIA199', 'Round Brilliant Cut Diamond - America', 'America', 2.75, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0.jpg?alt=media&token=b6cf71d6-3292-4dec-9f0d-d4e6cb84752c'),
(200, 'DIA200', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.33, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 100, NULL, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Full_Product%2FDiamond%2FJ-Color%2FDefault_generate_for_me_a_picture_of_a_round_brilliant_cut_dia_0%20(1).jpg?alt=media&token=2d1fac7d-419a-479e-b429-a5e598ead86b');
*/

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



select * from Users
select * from Customer
select * from Employee
select * from Category
select * from Blogs
select * from Collections
select * from Material
select * from MaterialPriceList
select * from Gem_Price_List
select * from Diamond
select * from Product
select * from Production_Order
select * from Product_Design_Shell
select * from Product_Design

SELECT p.production_Order_Id FROM Production_Order p WHERE p.category_Id = 1