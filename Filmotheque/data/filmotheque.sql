--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.4
-- Dumped by pg_dump version 9.6.4

-- Started on 2018-09-29 21:06:57

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12387)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2196 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- TOC entry 197 (class 1255 OID 16509)
-- Name: journaliser(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION journaliser() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE 
	description text;
    objetAvant text;
    objetApres text;
    operation text;
BEGIN
	objetAvant := '';
	objetApres := '';

	IF TG_OP = 'INSERT' THEN
    	objetAvant := '{}';
   		objetApres := '{'||NEW.titre||','||NEW.description||','||NEW.genre||','||NEW.date_de_sortie||','||NEW.duree||'}';
        operation := 'AJOUTER';
    END IF;

	IF TG_OP = 'UPDATE' THEN
    	objetAvant := '{'||OLD.titre||','||OLD.description||','||OLD.genre||','||OLD.date_de_sortie||','||OLD.duree||'}';
   		objetApres := '{'||NEW.titre||','||NEW.description||','||NEW.genre||','||NEW.date_de_sortie||','||NEW.duree||'}';
        operation := 'MODIFIER';
    END IF;

	IF TG_OP = 'DELETE' THEN
    	objetAvant := '{'||OLD.titre||','||OLD.description||','||OLD.genre||','||OLD.date_de_sortie||','||OLD.duree||'}';
    	objetApres := '{}';
        operation := 'EFFACER';
    END IF;

	description := objetAvant || ' -> ' || objetApres;
    
    INSERT into journal(moment, operation, objet, description) VALUES(NOW(), operation, 'film', description);
    
	IF TG_OP = 'DELETE' THEN
		return OLD;
	END IF; 
    
    return NEW;
END
$$;


ALTER FUNCTION public.journaliser() OWNER TO postgres;

--
-- TOC entry 210 (class 1255 OID 16635)
-- Name: surveillance(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION surveillance() RETURNS void
    LANGUAGE plpgsql
    AS $$DECLARE
	moment timestamp with time zone;

	-- surveillanceFilm
    nombreFilm int;
    moyenneDuree real;
    checksumTitre text;

	-- surveillanceActeur
	nombreActeur int;
    moyenneTaille real;
    checksumNom text;
    
    -- surveillanceActeurParFilm
    nombreActeurParFilm int;
    moyenneTailleParFilm real;
    checksumNomParFilm text;
    ligneFilm film%rowtype;
BEGIN
	moment := NOW();

	-- surveillanceFilm
	SELECT COUNT(*), AVG(duree), md5(string_agg(film.titre::text, '' ORDER BY id)) INTO nombreFilm, moyenneDuree, checksumTitre FROM film;
    INSERT INTO "surveillanceFilm" (moment, "nombreFilm", "moyenneDuree", "checksumTitre") VALUES(moment, nombreFilm, moyenneDuree, checksumTitre);
    
    -- surveillanceActeur
    SELECT COUNT(*), AVG(taille), md5(string_agg(acteur.nom::text, '' ORDER BY id)) INTO nombreActeur, moyenneTaille, checksumNom FROM acteur;
    INSERT INTO "surveillanceActeur" (moment, "nombreActeur", "moyenneTaille", "checksumNom") VALUES(moment, nombreActeur, moyenneTaille, checksumNom);
    
    -- surveillanceActeurParFilm
    FOR ligneFilm IN SELECT * FROM film LOOP
    	SELECT COUNT(*), AVG(taille), md5(string_agg(acteur.nom::text, '' ORDER BY id)) INTO nombreActeurParFilm, moyenneTailleParFilm, checksumNomParFilm FROM acteur WHERE id_film = ligneFilm.id;
    	INSERT INTO "surveillanceActeurParFilm"(moment, "nombreActeur", "moyenneTaille", "checksumNom") VALUES(moment, nombreActeurParFilm, moyenneTailleParFilm, checksumNomParFilm);
    END LOOP;
END$$;


ALTER FUNCTION public.surveillance() OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 188 (class 1259 OID 16435)
-- Name: acteur; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE acteur (
    id bigint NOT NULL,
    nom text,
    naissance text,
    nationalite text,
    id_film integer,
    taille integer
);


ALTER TABLE acteur OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 16433)
-- Name: acteur_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE acteur_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE acteur_id_seq OWNER TO postgres;

--
-- TOC entry 2197 (class 0 OID 0)
-- Dependencies: 187
-- Name: acteur_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE acteur_id_seq OWNED BY acteur.id;


--
-- TOC entry 185 (class 1259 OID 16422)
-- Name: film; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE film (
    titre text,
    description text,
    genre text,
    date_de_sortie text,
    id bigint NOT NULL,
    duree integer
);


ALTER TABLE film OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 16428)
-- Name: film_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE film_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE film_id_seq OWNER TO postgres;

--
-- TOC entry 2198 (class 0 OID 0)
-- Dependencies: 186
-- Name: film_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE film_id_seq OWNED BY film.id;


--
-- TOC entry 189 (class 1259 OID 16455)
-- Name: journal; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE journal (
    moment timestamp with time zone NOT NULL,
    operation text NOT NULL,
    description text,
    objet text NOT NULL,
    id bigint NOT NULL
);


ALTER TABLE journal OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 16540)
-- Name: journal_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE journal_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE journal_id_seq OWNER TO postgres;

--
-- TOC entry 2199 (class 0 OID 0)
-- Dependencies: 193
-- Name: journal_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE journal_id_seq OWNED BY journal.id;


--
-- TOC entry 191 (class 1259 OID 16523)
-- Name: surveillanceActeur; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "surveillanceActeur" (
    moment timestamp with time zone,
    "nombreActeur" integer,
    "moyenneTaille" real,
    "checksumNom" text,
    id bigint NOT NULL
);


ALTER TABLE "surveillanceActeur" OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 16531)
-- Name: surveillanceActeurParFilm; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "surveillanceActeurParFilm" (
    moment timestamp with time zone,
    "nombreActeur" integer,
    "moyenneTaille" real,
    "checksumNom" text,
    id bigint NOT NULL
);


ALTER TABLE "surveillanceActeurParFilm" OWNER TO postgres;

--
-- TOC entry 195 (class 1259 OID 16563)
-- Name: surveillanceActeurParFilm_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "surveillanceActeurParFilm_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "surveillanceActeurParFilm_id_seq" OWNER TO postgres;

--
-- TOC entry 2200 (class 0 OID 0)
-- Dependencies: 195
-- Name: surveillanceActeurParFilm_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "surveillanceActeurParFilm_id_seq" OWNED BY "surveillanceActeurParFilm".id;


--
-- TOC entry 194 (class 1259 OID 16551)
-- Name: surveillanceActeur_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "surveillanceActeur_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "surveillanceActeur_id_seq" OWNER TO postgres;

--
-- TOC entry 2201 (class 0 OID 0)
-- Dependencies: 194
-- Name: surveillanceActeur_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "surveillanceActeur_id_seq" OWNED BY "surveillanceActeur".id;


--
-- TOC entry 190 (class 1259 OID 16515)
-- Name: surveillanceFilm; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "surveillanceFilm" (
    moment timestamp with time zone,
    "nombreFilm" integer,
    "moyenneDuree" real,
    "checksumTitre" text,
    id bigint NOT NULL
);


ALTER TABLE "surveillanceFilm" OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 16575)
-- Name: surveillanceFilm_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "surveillanceFilm_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "surveillanceFilm_id_seq" OWNER TO postgres;

--
-- TOC entry 2202 (class 0 OID 0)
-- Dependencies: 196
-- Name: surveillanceFilm_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "surveillanceFilm_id_seq" OWNED BY "surveillanceFilm".id;


--
-- TOC entry 2040 (class 2604 OID 16438)
-- Name: acteur id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acteur ALTER COLUMN id SET DEFAULT nextval('acteur_id_seq'::regclass);


--
-- TOC entry 2039 (class 2604 OID 16430)
-- Name: film id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY film ALTER COLUMN id SET DEFAULT nextval('film_id_seq'::regclass);


--
-- TOC entry 2041 (class 2604 OID 16542)
-- Name: journal id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY journal ALTER COLUMN id SET DEFAULT nextval('journal_id_seq'::regclass);


--
-- TOC entry 2043 (class 2604 OID 16553)
-- Name: surveillanceActeur id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "surveillanceActeur" ALTER COLUMN id SET DEFAULT nextval('"surveillanceActeur_id_seq"'::regclass);


--
-- TOC entry 2044 (class 2604 OID 16565)
-- Name: surveillanceActeurParFilm id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "surveillanceActeurParFilm" ALTER COLUMN id SET DEFAULT nextval('"surveillanceActeurParFilm_id_seq"'::regclass);


--
-- TOC entry 2042 (class 2604 OID 16577)
-- Name: surveillanceFilm id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "surveillanceFilm" ALTER COLUMN id SET DEFAULT nextval('"surveillanceFilm_id_seq"'::regclass);


--
-- TOC entry 2181 (class 0 OID 16435)
-- Dependencies: 188
-- Data for Name: acteur; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY acteur (id, nom, naissance, nationalite, id_film, taille) FROM stdin;
2	Bruce Willis	19 mars 1955	Allemand/Américain	1	183
1	Alan Rickman	21 février 1946	Britannique	1	185
4	Sylvester Stallone	6 juillet 1946	Américaine	4	177
3	Richard Crenna	30 novembre 1926	Américaine	2	185
6	Viggo Mortensen	20 octobre 1958	Danoise/Américaine	3	180
5	Talia Shire	25 avril 1946	Américaine	4	162
7	Elijah Wood	28 janvier 1981	Américaine	3	168
\.


--
-- TOC entry 2203 (class 0 OID 0)
-- Dependencies: 187
-- Name: acteur_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('acteur_id_seq', 15, true);


--
-- TOC entry 2178 (class 0 OID 16422)
-- Dependencies: 185
-- Data for Name: film; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY film (titre, description, genre, date_de_sortie, id, duree) FROM stdin;
Rambo	Revenu du Viêtnam, abruti autant par les mauvais traitements que lui ont jadis infligés ses tortionnaires que par l'indifférence de ses concitoyens, le soldat Rambo, un ancien des commandos d'élite, traîne sa redoutable carcasse de ville en ville. Un shérif teigneux lui interdit l'accès de sa bourgade. Rambo insiste. Il veut seulement manger. Le shérif le met sous les verrous et laisse son adjoint brutaliser ce divertissant clochard.	Drame/Thriller	1982	2	93
Die Hard	Un policier new-yorkais, John McClane, est séparé de sa femme Holly, cadre dans une puissante multinationale japonaise, la Nakatomi Corporation. Venu à Los Angeles passer les fêtes avec elle, il se rend à la tour Nakatomi où le patron donne une grande soirée. Tandis que John s'isole pour téléphoner, un groupe de terroristes allemands, dirigé par Hans Gruber, pénètre dans l'immeuble.	énigme/Thriller	1988	1	132
Rocky	Rocky Balboa travaille pour Tony Gazzo, un usurier, et dispute de temps à autre des combats de boxe pour quelques dizaines de dollars sous l'appellation de l'Étalon Italien. Cependant, Mickey, propriétaire du club de boxe où Rocky a l'habitude de s'entraîner, décide de céder son casier à un boxeur plus talentueux.	Drame/Sport	1976	4	122
Le Seigneur des anneaux : La Communauté de l'anneau	Un jeune et timide `Hobbit', Frodon Sacquet, hérite d'un anneau magique. Bien loin d'être une simple babiole, il s'agit d'un instrument de pouvoir absolu qui permettrait à Sauron, le `Seigneur des ténèbres', de régner sur la `Terre du Milieu' et de réduire en esclavage ses peuples. Frodon doit parvenir jusqu'à la `Crevasse du Destin' pour détruire l'anneau.	fantasy/Action	2001	3	228
\.


--
-- TOC entry 2204 (class 0 OID 0)
-- Dependencies: 186
-- Name: film_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('film_id_seq', 26, true);


--
-- TOC entry 2182 (class 0 OID 16455)
-- Dependencies: 189
-- Data for Name: journal; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY journal (moment, operation, description, objet, id) FROM stdin;
2018-09-29 17:50:30.63691-04	MODIFIER	{sa,a,2,a,z} -> {qwe,a,2,a,z}	film	1
2018-09-29 17:50:47.451292-04	EFFACER	{qwe,a,2,a,z} -> {}	film	2
2018-09-29 17:50:55.686466-04	AJOUTER	{} -> {q,w,e,r,t}	film	3
2018-09-29 18:23:16.586444-04	MODIFIER	\N	film	4
2018-09-29 18:23:16.586444-04	MODIFIER	\N	film	5
2018-09-29 18:23:16.586444-04	MODIFIER	\N	film	6
2018-09-29 18:23:16.586444-04	MODIFIER	\N	film	7
2018-09-29 18:23:16.586444-04	MODIFIER	\N	film	8
2018-09-29 18:38:23.440132-04	AJOUTER	{} -> {q,w,e,22,22}	film	9
2018-09-29 18:38:29.629153-04	MODIFIER	{q,w,e,22,22} -> {q,w,e,22,4}	film	10
2018-09-29 18:44:41.972711-04	EFFACER	{q,w,e,r,1} -> {}	film	11
2018-09-29 21:06:03.253731-04	MODIFIER	{q,w,e,22,4} -> {wds,wds,ed,1,46}	film	12
2018-09-29 21:06:12.253597-04	MODIFIER	{wds,wds,ed,1,46} -> {wds,wds,ed,1,46}	film	13
2018-09-29 21:06:14.842421-04	EFFACER	{wds,wds,ed,1,46} -> {}	film	14
\.


--
-- TOC entry 2205 (class 0 OID 0)
-- Dependencies: 193
-- Name: journal_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('journal_id_seq', 14, true);


--
-- TOC entry 2184 (class 0 OID 16523)
-- Dependencies: 191
-- Data for Name: surveillanceActeur; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "surveillanceActeur" (moment, "nombreActeur", "moyenneTaille", "checksumNom", id) FROM stdin;
2018-09-29 20:09:33.402317-04	7	1.77142859	5a10654defc466eb30f430bebb153237	8
\.


--
-- TOC entry 2185 (class 0 OID 16531)
-- Dependencies: 192
-- Data for Name: surveillanceActeurParFilm; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "surveillanceActeurParFilm" (moment, "nombreActeur", "moyenneTaille", "checksumNom", id) FROM stdin;
2018-09-29 20:09:33.402317-04	1	1.85000002	8143dcfec45be7ac42c275d22b980ab0	30
2018-09-29 20:09:33.402317-04	2	1.84000003	69f9452d935422c8d5ca6492699df8e6	31
2018-09-29 20:09:33.402317-04	2	1.69499993	7af7132566b92ba5ff951143c7dbb178	32
2018-09-29 20:09:33.402317-04	2	1.74000001	2f3cd300c1121849615d99bfd046b43a	33
2018-09-29 20:09:33.402317-04	0	\N	\N	34
\.


--
-- TOC entry 2206 (class 0 OID 0)
-- Dependencies: 195
-- Name: surveillanceActeurParFilm_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"surveillanceActeurParFilm_id_seq"', 34, true);


--
-- TOC entry 2207 (class 0 OID 0)
-- Dependencies: 194
-- Name: surveillanceActeur_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"surveillanceActeur_id_seq"', 8, true);


--
-- TOC entry 2183 (class 0 OID 16515)
-- Dependencies: 190
-- Data for Name: surveillanceFilm; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "surveillanceFilm" (moment, "nombreFilm", "moyenneDuree", "checksumTitre", id) FROM stdin;
2018-09-29 20:09:33.402317-04	5	115.800003	54f9e669a58dce95ff265e993c1e4d35	9
\.


--
-- TOC entry 2208 (class 0 OID 0)
-- Dependencies: 196
-- Name: surveillanceFilm_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"surveillanceFilm_id_seq"', 9, true);


--
-- TOC entry 2048 (class 2606 OID 16443)
-- Name: acteur acteur_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acteur
    ADD CONSTRAINT acteur_pkey PRIMARY KEY (id);


--
-- TOC entry 2046 (class 2606 OID 16432)
-- Name: film film_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY film
    ADD CONSTRAINT film_pkey PRIMARY KEY (id);


--
-- TOC entry 2050 (class 2606 OID 16550)
-- Name: journal journal_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY journal
    ADD CONSTRAINT journal_pkey PRIMARY KEY (id);


--
-- TOC entry 2056 (class 2606 OID 16573)
-- Name: surveillanceActeurParFilm surveillanceActeurParFilm_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "surveillanceActeurParFilm"
    ADD CONSTRAINT "surveillanceActeurParFilm_pkey" PRIMARY KEY (id);


--
-- TOC entry 2054 (class 2606 OID 16562)
-- Name: surveillanceActeur surveillanceActeur_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "surveillanceActeur"
    ADD CONSTRAINT "surveillanceActeur_pkey" PRIMARY KEY (id);


--
-- TOC entry 2052 (class 2606 OID 16585)
-- Name: surveillanceFilm surveillanceFilm_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "surveillanceFilm"
    ADD CONSTRAINT "surveillanceFilm_pkey" PRIMARY KEY (id);


--
-- TOC entry 2058 (class 2620 OID 16537)
-- Name: film evenementajoutfilm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER evenementajoutfilm BEFORE INSERT ON film FOR EACH ROW EXECUTE PROCEDURE journaliser();


--
-- TOC entry 2059 (class 2620 OID 16538)
-- Name: film evenementeffacerfilm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER evenementeffacerfilm BEFORE DELETE ON film FOR EACH ROW EXECUTE PROCEDURE journaliser();


--
-- TOC entry 2060 (class 2620 OID 16539)
-- Name: film evenementmodifierfilm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER evenementmodifierfilm BEFORE UPDATE ON film FOR EACH ROW EXECUTE PROCEDURE journaliser();


--
-- TOC entry 2057 (class 2606 OID 16450)
-- Name: acteur acteur_id_film_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acteur
    ADD CONSTRAINT acteur_id_film_fkey FOREIGN KEY (id_film) REFERENCES film(id) ON DELETE CASCADE;


-- Completed on 2018-09-29 21:06:57

--
-- PostgreSQL database dump complete
--

