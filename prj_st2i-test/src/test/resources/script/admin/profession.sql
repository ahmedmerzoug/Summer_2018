DELETE FROM adm_profession where cod_profes='testNG';
DELETE FROM adm_profession where cod_profes='testNGAdd';
INSERT INTO adm_profession(id_profes,
           cod_profes, des_profes, des_profes_ar)
    VALUES (-1,'testNG', 'testNG', 'testNG');
COMMIT;