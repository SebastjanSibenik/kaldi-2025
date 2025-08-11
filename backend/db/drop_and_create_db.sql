CREATE EXTENSION IF NOT EXISTS dblink;

DO $$
BEGIN
    PERFORM dblink_exec('dbname=postgres', 'DROP DATABASE IF EXISTS kaldi');
    PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE kaldi');
END
$$ LANGUAGE plpgsql;
