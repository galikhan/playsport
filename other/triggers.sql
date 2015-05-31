CREATE OR REPLACE FUNCTION generic_stamp()
    RETURNS TRIGGER AS $generic_stamp$
BEGIN
    IF (TG_OP = 'INSERT') THEN
      NEW.created_date_ = now();
      NEW.modified_date_ = now();
      NEW.enabled_ = true;
      NEW.removed_ = false;
      RETURN NEW;
    ELSE
      NEW.modified_date_ = now();
      RETURN NEW;
    END IF;
END;
$generic_stamp$ LANGUAGE plpgsql;

--1
CREATE TRIGGER ps_cities BEFORE INSERT OR UPDATE ON ps_cities
    FOR EACH ROW EXECUTE PROCEDURE generic_stamp();

--2
CREATE TRIGGER ps_coverings BEFORE INSERT OR UPDATE ON ps_coverings
    FOR EACH ROW EXECUTE PROCEDURE generic_stamp();

--3
CREATE TRIGGER ps_fields BEFORE INSERT OR UPDATE ON ps_fields
    FOR EACH ROW EXECUTE PROCEDURE generic_stamp();

--4
CREATE TRIGGER ps_field_types BEFORE INSERT OR UPDATE ON ps_field_types
    FOR EACH ROW EXECUTE PROCEDURE generic_stamp();

--5
CREATE TRIGGER ps_images BEFORE INSERT OR UPDATE ON ps_images
    FOR EACH ROW EXECUTE PROCEDURE generic_stamp();

--6
CREATE TRIGGER ps_users BEFORE INSERT OR UPDATE ON ps_users
    FOR EACH ROW EXECUTE PROCEDURE generic_stamp();

--7
CREATE TRIGGER ps_field_comforts BEFORE INSERT OR UPDATE ON ps_field_comforts
    FOR EACH ROW EXECUTE PROCEDURE generic_stamp();


