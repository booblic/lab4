INSERT INTO ROLE (ROLE_ID, ROLE_NAME , AUTHORITY) VALUES
  (1, 'Users', 'ROLE_USER'),
  (2, 'Administrators', 'ROLE_ADMIN');
INSERT INTO USER (USER_ID, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, MIDDLE_NAME, EMAIL, PHONE_NUMBER) VALUES
  (1, 'admin', '$2a$10$ks.dpu2YDc5BrpoZwR6T2e.l5EScUuSuyYDoxtuCx3nVU2YVCVlA.', 'Admin' , 'Adminov', 'Adminovich', 'admin@gmail.com', '88005553535'),
  (2, 'kirill95', '$2a$10$nqRN2rqMSkSDU9eMgSobAeuAjBvH20OyLd7qSV5v7YdFTwZg/ypq2', 'Kirill' , 'Terentev', 'Aleksandrovich', 'johnyXY@yandex.ru', '88005553535');

INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES
  (1, 1),
  (1, 2),
  (2, 1);