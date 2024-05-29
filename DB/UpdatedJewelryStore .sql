
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
	[UserId] [int] IDENTITY(1,1),
	[Email] [varchar](255) NOT NULL,
	[Password] [char](64) NOT NULL,
	[RoleID] [int] NOT NULL,
	[Status] bit NOT NULL,
CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[UserId] ASC
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
	[FullName] [nvarchar](255) NOT NULL,
	[UserId] [int] REFERENCES [Users](UserId),
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
	[FullName] [nvarchar](255) NOT NULL,
	[Address] [nvarchar](max) NOT NULL,
	[PhoneNumber] [varchar](10) NOT NULL,
	[UserId] [int] REFERENCES [Users](UserId),
CONSTRAINT [PK_Customer] PRIMARY KEY CLUSTERED 
(
	[CustomerId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [Category] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category](
	[CategoryId] int identity(1,1) NOT NULL,
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

	[ProductId] int,
CONSTRAINT [PK_Gemstone] PRIMARY KEY CLUSTERED 
	(
		[GemId] ASC
	) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
	CONSTRAINT [FK_Gemstone_Product] FOREIGN KEY ([ProductId]) REFERENCES [dbo].[Product] ([ProductId])
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
	[CategoryId] int REFERENCES [Category](CategoryId),
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
	[CategoryId] int REFERENCES [Category]([CategoryId]),
	
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

	[Status] [nvarchar](10),  --created, requested, quoted, ordered, confirmed, delivered

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
	[CategoryId] int REFERENCES [Category](CategoryId),
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


/* Table [ProductDesignDiamond] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProductDesignDiamond](
	[PDDId] int IDENTITY(1,1),
	[GemId] int,
	[GemMinSize] float,
	[GemMaxSize] float,
	
CONSTRAINT [PK_ProductDesignDiamond] PRIMARY KEY CLUSTERED 
(
	[PDDId] ASC
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
INSERT INTO [dbo].[Employee] (EmployeeId, FullName, UserId)
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
INSERT INTO [dbo].[Customer] (CustomerId, FullName, Address, PhoneNumber, UserId)
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
INSERT INTO [dbo].[Category] (CategoryName, Status) VALUES
('ring', 1),
('necklace', 1),
('earrings', 1),
('bracelet', 1),
('cufflink', 1);

INSERT INTO [dbo].[Blogs] ([Title], [Content], [Date], [EmployeeId]) VALUES
('The Brilliance of Diamonds', 
 'Explore the mesmerizing brilliance of diamonds, discussing their formation, characteristics, and symbolism in various cultures. Highlight the unique features that make diamonds a timeless choice for jewelry.', 
 '2024-05-27', 'SS001'),

('Crafting Beauty: The Art of Gold Jewelry Making', 
 'Delve into the craftsmanship behind gold jewelry, showcasing the intricate techniques used by artisans to create stunning pieces. Discuss the versatility of gold and its significance in different jewelry designs.', 
 '2024-05-28', 'DE001'),

('Unveiling the Mystique of Precious Gemstones', 
 'Take your readers on a journey through the world of precious gemstones, including sapphires, rubies, and emeralds. Explore their origins, colors, and cultural meanings, offering insights into why these gems are treasured.', 
 '2024-05-29', 'SS003'),

('From Mine to Market: The Diamond Supply Chain', 
 'Shed light on the journey of diamonds from the mines to the market, discussing ethical sourcing practices, sustainability efforts, and the role of certification in ensuring transparency and integrity.', 
 '2024-05-30', 'PR001'),

('The Language of Jewelry: Symbols and Meanings', 
 'Explore the symbolic meanings behind common jewelry motifs such as hearts, infinity symbols, and flowers. Discuss how these symbols resonate with individuals and convey messages of love, hope, and empowerment.', 
 '2024-06-01', 'DE003');

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
DECLARE @StartDate DATE = '2024-05-28'; -- Start date for generating data
DECLARE @EndDate DATE = '2024-05-28';   -- End date for generating data

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
DECLARE @StartDateGem DATE = '2024-05-29'; -- Start date for generating data
DECLARE @EndDateGem DATE = '2024-5-29';   -- End date for generating data
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

-- Insert data into the Gemstone table
INSERT INTO [dbo].[Gemstone] ([GemCode], [GemName], [Origin], [CaratWeight], [Color], [Clarity], [Cut], [Proportions], [Polish], [Symmetry], [Fluorescence], [Status])
VALUES
-- Gemstones with D Color
('DIA001', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.17, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA002', 'Round Brilliant Cut Diamond - America', 'America', 0.17, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA003', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.17, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA004', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.17, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA005', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.17, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
 
-- Gemstones with E Color
('DIA006', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.17, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA007', 'Round Brilliant Cut Diamond - America', 'America', 0.17, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA008', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.17, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA009', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.17, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA010', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.17, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with F Color
('DIA011', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.17, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA012', 'Round Brilliant Cut Diamond - America', 'America', 0.17, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA013', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.17, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA014', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.17, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA015', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.17, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with J Color
('DIA016', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.17, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA017', 'Round Brilliant Cut Diamond - America', 'America', 0.17, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA018', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.17, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA019', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.17, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA020', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.17, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with D Color
('DIA021', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.34, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA022', 'Round Brilliant Cut Diamond - America', 'America', 0.34, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA023', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.34, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA024', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.34, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA025', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.34, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),

-- Gemstones with E Color
('DIA026', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.34, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA027', 'Round Brilliant Cut Diamond - America', 'America', 0.34, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA028', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.34, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA029', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.34, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA030', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.34, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with F Color
('DIA031', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.34, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA032', 'Round Brilliant Cut Diamond - America', 'America', 0.34, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA033', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.34, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA034', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.34, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA015', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.34, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with J Color
('DIA036', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.34, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA037', 'Round Brilliant Cut Diamond - America', 'America', 0.34, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA038', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.34, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA039', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.34, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA040', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.34, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with D Color
('DIA041', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.5, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA042', 'Round Brilliant Cut Diamond - America', 'America', 0.5, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA043', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.5, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA044', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.5, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA045', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.5, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),

-- Gemstones with E Color
('DIA046', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.5, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA047', 'Round Brilliant Cut Diamond - America', 'America', 0.5, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA048', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.5, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA049', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.5, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA050', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.5, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with F Color
('DIA051', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.5, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA052', 'Round Brilliant Cut Diamond - America', 'America', 0.5, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA053', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.5, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA054', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.5, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA055', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.5, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with J Color
('DIA056', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.5, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA057', 'Round Brilliant Cut Diamond - America', 'America', 0.5, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA058', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.5, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA059', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.5, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA060', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.5, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with D Color
('DIA061', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA062', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA063', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA064', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA065', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with E Color
('DIA066', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA067', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA068', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA069', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA070', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with F Color
('DIA071', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA072', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA073', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA074', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA075', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with J Color
('DIA076', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA077', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA078', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA079', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA080', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.52, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with D Color
('DIA081', 'Round Brilliant Cut Diamond - America', 'America', 0.6, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA082', 'Round Brilliant Cut Diamond - America', 'America', 0.6, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA083', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.6, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA084', 'Round Brilliant Cut Diamond - America', 'America', 0.6, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA085', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.6, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with E Color
('DIA086', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.6, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA087', 'Round Brilliant Cut Diamond - America', 'America', 0.6, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA088', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.6, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA089', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.6, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA090', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.6, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with F Color
('DIA091', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.6, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA092', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.6, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA093', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.6, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA094', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.6, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA095', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.6, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with J Color
('DIA096', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.6, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA097', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.6, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA098', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.6, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA099', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.6, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA100', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.6, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with D Color
('DIA101', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.84, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA102', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.84, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA103', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.84, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA104', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.84, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA105', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.84, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with E Color
('DIA106', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.84, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA107', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.84, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA108', 'Round Brilliant Cut Diamond - America', 'America', 0.84, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA109', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.84, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA110', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.84, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with F Color
('DIA111', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.84, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA112', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.84, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA113', 'Round Brilliant Cut Diamond - America', 'America', 0.84, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA114', 'Round Brilliant Cut Diamond - Australia', 'Australia', 0.84, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA115', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.84, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with J Color
('DIA116', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.84, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA117', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.84, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA118', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.84, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA119', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.84, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA120', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 0.84, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with D Color
('DIA121', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.92, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA122', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.92, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA123', 'Round Brilliant Cut Diamond - Italy', 'Italy', 0.92, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA124', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.92, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA125', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.92, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with E Color
('DIA126', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.92, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA127', 'Round Brilliant Cut Diamond - America', 'America', 0.92, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA128', 'Round Brilliant Cut Diamond - America', 'America', 0.92, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA129', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.92, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA130', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.92, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with F Color
('DIA131', 'Round Brilliant Cut Diamond - America', 'America', 0.92, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA132', 'Round Brilliant Cut Diamond - America', 'America', 0.92, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA133', 'Round Brilliant Cut Diamond - America', 'America', 0.92, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA134', 'Round Brilliant Cut Diamond - America', 'America', 0.92, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA135', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.92, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with J Color
('DIA136', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.92, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA137', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.92, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA138', 'Round Brilliant Cut Diamond - America', 'America', 0.92, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA139', 'Round Brilliant Cut Diamond - America', 'America', 0.92, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA140', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 0.92, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with D Color
('DIA141', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.25, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA142', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.25, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA143', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.25, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA144', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.25, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 0),
('DIA145', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.25, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with E Color
('DIA146', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.25, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA147', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.25, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA148', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.25, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA149', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.25, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA150', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.25, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with F Color
('DIA151', 'Round Brilliant Cut Diamond - Italy', 'Italy', 1.25, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA152', 'Round Brilliant Cut Diamond - Italy', 'Italy', 1.25, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA153', 'Round Brilliant Cut Diamond - Italy', 'Italy', 1.25, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA154', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.25, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA155', 'Round Brilliant Cut Diamond - Italy', 'Italy', 1.25, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with J Color
('DIA156', 'Round Brilliant Cut Diamond - America', 'America', 1.25, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA157', 'Round Brilliant Cut Diamond - America', 'America', 1.25, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA158', 'Round Brilliant Cut Diamond - America', 'America', 1.25, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA159', 'Round Brilliant Cut Diamond - America', 'America', 1.25, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA160', 'Round Brilliant Cut Diamond - America', 'America', 1.25, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with D Color
('DIA161', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.33, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA162', 'Round Brilliant Cut Diamond - Australia', 'Australia', 1.33, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA163', 'Round Brilliant Cut Diamond - America', 'America', 1.33, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA164', 'Round Brilliant Cut Diamond - Australia', 'Australia', 1.33, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA165', 'Round Brilliant Cut Diamond - Italy', 'Italy', 1.33, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with E Color
('DIA166', 'Round Brilliant Cut Diamond - Italy', 'Italy', 1.33, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA167', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.33, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA168', 'Round Brilliant Cut Diamond - Italy', 'Italy', 1.33, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA169', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.33, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA170', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.33, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with F Color
('DIA171', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.33, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA172', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.33, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA173', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.33, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA174', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.33, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA175', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.33, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with J Color
('DIA176', 'Round Brilliant Cut Diamond - Italy', 'Italy', 1.33, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA177', 'Round Brilliant Cut Diamond - South Africa', 'South Africa', 1.33, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA178', 'Round Brilliant Cut Diamond - Italy', 'Italy', 1.33, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA179', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.33, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA180', 'Round Brilliant Cut Diamond - Australia', 'Australia', 1.33, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with D Color
('DIA181', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 2.75, 'D', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA182', 'Round Brilliant Cut Diamond - Australia', 'Australia', 2.75, 'D', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA183', 'Round Brilliant Cut Diamond - Australia', 'Australia', 2.75, 'D', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA184', 'Round Brilliant Cut Diamond - Australia', 'Australia', 2.75, 'D', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA185', 'Round Brilliant Cut Diamond - Australia', 'Australia', 2.75, 'D', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with E Color
('DIA186', 'Round Brilliant Cut Diamond - Australia', 'Australia', 2.75, 'E', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA187', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 2.75, 'E', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA188', 'Round Brilliant Cut Diamond - Australia', 'Australia', 2.75, 'E', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA189', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 2.75, 'E', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA190', 'Round Brilliant Cut Diamond - America', 'America', 2.75, 'E', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with F Color
('DIA191', 'Round Brilliant Cut Diamond - America', 'America', 2.75, 'F', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA192', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 2.75, 'F', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA193', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 2.75, 'F', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA194', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 2.75, 'F', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA195', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 2.75, 'F', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),

-- Gemstones with J Color
('DIA196', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 2.75, 'J', 'IF', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA197', 'Round Brilliant Cut Diamond - America', 'America', 2.75, 'J', 'VVS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA198', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 2.75, 'J', 'VVS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA199', 'Round Brilliant Cut Diamond - America', 'America', 2.75, 'J', 'VS1', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1),
('DIA200', 'Round Brilliant Cut Diamond - Vietnam', 'Vietnam', 1.33, 'J', 'VS2', 'Excellent', 'Ideal', 'Excellent', 'Excellent', 'None', 1);

-- Insert data into the Product table
INSERT INTO [dbo].[Product] ([ProductCode], [ProductName], [CategoryId], [CollectionId], [Description], [Gender], [Size], [Status])
VALUES
('PO001', 'Radiant Sunburst Diamond Ring', 1, NULL, 'Radiant sunburst design featuring a brilliant round cut diamond center stone.', 'Unisex', 7, 1),
('PO002', 'Ethereal Dream Diamond Necklace', 2, NULL, 'An ethereal dream necklace showcasing a mesmerizing round brilliant cut diamond pendant.', 'Women', 18, 1),
('PO003', 'Starry Night Diamond Earrings', 3, NULL, 'Starry night inspired diamond earrings featuring dazzling round brilliant cut diamonds.', 'Women', 8, 1),
('PO004', 'Celestial Cascade Diamond Bracelet', 4, NULL, 'Celestial cascade bracelet adorned with cascading round brilliant cut diamonds.', 'Women', 7, 1),
('PO005', 'Lunar Elegance Diamond Cufflinks', 5, NULL, 'Lunar elegance cufflinks crafted with precision and featuring round brilliant cut diamonds.', 'Men', 10, 1),
('PO006', 'Diamond Galaxy Engagement Ring', 1, NULL, 'Embark on a journey with this diamond galaxy engagement ring, featuring a stunning round brilliant cut diamond.', 'Women', 7, 1),
('PO007', 'Aurora Borealis Diamond Pendant', 2, NULL, 'Capture the beauty of the northern lights with this aurora borealis diamond pendant.', 'Women', 18, 1),
('PO008', 'Eternal Love Diamond Earrings', 3, NULL, 'Symbolize eternal love with these breathtaking diamond earrings featuring round brilliant cut diamonds.', 'Women', 8, 1),
('PO009', 'Twilight Serenade Diamond Bracelet', 4, NULL, 'Serene elegance meets twilight allure in this stunning diamond bracelet.', 'Women', 7, 1),
('PO010', 'Galactic Odyssey Diamond Cufflinks', 5, NULL, 'Embark on a galactic odyssey with these exquisite diamond cufflinks.', 'Men', 10, 1),
('PO011', 'Dazzling Horizon Diamond Ring', 1, NULL, 'Experience the dazzling horizon with this exquisite diamond ring featuring a round brilliant cut diamond.', 'Unisex', 7, 1),
('PO012', 'Moonlit Sonata Diamond Necklace', 2, NULL, 'Let the moonlit sonata serenade you with this enchanting diamond necklace.', 'Women', 18, 1),
('PO013', 'Starry-eyed Romance Diamond Earrings', 3, NULL, 'Indulge in starry-eyed romance with these captivating diamond earrings.', 'Women', 8, 1),
('PO014', 'Celestial Bliss Diamond Bracelet', 4, NULL, 'Experience celestial bliss with this exquisite diamond bracelet.', 'Women', 7, 1),
('PO015', 'Lunar Luminary Diamond Cufflinks', 5, NULL, 'Illuminate your style with these lunar luminary diamond cufflinks.', 'Men', 10, 1),
('PO016', 'Enchanted Garden Diamond Ring', 1, NULL, 'Step into an enchanted garden with this mesmerizing diamond ring.', 'Women', 7, 1),
('PO017', 'Starlight Symphony Diamond Pendant', 2, NULL, 'Let the starlight symphony enchant you with this exquisite diamond pendant.', 'Women', 18, 1),
('PO018', 'Eternal Embrace Diamond Earrings', 3, NULL, 'Embrace eternity with these stunning diamond earrings.', 'Women', 8, 1),
('PO019', 'Twilight Reverie Diamond Bracelet', 4, NULL, 'Drift into a twilight reverie with this elegant diamond bracelet.', 'Women', 7, 1),
('PO020', 'Cosmic Charm Diamond Cufflinks', 5, NULL, 'Add cosmic charm to your attire with these exquisite diamond cufflinks.', 'Men', 10, 1);

-- Insert data into the ProductMaterial table
INSERT INTO [dbo].[ProductMaterial] (ProductId, MaterialId, MaterialWeight)
VALUES
-- Combining products with materials and assigning weights
(1, 7, 1),   -- Product PO001 with Gold18K, weight 1
(2, 8, 1),   -- Product PO002 with Gold22K, weight 1
(3, 4, 2),   -- Product PO003 with Gold14K, weight 2
(4, 6, 4),   -- Product PO004 with Gold16.3K, weight 4
(5, 7, 2),   -- Product PO005 with Gold18K, weight 2
(6, 7, 1),   -- Product PO006 with Gold18K, weight 1
(7, 7, 4),   -- Product PO007 with Gold18K, weight 4
(8, 7, 5),   -- Product PO008 with Gold18K, weight 5
(9, 8, 6),   -- Product PO009 with Gold22K, weight 6
(10, 9, 7), -- Product PO010 with SJC, weight 7
(11, 1, 4),  -- Product PO011 with Gold8K, weight 4
(12, 2, 6),  -- Product PO012 with Gold9K, weight 6
(13, 3, 8),  -- Product PO013 with Gold10K, weight 8
(14, 4, 5),  -- Product PO014 with Gold14K, weight 5
(15, 5, 7),  -- Product PO015 with Gold15.6K, weight 7
(16, 6, 4),  -- Product PO016 with Gold16.3K, weight 4
(17, 7, 5),  -- Product PO017 with Gold18K, weight 5
(18, 8, 6),  -- Product PO018 with Gold22K, weight 6
(19, 9, 7),  -- Product PO019 with SJC, weight 7
(20, 10, 1); -- Product PO020 with PNJ24K, weight 1


INSERT INTO [dbo].[ProductionOrder] ([Date], [CustomerId], [CategoryId], [MaterialName], [MaterialColor], [MaterialWeight], [MaterialId], [GemName], [GemColor], [GemWeight], [GemId], [ProductSize], [Description], [DiamondAmount], [MaterialAmount], [ProductionAmount], [SideMaterialCost], [SideGemCost], [TotalAmount], [SalesStaffName], [DesignStaffName], [ProductionStaffName], [Status], [ProductId])
VALUES 
('2024-05-28', 'CUS010', 3, 'Gold 10K', 'White Gold', 8, NULL, 'Beautiful Diamond', 'White', 0.7, NULL, 14, 'Custom earrings design', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'created', NULL),
('2024-05-27', 'CUS004', 4, 'Gold 22K', 'Yellow', 20, NULL, 'Diamond', 'Lightly Gold', 1.0, NULL, 20, 'Custom bracelet design', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'created', NULL),

('2024-05-28', 'CUS005', 5, 'Gold 9K', 'Yellow', 5, NULL, 'Diamond', 'F', 0.3, NULL, 12, 'Custom cufflink design', NULL, NULL, NULL, NULL, NULL, NULL, 'Ivy Scott', NULL, NULL, 'requested', NULL),
('2024-05-27', 'CUS001', 1, 'Gold 18K', 'Yellow', 10, NULL, 'Round Brilliant Cut Diamond', 'D', 0.5, NULL, 16, 'Custom engagement ring design', NULL, NULL, NULL, NULL, NULL, NULL, 'Jack Turner', NULL, NULL, 'requested', NULL),
('2024-05-26', 'CUS002', 2, 'Gold 14K', 'Rose Gold', 15, NULL, 'Shining Diamond', 'Blue', 0.8, NULL, 18, 'Custom pendant design', NULL, NULL, NULL, NULL, NULL, NULL, 'Ivy Scott', NULL, NULL, 'requested', NULL),
('2024-05-20', 'CUS005', 5, 'Gold 15.6K', 'Yellow', 4, NULL, 'Diamond', 'Yellow', 1, NULL, 15, 'Pair of gold cufflinks featuring white diamond stones', NULL, NULL, NULL, NULL, NULL, NULL, 'Leo Adams', NULL, NULL, 'requested', NULL),

('2024-05-28', 'CUS006', 1, 'Gold 16.3K', 'White Gold', 12, 6, 'Diamond', 'E', 0.6, 6, 6, 'Custom wedding band design', 412.23, 196.11, 300.00, 70.0, 120.0, 1208.17, 'Leo Adams', NULL, NULL, 'quoted', NULL),
('2024-05-27', 'CUS008', 2, 'Gold 15.6K', 'Yellow', 18, 5, 'Diamond', 'Silver', 0.9, 2, 45, 'Custom necklace design', 384.75, 187.27, 200.00, 90.0, 20.0, 970.22, 'Jack Turner', NULL, NULL, 'quoted', NULL),
('2024-05-27', 'CUS007', 2, 'Gold 22K', 'Yellow', 9, 8, 'Diamond', 'Blue', 3, 11, 40, 'Exquisite gold necklace featuring a large diamond pendant', 417.02, 269.25, 300.00, 150.00, 100.00, 1359.90, 'Karen Walker', NULL, NULL, 'quoted', NULL),


('2024-05-28', 'CUS003', 3, 'Gold 14K', 'Rose', 3, 4, 'Diamond', 'White Green', 1, 3, 4, 'Rose gold earrings featuring diamond gemstones', 345.74, 168.03, 200.00, 50.00, 50.00, 895.147, 'Jack Turner', 'Mia Nelson', NULL, 'ordered', NULL),
('2024-05-28', 'CUS004', 4, 'Gold 16.3K', 'White', 6, 6, 'Diamond', 'White Red', 1.5, 17, 17, 'White gold bracelet adorned with diamond gemstones', 331.91, 196.11, 200.00, 50.00, 50.00, 910.82, 'Karen Walker', 'Nate Perez', NULL, 'ordered', NULL),

('2024-05-26', 'CUS008', 3, 'Gold 18K', 'Yellow', 10, 7, 'Diamond', 'Clearly White', 0.7, 16, 14, 'Custom earrings design', 340.43, 216.83, 550.00, 30.00, 50.00, 1305.97, 'Ivy Scott', 'Nate Perez', 'Quinn Baker', 'confirmed', NULL),
('2024-05-28', 'CUS002', 2, 'Gold 22K', 'Yellow', 8, 8, 'Diamond', 'Blue', 2, 18, 45, 'Handcrafted gold necklace with a diamond pendant', 276.6, 269.25, 250.00, 60.00, 60.00, 1007.44, 'Ivy Scott', 'Olivia Hill', 'Rachel Carter', 'confirmed', NULL),

('2024-05-27', 'CUS001', 1, 'Gold 18K', 'Yellow', 5, 7, 'Diamond', 'D', 0.5, 1, 4, 'Customized ring with diamond accents', 424.01, 216.83, 350.00, 70.00, 70.00, 1243.92, 'Leo Adams', 'Mia Nelson', 'Sam Edwards', 'delivered', 1),
('2024-06-01', 'CUS006', 1, 'Gold 18K', 'Yellow', 7, 7, 'Diamond', 'F', 0.7, 9, 7, 'Custom-designed gold ring with a diamond', 282.88, 216.83, 150.00, 60.00, 60.00, 846.68, 'Leo Adams', 'Paul Young', 'Rachel Carter', 'delivered', 6),

('2024-05-20', 'CUS002', 1, 'Gold 22K', 'Yellow', 5, 8, 'Diamond', 'D', 0.34, 21, 5, 'Customized ring with diamond accents', 1055.32, 269.25, 350.00, 70.00, 70.00, 1996.03, 'Leo Adams', 'Mia Nelson', 'Sam Edwards', 'delivered', 2),
('2024-04-20', 'CUS003', 2, 'Gold 14K', 'Yellow', 7, 4, 'Diamond', 'F', 0.3, 22, 45, 'Custom-designed gold necklace with a diamond', 902.13, 336.06, 150.00, 60.00, 60.00, 1659.01, 'Leo Adams', 'Paul Young', 'Rachel Carter', 'delivered', 3),
('2024-05-21', 'CUS004', 2, 'Gold 16.3K', 'Yellow', 5, 6, 'Diamond', 'D', 0.34, 23, 42, 'Customized necklace with diamond accents', 842.55, 784.44, 300.00, 100.00, 100.00, 2339.69, 'Leo Adams', 'Mia Nelson', 'Sam Edwards', 'delivered', 4),
('2024-05-23', 'CUS005', 1, 'Gold 18K', 'Yellow', 7, 7, 'Diamond', 'F', 0.34, 24, 8, 'Custom-designed gold ring with a diamond', 787.23, 433.66, 250.00, 100.00, 100.00, 1837.98, 'Leo Adams', 'Paul Young', 'Rachel Carter', 'delivered', 5),
('2024-05-22', 'CUS007', 3, 'Gold 18K', 'Yellow', 5, 7, 'Diamond', 'D', 0.34, 25, 1, 'Customized gold earrings with diamonds', 680.85, 867.32, 150.00, 70.00, 70.00, 2021.99, 'Leo Adams', 'Mia Nelson', 'Sam Edwards', 'delivered', 7),
('2024-05-24', 'CUS008', 4, 'Gold 18K', 'Yellow', 7, 7, 'Diamond', 'F', 0.34, 26, 20, 'Custom-designed gold bracelet with a diamond center stone', 961.7, 1084.15, 350.00, 90.00, 90.00, 2833.44, 'Leo Adams', 'Paul Young', 'Rachel Carter', 'delivered', 8),
('2024-05-25', 'CUS009', 3, 'Gold 22K', 'Yellow', 5, 8, 'Diamond', 'D', 0.5, 41, 1, 'Customized earrings with diamond accents', 1659.57, 1615.5, 350.00, 70.00, 70.00, 4140.58, 'Leo Adams', 'Mia Nelson', 'Sam Edwards', 'delivered', 9),
('2024-05-20', 'CUS010', 4, 'SJC Gold Piece', 'Yellow', 7, 7, 'Diamond', 'F', 0.5, 42, 19, 'Custom-designed gold bracelet with a diamond center stone', 1553.19, 2456.93, 550.00, 160.00, 160.00, 5368.13, 'Leo Adams', 'Paul Young', 'Rachel Carter', 'delivered', 10),
('2024-05-27', 'CUS007', 5, 'Gold 8K', 'Yellow', 5, 1, 'Diamond', 'D', 0.5, 43, 5, 'Customized cufflinks with diamond accents', 1489.36, 370.6, 350.00, 70.00, 70.00, 2584.96, 'Leo Adams', 'Mia Nelson', 'Sam Edwards', 'delivered', 11),
('2024-05-21', 'CUS002', 1, 'Gold 9K', 'Yellow', 7, 2, 'Diamond', 'F', 0.5, 44, 48, 'Custom-designed gold ring with a diamond center stone', 1361.7, 635.76, 150.00, 60.00, 60.00, 2494.21, 'Leo Adams', 'Paul Young', 'Rachel Carter', 'delivered', 12),
('2024-05-28', 'CUS002', 5, 'Gold 10K', 'Yellow', 5, 3, 'Diamond', 'D', 0.5, 45, 5, 'Customized cufflinks with diamond accents', 1297.87, 944.72, 350.00, 50.00, 50.00, 2961.85, 'Leo Adams', 'Mia Nelson', 'Sam Edwards', 'delivered', 13),
('2024-05-10', 'CUS001', 3, 'Gold 14K', 'Yellow', 7, 4, 'Diamond', 'F', 0.5, 46, 17, 'Custom-designed gold earrings with a diamond center stone', 1621.28, 840.15, 450.00, 60.00, 60.00, 3334.57, 'Leo Adams', 'Paul Young', 'Rachel Carter', 'delivered', 14),
('2024-05-27', 'CUS001', 2, 'Gold 15.6K', 'Yellow', 5, 5, 'Diamond', 'D', 0.6, 81, 50, 'Customized necklace with diamond accents', 3109.42, 1301.89, 350.00, 70.00, 70.00, 5401.34, 'Leo Adams', 'Mia Nelson', 'Sam Edwards', 'delivered', 15),
('2024-05-13', 'CUS006', 1, 'Gold 16.3K', 'Yellow', 7, 6, 'Diamond', 'F', 0.6, 82, 9, 'Custom-designed gold ring with a diamond center stone', 1310.89, 784.44, 1000.00, 160.00, 160.00, 4195.33, 'Leo Adams', 'Paul Young', 'Rachel Carter', 'delivered', 16),
('2024-05-27', 'CUS004', 1, 'Gold 18K', 'Yellow', 5, 7, 'Diamond', 'D', 1.25, 141, 5, 'Customized ring with diamond accents', 14782.61, 1084.15, 2350.00, 160.00, 160.00, 20390.44, 'Leo Adams', 'Mia Nelson', 'Sam Edwards', 'delivered', 17),
('2024-05-09', 'CUS005', 4, 'Gold22K', 'Yellow', 7, 8, 'Diamond', 'F', 1.25, 142, 15, 'Custom-designed gold bracelet with a diamond center stone', 14739.13, 1615.5, 2350.00, 160.00, 160.00, 20927.09, 'Leo Adams', 'Paul Young', 'Rachel Carter', 'delivered', 18),
('2024-05-28', 'CUS006', 4, 'SJC Gold Piece', 'Yellow', 5, 9, 'Diamond', 'D', 1.25, 143, 16, 'Customized bracelet with diamond accents', 14260.87, 2456.93, 1350.00, 160.00, 160.00, 20226.58, 'Leo Adams', 'Mia Nelson', 'Sam Edwards', 'delivered', 19),
('2024-05-29', 'CUS007', 5, 'PNJ Gold 24K', 'Yellow', 7, 10, 'Diamond', 'F', 1.25, 144, 4, 'Custom-designed gold cufflinks with a diamond center stone', 13713.04, 292.88, 5150.00, 160.00, 160.00, 21643.51, 'Leo Adams', 'Paul Young', 'Rachel Carter', 'delivered', 20);


-- Insert data into the ProductDesignShell table
INSERT INTO [dbo].[ProductDesignShell] (MaterialId, Weight)
VALUES
-- Gold 8K (MaterialId = 1)
(1, 1),
(1, 2),
(1, 3),
(1, 4),

-- Gold 9K (MaterialId = 2)
(2, 1),
(2, 2),
(2, 3),
(2, 4),

-- Gold 10K (MaterialId = 3)
(3, 1),
(3, 2),
(3, 3),
(3, 4),

-- Gold 14K (MaterialId = 4)
(4, 1),
(4, 2),
(4, 3),
(4, 4),

-- Gold 15.6K (MaterialId = 5)
(5, 1),
(5, 2),
(5, 3),
(5, 4),

-- Gold 16.6K (MaterialId = 6)
(6, 1),
(6, 2),
(6, 3),
(6, 4),
-- Gold 18K (MaterialId = 7)
(7, 1),
(7, 2),
(7, 3),
(7, 4),
-- Gold 22K (MaterialId = 8)
(8, 1),
(8, 2),
(8, 3),
(8, 4),
-- SJC Gold Piece (MaterialId = 9)
(9, 1),
(9, 2),
(9, 3),
(9, 4),
-- PNJ Ring Gold 24K (MaterialId = 10)
(10, 1),
(10, 2),
(10, 3),
(10, 4);

-- Insert data into the ProductDesignDiamond table
INSERT INTO [dbo].[ProductDesignDiamond] (GemId, GemMinSize, GemMaxSize)
VALUES
-- GemId from 1 to 6 with size 0.17 to 0.5
(1, 0.17, 0.5),
(2, 0.17, 0.5),
(3, 0.17, 0.5),
(4, 0.17, 0.5),
(5, 0.17, 0.5),
(6, 0.17, 0.5),

-- GemId from 7 to 12 with size 0.5 to 0.6
(7, 0.5, 0.6),
(8, 0.5, 0.6),
(9, 0.5, 0.6),
(10, 0.5, 0.6),
(11, 0.5, 0.6),
(12, 0.5, 0.6),

-- GemId from 13 to 18 with size 0.6 to 0.92
(13, 0.6, 0.92),
(14, 0.6, 0.92),
(15, 0.6, 0.92),
(16, 0.6, 0.92),
(17, 0.6, 0.92),
(18, 0.6, 0.92),

-- GemId from 19 to 24 with size 0.92 to 1.25
(19, 0.92, 1.25),
(20, 0.92, 1.25),
(21, 0.92, 1.25),
(22, 0.92, 1.25),
(23, 0.92, 1.25),
(24, 0.92, 1.25),

-- GemId from 25 to 30 with size 1.25 to 2.75
(25, 1.25, 2.75),
(26, 1.25, 2.75),
(27, 1.25, 2.75),
(28, 1.25, 2.75),
(29, 1.25, 2.75),
(30, 1.25, 2.75);


select * from Users
select * from Customer
select * from Employee
select * from Category
select * from Blogs
select * from Collections
select * from Material
select * from MaterialPriceList
select * from GemPriceList
select * from Gemstone
select * from Product
select * from ProductionOrder
select * from ProductDesignShell
select * from ProductDesignDiamond

