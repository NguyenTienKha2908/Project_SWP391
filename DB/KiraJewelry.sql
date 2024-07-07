
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
CREATE TABLE [dbo].[users](
	[user_id] [int] IDENTITY(1,1),
	[email] [varchar](255) NOT NULL,
	[password] [varchar](255) NOT NULL,
	[role_id] [int] NOT NULL,
	[status] bit NOT NULL,
CONSTRAINT [PK_user] PRIMARY KEY CLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


/* Table [Employee] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[employee](
	[employee_id] [nvarchar] (255) NOT NULL,
	[full_name] [nvarchar](255) NOT NULL,
	[user_id] [int] REFERENCES [Users]([User_Id]),
CONSTRAINT [PK_Employee] PRIMARY KEY CLUSTERED 
(
	[employee_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [Customer] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[customer](
	[customer_id] [nvarchar] (255) NOT NULL,
	[full_name] [nvarchar](255) NOT NULL,
	[address] [nvarchar](max) NOT NULL,
	[phone_Number] [varchar](10) NOT NULL,
	[user_id] [int] REFERENCES [users]([user_id]),
CONSTRAINT [PK_customer] PRIMARY KEY CLUSTERED 
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
CREATE TABLE [dbo].[category](
	[category_id] int identity(1,1) NOT NULL,
	[category_name] [nvarchar](255) NOT NULL,
	[status] [bit]  NOT NULL,
CONSTRAINT [PK_category] PRIMARY KEY CLUSTERED 
(
	[category_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [collection] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[collections](
	[collection_Id] int IDENTITY(1,1),
	[collection_Name] [nvarchar](255) NOT NULL,
	[status] [bit] NOT NULL,
	[Img_Url] varchar(255) NOT NULL,
CONSTRAINT [PK_collections] PRIMARY KEY CLUSTERED 
(
	[collection_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [Blogs] */
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[blogs](
    [blog_id] [int] IDENTITY(1,1),
	[title] varchar(100) NOT NULL,
	[content] [nvarchar](max) NOT NULL,
	[date] [date] NOT NULL,
	[employee_id] [nvarchar] (255) REFERENCES [Employee]([Employee_Id]),
	[status] bit NOT NULL,
	[img_url] varchar(255) NOT NULL,
CONSTRAINT [PK_Blogs] PRIMARY KEY CLUSTERED 
(
	[blog_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [material] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[material](
	[material_id] int IDENTITY(1,1),
	[material_code] [nvarchar] (255) NOT NULL,
	[material_name] [nvarchar](255) NOT NULL,
	[status] bit NOT NULL,
CONSTRAINT [PK_material] PRIMARY KEY CLUSTERED 
(
	[material_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [materialPriceList] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[material_price_list](
	[id] int IDENTITY(1,1),
	[price] [float] NOT NULL,
	[eff_date] [date] NOT NULL,
	[material_id] int REFERENCES [material]([material_id])
CONSTRAINT [PK_material_price_list] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [product] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product](
	[product_id] [int] IDENTITY(1,1),
	[product_code] [nvarchar] (max),
	[product_name] [nvarchar](255),
	[collection_id] int REFERENCES collections([collection_Id]),
	[category_id] int REFERENCES Category([Category_Id]),
	[description] [nvarchar](max),

	[gender] [nvarchar](50) NOT NULL,
	[size] [int] NOT NULL,

	[status] bit NOT NULL,
	--[Warranty_Card_Id] int,
	
CONSTRAINT [PK_product] PRIMARY KEY CLUSTERED 
(
	[product_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [productionOrder] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[production_order](
	[production_order_id] nvarchar(255),
	[date] datetime NOT NULL,

	[customer_id] [nvarchar] (255) REFERENCES [Customer](Customer_Id),
	[category_id] int REFERENCES [Category](Category_Id),
	[product_size] [int],

	[description] [nvarchar](255),

	[q_diamond_amount] float,
	[q_material_amount] float,
	[q_production_amount] float,
	[q_total_amount] float,

	[o_diamond_amount] float,
	[o_material_amount] float,
	[o_production_amount] float,
	[o_total_amount] float,

	[sales_staff_id] [nvarchar](255),
	[design_staff_id] [nvarchar](255),
	[production_staff_id] [nvarchar](255),

	[status] [nvarchar](50),  --Created,
							  --Requested, 
							  --Quoted (NAM) |Quoted (RJM)| -> Quoted (NAC) |Quoted (RJC)| ->  Quoted (NP), 
							  --Ordered
							  --Confirmed
							  --Completed
							  --Delivered
							  --Customized (In process) --> Canceled
							  --In Payment (Await payment)
							  --In Deliver (Await Deliver)
							  --Delivered 

	[product_id] int REFERENCES product([product_id])
CONSTRAINT [PK_production_Order] PRIMARY KEY CLUSTERED 
(
	[production_order_id] ASC
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





/* Table [productmaterial] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_material](
	product_Id int REFERENCES product([product_id]),
	material_id int REFERENCES material([material_id]),
	material_weight float,
	[q_price] float,
	[o_price] float,

CONSTRAINT [PK_product_material] PRIMARY KEY CLUSTERED 
(
	product_id ASC, material_id ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


/* Table [GemPriceList] */
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[diamond_price_list] (
    [id] [int] IDENTITY(1,1),
	[origin] [nvarchar] (255) NOT NULL,
    [carat_weight_from] [float] NOT NULL,
	[carat_weight_to] [float] NOT NULL,
	[color] [char](10) NOT NULL,
	[clarity] [char](10) NOT NULL,
	[cut] [nvarchar] (255) NOT NULL,
	[price] [float] NOT NULL,
	[eff_date] [date] NOT NULL,
	
CONSTRAINT [PK_diamond_price_list] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [diamond] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[diamond](
	[dia_id] [int] IDENTITY(1,1),
	[dia_code] [nvarchar](255) NOT NULL,
	[dia_name] [nvarchar](255) NOT NULL,

	[origin] [nvarchar] (255) NOT NULL,
    [carat_weight] [float] NOT NULL,
	[color] [char](10) NOT NULL,
	[clarity] [char](10) NOT NULL,
	[cut] [nvarchar] (255) NOT NULL,

	[proportions] [nvarchar](255) NOT NULL,
	[polish] [nvarchar](255) NOT NULL,
	[symmetry] [nvarchar](255) NOT NULL,
	[fluorescence] [char](10) NOT NULL,
	[status] [bit] NOT NULL, -- 1/Active | 0/Inactive (Used by some Pro)

	[q_price] float,
	[o_price] float,

	[product_id] int REFERENCES product([product_id])

CONSTRAINT [PK_diamond] PRIMARY KEY CLUSTERED 
	(
		[dia_id] ASC
	) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
	--CONSTRAINT [FK_diamond_product] FOREIGN KEY ([product_Id]) REFERENCES [dbo].[product] ([product_Id])
) ON [PRIMARY]
GO

/* Table [productDesignShell] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_design_shell](
	[product_design_shell_id] int IDENTITY(1,1),
	[material_id] int,
	[weight] float,
	
CONSTRAINT [PK_product_design_shell] PRIMARY KEY CLUSTERED 
(
	[product_design_shell_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/* Table [productDesign] */ 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_design](
	[product_design_id] [int] IDENTITY(1,1),
	[product_design_code] [nvarchar] (max) NOT NULL,
	[product_design_name] [nvarchar](255) NOT NULL,
	[category_id] int REFERENCES [category](category_id),
	[collection_id] int REFERENCES [collections](collection_id),
	[description] [nvarchar](max),

	[gender] [nvarchar](50),
	[product_size] [int],

	[product_design_shell_id] int REFERENCES [product_design_shell]([product_design_shell_id]),
	[gem_min_size] float,
	[gem_max_size] float,

	[status] bit NOT NULL,
	[product_id] int REFERENCES product([product_id])
CONSTRAINT [PK_product_design] PRIMARY KEY CLUSTERED 
(
	[product_design_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/*================ INSERT DATA ================*/

--Insert data into the Users table
SET IDENTITY_INSERT [users] ON;
select * from Users
--delete from production_Order
INSERT INTO [dbo].[users] (user_id, email, password, role_id, status)
VALUES

-- 10 Customers
(1, 'customer1@example.com', 'Cust1#Secure', 1, 1),
(2, 'customer2@example.com', 'Cust2*Strong', 1, 1),
(3, 'customer3@example.com', 'Cust3@Safe!', 1, 0),
(4, 'customer4@example.com', 'Cust4%Tough', 1, 1),
(5, 'customer5@example.com', 'Cust5@Solid', 1, 1),
(6, 'customer6@example.com', 'Cust6&Hard!', 1, 0),
(7, 'customer7@example.com', 'Cust7!Secure', 1, 1),
(8, 'customer8@example.com', 'Cust8#Strong', 1, 1),
(9, 'customer9@example.com', 'Cust9*Safe!', 1, 0),
(10, 'customer10@example.com', 'Cust10%Tough', 1, 1),
(11, 'truongantnh@gmail.com', 'andhtSE180230@fpt', 1, 1),
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
-- 4 production Staff
(28, 'prod1@example.com', 'Prod1#Make!', 6, 1),
(29,'prod2@example.com', 'Prod2*Build', 6, 1),
(30, 'prod3@example.com', 'Prod3@Form!', 6, 1),
(31, 'prod4@example.com', 'Prod4%Forge', 6, 1);
SET IDENTITY_INSERT [users] OFF;

-- Insert data into the Employee table
INSERT INTO [dbo].[employee] (employee_id, full_name, user_id)
VALUES 
-- Admins
('AD001', 'Alice Johnson', 12),
('AD002', 'Bob Smith', 13),
('AD003', 'Charlie Brown', 14),
('AD004', 'diana White', 15),
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
-- production Staff
('PR001', 'Quinn Baker', 28),
('PR002', 'Rachel Carter', 29),
('PR003', 'Sam Edwards', 30),
('PR004', 'Tina Flores', 31);

-- Insert data into the Customer table
INSERT INTO [dbo].[customer] (customer_id, full_name, address, phone_number, user_id)
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


SET IDENTITY_INSERT [category] ON;
-- Insert data into Category table
INSERT INTO [dbo].[category] (category_id, category_name, status) VALUES
(1, 'ring', 1),
(2, 'necklace', 1),
(3, 'earrings', 1),
(4, 'bracelet', 1),
(5, 'cufflinks', 1);

SET IDENTITY_INSERT [category] OFF

SET IDENTITY_INSERT [blogs] ON;
INSERT INTO [dbo].[blogs] (blog_id, title, content, date, employee_id, status, Img_Url) VALUES
(1, 'The Brilliance of diamonds', 
 'Explore the mesmerizing brilliance of diamonds, discussing their formation, characteristics, and symbolism in various cultures. Highlight the unique features that make diamonds a timeless choice for jewelry.', 
 '2024-05-27', 'SS001', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Blogs%2FDefault_generate_for_me_a_picture_of_Explore_the_mesmerizing_b_1.jpg?alt=media&token=3e64a95d-6782-461f-85e8-74cf786fc1d5'),

(2, 'Crafting Beauty: The Art of Gold Jewelry Making', 
 'Delve into the craftsmanship behind gold jewelry, showcasing the intricate techniques used by artisans to create stunning pieces. Discuss the versatility of gold and its significance in different jewelry designs.', 
 '2024-05-28', 'DE001', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Blogs%2FDefault_generate_for_me_a_picture_of_Delve_into_the_craftsmans_0.jpg?alt=media&token=27de7360-b0d0-412c-955d-61371d27aaa1'),

(3, 'Unveiling the Mystique of Precious diamonds', 
 'Take your readers on a journey through the world of precious diamonds. Explore their origins, colors, and cultural meanings, offering insights into why these diamonds are treasured.', 
 '2024-05-29', 'SS003', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Blogs%2FDefault_generate_for_me_a_picture_of_a_journey_through_the_wor_0.jpg?alt=media&token=00672747-51f2-4d57-b843-0343cdfa04e6'),

(4, 'From Mine to Market: The diamond Supply Chain', 
 'Shed light on the journey of diamonds from the mines to the market, discussing ethical sourcing practices, sustainability efforts, and the role of certification in ensuring transparency and integrity.', 
 '2024-05-30', 'PR001', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Blogs%2FDefault_generate_for_me_a_picture_of_The_diamond_Supply_Chain_0.jpg?alt=media&token=0b045f91-d503-44ef-acaf-a4cb53c5b524'),

(5, 'The Language of Jewelry: Symbols and Meanings', 
 'Explore the symbolic meanings behind common jewelry motifs such as hearts, infinity symbols, and flowers. Discuss how these symbols resonate with individuals and convey messages of love, hope, and empowerment.', 
 '2024-06-01', 'DE003', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Blogs%2FDefault_generate_for_me_a_picture_of_symbolic_meanings_behind_0.jpg?alt=media&token=d7e1fc68-3fac-4ed0-b58e-4cadf2f64b1a');

SET IDENTITY_INSERT [blogs] OFF
SET IDENTITY_INSERT [collections] ON;
 -- Insert collections into the collection table
INSERT INTO [dbo].[collections] (collection_id, collection_name, status, img_url) VALUES
(1, 'Summer collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/collections%2F1A-3.jpg?alt=media&token=b921cd83-f8d5-4b8b-a8e5-9b49d7c184b4'),
(2, 'Spring collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/collections%2F2.jpg?alt=media&token=12c89fa6-c177-404f-a957-a54309ce8aa5'),
(3, 'Winter collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/collections%2F2022-10-2910.13.16_e3d0212c-a298-4ed4-a661-1aee76c86592_1500x.jpg?alt=media&token=f20b895b-2539-45ae-8b9f-46864f170896'),
(4, 'Autumn collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/collections%2F893e69de69123ff7862ff40d56999db0%20(1).jpg?alt=media&token=4e742a68-b296-4dc1-810d-459836674748'),
(5, 'Wedding collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/collections%2F893e69de69123ff7862ff40d56999db0.jpg?alt=media&token=6335f0bd-e19b-4db0-9aa3-5b15c8e7448e'),
(6, 'Wildcard collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/collections%2FDefault_A_closeup_photo_of_a_summer_collection_of_gold_jewelry_2.jpg?alt=media&token=1e38754a-7b5c-4fde-a91c-ee82e2dddc42'),
(7, 'Galaxy collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/collections%2Fimg_proxy_7420ce1a-960b-414c-a928-b427c54582d7.jpg?alt=media&token=dc9d6ed1-16ce-4548-8d39-b764ec1eb7a8'),
(8, 'Mountain collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/collections%2Fil_794xN.2261231212_j1mk_7c7bfe28-fbb7-46ac-8db9-e705d39128f9.jpg?alt=media&token=d8c79722-3a9e-46c6-bddc-9570f3b71583'),
(9, 'Ocean collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/collections%2Fcd094e599243fa7e760e1fc0323fd1e9.jpg?alt=media&token=39319424-92e5-4b50-909f-a6706a574a84'),
(10, 'Disney collection', 1, 'https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/collections%2FT098_grande.jpg?alt=media&token=f3a03997-8958-4632-af12-fe52971e84c9');

SET IDENTITY_INSERT [collections] OFF
SET IDENTITY_INSERT [material] ON;
-- Insert data into material table
INSERT INTO [dbo].[material] (material_id, material_code, material_name, status)
VALUES
    (1, 'SJC', 'Gold Piece SJC 999.9', 1),
    (2, 'RingPNJ', 'PNJ 999.9', 1),
    (3, 'KBGold', 'Kim Bao Gold 999.9', 1),
    (4, 'PLTGold', 'Phuc Loc Tai Gold 999.9', 1),
    (5, 'NT999.9', 'Nu Trang Gold 999.9', 1),
    (6, 'NT999', 'Nu Trang Gold 999', 1),
    (7, 'NT99', 'Nu Trang Gold 99', 1),
    (8, 'Gold18k', 'Gold 750', 1),
    (9, 'Gold14k', 'Gold 585', 1),
    (10, 'Gold10k', 'Gold 416', 1),
	(11, 'GoldPNJ', 'Phuong Hoang Gold', 1),
    (12, 'Gold22K', 'Gold 916', 1),
    (13, 'Gold15.6K', 'Gold 650', 1),
    (14, 'Gold16.3K', 'Gold 680', 1),
    (15, 'Gold14.6', 'Gold 610', 1),
    (16, 'Gold9k', 'Gold 375', 1),
	(17, 'Gold8k', 'Gold 333', 1);

/*
-- Insert data into materialPriceList table
DECLARE @StartDate DATE = '2024-07-05'; -- Start date for generating data
DECLARE @EndDate DATE = '2024-07-06';   -- End date for generating data

WHILE @StartDate <= @EndDate
BEGIN
    -- Gold prices for each type of gold on the current date
    INSERT INTO [dbo].[material_Price_List] ([Price], [Eff_Date], [material_Id])
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
END;*/

-- Insert data for [GemPriceList] table (diamond prices for different dates)
DECLARE @StartDateGem DATE = '2024-06-17'; -- Start date for generating data
DECLARE @EndDateGem DATE = '2024-06-17';   -- End date for generating data
-- Loop through dates to generate data for each day
WHILE @StartDateGem <= @EndDateGem
BEGIN
    -- Generate data for GemPriceList table (diamond prices for different dates)
INSERT INTO [dbo].[diamond_Price_List] (origin, carat_weight_from, carat_weight_to, color, clarity, cut, price, eff_date)
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

SET IDENTITY_INSERT [material] OFF
SET IDENTITY_INSERT [diamond] ON;
select * from diamond_Price_List

-- Insert data into the [diamond] table
INSERT INTO [dbo].[diamond] (dia_id, dia_code, dia_name, origin, carat_weight, color, clarity, cut, proportions, polish, symmetry, fluorescence, status, q_price, o_price)
VALUES
-- Gemstones with D Color
(1, 'dia001', 'Round Brilliant Cut diamond', 'Natural', 1.85, 'D', 'IF', 'Shallow', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 243, 1),
(2, 'dia002', 'Round Brilliant Cut diamond', 'Natural', 0.49, 'D', 'VVS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 33, 1),
(3, 'dia003', 'Round Brilliant Cut diamond', 'Natural', 2.99, 'E', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 300, 1),
(4, 'dia004', 'Round Brilliant Cut diamond', 'Natural', 2.75, 'J', 'VVS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 110, 1),
(5, 'dia005', 'Round Brilliant Cut diamond', 'Natural', 2.99, 'D', 'VVS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 260, 1),
 
-- Gemstones with E Color
(6, 'dia006', 'Round Brilliant Cut diamond', 'Natural', 4.5, 'E', 'VVS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 550, 1),
(7, 'dia007', 'Round Brilliant Cut diamond', 'Natural', 4.25, 'D', 'VS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 535, 1),
(8, 'dia008', 'Round Brilliant Cut diamond', 'Natural', 3.25, 'F', 'VVS1', 'Shallow', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 425, 1),
(9, 'dia009', 'Round Brilliant Cut diamond', 'Natural', 10, 'E', 'VS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 875, 1),
(10, 'dia010', 'Round Brilliant Cut diamond', 'Natural', 2.21, 'E', 'VS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 175, 1),

-- Gemstones with F Color
(11, 'dia011', 'Round Brilliant Cut diamond', 'Natural', 2.5, 'E', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 300, 1),
(12, 'dia012', 'Round Brilliant Cut diamond', 'Natural', 3.5, 'E', 'VVS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 485, 1),
(13, 'dia013', 'Round Brilliant Cut diamond', 'Natural', 1.5, 'J', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 85, 1),
(14, 'dia014', 'Round Brilliant Cut diamond', 'Natural', 1.25, 'D', 'VS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 108, 1),
(15, 'dia015', 'Round Brilliant Cut diamond', 'Natural', 5.5, 'F', 'VVS1', 'Shallow', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 715, 1),

-- Gemstones with J Color
(16, 'dia016', 'Round Brilliant Cut diamond', 'Natural', 2.25, 'J', 'VVS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 110, 1),
(17, 'dia017', 'Round Brilliant Cut diamond', 'Natural', 5.25, 'J', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 300, 1),
(18, 'dia018', 'Round Brilliant Cut diamond', 'Natural', 10.25, 'D', 'VVS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 430, 1),
(19, 'dia019', 'Round Brilliant Cut diamond', 'Natural', 3.25, 'J', 'VSS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 225, 1),
(20, 'dia020', 'Round Brilliant Cut diamond', 'Natural', 4.75, 'J', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 230, 1),

(21, 'dia021', 'Round Brilliant Cut diamond', 'Natural', 0.45, 'J', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 22, 1),
(22, 'dia022', 'Round Brilliant Cut diamond', 'Natural', 0.75, 'D', 'VVS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 58, 1),
(23, 'dia023', 'Round Brilliant Cut diamond', 'Natural', 3.55, 'D', 'VS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 400, 1),
(24, 'dia024', 'Round Brilliant Cut diamond', 'Natural', 2.65, 'F', 'VS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 175, 1),
(25, 'dia025', 'Round Brilliant Cut diamond', 'Natural', 5.34, 'D', 'VS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 835, 1),
-- Gemstones with E Color
(26, 'dia026', 'Round Brilliant Cut diamond', 'Natural', 3.45, 'F', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 450, 1),
(27, 'dia027', 'Round Brilliant Cut diamond', 'Natural', 3.35, 'J', 'VVS1', 'Shallow', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 225, 1),
(28, 'dia028', 'Round Brilliant Cut diamond', 'Natural', 4.53, 'E', 'VVS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 550, 1),
(29, 'dia029', 'Round Brilliant Cut diamond', 'Natural', 2.75, 'E', 'VS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 175, 1),
(30, 'dia030', 'Round Brilliant Cut diamond', 'Natural', 1.55, 'D', 'VS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 126, 1),

-- Gemstones with F Color
(31, 'dia031', 'Round Brilliant Cut diamond', 'Natural', 3.45, 'F', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 450, 1),
(32, 'dia032', 'Round Brilliant Cut diamond', 'Natural', 4.87, 'E', 'VVS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 550, 1),
(33, 'dia033', 'Round Brilliant Cut diamond', 'Natural', 4.99, 'J', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 230, 1),
(34, 'dia034', 'Round Brilliant Cut diamond', 'Natural', 5.99, 'F', 'VVS1', 'Shallow', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 715, 1),
(35, 'dia015', 'Round Brilliant Cut diamond', 'Natural', 5.99, 'D', 'VS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 835, 1),

-- Gemstones with J Color
(36, 'dia036', 'Round Brilliant Cut diamond', 'Natural', 1.75, 'J', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 58, 1),
(37, 'dia037', 'Round Brilliant Cut diamond', 'Natural', 2.75, 'J', 'VVS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 110, 1),
(38, 'dia038', 'Round Brilliant Cut diamond', 'Natural', 3.5, 'F', 'VVS1', 'Shallow', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 425, 1),
(39, 'dia039', 'Round Brilliant Cut diamond', 'Natural', 4.3, 'D', 'VS1', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 535, 1),
(40, 'dia040', 'Round Brilliant Cut diamond', 'Natural', 4.99, 'J', 'IF', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 230, 1),

-- Gemstones with D Color
(41, 'dia041', 'Round Brilliant Cut diamond', 'Natural', 0.85, 'F', 'VS1', 'Shallow', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 45, 1),
(42, 'dia042', 'Round Brilliant Cut diamond', 'Natural', 2.5, 'D', 'VVS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 260, 1),
(43, 'dia043', 'Round Brilliant Cut diamond', 'Natural', 10.5, 'D', 'VVS2', 'Ideal', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 1300, 1),
(44, 'dia044', 'Round Brilliant Cut diamond', 'Natural', 3.5, 'F', 'VVS1', 'Shallow', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 425, 1),
(45, 'dia045', 'Round Brilliant Cut diamond', 'Natural', 10.5, 'F', 'VS1', 'Shallow', 'Ideal', 'Excellent', 'Excellent', 'None', 1, 875, 1);
SET IDENTITY_INSERT [diamond] OFF;

-- Insert data into the [product] table
SET IDENTITY_INSERT [product] ON;
INSERT INTO [dbo].[product] (product_id, product_code, product_name, collection_id, category_id, description, gender, size, status)
Values
(1, 'PO00001', 'Customized Ring', null, 1, 'Beautiful crafted customized ring for your lovely wife', 'female', 7, 1),
(2, 'PO00002', 'Customized Ring', null, 1, 'Beautiful crafted customized ring for your lovely wife', 'female', 7, 0);

SET IDENTITY_INSERT [product] OFF;

-- Insert data into the [production_Order] table
INSERT INTO [dbo].[production_order] (production_order_id, date, customer_id, category_id, product_size, description, 
	q_diamond_amount,
	q_material_amount,
	q_production_amount,
	q_total_amount,
	o_diamond_amount,
	o_material_amount,
	o_production_amount,
	o_total_amount,
	sales_staff_id,
	design_staff_id,
	production_staff_id,
	product_id,
	Status)
Values
('POI001', '2024-7-1', 'CUS001', 1, 7, 'Beautiful crafted customized ring for your lovely wife', 300, 92.65, 100, 492.65, 300, 92.65, 100, 492.65, 'SS001', 'DE001', 'PR001', 1, 'Delivered'),
('POI002', '2024-7-3', 'CUS001', 1, 7, 'Beautiful crafted customized ring for your lovely wife', 110, 92.65, 100, 282.65, 110, 92.65, 100, 282.65, 'SS001', 'DE001', NULL, 2, 'Ordered');


-- Insert data into the [productmaterial] table
INSERT INTO [dbo].[product_material] (product_id, material_id, material_weight, q_price, o_price) 
Values
(1, 1, 1, 92.65, 92.65),
(2, 1, 1, 92.65, 92.65);

-- UPDATE THE diaMOND USED!
update diamond set product_Id = 1, status = 0, o_price = 300
where dia_id = 3
update diamond set product_Id = 2, status = 0, o_price = 1
where dia_id = 4


SET IDENTITY_INSERT [product_design_shell] ON;
-- Insert data into the productDesignShell table
INSERT INTO [dbo].[product_design_shell] (product_design_shell_id, material_id, weight)
VALUES

-- Gold 10K (materialId = 3)
(1, 3, 1),
(2, 3, 2),
(3, 3, 3),
(4, 3, 4),

-- Gold 15.6K (materialId = 5)
(5, 5, 1),
(6, 5, 2),
(7, 5, 3),
(8, 5, 4),

-- Gold 18K (materialId = 7)
(9, 7, 1),
(10, 7, 2),
(11, 7, 3),
(12, 7, 4),

-- Gold 22K (materialId = 8)
(13, 8, 1),
(14, 8, 2),
(15, 8, 3),
(16, 8, 4),

-- SJC Gold Piece (materialId = 9)
(17, 9, 1),
(18, 9, 2),
(19, 9, 3),
(20, 9, 4),

-- PNJ Ring Gold 24K (materialId = 10)
(21, 10, 1),
(22, 10, 2),
(23, 10, 3),
(24, 10, 4);
SET IDENTITY_INSERT [product_design_shell] OFF;



select * from users
select * from customer
select * from employee
select * from category
select * from blogs
select * from collections
select * from material
select * from material_price_list
select * from diamond_price_list
select * from diamond
select * from product
select * from production_order
select * from product_design_shell
select * from product_design
select * from product_material
