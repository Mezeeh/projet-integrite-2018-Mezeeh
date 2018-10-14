--
-- PostgreSQL database dump
--

-- Dumped from database version 10.5 (Ubuntu 10.5-1.pgdg18.04+1)
-- Dumped by pg_dump version 10.5 (Ubuntu 10.5-1.pgdg18.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: filmotheque; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE filmotheque WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_CA.UTF-8' LC_CTYPE = 'en_CA.UTF-8';


ALTER DATABASE filmotheque OWNER TO postgres;

\connect filmotheque

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: journaliser(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.journaliser() RETURNS trigger
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
-- Name: surveillance(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.surveillance() RETURNS void
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
-- Name: acteur; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.acteur (
    id bigint NOT NULL,
    nom text,
    naissance text,
    nationalite text,
    id_film integer,
    taille integer
);


ALTER TABLE public.acteur OWNER TO postgres;

--
-- Name: acteur_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.acteur_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.acteur_id_seq OWNER TO postgres;

--
-- Name: acteur_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.acteur_id_seq OWNED BY public.acteur.id;


--
-- Name: film; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.film (
    titre text,
    description text,
    genre text,
    date_de_sortie text,
    id bigint NOT NULL,
    duree integer
);


ALTER TABLE public.film OWNER TO postgres;

--
-- Name: film_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.film_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.film_id_seq OWNER TO postgres;

--
-- Name: film_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.film_id_seq OWNED BY public.film.id;


--
-- Name: journal; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.journal (
    moment timestamp with time zone NOT NULL,
    operation text NOT NULL,
    description text,
    objet text NOT NULL,
    id bigint NOT NULL
);


ALTER TABLE public.journal OWNER TO postgres;

--
-- Name: journal_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.journal_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.journal_id_seq OWNER TO postgres;

--
-- Name: journal_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.journal_id_seq OWNED BY public.journal.id;


--
-- Name: surveillanceActeur; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."surveillanceActeur" (
    moment timestamp with time zone,
    "nombreActeur" integer,
    "moyenneTaille" real,
    "checksumNom" text,
    id bigint NOT NULL
);


ALTER TABLE public."surveillanceActeur" OWNER TO postgres;

--
-- Name: surveillanceActeurParFilm; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."surveillanceActeurParFilm" (
    moment timestamp with time zone,
    "nombreActeur" integer,
    "moyenneTaille" real,
    "checksumNom" text,
    id bigint NOT NULL
);


ALTER TABLE public."surveillanceActeurParFilm" OWNER TO postgres;

--
-- Name: surveillanceActeurParFilm_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."surveillanceActeurParFilm_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."surveillanceActeurParFilm_id_seq" OWNER TO postgres;

--
-- Name: surveillanceActeurParFilm_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."surveillanceActeurParFilm_id_seq" OWNED BY public."surveillanceActeurParFilm".id;


--
-- Name: surveillanceActeur_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."surveillanceActeur_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."surveillanceActeur_id_seq" OWNER TO postgres;

--
-- Name: surveillanceActeur_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."surveillanceActeur_id_seq" OWNED BY public."surveillanceActeur".id;


--
-- Name: surveillanceFilm; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."surveillanceFilm" (
    moment timestamp with time zone,
    "nombreFilm" integer,
    "moyenneDuree" real,
    "checksumTitre" text,
    id bigint NOT NULL
);


ALTER TABLE public."surveillanceFilm" OWNER TO postgres;

--
-- Name: surveillanceFilm_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."surveillanceFilm_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."surveillanceFilm_id_seq" OWNER TO postgres;

--
-- Name: surveillanceFilm_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."surveillanceFilm_id_seq" OWNED BY public."surveillanceFilm".id;


--
-- Name: acteur id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.acteur ALTER COLUMN id SET DEFAULT nextval('public.acteur_id_seq'::regclass);


--
-- Name: film id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.film ALTER COLUMN id SET DEFAULT nextval('public.film_id_seq'::regclass);


--
-- Name: journal id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.journal ALTER COLUMN id SET DEFAULT nextval('public.journal_id_seq'::regclass);


--
-- Name: surveillanceActeur id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."surveillanceActeur" ALTER COLUMN id SET DEFAULT nextval('public."surveillanceActeur_id_seq"'::regclass);


--
-- Name: surveillanceActeurParFilm id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."surveillanceActeurParFilm" ALTER COLUMN id SET DEFAULT nextval('public."surveillanceActeurParFilm_id_seq"'::regclass);


--
-- Name: surveillanceFilm id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."surveillanceFilm" ALTER COLUMN id SET DEFAULT nextval('public."surveillanceFilm_id_seq"'::regclass);


--
-- Data for Name: acteur; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.acteur VALUES (2, 'Bruce Willis', '19 mars 1955', 'Allemand/Américain', 1, 183);
INSERT INTO public.acteur VALUES (1, 'Alan Rickman', '21 février 1946', 'Britannique', 1, 185);
INSERT INTO public.acteur VALUES (4, 'Sylvester Stallone', '6 juillet 1946', 'Américaine', 4, 177);
INSERT INTO public.acteur VALUES (3, 'Richard Crenna', '30 novembre 1926', 'Américaine', 2, 185);
INSERT INTO public.acteur VALUES (6, 'Viggo Mortensen', '20 octobre 1958', 'Danoise/Américaine', 3, 180);
INSERT INTO public.acteur VALUES (5, 'Talia Shire', '25 avril 1946', 'Américaine', 4, 162);
INSERT INTO public.acteur VALUES (7, 'Elijah Wood', '28 janvier 1981', 'Américaine', 3, 168);


--
-- Data for Name: film; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.film VALUES ('Rambo', 'Revenu du Viêtnam, abruti autant par les mauvais traitements que lui ont jadis infligés ses tortionnaires que par l''indifférence de ses concitoyens, le soldat Rambo, un ancien des commandos d''élite, traîne sa redoutable carcasse de ville en ville. Un shérif teigneux lui interdit l''accès de sa bourgade. Rambo insiste. Il veut seulement manger. Le shérif le met sous les verrous et laisse son adjoint brutaliser ce divertissant clochard.', 'Drame/Thriller', '1982', 2, 93);
INSERT INTO public.film VALUES ('Die Hard', 'Un policier new-yorkais, John McClane, est séparé de sa femme Holly, cadre dans une puissante multinationale japonaise, la Nakatomi Corporation. Venu à Los Angeles passer les fêtes avec elle, il se rend à la tour Nakatomi où le patron donne une grande soirée. Tandis que John s''isole pour téléphoner, un groupe de terroristes allemands, dirigé par Hans Gruber, pénètre dans l''immeuble.', 'énigme/Thriller', '1988', 1, 132);
INSERT INTO public.film VALUES ('Rocky', 'Rocky Balboa travaille pour Tony Gazzo, un usurier, et dispute de temps à autre des combats de boxe pour quelques dizaines de dollars sous l''appellation de l''Étalon Italien. Cependant, Mickey, propriétaire du club de boxe où Rocky a l''habitude de s''entraîner, décide de céder son casier à un boxeur plus talentueux.', 'Drame/Sport', '1976', 4, 122);
INSERT INTO public.film VALUES ('Le Seigneur des anneaux : La Communauté de l''anneau', 'Un jeune et timide `Hobbit'', Frodon Sacquet, hérite d''un anneau magique. Bien loin d''être une simple babiole, il s''agit d''un instrument de pouvoir absolu qui permettrait à Sauron, le `Seigneur des ténèbres'', de régner sur la `Terre du Milieu'' et de réduire en esclavage ses peuples. Frodon doit parvenir jusqu''à la `Crevasse du Destin'' pour détruire l''anneau.', 'fantasy/Action', '2001', 3, 228);


--
-- Data for Name: journal; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.journal VALUES ('2018-09-29 17:50:30.63691-04', 'MODIFIER', '{sa,a,2,a,z} -> {qwe,a,2,a,z}', 'film', 1);
INSERT INTO public.journal VALUES ('2018-09-29 17:50:47.451292-04', 'EFFACER', '{qwe,a,2,a,z} -> {}', 'film', 2);
INSERT INTO public.journal VALUES ('2018-09-29 17:50:55.686466-04', 'AJOUTER', '{} -> {q,w,e,r,t}', 'film', 3);
INSERT INTO public.journal VALUES ('2018-09-29 18:23:16.586444-04', 'MODIFIER', NULL, 'film', 4);
INSERT INTO public.journal VALUES ('2018-09-29 18:23:16.586444-04', 'MODIFIER', NULL, 'film', 5);
INSERT INTO public.journal VALUES ('2018-09-29 18:23:16.586444-04', 'MODIFIER', NULL, 'film', 6);
INSERT INTO public.journal VALUES ('2018-09-29 18:23:16.586444-04', 'MODIFIER', NULL, 'film', 7);
INSERT INTO public.journal VALUES ('2018-09-29 18:23:16.586444-04', 'MODIFIER', NULL, 'film', 8);
INSERT INTO public.journal VALUES ('2018-09-29 18:38:23.440132-04', 'AJOUTER', '{} -> {q,w,e,22,22}', 'film', 9);
INSERT INTO public.journal VALUES ('2018-09-29 18:38:29.629153-04', 'MODIFIER', '{q,w,e,22,22} -> {q,w,e,22,4}', 'film', 10);
INSERT INTO public.journal VALUES ('2018-09-29 18:44:41.972711-04', 'EFFACER', '{q,w,e,r,1} -> {}', 'film', 11);
INSERT INTO public.journal VALUES ('2018-09-29 21:06:03.253731-04', 'MODIFIER', '{q,w,e,22,4} -> {wds,wds,ed,1,46}', 'film', 12);
INSERT INTO public.journal VALUES ('2018-09-29 21:06:12.253597-04', 'MODIFIER', '{wds,wds,ed,1,46} -> {wds,wds,ed,1,46}', 'film', 13);
INSERT INTO public.journal VALUES ('2018-09-29 21:06:14.842421-04', 'EFFACER', '{wds,wds,ed,1,46} -> {}', 'film', 14);


--
-- Data for Name: surveillanceActeur; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."surveillanceActeur" VALUES ('2018-09-29 20:09:33.402317-04', 7, 1.77142859, '5a10654defc466eb30f430bebb153237', 8);


--
-- Data for Name: surveillanceActeurParFilm; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."surveillanceActeurParFilm" VALUES ('2018-09-29 20:09:33.402317-04', 1, 1.85000002, '8143dcfec45be7ac42c275d22b980ab0', 30);
INSERT INTO public."surveillanceActeurParFilm" VALUES ('2018-09-29 20:09:33.402317-04', 2, 1.84000003, '69f9452d935422c8d5ca6492699df8e6', 31);
INSERT INTO public."surveillanceActeurParFilm" VALUES ('2018-09-29 20:09:33.402317-04', 2, 1.69499993, '7af7132566b92ba5ff951143c7dbb178', 32);
INSERT INTO public."surveillanceActeurParFilm" VALUES ('2018-09-29 20:09:33.402317-04', 2, 1.74000001, '2f3cd300c1121849615d99bfd046b43a', 33);
INSERT INTO public."surveillanceActeurParFilm" VALUES ('2018-09-29 20:09:33.402317-04', 0, NULL, NULL, 34);


--
-- Data for Name: surveillanceFilm; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."surveillanceFilm" VALUES ('2018-09-29 20:09:33.402317-04', 5, 115.800003, '54f9e669a58dce95ff265e993c1e4d35', 9);


--
-- Name: acteur_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.acteur_id_seq', 15, true);


--
-- Name: film_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.film_id_seq', 26, true);


--
-- Name: journal_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.journal_id_seq', 14, true);


--
-- Name: surveillanceActeurParFilm_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."surveillanceActeurParFilm_id_seq"', 34, true);


--
-- Name: surveillanceActeur_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."surveillanceActeur_id_seq"', 8, true);


--
-- Name: surveillanceFilm_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."surveillanceFilm_id_seq"', 9, true);


--
-- Name: acteur acteur_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.acteur
    ADD CONSTRAINT acteur_pkey PRIMARY KEY (id);


--
-- Name: film film_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.film
    ADD CONSTRAINT film_pkey PRIMARY KEY (id);


--
-- Name: journal journal_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.journal
    ADD CONSTRAINT journal_pkey PRIMARY KEY (id);


--
-- Name: surveillanceActeurParFilm surveillanceActeurParFilm_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."surveillanceActeurParFilm"
    ADD CONSTRAINT "surveillanceActeurParFilm_pkey" PRIMARY KEY (id);


--
-- Name: surveillanceActeur surveillanceActeur_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."surveillanceActeur"
    ADD CONSTRAINT "surveillanceActeur_pkey" PRIMARY KEY (id);


--
-- Name: surveillanceFilm surveillanceFilm_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."surveillanceFilm"
    ADD CONSTRAINT "surveillanceFilm_pkey" PRIMARY KEY (id);


--
-- Name: film evenementajoutfilm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER evenementajoutfilm BEFORE INSERT ON public.film FOR EACH ROW EXECUTE PROCEDURE public.journaliser();


--
-- Name: film evenementeffacerfilm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER evenementeffacerfilm BEFORE DELETE ON public.film FOR EACH ROW EXECUTE PROCEDURE public.journaliser();


--
-- Name: film evenementmodifierfilm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER evenementmodifierfilm BEFORE UPDATE ON public.film FOR EACH ROW EXECUTE PROCEDURE public.journaliser();


--
-- Name: acteur acteur_id_film_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.acteur
    ADD CONSTRAINT acteur_id_film_fkey FOREIGN KEY (id_film) REFERENCES public.film(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

