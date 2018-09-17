--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.4
-- Dumped by pg_dump version 9.6.4

-- Started on 2018-09-17 19:15:12

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
-- TOC entry 2143 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

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
    taille text,
    nationalite text,
    id_flim integer
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
-- TOC entry 2144 (class 0 OID 0)
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
    duree text,
    id bigint NOT NULL
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
-- TOC entry 2145 (class 0 OID 0)
-- Dependencies: 186
-- Name: film_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE film_id_seq OWNED BY film.id;


--
-- TOC entry 2010 (class 2604 OID 16438)
-- Name: acteur id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acteur ALTER COLUMN id SET DEFAULT nextval('acteur_id_seq'::regclass);


--
-- TOC entry 2009 (class 2604 OID 16430)
-- Name: film id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY film ALTER COLUMN id SET DEFAULT nextval('film_id_seq'::regclass);


--
-- TOC entry 2136 (class 0 OID 16435)
-- Dependencies: 188
-- Data for Name: acteur; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY acteur (id, nom, naissance, taille, nationalite, id_flim) FROM stdin;
\.


--
-- TOC entry 2146 (class 0 OID 0)
-- Dependencies: 187
-- Name: acteur_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('acteur_id_seq', 1, false);


--
-- TOC entry 2133 (class 0 OID 16422)
-- Dependencies: 185
-- Data for Name: film; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY film (titre, description, genre, date_de_sortie, duree, id) FROM stdin;
Die Hard	Un policier new-yorkais, John McClane, est séparé de sa femme Holly, cadre dans une puissante multinationale japonaise, la Nakatomi Corporation. Venu à Los Angeles passer les fêtes avec elle, il se rend à la tour Nakatomi où le patron donne une grande soirée. Tandis que John s'isole pour téléphoner, un groupe de terroristes allemands, dirigé par Hans Gruber, pénètre dans l'immeuble.	énigme/Thriller	1988	2h 12m	1
Rambo	Revenu du Viêtnam, abruti autant par les mauvais traitements que lui ont jadis infligés ses tortionnaires que par l'indifférence de ses concitoyens, le soldat Rambo, un ancien des commandos d'élite, traîne sa redoutable carcasse de ville en ville. Un shérif teigneux lui interdit l'accès de sa bourgade. Rambo insiste. Il veut seulement manger. Le shérif le met sous les verrous et laisse son adjoint brutaliser ce divertissant clochard.	Drame/Thriller	1982	1h 33m	2
Le Seigneur des anneaux : La Communauté de l'anneau	Un jeune et timide `Hobbit', Frodon Sacquet, hérite d'un anneau magique. Bien loin d'être une simple babiole, il s'agit d'un instrument de pouvoir absolu qui permettrait à Sauron, le `Seigneur des ténèbres', de régner sur la `Terre du Milieu' et de réduire en esclavage ses peuples. Frodon doit parvenir jusqu'à la `Crevasse du Destin' pour détruire l'anneau.	fantasy/Action	2001	3h 48m	3
Rocky	Rocky Balboa travaille pour Tony Gazzo, un usurier, et dispute de temps à autre des combats de boxe pour quelques dizaines de dollars sous l'appellation de l'Étalon Italien. Cependant, Mickey, propriétaire du club de boxe où Rocky a l'habitude de s'entraîner, décide de céder son casier à un boxeur plus talentueux.	Drame/Sport	1976	2h 2m	4
1	2	3	4	5	15
\.


--
-- TOC entry 2147 (class 0 OID 0)
-- Dependencies: 186
-- Name: film_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('film_id_seq', 15, true);


--
-- TOC entry 2014 (class 2606 OID 16443)
-- Name: acteur acteur_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acteur
    ADD CONSTRAINT acteur_pkey PRIMARY KEY (id);


--
-- TOC entry 2012 (class 2606 OID 16432)
-- Name: film film_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY film
    ADD CONSTRAINT film_pkey PRIMARY KEY (id);


--
-- TOC entry 2015 (class 2606 OID 16444)
-- Name: acteur acteur_id_flim_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acteur
    ADD CONSTRAINT acteur_id_flim_fkey FOREIGN KEY (id_flim) REFERENCES film(id);


-- Completed on 2018-09-17 19:15:12

--
-- PostgreSQL database dump complete
--

