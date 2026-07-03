--
-- PostgreSQL database dump
--

\restrict O6xfQlHIqk7TPm2QS2gzYsisKVsLbA0Iz8jbiuruWxYi0B0hPE5hwPRg1lvTgln

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.6

-- Started on 2026-06-05 17:00:13

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 24577)
-- Name: clienti; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clienti (
    cod_client character varying(50) NOT NULL,
    nume character varying(100),
    varsta integer
);


ALTER TABLE public.clienti OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 24582)
-- Name: conturi; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.conturi (
    iban character varying(50) NOT NULL,
    sold numeric(15,2),
    cod_client character varying(50)
);


ALTER TABLE public.conturi OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 24593)
-- Name: tranzactii; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tranzactii (
    id integer NOT NULL,
    iban_sursa character varying(50),
    iban_destinatie character varying(50),
    suma numeric(15,2),
    data_tranzactie timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.tranzactii OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 24592)
-- Name: tranzactii_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tranzactii_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tranzactii_id_seq OWNER TO postgres;

--
-- TOC entry 4866 (class 0 OID 0)
-- Dependencies: 219
-- Name: tranzactii_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tranzactii_id_seq OWNED BY public.tranzactii.id;


--
-- TOC entry 4703 (class 2604 OID 24596)
-- Name: tranzactii id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tranzactii ALTER COLUMN id SET DEFAULT nextval('public.tranzactii_id_seq'::regclass);


--
-- TOC entry 4706 (class 2606 OID 24581)
-- Name: clienti clienti_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clienti
    ADD CONSTRAINT clienti_pkey PRIMARY KEY (cod_client);


--
-- TOC entry 4708 (class 2606 OID 24586)
-- Name: conturi conturi_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conturi
    ADD CONSTRAINT conturi_pkey PRIMARY KEY (iban);


--
-- TOC entry 4710 (class 2606 OID 24599)
-- Name: tranzactii tranzactii_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tranzactii
    ADD CONSTRAINT tranzactii_pkey PRIMARY KEY (id);


--
-- TOC entry 4711 (class 2606 OID 24587)
-- Name: conturi conturi_cod_client_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conturi
    ADD CONSTRAINT conturi_cod_client_fkey FOREIGN KEY (cod_client) REFERENCES public.clienti(cod_client);


-- Completed on 2026-06-05 17:00:13

--
-- PostgreSQL database dump complete
--

\unrestrict O6xfQlHIqk7TPm2QS2gzYsisKVsLbA0Iz8jbiuruWxYi0B0hPE5hwPRg1lvTgln

