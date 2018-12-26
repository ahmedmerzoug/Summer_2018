delete from adm_profil WHERE cod_profil='testNG';
delete from adm_profil WHERE cod_profil='testNGAdd';
delete from adm_application WHERE cod_app='testNG';
INSERT INTO adm_application(id_app,cod_app,des_app,des_app_ar) VALUES(-1,'testNG','testNG','testNG');
INSERT INTO adm_profil(
            id_profil, id_app, cod_profil, des_profil, des_profil_ar, f_actif, 
            dat_creat, dat_der_modif)
    VALUES (-1, -1, 'testNG', 'testNG', 'testNG', 1, current_date, current_date);
COMMIT;