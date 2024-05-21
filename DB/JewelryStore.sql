
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
CREATE TABLE [dbo].[User](
	[UserId] [int] IDENTITY(1,1),
	[Email] [varchar](255) NOT NULL,
	[FullName] [nvarchar](255) NOT NULL,
	[Password] [char](64) NOT NULL,
	[RoleID] [int] NOT NULL,
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
	[UserId] [int] REFERENCES [User](UserId),
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
	[Address] [nvarchar](max) NOT NULL,
	[PhoneNumber] [int] NOT NULL,
	[Membership] [nvarchar](max)NOT NULL,
	[UserId] [int] REFERENCES [User](UserId),
CONSTRAINT [PK_Customer] PRIMARY KEY CLUSTERED 
(
	[CustomerId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [Request] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Request](
	[Id] [int] IDENTITY(1,1),
	[Date] [date] NOT NULL,
	
	[CustomerId] [nvarchar] (255) REFERENCES [Customer](CustomerId),
	[CategoryName] [nvarchar](255) NOT NULL,
	[MaterialName] [nvarchar](255) NOT NULL,
	[MaterialColor] [char](10) NOT NULL,
	[MaterialWeight] [float] NOT NULL,
	[GemName] [nvarchar](255) NOT NULL,
	[GemColor] [char](10) NOT NULL,
	[GemlWeight] [float] NOT NULL,
	[Size] [int] NOT NULL,
	[Description] [nvarchar](max) NOT NULL,
	[Status] [bit]
CONSTRAINT [PK_Request] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [Request]
ADD [Gender] [nvarchar](50) NOT NULL


/* Table [Quote] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Quote](
	[Id] [int] IDENTITY(1,1),
	[Date] [date] NOT NULL,
	[CategoryId] [nvarchar](255) REFERENCES [Category](CategoryId),
	[MaterialId] [nvarchar] (255)REFERENCES [Material](MaterialId),
	[MaterialWeight] [float] NOT NULL,
	[MaterialPrice] [float] NOT NULL,
	[GemId] [int] REFERENCES [GemStone](GemId),
	[GemWeight] [float] NOT NULL,
	[SideMaterialCost] [float] NOT NULL,
	[SideGemCost] [float] NOT NULL,
	[DesginCost] [float] NOT NULL,
	[ProductionCost] [float] NOT NULL,
	[TotalPrice][float] NOT NULL,
	[RequestId] [int] REFERENCES [Request](Id),
	[EmployeeId] [nvarchar] (255)REFERENCES [Employee](EmployeeId),
	[Status] bit
CONSTRAINT [PK_Quote] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [Quote]
ADD [PriceRate] [float] NOT NULL;
select * from [Quote]

/* Table [QuoteEmployee] */
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[QuoteEmployee](
	[QuoteId] [int] REFERENCES [Quote](Id),
	[EmployeeId] [nvarchar] (255) REFERENCES [Employee](EmployeeId)
CONSTRAINT [PK_QuoteEmployee] PRIMARY KEY CLUSTERED 
(
	[QuoteId] ASC,[EmployeeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
drop table [QuoteEmployee]

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


/* Table [Gemstone] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Gemstone](
	[GemId] [int] IDENTITY(1,1),
	[GemCode] [nvarchar](255) NOT NULL,
	[GemName] [nvarchar](255) NOT NULL,
	[Orgin] [nvarchar] (max) NOT NULL,
	[Proportions] [nvarchar](255) NOT NULL,
	[Polish] [nvarchar](255) NOT NULL,
	[Symmetry] [nvarchar](255) NOT NULL,
	[Fluorescence] [char](10) NOT NULL,
	[Status] [bit]
CONSTRAINT [PK_Gemstone] PRIMARY KEY CLUSTERED 
(
	[GemId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [GemPriceList] */
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GemPriceList] (
    [Id] [nvarchar] (255) NOT NULL,
	[Orgin] [nvarchar] (max) NOT NULL,
    [CaratWeight] [float] NOT NULL,
	[Color] [char](10) NOT NULL,
	[Clarity] [char](10) NOT NULL,
	[Cut] [nvarchar] (255) NOT NULL,
	[Price] [float] NOT NULL,
	[EffDate] [date] NOT NULL,
	[GemId] [int] REFERENCES [Gemstone](GemId),
CONSTRAINT [PK_GemPriceList] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [Material] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Material](
	[MaterialId] [nvarchar] (255) NOT NULL,
	[MaterialCode] [nvarchar] (255) NOT NULL,
	[MaterialName] [nvarchar](255) NOT NULL,
	[Weight] [float] NOT NULL,
	[Color] [char](10) NOT NULL,
	[Price] [float] NOT NULL,
	[EffDate] [date] NOT NULL
CONSTRAINT [PK_Material] PRIMARY KEY CLUSTERED 
(
	[MaterialId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [Collection] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Collection](
	[CollectionId] [nvarchar] (255) NOT NULL,
	[CollectionName] [nvarchar](255) NOT NULL,
	[Status] [bit] 
CONSTRAINT [PK_Collection] PRIMARY KEY CLUSTERED 
(
	[CollectionId] ASC
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
	[Description] [nvarchar](max),
	[Gender] [nvarchar](50) NOT NULL,
	[Size] [int] NOT NULL,
	[CategoryId] [nvarchar] (255) REFERENCES [Category](CategoryId),
	[MaterialId] [nvarchar] (255) REFERENCES [Material](MaterialId),
	[GemId] [int] REFERENCES [Gemstone](GemId),
	[SideMaterialCost] [float] NOT NULL,
	[SideGemCost] [float] NOT NULL,
	[ProductionCost] [float] NOT NULL,
	[CollectionId] [nvarchar] (255) REFERENCES [Collection](CollectionId)
CONSTRAINT [PK_Product] PRIMARY KEY CLUSTERED 
(
	[ProductId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

Alter table [Product]
add [Status] bit;

/* Table [Order] */
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order](
    [OrderId] [int] IDENTITY(1,1),
	[OrderCode] [nvarchar](255) NOT NULL,
    [Date] [date] NOT NULL,
	[Status] [bit],
	[QuoteId] [int]REFERENCES [Quote](Id),
	[CustomerId] [nvarchar] (255) REFERENCES [Customer](CustomerId)
CONSTRAINT [PK_Order] PRIMARY KEY CLUSTERED 
(
	[OrderId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [Feedback] */
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Feedback](
    [Id] [int] IDENTITY(1,1),
	[Description] [nvarchar](max) NOT NULL,
	[CustomerId] [nvarchar] (255) REFERENCES [Customer](CustomerId)
CONSTRAINT [PK_Feedback] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
alter table [Feedback] 
Add  [OrderId][int] REFERENCES [Order](OrderId)



