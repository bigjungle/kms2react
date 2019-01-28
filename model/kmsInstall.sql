--create FUNCTION
CREATE FUNCTION kms.tf_para_user_bf_iud()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$declare
  li_id bigint;
  lv_log character varying;
  li_sso integer;
begin
	select nextval('eduty.eduty_user_add_sequence') into li_sso;
	if (TG_OP = 'INSERT') then
	update kms.JHI_USER set email=lower(new.person_id)||'@aerolink.com'  where login=lower(new.person_id);
	IF NOT FOUND THEN
		insert into kms.JHI_USER(id,login,password_hash,first_name,email,activated,lang_key,created_by,created_date,last_modified_by,last_modified_date)
		values (new.id,lower(new.person_id),'$2a$10$buFgLwcraDI7ZN8gOgVeQuofhdnE5zGXOo6fdSI5yOYh0riHXFG4m',new.user_name,lower(new.person_id)||'@aerolink.com',true,'zh-cn','admin',current_timestamp,'admin',current_timestamp);
	END IF;
	return new;
	elsif (TG_OP = 'UPDATE') THEN
			UPDATE kms.JHI_USER set first_name = new.user_name,email=lower(new.person_id)||'@aerolink.com',last_modified_date = current_timestamp  where login=lower(new.person_id);
			IF NOT FOUND THEN
				insert into kms.JHI_USER(id,login,password_hash,first_name,email,activated,lang_key,created_by,created_date,last_modified_by,last_modified_date)
				values (new.id,lower(new.person_id),'$2a$10$buFgLwcraDI7ZN8gOgVeQuofhdnE5zGXOo6fdSI5yOYh0riHXFG4m',new.user_name,lower(new.person_id)||'@aerolink.com',true,'zh-cn','admin',current_timestamp,'admin',current_timestamp);
			END IF;
		return new;
	elsif (TG_OP = 'DELETE') then
		delete from kms.JHI_USER_AUTHORITY where user_id = (select id from kms.JHI_USER where login = lower(old.person_id));
		delete from kms.JHI_USER where login = lower(old.person_id);

		RETURN OLD;
	end if;
exception
	when others then
		lv_log :="pkg_sys_public"."p_log"('kms.tr_para_user_bf_iud',to_char(current_timestamp,'YYYYMMDDHH24MISS'),SQLERRM,'kms.tr_para_user_bf_iud');
		return null;
end;

$BODY$;

ALTER FUNCTION kms.tf_para_user_bf_iud()
    OWNER TO asys;

GRANT EXECUTE ON FUNCTION kms.tf_para_user_bf_iud() TO PUBLIC;

GRANT EXECUTE ON FUNCTION kms.tf_para_user_bf_iud() TO asys WITH GRANT OPTION;

--create TRIGGER
CREATE TRIGGER tr_para_user_bf_iud
    BEFORE INSERT OR DELETE OR UPDATE
    ON kms.rms_user
    FOR EACH ROW
    EXECUTE PROCEDURE kms.tf_para_user_bf_iud();
---------------------
-- only for lost authority data:
insert into kms.jhi_user_authority(user_id,authority_name) values(1,'ROLE_ADMIN');
insert into kms.jhi_user_authority(user_id,authority_name) values(1,'ROLE_USER');
insert into kms.jhi_user_authority(user_id,authority_name) values(3,'ROLE_ADMIN');
insert into kms.jhi_user_authority(user_id,authority_name) values(3,'ROLE_USER');
insert into kms.jhi_user_authority(user_id,authority_name) values(4,'ROLE_USER');

--insert api user apiadmin
--old admin $2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC
--apiadmin password www.if369.com $2a$10$buFgLwcraDI7ZN8gOgVeQuofhdnE5zGXOo6fdSI5yOYh0riHXFG4m
--apiantian password www.oa369.com $2a$10$Y6YTE2X4bN/4kX4JXXy6wOPlfHYR2mq72NK7EN3yz/9iz3EQbDypC
insert into kms.JHI_USER(id,login,password_hash,first_name,email,activated,lang_key,created_by,created_date,last_modified_by,last_modified_date)
	values (11,'apiadmin','$2a$10$buFgLwcraDI7ZN8gOgVeQuofhdnE5zGXOo6fdSI5yOYh0riHXFG4m','apiadmin','apiadmin@aerolink.com',true,'zh-cn','admin',current_timestamp,'admin',current_timestamp);
insert into kms.JHI_USER(id,login,password_hash,first_name,email,activated,lang_key,created_by,created_date,last_modified_by,last_modified_date)
	values (12,'apiantian','$2a$10$Y6YTE2X4bN/4kX4JXXy6wOPlfHYR2mq72NK7EN3yz/9iz3EQbDypC','apiantian','apiantian@aerolink.com',true,'zh-cn','admin',current_timestamp,'admin',current_timestamp);
insert into kms.jhi_user_authority(user_id,authority_name) values(11,'ROLE_ADMIN');
insert into kms.jhi_user_authority(user_id,authority_name) values(11,'ROLE_USER');
insert into kms.jhi_user_authority(user_id,authority_name) values(12,'ROLE_ADMIN');
insert into kms.jhi_user_authority(user_id,authority_name) values(12,'ROLE_USER');


insert into kms.jhi_authority(name) values('ROLE_VERIFY');
insert into kms.jhi_authority(name) values('ROLE_STATISTICS');

insert into kms.JHI_USER(id,login,password_hash,first_name,email,activated,lang_key,created_by,created_date,last_modified_by,last_modified_date)
	values (13,'mhadmin','$2a$10$buFgLwcraDI7ZN8gOgVeQuofhdnE5zGXOo6fdSI5yOYh0riHXFG4m','mhadmin','mhadmin@aerolink.com',true,'zh-cn','admin',current_timestamp,'admin',current_timestamp);
insert into kms.jhi_user_authority(user_id,authority_name) values(13,'ROLE_ADMIN');
insert into kms.jhi_user_authority(user_id,authority_name) values(13,'ROLE_USER');
