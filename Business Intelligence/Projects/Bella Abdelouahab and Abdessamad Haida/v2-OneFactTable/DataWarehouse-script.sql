USE [labMedAnalyseDW]
GO
/****** Object:  Table [dbo].[DimDate]    Script Date: 1/4/2024 9:10:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DimDate](
	[date_key] [date] NOT NULL,
	[jour] [int] NULL,
	[mois] [int] NULL,
	[year] [int] NULL,
 CONSTRAINT [PK_DimDate] PRIMARY KEY CLUSTERED 
(
	[date_key] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[DimDemographique]    Script Date: 1/4/2024 9:10:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DimDemographique](
	[demographique_key] [int] NOT NULL,
	[genre] [varchar](1) NULL,
	[age_category] [varchar](40) NULL,
 CONSTRAINT [PK_DimDemogrphique] PRIMARY KEY CLUSTERED 
(
	[demographique_key] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[DimExamen]    Script Date: 1/4/2024 9:10:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DimExamen](
	[examen_key] [int] NOT NULL,
	[code] [varchar](10) NULL,
 CONSTRAINT [PK_DimExamen] PRIMARY KEY CLUSTERED 
(
	[examen_key] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[DimModePayement]    Script Date: 1/4/2024 9:10:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DimModePayement](
	[mode_payement_key] [varchar](50) NOT NULL,
	[libelle_reduit] [varchar](20) NULL,
 CONSTRAINT [PK_DimModePayement] PRIMARY KEY CLUSTERED 
(
	[mode_payement_key] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[DimOrganisme]    Script Date: 1/4/2024 9:10:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DimOrganisme](
	[organisme_key] [int] NOT NULL,
	[nom] [varchar](45) NULL,
 CONSTRAINT [PK_DimOrganisme] PRIMARY KEY CLUSTERED 
(
	[organisme_key] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[facttest]    Script Date: 1/4/2024 9:10:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[facttest](
	[surrogate_key] [int] IDENTITY(1,1) NOT NULL,
	[organisme_key] [int] NULL,
	[demographique_key] [int] NULL,
	[CA] [real] NULL,
	[date_key] [date] NULL,
	[compte_key] [int] NULL,
	[facture_key] [int] NULL,
	[mode_payement_key] [varchar](50) NULL,
	[examen_key] [int] NULL,
	[demande_key] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[surrogate_key] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[facttest]  WITH CHECK ADD  CONSTRAINT [FK_facttest_DimDate] FOREIGN KEY([date_key])
REFERENCES [dbo].[DimDate] ([date_key])
GO
ALTER TABLE [dbo].[facttest] CHECK CONSTRAINT [FK_facttest_DimDate]
GO
ALTER TABLE [dbo].[facttest]  WITH CHECK ADD  CONSTRAINT [FK_facttest_DimDemographique] FOREIGN KEY([demographique_key])
REFERENCES [dbo].[DimDemographique] ([demographique_key])
GO
ALTER TABLE [dbo].[facttest] CHECK CONSTRAINT [FK_facttest_DimDemographique]
GO
ALTER TABLE [dbo].[facttest]  WITH CHECK ADD  CONSTRAINT [FK_facttest_DimExamen] FOREIGN KEY([examen_key])
REFERENCES [dbo].[DimExamen] ([examen_key])
GO
ALTER TABLE [dbo].[facttest] CHECK CONSTRAINT [FK_facttest_DimExamen]
GO
ALTER TABLE [dbo].[facttest]  WITH CHECK ADD  CONSTRAINT [FK_facttest_DimModePayement] FOREIGN KEY([mode_payement_key])
REFERENCES [dbo].[DimModePayement] ([mode_payement_key])
GO
ALTER TABLE [dbo].[facttest] CHECK CONSTRAINT [FK_facttest_DimModePayement]
GO
ALTER TABLE [dbo].[facttest]  WITH CHECK ADD  CONSTRAINT [FK_facttest_DimOrganisme] FOREIGN KEY([organisme_key])
REFERENCES [dbo].[DimOrganisme] ([organisme_key])
GO
ALTER TABLE [dbo].[facttest] CHECK CONSTRAINT [FK_facttest_DimOrganisme]
GO
