


CREATE DATABASE MedLabDW
USE MedLabDW

CREATE TABLE [DimCompte](
	[id_compte] [int] NOT NULL,
	[profil] [nvarchar](2) NOT NULL,
	[actif] [bit] NOT NULL,
	[acces_externe] [bit] NOT NULL,
 CONSTRAINT [PK_DimCompte] PRIMARY KEY CLUSTERED 
(
	[id_compte] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [DimDate]    Script Date: 12/30/2023 11:17:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DimDate](
	[id_date] [date] NOT NULL,
	[jour] [smallint] NOT NULL,
	[mois] [smallint] NOT NULL,
	[annee] [smallint] NOT NULL,
 CONSTRAINT [PK_DimDate] PRIMARY KEY CLUSTERED 
(
	[id_date] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [DimExamen]    Script Date: 12/30/2023 11:17:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DimExamen](
	[id_examen] [int] NOT NULL,
	[code] [nvarchar](45) NOT NULL,
	[designation] [nvarchar](50) NOT NULL,
	[delai] [smallint] NOT NULL,
	[etat] [bit] NOT NULL,
 CONSTRAINT [PK_DimExamen] PRIMARY KEY CLUSTERED 
(
	[id_examen] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [DimModePaiement]    Script Date: 12/30/2023 11:17:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DimModePaiement](
	[mode_paiement] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_DimModePaiement] PRIMARY KEY CLUSTERED 
(
	[mode_paiement] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [DimOrganisme]    Script Date: 12/30/2023 11:17:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DimOrganisme](
	[id_organisme] [int] NOT NULL,
	[nom] [nvarchar](45) NOT NULL,
	[specialite] [nvarchar](50) NULL,
 CONSTRAINT [PK_DimOrganisme] PRIMARY KEY CLUSTERED 
(
	[id_organisme] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [DimPatient]    Script Date: 12/30/2023 11:17:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DimPatient](
	[id_patient] [int] NOT NULL,
	[genre] [nvarchar](2) NOT NULL,
	[tranche_age] [nvarchar](45) NOT NULL,
 CONSTRAINT [PK_DimPatient] PRIMARY KEY CLUSTERED 
(
	[id_patient] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [FactDemande]    Script Date: 12/30/2023 11:17:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [FactDemande](
	[id_date] [date] NOT NULL,
	[id_compte] [int] NOT NULL,
	[id_patient] [int] NOT NULL,
	[id_organisme] [int] NULL,
	[chiffre_affaire] [real] NOT NULL,
	[nbr_demande] [numeric](18, 0) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [FactExamen]    Script Date: 12/30/2023 11:17:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [FactExamen](
	[id_date] [date] NOT NULL,
	[id_examen] [int] NOT NULL,
	[id_organisme] [int] NULL,
	[chiffre_affaire] [real] NOT NULL,
	[nbr_demande] [numeric](18, 0) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [FactModePaiement]    Script Date: 12/30/2023 11:17:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [FactModePaiement](
	[mode_paiement] [nvarchar](20) NOT NULL,
	[id_organisme] [int] NOT NULL,
	[chiffre_affaire] [int] NOT NULL,
	[nbr_demande] [bigint] NOT NULL,
 CONSTRAINT [PK_FactModePaiement] PRIMARY KEY CLUSTERED 
(
	[mode_paiement] ASC,
	[id_organisme] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [FactDemande]  WITH NOCHECK ADD  CONSTRAINT [FK_FactDemande_DimCompte] FOREIGN KEY([id_compte])
REFERENCES [DimCompte] ([id_compte])
GO
ALTER TABLE [FactDemande] CHECK CONSTRAINT [FK_FactDemande_DimCompte]
GO
ALTER TABLE [FactDemande]  WITH NOCHECK ADD  CONSTRAINT [FK_FactDemande_DimDate] FOREIGN KEY([id_date])
REFERENCES [DimDate] ([id_date])
GO
ALTER TABLE [FactDemande] CHECK CONSTRAINT [FK_FactDemande_DimDate]
GO
ALTER TABLE [FactDemande]  WITH CHECK ADD  CONSTRAINT [FK_FactDemande_DimOrganisme] FOREIGN KEY([id_organisme])
REFERENCES [DimOrganisme] ([id_organisme])
GO
ALTER TABLE [FactDemande] CHECK CONSTRAINT [FK_FactDemande_DimOrganisme]
GO
ALTER TABLE [FactDemande]  WITH CHECK ADD  CONSTRAINT [FK_FactDemande_DimPatient] FOREIGN KEY([id_patient])
REFERENCES [DimPatient] ([id_patient])
GO
ALTER TABLE [FactDemande] CHECK CONSTRAINT [FK_FactDemande_DimPatient]
GO
ALTER TABLE [FactExamen]  WITH CHECK ADD  CONSTRAINT [FK_FactExamen_DimDate1] FOREIGN KEY([id_date])
REFERENCES [DimDate] ([id_date])
GO
ALTER TABLE [FactExamen] CHECK CONSTRAINT [FK_FactExamen_DimDate1]
GO
ALTER TABLE [FactExamen]  WITH NOCHECK ADD  CONSTRAINT [FK_FactExamen_DimExamen] FOREIGN KEY([id_examen])
REFERENCES [DimExamen] ([id_examen])
GO
ALTER TABLE [FactExamen] CHECK CONSTRAINT [FK_FactExamen_DimExamen]
GO
ALTER TABLE [FactExamen]  WITH CHECK ADD  CONSTRAINT [FK_FactExamen_DimOrganisme] FOREIGN KEY([id_organisme])
REFERENCES [DimOrganisme] ([id_organisme])
GO
ALTER TABLE [FactExamen] CHECK CONSTRAINT [FK_FactExamen_DimOrganisme]
GO
ALTER TABLE [FactModePaiement]  WITH NOCHECK ADD  CONSTRAINT [FK_FactModePaiement_DimModePaiement] FOREIGN KEY([mode_paiement])
REFERENCES [DimModePaiement] ([mode_paiement])
GO
ALTER TABLE [FactModePaiement] CHECK CONSTRAINT [FK_FactModePaiement_DimModePaiement]
GO
ALTER TABLE [FactModePaiement]  WITH NOCHECK ADD  CONSTRAINT [FK_FactModePaiement_DimOrganisme] FOREIGN KEY([id_organisme])
REFERENCES [DimOrganisme] ([id_organisme])
GO
ALTER TABLE [FactModePaiement] CHECK CONSTRAINT [FK_FactModePaiement_DimOrganisme]
GO
USE [master]
GO
ALTER DATABASE [MedLabDW] SET  READ_WRITE 
GO
