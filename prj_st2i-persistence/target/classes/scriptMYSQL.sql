CREATE TABLE  adm_application(
  id_app BIGINT NOT NULL AUTO_INCREMENT,
  cod_app VARCHAR(40) NOT NULL,
  des_app VARCHAR(300) NOT NULL,
  des_app_ar VARCHAR(300) NOT NULL,
  CONSTRAINT pk_adm_application PRIMARY KEY(id_app)
);
  
CREATE TABLE adm_fonc (
  id_fonc BIGINT NOT NULL AUTO_INCREMENT,
  id_parent BIGINT,
  id_app BIGINT NOT NULL,
  cod VARCHAR(40) NOT NULL,
  label VARCHAR(300) NOT NULL,
  action VARCHAR(300),
  icon VARCHAR(300),
  f_aff_menu BIGINT DEFAULT 0 NOT NULL,
  f_aff_etat BIGINT DEFAULT 0 NOT NULL,
  f_admin BIGINT DEFAULT 0 NOT NULL,
  des_fonc VARCHAR(300) NOT NULL,
  des_fonc_ar VARCHAR(300) NOT NULL,
  f_valid BIGINT DEFAULT 1 NOT NULL,
  url_acces VARCHAR(1500),
  CONSTRAINT pk_adm_fonc PRIMARY KEY(id_fonc),
  CONSTRAINT fk_adm_fonc_application FOREIGN KEY (id_app)
    REFERENCES adm_application(id_app)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT fk_adm_fonc_fonc FOREIGN KEY (id_parent)
    REFERENCES adm_fonc(id_fonc)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
) ;


CREATE TABLE adm_profession (
  id_profes BIGINT NOT NULL AUTO_INCREMENT,
  cod_profes VARCHAR(40) NOT NULL,
  des_profes VARCHAR(300) NOT NULL,
  des_profes_ar VARCHAR(300) NOT NULL,
  CONSTRAINT pk_adm_profession PRIMARY KEY(id_profes)
) ;
  
CREATE TABLE adm_utilisateur (
  id_user BIGINT NOT NULL AUTO_INCREMENT,
  id_profes BIGINT,
  login VARCHAR(60) NOT NULL,
  pwd VARCHAR(100) NOT NULL,
  mail VARCHAR(60) NOT NULL,
  cin VARCHAR(40) ,
  nom_user VARCHAR(300) NOT NULL,
  nom_user_ar VARCHAR(300) NOT NULL,
  dat_naiss DATE,
  genre NUMERIC(1,0) DEFAULT 0 NOT NULL,
  autre_profession VARCHAR(300),
  direction_rattache VARCHAR(300),
  dat_der_cx DATE,
  f_actif NUMERIC(1,0) DEFAULT 0 NOT NULL,
  f_expire NUMERIC(1,0) DEFAULT 0 NOT NULL,
  f_susp NUMERIC(1,0) DEFAULT 0 NOT NULL,
  dat_expire DATE,
  dat_der_modif DATE,
  dat_creat DATE,
  dat_susp_debut DATE,
  dat_susp_fin DATE,
  CONSTRAINT pk_adm_utilisateur PRIMARY KEY(id_user),
  CONSTRAINT fk_adm_utilisateur_profession FOREIGN KEY (id_profes)
    REFERENCES adm_profession(id_profes)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
) ;
  
CREATE TABLE adm_profil (
  id_profil BIGINT NOT NULL AUTO_INCREMENT,
  id_app BIGINT NOT NULL,
  cod_profil VARCHAR(40) NOT NULL,
  des_profil VARCHAR(300) NOT NULL,
  des_profil_ar VARCHAR(300) NOT NULL,
  f_actif NUMERIC(1,0) DEFAULT 0 NOT NULL,
  dat_creat DATE,
  dat_der_modif DATE,
  CONSTRAINT pk_adm_profil PRIMARY KEY(id_profil),
  CONSTRAINT fk_adm_profil_application FOREIGN KEY (id_app)
    REFERENCES adm_application(id_app)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
);
  
CREATE TABLE adm_fonc_profil (
  id_fonc_profil BIGINT NOT NULL AUTO_INCREMENT,
  id_fonc BIGINT NOT NULL,
  id_profil BIGINT NOT NULL,
  f_editer NUMERIC(1,0) DEFAULT 0 NOT NULL,
  f_valider NUMERIC(1,0) DEFAULT 0 NOT NULL,
  CONSTRAINT pk_adm_fonc_profil PRIMARY KEY(id_fonc_profil),
  CONSTRAINT fk_adm_fonc_profil_fonc FOREIGN KEY (id_fonc)
    REFERENCES adm_fonc(id_fonc)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_adm_fonc_profil_profil FOREIGN KEY (id_profil)
    REFERENCES adm_profil(id_profil)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
);

CREATE TABLE adm_user_profil (
  id_user_profil BIGINT NOT NULL AUTO_INCREMENT,
  id_user BIGINT NOT NULL,
  id_profil BIGINT NOT NULL,
  f_valid NUMERIC(1,0) DEFAULT 0 NOT NULL,
  dat_expire DATE,
  CONSTRAINT pk_adm_user_profil PRIMARY KEY(id_user_profil),
  CONSTRAINT fk_adm_user_profil_profil FOREIGN KEY (id_profil)
    REFERENCES adm_profil(id_profil)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT fk_adm_user_profil_utilisateur FOREIGN KEY (id_user)
    REFERENCES adm_utilisateur(id_user)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
) ;

CREATE TABLE adm_log_acces (
  id_log BIGINT NOT NULL AUTO_INCREMENT,
  dat_log DATE,
  time_log TIME,
  session_id VARCHAR(100) NOT NULL,
  remote_host VARCHAR(40),
  remote_addr VARCHAR(40),
  browser VARCHAR(40),
  login VARCHAR(200) NOT NULL,
  acces VARCHAR(20) NOT NULL,
  id_user BIGINT,
  nom VARCHAR(200),
  id_fonc BIGINT,
  label_fonc VARCHAR(200),
  obs VARCHAR(3000),
  CONSTRAINT adm_log_acces_pkey PRIMARY KEY(id_log)
) ;

CREATE INDEX adm_log_acces_dat_log_idx USING btree ON adm_log_acces(dat_log);

CREATE INDEX adm_log_acces_id_fonc_idx USING btree ON adm_log_acces(id_fonc);

CREATE INDEX adm_log_acces_id_user_idx USING btree ON adm_log_acces(id_user);

CREATE INDEX adm_log_acces_remote_addr_idx USING btree ON adm_log_acces(remote_addr);

CREATE INDEX adm_log_acces_session_id_idx  USING btree ON adm_log_acces(session_id);

CREATE TABLE adm_log_data (
  id_log BIGINT NOT NULL AUTO_INCREMENT,
  dat_log DATE,
  time_log TIME,
  session_id VARCHAR(100) NOT NULL,
  remote_host VARCHAR(40),
  remote_addr VARCHAR(40),
  browser VARCHAR(40),
  login VARCHAR(200) NOT NULL,
  id_user BIGINT,
  nom VARCHAR(200),
  typ_op VARCHAR(20),
  table_name VARCHAR(100),
  data TEXT,
  CONSTRAINT adm_log_data_pkey PRIMARY KEY(id_log)
);

CREATE INDEX adm_log_data_dat_log_idx USING btree ON adm_log_data(dat_log);

CREATE INDEX adm_log_data_id_user_idx USING btree ON adm_log_data(id_user);

CREATE INDEX adm_log_data_remote_addr_idx USING btree ON adm_log_data(remote_addr);

CREATE INDEX adm_log_data_session_id_idx  USING btree ON adm_log_data(session_id);


CREATE TABLE adm_persistent_logins
(
  series VARCHAR(64) NOT NULL,
  username VARCHAR(64) NOT NULL,
  token VARCHAR(64) NOT NULL,
  last_used timestamp  NOT NULL,
  CONSTRAINT persistent_logins_pkey PRIMARY KEY (series)
)
;

CREATE TABLE adm_libelle (
  id BIGINT NOT NULL AUTO_INCREMENT,
  code VARCHAR(50),
  val_fr VARCHAR(512),
  val_ar VARCHAR(512),
  ordre INTEGER,
  CONSTRAINT pk_adm_libelle PRIMARY KEY(id)
);

CREATE TABLE adm_param (
  id BIGINT NOT NULL AUTO_INCREMENT,
  cod_param VARCHAR(50),
  val_param VARCHAR(256),
  CONSTRAINT pk_adm_param PRIMARY KEY(id)
);

DELIMITER $$

CREATE FUNCTION adm_formate_date (
  ad_date date,
  ac_lang varchar(2)
) RETURNS varchar(10) 

BEGIN

IF (ac_lang IS NULL OR ac_lang='') THEN SET ac_lang:='FR'; END IF;

IF (ac_lang='FR') THEN
  RETURN DATE_FORMAT(ad_date, '%d/%l/%Y');
END IF;

IF (ac_lang='AR') THEN
  RETURN DATE_FORMAT(ad_date, '%d/%l/%Y');
END IF;

RETURN NULL;

END;
$$
CREATE FUNCTION adm_verify_user_etat (
  an_id_user bigint
)
RETURNS integer
BEGIN

DECLARE ld_date date;
DECLARE ln_is_sus integer;

DECLARE ln_f_actif decimal(1,0);
DECLARE ln_f_susp decimal(1,0);
DECLARE ld_dat_susp_debut date;
DECLARE ld_dat_susp_fin date;
DECLARE ln_f_expire decimal(1,0);
DECLARE ld_dat_expire date;

/**
RETURNED VALUES:
	1 : ACTIF
    2 : INACTIF
    3 : SUSPENDU
    4 : EXPIRE

**/

SET ld_date:=sysdate();

SELECT f_actif,f_susp,dat_susp_debut,dat_susp_fin,f_expire,dat_expire
INTO ln_f_actif,ln_f_susp,ld_dat_susp_debut,ld_dat_susp_fin,ln_f_expire,ld_dat_expire
FROM adm_utilisateur 
WHERE id_user=an_id_user;

IF (ln_f_actif=0) THEN
	RETURN 2;
END IF;

IF(ln_f_susp=1) THEN
	SET ln_is_sus:=1;
    
	IF (ld_dat_susp_debut IS NOT NULL OR ld_dat_susp_fin IS NOT NULL) THEN

        IF(ld_dat_susp_fin IS NOT NULL AND ld_date>ld_dat_susp_fin) THEN
           	SET ln_is_sus:=0;
        END IF;
        
        IF (ld_dat_susp_debut IS NOT NULL AND ld_date<ld_dat_susp_debut) THEN
        	SET ln_is_sus:=0;
        END IF;
        
    END IF;

	IF (ln_is_sus=1) THEN
		RETURN 3;
    END IF;
    
END IF;

IF (ln_f_expire!=(-1) AND (ln_f_expire=1 OR ld_dat_expire<=ld_date)) THEN
 	RETURN 4;

END IF;

RETURN 1;

END;
$$
CREATE FUNCTION adm_get_des_etat_user (
  ac_lang varchar(2),
  an_f_actif numeric,
  an_f_expire numeric,
  an_f_susp numeric,
  ad_dat_expire date,
  ad_dat_susp_debut date,
  ad_dat_susp_fin date
)
RETURNS varchar(300)

BEGIN
	
 DECLARE lc_mes VARCHAR(256);
 DECLARE lc_val_fr VARCHAR(512);
 DECLARE lc_val_ar VARCHAR(512);
 DECLARE lc_code_lib VARCHAR(50);
 DECLARE ld_date DATE;
 DECLARE ld_sys_date DATE;
 DECLARE lb_bool boolean;

SET ld_sys_date:=sysdate();

SET lb_bool:=true;
IF (an_f_actif=0) THEN 
	SET lc_code_lib:='USER_NO_ACTIF';
	ELSE IF (an_f_susp=1 AND (ad_dat_susp_debut IS NOT NULL AND ld_sys_date<ad_dat_susp_debut) 
        AND (an_f_expire=1 OR (ad_dat_expire IS NOT NULL AND ld_sys_date>ad_dat_expire AND an_f_expire!=(-1)))) THEN
			SET lc_code_lib:='USER_EXPIRE';
            ELSE IF (an_f_susp=1 AND NOT(ad_dat_susp_fin IS NOT NULL AND ld_sys_date>ad_dat_susp_fin)) THEN 
            	SET lc_code_lib:='USER_SUSP';
                SET lb_bool:=false;
                ELSE IF (an_f_expire=1 OR (ad_dat_expire IS NOT NULL AND ld_sys_date>ad_dat_expire AND an_f_expire!=(-1))) THEN 
                	SET lc_code_lib:='USER_EXPIRE';
                	ELSE SET lc_code_lib:='USER_ACTIF';
END IF;    
END IF;
END IF;
END IF;
        
SELECT val_ar,val_fr into lc_val_ar,lc_val_fr FROM adm_libelle where code=lc_code_lib;

IF(ac_lang='AR') THEN
	SET lc_mes:=lc_val_ar;
ELSE
	SET lc_mes:=lc_val_fr;
END IF;

IF (lb_bool) THEN
	RETURN lc_mes;
END IF;

IF(an_f_susp=1) THEN
    IF(ad_dat_susp_debut IS NOT NULL OR ad_dat_susp_fin IS NOT NULL)THEN
    	SET lb_bool:=false;
        IF(ad_dat_susp_debut IS NULL) THEN
        	SET lc_code_lib:='USER_SUSP_FIN';
     		ELSE IF (ad_dat_susp_fin IS NULL) THEN
            	SET lc_code_lib:='USER_SUSP_DEB';
            ELSE  
            SET lc_code_lib:='USER_SUSP_DEB_1';
            SET lb_bool:=true;
    	END IF;
      END IF;
        
   SELECT val_ar,val_fr into lc_val_ar,lc_val_fr FROM adm_libelle where code=lc_code_lib;
        
    IF(ac_lang='AR') THEN
			SET lc_mes:=lc_mes||' '||lc_val_ar;
		ELSE
			SET lc_mes:=lc_mes||' '||lc_val_fr;
		END IF;
        
        IF(ad_dat_susp_debut IS NULL) THEN
        	SET ld_date:=ad_dat_susp_fin;
        ELSE
          SET ld_date:=ad_dat_susp_debut;
        END IF;
        
        SET lc_mes:=lc_mes||' '||adm_formate_date(ld_date,ac_lang);
        
        IF (NOT lb_bool) THEN
        	RETURN lc_mes;
        END IF;
        
        SELECT val_ar,val_fr into lc_val_ar,lc_val_fr FROM adm_libelle where code='USER_SUSP_FIN_1';
        
    IF(ac_lang='AR') THEN
			SET lc_mes:=lc_mes||' '||lc_val_ar;
		ELSE
			SET lc_mes:=lc_mes||' '||lc_val_fr;
		END IF;
        
        SET lc_mes:=lc_mes||' '||adm_formate_date(ad_dat_susp_fin,ac_lang);
        
        RETURN lc_mes;
    END IF;
    
    RETURN lc_mes; 
END IF;

RETURN lc_mes;

END;
$$
CREATE  PROCEDURE adm_update_date_exp_user (
  an_id_user bigint
)

update_label:BEGIN
  
  DECLARE ln_nb_jour integer;
  
  SELECT CONVERT(val_param,SIGNED INTEGER)
  INTO ln_nb_jour 
  FROM adm_param 
  WHERE cod_param='USER_SESSION_VALIDITY';
  
  IF (ln_nb_jour=0) THEN
  	LEAVE update_label;
  END IF;
  
  UPDATE adm_utilisateur
  SET dat_expire=ADDDATE(sysdate(),INTERVAL ln_nb_jour DAY)
  WHERE id_user=an_id_user;
  
END;

$$  
CREATE VIEW v_adm_fonc_utilisateur (
    id_fonc,
    id_parent,
    id_app,
    cod,
    label,
    action,
    icon,
    f_aff_menu,
    f_aff_etat,
    f_admin,
    des_fonc,
    des_fonc_ar,
    f_editer,
    f_valider,
    id_user,
    url_acces)
AS
SELECT fonc.id_fonc, fonc.id_parent, fonc.id_app, fonc.cod, fonc.label,
    fonc.action, fonc.icon, fonc.f_aff_menu, fonc.f_aff_etat, fonc.f_admin,
    fonc.des_fonc, fonc.des_fonc_ar, max(fp.f_editer) AS f_editer,
    max(fp.f_valider) AS f_valider, u.id_user,fonc.url_acces
FROM adm_fonc fonc, adm_fonc_profil fp, adm_user_profil up, adm_utilisateur u, adm_profil p
WHERE fonc.id_fonc = fp.id_fonc AND up.id_profil = fp.id_profil AND up.id_user
    = u.id_user AND fonc.f_valid = 1 AND fp.id_profil = p.id_profil AND p.f_actif = 1
GROUP BY fonc.id_fonc, fonc.id_parent, fonc.id_app, fonc.cod, fonc.label,
    fonc.action, fonc.icon, fonc.f_aff_menu, fonc.f_aff_etat, fonc.f_admin, fonc.des_fonc, fonc.des_fonc_ar, u.id_user;
$$
CREATE or replace VIEW v_adm_profil (
    id_profil,
    id_app,
    cod_profil,
    des_profil,
    des_profil_ar,
    f_actif,
    des_fr_actif,
    des_ar_actif,
    dat_creat,
    dat_der_modif,
    cod_app,
    des_app,
    des_app_ar)
AS
SELECT p.id_profil, p.id_app, p.cod_profil, p.des_profil, p.des_profil_ar,
    p.f_actif,
    (
    SELECT ou.val_fr
    FROM adm_libelle ou
    WHERE ou.code= 'CHOU' AND ou.ordre =
                CASE p.f_actif
                    WHEN 0 THEN 2
                    WHEN 1 THEN 1
                    ELSE NULL
                END
    ) AS des_fr_actif,
    (
    SELECT ou.val_ar
    FROM adm_libelle ou
    WHERE ou.code = 'CHOU' AND ou.ordre =
                CASE p.f_actif
                    WHEN 0 THEN 2
                    WHEN 1 THEN 1
                    ELSE NULL
                END
    ) AS des_ar_actif,
    p.dat_creat, p.dat_der_modif, a.cod_app, a.des_app, a.des_app_ar
FROM adm_profil p
   LEFT JOIN adm_application a ON p.id_app = a.id_app;
$$ 
CREATE OR REPLACE VIEW v_adm_fonc_profil (
    id_fonc_profil,
    id_fonc,
    id_profil,
    f_editer,
    f_valider,
    id_app_profil,
    cod_profil,
    des_profil,
    des_profil_ar,
    f_actif,
    des_fr_actif,
    des_ar_actif,
    dat_creat,
    dat_der_modif,
    cod_app,
    des_app,
    des_app_ar,
    id_parent,
    id_app_fonc,
    cod,
    label,
    action,
    icon,
    f_aff_menu,
    f_aff_etat,
    f_admin,
    des_fonc,
    des_fonc_ar)
AS
SELECT fp.id_fonc_profil, fp.id_fonc, fp.id_profil, fp.f_editer, fp.f_valider,
    vp.id_app AS id_app_profil, vp.cod_profil, vp.des_profil, vp.des_profil_ar,
    vp.f_actif, vp.des_fr_actif, vp.des_ar_actif, vp.dat_creat,
    vp.dat_der_modif, vp.cod_app, vp.des_app, vp.des_app_ar, f.id_parent,
    f.id_app AS id_app_fonc, f.cod, f.label, f.action, f.icon, f.f_aff_menu,
    f.f_aff_etat, f.f_admin, f.des_fonc, f.des_fonc_ar
FROM adm_fonc_profil fp
   JOIN v_adm_profil vp ON fp.id_profil = vp.id_profil
   JOIN adm_fonc f ON fp.id_fonc = f.id_fonc
WHERE f.f_valid = 1;

$$

CREATE OR REPLACE VIEW v_adm_utilisateur (
    id_user,
    login,
    pwd,
    mail,
    cin,
    nom_user,
    nom_user_ar,
    dat_naiss,
    genre,
    autre_profession,
    direction_rattache,
    dat_der_cx,
    f_actif,
    f_expire,
    f_susp,
    dat_expire,
    dat_der_modif,
    dat_creat,
    dat_susp_debut,
    dat_susp_fin,
    id_profes,
    cod_profes,
    des_profes,
    des_profes_ar,
    id_user_etat,
    des_fr_user_etat,
    des_ar_user_etat)
AS
SELECT u.id_user, u.login, u.pwd, u.mail, u.cin, u.nom_user, u.nom_user_ar,
    u.dat_naiss, u.genre, u.autre_profession, u.direction_rattache,
    u.dat_der_cx, u.f_actif, u.f_expire, u.f_susp, u.dat_expire,
    u.dat_der_modif, u.dat_creat, u.dat_susp_debut, u.dat_susp_fin, u.id_profes,
    p.cod_profes, p.des_profes, p.des_profes_ar,
    (
    SELECT t.id
    FROM adm_libelle t
    WHERE t.code =
                CASE adm_verify_user_etat(u.id_user)
                    WHEN 1 THEN 'USER_ACTIF'
                    WHEN 2 THEN 'USER_NO_ACTIF'
                    WHEN 3 THEN 'USER_SUSP'
                    WHEN 4 THEN 'USER_EXPIRE'
                    ELSE NULL
                END
    ) AS id_user_etat,
    adm_get_des_etat_user('FR', u.f_actif, u.f_expire,
        u.f_susp, u.dat_expire, u.dat_susp_debut, u.dat_susp_fin) AS des_fr_user_etat,
    adm_get_des_etat_user('AR', u.f_actif, u.f_expire,
        u.f_susp, u.dat_expire, u.dat_susp_debut, u.dat_susp_fin) AS des_ar_user_etat
FROM adm_utilisateur u
   LEFT JOIN adm_profession p ON u.id_profes = p.id_profes;

$$
  
CREATE OR REPLACE VIEW v_adm_log_acces (
    id_log,
    dat_log,
    time_log,
    session_id,
    remote_host,
    remote_addr,
    browser,
    login,
    acces,
    acces_fr,
    acces_ar,
    id_user,
    nom,
    id_fonc,
    label_fonc,
    obs)
AS
SELECT log_acces.id_log, log_acces.dat_log, TIME_FORMAT(log_acces.time_log,'HH24:MI:SS'),
    log_acces.session_id, log_acces.remote_host, log_acces.remote_addr,
    log_acces.browser, log_acces.login,log_acces.acces,(select ti.val_fr from adm_libelle  ti where log_acces.acces=ti.code), 
    (select ti.val_ar from adm_libelle  ti where log_acces.acces=ti.code),log_acces.id_user,
    log_acces.nom, log_acces.id_fonc, log_acces.label_fonc, log_acces.obs
FROM adm_log_acces log_acces;

$$

CREATE OR REPLACE VIEW v_adm_log_data (
    id_log,
    dat_log,
    time_log,
    session_id,
    remote_host,
    remote_addr,
    browser,
    login,
    id_user,
    nom,
    typ_op,
    typ_op_fr,
    typ_op_ar,
    table_name,
    data)
AS
SELECT log_data.id_log, log_data.dat_log, TIME_FORMAT( log_data.time_log,'HH24:MI:SS'),
    log_data.session_id, log_data.remote_host, log_data.remote_addr,
    log_data.browser, log_data.login, log_data.id_user, log_data.nom,log_data.typ_op,
    (select ti.val_fr from adm_libelle  ti where  log_data.typ_op=ti.code),
    (select ti.val_ar from adm_libelle  ti where  log_data.typ_op=ti.code), 
	log_data.table_name, log_data.data
FROM adm_log_data log_data;
	$$
DELIMITER ;

INSERT INTO adm_application (cod_app, des_app, des_app_ar) VALUES ('01', 'APPLICATION', 'التطبيقة');
INSERT INTO adm_application (cod_app, des_app, des_app_ar) VALUES ('02', 'ADMINISTRATION APPLICATION', 'إدارة نظام التطبيقة');


INSERT INTO adm_fonc (id_parent, id_app, cod, label, action, icon, f_aff_menu, f_aff_etat, f_admin, des_fonc, des_fonc_ar, f_valid) VALUES ( NULL, 2, '080000', 'menu.admin', NULL, NULL, 1, 0, 1, 'Administration', 'الإدارة', 1);
INSERT INTO adm_fonc (id_parent, id_app, cod, label, action, icon, f_aff_menu, f_aff_etat, f_admin, des_fonc, des_fonc_ar, f_valid,url_acces) VALUES ( 1, 2, '060200', 'menu.admin.user', 'userUI.initBean()', NULL, 1, 0, 1, 'Gestion des utilisateurs', 'إدارة المستخدمين', 1 ,'/pages/admin/user/**');
INSERT INTO adm_fonc (id_parent, id_app, cod, label, action, icon, f_aff_menu, f_aff_etat, f_admin, des_fonc, des_fonc_ar, f_valid,url_acces) VALUES ( 1, 2, '060300', 'menu.amdin.acces', 'logAccessUI.initBean()', NULL, 1, 0, 1, 'Traçage des accés', 'تتبع المستخدمين', 1,'/pages/admin/log/listeLogAccess.jsf*');
INSERT INTO adm_fonc (id_parent, id_app, cod, label, action, icon, f_aff_menu, f_aff_etat, f_admin, des_fonc, des_fonc_ar, f_valid,url_acces) VALUES ( 1, 2, '060400', 'menu.admin.donne', 'logDataUI.initBean()', NULL, 1, 0, 1, 'Traçage des données', 'تتبع البيانات', 1,'/pages/admin/log/listeLogData.jsf*');
INSERT INTO adm_fonc (id_parent, id_app, cod, label, action, icon, f_aff_menu, f_aff_etat, f_admin, des_fonc, des_fonc_ar, f_valid,url_acces) VALUES ( 1, 2, '060100', 'menu.admin.profil', 'profilUI.initBean()', NULL, 1, 0, 1, 'Gestion des profils', 'إدارة الصلاحيّات', 1,'/pages/admin/profil/**');
INSERT INTO adm_fonc (id_parent, id_app, cod, label, action, icon, f_aff_menu, f_aff_etat, f_admin, des_fonc, des_fonc_ar, f_valid,url_acces) VALUES ( 1, 2, '080200', 'menu.nmtAdmin.prof', 'professionUI.initBean()', NULL, 1, 0, 1, 'Profession', 'المهن', 1,'/pages/admin/nmt/listeProfes.jsf*');



INSERT INTO adm_profession ( cod_profes, des_profes, des_profes_ar) VALUES ( 'ING', 'Ingénieur', 'مهندس');
INSERT INTO adm_profession ( cod_profes, des_profes, des_profes_ar) VALUES ( 'CC', 'Chef cabinet', 'رئيس ديوان');
INSERT INTO adm_profession (cod_profes, des_profes, des_profes_ar) VALUES ( 'DirGen', 'Directeur général', 'مدير عام');
INSERT INTO adm_profession ( cod_profes, des_profes, des_profes_ar) VALUES ( 'ZZZ', 'Autre profession ...', 'مهنة أخرى');


INSERT INTO adm_profil ( id_app, cod_profil, des_profil, des_profil_ar, f_actif, dat_creat, dat_der_modif) VALUES ( 2, 'ADMIN', 'Administration', 'صلاحية الاداري', 1, '2014-01-01', '2014-01-01');


INSERT INTO adm_fonc_profil ( id_fonc, id_profil, f_editer, f_valider) VALUES ( 1, 1, 1, 1);
INSERT INTO adm_fonc_profil ( id_fonc, id_profil, f_editer, f_valider) VALUES ( 2, 1, 1, 1);
INSERT INTO adm_fonc_profil ( id_fonc, id_profil, f_editer, f_valider) VALUES ( 3, 1, 1, 1);
INSERT INTO adm_fonc_profil ( id_fonc, id_profil, f_editer, f_valider) VALUES ( 4, 1, 1, 1);
INSERT INTO adm_fonc_profil ( id_fonc, id_profil, f_editer, f_valider) VALUES ( 5, 1, 1, 1);
INSERT INTO adm_fonc_profil ( id_fonc, id_profil, f_editer, f_valider) VALUES ( 6, 1, 1, 1);


INSERT INTO adm_utilisateur ( id_profes, login, pwd, mail, cin, nom_user, nom_user_ar, dat_naiss, genre, autre_profession, direction_rattache, dat_der_cx, f_actif, f_expire, f_susp, dat_expire, dat_der_modif, dat_creat, dat_susp_debut, dat_susp_fin) VALUES ( 2, 'admin', '23b6b319e4461abc633f07c9d42ccd14', 'admin@st2i.com.tn', '00000000', 'Administrateur', 'إداري', '2013-12-26', 1, NULL, NULL, NULL, 1, 1, 0, NULL, NULL, '2014-01-01', NULL, NULL);


INSERT INTO adm_user_profil ( id_user, id_profil, f_valid, dat_expire) VALUES ( 1, 1, 1, NULL);


INSERT INTO adm_param ( cod_param, val_param) VALUES ( 'OTHER_PROFESSION_ID', '4');
INSERT INTO adm_param ( cod_param, val_param) VALUES ( 'USER_SESSION_VALIDITY', '30');


INSERT INTO adm_libelle ( code, val_fr, val_ar, ordre) VALUES ( 'USER_SUSP_DEB_1', 'du', 'من', 1);
INSERT INTO adm_libelle ( code, val_fr, val_ar, ordre) VALUES ( 'USER_SUSP_FIN_1', 'au', 'إلى ', 1);
INSERT INTO adm_libelle ( code, val_fr, val_ar, ordre) VALUES ( 'TYPE_OP', 'Ajout ou modification', 'إضافة أو تعديل', 1);
INSERT INTO adm_libelle ( code, val_fr, val_ar, ordre) VALUES ( 'TYPE_OP', 'Suppression', 'حذف', 1);
INSERT INTO adm_libelle ( code, val_fr, val_ar, ordre) VALUES ( 'USER_SUSP_FIN', 'jusqu''au', 'إلى غاية', 1);
INSERT INTO adm_libelle ( code, val_fr, val_ar, ordre) VALUES ( 'USER_ACTIF', 'Actif', 'نشط', 1);
INSERT INTO adm_libelle ( code, val_fr, val_ar, ordre) VALUES ( 'USER_SUSP_DEB', 'à partir du', 'إبتداء من', 1);
INSERT INTO adm_libelle ( code, val_fr, val_ar, ordre) VALUES ( 'USER_NO_ACTIF', 'Non-actif', 'غير نشط', 2);
INSERT INTO adm_libelle ( code, val_fr, val_ar, ordre) VALUES ( 'USER_SUSP', 'Suspendu', 'معلق الصلاحية', 3);
INSERT INTO adm_libelle ( code, val_fr, val_ar, ordre) VALUES ( 'USER_EXPIRE', 'Expiré', 'منتهي الصلاحية', 4);
INSERT INTO adm_libelle ( code, val_fr, val_ar, ordre) VALUES ( 'SAVE_OR_EDIT', 'Ajout/Modification', 'إضافة/تحيين', 1);
INSERT INTO adm_libelle ( code, val_fr, val_ar, ordre) VALUES ( 'DELETE', 'Suppression', 'حذف', 1);
INSERT INTO adm_libelle ( code, val_fr, val_ar, ordre) VALUES ( 'SUCCES', 'Succès', 'نجاح', 1);
INSERT INTO adm_libelle ( code, val_fr, val_ar, ordre) VALUES ( 'ECHEC', 'Echec', 'فشل', 1);
INSERT INTO adm_libelle ( code, val_fr, val_ar, ordre) VALUES ( 'CHOU', 'Oui', 'نعم', 1);
INSERT INTO adm_libelle ( code, val_fr, val_ar, ordre) VALUES ( 'CHOU', 'Non', 'لا', 2);


   
