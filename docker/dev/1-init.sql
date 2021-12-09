drop schema if exists dictionary cascade;

drop user if exists dictionary;

-- schema owner
CREATE USER dictionary WITH password 'dictionary';

-- create schema
CREATE SCHEMA dictionary AUTHORIZATION dictionary;

GRANT USAGE ON SCHEMA dictionary TO dictionary;

ALTER DEFAULT PRIVILEGES FOR USER dictionary IN SCHEMA dictionary GRANT SELECT,INSERT,UPDATE,DELETE,TRUNCATE ON TABLES TO dictionary;
ALTER DEFAULT PRIVILEGES FOR USER dictionary IN SCHEMA dictionary GRANT USAGE ON SEQUENCES TO dictionary;
ALTER DEFAULT PRIVILEGES FOR USER dictionary IN SCHEMA dictionary GRANT EXECUTE ON FUNCTIONS TO dictionary;
