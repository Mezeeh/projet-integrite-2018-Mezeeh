--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.4
-- Dumped by pg_dump version 9.6.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: filmotheque; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE filmotheque WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'French_Canada.1252' LC_CTYPE = 'French_Canada.1252';


ALTER DATABASE filmotheque OWNER TO postgres;

\connect filmotheque

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
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


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: film; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE film (
    id integer NOT NULL,
    titre text,
    description text,
    genre text,
    "dateDeSortie" text,
    duree text
);


ALTER TABLE film OWNER TO postgres;

--
-- Data for Name: film; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO film VALUES (1, 'Die Hard', 'Un policier new-yorkais, John McClane, est séparé de sa femme Holly, cadre dans une puissante multinationale japonaise, la Nakatomi Corporation. Venu à Los Angeles passer les fêtes avec elle, il se rend à la tour Nakatomi où le patron donne une grande soirée. Tandis que John s''isole pour téléphoner, un groupe de terroristes allemands, dirigé par Hans Gruber, pénètre dans l''immeuble.', 'énigme/Thriller', '1988', '2h 12m');
INSERT INTO film VALUES (2, 'Rambo', 'Revenu du Viêtnam, abruti autant par les mauvais traitements que lui ont jadis infligés ses tortionnaires que par l''indifférence de ses concitoyens, le soldat Rambo, un ancien des commandos d''élite, traîne sa redoutable carcasse de ville en ville. Un shérif teigneux lui interdit l''accès de sa bourgade. Rambo insiste. Il veut seulement manger. Le shérif le met sous les verrous et laisse son adjoint brutaliser ce divertissant clochard.', 'Drame/Thriller', '1982', '1h 33m');
INSERT INTO film VALUES (4, 'Le Seigneur des anneaux : La Communauté de l''anneau', 'Un jeune et timide `Hobbit'', Frodon Sacquet, hérite d''un anneau magique. Bien loin d''être une simple babiole, il s''agit d''un instrument de pouvoir absolu qui permettrait à Sauron, le `Seigneur des ténèbres'', de régner sur la `Terre du Milieu'' et de réduire en esclavage ses peuples. Frodon doit parvenir jusqu''à la `Crevasse du Destin'' pour détruire l''anneau.', 'fantasy/Action', '2001', '3h 48m');
INSERT INTO film VALUES (3, 'Rocky', 'Rocky Balboa travaille pour Tony Gazzo, un usurier, et dispute de temps à autre des combats de boxe pour quelques dizaines de dollars sous l''appellation de l''Étalon Italien. Cependant, Mickey, propriétaire du club de boxe où Rocky a l''habitude de s''entraîner, décide de céder son casier à un boxeur plus talentueux.', 'Drame/Sport', '1976', '2h 2m');


--
-- Name: film film_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY film
    ADD CONSTRAINT film_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

