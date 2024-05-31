
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
	[RoleID] [int] NOT NULL,
	[Status] bit NOT NULL,
CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
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
	[EmployeeId] [nvarchar] (255) NOT NULL,
	[Full_Name] [nvarchar](255) NOT NULL,
	[User_Id] [int] REFERENCES [Users](User_Id),
CONSTRAINT [PK_Employee] PRIMARY KEY CLUSTERED 
(
	[EmployeeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


/* Table [Customer] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customer](
	[CustomerId] [nvarchar] (255) NOT NULL,
	[Full_Name] [nvarchar](255) NOT NULL,
	[Address] [nvarchar](max) NOT NULL,
	[PhoneNumber] [varchar](10) NOT NULL,
	[User_Id] [int] REFERENCES [Users](User_Id),
CONSTRAINT [PK_Customer] PRIMARY KEY CLUSTERED 
(
	[CustomerId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

drop table Customer
drop table Employee

/* Table [Category] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category](
	[CategoryId] [nvarchar] (255) NOT NULL,
	[CategoryName] [nvarchar](255) NOT NULL,
	[Status] [bit] 
CONSTRAINT [PK_Category] PRIMARY KEY CLUSTERED 
(
	[CategoryId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [Collection] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Collections](
	[CollectionId] int IDENTITY(1,1),
	[CollectionName] [nvarchar](255) NOT NULL,
	[Status] [bit] 
CONSTRAINT [PK_Collections] PRIMARY KEY CLUSTERED 
(
	[CollectionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [Blogs] */
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Blogs](
    [BlogId] [int] IDENTITY(1,1),
	[Title] varchar(100) NOT NULL,
	[Content] [nvarchar](max) NOT NULL,
	[Date] [date] NOT NULL,
	[EmployeeId] [nvarchar] (255) REFERENCES [Employee]([EmployeeId])
CONSTRAINT [PK_Blogs] PRIMARY KEY CLUSTERED 
(
	[BlogId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [Material] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Material](
	[MaterialId] int IDENTITY(1,1),
	[MaterialCode] [nvarchar] (255) NOT NULL,
	[MaterialName] [nvarchar](255) NOT NULL,
CONSTRAINT [PK_Material] PRIMARY KEY CLUSTERED 
(
	[MaterialId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [MaterialPriceList] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MaterialPriceList](
	[id] int IDENTITY(1,1),
	[Price] [float] NOT NULL,
	[EffDate] [date] NOT NULL,
	[MaterialId] int REFERENCES [Material]([MaterialId])
CONSTRAINT [PK_MaterialPriceList] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [ProductMaterial] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProductMaterial](
	ProductId int REFERENCES Product([ProductId]),
	MaterialId int REFERENCES Material([MaterialId]),
	MaterialWeight int,
CONSTRAINT [PK_ProductMaterial] PRIMARY KEY CLUSTERED 
(
	ProductId ASC, MaterialId ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


/* Table [GemPriceList] */
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GemPriceList] (
    [Id] [int] IDENTITY(1,1),
	[Origin] [nvarchar] (255) NOT NULL,
    [CaratWeight] [float] NOT NULL,
	[Color] [char](10) NOT NULL,
	[Clarity] [char](10) NOT NULL,
	[Cut] [nvarchar] (255) NOT NULL,
	[Price] [float] NOT NULL,
	[EffDate] [date] NOT NULL,
CONSTRAINT [PK_GemPriceList] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


/* Table [Gemstone] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Gemstone](
	[GemId] [int] IDENTITY(1,1),
	[GemCode] [nvarchar](255) NOT NULL,
	[GemName] [nvarchar](255) NOT NULL,

	[Origin] [nvarchar] (255) NOT NULL,
    [CaratWeight] [float] NOT NULL,
	[Color] [char](10) NOT NULL,
	[Clarity] [char](10) NOT NULL,
	[Cut] [nvarchar] (255) NOT NULL,

	[Proportions] [nvarchar](255) NOT NULL,
	[Polish] [nvarchar](255) NOT NULL,
	[Symmetry] [nvarchar](255) NOT NULL,
	[Fluorescence] [char](10) NOT NULL,
	[Status] [bit] NOT NULL, -- 1/Active | 0/Inactive (Used by some Pro)

	[ProductId] int REFERENCES Product([ProductId]),
CONSTRAINT [PK_Gemstone] PRIMARY KEY CLUSTERED 
(
	[GemId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


/* Table [Product] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product](
	[ProductId] [int] IDENTITY(1,1),
	[ProductCode] [nvarchar] (max) NOT NULL,
	[ProductName] [nvarchar](255) NOT NULL,
	[CategoryId] [nvarchar] (255) REFERENCES [Category](CategoryId),
	[CollectionId] int REFERENCES [Collections](CollectionId),
	[Description] [nvarchar](max),

	[Gender] [nvarchar](50) NOT NULL,
	[Size] [int] NOT NULL,

	[Status] bit NOT NULL,
	
CONSTRAINT [PK_Product] PRIMARY KEY CLUSTERED 
(
	[ProductId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [ProductionOrder] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProductionOrder](
	[ProductionOrderId] [int] IDENTITY(1,1), --R001, Q001, -O001
	[Date] [date] NOT NULL,

	[CustomerId] [nvarchar] (255) REFERENCES [Customer](CustomerId),
	[CategoryId] [nvarchar] (255) REFERENCES [Category]([CategoryId]),
	
	[MaterialName] [nvarchar](255),
	[MaterialColor] [nvarchar](100),
	[MaterialWeight] float,
	[MaterialId] int,

	[GemName] [nvarchar](255),
	[GemColor] [nvarchar](100),
	[GemWeight] float,
	[GemId] int,

	[ProductSize] [int],
	[Description] [nvarchar](max),

	[DiamondAmount] float,
	[MaterialAmount] float,
	[ProductionAmount] float,

	[SideMaterialCost] float,
	[SideGemCost] float,

	[TotalAmount] float,

	[SalesStaffName] [nvarchar](255),
	[DesignStaffName] [nvarchar](255),
	[ProductionStaffName] [nvarchar](255),

	[Status] [nvarchar](10),  --request, quote, order, deliver

	[ProductId] [int] REFERENCES Product([ProductId]), 
CONSTRAINT [PK_ProductionOrder] PRIMARY KEY CLUSTERED 
(
	[ProductionOrderId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO



/* Table [ProductDesign] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProductDesign](
	[PDId] [int] IDENTITY(1,1),
	[PDCode] [nvarchar] (max) NOT NULL,
	[PDName] [nvarchar](255) NOT NULL,
	[CategoryId] [nvarchar] (255) REFERENCES [Category](CategoryId),
	[CollectionId] int REFERENCES [Collections](CollectionId),
	[Description] [nvarchar](max),

	[Gender] [nvarchar](50) NOT NULL,
	[PDSize] [int] NOT NULL,

	[PDSId] int REFERENCES [ProductDesignShell]([PDSId]),
	[GemMinSize] float,
	[GemMaxSize] float,

	[Status] bit NOT NULL,
	
CONSTRAINT [PK_ProductDesign] PRIMARY KEY CLUSTERED 
(
	[PDId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


/* Table [ProductDesignShell] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProductDesignShell](
	[PDSId] int IDENTITY(1,1),
	[MaterialId] int,
	[Weight] int,
	
CONSTRAINT [PK_ProductDesignShell] PRIMARY KEY CLUSTERED 
(
	[PDSId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


/*================ INSERT DATA ================*/

-- Insert data into the Users table
INSERT INTO [dbo].[Users] (Email, Password, RoleID, Status)
VALUES 
-- 10 Customers
('customer1@example.com', 'Cust1#Secure', 1, 1),
('customer2@example.com', 'Cust2*Strong', 1, 1),
('customer3@example.com', 'Cust3@Safe!', 1, 0),
('customer4@example.com', 'Cust4%Tough', 1, 1),
('customer5@example.com', 'Cust5@Solid', 1, 1),
('customer6@example.com', 'Cust6&Hard!', 1, 0),
('customer7@example.com', 'Cust7!Secure', 1, 1),
('customer8@example.com', 'Cust8#Strong', 1, 1),
('customer9@example.com', 'Cust9*Safe!', 1, 0),
('customer10@example.com', 'Cust10%Tough', 1, 1),
-- 4 Admins
('admin1@example.com', 'Admin1#Secure', 2, 1),
('admin2@example.com', 'Admin2*Strong', 2, 1),
('admin3@example.com', 'Admin3@Safe!', 2, 1),
('admin4@example.com', 'Admin4%Tough', 2, 1),
-- 4 Managers
('manager1@example.com', 'Mgr1#Secure!', 3, 1),
('manager2@example.com', 'Mgr2*Strong', 3, 1),
('manager3@example.com', 'Mgr3@Safe!', 3, 1),
('manager4@example.com', 'Mgr4%Tough', 3, 1),
-- 4 Sales Staff
('sales1@example.com', 'Sales1#Good!', 4, 1),
('sales2@example.com', 'Sales2*Best', 4, 1),
('sales3@example.com', 'Sales3@Fine!', 4, 1),
('sales4@example.com', 'Sales4%Great', 4, 1),
-- 4 Design Staff
('design1@example.com', 'Design1#Art!', 5, 1),
('design2@example.com', 'Design2*Skill', 5, 1),
('design3@example.com', 'Design3@Work!', 5, 1),
('design4@example.com', 'Design4%Craft', 5, 1),
-- 4 Production Staff
('prod1@example.com', 'Prod1#Make!', 6, 1),
('prod2@example.com', 'Prod2*Build', 6, 1),
('prod3@example.com', 'Prod3@Form!', 6, 1),
('prod4@example.com', 'Prod4%Forge', 6, 1);

-- Insert data into the Employee table
INSERT INTO [dbo].[Employee] (EmployeeId, Full_Name, User_Id)
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
INSERT INTO [dbo].[Customer] (CustomerId, Full_Name, Address, PhoneNumber, User_Id)
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
('CUS010', 'David Evans', '707 Ash St', '5551110010', 10);

-- Insert data into Category table
INSERT INTO [dbo].[Category] (CategoryId, CategoryName, Status) VALUES
('1', 'ring', 1),
('2', 'necklace', 1),
('3', 'earrings', 1),
('4', 'bracelet', 1),
('5', 'cufflink', 1);

INSERT INTO [dbo].[Blogs] ([Name], [Content], [Date], [EmployeeId]) VALUES
('The Brilliance of Diamonds', 
 'Explore the mesmerizing brilliance of diamonds, discussing their formation, characteristics, and symbolism in various cultures. Highlight the unique features that make diamonds a timeless choice for jewelry.', 
 '2024-05-27', 'SA001'),

('Crafting Beauty: The Art of Gold Jewelry Making', 
 'Delve into the craftsmanship behind gold jewelry, showcasing the intricate techniques used by artisans to create stunning pieces. Discuss the versatility of gold and its significance in different jewelry designs.', 
 '2024-05-28', 'DE001'),

('Unveiling the Mystique of Precious Gemstones', 
 'Take your readers on a journey through the world of precious gemstones, including sapphires, rubies, and emeralds. Explore their origins, colors, and cultural meanings, offering insights into why these gems are treasured.', 
 '2024-05-29', 'SA001'),

('From Mine to Market: The Diamond Supply Chain', 
 'Shed light on the journey of diamonds from the mines to the market, discussing ethical sourcing practices, sustainability efforts, and the role of certification in ensuring transparency and integrity.', 
 '2024-05-30', 'PRO001'),

('The Language of Jewelry: Symbols and Meanings', 
 'Explore the symbolic meanings behind common jewelry motifs such as hearts, infinity symbols, and flowers. Discuss how these symbols resonate with individuals and convey messages of love, hope, and empowerment.', 
 '2024-06-01', 'SA002');

 -- Insert collections into the Collection table
INSERT INTO [dbo].[Collections] ([CollectionName], [Status]) VALUES
('Summer Collection', 1),
('Spring Collection', 1),
('Winter Collection', 1),
('Autumn Collection', 1),
('Wedding Collection', 1),
('Wildcard Collection', 1),
('Galaxy Collection', 1),
('Mountain Collection', 1),
('Ocean Collection', 1),
('Disney Collection', 1);

-- Insert data into Material table
INSERT INTO [dbo].[Material] ([MaterialCode], [MaterialName])
VALUES
    ('Gold8K', 'Gold 8K'),
    ('Gold9K', 'Gold 9K'),
    ('Gold10K', 'Gold 10K'),
    ('Gold14K', 'Gold 14K'),
    ('Gold15.6K', 'Gold 15.6K'),
    ('Gold16.3K', 'Gold 16.3K'),
    ('Gold18K', 'Gold 18K'),
    ('Gold22K', 'Gold 22K'),
    ('SJC', 'SJC Gold Piece'),
    ('PNJ24K', 'PNJ Ring Gold 24K');

-- Insert data into MaterialPriceList table
DECLARE @StartDate DATE = '2024-01-01'; -- Start date for generating data
DECLARE @EndDate DATE = '2024-12-31';   -- End date for generating data

WHILE @StartDate <= @EndDate
BEGIN
    -- Gold prices for each type of gold on the current date
    INSERT INTO [dbo].[MaterialPriceList] ([Price], [EffDate], [MaterialId])
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
DECLARE @StartDateGem DATE = '2024-05-26'; -- Start date for generating data
DECLARE @EndDateGem DATE = '2024-5-26';   -- End date for generating data
-- Loop through dates to generate data for each day
WHILE @StartDateGem <= @EndDateGem
BEGIN
    -- Generate data for GemPriceList table (Diamond prices for different dates)
INSERT INTO [dbo].[GemPriceList] ([Origin], [CaratWeight], [Color], [Clarity], [Cut], [Price], [EffDate])
VALUES
		-- 3.6mm
		-- D Color
	('Vietnam', 0.17, 'D', 'IF', 'Excellent', 424.01, @StartDateGem),
	('America', 0.17, 'D', 'VVS1', 'Excellent', 384.75, @StartDateGem),
	('Italy', 0.17, 'D', 'VVS2', 'Excellent', 345.74, @StartDateGem),
	('Australia', 0.17, 'D', 'VS1', 'Excellent', 322.17, @StartDateGem),
	('South Africa', 0.17, 'D', 'VS2', 'Excellent', 275.02, @StartDateGem),
		-- E Color
	('Vietnam', 0.17, 'E', 'IF', 'Excellent', 412.23, @StartDateGem),
	('America', 0.17, 'E', 'VVS1', 'Excellent', 361.19, @StartDateGem),
	('Italy', 0.17, 'E', 'VVS2', 'Excellent', 314.31, @StartDateGem),
	('Australia', 0.17, 'E', 'VS1', 'Excellent', 282.88, @StartDateGem),
	('South Africa', 0.17, 'E', 'VS2', 'Excellent', 196.44, @StartDateGem),
		-- F Color
	('Vietnam', 0.17, 'F', 'IF', 'Excellent', 417.02, @StartDateGem),
	('America', 0.17, 'F', 'VVS1', 'Excellent', 387.23, @StartDateGem),
	('Italy', 0.17, 'F', 'VVS2', 'Excellent', 300.928, @StartDateGem),
	('Australia', 0.17, 'F', 'VS1', 'Excellent', 255.32, @StartDateGem),
	('South Africa', 0.17, 'F', 'VS2', 'Excellent', 165.96, @StartDateGem),
		-- J Color
	('Vietnam', 0.17, 'J', 'IF', 'Excellent', 340.43 , @StartDateGem),
	('America', 0.17, 'J', 'VVS1', 'Excellent', 331.91, @StartDateGem),
	('Italy', 0.17, 'J', 'VVS2', 'Excellent', 276.60, @StartDateGem),
	('Australia', 0.17, 'J', 'VS1', 'Excellent', 263.83, @StartDateGem),
	('South Africa', 0.17, 'J', 'VS2', 'Excellent', 225.53, @StartDateGem),
		
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
    ('Vietnam', 2.75, 'J', 'VS2', 'Excellent', 19043.48, @StartDateGem);

    SET @StartDateGem = DATEADD(day, 1, @StartDateGem);
END;

-- Insert data into Gemstone table (Diamonds)
INSERT INTO [dbo].[Gemstone] ([GemPriceListId], [GemCode], [GemName], [Origin], [Proportions], [Polish], [Symmetry], [Fluorescence], [Status])
VALUES
    (1, 'DIA001', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 'Excellent', 'Excellent', 'Excellent', 'None', 1),
    (2, 'DIA002', 'Round Brilliant Cut Diamond - Russia', 'Russia', 'Very Good', 'Very Good', 'Very Good', 'Medium', 1),
    (3, 'DIA003', 'Round Brilliant Cut Diamond - Australia', 'Australia', 'Very Good', 'Very Good', 'Very Good', 'Strong', 1),
    (4,'DIA004', 'Round Brilliant Cut Diamond - Canada', 'Canada', 'Good', 'Good', 'Good', 'Faint', 1),
    (5,'DIA005', 'Round Brilliant Cut Diamond - India', 'India', 'Good', 'Good', 'Good', 'None', 1),
    (6,'DIA006', 'Round Brilliant Cut Diamond - America', 'America', 'Excellent', 'Excellent', 'Excellent', 'Medium', 1),
    (7,'DIA007', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 'Very Good', 'Very Good', 'Very Good', 'Strong', 1),
    (8,'DIA008', 'Round Brilliant Cut Diamond - China', 'China', 'Very Good', 'Very Good', 'Very Good', 'Faint', 1),
    (9,'DIA009', 'Round Brilliant Cut Diamond - Germany', 'Germany', 'Good', 'Good', 'Good', 'None', 1),
    (10,'DIA010', 'Round Brilliant Cut Diamond - Taiwan', 'Taiwan', 'Good', 'Good', 'Good', 'Medium', 1),
    (11,'DIA011', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 'Excellent', 'Excellent', 'Excellent', 'None', 1),
    (12,'DIA012', 'Round Brilliant Cut Diamond - Italy', 'Italy', 'Very Good', 'Very Good', 'Very Good', 'Medium', 1),
    (13,'DIA013', 'Round Brilliant Cut Diamond - Malaysia', 'Malaysia', 'Very Good', 'Very Good', 'Very Good', 'Strong', 1),
    (14,'DIA014', 'Round Brilliant Cut Diamond - Italy', 'Italy', 'Good', 'Good', 'Good', 'Faint', 1),
    (15,'DIA015', 'Round Brilliant Cut Diamond - India', 'India', 'Good', 'Good', 'Good', 'None', 1),
    (16,'DIA016', 'Round Brilliant Cut Diamond - Russia', 'Russia', 'Excellent', 'Excellent', 'Excellent', 'Medium', 1),
    (17,'DIA017', 'Round Brilliant Cut Diamond - Russia', 'Russia', 'Very Good', 'Very Good', 'Very Good', 'Strong', 1),
    (18,'DIA018', 'Round Brilliant Cut Diamond - Australia', 'Australia', 'Very Good', 'Very Good', 'Very Good', 'Faint', 1),
    (19,'DIA019', 'Round Brilliant Cut Diamond - Canada', 'Canada', 'Good', 'Good', 'Good', 'None', 1),
    (20,'DIA020', 'Round Brilliant Cut Diamond - India', 'India', 'Good', 'Good', 'Good', 'Medium', 1),
    (21,'DIA021', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 'Excellent', 'Excellent', 'Excellent', 'None', 1),
    (22,'DIA022', 'Round Brilliant Cut Diamond - Russia', 'Russia', 'Very Good', 'Very Good', 'Very Good', 'Medium', 1),
    (23,'DIA023', 'Round Brilliant Cut Diamond - Australia', 'Australia', 'Very Good', 'Very Good', 'Very Good', 'Strong', 1),
    (24,'DIA024', 'Round Brilliant Cut Diamond - Canada', 'Canada', 'Good', 'Good', 'Good', 'Faint', 1),
    (25,'DIA025', 'Round Brilliant Cut Diamond - India', 'India', 'Good', 'Good', 'Good', 'None', 1),
    (26,'DIA026', 'Round Brilliant Cut Diamond - America', 'America', 'Excellent', 'Excellent', 'Excellent', 'Medium', 1),
    (27,'DIA027', 'Round Brilliant Cut Diamond - Russia', 'Russia', 'Very Good', 'Very Good', 'Very Good', 'Strong', 1),
    (28,'DIA028', 'Round Brilliant Cut Diamond - Australia', 'Australia', 'Very Good', 'Very Good', 'Very Good', 'Faint', 1),
    (29,'DIA029', 'Round Brilliant Cut Diamond - Canada', 'Canada', 'Good', 'Good', 'Good', 'None', 1),
    (30,'DIA030', 'Round Brilliant Cut Diamond - Italy', 'Italy', 'Good', 'Good', 'Good', 'Medium', 1);


select * from Users
select * from Customer
select * from Employee
select * from Category
select * from Blogs
select * from [Collection]
select * from Material
select * from MaterialPriceList
select * from Gemstone
select * from GemPriceList

SELECT u FROM User u WHERE u.email = 'customer2@example.com' AND u.password ='Cust2*Strong'